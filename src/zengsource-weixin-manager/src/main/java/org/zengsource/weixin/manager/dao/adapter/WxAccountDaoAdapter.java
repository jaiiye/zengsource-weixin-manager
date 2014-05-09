/**
 * 
 */
package org.zengsource.weixin.manager.dao.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.dao.AccountDao;
import org.zengsource.weixin.core.domain.Account;
import org.zengsource.weixin.manager.domain.WxAccount;
import org.zengsource.weixin.manager.service.WxAccountService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxAccountDaoAdapter implements AccountDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxAccountService wxAccountService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Account queryByOpenId(String openId) {
		WxAccount wxAccount = wxAccountService.findByOpenId(openId);
		if (wxAccount != null) {
			return wxAccount.toAccount();
		}
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
