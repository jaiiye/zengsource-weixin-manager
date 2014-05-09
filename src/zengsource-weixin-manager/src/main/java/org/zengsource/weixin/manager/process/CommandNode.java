/**
 * 
 */
package org.zengsource.weixin.manager.process;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.node.ResponseMessages;
import org.zengsource.weixin.core.node.TextNode;

/**
 * 处理数字命令的节点。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public class CommandNode extends TextNode implements Commands, ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected Message onTextReceived(Message in) {
		if (in == null) {
			logger.info("Error : Null incoming message!");
			return null;
		}
		int command = NumberUtils.toInt(in.getContent(), CMD_UNKNOWN);
		return onNumberReceived(in, command);
	}

	protected Message onNumberReceived(Message in, int command) {
		logger.info("Run command : " + command);
		Message out = in.replyText();
		out.setContent("Command received : " + command);
		return out;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
