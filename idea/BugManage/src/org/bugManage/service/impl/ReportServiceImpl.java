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
	//ͨ��������Ŀ���ͳ������ģ���bug����
	public List<Object[]> findReport(Long fatherId) {
		
		list = reportDao.findByModule(fatherId);
		return list;
	}


	//ͨ����ͳ��Bug����
	public List<Object[]> findByDay() {
		list = reportDao.findByDay();
		return list;
	}

	//ͨ����ͳ��Bug����
	public List<Object[]> findByMonth() {
		list = reportDao.findByMonth();
		return list;
	}

}
