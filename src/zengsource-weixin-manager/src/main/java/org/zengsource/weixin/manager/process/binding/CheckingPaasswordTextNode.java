/**
 * 
 */
package org.zengsource.weixin.manager.process.binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.node.TextNode;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class CheckingPaasswordTextNode extends TextNode {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;
	private static final String ERROR_PASSWORD = "密码错误！";
	private static final String SUCCESS_PASSWORD = "密码正确。请注意删除本地消息。";

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxUserService wxUserService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected Message onTextReceived(Message in) {
		Message out = in.replyText();
		WxUser wxUser = wxUserService.findByOpenId(in.getFromUserName());
		// 检查密码
		WxUser checked = wxUserService.authenticate(wxUser, in.getContent());
		if (checked == null) { // 密码不匹配
			out.setType(Message.TYPE_ERROR);
			out.setContent(ERROR_PASSWORD);
		} else { // 密码正确
			out.setContent(SUCCESS_PASSWORD);
		}
		return out;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
