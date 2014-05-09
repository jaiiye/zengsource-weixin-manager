/**
 * 
 */
package org.zengsource.weixin.manager.process.receiving;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.util.DateUtils;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.core.node.ResponseMessages;
import org.zengsource.weixin.core.node.TextNode;
import org.zengsource.weixin.manager.WeixinProperties;
import org.zengsource.weixin.manager.domain.WxAccount;
import org.zengsource.weixin.manager.domain.WxMessage;
import org.zengsource.weixin.manager.domain.WxMessageArticle;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxAccountService;
import org.zengsource.weixin.manager.service.WxMessageService;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * 接收用户消息。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ReceivingMessageNode extends TextNode implements ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private WxMessageService wxMessageService;
	@Autowired
	private WeixinProperties weixinProperties;
	@Autowired
	private WxAccountService wxAccountService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected Message onTextReceived(Message in) {
		boolean isAdmin = weixinProperties.getAdminOpenId().equals(in.getFromUserName());
		if (isAdmin) {// 管理员发送的消息忽略，仅为了查询消息
			logger.info("==> Admin管理员查看消息。");
		} else { // 接收粉丝发送的消息
			WxUser fromUser = wxUserService.findByOpenId(in.getFromUserName());
			if (fromUser.getSubscribe() == User.UNSUBSCRIBE) {
				fromUser.setSubscribe(User.SUBSCRIBE);
				fromUser = wxUserService.update(fromUser);
				logger.info("==> Update user with subscribe.");
			}
			WxMessage wxMessage = new WxMessage();
			wxMessage.setFromUserName(in.getToUserName());
			wxMessage.setToUserName(weixinProperties.getAdminOpenId());
			wxMessage.setMsgType(MSG_TYPE_TEXT);
			wxMessage.setContent("" //
					+ "[" + fromUser.getId() + "] " // 用户本地ID
					+ (StringUtils.isEmpty(fromUser.getNickname()) ? "未知" // 未认证帐号查不到
							: fromUser.getNickname()) + " - " // 用户昵称
					+ in.getContent()); // 用户发送的消息
			// 检查是否为认证帐号，可以通过客服消息转发给管理员
			WxAccount wxAccount = wxAccountService.findByOpenId(in.getToUserName());
			WxUser admin = wxUserService.findByOpenId(weixinProperties.getAdminOpenId());
			if (admin != null // 直接发送客服消息给管理员
					&& wxAccount != null && wxAccount.isCertificated() // 认证帐号
					&& wxUserService.isOnline(admin)) { // 客服在线
				wxMessageService.send(wxMessage, false);
			} else { // 不发送，保存为未读消息
				wxMessage.setStatus(WxMessage.UNREAD);
				wxMessage = wxMessageService.create(wxMessage);
			}
		}
		// 查询并返回未读消息
		Message out = null;
		List<WxMessage> messages = wxMessageService.findUnread(in.getFromUserName());
		logger.info("==> 未读消息  " + messages == null ? 0 : messages.size());
		if (messages == null || messages.size() == 0) { // 没有未读消息
			out = in.replyText();
			if (isAdmin) {
				out.setContent("没有未读消息。");
			} else {
				out.setContent("谢谢留言。如有必要回复，我会在明天回复你。明天发送任意消息就可以查看我的回复。");
			}
		} else if (messages.size() == 1) { // 只有一条消息
			out = in.replyText();
			WxMessage wxm = messages.get(0);
			out.setContent(wxm.getContent() + "\n" //
					+ DateUtils.format(wxm.getCreatedTime(), DateUtils.FULL_CN3));
			// 修改消息状态为已读
			wxm.setStatus(WxMessage.READ);
			wxMessageService.update(wxm);
		} else { // 多条消息
			WxMessage news = WxMessage.from(in.replyNews());
			// 标题文章
			WxMessageArticle article = new WxMessageArticle();
			if (isAdmin) {
				article.setTitle("新消息");
			} else {
				article.setTitle("我的回复");
			}
			article.setPicUrl(weixinProperties.getPicUrl());
			article.setUrl(weixinProperties.getPicUrl());
			// article.setDescription("下面列表出了最新 " + notices.size() + " 条通知。");
			news.addArticle(article); // 添加标题文章
			for (WxMessage wxm : messages) {
				article = new WxMessageArticle();
				article.setTitle(wxm.getContent() + " " //
						+ DateUtils.format(wxm.getCreatedTime(), DateUtils.FULL_CN3));
				article.setPicUrl(weixinProperties.getPicUrl());
				article.setUrl(weixinProperties.getPicUrl());
				// article.setDescription("");
				news.addArticle(article); // 添加第n篇文章
				// 修改消息状态为已读
				wxm.setStatus(WxMessage.READ);
				wxMessageService.update(wxm);
			}
			out = news.toMessage();
		}

		return out;
	}
	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
