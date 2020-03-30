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

	//������
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		//��ȡHttp��������Ӧ
		HttpServletRequest request =(HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		//��ȡSession
		HttpSession session = request.getSession();
		//�ӻỰ�л�ȡ�û�
		Userinfo user = (Userinfo) session.getAttribute("user");
		//�ж��û��Ƿ��¼,��Ϊ�վ�ͨ��
		if (user != null) {
			//ͨ��
			chain.doFilter(request, response);
		}
		

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
