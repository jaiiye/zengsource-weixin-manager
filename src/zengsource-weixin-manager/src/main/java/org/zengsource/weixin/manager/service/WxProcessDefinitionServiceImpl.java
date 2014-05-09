/**
 * 
 */
package org.zengsource.weixin.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.manager.dao.WxProcessDefinitionDao;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;
import org.zengsource.weixin.manager.domain.WxProcessNode;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxProcessDefinitionServiceImpl implements WxProcessDefinitionService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	@Autowired
	WxProcessNodeService wxProcessNodeService;
	
	@Autowired
	private WxProcessDefinitionDao wxProcessDefinitionDao;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxProcessDefinitionServiceImpl() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxProcessDefinition create(WxProcessDefinition wxpd) {
		return wxProcessDefinitionDao.insert(wxpd);
	}

	@Override
	public List<WxProcessDefinition> findAll() {
		return wxProcessDefinitionDao.queryAll();
	}

	@Override
	public WxProcessDefinition findByName(String name) {
		return wxProcessDefinitionDao.queryUnique("name", name);
	}

	@Override
	public Definition convert(WxProcessDefinition wxpd) {
		Definition definition = new Definition();
		definition.setName(wxpd.getName());
		definition.setIndex(wxpd.getIndex());
		definition.setDescription(wxpd.getDescription());
		for (WxProcessNode wxpn : wxpd.getNodes()) {
			Node node = wxProcessNodeService.convert(wxpn);			
			definition.addNode(node);
		}
		return definition;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxProcessDefinitionDao getWxProcessDefinitionDao() {
		return wxProcessDefinitionDao;
	}

	public void setWxProcessDefinitionDao(WxProcessDefinitionDao wxProcessDefinitionDao) {
		this.wxProcessDefinitionDao = wxProcessDefinitionDao;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
