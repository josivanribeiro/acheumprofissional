/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.acheumprofissional.util.Constants;
import br.com.acheumprofissional.vo.ContractorVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Abstract Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class AbstractController {
	
	/**
	 * Gets the logged student from the session.
	 * 
	 * @return an instance of student.
	 *//*
	protected StudentVO getLoggedStudentFromSession () {
		StudentVO loggedStudent = null;
		loggedStudent = (StudentVO) FacesContext
    							.getCurrentInstance()
    								.getExternalContext()
    									.getSessionMap()
    										.get (Constants.LOGGED_STUDENT);
		return loggedStudent;
	}*/
	
	/** 
	 * Adds a info message to the faces message.
	 * 
	 * @param clientId the client id. 
	 * @param message the message.
	 */
	protected void addInfoMessage (String clientId, String message) {
		this.addFacesMessage (clientId, FacesMessage.SEVERITY_INFO, "Info", message);
    }
    
	/** 
	 * Adds a warn message to the faces message.
	 * 
	 * @param clientId the client id.
	 * @param message the message.
	 */
	protected void addWarnMessage (String clientId, String message) {
        this.addFacesMessage (clientId, FacesMessage.SEVERITY_WARN, "Warning!", message);
    }
	
	/** 
	 * Adds an error message to the faces message.
	 *
	 * @param clientId the client id. 
	 * @param message the message.
	 */
	protected void addErrorMessage (String clientId, String message) {
        this.addFacesMessage (clientId, FacesMessage.SEVERITY_ERROR, "Error!", message);
    }
    
    /** 
	 * Adds a fatal message to the faces message.
	 * 
	 * @param clientId the client id.
	 * @param message the message.
	 */
	protected void addFatalMessage (String clientId, String message) {
		this.addFacesMessage (clientId, FacesMessage.SEVERITY_FATAL, "Fatal!", message);
    }
	
	/**
	 * Utility method for adding FacesMessages to specified component.
	 *
	 * @param clientId - the client id.
	 * @param message - message to add.
	 */
	private void addFacesMessage (String clientId, Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage (clientId, new FacesMessage (severity, summary, detail));
	}
	
	/**
	 * Gets the logged worker id from session.
	 * 
	 * @return the worker id.
	 */
	protected Long getLoggedWorkerIdFromSession () {
		Long workerId = null;
		WorkerVO workerVO = (WorkerVO)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.WORKER_LOGGED_USER);
		if (workerVO != null) {
			workerId = workerVO.getWorkerId();  
		}		
		return workerId;
	} 
	
	/**
	 * Gets the logged contractor id from session.
	 * 
	 * @return the worker id.
	 */
	protected Long getLoggedContractorIdFromSession () {
		Long contractorId = null;
		ContractorVO contractorVO = (ContractorVO)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.CONTRACTOR_LOGGED_USER);
		if (contractorVO != null) {
			contractorId = contractorVO.getContractorId();
		}		
		return contractorId;
	}
	
}
