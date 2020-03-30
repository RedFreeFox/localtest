package org.bugManage.dao;



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @category ReportDAO
 * @author gikoukou
 *	@date 2012-02-22
 */
public class ReportDAO extends HibernateDaoSupport {
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
	
	//按模块查询Bug数量
	public List<Object[]> findByModule(Long fatherId) {
		try {
			String queryString = "select bug.projectmodel.projectmodelname,count(*) as total from Bug as bug where bug.projectmodel.projectmodel.projectmodelid=? group by bug.projectmodel.projectmodelname";
			return getHibernateTemplate().find(queryString,new Object[]{fatherId});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	//按天查询Bug数量
	public List<Object[]> findByDay() {
		try {
			String queryString = "select to_char(bug.createtime,'mm-dd'),count(*)  from Bug as bug  group by bug.createtime";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//按月查询Bug数量
	public List<Object[]> findByMonth() {
		try {
			String queryString = "select to_char(b.createtime,'yyyy-mm'),count(*)  from Bughistory as b  group by to_char(b.createtime,'yyyy-mm')";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	
	
}
