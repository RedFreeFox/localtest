package org.bugManage.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Userinfo entity. @author MyEclipse Persistence Tools
 */

public class Userinfo implements java.io.Serializable {

	// Fields

	private Long userid;
	private String name;
	private String password;
	private String email;
	private String telephone;
	private String department;
	private Long type;
	private Long actived;
	private Date registtime;
	private Date lastlogintime;
	private Set projectusers = new HashSet(0);
	private Set bughistories = new HashSet(0);
	private Set bugsForPrincipal = new HashSet(0);
	private Set bugsForTester = new HashSet(0);

	// Constructors

	/** default constructor */
	public Userinfo() {
	}

	/** minimal constructor */
	public Userinfo(String name) {
		this.name = name;
	}

	/** full constructor */
	public Userinfo(String name, String password, String email,
			String telephone, String department, Long type, Long actived,
			Date registtime, Date lastlogintime, Set projectusers,
			Set bughistories, Set bugsForPrincipal, Set bugsForTester) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.telephone = telephone;
		this.department = department;
		this.type = type;
		this.actived = actived;
		this.registtime = registtime;
		this.lastlogintime = lastlogintime;
		this.projectusers = projectusers;
		this.bughistories = bughistories;
		this.bugsForPrincipal = bugsForPrincipal;
		this.bugsForTester = bugsForTester;
	}

	// Property accessors

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getActived() {
		return this.actived;
	}

	public void setActived(Long actived) {
		this.actived = actived;
	}

	public Date getRegisttime() {
		return this.registtime;
	}

	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}

	public Date getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public Set getProjectusers() {
		return this.projectusers;
	}

	public void setProjectusers(Set projectusers) {
		this.projectusers = projectusers;
	}

	public Set getBughistories() {
		return this.bughistories;
	}

	public void setBughistories(Set bughistories) {
		this.bughistories = bughistories;
	}

	public Set getBugsForPrincipal() {
		return this.bugsForPrincipal;
	}

	public void setBugsForPrincipal(Set bugsForPrincipal) {
		this.bugsForPrincipal = bugsForPrincipal;
	}

	public Set getBugsForTester() {
		return this.bugsForTester;
	}

	public void setBugsForTester(Set bugsForTester) {
		this.bugsForTester = bugsForTester;
	}

}