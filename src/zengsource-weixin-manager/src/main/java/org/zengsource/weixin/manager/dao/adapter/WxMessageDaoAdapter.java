/**
 * 
 */
package org.zengsource.weixin.manager.dao.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.dao.MessageDao;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.manager.domain.WxMessage;
import org.zengsource.weixin.manager.service.WxMessageService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxMessageDaoAdapter implements MessageDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxMessageService wxMessageService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Message queryByMsgId(Long msgId) {
		WxMessage wxMessage = wxMessageService.findByMsgId(msgId);
		if (wxMessage != null) {
			return wxMessage.toMessage();
		}
		return null;
	}

	@Override
	public Message insert(Message message) {
		if (message != null) {
			WxMessage wxMessage = WxMessage.from(message);
			wxMessage = this.wxMessageService.create(wxMessage);
			return wxMessage.toMessage();
		}
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
