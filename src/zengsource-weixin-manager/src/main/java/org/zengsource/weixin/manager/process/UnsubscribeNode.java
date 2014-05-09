/**
 * 
 */
package org.zengsource.weixin.manager.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.core.node.EventNode;
import org.zengsource.weixin.manager.WeixinProperties;
import org.zengsource.weixin.manager.domain.WxMessage;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxMessageService;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class UnsubscribeNode extends EventNode {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private WxMessageService wxMessageService;
	@Autowired
	private WeixinProperties weixinProperties;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected Message onUnsubscribe(Message in) {
		// 转发给管理员
		WxUser admin = wxUserService.findOneAdmin();
		if (admin != null) {
			WxUser fromUser = wxUserService.findByOpenId(in.getFromUserName());
			fromUser.setSubscribe(User.UNSUBSCRIBE);
			fromUser = wxUserService.update(fromUser);
			WxMessage wxMessage = new WxMessage();
			wxMessage.setFromUserName(in.getToUserName());
			wxMessage.setToUserName(admin.getOpenId());
			wxMessage.setMsgType(MSG_TYPE_TEXT);
			wxMessage.setContent("用户取消关注 -> " //
					+ "[" + fromUser.getId() + "] " + fromUser.getNickname());
			if (weixinProperties.isCertificated() && wxUserService.isOnline(admin)) {
				// 直接发送客服消息
				wxMessageService.send(wxMessage, false);
			} else { // 不发送，保存为未读消息
				wxMessage.setStatus(WxMessage.UNREAD);
				wxMessage = wxMessageService.create(wxMessage);
			}
		}
		// 返回空消息
		Message out = in.replyText();
		out.setContent(INFO_FAREWELL_UNSUBSTRIBE);
		return out;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
