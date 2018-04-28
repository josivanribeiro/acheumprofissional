/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.ContractorService;
import br.com.acheumprofissional.services.WorkerService;
import br.com.acheumprofissional.util.Utils;
import br.com.acheumprofissional.vo.ContractorVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Contractor Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@RequestScoped
public class ContractorController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (ContractorController.class.getName());
	private ContractorService contractorService = new ContractorService();
	private WorkerService workerService = new WorkerService();
			
	private Long contractorIdForm;
	private String firstNameForm;
	private String lastNameForm;
	private String usernameForm;
	private String pwdForm;
	private String cpfForm;
	private String emailForm;
	private List<WorkerVO> workerWithWorkerViewsListForm = new ArrayList<WorkerVO>();
	
	private String firstPwdForm;
	private String confirmationPwdForm;
		
	public Long getContractorIdForm() {
		return contractorIdForm;
	}

	public void setContractorIdForm(Long contractorIdForm) {
		this.contractorIdForm = contractorIdForm;
	}

	public String getFirstNameForm() {
		return firstNameForm;
	}

	public void setFirstNameForm(String firstNameForm) {
		this.firstNameForm = firstNameForm;
	}

	public String getLastNameForm() {
		return lastNameForm;
	}

	public void setLastNameForm(String lastNameForm) {
		this.lastNameForm = lastNameForm;
	}

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
	
	public String getCpfForm() {
		return cpfForm;
	}

	public void setCpfForm(String cpfForm) {
		this.cpfForm = cpfForm;
	}
	
	public String getEmailForm() {
		return emailForm;
	}

	public void setEmailForm(String emailForm) {
		this.emailForm = emailForm;
	}

	public List<WorkerVO> getWorkerWithWorkerViewsListForm() {
		return workerWithWorkerViewsListForm;
	}

	public void setWorkerWithWorkerViewsListForm(List<WorkerVO> workerWithWorkerViewsListForm) {
		this.workerWithWorkerViewsListForm = workerWithWorkerViewsListForm;
	}
	
	public String getFirstPwdForm() {
		return firstPwdForm;
	}

	public void setFirstPwdForm(String firstPwdForm) {
		this.firstPwdForm = firstPwdForm;
	}

	public String getConfirmationPwdForm() {
		return confirmationPwdForm;
	}

	public void setConfirmationPwdForm(String confirmationPwdForm) {
		this.confirmationPwdForm = confirmationPwdForm;
	}

	@PostConstruct
    public void init() {
		this.findByIdWithWorkerViews();
		loadById ();
    }
	
	/**
	 * Performs the contractor save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidForm ()) {
			ContractorVO contractorVO = new ContractorVO ();
			contractorVO.setFirstName(firstNameForm);
			contractorVO.setLastName(lastNameForm);
			contractorVO.setUsername(usernameForm);
			contractorVO.setPwd(pwdForm);
			contractorVO.setCpf(cpfForm);
			contractorVO.setEmail(emailForm);
			contractorVO.setStatus("1");
			try {
				long newId = contractorService.insert (contractorVO);
				if (newId > 0) {
					super.addInfoMessage ("formAddContractor:messagesAddContractor", " Contratante cadastrado com sucesso.");
					logger.info ("The contractor [" + contractorVO.getUsername() + "] has been successfully inserted.");
				}			
			} catch (BusinessException e) {
				String error = "An error occurred while saving or updating the contractor. " + e.getMessage();
				logger.error (error);
			}
			this.resetForm();			
		}
	}
	
	/**
	 * Performs the contractor update operation.
	 */
	public void update (ActionEvent actionEvent) {
		if (isValidUpdateForm ()) {
			ContractorVO contractorVO = new ContractorVO ();
			contractorVO.setContractorId(contractorIdForm);
			contractorVO.setFirstName(firstNameForm);
			contractorVO.setLastName(lastNameForm);
			contractorVO.setUsername(usernameForm);
			contractorVO.setCpf(cpfForm);
			contractorVO.setEmail(emailForm);
			try {
				int affectedRows = contractorService.update (contractorVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateContractor:messagesUpdateContractor", " Contratante atualizado com sucesso.");
					logger.info ("The contractor [" + contractorVO.getUsername() + "] has been successfully updated.");
				}			
			} catch (BusinessException e) {
				String error = "An error occurred while updating the contractor. " + e.getMessage();
				logger.error (error);
			}						
		}
	}
	
	/**
	 * Performs the contractor pwd update operation.
	 */
	public void updatePwd (ActionEvent actionEvent) {
		logger.debug("\n\n\nStarting updatePwd\n\n\n");
		if (isValidUpdatePwdForm ()) {
			ContractorVO contractorVO = new ContractorVO ();
			contractorVO.setContractorId(contractorIdForm);
			contractorVO.setUsername(usernameForm);
			contractorVO.setPwd(firstPwdForm);						
			try {
				long affectedRows = contractorService.updatePwd (contractorVO);
				
				logger.debug("\n\n\naffectedRows: " + affectedRows);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateContractorPwd:messagesUpdateContractorPwd", " Senha atualizada com sucesso.");
					logger.info ("The contractor [" + contractorVO.getUsername() + "] has its pwd successfully updated.");
				}			
			} catch (BusinessException e) {
				String error = "An error occurred while updating the contractor pwd. " + e.getMessage();
				logger.error (error);
			}
			this.resetChangePwdForm();
		}
	}
	
	/**
	 * Sets a recommendation.
	 * 
	 * @param workerId the worker id.
	 */
	public void setRecommendation(Long workerId) {
		logger.info("workerIdForm: " + workerId);
		WorkerVO workerVO = new WorkerVO();
		workerVO.setWorkerId(workerId);
		
		if (!checkRecommendationExists(workerId)) {
			try {				
				Long contractorId = getLoggedContractorIdFromSession();
				ContractorVO contractor = new ContractorVO();
				contractor.setContractorId(contractorId);
					workerService.updateRecommendations(workerVO, contractor);
			} catch (BusinessException e) {
				String error = "An error occurred while saving recommendation to the worker. " + e.getMessage();
				logger.error (error);
			}
		}		
	}
	
	/**
	 * Checks if recommendations exists.
	 * 
	 * @param workerId the worker id.
	 * @return
	 */
	public boolean checkRecommendationExists (Long workerId) {
		boolean exists = false;
		try {
			Long contractorId = getLoggedContractorIdFromSession();
			logger.debug("contractorId from session: " + contractorId);
			exists = contractorService.existsRecommendation(contractorId, workerId);
			
			logger.debug("\n\n\n\nexists: " + exists);
			
			if (exists) {
				super.addWarnMessage ("formRecommendation:messagesRecommendation", " Você já recomendou este prestador de serviço.");
				exists = true;
			} 
						
		} catch (BusinessException e) {
			String error = "An error occurred while saving recommendation to the worker. " + e.getMessage();
			logger.error (error);
		}
		return exists;
	}
	
	/**
	 * Finds the workers with worker views.
	 */
	public void findByIdWithWorkerViews () {
		logger.debug("\nStarting findByIdWithWorkerViews.");
		try {
			Long contractorId = getLoggedContractorIdFromSession();
			logger.debug("\n\n\ncontractorId from session - " + contractorId);;
			if (contractorId != null) {
				this.workerWithWorkerViewsListForm = contractorService.findByIdWithWorkerViews(contractorId);
			}
			logger.debug ("workerWithWorkerViewsListForm.size() [" + workerWithWorkerViewsListForm.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the workers by workerIdWithWorkerViews. " + e.getMessage();
			logger.error (error);
		}		
	}
		
	/**
	 * Loads a contractor according with the specified id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		Long contractorId = getLoggedContractorIdFromSession();
		if (contractorId != null && contractorId > 0) {
			
	        try {
	        	ContractorVO foundContractorVO = this.contractorService.findById (contractorId);
	        	contractorIdForm = foundContractorVO.getContractorId();
	        	firstNameForm = foundContractorVO.getFirstName();
	        	lastNameForm = foundContractorVO.getLastName();
	        	usernameForm = foundContractorVO.getUsername();
	        	cpfForm = foundContractorVO.getCpf();
	        	emailForm = foundContractorVO.getEmail();
	        	logger.debug ("foundContractorVO.getFirstName() " + foundContractorVO.getFirstName());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the contractor by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Validates the career insert form.
	 * 
	 */
	private boolean isValidForm () {						
		if (!Utils.isNonEmpty (this.firstNameForm)
				|| !Utils.isNonEmpty (this.lastNameForm)
				|| !Utils.isNonEmpty (this.usernameForm)
				|| !Utils.isNonEmpty (this.pwdForm)
				|| !Utils.isNonEmpty (this.cpfForm)) {
			super.addWarnMessage ("formAddContractor:messagesAddContractor", " Preencha todos os campos corretamente.");
			return false;
		} else if (!Utils.isCPF(Utils.getCPFAsNumber(this.cpfForm))) {
			super.addWarnMessage ("formAddContractor:messagesAddContractor", " CPF inválido.");
			return false;
		} else if (Utils.isNonEmpty (this.emailForm) && !Utils.isValidEmail(emailForm)) {
			super.addWarnMessage ("formAddContractor:messagesAddContractor", " Email inválido.");
			return false;
		} else if (this.usernameExists(usernameForm)) {
			super.addWarnMessage ("formAddContractor:messagesAddContractor", " Nome de usuário já existe.");
			return false;
		} else if (this.cpfExists(cpfForm)) {
			super.addWarnMessage ("formAddContractor:messagesAddContractor", " CPF já existe.");
			return false;
		}
		return true;
    }
	
	private boolean isValidUpdateForm () {						
		if (!Utils.isNonEmpty (this.firstNameForm)
				|| !Utils.isNonEmpty (this.lastNameForm)
				|| !Utils.isNonEmpty (this.usernameForm)
				|| !Utils.isNonEmpty (this.cpfForm)) {
			super.addWarnMessage ("formUpdateContractor:messagesUpdateContractor", " Preencha todos os campos corretamente.");
			return false;
		} else if (!Utils.isCPF(Utils.getCPFAsNumber(this.cpfForm))) {
			super.addWarnMessage ("formUpdateContractor:messagesUpdateContractor", " CPF inválido.");
			return false;
		} else if (Utils.isNonEmpty (this.emailForm) && !Utils.isValidEmail(this.emailForm)) {
			super.addWarnMessage ("formUpdateContractor:messagesUpdateContractor", " Email inválido.");
			return false;
		} else if (this.usernameExists(usernameForm)) {
			super.addWarnMessage ("formUpdateContractor:messagesUpdateContractor", " Nome de usuário já existe.");
			return false;
		} else if (this.cpfExists(cpfForm)) {
			super.addWarnMessage ("formUpdateContractor:messagesUpdateContractor", " CPF já existe.");
			return false;
		}
		return true;
    }
	
	private boolean isValidUpdatePwdForm () {	
		if (!firstPwdForm.equals(confirmationPwdForm)) {
			super.addWarnMessage ("formUpdateContractorPwd:messagesUpdateContractorPwd", " A primeira senha deve ser igual à segunda.");
			return false;
		} else if (this.contractorIdForm == null || this.contractorIdForm == 0
				|| !Utils.isNonEmpty (this.firstPwdForm)
				|| !Utils.isNonEmpty (this.confirmationPwdForm)) {
			super.addWarnMessage ("formUpdateContractorPwd:messagesUpdateContractorPwd", " Preencha todos os campos corretamente.");
			return false;
		} 
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {
		this.contractorIdForm = null;
		this.firstNameForm    = null;
		this.lastNameForm     = null;
		this.usernameForm     = null;
		this.pwdForm          = null;
		this.cpfForm          = null;
		this.emailForm        = null;
	}
	
	/**
	 * Resets the update pwd form.
	 */
	private void resetChangePwdForm () {
		this.contractorIdForm = null;
		this.firstPwdForm = null;
		this.confirmationPwdForm = null;			
	}
	
	/**
	 * Checks if a username already exists.
	 * 
	 * @param username the username.
	 * @return
	 */
	private boolean usernameExists (String username) {
		boolean result = false;
		try {
			result = contractorService.existsUsername(username);
		} catch (BusinessException e) {
			String error = "An error occurred checking if an username exists. " + e.getMessage();
			logger.error (error);
		}
		return result;
	}
	
	/**
	 * Checks if a cpf already exists.
	 * 
	 * @param cpf the cpf
	 * @return
	 */
	private boolean cpfExists (String cpf) {
		boolean result = false;
		try {
			result = contractorService.existsCpf(cpf);
		} catch (BusinessException e) {
			String error = "An error occurred checking if a cpf exists. " + e.getMessage();
			logger.error (error);
		}
		return result;
	}
	
}
