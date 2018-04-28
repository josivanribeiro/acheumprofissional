/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.dao.ContractorDAO;
import br.com.acheumprofissional.dao.ContractorWorkerRecommendationDAO;
import br.com.acheumprofissional.dao.DataAccessException;
import br.com.acheumprofissional.dao.WorkerDAO;
import br.com.acheumprofissional.dao.WorkerViewsDAO;
import br.com.acheumprofissional.vo.ContractorVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Service class for Contractor.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ContractorService {

	static Logger logger = Logger.getLogger (ContractorService.class.getName());	
	private ContractorDAO contractorDAO = new ContractorDAO();
	private WorkerViewsDAO workerViewsDAO = new WorkerViewsDAO();
	private WorkerDAO workerDAO = new WorkerDAO();
	private ContractorWorkerRecommendationDAO contractorWorkerRecommendationDAO = new ContractorWorkerRecommendationDAO();
	
	public ContractorService() {
		
    }
	
	/**
	 * Inserts a new contractor.
	 * 
	 * @param contractorVO the contractor.
	 * @return the new contractor id.
	 * @throws BusinessException
	 */
	public Long insert (ContractorVO contractorVO) throws BusinessException {
		Long newContractorId = new Long(0);
		try {
			newContractorId = contractorDAO.insert (contractorVO);
			logger.debug ("new newContractorId [" + newContractorId + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the contractor. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return newContractorId;
	}
	
	/**
	 * Updates the contractor.
	 * 
	 * @param contractorVO the contractor.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (ContractorVO contractorVO) throws BusinessException {
		int contractorAffectedRows = 0;
		try {
			contractorAffectedRows = contractorDAO.update (contractorVO);
			logger.debug ("contractorAffectedRows [" + contractorAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the contractor. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return contractorAffectedRows;
	}
	
	/**
	 * Updates the worker pwd.
	 * 
	 * @param workerVO the worker.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updatePwd (ContractorVO contractorVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = contractorDAO.updatePwd (contractorVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the contractor pwd. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return affectedRows;
	}
	
	/**
	 * Finds the contractor by username.
	 * @param username the username.
	 * @return a contractor.
	 * @throws BusinessException
	 */
	public ContractorVO findByUsername (String username) throws BusinessException {
		ContractorVO contractorVO = null;
		try {
			contractorVO = contractorDAO.findByUsername(username);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the contractor by username. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return contractorVO;
	}
	
	/**
	 * Finds email by username.
	 * 
	 * @param username the username.
	 * @return
	 * @throws BusinessException
	 */
	public ContractorVO findEmailByUsername (String username) throws BusinessException {
		ContractorVO contractorVO = null;
		try {
			contractorVO = contractorDAO.findEmailByUsername(username);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the contractor email by username. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return contractorVO;
	}
	
	/**
	 * Finds the contractor by id.
	 * 
	 * @param id the id.
	 * @return a contractor.
	 * @throws BusinessException
	 */
	public ContractorVO findById (Long id) throws BusinessException {
		ContractorVO contractorVO = null;
		try {
			contractorVO = contractorDAO.findById(id);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the contractor by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return contractorVO;
	}
	
	/**
	 * Finds the worker by contractor id with work views.
	 * @param contractorId the id.
	 * @return a list of workers.
	 * @throws BusinessException
	 */
	public List<WorkerVO> findByIdWithWorkerViews (Long contractorId) throws BusinessException {
		List<WorkerVO> workerVOList = new ArrayList<WorkerVO>();
		try {
			List<WorkerVO> workerVOListDb = workerViewsDAO.findByContractorId(contractorId);
			for(WorkerVO workerVO : workerVOListDb) {
				WorkerVO newWorkerVO = workerDAO.findById(workerVO.getWorkerId());
				newWorkerVO.setWorkerViewsCount(workerVO.getWorkerViewsCount());
				workerVOList.add(newWorkerVO);
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the workers by contract id. " + e.getMessage();
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
			result = contractorDAO.existsUsername(username);
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
			result = contractorDAO.existsCpf(cpf);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while checking if a cpf exists. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return result;
	}
	
	/**
	 * Checks if a recommendation exists.
	 * 
	 * @param contractorId the contractorId.
	 * @param workerId the workerId.
	 * @return the result.
	 * @throws BusinessException
	 */
	public boolean existsRecommendation (Long contractorId, Long workerId) throws BusinessException {
		boolean result = false;
		try {
			result = contractorWorkerRecommendationDAO.existsRecommendation(contractorId, workerId);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while checking if a recommendation exists. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return result;
	}
	
}
