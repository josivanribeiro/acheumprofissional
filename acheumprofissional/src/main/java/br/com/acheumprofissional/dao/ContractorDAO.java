/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

import java.util.List;

import br.com.acheumprofissional.util.SecurityUtils;
import br.com.acheumprofissional.vo.ContractorVO;

/**
 * DAO class for Worker.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ContractorDAO extends AbstractDAO {

	
	public ContractorDAO() {
		
	}	
	
	/**
	 * Inserts a new contractor.
	 * 
	 * @param contractorVO the contractor.
	 * @return the new contractor id.
	 * @throws DataAccessException
	 */
	public Long insert (ContractorVO contractorVO) throws DataAccessException {
		Long contractorId = new Long(0);
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CONTRACTOR (FIRST_NAME, LAST_NAME, USERNAME, PWD, CPF, EMAIL, STATUS) ");
		sbSql.append ("VALUES ('" +  contractorVO.getFirstName() + "', ");
		sbSql.append ("'" + contractorVO.getLastName() + "', ");
		sbSql.append ("'" + contractorVO.getUsername() + "', ");
		String pwdHash = SecurityUtils.getSHA512Password (contractorVO.getPwd(), contractorVO.getUsername());
		sbSql.append ("'" + pwdHash + "', ");
		sbSql.append ("'" + contractorVO.getCpf() + "', ");
		sbSql.append ("'" + contractorVO.getEmail() + "', ");
		sbSql.append ("'" + contractorVO.getStatus() + "')");
		logger.debug ("sql: " + sbSql.toString());
		contractorId = insertDbWithLongKey (sbSql.toString());
		return contractorId;
	}
	
	/**
	 * Updates the contractor.
	 * 
	 * @param contractorVO the contractor.
	 * @return the number of affected rows. 
	 * @throws DataAccessException
	 */
	public int update (ContractorVO contractorVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE CONTRACTOR SET ");
		sbSql.append ("FIRST_NAME=");
		sbSql.append ("'" + contractorVO.getFirstName() + "', ");
		sbSql.append ("LAST_NAME=");
		sbSql.append ("'" + contractorVO.getLastName() + "', ");
		sbSql.append ("CPF=");
		sbSql.append ("'" + contractorVO.getCpf() + "', ");
;		sbSql.append ("EMAIL=");
		sbSql.append ("'" + contractorVO.getEmail() + "' ");
		whereClause = "WHERE CONTRACTOR_ID = " + contractorVO.getContractorId();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the contractor pwd.
	 * 
	 * @param contractorVO the contractorVO.
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePwd (ContractorVO contractorVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE CONTRACTOR SET ");
		String pwdHash = SecurityUtils.getSHA512Password (contractorVO.getPwd(), contractorVO.getUsername());
		sbSql.append ("PWD=");
		sbSql.append ("'" + pwdHash + "' ");
		whereClause = "WHERE CONTRACTOR_ID = " + contractorVO.getContractorId();
		sbSql.append (whereClause);
		logger.debug ("\n\nsql: " + sbSql.toString());
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
	public ContractorVO findByUsername (String username) throws DataAccessException {
		ContractorVO foundContractor = null;
		String sql             = null;
		List<Object> rowList   = null;
		sql = "SELECT CONTRACTOR_ID, FIRST_NAME, LAST_NAME, USERNAME, CPF, EMAIL, STATUS FROM CONTRACTOR WHERE USERNAME = '" + username + "'";
		rowList = selectDb (sql, 7);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Long contractorId        = (Long) columns.get (0);
				String firstName     = (String) columns.get (1);
				String lastName      = (String) columns.get (2);
				String usernameDb    = (String) columns.get (3);
				String cpf           = (String) columns.get (4);
				String email         = (String) columns.get (5);
				String status        = (String) columns.get (6);
				
				foundContractor = new ContractorVO();
				foundContractor.setContractorId(contractorId);
				foundContractor.setFirstName(firstName);
				foundContractor.setLastName(lastName);
				foundContractor.setUsername(usernameDb);
				foundContractor.setCpf(cpf);
				foundContractor.setEmail(email);
				foundContractor.setStatus(status);
			}
		}
		return foundContractor;
	}
	
	
	/**
	 * Finds a contractor by id.
	 * 
	 * @param id the id.
	 * @return
	 * @throws DataAccessException
	 */
	public ContractorVO findById (Long id) throws DataAccessException {
		ContractorVO foundContractor = null;
		String sql                   = null;
		List<Object> rowList         = null;
		sql = "SELECT CONTRACTOR_ID, FIRST_NAME, LAST_NAME, USERNAME, CPF, EMAIL, STATUS FROM CONTRACTOR WHERE CONTRACTOR_ID = " + id;
		logger.debug("\n\n" + sql + "\n\n");
		rowList = selectDb (sql, 7);
		logger.debug("rowList.size() " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Long contractorId    = (Long) columns.get (0);
				String firstName     = (String) columns.get (1);
				String lastName      = (String) columns.get (2);
				String usernameDb    = (String) columns.get (3);
				String cpf           = (String) columns.get (4);
				String email         = (String) columns.get (5);
				String status        = (String) columns.get (6);
				
				foundContractor = new ContractorVO();
				foundContractor.setContractorId(contractorId);
				foundContractor.setFirstName(firstName);
				foundContractor.setLastName(lastName);
				foundContractor.setUsername(usernameDb);
				foundContractor.setCpf(cpf);
				foundContractor.setEmail(email);
				foundContractor.setStatus(status);							
			}
		}
		return foundContractor;
	}
	
	/**
	 * Finds email by username.
	 * 
	 * @param username the username.
	 * @return
	 * @throws DataAccessException
	 */
	public ContractorVO findEmailByUsername (String username) throws DataAccessException {
		ContractorVO foundContractor = null;
		String sql                   = null;
		List<Object> rowList         = null;
		sql = "SELECT EMAIL FROM CONTRACTOR WHERE USERNAME = '" + username + "'";
		logger.debug("\n\n" + sql + "\n\n");
		rowList = selectDb (sql, 1);
		logger.debug("rowList.size() " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				
				String email = (String) columns.get (0);
								
				foundContractor = new ContractorVO();
				foundContractor.setEmail(email);											
			}
		}
		return foundContractor;
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
		sql = "SELECT COUNT(*) FROM CONTRACTOR WHERE USERNAME = '" + username + "'";
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
		sql = "SELECT COUNT(*) FROM CONTRACTOR WHERE CPF = '" + cpf + "'";
		logger.debug ("sql: " + sql);
		int count = selectRowCount (sql);
		result = count > 1 ? true : false;
		return result;
	}	
	
}
