package org.bugManage.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Projectmodel entity. @author MyEclipse Persistence Tools
 */

public class Projectmodel implements java.io.Serializable {

	// Fields
	
	private Long projectmodelid;
	//父类
	private Projectmodel projectmodel ;
	private Project project = new Project();
	private String projectmodelname;
	private Long status;
	//子类

	private Set projectmodels = new HashSet(0);
	private Set bugs = new HashSet(0);
	//是否存在子类
	private boolean flag=false; 


	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isFlag() {
		return flag;
	}


	/** default constructor */
	public Projectmodel() {
	}

	/** full constructor */
	public Projectmodel(Projectmodel projectmodel, Project project,
			String projectmodelname, Long status, Set projectmodels, Set bugs) {
		this.projectmodel = projectmodel;
		this.project = project;
		this.projectmodelname = projectmodelname;
		this.status = status;
		this.projectmodels = projectmodels;
		this.bugs = bugs;
	}

	// Property accessors

	public Long getProjectmodelid() {
		return this.projectmodelid;
	}

	public void setProjectmodelid(Long projectmodelid) {
		this.projectmodelid = projectmodelid;
	}

	public Projectmodel getProjectmodel() {
		return this.projectmodel;
	}

	public void setProjectmodel(Projectmodel projectmodel) {
		this.projectmodel = projectmodel;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectmodelname() {
		return this.projectmodelname;
	}

	public void setProjectmodelname(String projectmodelname) {
		this.projectmodelname = projectmodelname;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Set getProjectmodels() {
		return this.projectmodels;
	}

	public void setProjectmodels(Set projectmodels) {
		this.projectmodels = projectmodels;
	}

	public Set getBugs() {
		return this.bugs;
	}

	public void setBugs(Set bugs) {
		this.bugs = bugs;
	}

}