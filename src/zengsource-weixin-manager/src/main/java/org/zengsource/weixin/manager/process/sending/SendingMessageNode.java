/**
 * 
 */
package org.zengsource.weixin.manager.process.sending;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.node.ResponseMessages;
import org.zengsource.weixin.core.node.TextNode;
import org.zengsource.weixin.manager.WeixinProperties;
import org.zengsource.weixin.manager.domain.WxAccount;
import org.zengsource.weixin.manager.domain.WxMessage;
import org.zengsource.weixin.manager.domain.WxRole;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxAccountService;
import org.zengsource.weixin.manager.service.WxMessageService;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * 给用户发送消息。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public class SendingMessageNode extends TextNode implements ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

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
		Message out = in.replyText();
		// 检查用户
		WxUser admin = wxUserService.findByOpenId(in.getFromUserName());
		if (admin == null || !admin.hasRole(WxRole.ADMIN)) {
			out.setContent(ERROR_UNKNOWN_OPERATION);
			out.setType(Message.TYPE_ABORT);
			return out;
		}
		// 发送消息
		String[] arr = in.getContent().split(":");
		long id = NumberUtils.toLong(arr[0].substring(1), 0l);
		String message = arr[1];
		WxUser wxUser = wxUserService.findById(id);
		if (wxUser == null) {
			out.setContent("用户不存在！");
		} else if (wxUser.getSubscribe() == 0) {
			out.setContent("用户未关注！");
		} else { // 处理发送消息
			WxMessage wxMessage = new WxMessage();
			wxMessage.setFromUserName(in.getToUserName());
			wxMessage.setToUserName(wxUser.getOpenId());
			wxMessage.setMsgType(MSG_TYPE_TEXT);
			wxMessage.setContent(message);
			// 检查帐号
			WxAccount wxAccount = wxAccountService.findByOpenId(in.getToUserName());
			if (wxAccount != null && wxAccount.isCertificated() && // 认证帐号
					wxUserService.isOnline(wxUser)) { // 未超时
				wxMessageService.send(wxMessage, false);
				out.setContent("消息已发送。");
			} else { // 保存为未读消息
				wxMessage.setStatus(WxMessage.UNREAD);
				wxMessage = wxMessageService.create(wxMessage);
				out.setContent("消息已保存。");
			}
		}
		return out;
	}
	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
