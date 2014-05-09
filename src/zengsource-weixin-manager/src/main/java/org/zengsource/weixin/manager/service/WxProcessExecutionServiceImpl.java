/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.manager.dao.WxProcessExecutionDao;
import org.zengsource.weixin.manager.domain.WxProcessExecution;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxProcessExecutionServiceImpl implements WxProcessExecutionService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxProcessExecutionDao wxProcessExecutionDao;
	@Autowired
	private WxProcessDefinitionService wxProcessDefinitionService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxProcessExecution create(WxProcessExecution wxpe) {
		if (wxpe != null) {
			return wxProcessExecutionDao.insert(wxpe);
		}
		return null;
	}

	@Override
	public WxProcessExecution findRunning(WxUser wxUser) {
		if (wxUser != null) {
			return wxProcessExecutionDao.queryRunning(wxUser);
		}
		return null;
	}

	@Override
	public WxProcessExecution update(WxProcessExecution wxpe) {
		if (wxpe != null) {
			return this.wxProcessExecutionDao.update(wxpe);
		}
		return null;
	}

	@Override
	public Execution convert(WxProcessExecution wxpe) {
		Execution execution = new Execution();
		execution.setDefinition(wxProcessDefinitionService.convert(wxpe.getWxProcessDefinition()));
		execution.setUser(wxpe.getWxUser().toUser());
		execution.setCurrent(wxpe.getCurrent());
		return execution;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxProcessExecutionDao getWxProcessExecutionDao() {
		return wxProcessExecutionDao;
	}

	public void setWxProcessExecutionDao(WxProcessExecutionDao wxProcessExecutionDao) {
		this.wxProcessExecutionDao = wxProcessExecutionDao;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
