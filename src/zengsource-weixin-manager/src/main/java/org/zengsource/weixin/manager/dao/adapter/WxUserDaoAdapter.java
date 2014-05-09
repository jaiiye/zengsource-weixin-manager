/**
 * 
 */
package org.zengsource.weixin.manager.dao.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.dao.UserDao;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxUserDaoAdapter implements UserDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxUserService wxUserService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public User queryByOpenId(String openId) {
		WxUser wxUser = this.wxUserService.findByOpenId(openId);
		if (wxUser != null) {
			return wxUser.toUser();
		}
		return null;
	}

	@Override
	public User insert(User user) {
		WxUser wxUser = WxUser.from(user);
		wxUser = this.wxUserService.create(wxUser);
		if (wxUser != null) {
			return wxUser.toUser();
		}
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
