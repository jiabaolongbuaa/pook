package com.app.server.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.app.server.constant.ResponseCode;
import com.app.server.web.ServerResponseBean;

public class NonUserFilter implements Filter {
	static Logger logger = Logger.getLogger(NonUserFilter.class.getName());
	private FilterConfig fc;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;

		String ua = req.getHeader("User-Agent");

		if (!StringUtils.isEmpty(ua)) {
			// return ;
		}

		HttpSession session = req.getSession(false);
		if (session != null) {
			// logger.info("in filter session = "+session.getId());
			Object userId = session.getAttribute("user");
			if (userId != null) {
				// logger.info("in filter session userId is not null");
				chain.doFilter(request, response);

			} else {
				// logger.info("in filter session userId is null");
				// 长时间未登陆或帐户在其他设备登陆
				response.getWriter().print(
						JSONObject.fromObject(
								new ServerResponseBean(
										ResponseCode.POOK_NOT_LOGGED_IN, null))
								.toString());

				response.getWriter().flush();
			}
		} else {
			// logger.info("session is null ");
			// 长时间未登陆或帐户在其他设备登陆
			response.getWriter().print(
					JSONObject.fromObject(
							new ServerResponseBean(
									ResponseCode.POOK_NOT_LOGGED_IN, null))
							.toString());

			response.getWriter().flush();
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.fc = config;

	}

}
