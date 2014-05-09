/**
 * 
 */
package org.zengsource.weixin.manager.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.core.domain.Parameters;
import org.zengsource.weixin.core.service.impl.DefaultNodeService;
import org.zengsource.weixin.manager.dao.WxProcessNodeDao;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;
import org.zengsource.weixin.manager.domain.WxProcessNode;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxProcessNodeServiceImpl extends DefaultNodeService implements WxProcessNodeService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxProcessNodeDao wxProcessNodeDao;

	@Autowired
	private WebApplicationContext webApplicationContext;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxProcessNode create(WxProcessNode wxpn) {
		if (wxpn != null) {
			return this.wxProcessNodeDao.insert(wxpn);
		}
		return null;
	}

	@Override
	public WxProcessNode find(WxProcessDefinition wxpd, String name) {
		if (wxpd != null) {
			return wxProcessNodeDao.queryUnique(wxpd, name);
		}
		return null;
	}

	@Override
	public Node getNodeByName(String name) {
		try {
			return (Node) webApplicationContext.getBean(name);
		} catch (BeansException e) {
			// e.printStackTrace();
		}
		return null;
	}

	@Override
	public Node convert(WxProcessNode wxpn) {
		if (wxpn == null) {
			return null;
		}
		Node node = getNodeByName(wxpn.getName());
		if (node == null) {
			return null;
		}
		node.setName(wxpn.getName());
		node.setClazz(wxpn.getClazz());
		node.setMsgType(wxpn.getMsgType());
		node.setPrompt(wxpn.getPrompt());
		node.setDescription(wxpn.getDescription());
		if (Parameters.MSG_TYPE_TEXT.equals(node.getMsgType())) {
			node.setMsgContent(wxpn.getMsgContent());
		} else if (Parameters.MSG_TYPE_EVENT.equals(node.getMsgType())) {
			node.setEvent(wxpn.getEvent());
			node.setEventKey(wxpn.getEventKey());
		}
		return node;
	}

	@Override
	public Node getNodeByClass(String className) {
		try {
			return (Node) webApplicationContext.getBean(Class.forName(className));
		} catch (BeansException e) {
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		}
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
