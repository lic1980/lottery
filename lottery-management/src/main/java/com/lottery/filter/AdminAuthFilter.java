package com.lottery.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.lottery.Const;

public class AdminAuthFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String adminIdFromSession = (String) req.getSession().getAttribute(Const.ADMIN_ID_KEY_IN_SESSION);
		if (StringUtils.isEmpty(adminIdFromSession)) {
			LOGGER.info("There may be soembody try to hack the application, source: {}", req.getRemoteAddr());
			String uri = req.getRequestURI();
			throw new UnavailableException("No right to access " + uri);
		}
	}

	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
