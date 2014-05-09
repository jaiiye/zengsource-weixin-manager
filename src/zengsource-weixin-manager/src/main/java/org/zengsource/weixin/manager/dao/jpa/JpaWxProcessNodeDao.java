/**
 * 
 */
package org.zengsource.weixin.manager.dao.jpa;

import java.util.HashMap;
import java.util.Map;

import org.zengsource.util.dao.template.JpaTemplate;
import org.zengsource.weixin.manager.dao.WxProcessNodeDao;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;
import org.zengsource.weixin.manager.domain.WxProcessNode;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class JpaWxProcessNodeDao extends JpaTemplate<WxProcessNode, Long> implements
		WxProcessNodeDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	@Override
	public WxProcessNode queryUnique(WxProcessDefinition wxpd, String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wxProcessDefinition", wxpd);
		params.put("name", name);
		return super.queryUnique(params);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
