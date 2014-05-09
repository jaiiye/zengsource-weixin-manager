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
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 权限。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_PERMISSIONS")
public class WxPermission implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "Permission_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "Permission_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Permission_Gen")
	@Column(name = "UID_")
	private long id;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "CODE_")
	private String code;
	@ManyToMany
	@JoinTable( //
	name = "WX_ROLE_PERMISSION", //
	joinColumns = @JoinColumn(name = "PERMISSIONID_"), //
	inverseJoinColumns = @JoinColumn(name = "ROLEID_"))
	private Set<WxRole> roles;
	@Column(name = "CREATEDTIME_")
	private Date createdTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxPermission() {
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

	public Set<WxRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<WxRole> roles) {
		this.roles = roles;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
