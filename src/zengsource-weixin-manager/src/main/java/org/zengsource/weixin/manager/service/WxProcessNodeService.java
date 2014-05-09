/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.core.service.NodeService;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;
import org.zengsource.weixin.manager.domain.WxProcessNode;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxProcessNodeService extends NodeService{

	public WxProcessNode find(WxProcessDefinition wxpd, String name);

	public WxProcessNode create(WxProcessNode wxpn);

	public Node convert(WxProcessNode wxpn);

}
