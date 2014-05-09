/**
 * 
 */
package org.zengsource.weixin.manager.process.binding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.node.ResponseMessages;
import org.zengsource.weixin.manager.WeixinProperties;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.process.CommandNode;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class BindingAdminCommandNode extends CommandNode implements ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private WeixinProperties weixinProperties;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected Message onNumberReceived(Message in, int command) {
		Message out = in.replyText();
		WxUser wxUser = wxUserService.findByOpenId(in.getFromUserName());
		if (!wxUser.hasRole("admin")) { // 非管理员
			//logger.info("==> open  id " + wxUser.getOpenId());
			//logger.info("==> admin id " + weixinProperties.getAdminOpenId());
			if (wxUser.getOpenId().equals(weixinProperties.getAdminOpenId())) {
				// 管理员第一次接入系统
				wxUser.setPassword(weixinProperties.getAdminPwd());
				wxUser = wxUserService.changePassword(wxUser);
				wxUser = wxUserService.createAdmin(wxUser); // 创建管理员
				out.setContent("您成为了管理员！");
				logger.info("==> Admin created.");
			} else { // 已经有管理员，必须由管理员才有权限添加
				out.setContent(ERROR_UNKNOWN_OPERATION);
				out.setType(Message.TYPE_ABORT); // 中止流程
				logger.warn("==> Somebody hacked the admin code!!!");
			}
		} else { // 是管理员
			logger.info("==> Admin logging in.");
			out.setContent("您是管理员，请继续操作。");
		}
		return out;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
