/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

import br.com.acheumprofissional.vo.ContractorWorkerRecomendationVO;

/**
 * DAO class for WorkerViewsDAO.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ContractorWorkerRecommendationDAO extends AbstractDAO {

	
	public ContractorWorkerRecommendationDAO() {
		
	}
	
	/**
	 * Inserts a new contractorWorkerRecomendation.
	 * 
	 * @param contractorWorkerRecommendationVO the contractorWorkerRecommendationVO.
	 * @return the new worker id.
	 * @throws DataAccessException
	 */
	public Long insert (ContractorWorkerRecomendationVO contractorWorkerRecommendationVO) throws DataAccessException {
		Long id = new Long(0);
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CONTRACTOR_WORKER_RECOMMENDATION (CONTRACTOR_ID, WORKER_ID) ");
		sbSql.append ("VALUES (" + contractorWorkerRecommendationVO.getContractorId() + ", ");
		sbSql.append (contractorWorkerRecommendationVO.getWorkerId() + ") ");
		logger.debug ("sql: " + sbSql.toString());
		id = insertDbWithLongKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Checks if exists a recommendation.
	 * 
	 * @param contractorId the contractor id.
	 * @param workerId the worker id.
	 * @return
	 * @throws DataAccessException
	 */
	public boolean existsRecommendation (Long contractorId, Long workerId) throws DataAccessException {
		boolean result = false;
		String sql = null;
		sql = "SELECT COUNT(*) " + 
			  "FROM CONTRACTOR_WORKER_RECOMMENDATION " + 
			  "WHERE CONTRACTOR_ID = " + contractorId + " AND WORKER_ID = " + workerId;
		logger.debug ("sql: " + sql);
		int count = selectRowCount (sql);
		result = count > 1 ? true : false;
		return result;
	}
			
}
