/**
 * 
 */
package org.zengsource.weixin.manager;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zengsource.weixin.manager.dao.WxAccountDao;
import org.zengsource.weixin.manager.dao.WxMessageDao;
import org.zengsource.weixin.manager.dao.WxPermissionDao;
import org.zengsource.weixin.manager.dao.WxProcessDefinitionDao;
import org.zengsource.weixin.manager.dao.WxProcessExecutionDao;
import org.zengsource.weixin.manager.dao.WxProcessNodeDao;
import org.zengsource.weixin.manager.dao.WxRoleDao;
import org.zengsource.weixin.manager.dao.WxUserDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxAccountDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxMessageDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxPermissionDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxProcessDefinitionDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxProcessExecutionDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxProcessNodeDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxRoleDao;
import org.zengsource.weixin.manager.dao.jpa.JpaWxUserDao;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Configuration
@EnableTransactionManagement
public class JpaConfiguration {

	Log logger = LogFactory.getLog(getClass());

	@Autowired
	private JdbcProperties jdbcProperties;

	@Bean
	public DataSource dataSource() throws SQLException {
		PGPoolingDataSource dataSource = new PGPoolingDataSource();
		dataSource.setServerName(jdbcProperties.getServer());
		dataSource.setDatabaseName(jdbcProperties.getDb());
		dataSource.setUser(jdbcProperties.getUser());
		dataSource.setPassword(jdbcProperties.getPassword());
		dataSource.setInitialConnections(10);
		// EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		// return builder.setType(EmbeddedDatabaseType.H2).build();
		logger.info("==> Connect DB with user " + jdbcProperties.getUser());
		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws SQLException {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		jpaVendorAdapter.setDatabasePlatform(PostgreSQL82Dialect.class.getName());

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan("org.zengsource.weixin.manager");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		factory.getJpaPropertyMap().put( // TODO not working
				Environment.HBM2DDL_IMPORT_FILES, "/META-INF/import.sql");

		return factory.getObject();
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	// DAO

	@Bean
	public WxAccountDao wxAccountDao() {
		return new JpaWxAccountDao();
	}

	@Bean
	public WxUserDao wxUserDao() {
		return new JpaWxUserDao();
	}

	@Bean
	public WxRoleDao wxRoleDao() {
		return new JpaWxRoleDao();
	}

	@Bean
	public WxPermissionDao wxPermissionDao() {
		return new JpaWxPermissionDao();
	}

	@Bean
	public WxMessageDao wxMessageDao() {
		return new JpaWxMessageDao();
	}

	@Bean
	public WxProcessNodeDao wxProcessNodeDao() {
		return new JpaWxProcessNodeDao();
	}

	@Bean
	public WxProcessDefinitionDao wxProcessDefinitionDao() {
		return new JpaWxProcessDefinitionDao();
	}

	@Bean
	public WxProcessExecutionDao wxProcessExecutionDao() {
		return new JpaWxProcessExecutionDao();
	}

}
