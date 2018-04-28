/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.util;

/**
 * Class containing useful constants.
 * 
 * @author Josivan Silva
 *
 */
public class Constants {

	public static final String USER_DIR = "user.dir";
	public static final String DEV_CONFIG_PATH = "dev.config.path";
	/**
	 * Constant that defines the key used by the logged worker.
	 */
	public static final String WORKER_LOGGED_USER = "workerLoggedUser";
	/**
	 * Constant that defines the key used by the logged contractor.
	 */
	public static final String CONTRACTOR_LOGGED_USER = "contractorLoggedUser";
	/**
	 * Page constants.
	 */
	public static final String URL_LOGIN_PAGE = "login.page";
	
	public static final String URI_WELCOME_PAGE = "index.page";
	
	public static final String CONTACT_VIEWS_PAGE = "contactViews.page";
	
	public static final String CONTACTS_VIEWED_PAGE = "contactsViewed.page";
	
	public static final String SEARCH_RESULT_PAGE = "searchResult.page";
	
	/**
	 * Log service constants.
	 */
	public static final int LOG_STATUS_SUCCESS = 1;
	public static final int LOG_STATUS_ERROR = 0;
	
	/**
	 * Constant that defines the default smtp host.
	 */
	public static final String DEFAULT_SMTP_HOST = "smtp.acheumprofissional.com.br";

	/**
	 * Constant that defines the default smtp authentication user.
	 */
	public static final String DEFAULT_SMTP_AUTH_USER = "noreply@acheumprofissional.com.br";

	/**
	 * Constant that defines the default smtp authentication password.
	 */
	public static final String DEFAULT_SMTP_AUTH_PWD = "nrply123";

	/**
	 * Constant that defines the default smtp port.
	 */
	public static final int DEFAULT_SMTP_PORT = 587;

	/**
	 * Constant that defines the TheSyncMe default sender.
	 */
	public static final String DEFAULT_SENDER = "noreply@acheumprofissional.com.br";

	/**
	 * Constant that defines the default header mailer.
	 */
	public static final String DEFAULT_HEADER_MAILER = "X-Mailer";
	
	/**
	 * Constant that defines the default mail receiver.
	 */
	public static final String DEFAULT_RECEIVER = "josivan@josivansilva.com";

	/**
	 * Constant that defines the subject of the Reset your password feature.
	 */
	public static final String CONTACT_SUBJECT = "Ache um profissional | Reset senha";
	
	public static final String PRESTADOR_SERVICO = "P";
	
	public static final String CONTRATANTE = "C";
	
}
