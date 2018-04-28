/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

/**
 * Root of the hierarchy of data access exceptions.
 * 
 * @author Josivan Silva
 *
 */
public class DataAccessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for DataAccessException.
	 * 
	 * @param message the detail message.
	 */
	public DataAccessException (String message) {
		super(message);
	}

	/**
	 * Constructor for DataAccessException.
	 * 
	 * @param message the detail message.
	 * @param cause the root cause.
	 */
	public DataAccessException (String message, Throwable cause) {
		super (message, cause);
	}
}
