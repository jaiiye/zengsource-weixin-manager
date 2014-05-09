/**
 * 
 */
package org.zengsource.weixin.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class JdbcProperties {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Value("${jdbc.server}")
	private String server = "localhost";
	@Value("${jdbc.db}")
	private String db;
	@Value("${jdbc.user}")
	private String user;
	@Value("${jdbc.password}")
	private String password;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
