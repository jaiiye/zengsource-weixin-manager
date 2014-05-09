/**
 * 
 */
package org.zengsource.weixin.manager.service;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface ConcurrencyService {

	public void run(Runnable command);

}
