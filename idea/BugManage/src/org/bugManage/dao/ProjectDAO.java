package org.bugManage.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Project;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Project entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.bugManage.entity.Project
 * @author MyEclipse Persistence Tools
 */

public class ProjectDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ProjectDAO.class);
	// property constants
	public static final String PROJECTNAME = "projectname";
	public static final String STATUS = "status";

	protected void initDao() {
		// do nothing
	}

	public void save(Project transientInstance) {
		log.debug("saving Project instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Project persistentInstance) {
		log.debug("deleting Project instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Project findById(java.lang.Long id) {
		log.debug("getting Project instance with id: " + id);
		try {
			Project instance = (Project) getHibernateTemplate().get(
					"org.bugManage.entity.Project", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Project instance) {
		log.debug("finding Project instance by example");
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
	
	//根据模块编号查询项目信息
	public List findByModelId(Long modelid) {
		try {
//			String sql="select * from project where projectid in(select  p.projectid from userinfo u,projectuser p where u.userid = p.projectuserid and u.userid="+userid+")";
			String sql = "select * from project where projectid in (select projectid from projectmodel where projectmodelid="+modelid+")";
			return this.getSession().createSQLQuery(sql).addEntity(Project.class).list();
//			return getHibernateTemplate().find(queryString, new Object[]{userid});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Project instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Project as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProjectname(Object projectname) {
		return findByProperty(PROJECTNAME, projectname);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Project instances");
		try {
			String queryString = "from Project";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Project merge(Project detachedInstance) {
		log.debug("merging Project instance");
		try {
			Project result = (Project) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Project instance) {
		log.debug("attaching dirty Project instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Project instance) {
		log.debug("attaching clean Project instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List findByNameAndStatus(String name,Long status) {
		try {
			String queryString = "from Project as model where model.projectname like ? and model.status=?";
			return getHibernateTemplate().find(queryString, new Object[]{"%"+name+"%",status});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByName(String name) {
		try {
			String queryString = "from Project as model where model.projectname like ?";
			return getHibernateTemplate().find(queryString, new Object[]{"%"+name+"%"});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//通过项目状态过滤查询所有，并按编号排序
	public List findAllProjectByStatus() {
		try {
			String queryString = "from Project as model where model.status in (2,3)  order by model.projectid asc";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	public static ProjectDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProjectDAO) ctx.getBean("ProjectDAO");
	}
	
	
	
}