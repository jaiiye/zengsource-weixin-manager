/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ConcurrencyServiceImpl implements ConcurrencyService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private ExecutorService executors = Executors.newFixedThreadPool(10);

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public void run(Runnable command) {
		executors.execute(command);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
