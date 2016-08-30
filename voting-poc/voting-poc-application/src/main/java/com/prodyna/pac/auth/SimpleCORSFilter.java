package com.prodyna.pac.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Custom filter to handle Cross-Origin Resource Sharing (CORS) handling
 * <p>
 * Get further information <a href="https://en.wikipedia.org/wiki/Cross-origin_resource_sharing">here</a>.
 *
 * @see GenericFilterBean
 */
public class SimpleCORSFilter extends GenericFilterBean {

	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

	public SimpleCORSFilter() {
		super();
		log.info("SimpleCORSFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "OPTIONS, POST, GET, PUT, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, X-AUTH-TOKEN");
		response.setHeader("Access-Control-Expose-Headers", "X-AUTH-TOKEN");

		chain.doFilter(request, res);
	}

}