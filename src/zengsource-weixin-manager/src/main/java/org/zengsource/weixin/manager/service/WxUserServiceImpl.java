/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zengsource.util.DateUtils;
import org.zengsource.weixin.manager.WeixinProperties;
import org.zengsource.weixin.manager.dao.WxUserDao;
import org.zengsource.weixin.manager.domain.WxMessage;
import org.zengsource.weixin.manager.domain.WxRole;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxUserServiceImpl implements WxUserService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxUserDao wxUserDao;

	@Autowired
	private WxRoleService wxRoleService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private WeixinProperties weixinProperties;

	@Autowired
	private WxMessageService wxMessageService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxUser findByOpenId(String openId) {
		return this.wxUserDao.queryUnique("openId", openId);
	}

	@Override
	public WxUser create(WxUser wxUser) {
		if (StringUtils.isNotEmpty(wxUser.getPassword())) {
			wxUser.setCreatedTime(new Date());
			wxUser.setPassword(this.hashPassword(wxUser));
		}
		if (StringUtils.isEmpty(wxUser.getUsername())) {
			wxUser.setUsername(wxUser.getOpenId());
		}
		return this.wxUserDao.insert(wxUser);
	}

	@Override
	public WxUser update(WxUser wxUser) {
		if (wxUser != null) {
			return wxUserDao.update(wxUser);
		}
		return null;
	}

	private String hashPassword(WxUser wxUser) {
		if (wxUser.getPassword() == null) {
			throw new RuntimeException("No Password Yet!");
		}
		if (wxUser.getSalt() == null) {
			wxUser.setSalt(DateUtils.format(wxUser.getCreatedTime(), DateUtils.yyyyMMddHHmmssSSS));
		}
		return this.passwordEncoder.encode(wxUser.getPassword() + wxUser.getSalt());
	}

	@Override
	public WxUser setUsername(WxUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WxUser changePassword(WxUser user) {
		if (StringUtils.isBlank(user.getPassword())) {
			return null;
		}
		if (user.getCreatedTime() == null) {
			user.setCreatedTime(new Date());
		}
		user.setPassword(this.hashPassword(user));
		return wxUserDao.update(user);
	}

	@Override
	public WxUser changeEmail(WxUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WxUser changeStatus(WxUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WxUser authenticate(WxUser user) {
		WxUser dbUser = this.findByUsername(user.getUsername());
		if (dbUser == null) {
			return null;
		}
		if (!this.matchesPassword(user, dbUser)) {
			return null; // 密码不匹配
		}
		return dbUser;
	}

	@Override
	public WxUser authenticate(WxUser wxUser, String password) {
		if (wxUser != null) {
			WxUser rawUser = new WxUser(wxUser.getUsername(), password);
			if (this.matchesPassword(rawUser, wxUser)) {
				return wxUser;
			}
		}
		return null;
	}

	private boolean matchesPassword(WxUser rawUser, WxUser encUser) {
		String rawPassword = rawUser.getPassword() + encUser.getSalt();
		return this.passwordEncoder.matches(rawPassword, encUser.getPassword());
	}

	@Override
	public boolean isAuthenticated(WxUser user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public WxUser findById(long id) {
		return wxUserDao.queryById(id);
	}

	@Override
	public WxUser findByUsername(String username) {
		if (StringUtils.isNotEmpty(username)) {
			return wxUserDao.queryUnique("username", username);
		}
		return null;
	}

	@Override
	public WxUser getCurrent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WxUser> findAdmins() {
		WxRole role = wxRoleService.getAdminRole();
		return wxUserDao.queryByRole(role);
	}

	@Override
	public WxUser findOneAdmin() {
		List<WxUser> admins = findAdmins();
		if (admins != null && admins.size() > 0) {
			return admins.get(0); // TODO 选择管理员
		}
		return null;
	}

	@Override
	public WxUser createAdmin(WxUser user) {
		WxRole role = wxRoleService.getAdminRole();
		user.addRole(role);
		return wxUserDao.update(user);
	}

	@Override
	public boolean isOnline(WxUser user) {
		int hours = weixinProperties.getOnlineHours();
		WxMessage lastMessage = wxMessageService.findLast(user);
		if (lastMessage != null) {
			long now = System.currentTimeMillis();
			if (now - lastMessage.getCreatedTime().getTime() < hours * 3600000) {
				return true;
			}
		}
		return false;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
