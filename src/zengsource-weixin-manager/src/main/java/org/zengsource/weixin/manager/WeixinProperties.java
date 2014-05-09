/**
 * 
 */
package org.zengsource.weixin.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.zengsource.weixin.core.domain.Account;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Configuration
@PropertySource("classpath:weixin.properties")
public class WeixinProperties {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Value("${weixin.appid}")
	private String appId = "wx39acbbc727a861eb";
	@Value("${weixin.appsecret}")
	private String appSecret = "76f8c0585cbff7d8e146dd514311c61a";
	@Value("${weixin.customer.online}")
	private Integer onlineHours;
	@Value("${weixin.type}")
	private Integer type = Account.UNCERTITICATED;
	@Value("${weixin.openid}")
	private String openid;
	@Value("${weixin.adminid}")
	private String adminOpenId;
	@Value("${weixin.adminpwd}")
	private String adminPwd;
	@Value("${weixin.picurl}")
	private String picUrl;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public boolean isCertificated() {
		return getType() == Account.UNCERTITICATED;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Integer getOnlineHours() {
		return onlineHours;
	}

	public void setOnlineHours(Integer onlineHours) {
		this.onlineHours = onlineHours;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer certificated) {
		this.type = certificated;
	}

	public String getAdminOpenId() {
		return adminOpenId;
	}

	public void setAdminOpenId(String adminOpenId) {
		this.adminOpenId = adminOpenId;
	}
	
	public String getAdminPwd() {
		return adminPwd;
	}
	
	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
