/**
 * 
 */
package org.zengsource.weixin.manager.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 访问Spring Security上下文。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public abstract class SecurityContextHelper {

	public static String currentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth == null ? null : (String) auth.getPrincipal();
	}

	public static void authenticate(String username, String password,
			List<GrantedAuthority> authorities) {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(username, password, authorities));
	}

	public static void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
