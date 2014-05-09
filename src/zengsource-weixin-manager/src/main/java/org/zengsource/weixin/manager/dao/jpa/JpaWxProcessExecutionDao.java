/**
 * 
 */
package org.zengsource.weixin.manager.dao.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.zengsource.util.dao.template.JpaTemplate;
import org.zengsource.weixin.manager.dao.WxProcessExecutionDao;
import org.zengsource.weixin.manager.domain.WxProcessExecution;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class JpaWxProcessExecutionDao extends JpaTemplate<WxProcessExecution, Long> implements
		WxProcessExecutionDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxProcessExecution queryRunning(WxUser wxUser) {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<WxProcessExecution> c = cb.createQuery(getEntityClass());
		Root<WxProcessExecution> root = c.from(getEntityClass());
		Predicate predicate = cb.conjunction();
		Expression<Integer> current = root.get("current");
		predicate = cb.and(predicate, cb.equal(root.get("wxUser"), wxUser));
		predicate = cb.and(predicate, cb.gt(current, new Integer(-1)));
		c.select(root).where(predicate);
		return super.queryUnique(c);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
