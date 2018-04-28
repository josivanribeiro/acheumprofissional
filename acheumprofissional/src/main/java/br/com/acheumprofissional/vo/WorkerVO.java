/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.vo;

import java.io.InputStream;
import java.io.Serializable;

/**
 * VO class for Worker.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class WorkerVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long workerId;
	private String firstName;
	private String lastName;
	private String username;
	private Long jobCategoryId;
	private String jobCategoryName;
	private String pwd;
	private String cpf;
	private String phone;
	private String email;
	private String birth;
	private InputStream imageThumb;
	private String description;
	private String status;
	private Long workerViewsCount;
	private int recommendations;
	
	
	public Long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getJobCategoryId() {
		return jobCategoryId;
	}
	public void setJobCategoryId(Long jobCategoryId) {
		this.jobCategoryId = jobCategoryId;
	}
	
	public String getJobCategoryName() {
		return jobCategoryName;
	}
	public void setJobCategoryName(String jobCategoryName) {
		this.jobCategoryName = jobCategoryName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public InputStream getImageThumb() {
		return imageThumb;
	}
	public void setImageThumb(InputStream imageThumb) {
		this.imageThumb = imageThumb;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getWorkerViewsCount() {
		return workerViewsCount;
	}
	public void setWorkerViewsCount(Long workerViewsCount) {
		this.workerViewsCount = workerViewsCount;
	}
	public int getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(int recommendations) {
		this.recommendations = recommendations;
	}		
}
