/**
 * 
 */
package org.zengsource.weixin.manager.dao.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.dao.ExecutionDao;
import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;
import org.zengsource.weixin.manager.domain.WxProcessExecution;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxProcessDefinitionService;
import org.zengsource.weixin.manager.service.WxProcessExecutionService;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxProcessExecutionDaoAdapter implements ExecutionDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxProcessDefinitionService wxProcessDefinitionService;
	@Autowired
	private WxProcessExecutionService wxProcessExecutionService;
	@Autowired
	private WxUserService wxUserService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Execution queryByUser(User user) {
		WxUser wxUser = wxUserService.findByOpenId(user.getOpenId());
		WxProcessExecution wxpe = wxProcessExecutionService.findRunning(wxUser);
		if (wxpe != null) {
			return wxProcessExecutionService.convert(wxpe);
		}
		return null;
	}

	@Override
	public Execution insert(Execution execution) {
		if (execution != null) {
			WxProcessExecution wxpe = WxProcessExecution.from(execution);
			WxUser wxUser = wxUserService.findByOpenId(execution.getUser().getOpenId());
			WxProcessDefinition wxpd = wxProcessDefinitionService.findByName( //
					execution.getDefinition().getName());
			wxpe.setWxUser(wxUser);
			wxpe.setWxProcessDefinition(wxpd);
			wxpe = wxProcessExecutionService.create(wxpe);
			if (wxpe != null) {
				return wxProcessExecutionService.convert(wxpe);
			}
		}
		return null;
	}

	@Override
	public Execution update(Execution execution) {
		WxUser wxUser = wxUserService.findByOpenId(execution.getUser().getOpenId());
		WxProcessExecution wxpe = wxProcessExecutionService.findRunning(wxUser);
		wxpe.setCurrent(execution.getCurrent());
		wxpe = wxProcessExecutionService.update(wxpe);
		return wxProcessExecutionService.convert(wxpe);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
