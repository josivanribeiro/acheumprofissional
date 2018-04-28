/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.vo;

import java.io.Serializable;

/**
 * VO class for Contractor.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ContractorVO implements Serializable    {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long contractorId;
	private String firstName;
	private String lastName;
	private String username;
	private String pwd;
	private String cpf;
	private String email;
	private String status;
	
	public Long getContractorId() {
		return contractorId;
	}
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	
}
