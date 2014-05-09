/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.manager.domain.WxProcessExecution;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxProcessExecutionService {

	public WxProcessExecution findRunning(WxUser wxUser);

	public WxProcessExecution create(WxProcessExecution wxpe);

	public WxProcessExecution update(WxProcessExecution wxpe);
	
	public Execution convert(WxProcessExecution wxpe);

}
