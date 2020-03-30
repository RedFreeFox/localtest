package org.bugManage.entity;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role implements java.io.Serializable {

	// Fields
	//ID
	private Long roleid;
	//权限ID
	private Long limitid;
	//权限描述
	private String muenname;
	//对于action
	private String action;
	//属性
	private String param;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(Long limitid) {
		this.limitid = limitid;
	}

	/** full constructor */
	public Role(Long limitid, String muenname, String action, String param) {
		this.limitid = limitid;
		this.muenname = muenname;
		this.action = action;
		this.param = param;
	}

	// Property accessors

	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Long getLimitid() {
		return this.limitid;
	}

	public void setLimitid(Long limitid) {
		this.limitid = limitid;
	}

	public String getMuenname() {
		return this.muenname;
	}

	public void setMuenname(String muenname) {
		this.muenname = muenname;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}