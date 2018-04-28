/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.acheumprofissional.vo.WorkerVO;
import br.com.acheumprofissional.vo.WorkerViewsVO;

/**
 * DAO class for WorkerViewsDAO.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class WorkerViewsDAO extends AbstractDAO {

	
	public WorkerViewsDAO() {
		
	}
	
	/**
	 * Inserts a new worker.
	 * 
	 * @param workerVO the workerVO.
	 * @return the new worker id.
	 * @throws DataAccessException
	 */
	public Long insert (WorkerViewsVO workerViewsVO) throws DataAccessException {
		Long workerViewsId = new Long(0);
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO WORKER_VIEWS (CONTRACTOR_ID, WORKER_ID) ");
		sbSql.append ("VALUES (" + workerViewsVO.getContractorId() + ", ");
		sbSql.append (workerViewsVO.getWorkerId() + ") ");
		logger.debug ("sql: " + sbSql.toString());
		workerViewsId = insertDbWithLongKey (sbSql.toString());
		return workerViewsId;
	}
	
	/**
	 * Finds by workerId.
	 * @param workerId the workerId.
	 * @return
	 * @throws DataAccessException
	 */
	public List<WorkerVO> findByWorkerId (Long workerId) throws DataAccessException {
		List<WorkerVO> listWorker = new ArrayList<WorkerVO>();
		String sql                = null;
		List<Object> rowList      = null;
		sql = "SELECT COUNT(WV.CONTRACTOR_ID), WV.WORKER_ID " + 
			  "FROM WORKER_VIEWS WV " + 
			  "INNER JOIN CONTRACTOR C ON C.CONTRACTOR_ID = WV.CONTRACTOR_ID " + 
			  "WHERE WV.WORKER_ID = " +  workerId + " " +
			  "GROUP BY WV.WORKER_ID " +
			  "ORDER BY WORKER_ID";
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 2);
		logger.debug ("rowList.size(): " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Long contractorViews = (Long) columns.get (0);
				Long workerIdDb      = (Long) columns.get (1);
							
				WorkerVO workerVO = new WorkerVO();
				workerVO.setWorkerViewsCount(contractorViews);
				workerVO.setWorkerId(workerIdDb);
				
				listWorker.add(workerVO);		
			}
		}
		return listWorker;
	}
	
	/**
	 * Finds by contractorId.
	 * 
	 * @param contractorId the contractor id.
	 * @return
	 * @throws DataAccessException
	 */
	public List<WorkerVO> findByContractorId (Long contractorId) throws DataAccessException {
		List<WorkerVO> listWorker = new ArrayList<WorkerVO>();
		String sql                = null;
		List<Object> rowList      = null;
		sql = "SELECT COUNT(WV.CONTRACTOR_ID), WV.WORKER_ID " + 
			  "FROM WORKER_VIEWS WV " + 
			  "INNER JOIN CONTRACTOR C ON C.CONTRACTOR_ID = WV.CONTRACTOR_ID " + 
			  "WHERE C.CONTRACTOR_ID = " + contractorId + " " + 
			  "GROUP BY WV.WORKER_ID " + 
			  "ORDER BY WORKER_ID";
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 2);
		logger.debug ("rowList.size(): " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Long contractorViews = (Long) columns.get (0);
				Long workerIdDb      = (Long) columns.get (1);
							
				WorkerVO workerVO = new WorkerVO();
				workerVO.setWorkerViewsCount(contractorViews);
				workerVO.setWorkerId(workerIdDb);
				
				listWorker.add(workerVO);		
			}
		}
		return listWorker;
	}
	
	
	
}
