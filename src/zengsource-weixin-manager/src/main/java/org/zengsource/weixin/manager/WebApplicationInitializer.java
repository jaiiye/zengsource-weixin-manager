package org.zengsource.weixin.manager;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 在Servlet 3.0容器中初始化Servlet，并且注入Spring和Web的配置。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WebApplicationInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { //
		AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { // WebSecurityConfig.class,
		ServletConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		return super.getServletFilters();
	}

}
