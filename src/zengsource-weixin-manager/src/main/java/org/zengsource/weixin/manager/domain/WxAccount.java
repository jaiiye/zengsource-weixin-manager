/**
 * 
 */
package org.zengsource.weixin.manager.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.zengsource.weixin.core.domain.Account;

/**
 * 微信帐号
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_ACCOUNTS")
public class WxAccount implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static WxAccount from(Account account) {
		if (account != null) {
			WxAccount wxa = new WxAccount();
			wxa.setAppId(account.getAppId());
			wxa.setAppSecret(account.getAppSecret());
			wxa.setOpenId(account.getOpenId());
			wxa.setType(account.getType());
			return wxa;
		}
		return null;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "WxAccount_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "WxAccount_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WxAccount_Gen")
	@Column(name = "ID_")
	private long id;
	@Column(name = "APPID_")
	private String appId;
	@Column(name = "APPSECRET_")
	private String appSecret;
	@Column(name = "OPENID_")
	private String openId;
	@Column(name = "TYPE_")
	private Integer type = Account.UNCERTITICATED;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public boolean isCertificated() {
		return type != null && type == Account.CERTIFICATED;
	}

	public Account toAccount() {
		Account account = new Account();
		account.setAppId(appId);
		account.setAppSecret(appSecret);
		account.setOpenId(openId);
		account.setType(type == null ? Account.UNCERTITICATED : type.intValue());
		return account;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
