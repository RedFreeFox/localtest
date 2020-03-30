package org.bugManage.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bugManage.entity.Userinfo;

public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	//过滤器
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		//获取Http请求与响应
		HttpServletRequest request =(HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		//获取Session
		HttpSession session = request.getSession();
		//从会话中获取用户
		Userinfo user = (Userinfo) session.getAttribute("user");
		//判断用户是否登录,不为空就通行
		if (user != null) {
			//通行
			chain.doFilter(request, response);
		}
		

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
