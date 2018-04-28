/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.dao.DataAccessException;
import br.com.acheumprofissional.dao.JobCategoryDAO;
import br.com.acheumprofissional.vo.JobCategoryVO;

/**
 * Service class for Job Category.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class JobCategoryService {

	static Logger logger = Logger.getLogger (JobCategoryService.class.getName());	
	private JobCategoryDAO jobCategoryDAO = new JobCategoryDAO();
	
	public JobCategoryService() {
		
    }
	
	/**
	 * Finds all the job categories.
	 * 
	 * @return a list of job categories.
	 * @throws BusinessException
	 */
	public List<JobCategoryVO> findAll () throws BusinessException {
		List<JobCategoryVO> list = null;
		try {
			list = jobCategoryDAO.findAll();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding all the job categories. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return list;
	}
	
	/**
	 * Finds by id.
	 * 
	 * @param id the id.
	 * @return
	 * @throws BusinessException
	 */
	public JobCategoryVO findById (Long id) throws BusinessException {
		JobCategoryVO jobCategoryVO = null;
		try {
			jobCategoryVO = jobCategoryDAO.findById(id);
			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the job category. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return jobCategoryVO;
	}
	
}
