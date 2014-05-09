/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.List;

import org.zengsource.weixin.manager.domain.WxMessage;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxMessageService {

	public WxMessage findByMsgId(Long msgId);

	/** 查找一条消息的重复发送消息。 */
	public WxMessage findDuplicated(WxMessage wxMessage);

	public WxMessage create(WxMessage wxMessage);
	
	public WxMessage send(WxMessage wxMessage, boolean async);

	/** 查询未读消息。*/
	public List<WxMessage> findUnread(String toUserName);

	public WxMessage update(WxMessage wxMessage);

	public WxMessage findLast(WxUser user);

}
