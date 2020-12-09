package com.example.spring02.controller.shop;

import java.io.File;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.shop.dto.ProductDTO;
import com.example.spring02.service.shop.ProductService;

@Controller
@RequestMapping("shop/product/*") //공통 url pattern
public class ProductController {
	
	@Inject
	ProductService productService;
	
	@RequestMapping("list.do") //세부 url pattern
	public ModelAndView list(ModelAndView mav) {
		//포워딩할 뷰의 경로
		mav.setViewName("/shop/product_list");
		//전달할 데이터
		mav.addObject("list", productService.listProduct());
		return mav;
	}
	
	@RequestMapping("detail/{product_id}")
	public ModelAndView detail(@PathVariable int product_id
			, ModelAndView mav) {
		//포워딩할 뷰의 이름
		mav.setViewName("/shop/product_detail");
		//뷰에 전달할 데이터
		mav.addObject("dto", productService.detailProduct(product_id));
		return mav;
	}
	
	@RequestMapping("write.do")
	public String write() {
		//views/shop/product_write.jsp이동
		return "shop/product_write";
	}
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute ProductDTO dto) {
		String filename="-"; //not null로 했기에 "-"
		//첨부 파일이 있으면
		if(!dto.getFile1().isEmpty()) {
			//첨부 파일의 이름 가져옴
			filename=dto.getFile1().getOriginalFilename();
			try {
				String path="D:\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core"
						+ "\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
				//디렉토리가 존재하지 않으면 생성
				new File(path).mkdir();
				//임시 디렉토리에 저장된 첨부파일을 이동
				dto.getFile1().transferTo(new File(path+filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//if()
		dto.setPicture_url(filename);
		productService.insertProduct(dto);
		return "redirect:/shop/product/list.do";
	}//insert()
	
	@RequestMapping("edit/{product_id}")
	public ModelAndView edit(@PathVariable("product_id") int product_id,
			ModelAndView mav) {
		//이동할 뷰의 이름
		mav.setViewName("shop/product_edit");
		//뷰에 전달할 데이터 저장
		mav.addObject("dto", productService.detailProduct(product_id));
		return mav;
	}//edit()
	
	@RequestMapping("update.do")
	public String update(ProductDTO dto) {
		String filename="-";
		//새로운 첨부파일이 있으면
		if(!dto.getFile1().isEmpty()) {
			//첨부 파일의 이름 가져옴
			filename=dto.getFile1().getOriginalFilename();
			try {
				String path="D:\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core"
						+ "\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
				//디렉토리가 존재하지 않으면 생성
				new File(path).mkdir();
				//임시 디렉토리에 저장된 첨부파일을 이동
				dto.getFile1().transferTo(new File(path+filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setPicture_url(filename);
		}else {//새로운 첨부 파일이 없을 때
			//기존에 첨부한 파일 정보를 가져옴
			ProductDTO dto2
			=productService.detailProduct(dto.getProduct_id());
			dto.setPicture_url(dto2.getPicture_url());
		}
		//상품정보 수정
		productService.updateProduct(dto);
		return "redirect:/shop/product/list.do";
	}//update()
	
	@RequestMapping("delete.do")
	public String delete(@RequestParam int product_id) {
		//첨부파일 삭제
		String filename=productService.fileInfo(product_id);
		System.out.println("첨부파일 이름:"+filename);
		if(filename != null && !filename.equals("-")) {//파일이 있으면
			String path="D:\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core"
					+ "\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
			File f=new File(path+filename);
			System.out.println("파일존재여부:"+f.exists());
			if(f.exists()) {//파일이 존재하면
				f.delete();//파일 목록 삭제
				System.out.println("삭제되었습니다.");
			}//inner if
		}//outer if
		//레코드 삭제
		productService.deleteProduct(product_id);
		//화면 이동
		return "redirect:/shop/product/list.do";
	}
	

}