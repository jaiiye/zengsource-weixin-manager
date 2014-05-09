/**
 * 
 */
package org.zengsource.weixin.manager.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.core.domain.Node;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_PROCESSDEFINITIONS")
public class WxProcessDefinition implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static WxProcessDefinition from(Definition definition) {
		WxProcessDefinition wxpd = new WxProcessDefinition();
		wxpd.setName(definition.getName());
		wxpd.setIndex(definition.getIndex());
		wxpd.setDescription(definition.getDescription());
		for (Node node : definition.getNodes()) {
			wxpd.addNode(WxProcessNode.from(node));
		}
		return wxpd;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "WxProcDef_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "WxProcDef_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WxProcDef_Gen")
	@Column(name = "ID_")
	private long id;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "INDEX_")
	private Integer index;
	@Column(name = "DESCRIPTION_")
	private String description;
	@OneToMany(mappedBy = "wxProcessDefinition")
	@OrderBy("index ASC")
	private List<WxProcessNode> nodes;
	@Column(name = "CREATEDTIME_")
	private Date createdTime;
	@Column(name = "UPDATEDTIME_")
	private Date updatedTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxProcessDefinition() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public void addNode(WxProcessNode node) {
		if (nodes == null) {
			nodes = new ArrayList<>();
		}
		nodes.add(node);
	}

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

	public List<WxProcessNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<WxProcessNode> nodes) {
		this.nodes = nodes;
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
