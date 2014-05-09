/**
 * 
 */
package org.zengsource.util.dao.template;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zengsource.util.dao.DaoInterface;

/**
 * @author Shaoning Zeng
 * @param <F>
 * @since 7.0
 */
public abstract class JpaTemplate<E, F> implements DaoInterface<E, F> {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/** getEntityManager() */
	public EntityManager em() {
		return this.getEntityManager();
	}

	/** getEntityManager().getCriteriaBuilder() */
	public CriteriaBuilder cb() {
		return em().getCriteriaBuilder();
	}

	@Override
	public E insert(E entity) {
		if (entity != null) {
			try {
				this.entityManager.getTransaction().begin();
				this.timestamp(entity);
				this.entityManager.persist(entity);
				this.entityManager.flush();
				this.entityManager.getTransaction().commit();
				return entity;
			} catch (PersistenceException e) {
				logger.warn(e.getLocalizedMessage());
				this.entityManager.getTransaction().rollback();
			}
		}
		return null;
	}

	@Override
	public E update(E entity) {
		if (entity != null) {
			if (!this.entityManager.getTransaction().isActive()) {
				this.entityManager.getTransaction().begin();
			}
			this.entityManager.merge(entity);
			this.entityManager.getTransaction().commit();
			return entity;
		}
		return null;
	}

	public void timestamp(E entity) {
		timestamp(entity, "createdTime", "updatedTime");
	}

	public void timestamp(E entity, String createdTimeField) {
		timestamp(entity, //
				createdTimeField == null ? "createdTime" : createdTimeField, "updatedTime");
	}

	public void timestamp(E entity, String createdTimeField, String updatedTimeField) {
		Date now = new Date();
		try {
			if (StringUtils.isNotBlank(createdTimeField)) {
				if (BeanUtils.getProperty(entity, createdTimeField) == null) {
					BeanUtils.setProperty(entity, createdTimeField, now);
				}
			}
			if (StringUtils.isNotBlank(updatedTimeField)) {
				if (BeanUtils.getProperty(entity, updatedTimeField) == null) {
					BeanUtils.setProperty(entity, updatedTimeField, now);
				}
			}
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
		}
	}

	public void createdTimestamp(E entity) {
		timestamp(entity, "createdTime", null);
	}

	public void createdTimestamp(E entity, String createdTimeField) {
		timestamp(entity, createdTimeField, null);
	}

	public void updatedTimestamp(E entity) {
		timestamp(entity, null, "updatedTime");
	}

	public void updatedTimestamp(E entity, String updatedTimeField) {
		timestamp(entity, null, updatedTimeField);
	}

	@Override
	public void delete(E entity) {
		if (entity != null) {
			EntityManager em = getEntityManager();
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E queryById(F id) {
		if (id == null) {
			return null;
		}
		Query query = entityManager.createQuery(//
				"SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.id=:id")
				.setParameter("id", id);
		try {
			return (E) query.getSingleResult();
		} catch (NoResultException e) {
			// e.printStackTrace();
			return null;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/** 一层搜索，只搜索一个条件。 */
	protected List<E> query(String name, Object value, Integer start, Integer limit) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(name, value);
		return query(paramMap, start, limit);
	}

	/** 一层搜索，搜索多个条件（AND），各个条件顺序无关。 */
	protected List<E> query(Map<String, Object> paramMap, Integer start, Integer limit) {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<E> c = cb.createQuery(getEntityClass());
		Root<E> root = c.from(getEntityClass());
		if (paramMap == null || paramMap.size() == 0) {
			throw new RuntimeException("no criteria");
		}
		Predicate predicate = cb.conjunction();
		for (String name : paramMap.keySet()) {
			Object value = paramMap.get(name);
			// String type = value.getClass().getName();
			if ("asc".equals(name)) {
				c.orderBy(cb.asc(root.get(value.toString())));
			} else if ("desc".equals(name)) {
				c.orderBy(cb.desc(root.get(value.toString())));
			} else if (value instanceof List) { // IN
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) value;
				In<Object> in = cb.in(root.get(name));
				for (Object item : list) {
					in.value(item);
				}
				predicate = cb.and(predicate, in);
			} else { // Equal
				predicate = cb.and(predicate, cb.equal(root.get(name), value));
			}
		}
		c.select(root).where(predicate);
		return this.query(c, start, limit);
	}

	protected List<E> query(CriteriaQuery<E> c, Integer start, Integer limit) {
		TypedQuery<E> typedQuery = this.getEntityManager().createQuery(c);
		if (start != null && start >= 0) {
			typedQuery.setFirstResult(start);
		}
		if (limit == null || limit == 0) {
			typedQuery.setMaxResults(25);
		} else {
			typedQuery.setMaxResults(limit);
		}
		return typedQuery.getResultList();
	}

	@Override
	public E queryUnique(String name, Object value) {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<E> c = cb.createQuery(getEntityClass());
		Root<E> root = c.from(getEntityClass());
		c.select(root).where(cb.equal(root.get(name), value));
		return this.queryUnique(c);
	}

	public E queryUnique(Map<String, Object> params) {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<E> c = cb.createQuery(getEntityClass());
		Root<E> root = c.from(getEntityClass());
		Predicate predicate = cb.conjunction();
		for (String name : params.keySet()) {
			predicate = cb.and(predicate, cb.equal(root.get(name), params.get(name)));
		}
		c.select(root).where(predicate);
		return this.queryUnique(c);
	}

	protected E queryUnique(CriteriaQuery<E> c) {
		try {
			return this.getEntityManager().createQuery(c).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	protected long queryCount() {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<Long> c = cb.createQuery(Long.class);
		c.select(cb.count(c.from(getEntityClass())));
		return this.getEntityManager().createQuery(c).getSingleResult();
	}

	public long queryCount(String name, Object value) {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<Long> c = cb.createQuery(Long.class);
		Root<E> root = c.from(getEntityClass());
		c.select(cb.count(root)).where(cb.equal(root.get(name), value));
		return this.getEntityManager().createQuery(c).getSingleResult();
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return this.getEntityManager().getCriteriaBuilder();
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
