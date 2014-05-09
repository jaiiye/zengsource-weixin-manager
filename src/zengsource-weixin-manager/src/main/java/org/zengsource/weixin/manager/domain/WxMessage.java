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

import org.zengsource.weixin.core.domain.Article;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.Parameters;

/**
 * 保存微信进出的消息记录。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
@Entity
@Table(name = "WX_MESSAGES")
public class WxMessage implements Serializable, Parameters {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static final int UNREAD = -1;
	public static final int READ = 0;

	public static WxMessage from(Message message) {
		WxMessage wxMessage = new WxMessage();
		wxMessage.setFromUserName(message.getFromUserName());
		wxMessage.setToUserName(message.getToUserName());
		wxMessage.setMsgType(message.getMsgType());
		wxMessage.setCreateTime(message.getCreateTime());
		if (MSG_TYPE_EVENT.equals(message.getMsgType())) { // 事件
			wxMessage.setEvent(message.getEvent());
			if (EVENT_CLICK.equals(message.getEvent())) {
				wxMessage.setEventKey(message.getEventKey());
			}
		} else { // 普通消息
			wxMessage.setMsgId(message.getMsgId());
			if (MSG_TYPE_TEXT.equals(message.getMsgType())) {
				wxMessage.setContent(message.getContent());
			} else if (MSG_TYPE_IMAGE.equals(message.getMsgType())) {
				wxMessage.setPictureUrl(message.getPictureUrl());
				wxMessage.setMediaId(message.getMediaId());
				wxMessage.setMsgId(message.getMsgId());
			} else if (MSG_TYPE_VOICE.equals(message.getMsgType())) {
				wxMessage.setMediaId(message.getMediaId());
				wxMessage.setFormat(message.getFormat());
			} else if (MSG_TYPE_VIDEO.equals(message.getMsgType())) {
				wxMessage.setMediaId(message.getMediaId());
				wxMessage.setThumbMediaId(message.getThumbMediaId());
			} else if (MSG_TYPE_LOCATION.equals(message.getMsgType())) {
				wxMessage.setLocationX(message.getLocationX());
				wxMessage.setLocationY(message.getLocationY());
				wxMessage.setScale(message.getScale());
				wxMessage.setLabel(message.getLabel());
			} else if (MSG_TYPE_LINK.equals(message.getMsgType())) {
				wxMessage.setTitle(message.getTitle());
				wxMessage.setDescription(message.getDescription());
				wxMessage.setUrl(message.getUrl());
			} else if (MSG_TYPE_MUSIC.equals(message.getMsgType())) {
				wxMessage.setTitle(message.getTitle());
				wxMessage.setDescription(message.getDescription());
				wxMessage.setMusicUrl(message.getMusicUrl());
				wxMessage.setHqMusicUrl(message.getHqMusicUrl());
				wxMessage.setThumbMediaId(message.getThumbMediaId());
			} else if (MSG_TYPE_NEWS.equals(message.getMsgType())) {
				if (message.getArticles() != null) {
					for (Article article : message.getArticles()) {
						WxMessageArticle wxma = WxMessageArticle.from(article);
						wxMessage.addArticle(wxma);
					}
				}
			}
			// Send Message
			wxMessage.setResponseStatus(message.getResponseStatus());
			wxMessage.setResponseMime(message.getResponseMime());
			wxMessage.setResponseText(message.getResponseText());
			wxMessage.setErrorCode(message.getErrorCode());
			// Local message
			wxMessage.setType(message.getType());
		}
		return wxMessage;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@TableGenerator( //
	name = "WxMessage_Gen", table = "ID_GEN", //
	pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", //
	pkColumnValue = "WxMessage_Gen", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WxMessage_Gen")
	@Column(name = "ID_")
	private long id;

	// 本地数据
	@Column(name = "TYPE_")
	private Integer type = Message.TYPE_NORMAL;
	@Column(name = "STATUS_")
	private Integer status = READ;

	// 微信数据
	@Column(name = "TOUSERNAME_")
	private String toUserName;
	@Column(name = "FROMUSERNAME_")
	private String fromUserName;
	@Column(name = "CREATETIME_")
	private Long createTime;
	@Column(name = "MSGTYPE_")
	private String msgType;
	// 文本消息
	@Column(name = "CONTENT_")
	private String content;
	@Column(name = "MSGID_")
	private Long msgId;
	// 事件推送
	@Column(name = "EVENT_")
	private String event;
	@Column(name = "EVENTKEY_")
	private String eventKey;
	// 图片消息
	@Column(name = "MEDIAID_")
	private String mediaId;
	@Column(name = "PICTUREURL_")
	private String pictureUrl;
	// 语音
	@Column(name = "FORMAT_")
	private String format;
	// 视频
	@Column(name = "THUMBMEDIAID_")
	private String thumbMediaId;
	// 位置
	@Column(name = "LOCATIONX_")
	private Float locationX;
	@Column(name = "LOCATIONY_")
	private Float locationY;
	@Column(name = "SCALE_")
	private Integer scale = 1;
	@Column(name = "LABEL_")
	private String label;
	// 链接
	@Column(name = "TITLE_")
	private String title;
	@Column(name = "DESCRIPTION_")
	private String description;
	@Column(name = "URL_")
	private String url;
	// 扫描二维码事件
	@Column(name = "TICKET_")
	private String ticket;
	// 上报地理位置
	@Column(name = "LATITUDE_")
	private Float latitude;
	@Column(name = "LONGITUDE_")
	private Float longitude;
	@Column(name = "PRECISION_")
	private Float precision;
	// 回复音乐消息
	@Column(name = "MUSICURL_")
	private String musicUrl;
	@Column(name = "HQMUSICURL_")
	private String hqMusicUrl;
	// 回复图文消息
	@OneToMany(mappedBy = "wxMessage")
	@OrderBy("index ASC")
	private List<WxMessageArticle> articles;
	// 发送消息
	@Column(name = "RESPONSESTATUS_")
	private Integer responseStatus;
	@Column(name = "RESPONSEMIME_")
	private String responseMime;
	@Column(name = "RESPONSETEXT_")
	private String responseText;
	@Column(name = "ERRORCODE_")
	private Integer errorCode;

	@Column(name = "CREATEDTIME_")
	private Date createdTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxMessage() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public Message toMessage() {
		Message message = new Message();
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		message.setMsgType(msgType);
		message.setCreateTime(createTime);
		if (MSG_TYPE_EVENT.equals(msgType)) { // 事件
			message.setEvent(event);
			message.setEventKey(eventKey);
			if (EVENT_SUBSCRIBE.equals(message.getEvent())) {
				message.setTicket(ticket);
			} else if (EVENT_SCAN.equals(message.getEvent())) {
				message.setTicket(ticket);
			} else if (EVENT_LOCATION.equals(message.getEvent())) {
				message.setLatitude(latitude);
				message.setLongitude(longitude);
				message.setPrecision(precision);
			}
		} else { // 普通消息
			message.setMsgId(msgId);
			if (MSG_TYPE_TEXT.equals(message.getMsgType())) {
				message.setContent(content);
			} else if (MSG_TYPE_IMAGE.equals(message.getMsgType())) {
				message.setPictureUrl(pictureUrl);
				message.setMediaId(mediaId);
			} else if (MSG_TYPE_VOICE.equals(message.getMsgType())) {
				message.setMediaId(mediaId);
				message.setFormat(format);
			} else if (MSG_TYPE_VIDEO.equals(message.getMsgType())) {
				message.setMediaId(mediaId);
				message.setThumbMediaId(thumbMediaId);
			} else if (MSG_TYPE_LOCATION.equals(message.getMsgType())) {
				message.setLocationX(locationX);
				message.setLocationY(locationY);
				message.setScale(scale);
				message.setLabel(label);
			} else if (MSG_TYPE_LINK.equals(message.getMsgType())) {
				message.setTitle(title);
				message.setDescription(description);
				message.setUrl(url);
			} else if (MSG_TYPE_MUSIC.equals(message.getMsgType())) {
				message.setTitle(title);
				message.setDescription(description);
				message.setMusicUrl(musicUrl);
				message.setHqMusicUrl(hqMusicUrl);
				message.setThumbMediaId(thumbMediaId);
			} else if (MSG_TYPE_NEWS.equals(message.getMsgType())) {
				for (WxMessageArticle wxma : articles) {
					Article article = new Article();
					article.setUrl(wxma.getUrl());
					article.setTitle(wxma.getTitle());
					article.setPicUrl(wxma.getPicUrl());
					article.setDescription(wxma.getDescription());
					message.addArticle(article);
				}
			}
		}
		message.setResponseStatus(responseStatus);
		message.setResponseMime(responseMime);
		message.setResponseText(responseText);
		message.setErrorCode(errorCode);
		return message;
	}

	public void addArticle(WxMessageArticle article) {
		if (articles == null) {
			articles = new ArrayList<>();
		}
		article.setIndex(articles.size() - 1);
		articles.add(article);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
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

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public Float getLocationX() {
		return locationX;
	}

	public void setLocationX(Float locationX) {
		this.locationX = locationX;
	}

	public Float getLocationY() {
		return locationY;
	}

	public void setLocationY(Float locationY) {
		this.locationY = locationY;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getPrecision() {
		return precision;
	}

	public void setPrecision(Float precision) {
		this.precision = precision;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public List<WxMessageArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<WxMessageArticle> articles) {
		this.articles = articles;
	}

	public Integer getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Integer responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseMime() {
		return responseMime;
	}

	public void setResponseMime(String responseMime) {
		this.responseMime = responseMime;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
