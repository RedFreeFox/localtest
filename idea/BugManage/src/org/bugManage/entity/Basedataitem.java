package org.bugManage.entity;

/**
 * Basedataitem entity. @author MyEclipse Persistence Tools
 */

public class Basedataitem implements java.io.Serializable {

	// Fields

	private Long itemid;
	private Basedata basedata;
	private String itemname;
	private String itemvalue;

	// Constructors

	/** default constructor */
	public Basedataitem() {
	}

	/** full constructor */
	public Basedataitem(Basedata basedata, String itemname, String itemvalue) {
		this.basedata = basedata;
		this.itemname = itemname;
		this.itemvalue = itemvalue;
	}

	// Property accessors

	public Long getItemid() {
		return this.itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Basedata getBasedata() {
		return this.basedata;
	}

	public void setBasedata(Basedata basedata) {
		this.basedata = basedata;
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemvalue() {
		return this.itemvalue;
	}

	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}

}