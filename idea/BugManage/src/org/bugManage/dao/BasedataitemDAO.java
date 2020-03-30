package org.bugManage.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Basedataitem;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Basedataitem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.bugManage.entity.Basedataitem
 * @author MyEclipse Persistence Tools
 */

public class BasedataitemDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BasedataitemDAO.class);
	// property constants
	public static final String ITEMNAME = "itemname";
	public static final String ITEMVALUE = "itemvalue";

	protected void initDao() {
		// do nothing
	}

	public void save(Basedataitem transientInstance) {
		log.debug("saving Basedataitem instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Basedataitem persistentInstance) {
		log.debug("deleting Basedataitem instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Basedataitem findById(java.lang.Long id) {
		log.debug("getting Basedataitem instance with id: " + id);
		try {
			Basedataitem instance = (Basedataitem) getHibernateTemplate().get(
					"org.bugManage.entity.Basedataitem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Basedataitem instance) {
		log.debug("finding Basedataitem instance by example");
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
		log.debug("finding Basedataitem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Basedataitem as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByDateId(String DataID) {
		log.debug("finding all Basedataitem instances");
		try {
			String queryString = "from Basedataitem where DataID='"+DataID+"'";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findByDateIdAndValue(String DataID,String ItemValue) {
		log.debug("finding all Basedataitem instances");
		try {
			String queryString = "from Basedataitem where DataID='"+DataID+"' and ItemValue='"+ItemValue+"'";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findByItemname(Object itemname) {
		return findByProperty(ITEMNAME, itemname);
	}

	public List findByItemvalue(Object itemvalue) {
		return findByProperty(ITEMVALUE, itemvalue);
	}

	public List findAll() {
		log.debug("finding all Basedataitem instances");
		try {
			String queryString = "from Basedataitem";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Basedataitem merge(Basedataitem detachedInstance) {
		log.debug("merging Basedataitem instance");
		try {
			Basedataitem result = (Basedataitem) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Basedataitem instance) {
		log.debug("attaching dirty Basedataitem instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Basedataitem instance) {
		log.debug("attaching clean Basedataitem instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BasedataitemDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BasedataitemDAO) ctx.getBean("BasedataitemDAO");
	}
}