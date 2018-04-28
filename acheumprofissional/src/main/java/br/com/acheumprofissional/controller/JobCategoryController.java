/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.controller;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.acheumprofissional.services.BusinessException;
import br.com.acheumprofissional.services.JobCategoryService;
import br.com.acheumprofissional.vo.JobCategoryVO;
 

/**
 * Job Category Controller.
 * 
 * @author josivan@josivansilva.com
 *
 */
@ManagedBean
@ViewScoped
public class JobCategoryController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (JobCategoryController.class.getName());
	private JobCategoryService jobCategoryService = new JobCategoryService();
			
	private Long jobCategoryIdForm;
	private String nameForm;
	private int statusForm;
	private Map<String,Object> jobCategoryMap;
		
	public Long getJobCategoryIdForm() {
		return jobCategoryIdForm;
	}
	
	public void setJobCategoryIdForm(Long jobCategoryIdForm) {
		this.jobCategoryIdForm = jobCategoryIdForm;
	}
	
	public String getNameForm() {
		return nameForm;
	}

	public void setNameForm(String nameForm) {
		this.nameForm = nameForm;
	}

	public int getStatusForm() {
		return statusForm;
	}

	public void setStatusForm(int statusForm) {
		this.statusForm = statusForm;
	}
	
	public Map<String, Object> getJobCategoryMap() {
		return jobCategoryMap;
	}

	public void setJobCategoryMap(Map<String, Object> jobCategoryMap) {
		this.jobCategoryMap = jobCategoryMap;
	}

	@PostConstruct
    public void init() {
		initJobCategorySelect();
    }
	
	/**
	 * Initializes the job category select.
	 */
	private void initJobCategorySelect() {
		List<JobCategoryVO> jobCategoryVOList = null;
		jobCategoryMap = new LinkedHashMap<String,Object>();
		jobCategoryMap.put ("", ""); //label, value
		try {
			jobCategoryVOList = this.jobCategoryService.findAll();			
			if (jobCategoryVOList != null && jobCategoryVOList.size() > 0) {				
				for (JobCategoryVO jobCategoryVO : jobCategoryVOList) {
					jobCategoryMap.put (jobCategoryVO.getName(), jobCategoryVO.getJobCategoryId());
				}
			}			
		} catch (BusinessException e) {
			String error = "An error occurred while finding all the job categories. " + e.getMessage();
			logger.error (error);
		}
	}
}
