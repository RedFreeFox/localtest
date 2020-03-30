package org.bugManage.service;

import java.util.List;



/**
 * @category ReportService
 * @author gikoukou
 * @date 2012-02-22
 * 
 */
public interface ReportService {
	
	//ͨ��������Ŀ���ͳ������ģ���bug����
	public List<Object[]> findReport(Long fatherId);
	
	//ͨ����ͳ��Bug����
	public List<Object[]> findByDay();
	
	//ͨ����ͳ��Bug����
	public List<Object[]> findByMonth();
	
	
	

}
