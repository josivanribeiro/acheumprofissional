/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.ContractorService;
import br.com.acheumprofissional.services.ForgotPwdService;
import br.com.acheumprofissional.services.WorkerService;
import br.com.acheumprofissional.util.Constants;
import br.com.acheumprofissional.util.Utils;
import br.com.acheumprofissional.vo.ContractorVO;
import br.com.acheumprofissional.vo.ForgotPwdVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Reset Pwd Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@SessionScoped
public class ResetPwdController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (ResetPwdController.class.getName());
	private ForgotPwdService forgotPwdService = new ForgotPwdService();
	private WorkerService workerService = new WorkerService();
	private ContractorService contractorService = new ContractorService ();
		
	private ForgotPwdVO forgotPwdVOForm;
	
	private String firstResetPwdForm;
	private String secondResetPwdForm;
	
		
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
			if (findByToken(token) != null) {
				forgotPwdVOForm = findByToken(token);
			}			
		}		
    }
	
	
	/**
	 * Finds and reset the pwd.
	 * 
	 * @param actionEvent
	 */
	public void findAndResetPwd (ActionEvent actionEvent) {
		WorkerVO workerVO = null;
		ContractorVO contractorVO = null;
		
		if (isValidForm ()) {
		
			if (forgotPwdVOForm.getType().equals(Constants.PRESTADOR_SERVICO)) {
				//getting the worker
				try {
					workerVO = workerService.findByUsername(forgotPwdVOForm.getUsername());
				} catch (BusinessException e) {
					String error = "An error occurred while getting the worker. " + e.getMessage();
					logger.error (error);
				}			
				//updating the worker pwd
				try {
					workerVO.setPwd(firstResetPwdForm);
					int affectedRows = workerService.updatePwd(workerVO);
					
					if (affectedRows > 1) {
						super.addInfoMessage ("formResetPwd:messagesResetPwd", " Senha atualizada com sucesso.");
						logger.info ("The forgotPwd user [" + forgotPwdVOForm.getUsername() + "] pwd has been successfully changed.");
					}					
					
				} catch (BusinessException e) {
					String error = "An error occurred while updating the worker pwd. " + e.getMessage();
					logger.error (error);
				}		
			} else if (forgotPwdVOForm.getType().equals(Constants.CONTRATANTE)) {
				//getting the contractor
				try {
					contractorVO = contractorService.findByUsername(forgotPwdVOForm.getUsername());
				} catch (BusinessException e) {
					String error = "An error occurred while getting the contractor. " + e.getMessage();
					logger.error (error);
				}	
				//updating the contractor pwd
				try {
					contractorVO.setPwd(firstResetPwdForm);
					int affectedRows = contractorService.updatePwd(contractorVO);
					
					if (affectedRows > 1) {
						super.addInfoMessage ("formResetPwd:messagesResetPwd", " Senha atualizada com sucesso.");
						logger.info ("The forgotPwd user [" + forgotPwdVOForm.getUsername() + "] pwd has been successfully changed.");
					}
					
				} catch (BusinessException e) {
					String error = "An error occurred while updating the contractor pwd. " + e.getMessage();
					logger.error (error);
				}			
			}
			resetForm();
		}
		
	}
		
	/**
	 * Finds the job category name from a category.
	 */
	public ForgotPwdVO findByToken (String token) {
		ForgotPwdVO forgotPwdVO = null;
		try {
			forgotPwdVO = forgotPwdService.findByToken(token);
			if (forgotPwdVO == null) {
				super.addWarnMessage ("formResetPwd:messagesResetPwd", " Token inválido.");
			} else {
				logger.debug("forgotPwdVO.getToken() - " + forgotPwdVO.getToken());
			}			
		} catch (BusinessException e) {
			String error = "An error occurred while find the forgot pwd by token. " + e.getMessage();
			logger.error (error);
		}
		return forgotPwdVO;
	}
	
	private boolean isValidForm () {		
		if (!Utils.isNonEmpty (this.firstResetPwdForm)
				|| !Utils.isNonEmpty (this.secondResetPwdForm)) {
			super.addWarnMessage ("formResetPwd:messagesResetPwd", " Preencha todos os campos corretamente.");
			return false;
		} else if (!this.firstResetPwdForm.equals(secondResetPwdForm)) {
			super.addWarnMessage ("formResetPwd:messagesResetPwd", " As senhas devem ser iguais.");
			return false;
		}  
		return true;
    }
	
	private void resetForm () {
		this.firstResetPwdForm = null;
		this.secondResetPwdForm = null;				
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