/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.InputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.JobCategoryService;
import br.com.acheumprofissional.services.WorkerService;
import br.com.acheumprofissional.util.Utils;
import br.com.acheumprofissional.vo.JobCategoryVO;
import br.com.acheumprofissional.vo.WorkerVO;

/**
 * Details Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@SessionScoped
public class DetailsController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (DetailsController.class.getName());
	private WorkerService workerService = new WorkerService();
	private JobCategoryService jobCategoryService = new JobCategoryService();
		
	private WorkerVO workerVOForm;
	private StreamedContent thumbImage;
	private Long workerIdForm;
	
	public WorkerVO getWorkerVOForm() {
		return workerVOForm;
	}

	public void setWorkerVOForm(WorkerVO workerVOForm) {
		this.workerVOForm = workerVOForm;
	}
	
	public StreamedContent getThumbImage() {
		return thumbImage;
	}

	public void setThumbImage(StreamedContent thumbImage) {
		this.thumbImage = thumbImage;
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
	
	/**
	 * Finds the job category name from a category.
	 */
	public String findNameByJobCategoryId (Long jobCategoryId) {
		String name = null;
		try {
			JobCategoryVO jobCategoryVO = jobCategoryService.findById(jobCategoryId);
			name = jobCategoryVO.getName();
		} catch (BusinessException e) {
			String error = "An error occurred while find the workers by job category id. " + e.getMessage();
			logger.error (error);
		}
		return name;
	}
	
	/**
	 * Finds a worker by its id.
	 */
	public void findById () {
		logger.debug("\n\n\nStartinf find by id " + workerIdForm);
		try {
			workerVOForm = workerService.findById(workerIdForm);
			
			String jobCategoryName = this.findNameByJobCategoryId(workerVOForm.getJobCategoryId());
			workerVOForm.setJobCategoryName(jobCategoryName);
			
			if (workerVOForm.getImageThumb() != null) {
        		logger.debug("foundWorkerVO.getImageThumb() is not null");
        		String mimeType = "image/jpg";	     
            	//resizes an image
            	InputStream resizedImage = Utils.resizeImageWithHint(workerVOForm.getImageThumb());
            	
            	//logger.debug("resizedImage is null " + resizedImage == null);
            	
            	thumbImage = new DefaultStreamedContent(resizedImage, mimeType, "");
            	
            	logger.debug("thumbImage is null " + thumbImage == null);
        	}			
			logger.debug("workerVO.getWorkerId(): " + workerVOForm.getWorkerId());
										
		} catch (BusinessException e) {
			String error = "An error occurred while find the workers by job category id00. " + e.getMessage();
			logger.error (error);
		}		
	}	
			
}
;