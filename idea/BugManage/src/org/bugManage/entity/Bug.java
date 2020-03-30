package org.bugManage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bugManage.format.dateFormat;

/**
 * Bug entity. @author MyEclipse Persistence Tools
 */

public class Bug implements java.io.Serializable {

	// Fields

	private Long bugid;
	private Userinfo userinfoByTester=new Userinfo();
	private Project project=new Project();
	private Userinfo userinfoByPrincipal=new Userinfo();
	private Projectmodel projectmodel=new Projectmodel();
	//测试阶段
	private Long testphase;
	private String testphasestr;
	private Date testversion;
	private Date closeversion;
	private Date createtime;
	private String createtimeStr;
	private Date closetime;
	private String environment;
	private String summary;
	private String detail;
	private String detaildepart;
	public String getDetaildepart() {
		return detaildepart;
	}

	public void setDetaildepart(String detaildepart) {
		this.detaildepart = detaildepart;
	}

	private String analyse;
	private String affixpath;
	private Long gravitylevel;
	private String gravitylevelstr;
	private Long reappearance;
	private String reappearancestr;
	private Long qualitycharacter;
	private String qualitycharacterstr;
	private Long phase;
	private String phasestr;
	//状态
	private Long status;
	private String statusstr;
	private Date modifytimes;
	//优先级
	private Long priority;
	private String prioritystr;
	private Date lastmodifytime;
	private String lastmodifytimestr;
	public String getLastmodifytimestr() {
		return lastmodifytimestr;
	}

	public void setLastmodifytimestr(String lastmodifytimestr) {
		this.lastmodifytimestr = lastmodifytimestr;
	}

	//反馈类型
	private Long lastreply;
	private String lastreplystr;
	private Set bughistories = new HashSet(0);
	private List<Bughistory> bughistorieslist=new ArrayList<Bughistory>();
	public List<Bughistory> getBughistorieslist() {
		return bughistorieslist;
	}

	public void setBughistorieslist(List<Bughistory> bughistorieslist) {
		this.bughistorieslist = bughistorieslist;
	}

	//是否是该BUG的测试人
	private boolean testerif=false;
	//是否是该BUG的责任人
	private boolean principalif=false;
	// Constructors
	public String getPrioritystr() {
		return prioritystr;
	}

	public void setPrioritystr(String prioritystr) {
		this.prioritystr = prioritystr;
	}
	public String getPhasestr() {
		return phasestr;
	}

	public void setPhasestr(String phasestr) {
		this.phasestr = phasestr;
	}
	public String getTestphasestr() {
		return testphasestr;
	}

	public void setTestphasestr(String testphasestr) {
		this.testphasestr = testphasestr;
	}

	public String getGravitylevelstr() {
		return gravitylevelstr;
	}

	public void setGravitylevelstr(String gravitylevelstr) {
		this.gravitylevelstr = gravitylevelstr;
	}

	public String getReappearancestr() {
		return reappearancestr;
	}

	public void setReappearancestr(String reappearancestr) {
		this.reappearancestr = reappearancestr;
	}

	public String getQualitycharacterstr() {
		return qualitycharacterstr;
	}

	public void setQualitycharacterstr(String qualitycharacterstr) {
		this.qualitycharacterstr = qualitycharacterstr;
	}
	public String getStatusstr() {
		return statusstr;
	}

	public void setStatusstr(String statusstr) {
		this.statusstr = statusstr;
	}



	public String getLastreplystr() {
		return lastreplystr;
	}

	public void setLastreplystr(String lastreplystr) {
		this.lastreplystr = lastreplystr;
	}





	/** default constructor */
	public Bug() {
	}

