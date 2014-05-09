/**
 * 
 */
package org.zengsource.weixin.manager.dao;

import java.util.List;

import org.zengsource.util.dao.DaoInterface;
import org.zengsource.weixin.manager.domain.WxRole;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface WxUserDao extends DaoInterface<WxUser, Long> {

	public List<WxUser> queryByRole(WxRole wxRole);

}
