package org.bugManage.service;

import java.util.List;



/**
 * @category ReportService
 * @author gikoukou
 * @date 2012-02-22
 * 
 */
public interface ReportService {
	
	//通过给定项目编号统计所属模块的bug数量
	public List<Object[]> findReport(Long fatherId);
	
	//通过天统计Bug数量
	public List<Object[]> findByDay();
	
	//通过月统计Bug数量
	public List<Object[]> findByMonth();
	
	
	

}
