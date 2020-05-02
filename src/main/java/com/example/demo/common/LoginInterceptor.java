package com.example.demo.common;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.entity.ActionInfoBean;
import com.example.demo.entity.UserInfoBean;

/**
 * ログイン判定用インターセプター
 *
 */

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String contextPath = request.getContextPath();

		// sessionタイムアウトまたはログアウト状態
		if ((UserInfoBean) request.getSession().getAttribute("userInfo") == null) {
			System.out.println(request.getRequestURI());
			ActionInfoBean action = new ActionInfoBean();
			action.setAccessUrl(request.getRequestURI());
			action.setMethod(request.getMethod());
			action.setParameter(request.getParameterMap());
			request.getSession().setAttribute("actionInfo", action);

			RequestDispatcher dispatch = request.getRequestDispatcher("/callLogin/view");
			dispatch.forward(request, response);

			return false;
		}
		return true;
	}

}
