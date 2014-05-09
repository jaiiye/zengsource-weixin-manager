/**
 * 
 */
package org.zengsource.weixin.manager.dao.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.zengsource.util.dao.template.JpaTemplate;
import org.zengsource.weixin.manager.dao.WxMessageDao;
import org.zengsource.weixin.manager.domain.WxMessage;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class JpaWxMessageDao extends JpaTemplate<WxMessage, Long> implements WxMessageDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public WxMessage queryDuplicated(WxMessage wxMessage) {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<WxMessage> c = cb.createQuery(getEntityClass());
		Root<WxMessage> root = c.from(getEntityClass());
		Predicate predicate = cb.conjunction();
		cb.and(predicate, cb.equal(root.get("fromUserName"), wxMessage.getFromUserName()));
		cb.and(predicate, cb.equal(root.get("toUserName"), wxMessage.getToUserName()));
		cb.and(predicate, cb.equal(root.get("content"), wxMessage.getContent()));
		Expression<Integer> createTimePath = root.get("createTime");
		cb.and(predicate, cb.lt(createTimePath, wxMessage.getCreateTime()));
		c.select(root).where(predicate);
		List<WxMessage> messages = super.query(c, 0, 1);
		if (messages != null && messages.size() == 1) {
			return messages.get(0);
		}
		return null;
	}

	@Override
	public List<WxMessage> queryLast(String fromUserName, int start, int limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("fromUserName", fromUserName);
		params.put("desc", "createdTime");
		return super.query(params, start, limit);
	}

	@Override
	public List<WxMessage> queryByTarget(String toUserName, Integer status, int start, int limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("toUserName", toUserName);
		params.put("status", status);
		return super.query(params, start, limit);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
