/**
 * 
 */
package org.zengsource.weixin.manager.dao;

import org.zengsource.util.dao.DaoInterface;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;
import org.zengsource.weixin.manager.domain.WxProcessNode;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxProcessNodeDao extends DaoInterface<WxProcessNode, Long> {

	public WxProcessNode queryUnique(WxProcessDefinition wxpd, String name);

}
