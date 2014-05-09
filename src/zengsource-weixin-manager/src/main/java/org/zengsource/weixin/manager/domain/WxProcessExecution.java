/**
 * 
 */
package org.zengsource.weixin.manager.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import org.zengsource.weixin.core.domain.Execution;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_PROCESSEXECUTIONS")
public class WxProcessExecution implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static WxProcessExecution from(Execution e) {
		WxProcessExecution wxpe = new WxProcessExecution();
		Execution execution = (Execution) e;
		wxpe.setWxUser(WxUser.from(execution.getUser()));
		wxpe.setWxProcessDefinition(WxProcessDefinition.from(execution.getDefinition()));
		wxpe.setCurrent(execution.getCurrent());
		return wxpe;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "WxProcExec_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "WxProcExec_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WxProcExec_Gen")
	@Column(name = "ID_")
	private long id;
	@ManyToOne
	@JoinColumn(name = "WXUSERID_")
	private WxUser wxUser;
	@ManyToOne
	@JoinColumn(name = "WXPRODEFID_")
	private WxProcessDefinition wxProcessDefinition;
	@Column(name = "CURRENT_")
	private int current;

	@Column(name = "CREATEDTIME_")
	private Date createdTime;
	@Column(name = "UPDATEDTIME_")
	private Date updatedTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxProcessExecution() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public WxUser getWxUser() {
		return wxUser;
	}

	public void setWxUser(WxUser wxUser) {
		this.wxUser = wxUser;
	}

	public WxProcessDefinition getWxProcessDefinition() {
		return wxProcessDefinition;
	}

	public void setWxProcessDefinition(WxProcessDefinition wxProcessDefinition) {
		this.wxProcessDefinition = wxProcessDefinition;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
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
