package org.bugManage.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import org.bugManage.format.dateFormat;


/**
 * Project entity. @author MyEclipse Persistence Tools
 */

public class Project implements java.io.Serializable {

	// Fields

	private Long projectid;
	private String projectname;
	private Date starttime;
	private Date forefinishtime;
	private Date finishtime;
	private Long status;
	private Set bugeditions = new HashSet(0);
	private Set bugs = new HashSet(0);
	private Set projectmodels = new HashSet(0);
	private Set projectusers = new HashSet(0);
//	private dateFormat format;
//	private String startTime;
//	private String endTime;
	

	// Constructors

//	public dateFormat getFormat() {
//		return format;
//	}
//
//	public void setFormat(dateFormat format) {
//		this.format = format;
//	}
//
//	public String getStartTime() {
//		return this.format.dateToString(this.getStarttime());
//	}
//
//	public void setStartTime(String startTime) {
//		this.setStarttime(this.format.stringToDate(startTime));
//		this.startTime = startTime;
//	}
//
//	public String getEndTime() {
//		return this.format.dateToString(this.getForefinishtime());
//	}
//
//	public void setEndTime(String endTime) {
//		this.setForefinishtime(this.format.stringToDate(endTime));
//		this.endTime = endTime;
//	}

	/** default constructor */
	public Project() {
	}

	/** full constructor */
	public Project(String projectname, Date starttime, Date forefinishtime,
			Date finishtime, Long status, Set bugeditions, Set bugs,
			Set projectmodels, Set projectusers) {
		this.projectname = projectname;
		this.starttime = starttime;
		this.forefinishtime = forefinishtime;
		this.finishtime = finishtime;
		this.status = status;
		this.bugeditions = bugeditions;
		this.bugs = bugs;
		this.projectmodels = projectmodels;
		this.projectusers = projectusers;
	}

	// Property accessors

	public Long getProjectid() {
		return this.projectid;
	}

	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		
		this.starttime = starttime;
	}

	public Date getForefinishtime() {
		return this.forefinishtime;
	}

	public void setForefinishtime(Date forefinishtime) {
		this.forefinishtime = forefinishtime;
	}

	public Date getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Set getBugeditions() {
		return this.bugeditions;
	}

	public void setBugeditions(Set bugeditions) {
		this.bugeditions = bugeditions;
	}

	public Set getBugs() {
		return this.bugs;
	}

	public void setBugs(Set bugs) {
		this.bugs = bugs;
	}

	public Set getProjectmodels() {
		return this.projectmodels;
	}

	public void setProjectmodels(Set projectmodels) {
		this.projectmodels = projectmodels;
	}

	public Set getProjectusers() {
		return this.projectusers;
	}

	public void setProjectusers(Set projectusers) {
		this.projectusers = projectusers;
	}

}