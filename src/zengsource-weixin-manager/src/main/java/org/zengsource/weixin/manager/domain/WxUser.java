/**
 * 
 */
package org.zengsource.weixin.manager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.zengsource.weixin.core.domain.User;

/**
 * 用户通过微信交互的会话。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_USERS")
public class WxUser implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static WxUser from(User user) {
		WxUser wxUser = new WxUser();
		wxUser.setSubscribeTime(user.getSubscribeTime());
		wxUser.setSubscribe(user.getSubscribe());
		wxUser.setHeadImage(user.getHeadImage());
		wxUser.setNickname(user.getNickname());
		wxUser.setOpenId(user.getOpenId());
		wxUser.setSex(user.getSex());
		return wxUser;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "WxUser_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "WxUser_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WxUser_Gen")
	@Column(name = "UID_")
	private long id;
	// for login
	@Column(name = "USERNAME_", nullable = false)
	private String username;
	@Column(name = "PASSWORD_", nullable = true)
	private String password;
	@Column(name = "EMAIL_", nullable = true)
	private String email;
	@Column(name = "SALT_")
	private String salt;
	@ManyToMany
	@JoinTable( //
	name = "WX_USER_ROLE", //
	joinColumns = @JoinColumn(name = "USERID_"), //
	inverseJoinColumns = @JoinColumn(name = "ROLEID_"))
	private Set<WxRole> roles;
	@Column(name = "STATUS_")
	private Integer status = 0;
	// for weixin
	@Column(name = "OPENID_", nullable = false)
	private String openId;
	@Column(name = "SUBSCRIBE_")
	private Integer subscribe = 0;
	@Column(name = "NICKNAME_")
	private String nickname;
	@Column(name = "SEX_")
	private Integer sex = 0;
	@Column(name = "SUBSCRIBETIME_")
	private String subscribeTime;
	@Column(name = "HEADIMAGE_")
	private String headImage;
	@Column(name = "UPDATEDTIME_")
	private Date updatedTime;
	@Column(name = "CREATEDTIME_")
	private Date createdTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxUser() {
	}

	public WxUser(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public boolean hasNotSubscribed() {
		return !hasSubscribed();
	}

	public boolean hasSubscribed() {
		return subscribe != null && subscribe == 1 ? true : false;
	}

	public User toUser() {
		User user = new User();
		user.setSubscribeTime(subscribeTime);
		user.setSubscribe(subscribe);
		user.setHeadImage(headImage);
		user.setNickname(nickname);
		user.setOpenId(openId);
		user.setSex(sex);
		return user;
	}

	public boolean hasRole(String roleName) {
		if (roles != null) {
			for (WxRole role : roles) {
				if (role.getName().equals(roleName) || role.getCode().equals(roleName)) {
					return true;
				}
			}
		}
		return false;
	}

	public void addRole(WxRole role) {
		if (roles == null) {
			roles = new HashSet<>();
		}
		roles.add(role);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
