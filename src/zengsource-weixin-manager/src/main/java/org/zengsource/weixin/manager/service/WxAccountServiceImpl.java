/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.manager.WeixinProperties;
import org.zengsource.weixin.manager.dao.WxAccountDao;
import org.zengsource.weixin.manager.domain.WxAccount;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxAccountServiceImpl implements WxAccountService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxAccountDao wxAccountDao;
	@Autowired
	private WeixinProperties weixinProperties;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxAccount findByOpenId(String openId) {
		if (StringUtils.isNotEmpty(openId)) {
			WxAccount wxAccount = wxAccountDao.queryUnique("openId", openId);
			if (wxAccount == null && openId.equals(weixinProperties.getOpenid())) {
				// 将配置的帐号保存到数据库
				wxAccount = new WxAccount();
				wxAccount.setAppId(weixinProperties.getAppId());
				wxAccount.setAppSecret(weixinProperties.getAppSecret());
				wxAccount.setOpenId(weixinProperties.getOpenid());
				wxAccount.setType(weixinProperties.getType());
				wxAccount = wxAccountDao.insert(wxAccount);
			}
			return wxAccount;
		}
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxAccountDao getWxAccountDao() {
		return wxAccountDao;
	}

	public void setWxAccountDao(WxAccountDao wxAccountDao) {
		this.wxAccountDao = wxAccountDao;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
