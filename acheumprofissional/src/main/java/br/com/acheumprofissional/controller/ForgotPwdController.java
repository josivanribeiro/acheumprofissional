/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.ContractorService;
import br.com.acheumprofissional.services.EmailService;
import br.com.acheumprofissional.services.ForgotPwdService;
import br.com.acheumprofissional.services.WorkerService;
import br.com.acheumprofissional.util.Constants;
import br.com.acheumprofissional.util.SecurityUtils;
import br.com.acheumprofissional.util.Utils;
import br.com.acheumprofissional.vo.ContractorVO;
import br.com.acheumprofissional.vo.ForgotPwdVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Forgot Pwd Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@SessionScoped
public class ForgotPwdController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (ForgotPwdController.class.getName());
	private ForgotPwdService forgotPwdService = new ForgotPwdService();
	private WorkerService workerService = new WorkerService();
	private ContractorService contractorService = new ContractorService();
	private EmailService emailService = EmailService.INSTANCE;
	
	private String selectedTypeForm;
	private String usernameForm;
		
	private ForgotPwdVO forgotPwdVOForm;
	
	private String firstResetPwdForm;
	private String secondResetPwdForm;
	
		
	public String getSelectedTypeForm() {
		return selectedTypeForm;
	}

	public void setSelectedTypeForm(String selectedTypeForm) {
		this.selectedTypeForm = selectedTypeForm;
	}

	public String getUsernameForm() {
		return usernameForm;
	}

	public void setUsernameForm(String usernameForm) {
		this.usernameForm = usernameForm;
	}

	public ForgotPwdVO getForgotPwdVOForm() {
		return forgotPwdVOForm;
	}

	public void setForgotPwdVOForm(ForgotPwdVO forgotPwdVOForm) {
		this.forgotPwdVOForm = forgotPwdVOForm;
	}
	
	public String getFirstResetPwdForm() {
		return firstResetPwdForm;
	}

	public void setFirstResetPwdForm(String firstResetPwdForm) {
		this.firstResetPwdForm = firstResetPwdForm;
	}

	public String getSecondResetPwdForm() {
		return secondResetPwdForm;
	}

	public void setSecondResetPwdForm(String secondResetPwdForm) {
		this.secondResetPwdForm = secondResetPwdForm;
	}

	@PostConstruct
    public void init() {
		String token = getTokenParam();
		if (token != null && !"".equals(token)) {
			forgotPwdVOForm = findByToken(token);
		}		
    }
	
	/**
	 * Inserts and sends email.
	 * 
	 * @param actionEvent
	 */
	public void insertAndSendEmail (ActionEvent actionEvent) {
		int affectedRows = 0;
		String email = null;
		logger.debug("\n\n\nStarting insertAndSendEmail\n\n\n");
		if (isValidForm ()) {
			ForgotPwdVO forgotPwdVO = new ForgotPwdVO ();
			forgotPwdVO.setType(selectedTypeForm);
			forgotPwdVO.setUsername(usernameForm);
			
			if (selectedTypeForm.equals(Constants.PRESTADOR_SERVICO)) {
				WorkerVO workerVO = null;
				try {
					workerVO = workerService.findEmailByUsername(usernameForm);
				} catch (BusinessException e) {
					String error = "An error occurred while finding the worker email by username. " + e.getMessage();
					logger.error (error);
				}
				if (workerVO == null || "".equals(workerVO.getEmail().trim())) {
					super.addWarnMessage ("formForgotPwd:messagesForgotPwd", " É necessário cadastrar um email.");
				} else {
					email = workerVO.getEmail();
					logger.debug("workerVO.getEmail(): " + workerVO.getEmail());
				}
			} else if (selectedTypeForm.equals(Constants.CONTRATANTE)) {
				ContractorVO contractorVO = null;
				try {
					contractorVO = contractorService.findEmailByUsername(usernameForm);
				} catch (BusinessException e) {
					String error = "An error occurred while finding the contractor email by username. " + e.getMessage();
					logger.error (error);
				}
				if (contractorVO == null || "".equals(contractorVO.getEmail().trim())) {
					super.addWarnMessage ("formForgotPwd:messagesForgotPwd", " É necessário cadastrar um email.");
				} else {
					email = contractorVO.getEmail();
					logger.debug("contractorVO.getEmail(): " + contractorVO.getEmail());
				}				
			}
			
			
			if (email != null && !"".equals(email)) {
				
				forgotPwdVO.setEmail(email);			
				
				String token = SecurityUtils.getSHA512Password(email, new Timestamp(System.currentTimeMillis()).toString());
				forgotPwdVO.setToken(token);
				try {
					forgotPwdService.insert(forgotPwdVO);
					logger.debug("\n\n\naffectedRows: " + affectedRows);
					if (affectedRows > 0) {
						logger.info ("The forgotPwd user [" + forgotPwdVO.getEmail() + "] has been successfully inserted.");
					}			
				} catch (BusinessException e) {
					String error = "An error occurred while saving or updating the worker. " + e.getMessage();
					logger.error (error);
				}
				boolean result = false;
				try {
					result = emailService.sendResetEmail(email, token);
					if (result) {
						super.addInfoMessage ("formForgotPwd:messagesForgotPwd", " Email enviado com sucesso.");
						logger.info ("The forgotPwd user [" + forgotPwdVO.getEmail() + "] email has been successfully sent.");
					}
				} catch (Exception e) {
					String error = "An error occurred while sending reset email. " + e.getMessage();
					logger.error (error);
				}
				this.resetForm();
				
			}			
			
		}
	}
	
	/**
	 * Finds the job category name from a category.
	 */
	public ForgotPwdVO findByToken (String token) {
		ForgotPwdVO forgotPwdVO = null;
		try {
			forgotPwdVO = forgotPwdService.findByToken(token);
			logger.debug("forgotPwdVO.getToken() - " + forgotPwdVO.getToken());
		} catch (BusinessException e) {
			String error = "An error occurred while find the forgot pwd by token. " + e.getMessage();
			logger.error (error);
		}
		return forgotPwdVO;
	}
	
	private boolean isValidForm () {		
		if (!Utils.isNonEmpty (this.selectedTypeForm)
				|| !Utils.isNonEmpty (this.usernameForm)) {
			super.addWarnMessage ("formForgotPwd:messagesForgotPwd", " Preencha todos os campos corretamente.");
			return false;
		}
		return true;
    }
	
	private void resetForm () {
		this.selectedTypeForm = null;
		this.usernameForm = null;					
	}
	
	/**
	 *  Gets the token param from query string.
	 *  
	 * @return the workerId
	 */
	private String getTokenParam () {
		String token = null;
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String paramToken = params.get("t");
		if (paramToken != null && !"".equals(paramToken)) {
			token = paramToken;
			logger.debug("token: " + token);
		}		
		return token;
	}
			
}
;