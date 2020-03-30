package org.bugManage.entity;

/**
 * Projectuser entity. @author MyEclipse Persistence Tools
 */

public class Projectuser implements java.io.Serializable {

	// Fields

	private Long puId;
	private Project project = new Project();
	private Userinfo userinfo = new Userinfo();
	private Long projectusertype;

	// Constructors

	/** default constructor */
	public Projectuser() {
	}

	/** full constructor */
	public Projectuser(Project project, Userinfo userinfo, Long projectusertype) {
		this.project = project;
		this.userinfo = userinfo;
		this.projectusertype = projectusertype;
	}

	// Property accessors

	public Long getPuId() {
		return this.puId;
	}

	public void setPuId(Long puId) {
		this.puId = puId;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Userinfo getUserinfo() {
		return this.userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public Long getProjectusertype() {
		return this.projectusertype;
	}

	public void setProjectusertype(Long projectusertype) {
		this.projectusertype = projectusertype;
	}

}