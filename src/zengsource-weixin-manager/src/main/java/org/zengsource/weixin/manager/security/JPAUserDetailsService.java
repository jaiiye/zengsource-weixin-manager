/**
 * 
 */
package org.zengsource.weixin.manager.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zengsource.weixin.manager.domain.WxUser;
import org.zengsource.weixin.manager.service.WxUserService;

/**
 * 封装了JPA实现的用户查询。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public class JPAUserDetailsService implements UserDetailsService {

	@Autowired
	private WxUserService wxUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		WxUser localUser = this.wxUserService.findByUsername(username);
		if (localUser == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		// TODO not implement for authorities
		List<GrantedAuthority> authorities = new ArrayList<>();
		org.springframework.security.core.userdetails.User secureUser = new User(
				localUser.getUsername(), localUser.getPassword(), authorities);
		return secureUser;
	}

}
