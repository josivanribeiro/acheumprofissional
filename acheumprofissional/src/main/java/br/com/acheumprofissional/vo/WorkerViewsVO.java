/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.vo;

import java.io.Serializable;

/**
 * VO class for WorkerViewsVO.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class WorkerViewsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long workerViewsId;
	private Long contractorId;
	private Long workerId;
	
	public Long getWorkerViewsId() {
		return workerViewsId;
	}
	public void setWorkerViewsId(Long workerViewsId) {
		this.workerViewsId = workerViewsId;
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
