/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.service.CustomerMessager;
import org.zengsource.weixin.manager.WeixinProperties;
import org.zengsource.weixin.manager.dao.WxMessageDao;
import org.zengsource.weixin.manager.domain.WxMessage;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxMessageServiceImpl implements WxMessageService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxMessageDao wxMessageDao;
	@Autowired
	private WeixinProperties weixinProperties;
	@Autowired
	private ConcurrencyService concurrencyService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxMessage create(WxMessage wxMessage) {
		if (wxMessage != null) {
			if (wxMessage.getCreateTime() == null || wxMessage.getCreateTime() == 0) {
				wxMessage.setCreateTime((new Date().getTime()));
			}
			return wxMessageDao.insert(wxMessage);
		}
		return null;
	}

	@Override
	public WxMessage findByMsgId(Long msgId) {
		return this.wxMessageDao.queryUnique("msgId", msgId);
	}

	@Override
	public WxMessage findDuplicated(WxMessage wxMessage) {
		if (wxMessage != null) {
			WxMessage duplicated = this.wxMessageDao.queryDuplicated(wxMessage);
			if (wxMessage.getCreateTime() - duplicated.getCreateTime() > 5000 // TODO
					&& wxMessage.getCreateTime() - duplicated.getCreateTime() < 6000) {
				return duplicated; // 判定为重发消息
			}
		}
		return null;
	}

	@Override
	public WxMessage findLast(WxUser user) {
		List<WxMessage> messages = wxMessageDao.queryLast(user.getOpenId(), 0, 1);
		return messages != null && messages.size() == 1 ? messages.get(0) : null;
	}

	@Override
	public List<WxMessage> findUnread(String toUserName) {
		return wxMessageDao.queryByTarget(toUserName, WxMessage.UNREAD, 0, 9);
	}

	@Override
	public WxMessage update(WxMessage wxMessage) {
		if (wxMessage != null) {
			return wxMessageDao.update(wxMessage);
		}
		return null;
	}

	@Override
	public WxMessage send(final WxMessage wxMessage, boolean async) {
		if (async) {
			concurrencyService.run(new Runnable() {
				@Override
				public void run() {
					sendSync(wxMessage);
				}
			});
		} else {
			sendSync(wxMessage);
		}
		return wxMessage;
	}

	private WxMessage sendSync(WxMessage wxMessage) {
		wxMessage = create(wxMessage);
		Message message = CustomerMessager.getInstance().send( //
				wxMessage.toMessage(), //
				weixinProperties.getAppId(), //
				weixinProperties.getAppSecret());
		wxMessage.setResponseStatus(message.getResponseStatus());
		wxMessage.setResponseMime(message.getResponseMime());
		wxMessage.setResponseText(message.getResponseText());
		wxMessage.setErrorCode(message.getErrorCode());
		return update(wxMessage);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
