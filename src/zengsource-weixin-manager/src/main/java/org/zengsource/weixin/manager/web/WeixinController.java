/**
 * 
 */
package org.zengsource.weixin.manager.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.zengsource.weixin.core.service.DefinitionService;
import org.zengsource.weixin.core.service.ExecutionService;
import org.zengsource.weixin.core.service.MessageService;
import org.zengsource.weixin.core.service.NodeService;
import org.zengsource.weixin.core.service.UserService;
import org.zengsource.weixin.core.service.impl.DefaultAccountService;
import org.zengsource.weixin.core.service.impl.DefaultDefinitionService;
import org.zengsource.weixin.core.service.impl.DefaultExecutionService;
import org.zengsource.weixin.core.service.impl.DefaultMessageService;
import org.zengsource.weixin.core.service.impl.DefaultUserService;
import org.zengsource.weixin.core.web.WxController;
import org.zengsource.weixin.manager.dao.adapter.WxAccountDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxMessageDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxProcessDefinitionDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxProcessExecutionDaoAdapter;
import org.zengsource.weixin.manager.dao.adapter.WxUserDaoAdapter;
import org.zengsource.weixin.manager.service.WxProcessNodeService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController extends WxController {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxAccountDaoAdapter wxAccountDaoAdapter;
	@Autowired
	private WxUserDaoAdapter wxUserDaoAdapter;
	@Autowired
	private WxMessageDaoAdapter wxMessageDaoAdapter;
	@Autowired
	private WxProcessNodeService wxProcessNodeService;
	@Autowired
	private WxProcessExecutionDaoAdapter wxProcessExecutionDaoAdapter;
	@Autowired
	private WxProcessDefinitionDaoAdapter wxProcessDefinitionDaoAdapter;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@RequestMapping(method = RequestMethod.GET)
	@Override
	public void doGet(//
			HttpServletRequest request, //
			HttpServletResponse response, //
			@RequestParam String signature, //
			@RequestParam String timestamp, //
			@RequestParam String nonce, //
			@RequestParam String echostr) {
		super.doGet(request, response, signature, timestamp, nonce, echostr);
	}

	@RequestMapping(method = RequestMethod.POST)
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String rootPath = request.getServletContext().getRealPath("");
		if (getDefinitionFiles() == null) {
			setDefinitionFiles(new String[] { rootPath + "/Manager.pd.xml" });
		}
		super.doPost(request, response);
	}

	@Override
	public UserService getUserService() {
		DefaultUserService userService = new DefaultUserService();
		userService.setUserDao(wxUserDaoAdapter);
		return userService;
	}

	@Override
	public MessageService getMessageService() {
		DefaultAccountService accountService = new DefaultAccountService();
		accountService.setAccountDao(wxAccountDaoAdapter);
		DefaultMessageService messageService = new DefaultMessageService();
		messageService.setMessageDao(wxMessageDaoAdapter);
		messageService.setAccountService(accountService);
		return messageService;
	}

	@Override
	public NodeService getNodeService() {
		return wxProcessNodeService;
	}

	@Override
	public DefinitionService getDefinitionService() {
		DefaultDefinitionService definitionService = new DefaultDefinitionService();
		definitionService.setDefinitionDao(wxProcessDefinitionDaoAdapter);
		definitionService.setNodeService(getNodeService());
		return definitionService;
	}

	@Override
	public ExecutionService getExecutionService() {
		DefaultExecutionService executionService = new DefaultExecutionService();
		executionService.setExecutionDao(wxProcessExecutionDaoAdapter);
		return executionService;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
