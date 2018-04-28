/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.acheumprofissional.vo.ForgotPwdVO;

/**
 * DAO class for Forgot Pwd.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ForgotPwdDAO extends AbstractDAO {

	
	public ForgotPwdDAO() {
		
	}
	
	/**
	 * Inserts a new forgot pwd.
	 * 
	 * @param forgotPwdVO the forgotPwdVO.
	 * @return the affected rows.
	 * @throws DataAccessException
	 */
	public int insert (ForgotPwdVO forgotPwdVO) throws DataAccessException {
		Connection conn         = null;
		PreparedStatement pstmt = null;
		String sql              = null;
		int affectedRows = 0;
		try {
			conn = this.getConnection();
			sql = "INSERT INTO FORGOT_PWD (TYPE, USERNAME, EMAIL, TOKEN) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement (sql);
			pstmt.setString (1, forgotPwdVO.getType());
			pstmt.setString (2, forgotPwdVO.getUsername());
			pstmt.setString (3, forgotPwdVO.getEmail());
			pstmt.setString (4, forgotPwdVO.getToken());
			
			affectedRows = pstmt.executeUpdate();			
			logger.debug ("affectedRows: " + affectedRows);
		} catch (SQLException e) {
		    String error = "An error occurred while executing the insert for forgot pwd sql statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		return affectedRows;
	}
	
	/**
	 * Gets a forgot pwd by token.
	 * 
	 * @param token the token.
	 * @return
	 * @throws DataAccessException
	 */
	public ForgotPwdVO findByToken (String token) throws DataAccessException {
		ForgotPwdVO foundForgotPwd = null;
		String sql                 = null;
		List<Object> rowList       = null;
		sql = "SELECT TYPE, USERNAME, EMAIL, TOKEN FROM FORGOT_PWD WHERE TOKEN = '" + token + "'";
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 4);
		logger.debug ("rowList.size(): " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				String type          = (String) columns.get (0);
				String usernameDb    = (String) columns.get (1);
				String email         = (String) columns.get (2);
				String tokenDb       = (String) columns.get (3);				
				
				foundForgotPwd = new ForgotPwdVO();
				foundForgotPwd.setType(type);
				foundForgotPwd.setUsername(usernameDb);
				foundForgotPwd.setEmail(email);
				foundForgotPwd.setToken(tokenDb);							
			}
		}
		return foundForgotPwd;
	}
	
	/**
	 * Checks if exists a given token.
	 * 
	 * @param token the token.
	 * @return the result.
	 * @throws DataAccessException
	 */
	public boolean existsToken (String token) throws DataAccessException {
		boolean result = false;
		String sql = null;
		sql = "SELECT COUNT(*) FROM FORGOT_PWD WHERE TOKEN = '" + token + "'";
		logger.debug ("sql: " + sql);
		int count = selectRowCount (sql);
		result = count > 1 ? true : false;
		return result;
	}	
	
}
