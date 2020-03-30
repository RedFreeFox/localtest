package org.bugManage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bugManage.dao.ReportDAO;
import org.bugManage.service.ReportService;

public class ReportServiceImpl implements ReportService {
	
	private ReportDAO reportDao;
	

	public void setReportDao(ReportDAO reportDao) {
		this.reportDao = reportDao;
	}

	private List<Object[]> list = new ArrayList<Object[]>();
	//通过给定项目编号统计所属模块的bug数量
	public List<Object[]> findReport(Long fatherId) {
		
		list = reportDao.findByModule(fatherId);
		return list;
	}


	//通过天统计Bug数量
	public List<Object[]> findByDay() {
		list = reportDao.findByDay();
		return list;
	}

	//通过月统计Bug数量
	public List<Object[]> findByMonth() {
		list = reportDao.findByMonth();
		return list;
	}

}
