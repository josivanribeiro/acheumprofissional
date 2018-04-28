/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.acheumprofissional.util.SecurityUtils;

/**
 * DAO class for Login.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class LoginDAO extends AbstractDAO {

	
	public LoginDAO() {
		
	}
	
	/**
	 * Performs the worker login.
	 * 
	 * @param username the username.
	 * @param pwd the pwd.
	 * @return the operation result.
	 * @throws DataAccessException
	 */
	public boolean doLoginWorker (String username, String pwd) throws DataAccessException {
		logger.debug("Starting doLogin.");
		boolean isValid         = false;
		Connection conn         = null;
		PreparedStatement pstmt = null;
		ResultSet rs            = null;
		String sql              = null;
		int count               = 0;
		try {
			conn = this.getConnection();
			String pwdHash = SecurityUtils.getSHA512Password (pwd, username);
			sql = "SELECT COUNT(*) FROM WORKER WHERE USERNAME = ? AND PWD = ? AND STATUS = 1";			
			pstmt = conn.prepareStatement (sql);			
			pstmt.setString (1, username);
			pstmt.setString (2, pwdHash);						
			rs = pstmt.executeQuery();
		    while (rs.next()) {		    	
		    	count = rs.getInt (1);
		    }		    
		    logger.debug ("count: " + count);
		} catch (SQLException e) {
		    String error = "An error occurred while executing the login sql statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closeResultSet (rs);
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		isValid = count > 0 ? true : false;
		logger.debug("Finishing doLogin.");
		return isValid;
	}
	
	/**
	 * Performs the contractor login.
	 * 
	 * @param username the username.
	 * @param pwd the pwd.
	 * @return
	 * @throws DataAccessException
	 */
	public boolean doLoginContractor (String username, String pwd) throws DataAccessException {
		logger.debug("Starting doLogin.");
		boolean isValid         = false;
		Connection conn         = null;
		PreparedStatement pstmt = null;
		ResultSet rs            = null;
		String sql              = null;
		int count               = 0;
		try {
			conn = this.getConnection();
			String pwdHash = SecurityUtils.getSHA512Password (pwd, username);
			sql = "SELECT COUNT(*) FROM CONTRACTOR WHERE USERNAME = ? AND PWD = ? AND STATUS = 1";			
			pstmt = conn.prepareStatement (sql);			
			pstmt.setString (1, username);
			pstmt.setString (2, pwdHash);						
			rs = pstmt.executeQuery();
		    while (rs.next()) {		    	
		    	count = rs.getInt (1);
		    }		    
		    logger.debug ("count: " + count);
		} catch (SQLException e) {
		    String error = "An error occurred while executing the login sql statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closeResultSet (rs);
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		isValid = count > 0 ? true : false;
		logger.debug("Finishing doLogin.");
		return isValid;
	}
	
}
