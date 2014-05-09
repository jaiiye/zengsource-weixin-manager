/**
 * 
 */
package org.zengsource.weixin.manager.dao.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.weixin.core.dao.DefinitionDao;
import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;
import org.zengsource.weixin.manager.domain.WxProcessNode;
import org.zengsource.weixin.manager.service.WxProcessDefinitionService;
import org.zengsource.weixin.manager.service.WxProcessNodeService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxProcessDefinitionDaoAdapter implements DefinitionDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private WxProcessDefinitionService wxProcessDefinitionService;
	@Autowired
	private WxProcessNodeService wxProcessNodeService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public List<Definition> queryAll() {
		List<WxProcessDefinition> definitions = wxProcessDefinitionService.findAll();
		if (definitions != null) {
			List<Definition> allDefinitions = new ArrayList<>();
			for (WxProcessDefinition wxpd : definitions) {
				allDefinitions.add(wxProcessDefinitionService.convert(wxpd));
			}
			return allDefinitions;
		}
		return null;
	}

	@Override
	public Definition queryByName(String name) {
		WxProcessDefinition wxpd = wxProcessDefinitionService.findByName(name);
		return wxpd == null ? null : wxProcessDefinitionService.convert(wxpd);
	}

	@Override
	public Definition insert(Definition definition) {
		WxProcessDefinition wxpd = WxProcessDefinition.from(definition);
		wxpd = wxProcessDefinitionService.create(wxpd);
		for (Node node : definition.getNodes()) {
			WxProcessNode wxpn = wxProcessNodeService.find(wxpd, node.getName());
			if (wxpn == null) {
				wxpn = WxProcessNode.from(node);
				wxpn.setWxProcessDefinition(wxpd);
				wxpn = wxProcessNodeService.create(wxpn);
			}
		}
		return wxpd == null ? null : definition;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
