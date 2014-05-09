/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.manager.dao.WxRoleDao;
import org.zengsource.weixin.manager.domain.WxRole;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxRoleServiceImpl implements WxRoleService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxRoleDao wxRoleDao;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxRole getUserRole() {
		WxRole role = wxRoleDao.queryUnique("code", WxRole.USER);
		if (role == null) {
			role = new WxRole();
			role.setName("用户");
			role.setCode(WxRole.USER);
			role = wxRoleDao.insert(role);
		}
		return role;
	}

	@Override
	public WxRole getAdminRole() {
		WxRole role = wxRoleDao.queryUnique("code", WxRole.ADMIN);
		if (role == null) {
			role = new WxRole();
			role.setName("管理员");
			role.setCode(WxRole.ADMIN);
			role.setParent(getUserRole());
			role = wxRoleDao.insert(role);
		}
		return role;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
