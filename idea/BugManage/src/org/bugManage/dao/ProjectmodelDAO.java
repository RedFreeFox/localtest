package org.bugManage.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Projectmodel;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Projectmodel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.bugManage.entity.Projectmodel
 * @author MyEclipse Persistence Tools
 */

public class ProjectmodelDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProjectmodelDAO.class);
	// property constants
	public static final String FATHERMODELID = "FatherModelID";
	public static final String PROJECTMODELNAME = "projectmodelname";
	public static final String STATUS = "status";

	protected void initDao() {
		// do nothing
	}

	public void save(Projectmodel transientInstance) {
		log.debug("saving Projectmodel instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Projectmodel persistentInstance) {
		log.debug("deleting Projectmodel instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Projectmodel findById(java.lang.Long id) {
		log.debug("getting Projectmodel instance with id: " + id);
		try {
			Projectmodel instance = (Projectmodel) getHibernateTemplate().get(
					"org.bugManage.entity.Projectmodel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Projectmodel instance) {
		log.debug("finding Projectmodel instance by example");
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
		log.debug("finding Projectmodel instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Projectmodel as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	public List findAllProjectModel() {
		try {
			String queryString = "from Projectmodel as model where model.projectmodel.projectmodelid is null";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List findByProjectmodelname(Object projectmodelname) {
		return findByProperty(PROJECTMODELNAME, projectmodelname);
	}
	

	
	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}
	public List findAll() {
		log.debug("finding all Projectmodel instances");
		try {
			String queryString = "from Projectmodel";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findByFatherModelID(Object FatherModelID) {
		log.debug("finding all Projectmodel instances");
		try {
			String queryString = "from Projectmodel where FatherModelID="+FatherModelID+" and Status=1";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findByProjectID(Long ProjectID) {
		log.debug("finding all Projectmodel instances");
		try {
			String queryString = "from Projectmodel where FatherModelID is null and  ProjectID="+ProjectID+" and Status<>4";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findByProjectIDAll(Long ProjectID) {
		log.debug("finding all Projectmodel instances");
		try {
			String queryString = "from Projectmodel where   ProjectID="+ProjectID+" and Status<>4";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public Projectmodel merge(Projectmodel detachedInstance) {
		log.debug("merging Projectmodel instance");
		try {
			Projectmodel result = (Projectmodel) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	public void upName(Long modelid,String name) {
		log.debug("finding all Projectmodel instances");
		try {
			String queryString = "update Projectmodel set projectmodelname='"+name+"' where projectmodelid="+modelid;
			System.out.println(queryString);
			this.getSession().createQuery(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public void attachDirty(Projectmodel instance) {
		log.debug("attaching dirty Projectmodel instance");
		try {
			getHibernateTemplate().update(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List findByProID(Long ProjectID) {
		log.debug("finding all Projectmodel instances");
		try {
			String queryString = "select * from Projectmodel where ProjectID="+ProjectID+" and Status=1 and FatherModelID is null";
			return this.getSession().createSQLQuery(queryString).addEntity(Projectmodel.class).list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	//根据项目编号查询模块信息
	public List ModelByProjectId(Long projectid) {
		log.debug("finding all Projectmodel instances");
		try {
			String queryString = "select * from projectmodel where projectid="+projectid;
			return this.getSession().createSQLQuery(queryString).addEntity(Projectmodel.class).list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	public void attachClean(Projectmodel instance) {
		log.debug("attaching clean Projectmodel instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProjectmodelDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ProjectmodelDAO) ctx.getBean("ProjectmodelDAO");
	}
}