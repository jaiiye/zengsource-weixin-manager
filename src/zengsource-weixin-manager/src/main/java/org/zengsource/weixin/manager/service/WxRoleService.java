/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.zengsource.weixin.manager.domain.WxRole;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxRoleService {

	public WxRole getUserRole();

	public WxRole getAdminRole();

}
