/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.services;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.dao.DataAccessException;
import br.com.acheumprofissional.dao.ForgotPwdDAO;
import br.com.acheumprofissional.vo.ForgotPwdVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Service class for Worker.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ForgotPwdService {

	static Logger logger = Logger.getLogger (ForgotPwdService.class.getName());	
	private ForgotPwdDAO forgotPwdDAO = new ForgotPwdDAO();
	
	
	public ForgotPwdService() {
		
    }
	
	/**
	 * Inserts a new worker.
	 * 
	 * @param workerVO the worker.
	 * @return affected rows.
	 * @throws BusinessException
	 */
	public int insert (ForgotPwdVO forgotPwdVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = forgotPwdDAO.insert (forgotPwdVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the forgot pwd. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds by token.
	 * 
	 * @param token the token.
	 * @return
	 * @throws BusinessException
	 */
	public ForgotPwdVO findByToken (String token) throws BusinessException {
		ForgotPwdVO forgotPwdVO = null;
		try {
			forgotPwdVO = forgotPwdDAO.findByToken(token);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the forgot pwd by token. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return forgotPwdVO;
	}	
	
	/**
	 * Checks if a token exists.
	 * 
	 * @param token the token.
	 * @return the result.
	 * @throws BusinessException
	 */
	public boolean existsToken (String token) throws BusinessException {
		boolean result = false;
		try {
			result = forgotPwdDAO.existsToken(token);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while checking if a token exists. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return result;
	}
		
}
