package org.bugManage.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Bugedition;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Bugedition entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.bugManage.entity.Bugedition
 * @author MyEclipse Persistence Tools
 */

public class BugeditionDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BugeditionDAO.class);
	// property constants
	public static final String BUGEDITIONNAME = "bugeditionname";

	protected void initDao() {
		// do nothing
	}

	public void save(Bugedition transientInstance) {
		log.debug("saving Bugedition instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Bugedition persistentInstance) {
		log.debug("deleting Bugedition instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Bugedition findById(java.lang.Long id) {
		log.debug("getting Bugedition instance with id: " + id);
		try {
			Bugedition instance = (Bugedition) getHibernateTemplate().get(
					"org.bugManage.entity.Bugedition", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Bugedition instance) {
		log.debug("finding Bugedition instance by example");
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
		log.debug("finding Bugedition instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Bugedition as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBugeditionname(Object bugeditionname) {
		return findByProperty(BUGEDITIONNAME, bugeditionname);
	}

	public List findAll() {
		log.debug("finding all Bugedition instances");
		try {
			String queryString = "from Bugedition";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Bugedition merge(Bugedition detachedInstance) {
		log.debug("merging Bugedition instance");
		try {
			Bugedition result = (Bugedition) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Bugedition instance) {
		log.debug("attaching dirty Bugedition instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Bugedition instance) {
		log.debug("attaching clean Bugedition instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BugeditionDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BugeditionDAO) ctx.getBean("BugeditionDAO");
	}
}