/**
 * 
 */
package org.zengsource.weixin.manager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 角色。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_ROLES")
public class WxRole implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static final String USER = "user";
	public static final String ADMIN = "admin";

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "Role_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "Role_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Role_Gen")
	@Column(name = "UID_")
	private long id;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "CODE_")
	private String code;
	@ManyToOne
	@JoinColumn(name = "PARENTID_")
	private WxRole parent;
	@ManyToMany
	@JoinTable( //
	name = "WX_USER_ROLE", //
	joinColumns = @JoinColumn(name = "ROLEID_"), //
	inverseJoinColumns = @JoinColumn(name = "USERID_"))
	private Set<WxUser> users;
	@ManyToMany
	@JoinTable( //
	name = "WX_ROLE_PERMISSION", //
	joinColumns = @JoinColumn(name = "ROLEID_"), //
	inverseJoinColumns = @JoinColumn(name = "PERMISSIONID_"))
	private Set<WxPermission> wxPermissions;
	@ManyToOne
	@JoinColumn(name = "AUTHORID_")
	private WxUser author;
	@Column(name = "CREATEDTIME_")
	private Date createdTime;
	@Column(name = "UPDATEDTIME_")
	private Date updatedTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxRole() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public WxRole getParent() {
		return parent;
	}

	public void setParent(WxRole parent) {
		this.parent = parent;
	}

	public Set<WxUser> getUsers() {
		return users;
	}

	public void setUsers(Set<WxUser> users) {
		this.users = users;
	}

	public Set<WxPermission> getPermissions() {
		return wxPermissions;
	}

	public void setPermissions(Set<WxPermission> wxPermissions) {
		this.wxPermissions = wxPermissions;
	}

	public WxUser getAuthor() {
		return author;
	}

	public void setAuthor(WxUser author) {
		this.author = author;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
