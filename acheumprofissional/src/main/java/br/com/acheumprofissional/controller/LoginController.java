/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.ContractorService;
import br.com.acheumprofissional.services.LoginService;
import br.com.acheumprofissional.services.WorkerService;
import br.com.acheumprofissional.services.WorkerViewsService;
import br.com.acheumprofissional.util.Constants;
import br.com.acheumprofissional.util.SecurityUtils;
import br.com.acheumprofissional.util.Utils;
import br.com.acheumprofissional.vo.ContractorVO;
import br.com.acheumprofissional.vo.WorkerVO;
import br.com.acheumprofissional.vo.WorkerViewsVO;


/**
 * Login Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@SessionScoped
public class LoginController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (LoginController.class.getName());
	private LoginService loginService = new LoginService ();
	private WorkerService workerService = new WorkerService ();
	private ContractorService contractorService = new ContractorService ();
	private WorkerViewsService workerViewsService = new WorkerViewsService();
	private String usernameForm;
	private String pwdForm;
	private boolean isLogged = false;
	private Long workerIdForm;
		
		
	public String getUsernameForm() {
		return usernameForm;
	}
	public void setUsernameForm(String usernameForm) {
		this.usernameForm = usernameForm;
	}
	public String getPwdForm() {
		return pwdForm;
	}
	public void setPwdForm(String pwdForm) {
		this.pwdForm = pwdForm;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	public boolean isLogged() {
		return isLogged;
	}
	public Long getWorkerIdForm() {
		return workerIdForm;
	}
	public void setWorkerIdForm(Long workerIdForm) {
		this.workerIdForm = workerIdForm;
	}
	@PostConstruct
    public void init() {
		
    }
	
	
	public void doLogin (ActionEvent actionEvent) {
		String redirectToPage = null;
		RequestContext context = RequestContext.getCurrentInstance();
		if (isValidLoginForm()) {
						 
			try {
				if (this.loginService.doLoginWorker(usernameForm, pwdForm)) {
					
					WorkerVO workerVO = workerService.findByUsername(usernameForm);
					
					FacesContext
					.getCurrentInstance()
						.getExternalContext()
							.getSessionMap().put (Constants.WORKER_LOGGED_USER, workerVO);
				
					redirectToPage = Constants.CONTACT_VIEWS_PAGE;
					isLogged = true;
					
				} else if (this.loginService.doLoginContractor(usernameForm, pwdForm)){
					
					ContractorVO contractorVO = contractorService.findByUsername(usernameForm);
					FacesContext
					.getCurrentInstance()
						.getExternalContext()
							.getSessionMap().put (Constants.CONTRACTOR_LOGGED_USER, contractorVO);
				
					redirectToPage = Constants.CONTACTS_VIEWED_PAGE;
					isLogged = true;
				} else {					
					super.addErrorMessage ("formLogin:messagesLogin", " Nome de usuário ou senha inválidos.");
				}		
				resetForm();
			} catch (BusinessException e) {
				String error = "An error occurred while performing the user login. " + e.getMessage();
				logger.error (error);
			}		
			
		}
		context.addCallbackParam ("isLogged", isLogged);
		context.addCallbackParam ("redirectToPage", redirectToPage);
	}
	
	public void doLoginSearchResult () {
		String redirectToPage = null;
		RequestContext context = RequestContext.getCurrentInstance();
		if (isValidLoginForm()) {
						 
			try {
				if (this.loginService.doLoginContractor(usernameForm, pwdForm)) {
					
					ContractorVO contractorVO = contractorService.findByUsername(usernameForm);
					
					FacesContext
					.getCurrentInstance()
						.getExternalContext()
							.getSessionMap().put (Constants.CONTRACTOR_LOGGED_USER, contractorVO);
				
					//register the worker view click of phone
					insertWorkerViews(contractorVO);
					
					redirectToPage = Constants.SEARCH_RESULT_PAGE + "?workerId=" + workerIdForm;
					isLogged = true;
					
				} else {					
					super.addErrorMessage ("formLogin:messagesLogin", " Nome de usuário ou senha inválidos.");
				}		
				resetForm();
			} catch (BusinessException e) {
				String error = "An error occurred while performing the user login on search result. " + e.getMessage();
				logger.error (error);
			}		
			
		}
		context.addCallbackParam ("isLogged", isLogged);
		context.addCallbackParam ("redirectToPage", redirectToPage);
	}
	
	public void setWorkerId(Long workerId) {
		this.workerIdForm = workerId;
		logger.info("workerIdForm: " + workerIdForm);
	}
	
	
	/**
	 * Performs the student logout.
	 */
	public String logout() {
		logger.debug ("Starting logout.");
		String toPage = "index?faces-redirect=true";
		this.isLogged = false;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		SecurityUtils.logout (request);
		logger.debug ("Finishing logout.");
		return toPage;
	}
	
	/**
	 * Validates the user login form.
	 * 
	 */
	private boolean isValidLoginForm () {
		if (!Utils.isNonEmpty (this.usernameForm)
				|| !Utils.isNonEmpty (this.pwdForm)) {
			super.addWarnMessage ("formLogin:messagesLogin", " Preencha todos os campos.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the login form.
	 */
	private void resetForm () {
		this.usernameForm = "";
		this.pwdForm   = "";
	}
	
	/**
	 * Inserts a worker view.
	 * 
	 * @param contractorVO the contractor
	 */
	private void insertWorkerViews (ContractorVO contractorVO) {
		WorkerViewsVO workerViewsVO = new WorkerViewsVO();
		workerViewsVO.setContractorId(contractorVO.getContractorId());
		workerViewsVO.setWorkerId(workerIdForm);
		logger.debug ("contractorId: " + contractorVO.getContractorId());
		logger.debug ("workerIdFor: " + workerIdForm);
		try {
			workerViewsService.insert(workerViewsVO);
		} catch (BusinessException e) {
			String error = "An error occurred while performing the insert of worker views service. " + e.getMessage();
			logger.error (error);
		}
	}
}
