/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.dao.ContractorWorkerRecommendationDAO;
import br.com.acheumprofissional.dao.DataAccessException;
import br.com.acheumprofissional.dao.WorkerDAO;
import br.com.acheumprofissional.dao.WorkerViewsDAO;
import br.com.acheumprofissional.vo.ContractorVO;
import br.com.acheumprofissional.vo.ContractorWorkerRecomendationVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Service class for Worker.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class WorkerService {

	static Logger logger = Logger.getLogger (WorkerService.class.getName());	
	private WorkerDAO workerDAO = new WorkerDAO();
	private WorkerViewsDAO workerViewsDAO = new WorkerViewsDAO();
	private ContractorWorkerRecommendationDAO contractorWorkerRecommendationDAO = new ContractorWorkerRecommendationDAO();
	
	public WorkerService() {
		
    }
	
	/**
	 * Inserts a new worker.
	 * 
	 * @param workerVO the worker.
	 * @return affected rows.
	 * @throws BusinessException
	 */
	public int insert (WorkerVO workerVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = workerDAO.insert (workerVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the worker. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Updates the worker.
	 * 
	 * @param workerVO the worker.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (WorkerVO workerVO) throws BusinessException {
		int workerAffectedRows = 0;
		try {
			workerAffectedRows = workerDAO.update (workerVO);
			logger.debug ("workerAffectedRows [" + workerAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the worker. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return workerAffectedRows;
	}
	
	/**
	 * Updates the worker pwd.
	 * 
	 * @param workerVO the worker.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updatePwd (WorkerVO workerVO) throws BusinessException {
		int workerAffectedRows = 0;
		try {
			workerAffectedRows = workerDAO.updatePwd (workerVO);
			logger.debug ("workerAffectedRows [" + workerAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the worker pwd. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return workerAffectedRows;
	}
	
	/**
	 * Updates the worker recommendations.
	 * 
	 * @param workerVO the worker.
	 * @return
	 * @throws BusinessException
	 */
	public int updateRecommendations (WorkerVO workerVO, ContractorVO contractorVO) throws BusinessException {
		int affectedRows = 0;
		try {
			workerVO = workerDAO.findById(workerVO.getWorkerId());
			
			logger.debug("workerVO.getRecommendations(): " + workerVO.getRecommendations());
			
			
			int recommendations = workerVO.getRecommendations() + 1;
			workerVO.setRecommendations(recommendations);
			affectedRows = workerDAO.updateRecomendation(workerVO);
			
			//update the contractorWorkerRecommendation as well
			ContractorWorkerRecomendationVO contractorWorkerRecommendationVO = new ContractorWorkerRecomendationVO();
			contractorWorkerRecommendationVO.setContractorId(contractorVO.getContractorId());
			contractorWorkerRecommendationVO.setWorkerId(workerVO.getWorkerId());
			contractorWorkerRecommendationDAO.insert(contractorWorkerRecommendationVO);
			
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the worker recommendations. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return affectedRows;
	}
	
	/**
	 * Finds the worker by username.
	 * @param username the username.
	 * @return a worker.
	 * @throws BusinessException
	 */
	public WorkerVO findByUsername (String username) throws BusinessException {
		WorkerVO workerVO = null;
		try {
			workerVO = workerDAO.findByUsername(username);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the worker by username. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return workerVO;
	}
	
	/**
	 * Finds email by username.
	 * 
	 * @param username the username.
	 * @return
	 * @throws BusinessException
	 */
	public WorkerVO findEmailByUsername (String username) throws BusinessException {
		WorkerVO workerVO = null;
		try {
			workerVO = workerDAO.findEmailByUsername(username);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the worker email by username. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return workerVO;
	}
	
	/**
	 * Finds the worker by id.
	 * @param id the id.
	 * @return a worker.
	 * @throws BusinessException
	 */
	public WorkerVO findById (Long id) throws BusinessException {
		WorkerVO workerVO = null;
		try {
			workerVO = workerDAO.findById(id);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the worker by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return workerVO;
	}
	
	/**
	 * Finds the worker by id with work views.
	 * @param id the id.
	 * @return a worker.
	 * @throws BusinessException
	 */
	public List<WorkerVO> findByIdWithWorkerViews (Long workerId) throws BusinessException {
		List<WorkerVO> workerVOList = new ArrayList<WorkerVO>();
		try {
			List<WorkerVO> workerVOListDb = workerViewsDAO.findByWorkerId(workerId);
			for(WorkerVO workerVO : workerVOListDb) {
				WorkerVO newWorkerVO = findById(workerVO.getWorkerId());
				newWorkerVO.setWorkerViewsCount(workerVO.getWorkerViewsCount());
				workerVOList.add(newWorkerVO);
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the worker by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return workerVOList;
	}
	
	/**
	 * Finds workers by job category id. 
	 * 
	 * @param jobCategoryId the job category id.
	 * @return a list of workers.
	 * @throws BusinessException
	 */
	public List<WorkerVO> findByJobCategoryId (Long jobCategoryId) throws BusinessException {
		List<WorkerVO> workerVOList = null;
		try {
			workerVOList = workerDAO.findByJobCategoryId(jobCategoryId);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the workers by job category id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return workerVOList;
	}
	
	/**
	 * Checks if a username exists.
	 * 
	 * @param username the username.
	 * @return the result.
	 * @throws BusinessException
	 */
	public boolean existsUsername (String username) throws BusinessException {
		boolean result = false;
		try {
			result = workerDAO.existsUsername(username);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while checking if an username exists. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return result;
	}
	
	/**
	 * Checks if a Cpf exists.
	 * 
	 * @param cpf the cpf.
	 * @return the result.
	 * @throws BusinessException
	 */
	public boolean existsCpf (String cpf) throws BusinessException {
		boolean result = false;
		try {
			result = workerDAO.existsCpf(cpf);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while checking if a cpf exists. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return result;
	}
	
}
