/**
 * 
 */
package org.zengsource.weixin.manager;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.zengsource.weixin.manager.dao.adapter.WxAccountDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxMessageDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxProcessDefinitionDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxProcessExecutionDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxUserDaoAdapter;
import org.zengsource.weixin.manager.process.CommandNode;
import org.zengsource.weixin.manager.process.SubscribeNode;
import org.zengsource.weixin.manager.process.UnsubscribeNode;
import org.zengsource.weixin.manager.process.binding.BindingAdminCommandNode;
import org.zengsource.weixin.manager.process.binding.CheckingPaasswordTextNode;
import org.zengsource.weixin.manager.process.binding.SettingAdminTextNode;
import org.zengsource.weixin.manager.process.receiving.ReceivingMessageNode;
import org.zengsource.weixin.manager.process.sending.SendingMessageNode;
import org.zengsource.weixin.manager.service.ConcurrencyService;
import org.zengsource.weixin.manager.service.ConcurrencyServiceImpl;
import org.zengsource.weixin.manager.service.WxAccountService;
import org.zengsource.weixin.manager.service.WxAccountServiceImpl;
import org.zengsource.weixin.manager.service.WxMessageService;
import org.zengsource.weixin.manager.service.WxMessageServiceImpl;
import org.zengsource.weixin.manager.service.WxPermissionService;
import org.zengsource.weixin.manager.service.WxPermissionServiceImpl;
import org.zengsource.weixin.manager.service.WxProcessDefinitionService;
import org.zengsource.weixin.manager.service.WxProcessDefinitionServiceImpl;
import org.zengsource.weixin.manager.service.WxProcessExecutionService;
import org.zengsource.weixin.manager.service.WxProcessExecutionServiceImpl;
import org.zengsource.weixin.manager.service.WxProcessNodeService;
import org.zengsource.weixin.manager.service.WxProcessNodeServiceImpl;
import org.zengsource.weixin.manager.service.WxRoleService;
import org.zengsource.weixin.manager.service.WxRoleServiceImpl;
import org.zengsource.weixin.manager.service.WxUserService;
import org.zengsource.weixin.manager.service.WxUserServiceImpl;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Configuration
@EnableCaching
@ComponentScan(value = "org.zengsource.weixin.manager")
public class AppConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// DataSource Config

	@Bean
	public JdbcProperties jdbcProperties() {
		return new JdbcProperties();
	}

	@Bean
	public ConcurrencyService concurrencyService() {
		return new ConcurrencyServiceImpl();
	}

	// Weixin Config

	@Bean
	public WeixinProperties weixinProperties() {
		return new WeixinProperties();
	}

	// Cache - Ehcache

	@Bean
	public CacheManager cacheManager() {
		// configure and return an implementation of Spring's CacheManager SPI
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("default")));
		return cacheManager;
	}

	// Services

	@Bean
	public WxAccountService wxAccountService() {
		return new WxAccountServiceImpl();
	}

	@Bean
	public WxUserService wxUserService() {
		return new WxUserServiceImpl();
	}

	@Bean
	public WxRoleService wxRoleService() {
		return new WxRoleServiceImpl();
	}

	@Bean
	public WxPermissionService wxPermissionService() {
		return new WxPermissionServiceImpl();
	}

	@Bean
	public WxMessageService wxMessageService() {
		return new WxMessageServiceImpl();
	}

	@Bean
	public WxProcessNodeService wxProcessNodeService() {
		return new WxProcessNodeServiceImpl();
	}

	@Bean
	public WxProcessDefinitionService wxProcessDefinitionService() {
		return new WxProcessDefinitionServiceImpl();
	}

	@Bean
	public WxProcessExecutionService wxProcessExecutionService() {
		return new WxProcessExecutionServiceImpl();
	}

	@Bean
	public WxUserDaoAdapter wxUserDaoAdapter() {
		return new WxUserDaoAdapter();
	}

	@Bean
	public WxAccountDaoAdapter wxAccountDaoAdapter() {
		return new WxAccountDaoAdapter();
	}

	@Bean
	public WxMessageDaoAdapter wxMessageDaoAdapter() {
		return new WxMessageDaoAdapter();
	}

	@Bean
	public WxProcessExecutionDaoAdapter wxProcessExecutionDaoAdapter() {
		return new WxProcessExecutionDaoAdapter();
	}

	@Bean
	public WxProcessDefinitionDaoAdapter wxProcessDefinitionDaoAdapter() {
		return new WxProcessDefinitionDaoAdapter();
	}

	// Nodes

	@Bean
	public CommandNode commandNode() {
		return new CommandNode();
	}

	@Bean
	public SubscribeNode subscribeNode() {
		return new SubscribeNode();
	}

	@Bean
	public UnsubscribeNode unsubscribeNode() {
		return new UnsubscribeNode();
	}

	@Bean
	public SendingMessageNode sendingMessageNode() {
		return new SendingMessageNode();
	}

	@Bean
	public BindingAdminCommandNode bindingAdminCommandNode() {
		return new BindingAdminCommandNode();
	}

	@Bean
	public CheckingPaasswordTextNode checkingPaasswordTextNode() {
		return new CheckingPaasswordTextNode();
	}

	@Bean
	public SettingAdminTextNode settingAdminTextNode() {
		return new SettingAdminTextNode();
	}

	@Bean
	public ReceivingMessageNode receivingMessageNode() {
		return new ReceivingMessageNode();
	}

}
