/**
 * 
 */
package org.zengsource.weixin.manager.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.zengsource.util.dao.template.JpaTemplate;
import org.zengsource.weixin.manager.dao.WxUserDao;
import org.zengsource.weixin.manager.domain.WxRole;
import org.zengsource.weixin.manager.domain.WxUser;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class JpaWxUserDao extends JpaTemplate<WxUser, Long> implements WxUserDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public List<WxUser> queryByRole(WxRole wxRole) {
		TypedQuery<WxUser> query = getEntityManager().createQuery("" //
				+ "SELECT u FROM WxUser u " //
				+ "inner join u.roles role " //
				+ "where role.id=:roleId", WxUser.class).setParameter("roleId", wxRole.getId());
		return query.getResultList();
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
