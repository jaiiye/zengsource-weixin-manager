/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.List;

import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxProcessDefinitionService {

	public List<WxProcessDefinition> findAll();

	public WxProcessDefinition findByName(String name);

	public WxProcessDefinition create(WxProcessDefinition wxpd);

	public Definition convert(WxProcessDefinition wxpd);

}
