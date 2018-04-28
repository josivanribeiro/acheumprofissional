/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.vo;

/**
 * VO class for Job Category.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class JobCategoryVO {

	private Long jobCategoryId;
	private String name;
	private String status;
	
	public Long getJobCategoryId() {
		return jobCategoryId;
	}
	public void setJobCategoryId(Long jobCategoryId) {
		this.jobCategoryId = jobCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}
