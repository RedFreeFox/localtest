package org.bugManage.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Projectuser;
import org.bugManage.entity.Bug;
import org.bugManage.entity.Userinfo;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Userinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.bugManage.entity.Userinfo
 * @author MyEclipse Persistence Tools
 */

public class UserinfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(UserinfoDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String TELEPHONE = "telephone";
	public static final String DEPARTMENT = "department";
	public static final String TYPE = "type";
	public static final String ACTIVED = "actived";

	protected void initDao() {
		// do nothing
	}

	public void save(Userinfo transientInstance) {
		log.debug("saving Userinfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Userinfo persistentInstance) {
		log.debug("deleting Userinfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Userinfo findById(java.lang.Long id) {
		log.debug("getting Userinfo instance with id: " + id);
		try {
			Userinfo instance = (Userinfo) getHibernateTemplate().get(
					"org.bugManage.entity.Userinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Userinfo instance) {
		log.debug("finding Userinfo instance by example");
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
		log.debug("finding Userinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Userinfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByTelephone(Object telephone) {
		return findByProperty(TELEPHONE, telephone);
	}

	public List findByDepartment(Object department) {
		return findByProperty(DEPARTMENT, department);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByActived(Object actived) {
		return findByProperty(ACTIVED, actived);
	}

	public List findAll() {
		log.debug("finding all Userinfo instances");
		try {
			String queryString = "from Userinfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findAllByNoProID(List<Projectuser> Prouser) {
		log.debug("finding all Userinfo instances");
		try {
			String queryString = "select distinct a.* from  Userinfo a,ProjectUser b where a.Userid=b.ProjectUserID ";
			for(Projectuser pu:Prouser){
				queryString+=" and b.ProjectUserID<>"+pu.getUserinfo().getUserid();
			}
			SQLQuery q =this.getSession().createSQLQuery(queryString).addEntity(Userinfo.class);
			return q.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Userinfo merge(Userinfo detachedInstance) {
		log.debug("merging Userinfo instance");
		try {
			Userinfo result = (Userinfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Userinfo instance) {
		log.debug("attaching dirty Userinfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Userinfo instance) {
		log.debug("attaching clean Userinfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	// 登录
	public List<Userinfo> login(Userinfo user) {
		log.debug("finding all Userinfo instances");
		try {
			String queryString ="from Userinfo where name='"+user.getName()+"' and password='"+user.getPassword()+"'";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	//分页    pageNo--页数   pageSize--一页中显示的数据个数
	 public List<Userinfo> findPage(int pageNo,int pageSize ){
		 log.debug("finding all Userinfo instances");
		 try {
			 String sql="select * from UserInfo ";
			 SQLQuery query=super.getSession().createSQLQuery(sql).addEntity(Userinfo.class);
			 query.setFirstResult((pageNo-1)*pageSize);//从哪行数据开始
			 query.setMaxResults(pageSize);//显示几行
			return query.list();
		} catch (RuntimeException e) {
			// TODO: handle exception
			log.error("find all failed", e);
			throw e;
		}
	 }
		public List findSelect(String Id,Long FirstResult,Long MaxResults,String Dep) {
			log.debug("finding all Userinfo instances");
			try {
				String queryString = "select * from UserInfo where actived=1";
				if(!Id.equals("")&&Id!=null){
					queryString+="and name like '%"+Id+"%'";
				}
				if(!Dep.equals("")&&Dep!=null){
					queryString+="and department='"+Dep+"'";
				}
				SQLQuery qu=super.getSession().createSQLQuery(queryString);
				qu.setFirstResult(new Integer(FirstResult.toString()));
				qu.setMaxResults(new Integer(MaxResults.toString()));
				List<Userinfo> list=qu.addEntity(Userinfo.class).list();
				return list;
			} catch (RuntimeException re) {
				log.error("find all failed", re);
				throw re;
			}
		}
	 
		//分页    pageNo--页数   pageSize--一页中显示的数据个数
	 public Long findPageMax(){
		 log.debug("finding all Userinfo instances");
		 try {
			 String sql="select * from UserInfo ";
			 SQLQuery query=super.getSession().createSQLQuery(sql).addEntity(Userinfo.class);
			return new Long(query.list().size());
		} catch (RuntimeException e) {
			// TODO: handle exception
			log.error("find all failed", e);
			throw e;
		}
	 }
	 
		public List findByUser(Long userid,Long FirstResult,Long MaxResults) {
			log.debug("attaching clean Bug instance");
			try {
				String sql="select * from Userinfo where userid=" +userid;			
				SQLQuery qu=super.getSession().createSQLQuery(sql);
				qu.setFirstResult(new Integer(FirstResult.toString()));
				qu.setMaxResults(new Integer(MaxResults.toString()));
				List<Userinfo> list=qu.addEntity(Userinfo.class).list();
				return list;
			} catch (RuntimeException re) {
				log.error("attach failed", re);
				throw re;
			}
		}
		
	public static UserinfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserinfoDAO) ctx.getBean("UserinfoDAO");
	}
	
}
