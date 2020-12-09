package com.example.spring02.controller.chart;

import java.io.FileOutputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.service.chart.JFreeChartService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping("jchart/*") //공통  url mapping
public class JFreeChartController {
	
	@Inject
	JFreeChartService chartService;
	
	@RequestMapping("chart1.do") //세부 url
	public void createChart1(HttpServletResponse response) {
		try {
			//챠트 객체 리턴
			JFreeChart chart=chartService.createChart();
			//차트를 png이미지로 export
			ChartUtilities.writeChartAsPNG(response.getOutputStream(),
					chart, 900, 550);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//createChart1
	
	@RequestMapping("chart2.do")
	public ModelAndView createChart2(HttpServletResponse response) {
		String message="";
		try {
			//차트 객체 리턴
			JFreeChart chart=chartService.createChart();
			//pdf문서 객체
			Document document=new Document();
			//pdf생성 객체
			PdfWriter.getInstance(document, 
					new FileOutputStream("d:/test.pdf"));
			document.open();
			//챠트를 itextpdf 라이브러리에서 지원하는 이미지 형식으로
			Image png=Image.getInstance(ChartUtilities.encodeAsPNG(
					chart.createBufferedImage(500, 500)));
			//pdf 문서에 이미지를 추가
			document.add(png);
			//pdf 문서가 저장
			document.close();
			message="pdf파일이 생성되었습니다.";
		} catch (Exception e) {
			message="pdf파일 생성 실패...";
			e.printStackTrace();
		}
		return new ModelAndView("chart/jchart02", "message", message);
		
	}
	
	
	
	
	

}
