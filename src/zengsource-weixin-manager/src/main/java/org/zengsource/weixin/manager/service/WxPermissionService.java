/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.Set;

import org.zengsource.weixin.manager.domain.WxPermission;
/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxPermissionService {

	public Set<WxPermission> getUserPermissions();

	public Set<WxPermission> getAdminPermissions();

}
