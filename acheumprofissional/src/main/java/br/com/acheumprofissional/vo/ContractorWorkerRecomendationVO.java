/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.vo;

import java.io.Serializable;

/**
 * VO class for ContractorWorkerRecomendationVO.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class ContractorWorkerRecomendationVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long contractorWorkerRecommendation;
	private Long contractorId;
	private Long workerId;
	
	public Long getContractorWorkerRecommendation() {
		return contractorWorkerRecommendation;
	}
	public void setContractorWorkerRecommendation(Long contractorWorkerRecommendation) {
		this.contractorWorkerRecommendation = contractorWorkerRecommendation;
	}
	public Long getContractorId() {
		return contractorId;
	}
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}
	public Long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}
	
}
