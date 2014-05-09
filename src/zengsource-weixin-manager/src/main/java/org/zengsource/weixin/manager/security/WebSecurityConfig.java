/**
 * 
 */
package org.zengsource.weixin.manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security配置
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new JPAUserDetailsService();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new JPAAuthenticationProvider();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(16);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/weixin**").anonymous()
				.anyRequest().authenticated().and().formLogin()
				.defaultSuccessUrl("/signin/success").failureUrl("/signin/failure")
				.loginPage("/signin/auth").permitAll().and().logout()
				.logoutSuccessUrl("/signout/success").logoutUrl("/signout").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService());
		auth.authenticationProvider(this.authenticationProvider());
	}

}
