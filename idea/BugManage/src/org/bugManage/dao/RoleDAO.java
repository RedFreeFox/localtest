package org.bugManage.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Role;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Role
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.bugManage.entity.Role
 * @author MyEclipse Persistence Tools
 */

public class RoleDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(RoleDAO.class);
	// property constants
	public static final String LIMITID = "limitid";
	public static final String MUENNAME = "muenname";
	public static final String ACTION = "action";
	public static final String PARAM = "param";

	protected void initDao() {
		// do nothing
	}

	public void save(Role transientInstance) {
		log.debug("saving Role instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Role persistentInstance) {
		log.debug("deleting Role instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Role findById(java.lang.Long id) {
		log.debug("getting Role instance with id: " + id);
		try {
			Role instance = (Role) getHibernateTemplate().get(
					"org.bugManage.entity.Role", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Role instance) {
		log.debug("finding Role instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Role instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Role as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLimitid(Object limitid) {
		return findByProperty(LIMITID, limitid);
	}

	public List findByMuenname(Object muenname) {
		return findByProperty(MUENNAME, muenname);
	}

	public List findByAction(Object action) {
		return findByProperty(ACTION, action);
	}

	public List findByParam(Object param) {
		return findByProperty(PARAM, param);
	}

	public List findAll() {
		log.debug("finding all Role instances");
		try {
			String queryString = "from Role";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	
	
	public Role merge(Role detachedInstance) {
		log.debug("merging Role instance");
		try {
			Role result = (Role) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Role instance) {
		log.debug("attaching dirty Role instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Role instance) {
		log.debug("attaching clean Role instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RoleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RoleDAO) ctx.getBean("RoleDAO");
	}
}