/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.acheumprofissional.util.SecurityUtils;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * DAO class for Worker.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class WorkerDAO extends AbstractDAO {

	
	public WorkerDAO() {
		
	}
	
	/**
	 * Inserts a new worker.
	 * 
	 * @param workerVO the workerVO.
	 * @return the affected rows.
	 * @throws DataAccessException
	 */
	public int insert (WorkerVO workerVO) throws DataAccessException {
		Connection conn         = null;
		PreparedStatement pstmt = null;
		String sql              = null;
		int affectedRows = 0;
		try {
			conn = this.getConnection();
			sql = "INSERT INTO WORKER (FIRST_NAME, LAST_NAME, JOB_CATEGORY_ID, USERNAME, PWD, CPF, PHONE, EMAIL, BIRTH, IMAGE_THUMB, DESCRIPTION, STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement (sql);
			pstmt.setString (1, workerVO.getFirstName());
			pstmt.setString (2, workerVO.getLastName());
			pstmt.setLong (3, workerVO.getJobCategoryId());
			pstmt.setString (4, workerVO.getUsername());
			String pwdHash = SecurityUtils.getSHA512Password (workerVO.getPwd(), workerVO.getUsername());
			pstmt.setString (5, pwdHash);
			pstmt.setString (6, workerVO.getCpf());
			pstmt.setString (7, workerVO.getPhone());
			pstmt.setString (8, workerVO.getEmail());
			pstmt.setString (9, workerVO.getBirth());
			pstmt.setBinaryStream (10, workerVO.getImageThumb());
			pstmt.setString (11, workerVO.getDescription());
			pstmt.setString (12, workerVO.getStatus());
			affectedRows = pstmt.executeUpdate();			
			logger.debug ("affectedRows: " + affectedRows);
		} catch (SQLException e) {
		    String error = "An error occurred while executing the insert worker sql statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		return affectedRows;
	}
	
	/**
	 * Updates the worker.
	 * 
	 * @param workerVO the worker.
	 * @return the number of affected rows. 
	 * @throws DataAccessException
	 */
	public int update (WorkerVO workerVO) throws DataAccessException {
		Connection conn         = null;
		PreparedStatement pstmt = null;
		String sql              = null;
		int affectedRows = 0;
		try {
			conn = this.getConnection();
			sql = "UPDATE WORKER SET FIRST_NAME = ?, LAST_NAME = ?, JOB_CATEGORY_ID = ?, USERNAME = ?, CPF = ?, PHONE = ?,  EMAIL = ?, BIRTH = ?, IMAGE_THUMB = ?, DESCRIPTION = ? WHERE WORKER_ID = " + workerVO.getWorkerId();
			pstmt = conn.prepareStatement (sql);
			pstmt.setString (1, workerVO.getFirstName());
			pstmt.setString (2, workerVO.getLastName());
			pstmt.setLong (3, workerVO.getJobCategoryId());
			pstmt.setString (4, workerVO.getUsername());
			pstmt.setString (5, workerVO.getCpf());
			pstmt.setString (6, workerVO.getPhone());
			pstmt.setString (7, workerVO.getEmail());
			pstmt.setString (8, workerVO.getBirth());
			pstmt.setBinaryStream (9, workerVO.getImageThumb());
			pstmt.setString (10, workerVO.getDescription());
			affectedRows = pstmt.executeUpdate();			
			logger.debug ("affectedRows: " + affectedRows);
		} catch (SQLException e) {
		    String error = "An error occurred while executing the update worker sql statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		return affectedRows;
	}

	
	/**
	 * Updates the worker pwd.
	 * 
	 * @param workerVO the worker.
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePwd (WorkerVO workerVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE WORKER SET ");
		String pwdHash = SecurityUtils.getSHA512Password (workerVO.getPwd(), workerVO.getUsername());
		sbSql.append ("PWD=");
		sbSql.append ("'" + pwdHash + "' ");
		whereClause = "WHERE WORKER_ID = " + workerVO.getWorkerId();
		sbSql.append (whereClause);
		logger.debug ("\n\nsql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the worker recommendations.
	 * 
	 * @param workerVO
	 * @return
	 * @throws DataAccessException
	 */
	public int updateRecomendation (WorkerVO workerVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE WORKER SET ");
		sbSql.append ("RECOMMENDATIONS=");
		sbSql.append (workerVO.getRecommendations() + " ");
		whereClause = "WHERE WORKER_ID = " + workerVO.getWorkerId();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Finds a worker by its username.
	 * 
	 * @param username the username.
	 * @return a worker.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public WorkerVO findByUsername (String username) throws DataAccessException {
		WorkerVO foundWorker = null;
		String sql             = null;
		List<Object> rowList   = null;
		sql = "SELECT WORKER_ID, USERNAME, FIRST_NAME, LAST_NAME, CPF, PHONE, EMAIL, BIRTH, DESCRIPTION, STATUS FROM WORKER WHERE USERNAME = '" + username + "'";
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 10);
		logger.debug ("rowList.size(): " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Long workerId        = (Long) columns.get (0);
				String usernameDb    = (String) columns.get (1);
				String firstName     = (String) columns.get (2);
				String lastName      = (String) columns.get (3);
				String cpf           = (String) columns.get (4);
				String phone         = (String) columns.get (5);
				String email         = (String) columns.get (6);
				String birth         = (String) columns.get (7);
				String description   = (String) columns.get (8);
				String status        = (String) columns.get (9);
				
				foundWorker = new WorkerVO();
				foundWorker.setWorkerId(workerId);
				foundWorker.setUsername(usernameDb);
				foundWorker.setFirstName(firstName);
				foundWorker.setLastName(lastName);
				foundWorker.setCpf(cpf);
				foundWorker.setPhone(phone);
				foundWorker.setEmail(email);
				foundWorker.setBirth(birth);
				foundWorker.setDescription(description);
				foundWorker.setStatus(status);				
			}
		}
		return foundWorker;
	}
	
	/**
	 * Finds an email by username.
	 * 
	 * @param username the username.
	 * @return
	 * @throws DataAccessException
	 */
	public WorkerVO findEmailByUsername (String username) throws DataAccessException {
		WorkerVO foundWorker = null;
		String sql             = null;
		List<Object> rowList   = null;
		sql = "SELECT EMAIL FROM WORKER WHERE USERNAME = '" + username + "'";
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 1);
		logger.debug ("rowList.size(): " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				String email = (String) columns.get (0);
								
				foundWorker = new WorkerVO();
				foundWorker.setEmail(email);
				
			}
		}
		return foundWorker;
	}
	
	/**
	 * Finds a worker by id.
	 * 
	 * @param id the id.
	 * @return the found worker.
	 * @throws DataAccessException
	 */
	public WorkerVO findById (Long id) throws DataAccessException {
		WorkerVO foundWorker    = null;
		Connection conn         = null;
		PreparedStatement pstmt = null;
		ResultSet rs            = null;
		String sql              = null;
		try {
			conn = super.getConnection();
			sql = "SELECT WORKER_ID, USERNAME, FIRST_NAME, LAST_NAME, JOB_CATEGORY_ID, CPF, PHONE, EMAIL, BIRTH, DESCRIPTION, IMAGE_THUMB, RECOMMENDATIONS, STATUS FROM WORKER WHERE WORKER_ID = " + id;
			pstmt = conn.prepareStatement (sql);
			rs = pstmt.executeQuery();
		    while (rs.next()) {		    	
		    	Long workerId       = rs.getLong (1);
				String username     = rs.getString (2);
				String  firstName   = rs.getString (3);
				String  lastName    = rs.getString (4);
				Long jobCategoryId  = rs.getLong (5);
				String cpf          = rs.getString (6);
				String phone        = rs.getString (7);
				String email        = rs.getString (8);
				String birth        = rs.getString (9);
				String description  = rs.getString (10);
				InputStream file    = rs.getBinaryStream (11);
				int recommendations = rs.getInt(12);
				String status       = rs.getString (13);
		        
				foundWorker = new WorkerVO();
				foundWorker.setWorkerId(workerId);
				foundWorker.setUsername(username);
				foundWorker.setFirstName(firstName);
				foundWorker.setLastName(lastName);
				foundWorker.setJobCategoryId(jobCategoryId);
				foundWorker.setCpf(cpf);
				foundWorker.setPhone(phone);
				foundWorker.setEmail(email);
				foundWorker.setBirth(birth);
				foundWorker.setDescription(description);
				foundWorker.setImageThumb(file);
				foundWorker.setRecommendations(recommendations);
				foundWorker.setStatus(status);				
		    }
		    
		} catch (SQLException e) {
		    String error = "An error occurred while executing the sql select statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closeResultSet (rs);
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		return foundWorker;
	}
	
	
	/**
	 * Finds workers by job category id.
	 *  
	 * @param jobCategoryId the job category id.
	 * @return
	 * @throws DataAccessException
	 */
	public List<WorkerVO> findByJobCategoryId (Long jobCategoryId) throws DataAccessException {
		List<WorkerVO> workerList = new ArrayList<WorkerVO>();
		String sql                = null;
		List<Object> rowList      = null;
		sql = "SELECT WORKER_ID, USERNAME, FIRST_NAME, LAST_NAME, JOB_CATEGORY_ID, CPF, BIRTH, EMAIL, DESCRIPTION, RECOMMENDATIONS, STATUS FROM WORKER WHERE JOB_CATEGORY_ID = " + jobCategoryId;
		logger.debug(sql);
		rowList = selectDb (sql, 11);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns  = (List<Object>)columnList;
				Long workerId         = (Long) columns.get (0);
				String usernameDb     = (String) columns.get (1);
				String firstName      = (String) columns.get (2);
				String lastName       = (String) columns.get (3);
				Long newJobCategoryId = (Long) columns.get (4);
				String cpf            = (String) columns.get (5);
				String birth          = (String) columns.get (6);
				String email          = (String) columns.get (7);
				String description    = (String) columns.get (8);
				Integer recommendations = (Integer) columns.get (9);
				String status         = (String) columns.get (10);
				
				WorkerVO foundWorker = new WorkerVO();
				foundWorker = new WorkerVO();
				foundWorker.setWorkerId(workerId);
				foundWorker.setUsername(usernameDb);
				foundWorker.setFirstName(firstName);
				foundWorker.setLastName(lastName);
				foundWorker.setJobCategoryId(newJobCategoryId);
				foundWorker.setCpf(cpf);
				foundWorker.setBirth(birth);
				foundWorker.setEmail(email);
				foundWorker.setDescription(description);
				if (recommendations != null) {
					foundWorker.setRecommendations(recommendations);
				}				
				foundWorker.setStatus(status);
				
				workerList.add(foundWorker);
				
			}
		}
		return workerList;
	}
	
	/**
	 * Checks if exists a given username.
	 * 
	 * @param username the username.
	 * @return the result.
	 * @throws DataAccessException
	 */
	public boolean existsUsername (String username) throws DataAccessException {
		boolean result = false;
		String sql = null;
		sql = "SELECT COUNT(*) FROM WORKER WHERE USERNAME = '" + username + "'";
		logger.debug ("sql: " + sql);
		int count = selectRowCount (sql);
		result = count > 1 ? true : false;
		return result;
	}
	
	/**
	 * Checks if exists a given Cpf. 
	 * @param cpf the cpf
	 * @return the result
	 * @throws DataAccessException
	 */
	public boolean existsCpf (String cpf) throws DataAccessException {
		boolean result = false;
		String sql = null;
		sql = "SELECT COUNT(*) FROM WORKER WHERE CPF = '" + cpf + "'";
		logger.debug ("sql: " + sql);
		int count = selectRowCount (sql);
		result = count > 1 ? true : false;
		return result;
	}
	
}
