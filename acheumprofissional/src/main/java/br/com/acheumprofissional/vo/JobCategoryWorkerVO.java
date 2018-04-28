/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.vo;

/**
 * VO class for Job Category Worker.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class JobCategoryWorkerVO {

	private JobCategoryVO jobCategory;
	private WorkerVO workerVO;
	
	public JobCategoryVO getJobCategory() {
		return jobCategory;
	}
	public void setJobCategory(JobCategoryVO jobCategory) {
		this.jobCategory = jobCategory;
	}
	public WorkerVO getWorkerVO() {
		return workerVO;
	}
	public void setWorkerVO(WorkerVO workerVO) {
		this.workerVO = workerVO;
	}	
}
