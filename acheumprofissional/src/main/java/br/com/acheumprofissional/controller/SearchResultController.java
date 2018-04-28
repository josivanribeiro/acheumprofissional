/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.WorkerService;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Search Result Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@ViewScoped
public class SearchResultController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (SearchResultController.class.getName());
	private WorkerService workerService = new WorkerService();
		
	private Long jobCategoryIdForm;
	private List<WorkerVO> workerVOListForm;
	private boolean isShowPhone = false;
							
	
	public Long getJobCategoryIdForm() {
		return jobCategoryIdForm;
	}

	public void setJobCategoryIdForm(Long jobCategoryId) {
		this.jobCategoryIdForm = jobCategoryId;
	}

	public List<WorkerVO> getWorkerVOListForm() {
		return workerVOListForm;
	}

	public void setWorkerVOListForm(List<WorkerVO> workerVOListForm) {
		this.workerVOListForm = workerVOListForm;
	}
	
	public boolean isShowPhone() {
		return isShowPhone;
	}

	public void setShowPhone(boolean isShowPhone) {
		this.isShowPhone = isShowPhone;
	}
	
	@PostConstruct
    public void init() {
		Long jobCategoryId = getJobCategoryIdParam ();
		Long workerId = getWorkerIdParam ();
		
		if (jobCategoryId != null && jobCategoryId > 0) {
			findByJobCategoryId(jobCategoryId);
		} else if (workerId != null && workerId > 0) {
			findById (workerId);
		}
    }
	
	/**
	 * Finds all the workers from a category.
	 */
	public void findByJobCategoryId (Long jobCategoryId) {
		
		try {
			this.workerVOListForm = workerService.findByJobCategoryId(jobCategoryId);
			this.isShowPhone = false;
			logger.debug ("workerVOListForm.size() [" + workerVOListForm.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the workers by job category id00. " + e.getMessage();
			logger.error (error);
		}		
	}
	
	/**
	 * Finds a worker by its id.
	 */
	public void findById (Long workerId) {
		logger.debug("Starting findById");
		try {
			WorkerVO workerVO = workerService.findById(workerId);
			this.workerVOListForm = new ArrayList<WorkerVO>();
			workerVOListForm.add(workerVO);
			this.isShowPhone = true;											
		} catch (BusinessException e) {
			String error = "An error occurred while find the workers by job category id00. " + e.getMessage();
			logger.error (error);
		}		
	}
	
	/**
	 *  Gets the workerId param from query string.
	 * @return the workerId
	 */
	private Long getWorkerIdParam () {
		Long workerId = null;
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String paramWorkerId = params.get("workerId");
		if (paramWorkerId != null && !"".equals(paramWorkerId)) {
			workerId = new Long(paramWorkerId);
			logger.debug("workerId: " + workerId);
		}		
		return workerId;
	}
	
	
	/**
	 *  Gets the JobCategoryId param from query string.
	 * @return the workerId
	 */
	private Long getJobCategoryIdParam () {
		Long jobCategoryId = null;
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String paramJobCategoryId = params.get("id");
		if (paramJobCategoryId != null && !"".equals(paramJobCategoryId)) {
			jobCategoryId = new Long(paramJobCategoryId);
			logger.debug("jobCategoryId: " + jobCategoryId);
		}		
		return jobCategoryId;
	}
		
}
;