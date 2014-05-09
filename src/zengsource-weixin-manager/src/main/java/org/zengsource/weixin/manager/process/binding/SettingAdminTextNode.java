/**
 * 
 */
package org.zengsource.weixin.manager.process.binding;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.node.ResponseMessages;
import org.zengsource.weixin.core.node.TextNode;
import org.zengsource.weixin.manager.domain.WxRole;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class SettingAdminTextNode extends TextNode implements ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	@Autowired
	private WxUserService wxUserService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected Message onTextReceived(Message in) {
		WxUser wxUser = wxUserService.findByOpenId(in.getFromUserName());
		Message out = in.replyText();
		if (wxUser.hasRole(WxRole.ADMIN)) {
			long id = NumberUtils.toLong(in.getContent(), 0l);
			WxUser newAdmin = wxUserService.findById(id);
			if (newAdmin == null) { // 用户不存在
				out.setContent(FAILURE);
			} else if (newAdmin.hasRole(WxRole.ADMIN)) {
				out.setContent(SUCCESS);
			} else {
				newAdmin = wxUserService.createAdmin(newAdmin);
				logger.info("==> " //
						+ wxUser.getNickname() + " add " //
						+ newAdmin.getNickname() + " as a new admin.");
				out.setContent(SUCCESS);
			}
		} else { // 出现问题
			out.setContent(ERROR_UNKNOWN_OPERATION);
		}
		return out;
	}
	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
