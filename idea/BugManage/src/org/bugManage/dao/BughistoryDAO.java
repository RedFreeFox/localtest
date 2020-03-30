package org.bugManage.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Bughistory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Bughistory entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.bugManage.entity.Bughistory
 * @author MyEclipse Persistence Tools
 */

public class BughistoryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BughistoryDAO.class);
	// property constants
	public static final String STATUSBEFORE = "statusbefore";
	public static final String DESCRIBE = "describe";
	public static final String STATUS = "status";
	public static final String REPLY = "reply";

	protected void initDao() {
		// do nothing
	}

	public void save(Bughistory transientInstance) {
		log.debug("saving Bughistory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Bughistory persistentInstance) {
		log.debug("deleting Bughistory instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Bughistory findById(java.lang.Long id) {
		log.debug("getting Bughistory instance with id: " + id);
		try {
			Bughistory instance = (Bughistory) getHibernateTemplate().get(
					"org.bugManage.entity.Bughistory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Bughistory instance) {
		log.debug("finding Bughistory instance by example");
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
		log.debug("finding Bughistory instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Bughistory as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatusbefore(Object statusbefore) {
		return findByProperty(STATUSBEFORE, statusbefore);
	}

	public List findByDescribe(Object describe) {
		return findByProperty(DESCRIBE, describe);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByReply(Object reply) {
		return findByProperty(REPLY, reply);
	}

	public List findAll() {
		log.debug("finding all Bughistory instances");
		try {
			String queryString = "from Bughistory";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findByBugID(Long BugID) {
		log.debug("finding all Bughistory instances");
		try {
			String queryString = "from Bughistory where BugID="+BugID+"  ORDER BY BugID DESC";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public Bughistory findTimeMax(Long bugid) {
		log.debug("finding all Bughistory instances");
		try {
			String queryString = "from Bughistory order by HistoryID desc where BugID="+bugid;
			List li=getHibernateTemplate().find(queryString);
			if(li.size()==0){
				return null;
			}else{
				return (Bughistory) li.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Bughistory merge(Bughistory detachedInstance) {
		log.debug("merging Bughistory instance");
		try {
			Bughistory result = (Bughistory) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Bughistory instance) {
		log.debug("attaching dirty Bughistory instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Bughistory instance) {
		log.debug("attaching clean Bughistory instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BughistoryDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BughistoryDAO) ctx.getBean("BughistoryDAO");
	}
}