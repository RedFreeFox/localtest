package org.bugManage.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Basedata entity. @author MyEclipse Persistence Tools
 */

public class Basedata implements java.io.Serializable {

	// Fields

	private String dataid;
	private String info;
	private Set basedataitems = new HashSet(0);

	// Constructors

	/** default constructor */
	public Basedata() {
	}

	/** full constructor */
	public Basedata(String info, Set basedataitems) {
		this.info = info;
		this.basedataitems = basedataitems;
	}

	// Property accessors

	public String getDataid() {
		return this.dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Set getBasedataitems() {
		return this.basedataitems;
	}

	public void setBasedataitems(Set basedataitems) {
		this.basedataitems = basedataitems;
	}

}