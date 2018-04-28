/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.filter;


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

import org.apache.log4j.Logger;

import br.com.acheumprofissional.controller.LoginController;
import br.com.acheumprofissional.util.Constants;

/**
 * Session expired Security Filter.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class SessionExpiredFilter implements Filter {
	
	static Logger logger = Logger.getLogger (SessionExpiredFilter.class.getName());
		
	@Override
	public void init (FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter (ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    String uri = request.getRequestURI();
	    HttpSession session = request.getSession (false);
	    if (isExpiredAccess (uri, request, session)) {
	    	response.sendRedirect (Constants.URI_WELCOME_PAGE);
	    } else {
	    	chain.doFilter (req, res);
	    }	    
	}	

	@Override
	public void destroy() {
		
	}
	
	/**
	 * Checks if the given access is unauthorized or not.
	 * 
	 * @param uri the uri.
	 * @param request the request.
	 * @param session the session.
	 * @return the operation result.
	 */
	private boolean isExpiredAccess (String uri, HttpServletRequest request, HttpSession session) {
		boolean success = false;
		if (this.isRestrictedURI (uri) 
				&& !this.isLoggedUser (request, session)) {
	    	logger.debug ("uri: " + uri);
	    	logger.warn ("session is expired.");
			success = true;			
	    }
		return success;
	}
	
	/**
	 * Checks if the uri is restricted or not.
	 * 
	 * @param uri the uri.
	 * @return
	 */
	private boolean isRestrictedURI (String uri) {
		if (uri.indexOf ("changeWorkerPwd.page") == -1
				&& uri.indexOf ("changeContractorPwd") == -1
				&& uri.indexOf ("contactsViewed") == -1
				&& uri.indexOf ("contactViews") == -1
				&& uri.indexOf ("myContractorData") == -1
				&& uri.indexOf ("myWorkerData") == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the user is logged or not.
	 * 
	 * @param request the request.
	 * @return the operation result.
	 */
	private boolean isLoggedUser (HttpServletRequest request, HttpSession session) {
		boolean isLogged = false;
		if (session != null				
				&& session.getAttribute("loginController") != null) {
			LoginController loginController = (LoginController) session.getAttribute("loginController");
			if (loginController.isLogged()) {
				isLogged = true;
			}
		}
		return isLogged;
	}

}
