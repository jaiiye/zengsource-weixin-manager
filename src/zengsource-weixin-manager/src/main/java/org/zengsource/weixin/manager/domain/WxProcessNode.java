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

import org.zengsource.weixin.core.domain.Node;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_PROCESSNODES")
public class WxProcessNode implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static WxProcessNode from(Node node) {
		WxProcessNode wxpn = new WxProcessNode();
		wxpn.setName(node.getName());
		wxpn.setClazz(node.getClazz());
		wxpn.setIndex(node.getIndex());
		wxpn.setPrompt(node.getPrompt());
		wxpn.setDescription(node.getDescription());
		wxpn.setMsgType(node.getMsgType());
		wxpn.setMsgContent(node.getMsgContent());
		wxpn.setEvent(node.getEvent());
		wxpn.setEventKey(node.getEventKey());
		return wxpn;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "WxProcNode_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "WxProcNode_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WxProcNode_Gen")
	@Column(name = "ID_")
	private long id;
	@ManyToOne
	@JoinColumn(name = "PROCESSID_")
	private WxProcessDefinition wxProcessDefinition;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "CLAZZ_")
	private String clazz;
	@Column(name = "INDEX_")
	private Integer index;
	@Column(name = "DESCRIPTION_")
	private String description;

	@Column(name = "MSGTYPE_")
	private String msgType;
	@Column(name = "MSGCONTENT_")
	private String msgContent;
	@Column(name = "EVENT_")
	private String event;
	@Column(name = "EVENTKEY_")
	private String eventKey;
	
	@Column(name = "PROMPT_")
	private String prompt;

	@Column(name = "CREATEDTIME_")
	private Date createdTime;
	@Column(name = "UPDATEDTIME_")
	private Date updatedTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	public String getPrompt() {
		return prompt;
	}
	
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public WxProcessDefinition getWxProcessDefinition() {
		return wxProcessDefinition;
	}

	public void setWxProcessDefinition(WxProcessDefinition wxProcessDefinition) {
		this.wxProcessDefinition = wxProcessDefinition;
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
