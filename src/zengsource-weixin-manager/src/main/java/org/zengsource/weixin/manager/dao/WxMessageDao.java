/**
 * 
 */
package org.zengsource.weixin.manager.dao;

import java.util.List;

import org.zengsource.util.dao.DaoInterface;
import org.zengsource.weixin.manager.domain.WxMessage;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxMessageDao extends DaoInterface<WxMessage, Long> {

	public WxMessage queryDuplicated(WxMessage wxMessage);

	public List<WxMessage> queryLast(String fromUserName, int start, int limit);

	public List<WxMessage> queryByTarget(String toUserName, Integer status, int start, int limit);

}
