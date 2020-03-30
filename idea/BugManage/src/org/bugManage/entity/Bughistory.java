package org.bugManage.entity;

import java.util.Date;

import org.bugManage.format.dateFormat;

/**
 * Bughistory entity. @author MyEclipse Persistence Tools
 */

public class Bughistory implements java.io.Serializable {

	// Fields

	private Long historyid;
	private Bug bug = new Bug();
	private Userinfo userinfo = new Userinfo();
	private Long statusbefore;
	private String statusbeforestr;
	private String describe;
	private Long status;
	private String statusstr;
	public String getStatusbeforestr() {
		return statusbeforestr;
	}

	public void setStatusbeforestr(String statusbeforestr) {
		this.statusbeforestr = statusbeforestr;
	}

	public String getStatusstr() {
		return statusstr;
	}

	public void setStatusstr(String statusstr) {
		this.statusstr = statusstr;
	}

	private String reply;
	private String replystr;
	public String getReplystr() {
		return replystr;
	}

	public void setReplystr(String replystr) {
		this.replystr = replystr;
	}

	private Date createtime;
	private String createtimeStr;

	// Constructors

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	/** default constructor */
	public Bughistory() {
	}

	/** full constructor */
	public Bughistory(Bug bug, Userinfo userinfo, Long statusbefore,
			String describe, Long status, String reply, Date createtime) {
		this.bug = bug;
		this.userinfo = userinfo;
		this.statusbefore = statusbefore;
		this.describe = describe;
		this.status = status;
		this.reply = reply;
		this.createtime = createtime;
	}

	// Property accessors

	public Long getHistoryid() {
		return this.historyid;
	}

	public void setHistoryid(Long historyid) {
		this.historyid = historyid;
	}

	public Bug getBug() {
		return this.bug;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}

	public Userinfo getUserinfo() {
		return this.userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public Long getStatusbefore() {
		return this.statusbefore;
	}

	public void setStatusbefore(Long statusbefore) {
		this.statusbefore = statusbefore;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		dateFormat df=new dateFormat();
		this.createtimeStr=df.dateToString(createtime);
		this.createtime = createtime;
	}

}