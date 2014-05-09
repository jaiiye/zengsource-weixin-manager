/**
 * 
 */
package org.zengsource.weixin.manager.dao;

import java.util.List;

import org.zengsource.util.dao.DaoInterface;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxProcessDefinitionDao extends DaoInterface<WxProcessDefinition, Long> {

	public List<WxProcessDefinition> queryAll();

}
