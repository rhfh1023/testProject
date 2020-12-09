package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring02.model.board.dao.BoardDAO;
import com.example.spring02.model.board.dto.BoardDTO;

@Service //service bean 등록
public class BoardServiceImpl implements BoardService {
	
	@Inject
	BoardDAO boardDao;

	@Override
	public void deleteFile(String fullName) {
		boardDao.deleteFile(fullName);
	}

	@Override
	public List<String> getAttach(int bno) {
		return boardDao.getAttach(bno);
	}

	@Override
	public void addAttach(String fullName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAttach(String fullName, int bno) {
		// TODO Auto-generated method stub

	}
//1.글쓰기 - 게시물 번호 생성
//2.첨부파일 등록 - 게시물 번호 사용
//위 두개가 하나의 트랜잭션 단위로 처리	
	@Transactional
	@Override
	public void create(BoardDTO dto) throws Exception {
		//board 테이블에 레코드 추가
		boardDao.create(dto);
		//attach 테이블에 레코드 추가
		String[] files=dto.getFiles();//첨부파일 이름 배열처리
		if(files==null) return;//첨부파일이 없으면 skip
		for(String name : files) {
			boardDao.addAttach(name);//attach 테이블에 insert
		}
	}
    
	@Transactional //트랜잭션 처리
	@Override
	public void update(BoardDTO dto) throws Exception {
		boardDao.update(dto);
		//별도로 만들었던 attach 테이블 함께 수정
		//board와 attach를 transaction처리를 해줘야함
		//service는 transaction으로 묶기에 좀 더 유리한다.
		String[] files=dto.getFiles();
		if(files==null) return;
		for(String name : files) {
			System.out.println("첨부파일 이름 : "+name);
			boardDao.updateAttach(name, dto.getBno());
		}
	}

	@Transactional
	@Override
	public void delete(int bno) throws Exception {
		//reply레코드 삭제
		//attach레코드 삭제
		//board레코드 삭제
		boardDao.delete(bno);
	}

	@Override
	public List<BoardDTO> listAll(String search_option, String keyword,
			int start, int end) throws Exception {
		return boardDao.listAll(search_option,keyword,start,end);
	}

	@Override
	public void increateViewcnt(int bno, HttpSession session) throws Exception {
		long update_time=0;
		if(session.getAttribute("update_time_"+bno)!=null) {
			//최근에 조회수를 올린 시간
			update_time=(long)session.getAttribute("update_time_"+bno);
		}
		long current_time=System.currentTimeMillis();
		if(current_time - update_time > 5*1000) {//24*60*60*1000(하루)
			//조회수 증가 처리
			boardDao.increateViewcnt(bno);
			//조회수를 올린 시간 저장
			session.setAttribute("update_time_"+bno, current_time);
		}
	}

	@Override
	public int countArticle(String serach_option, String keyword) throws Exception {
		return boardDao.countArticle(serach_option, keyword);
	}

	@Override
	public BoardDTO read(int bno) throws Exception {
		return boardDao.read(bno);
	}

}
