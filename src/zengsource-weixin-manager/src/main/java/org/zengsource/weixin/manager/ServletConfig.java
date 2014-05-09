/**
 * 
 */
package org.zengsource.weixin.manager;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@EnableWebMvc
@Configuration
@MultipartConfig(maxFileSize = -1)
// TODO - from config
@ComponentScan(value = "org.zengsource.weixin")
public class ServletConfig extends WebMvcConfigurerAdapter {

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

}
