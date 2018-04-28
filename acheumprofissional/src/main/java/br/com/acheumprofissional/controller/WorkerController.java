/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.JobCategoryService;
import br.com.acheumprofissional.services.WorkerService;
import br.com.acheumprofissional.util.Utils;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Worker Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@RequestScoped
public class WorkerController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (WorkerController.class.getName());
	private WorkerService workerService = new WorkerService();
	private JobCategoryService jobCategoryService = new JobCategoryService();
	
	private Long workerIdForm;
	private String firstNameForm;
	private String lastNameForm;
	private Long jobCategoryIdForm;
	private String usernameForm;
	private String pwdForm;
	private String cpfForm;
	private String phoneForm;
	private String emailForm;
	private String birthForm;
	private String descriptionForm;
	private StreamedContent thumbImage;
	private List<WorkerVO> workerVOListForm;
	private Long jobCategoryIdFilterForm;
	private List<WorkerVO> workerWithWorkerViewsListForm = new ArrayList<WorkerVO>();
	
	private String firstPwdForm;
	private String confirmationPwdForm;
	
	private UploadedFile file;
			
	public JobCategoryService getJobCategoryService() {
		return jobCategoryService;
	}

	public void setJobCategoryService(JobCategoryService jobCategoryService) {
		this.jobCategoryService = jobCategoryService;
	}

	public Long getWorkerIdForm() {
		return workerIdForm;
	}

	public void setWorkerIdForm(Long workerIdForm) {
		this.workerIdForm = workerIdForm;
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

	public Long getJobCategoryIdForm() {
		return jobCategoryIdForm;
	}

	public void setJobCategoryIdForm(Long jobCategoryId) {
		this.jobCategoryIdForm = jobCategoryId;
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

	public String getPhoneForm() {
		return phoneForm;
	}

	public void setPhoneForm(String phoneForm) {
		this.phoneForm = phoneForm;
	}
	
	public String getEmailForm() {
		return emailForm;
	}

	public void setEmailForm(String emailForm) {
		this.emailForm = emailForm;
	}

	public String getBirthForm() {
		return birthForm;
	}

	public void setBirthForm(String birthForm) {
		this.birthForm = birthForm;
	}

	public String getDescriptionForm() {
		return descriptionForm;
	}

	public void setDescriptionForm(String descriptionForm) {
		this.descriptionForm = descriptionForm;
	}
	
	public StreamedContent getThumbImage() {
		return thumbImage;
	}

	public void setThumbImage(StreamedContent thumbImage) {
		this.thumbImage = thumbImage;
	}

	public List<WorkerVO> getWorkerVOListForm() {
		return workerVOListForm;
	}

	public void setWorkerVOListForm(List<WorkerVO> workerVOListForm) {
		this.workerVOListForm = workerVOListForm;
	}
	
	public Long getJobCategoryIdFilterForm() {
		return jobCategoryIdFilterForm;
	}

	public void setJobCategoryIdFilterForm(Long jobCategoryIdFilterForm) {
		this.jobCategoryIdFilterForm = jobCategoryIdFilterForm;
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
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	@PostConstruct
    public void init() {
		this.findByIdWithWorkerViews ();
		loadById ();
    }
	
	/**
	 * Performs the contractor save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidSaveForm ()) {
			WorkerVO workerVO = new WorkerVO ();
			workerVO.setFirstName(firstNameForm);
			workerVO.setLastName(lastNameForm);
			workerVO.setJobCategoryId(jobCategoryIdForm);
			workerVO.setUsername(usernameForm);
			workerVO.setPwd(pwdForm);
			workerVO.setCpf(cpfForm);
			workerVO.setPhone(phoneForm);
			workerVO.setEmail(emailForm);
			workerVO.setBirth(birthForm);
			try {
				workerVO.setImageThumb(file.getInputstream());
			} catch (IOException e1) {
				String error = "An error occurred while saving the file input stream. " + e1.getMessage();
				logger.error (error);
			}
			workerVO.setDescription(descriptionForm);
			workerVO.setStatus("1");
			try {
				int affectedRows = workerService.insert (workerVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formAddWorker:messagesAddWorker", " Prestador de serviço cadastrado com sucesso.");
					logger.info ("The worker [" + workerVO.getUsername() + "] has been successfully inserted.");
				}			
			} catch (BusinessException e) {
				String error = "An error occurred while saving or updating the worker. " + e.getMessage();
				logger.error (error);
			}
			this.resetForm();			
		}
	}
	
	/**
	 * Performs the contractor update operation.
	 */
	public void update (ActionEvent actionEvent) {
		logger.debug("\n\n\nStarting update\n\n\n");
		if (isValidUpdateForm ()) {
			WorkerVO workerVO = new WorkerVO ();
			workerVO.setWorkerId(workerIdForm);
			workerVO.setFirstName(firstNameForm);
			workerVO.setLastName(lastNameForm);
			workerVO.setJobCategoryId(jobCategoryIdForm);
			workerVO.setUsername(usernameForm);
			workerVO.setCpf(cpfForm);
			workerVO.setPhone(phoneForm);
			workerVO.setEmail(emailForm);
			workerVO.setBirth(birthForm);
			try {
				workerVO.setImageThumb(file.getInputstream());
			} catch (IOException e1) {
				String error = "An error occurred while saving the file input stream. " + e1.getMessage();
				logger.error (error);
			}
			workerVO.setDescription(descriptionForm);			
			try {
				long affectedRows = workerService.update (workerVO);
				
				logger.debug("\n\n\naffectedRows: " + affectedRows);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateWorker:messagesUpdateWorker", " Prestador de serviço atualizado com sucesso.");
					logger.info ("The worker [" + workerVO.getUsername() + "] has been successfully updated.");
				}			
			} catch (BusinessException e) {
				String error = "An error occurred while saving or updating the worker. " + e.getMessage();
				logger.error (error);
			}						
		}
	}
	
	/**
	 * Performs the worker pwd update operation.
	 */
	public void updatePwd (ActionEvent actionEvent) {
		logger.debug("\n\n\nStarting updatePwd\n\n\n");
		if (isValidUpdatePwdForm ()) {
			WorkerVO workerVO = new WorkerVO ();
			workerVO.setWorkerId(workerIdForm);
			workerVO.setUsername(usernameForm);
			workerVO.setPwd(firstPwdForm);						
			try {
				long affectedRows = workerService.updatePwd (workerVO);
				
				logger.debug("\n\n\naffectedRows: " + affectedRows);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateWorkerPwd:messagesUpdateWorkerPwd", " Senha atualizada com sucesso.");
					logger.info ("The worker [" + workerVO.getUsername() + "] has its pwd successfully updated.");
				}			
			} catch (BusinessException e) {
				String error = "An error occurred while updating the worker pwd. " + e.getMessage();
				logger.error (error);
			}
			this.resetChangePwdForm();
		}
	}
	
	/**
	 * Finds all the job categories.
	 */
	public void findByJobCategoryId () {
		
		try {
			this.workerVOListForm = workerService.findByJobCategoryId(jobCategoryIdFilterForm);
			logger.debug ("workerVOListForm.size() [" + workerVOListForm.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the workers by job category id. " + e.getMessage();
			logger.error (error);
		}		
	}
	
	/**
	 * Finds the workers with worker views.
	 */
	public void findByIdWithWorkerViews () {
		
		try {
			Long workerId = getLoggedWorkerIdFromSession();
			if (workerId != null) {
				this.workerWithWorkerViewsListForm = workerService.findByIdWithWorkerViews(workerId);
			}			
			logger.debug ("workerWithWorkerViewsListForm.size() [" + workerWithWorkerViewsListForm.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the workers by workerIdWithWorkerViews. " + e.getMessage();
			logger.error (error);
		}		
	}
	
	/**
	 * Loads a worker according with the specified id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		Long workerId = getLoggedWorkerIdFromSession();
		if (workerId != null && workerId > 0) {
			
	        try {
	        	WorkerVO foundWorkerVO = this.workerService.findById (workerId);
	        	workerIdForm = foundWorkerVO.getWorkerId();
	        	firstNameForm = foundWorkerVO.getFirstName();
	        	lastNameForm = foundWorkerVO.getLastName();
	        	jobCategoryIdForm = foundWorkerVO.getJobCategoryId();
	        	usernameForm = foundWorkerVO.getUsername();
	        	cpfForm = foundWorkerVO.getCpf();
	        	phoneForm = foundWorkerVO.getPhone();
	        	emailForm = foundWorkerVO.getEmail();
	        	birthForm = foundWorkerVO.getBirth();
	        	
	        	if (foundWorkerVO.getImageThumb() != null) {
	        		logger.debug("foundWorkerVO.getImageThumb() is not null");
	        		String mimeType = "image/jpg";	     
		        	//resizes an image
		        	InputStream resizedImage = Utils.resizeImageWithHint(foundWorkerVO.getImageThumb());
		        	if (resizedImage != null) {
		        		thumbImage = new DefaultStreamedContent(resizedImage, mimeType, "");
		        	}		        	
	        	}	        		        	      	 
	        	
	        	descriptionForm = foundWorkerVO.getDescription();
	        	logger.debug ("foundWorkerVO.getFirstName() " + foundWorkerVO.getFirstName());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the worker by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	
	/**
	 * Validates the worker insert/update form.
	 * 
	 */
	private boolean isValidSaveForm () {						
		if (!Utils.isNonEmpty (this.firstNameForm)
				|| !Utils.isNonEmpty (this.lastNameForm)
				|| (this.jobCategoryIdForm == null || this.jobCategoryIdForm == 0) 
				|| !Utils.isNonEmpty (this.usernameForm)
				|| !Utils.isNonEmpty (this.pwdForm)
				|| !Utils.isNonEmpty (this.cpfForm)
				|| !Utils.isNonEmpty (this.phoneForm)
				|| !Utils.isNonEmpty (this.birthForm)) {
			super.addWarnMessage ("formAddWorker:messagesAddWorker", " Preencha todos os campos corretamente.");
			return false;
		} else if (!Utils.isCPF(Utils.getCPFAsNumber(this.cpfForm))) {
			super.addWarnMessage ("formAddWorker:messagesAddWorker", " CPF inválido.");
			return false;
		} else if (Utils.isNonEmpty (this.emailForm) && !Utils.isValidEmail(emailForm)) {
			super.addWarnMessage ("formAddWorker:messagesAddWorker", " Email inválido.");
			return false;
		} else if (this.usernameExists(usernameForm)) {
			super.addWarnMessage ("formAddWorker:messagesAddWorker", " Nome de usuário já existe.");
			return false;
		} else if (this.cpfExists(cpfForm)) {
			super.addWarnMessage ("formAddWorker:messagesAddWorker", " CPF já existe.");
			return false;
		}
		return true;
    }
	
	private boolean isValidUpdateForm () {						
		if (!Utils.isNonEmpty (this.firstNameForm)
				|| !Utils.isNonEmpty (this.lastNameForm)
				|| (this.jobCategoryIdForm == null || this.jobCategoryIdForm == 0) 
				|| !Utils.isNonEmpty (this.usernameForm)
				|| !Utils.isNonEmpty (this.cpfForm)
				|| !Utils.isNonEmpty (this.phoneForm)
				|| !Utils.isNonEmpty (this.birthForm)) {
			super.addWarnMessage ("formUpdateWorker:messagesUpdateWorker", " Preencha todos os campos corretamente.");
			return false;
		} else if (!Utils.isCPF(Utils.getCPFAsNumber(this.cpfForm))) {
			super.addWarnMessage ("formUpdateWorker:messagesUpdateWorker", " CPF inválido.");
			return false ;
		} else if (Utils.isNonEmpty (this.emailForm) && !Utils.isValidEmail(emailForm)) {
			super.addWarnMessage ("formUpdateWorker:messagesUpdateWorker", " Email inválido.");
			return false;
		} else if (this.usernameExists(usernameForm)) {
			super.addWarnMessage ("formUpdateWorker:messagesUpdateWorker", " Nome de usuário já existe.");
			return false;
		} else if (this.cpfExists(cpfForm)) {
			super.addWarnMessage ("formUpdateWorker:messagesUpdateWorker", " CPF já existe.");
			return false;
		}
		return true;
    }
	
	private boolean isValidUpdatePwdForm () {		
		if (!firstPwdForm.equals(confirmationPwdForm)) {
			super.addWarnMessage ("formUpdateWorkerPwd:messagesUpdateWorkerPwd", " A primeira senha deve ser igual à segunda.");
			return false;
		} else if (this.workerIdForm == null || this.workerIdForm == 0
				|| !Utils.isNonEmpty (this.firstPwdForm)
				|| !Utils.isNonEmpty (this.confirmationPwdForm)) {
			super.addWarnMessage ("formUpdateWorkerPwd:messagesUpdateWorkerPwd", " Preencha todos os campos corretamente.");
			return false;
		} 
		return true;
    }	
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {
		this.workerIdForm = null;
		this.firstNameForm     = null;
		this.lastNameForm      = null;
		this.jobCategoryIdForm = null;
		this.usernameForm      = null;
		this.pwdForm           = null;
		this.cpfForm           = null;
		this.phoneForm         = null;
		this.emailForm         = null;
		this.birthForm         = null;
		this.descriptionForm   = null;		
	}
	
	/**
	 * Resets the update pwd form.
	 */
	private void resetChangePwdForm () {
		this.workerIdForm = null;
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
			result = workerService.existsUsername(username);
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
			result = workerService.existsCpf(cpf);
		} catch (BusinessException e) {
			String error = "An error occurred checking if a cpf exists. " + e.getMessage();
			logger.error (error);
		}
		return result;
	}
		
}
