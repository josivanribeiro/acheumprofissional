/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.services;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.dao.DataAccessException;
import br.com.acheumprofissional.dao.LoginDAO;

/**
 * Service class for Login.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class LoginService {

	static Logger logger = Logger.getLogger (LoginService.class.getName());	
	private LoginDAO loginDAO = new LoginDAO();
	
	public LoginService() {
		
    }
	
	/**
	 * Performs the worker login.
	 * 
	 * @param username the username.
	 * @param pwd the pwd.
	 * @return a the result of the login operation.
	 * @throws BusinessException
	 */
	public boolean doLoginWorker (String username, String pwd) throws BusinessException {
		boolean isLogged = false;
		try {
			isLogged = loginDAO.doLoginWorker(username, pwd);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while performing the worker login. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return isLogged;
	}
	
	/**
	 * Performs the contractor login.
	 * 
	 * @param contractorVO the contractor.
	 * @return a the result of the login operation.
	 * @throws BusinessException
	 */
	public boolean doLoginContractor (String username, String pwd) throws BusinessException {
		boolean isLogged = false;
		try {
			isLogged = loginDAO.doLoginContractor(username, pwd);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while performing the contractor login. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return isLogged;
	}
	
}
