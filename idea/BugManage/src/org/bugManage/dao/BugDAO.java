package org.bugManage.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bugManage.entity.Bug;
import org.bugManage.format.dateFormat;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Bug
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.bugManage.entity.Bug
 * @author MyEclipse Persistence Tools
 */

public class BugDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BugDAO.class);
	// property constants
	public static final String TESTPHASE = "testphase";
	public static final String ENVIRONMENT = "environment";
	public static final String SUMMARY = "summary";
	public static final String DETAIL = "detail";
	public static final String ANALYSE = "analyse";
	public static final String AFFIXPATH = "affixpath";
	public static final String GRAVITYLEVEL = "gravitylevel";
	public static final String REAPPEARANCE = "reappearance";
	public static final String QUALITYCHARACTER = "qualitycharacter";
	public static final String PHASE = "phase";
	public static final String STATUS = "status";
	public static final String MODIFYTIMES = "modifytimes";
	public static final String PRIORITY = "priority";
	public static final String LASTREPLY = "lastreply";

	protected void initDao() {
		// do nothing
	}

	public void save(Bug transientInstance) {
		log.debug("saving Bug instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	
	public void delete(Bug persistentInstance) {
		log.debug("deleting Bug instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Bug findById(java.lang.Long id) {
		log.debug("getting Bug instance with id: " + id);
		try {
			Bug instance = (Bug) getHibernateTemplate().get(
					"org.bugManage.entity.Bug", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Bug instance) {
		log.debug("finding Bug instance by example");
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
	
	@SuppressWarnings("unchecked")
	public List findByType(Long BugID,Long Tester,Long Principal,Long Status,String Summary,Date StarDate,Date OverDate,Long ModelId
			,Long FirstResult,Long MaxResults
	){
		String sql="select a.* from  bug a left join  ProjectModel b on(b.ProjectModelID=a.ModuleID) " +
		" where (b.ProjectModelID in ( select c.ProjectModelID from ProjectModel c start with c.projectmodelid="+ModelId+" connect by nocycle prior c.projectmodelid=c.FatherModelID) or a.moduleid="+ModelId+" )";
		sql+=" and a.Status<>4";
		dateFormat df=new dateFormat();
		if(BugID!=null&&BugID!=0){
			sql+=" and bugid like (%"+BugID+"%)";
		}
		if(Tester!=null&&Tester!=0){
			sql+=" and Tester="+Tester;
		}
		if(Principal!=null&&Principal!=0){
			sql+=" and Principal="+Principal;
		}
		if((StarDate!=null&&!StarDate.equals(""))&&(OverDate!=null&&!OverDate.equals(""))){
			sql+=" and createtime >to_date('"+df.dateToString(StarDate)+"','yyyy-MM-dd hh24:mi:ss') and createtime<to_date('"+df.dateToString(OverDate)+"','yyyy-MM-dd hh24:mi:ss')";
		}
		if(Status!=null&&Status!=0){
			sql+=" and a.Status="+Status+"";
		}
		if(Summary!=null&&!Summary.equals("")){
			sql+=" and summary like ('%"+Summary+"%')";
		}
		
		SQLQuery qu=super.getSession().createSQLQuery(sql);
		qu.setFirstResult(new Integer(FirstResult.toString()));
		qu.setMaxResults(new Integer(MaxResults.toString()));
		List<Bug> list=qu.addEntity(Bug.class).list();
		return list;
	}
	public List findByTypeToPage(Long BugID,Long Tester,Long Principal,Long Status,String Summary,Date StarDate,Date OverDate,Long ModelId
	){
		
		
		String sql="select a.* from  bug a left join  ProjectModel b on(b.ProjectModelID=a.ModuleID) " +
		" where ( b.ProjectModelID in ( select c.ProjectModelID from ProjectModel c start with c.projectmodelid="+ModelId+" connect by nocycle prior c.projectmodelid=c.FatherModelID) or a.moduleid="+ModelId+" )";
		sql+=" and a.Status<>4";
		dateFormat df=new dateFormat();
		if(BugID!=null&&BugID!=0){
			sql+=" and bugid like (%"+BugID+"%)";
		}
		if(Tester!=null&&Tester!=0){
			sql+=" and Tester="+Tester;
		}
		if(Principal!=null&&Principal!=0){
			sql+=" and Principal="+Principal;
		}
		if((StarDate!=null&&!StarDate.equals(""))&&(OverDate!=null&&!OverDate.equals(""))){
			sql+=" and createtime >to_date('"+df.dateToString(StarDate)+"','yyyy-MM-dd hh24:mi:ss') and createtime<to_date('"+df.dateToString(OverDate)+"','yyyy-MM-dd hh24:mi:ss')";
		}
		if(Status!=null&&Status!=0){
			sql+=" and a.Status="+Status+"";
		}
		if(Summary!=null&&!Summary.equals("")){
			sql+=" and summary like ('%"+Summary+"%')";
		}
		SQLQuery qu=super.getSession().createSQLQuery(sql);
		List<Bug> list=qu.addEntity(Bug.class).list();
		return list;
	}
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Bug instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Bug as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTestphase(Object testphase) {
		return findByProperty(TESTPHASE, testphase);
	}

	public List findByEnvironment(Object environment) {
		return findByProperty(ENVIRONMENT, environment);
	}

	public List findBySummary(Object summary) {
		return findByProperty(SUMMARY, summary);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findByAnalyse(Object analyse) {
		return findByProperty(ANALYSE, analyse);
	}

	public List findByAffixpath(Object affixpath) {
		return findByProperty(AFFIXPATH, affixpath);
	}

	public List findByGravitylevel(Object gravitylevel) {
		return findByProperty(GRAVITYLEVEL, gravitylevel);
	}

	public List findByReappearance(Object reappearance) {
		return findByProperty(REAPPEARANCE, reappearance);
	}

	public List findByQualitycharacter(Object qualitycharacter) {
		return findByProperty(QUALITYCHARACTER, qualitycharacter);
	}

	public List findByPhase(Object phase) {
		return findByProperty(PHASE, phase);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByModifytimes(Object modifytimes) {
		return findByProperty(MODIFYTIMES, modifytimes);
	}

	public List findByPriority(Object priority) {
		return findByProperty(PRIORITY, priority);
	}

	public List findByLastreply(Object lastreply) {
		return findByProperty(LASTREPLY, lastreply);
	}

	public List findAll() {
		log.debug("finding all Bug instances");
		try {
			String queryString = "from Bug";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findByModuleID(Long ModuleID) {
		log.debug("finding all Bug instances");
		try {
			String queryString = "from Bug where ModuleID="+ModuleID +"and ( Status<>4)";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public Bug merge(Bug detachedInstance) {
		log.debug("merging Bug instance");
		try {
			Bug result = (Bug) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Bug instance) {
		log.debug("attaching dirty Bug instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void set(Bug bug) {
		log.debug("attaching dirty Bug instance");
		try {
			getHibernateTemplate().update(bug);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public void attachClean(Bug instance) {
		log.debug("attaching clean Bug instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List findByModel(Long ModelId,Long FirstResult,Long MaxResults) {
		log.debug("attaching clean Bug instance");
		try {
			String sql="select a.* from  bug a left join  ProjectModel b on(b.ProjectModelID=a.ModuleID) " +
					" where ( b.ProjectModelID in ( select c.ProjectModelID from ProjectModel c start with c.projectmodelid="+ModelId+" connect by nocycle prior c.projectmodelid=c.FatherModelID) or a.moduleid="+ModelId+" )";
			sql+=" and a.Status<>4";
			
			SQLQuery qu=super.getSession().createSQLQuery(sql);
			qu.setFirstResult(new Integer(FirstResult.toString()));
			qu.setMaxResults(new Integer(MaxResults.toString()));
			List<Bug> list=qu.addEntity(Bug.class).list();
			return list;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	//查询特定BUG
	public List findByModelAndBugId(Long ModelId,Long bugid) {
		log.debug("attaching clean Bug instance");
		try {
			String sql="select a.* from  bug a left join  ProjectModel b on(b.ProjectModelID=a.ModuleID) " +
					" where ( b.ProjectModelID in ( select c.ProjectModelID from ProjectModel c start with c.projectmodelid="+ModelId+" connect by nocycle prior c.projectmodelid=c.FatherModelID) or  a.moduleid="+ModelId+" )";
			sql+=" and a.Status<>4";
			sql+=" and BugID="+bugid;
			SQLQuery qu=super.getSession().createSQLQuery(sql);
			List<Bug> list=qu.addEntity(Bug.class).list();
			return list;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	//无页码
	public List findByModel(Long ModelId) {
		log.debug("attaching clean Bug instance");
		try {
			String sql="select a.* from  bug a left join  ProjectModel b on(b.ProjectModelID=a.ModuleID) " +
					" where ( b.ProjectModelID in ( select c.ProjectModelID from ProjectModel c start with c.projectmodelid="+ModelId+" connect by nocycle prior c.projectmodelid=c.FatherModelID) or a.moduleid="+ModelId+" )";
			sql+=" and a.Status<>4";
			SQLQuery qu=super.getSession().createSQLQuery(sql);
			List<Bug> list=qu.addEntity(Bug.class).list();
			return list;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public List findByModelAndType(Long ModelId,Long FirstResult,Long MaxResults,Long Type) {
		log.debug("attaching clean Bug instance");
		try {
			String sql="select a.* from  bug a left join  ProjectModel b on(b.ProjectModelID=a.ModuleID) " +
			" where ( b.ProjectModelID in ( select c.ProjectModelID from ProjectModel c start with c.projectmodelid="+ModelId+" connect by nocycle prior c.projectmodelid=c.FatherModelID) or a.moduleid="+ModelId+" )";
			sql+=" and a.Status<>4";
			if(Type==0){
				sql+=" ORDER BY a.CreateTime DESC";
			}else if(Type==1){
				sql+=" ORDER BY a.Status DESC";
			}else if(Type==2){
				sql+=" ORDER BY a.Priority DESC";
			}else if(Type==3){
				sql+=" ORDER BY a.LastReply DESC";
			}
			SQLQuery qu=super.getSession().createSQLQuery(sql);
			qu.setFirstResult(new Integer(FirstResult.toString()));
			qu.setMaxResults(new Integer(MaxResults.toString()));
			List<Bug> list=qu.addEntity(Bug.class).list();
			return list;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	

	
	public static BugDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BugDAO) ctx.getBean("BugDAO");
	}
}