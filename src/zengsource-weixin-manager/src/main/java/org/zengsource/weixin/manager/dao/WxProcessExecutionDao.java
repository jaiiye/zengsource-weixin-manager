/**
 * 
 */
package org.zengsource.weixin.manager.dao;

import org.zengsource.util.dao.DaoInterface;
import org.zengsource.weixin.manager.domain.WxProcessExecution;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxProcessExecutionDao extends DaoInterface<WxProcessExecution, Long> {

	public WxProcessExecution queryRunning(WxUser wxUser);

}
