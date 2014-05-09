/**
 * 
 */
package org.zengsource.weixin.manager.dao.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.zengsource.util.dao.template.JpaTemplate;
import org.zengsource.weixin.manager.dao.WxProcessDefinitionDao;
import org.zengsource.weixin.manager.domain.WxProcessDefinition;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class JpaWxProcessDefinitionDao extends JpaTemplate<WxProcessDefinition, Long> implements
		WxProcessDefinitionDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public List<WxProcessDefinition> queryAll() {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<WxProcessDefinition> c = cb.createQuery(getEntityClass());
		Root<WxProcessDefinition> root = c.from(getEntityClass());
		c.orderBy(cb.asc(root.get("index")));
		return super.query(c.select(root), null, null);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
