/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.zengsource.weixin.manager.domain.WxAccount;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxAccountService {

	public WxAccount findByOpenId(String openId);

}
