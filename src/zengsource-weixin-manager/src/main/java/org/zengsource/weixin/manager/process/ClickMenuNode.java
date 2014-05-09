/**
 * 
 */
package org.zengsource.weixin.manager.process;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.node.EventNode;
import org.zengsource.weixin.core.node.ResponseMessages;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ClickMenuNode extends EventNode implements Commands, ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	public static final String PIC_URL_TMP = "http://shaoning.net/happystudy/%s";
	public static final String URL_PREFIX = "http://asktimely.com/";

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected Message onClick(Message in) {
		String eventKey = in.getEventKey();
		logger.info("=> User click menu : " + eventKey);
		Message out = null;
		if (EVENT_KEY_REFESH.equals(eventKey)) { // 刷新
			//out = onRefresh(in);
		} else {
			out = in.replyText();
			out.setContent(String.format(INFO_CLICK_MENU, eventKey) //
					+ ERROR_UNKNOWN_OPERATION);
		}
		return out;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
