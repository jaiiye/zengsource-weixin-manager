/**
 * 
 */
package org.zengsource.weixin.manager.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * 封装了JPA实现的用户验证。
 * @author Shaoning Zeng
 * @since 7.0
 */
public class JPAAuthenticationProvider implements AuthenticationProvider {
	
	private Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private WxUserService wxUserService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		WxUser user = this.wxUserService.authenticate(new WxUser(username, password));
		if (this.wxUserService.isAuthenticated(user)) {
			// TODO not implement
			List<GrantedAuthority> authorities = new ArrayList<>();
			logger.info("==> authenticate username " + username);
			return new UsernamePasswordAuthenticationToken(username, password, authorities);
		} 
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}	

}
