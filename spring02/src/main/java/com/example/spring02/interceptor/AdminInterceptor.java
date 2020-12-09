package com.example.spring02.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//세션객체생성
		HttpSession session=request.getSession();
		//admin_userid가 없으면
		if(session.getAttribute("admin_userid")==null) {
			//관리자 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath()
					+"/admin/login.do?message=nologin");
			return false;//예를들어 상품등록관련 /product/write.do요청이
			//와서 관리자 세션이 없으면 login 페이지로 이동
		}else {
			return true; //세션 변수가 있으면 계속 
			//메인액션(/product/write.do)으로 처리
		}
	}//preHandle()
	
	//후처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

}
