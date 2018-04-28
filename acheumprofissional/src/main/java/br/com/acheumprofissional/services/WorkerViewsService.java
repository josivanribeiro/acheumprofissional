/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.dao.DataAccessException;
import br.com.acheumprofissional.dao.WorkerViewsDAO;
import br.com.acheumprofissional.vo.WorkerViewsVO;

/**
 * Service class for WorkerViewsService.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class WorkerViewsService {

	static Logger logger = Logger.getLogger (WorkerViewsService.class.getName());	
	private WorkerViewsDAO workerViewsDAO = new WorkerViewsDAO();
	
	public WorkerViewsService() {
		
    }
	
	/**
	 * Inserts a new workerViewsService.
	 * 
	 * @param workerViewsVO the workerViews.
	 * @return the new worker views id.
	 * @throws BusinessException
	 */
	public Long insert (WorkerViewsVO workerViewsVO) throws BusinessException {
		Long newWorkerViewId = new Long(0);
		try {
			newWorkerViewId = workerViewsDAO.insert (workerViewsVO);
			logger.debug ("new newWorkerViewsId [" + newWorkerViewId + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the workerViews. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return newWorkerViewId;
	}
		
}
