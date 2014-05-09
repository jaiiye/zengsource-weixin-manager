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

import org.zengsource.weixin.core.domain.Article;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_MESSAGEARTCILES")
public class WxMessageArticle implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static WxMessageArticle from(Article article) {
		WxMessageArticle wxma = new WxMessageArticle();
		wxma.setUrl(article.getUrl());
		wxma.setTitle(article.getTitle());
		wxma.setPicUrl(article.getPicUrl());
		wxma.setDescription(article.getDescription());
		return wxma;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "WxMsgArticle_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "WxMsgArticle_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WxMsgArticle_Gen")
	@Column(name = "ID_")
	private long id;
	@ManyToOne
	@JoinColumn(name = "MESSAGEID_")
	private WxMessage wxMessage;
	@Column(name = "INDEX_")
	private Integer index;
	@Column(name = "TITLE_")
	private String title;
	@Column(name = "DESCRIPTION_")
	private String description;
	@Column(name = "PICURL_")
	private String picUrl;
	@Column(name = "URL_")
	private String url;
	@Column(name = "CREATEDTIME_")
	private Date createdTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxMessageArticle() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public WxMessage getWxMessage() {
		return wxMessage;
	}
	
	public void setWxMessage(WxMessage wxMessage) {
		this.wxMessage = wxMessage;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
