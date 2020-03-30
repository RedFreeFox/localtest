package org.bugManage.entity;

import java.util.Date;


import org.bugManage.format.dateFormat;

/**
 * Bugedition entity. @author MyEclipse Persistence Tools
 */

public class Bugedition implements java.io.Serializable {

	// Fields

	private Long bugeditionid;
	private Project project;
	private Date bugeditionname;
	private String bugeditionnameStr;

	// Constructors

	public String getBugeditionnameStr() {
		return bugeditionnameStr;
	}

	public void setBugeditionnameStr(String bugeditionnameStr) {
		this.bugeditionnameStr = bugeditionnameStr;
	}

	/** default constructor */
	public Bugedition() {
	}

	/** full constructor */
	public Bugedition(Project project, Date bugeditionname) {
		this.project = project;
		this.bugeditionname = bugeditionname;
	}

	// Property accessors

	public Long getBugeditionid() {
		return this.bugeditionid;
	}

	public void setBugeditionid(Long bugeditionid) {
		this.bugeditionid = bugeditionid;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getBugeditionname() {
		return this.bugeditionname;
	}

	public void setBugeditionname(Date bugeditionname) {
		dateFormat df=new dateFormat();
		this.bugeditionnameStr=df.dateToString(bugeditionname);
		this.bugeditionname = bugeditionname;
	}

}