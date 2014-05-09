/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.List;

import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxUserService {

	/** 创建用户。 */
	public WxUser create(WxUser user);

	public WxUser update(WxUser wxUser);

	public WxUser setUsername(WxUser user);

	public WxUser changePassword(WxUser user);

	public WxUser changeEmail(WxUser user);

	public WxUser changeStatus(WxUser user);

	/** 验证用户身份：登录。 */
	public WxUser authenticate(WxUser user);

	/** 验证用户身份：检查密码。 */
	public WxUser authenticate(WxUser wxUser, String content);

	/** 已通过验证，必须在执行 authenticate(user) 之后执行。 */
	public boolean isAuthenticated(WxUser user);

	/** 注销。 */
	public void logout();

	/** 查找用户。 */
	public WxUser findById(long id);

	/** 查找用户。 */
	public WxUser findByUsername(String username);

	public WxUser findByOpenId(String openId);

	/** 当前登录用户。 */
	public WxUser getCurrent();

	/** 查找管理员列表。 */
	public List<WxUser> findAdmins();

	/** 查找一个管理员。 */
	public WxUser findOneAdmin();

	/** 将帐号设置为管理员。 */
	public WxUser createAdmin(WxUser user);

	/** 判断微信用户是否在线，即可接收客服消息。 */
	public boolean isOnline(WxUser admin);

}
