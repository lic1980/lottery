package com.lottery.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.lottery.Const;

public class CustomerAuthFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuthFilter.class);
	private List<String> excludeUris = null;
	private Pattern pattern;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		if (getExcludeUri().contains(uri)) {
			chain.doFilter(request, response);
			return;
		}
		String cusIdFromUri = getCustomerIdFromUri(uri)
				.orElseThrow(() -> new UnavailableException("No right to access " + uri));
		String cusIdFromSession = (String) req.getSession().getAttribute(Const.AGENT_ID_KEY_IN_SESSION);
		if (cusIdFromUri.equals(cusIdFromSession)) {
			chain.doFilter(request, response);
		} else {
			LOGGER.info("There may be soembody try to hack the application, source: {}", req.getRemoteAddr());
			throw new UnavailableException("No right to access " + uri);
		}
	}

	private synchronized Pattern getPattern() {
		if (pattern == null) {
			String regEx = "/customers/(\\d+)\\S*";
			pattern = Pattern.compile(regEx);
		}
		return pattern;
	}

	private synchronized List<String> getExcludeUri() {
		if (excludeUris == null) {
			excludeUris = new ArrayList<>();
			//excludeUris.add("/customers/sessions");
		}
		return excludeUris;
	}

	private Optional<String> getCustomerIdFromUri(String uri) {
		Matcher matcher = getPattern().matcher(uri);
		if (matcher.find()) {
			return Optional.of(matcher.group());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
