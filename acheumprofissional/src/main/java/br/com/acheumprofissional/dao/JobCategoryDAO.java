/* Copyright josivanSilva (Developer); 2015-2017 */
package br.com.acheumprofissional.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.acheumprofissional.vo.JobCategoryVO;

/**
 * DAO class for Job Category.
 * 
 * @author josivan@josivansilva.com
 *
 */
public class JobCategoryDAO extends AbstractDAO {

	
	public JobCategoryDAO() {
		
	}
	
	/**
	 * Finds all the job categories.
	 * 
	 * @return a list of job category.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<JobCategoryVO> findAll () throws DataAccessException {
		List<JobCategoryVO> list = new ArrayList<JobCategoryVO>();
		String sql               = null;
		List<Object> rowList     = null;
		sql = "SELECT JOB_CATEGORY_ID, NAME, STATUS FROM JOB_CATEGORY ORDER BY NAME";
		rowList = selectDb (sql, 3);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Long jobCategoryId = (Long) columns.get (0);
				String name = (String) columns.get (1);
				String status  = (String) columns.get (2);
				
				JobCategoryVO jobCategoryVO = new JobCategoryVO();
				jobCategoryVO.setJobCategoryId(jobCategoryId);
				jobCategoryVO.setName(name);
				jobCategoryVO.setStatus(status);
				
				list.add (jobCategoryVO);
			}
		}
		return list;
	}
	
	/**
	 * Finds a job category by id.
	 * @param id the id.
	 * @return
	 * @throws DataAccessException
	 */
	public JobCategoryVO findById (Long id) throws DataAccessException {
		JobCategoryVO foundJobCategoryVO = null;
		String sql           = null;
		List<Object> rowList = null;
		sql = "SELECT JOB_CATEGORY_ID, NAME, STATUS FROM JOB_CATEGORY WHERE JOB_CATEGORY_ID = " + id;
		logger.debug("\n\n" + sql + "\n\n");
		rowList = selectDb (sql, 3);
		logger.debug("rowList.size() " + rowList.size());
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Long jobCategoryId   = (Long) columns.get (0);
				String name          = (String) columns.get (1);
				String status        = (String) columns.get (2);
				
				foundJobCategoryVO = new JobCategoryVO();
				foundJobCategoryVO.setJobCategoryId(jobCategoryId);
				foundJobCategoryVO.setName(name);
				foundJobCategoryVO.setStatus(status);				
			}
		}
		return foundJobCategoryVO;
	}
	
}