	/** full constructor */
	public Bug(Userinfo userinfoByTester, Project project,
			Userinfo userinfoByPrincipal, Projectmodel projectmodel,
			Long testphase, Date testversion, Date closeversion,
			Date createtime, Date closetime, String environment,
			String summary, String detail, String analyse, String affixpath,
			Long gravitylevel, Long reappearance, Long qualitycharacter,
			Long phase, Long status, Date modifytimes, Long priority,
			Date lastmodifytime, Long lastreply, Set bughistories) {
		this.userinfoByTester = userinfoByTester;
		this.project = project;
		this.userinfoByPrincipal = userinfoByPrincipal;
		this.projectmodel = projectmodel;
		this.testphase = testphase;
		this.testversion = testversion;
		this.closeversion = closeversion;
		this.createtime = createtime;
		this.closetime = closetime;
		this.environment = environment;
		this.summary = summary;
		this.detail = detail;
		this.analyse = analyse;
		this.affixpath = affixpath;
		this.gravitylevel = gravitylevel;
		this.reappearance = reappearance;
		this.qualitycharacter = qualitycharacter;
		this.phase = phase;
		this.status = status;
		this.modifytimes = modifytimes;
		this.priority = priority;
		this.lastmodifytime = lastmodifytime;
		this.lastreply = lastreply;
		this.bughistories = bughistories;
	}

	// Property accessors

	public Long getBugid() {
		return this.bugid;
	}

	public void setBugid(Long bugid) {
		this.bugid = bugid;
	}

	public Userinfo getUserinfoByTester() {
		return this.userinfoByTester;
	}

	public void setUserinfoByTester(Userinfo userinfoByTester) {
		this.userinfoByTester = userinfoByTester;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Userinfo getUserinfoByPrincipal() {
		return this.userinfoByPrincipal;
	}

	public void setUserinfoByPrincipal(Userinfo userinfoByPrincipal) {
		this.userinfoByPrincipal = userinfoByPrincipal;
	}

	public Projectmodel getProjectmodel() {
		return this.projectmodel;
	}

	public void setProjectmodel(Projectmodel projectmodel) {
		this.projectmodel = projectmodel;
	}

	public Long getTestphase() {
		return this.testphase;
	}

	public void setTestphase(Long testphase) {
		this.testphase = testphase;
	}

	public Date getTestversion() {
		return this.testversion;
	}

	public void setTestversion(Date testversion) {
		this.testversion = testversion;
	}

	public Date getCloseversion() {
		return this.closeversion;
	}

	public void setCloseversion(Date closeversion) {
		this.closeversion = closeversion;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		dateFormat df=new dateFormat();
		this.createtimeStr=df.dateToString(createtime);
		this.createtime = createtime;
	}
	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public Date getClosetime() {
		return this.closetime;
	}

	public void setClosetime(Date closetime) {
		this.closetime = closetime;
	}

	public String getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		if(detail!=null){
		if(detail.length()>28){
			this.detaildepart=detail.substring(0, 25);
		}else{
			this.detaildepart=detail.substring(0, detail.length()-1);
		}
		}
		this.detail = detail;
	}

	public String getAnalyse() {
		return this.analyse;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}

	public String getAffixpath() {
		return this.affixpath;
	}

	public void setAffixpath(String affixpath) {
		this.affixpath = affixpath;
	}

	public Long getGravitylevel() {
		return this.gravitylevel;
	}

	public void setGravitylevel(Long gravitylevel) {
		this.gravitylevel = gravitylevel;
	}

	public Long getReappearance() {
		return this.reappearance;
	}

	public void setReappearance(Long reappearance) {
		this.reappearance = reappearance;
	}

	public Long getQualitycharacter() {
		return this.qualitycharacter;
	}

	public void setQualitycharacter(Long qualitycharacter) {
		this.qualitycharacter = qualitycharacter;
	}

	public Long getPhase() {
		return this.phase;
	}

	public void setPhase(Long phase) {
		this.phase = phase;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}



	public Date getModifytimes() {
		return modifytimes;
	}

	public void setModifytimes(Date modifytimes) {
		this.modifytimes = modifytimes;
	}

	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Date getLastmodifytime() {
		return this.lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime) {
		dateFormat df=new dateFormat();
		this.lastmodifytimestr=df.dateToString(lastmodifytime);
		this.lastmodifytime = lastmodifytime;
	}

	public Long getLastreply() {
		return this.lastreply;
	}

	public void setLastreply(Long lastreply) {
		this.lastreply = lastreply;
	}

	public Set getBughistories() {
		return this.bughistories;
	}

	public void setBughistories(Set bughistories) {
		this.bughistories = bughistories;
	}
	public boolean isTesterif() {
		return testerif;
	}

	public void setTesterif(boolean testerif) {
		this.testerif = testerif;
	}

	public boolean isPrincipalif() {
		return principalif;
	}

	public void setPrincipalif(boolean principalif) {
		this.principalif = principalif;
	}
}