/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.vo;

import java.io.Serializable;

/**
 * VO class for Forgot Pwd.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ForgotPwdVO implements Serializable    {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long forgotPwdId;
	private String type;
	private String username;
	private String email;
	private String token;
	
	public Long getForgotPwdId() {
		return forgotPwdId;
	}
	public void setForgotPwdId(Long forgotPwdId) {
		this.forgotPwdId = forgotPwdId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
