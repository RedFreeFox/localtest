package org.bugManage.service;

import java.util.Date;
import java.util.List;
import java.util.Map;


import org.bugManage.entity.Basedata;
import org.bugManage.entity.Basedataitem;
import org.bugManage.entity.Bug;
import org.bugManage.entity.Bugedition;
import org.bugManage.entity.Bughistory;
import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;
import org.bugManage.entity.Userinfo;


public interface BugService {
//	浏览人员
	//	查看BUG详细 以及反馈
	public Bug findByID(Long id);
	//	按条件查找
	public List<Bug> findByType(Long BugID,Long Tester,Long Principal,Long Status,String Summary,String StarDate,String OverDate,Long page,Long number,Long Model);
	//	按模块查找
	public List<Bug> findByModel(Long Model,Long page,Long number);
	//获取排序页码
	public Long pagesoftBug(Long Model,Long number,Long Type);
	//	排序(优先，创建时间，状态，反馈类型)
	public List<Bug> softBug(Long Model,Long page,Long number,Long Type);
//测试人员(非自己的BUG)
	//新建BUG
	public void addBug(Bug bug);

//测试人员(自己的BUG)
	//	修改BUG
	public void setBug(Bug bug);
	//	删除BUG（更改状态为： 注销）
	public void delBug(Long bugid,Long userid);
	//	BUG反馈（修改责任人,引入阶段,关闭版本,修改Bug状态）
	public void addHistoryByConnerPer(Bug bug,Bughistory bh);
//开发人员(责任人)
	//	BUG反馈(修改责任人,引入阶段)
	public void addHistoryByDeveloper(Bug bug,Bughistory bh);
//项目经理
	//	删除BUG（更改状态为： 注销）
	//等同测试人员
	//	BUG反馈（修改责任人,引入阶段,优先级）
	public void addHistoryByItemManager(Bug bug,Bughistory bh);
	

	//显示引入阶段和版本
	public List<Bugedition> findEdition(Long ProID);
	//显示BUG信息
	public List<Basedataitem> findByBasedataitem(String Basedataitem);
	//获得所有模块(项目下)
	public List<Projectmodel> ProjectmodelfindByModel(Long ProID);
	//获得所有模块
	public List<Projectmodel> ProjectfindByAll();
	//获取项目开发/测试/浏览/经理人员
	public List<Userinfo> UserInfoFindByProjectID(Long ProID,Long type);
	//获取所有人
	public List<Userinfo> findAllUserInfo();
	//获取外部人员
	public List<Userinfo> findAllByNoProID(Long ProID);
	//新建测试版本
	public void addBugEdition(Bugedition bugEdition);
	//通过用户ID查询特定人
	public Userinfo userFindByID(Long ID);
	//批量处理项目人员添加删除
	public void batchProjectUser(List<Userinfo> user,Long ProID,Long Type);
	//通过模块名返回当前模块以及子模块
	public Map<String, List<Projectmodel>> modelFindByModel(Long Model,Long ProID);
	//获取按模块的页码
	public Long pageByModel(Long Model,Long number);
	//获取按排序的页码
	public Long pageBySort(Long Model,Long number);
	//获取按查找的页码
	public Long pageByType(Long Model,Long Tester,Long Principal,Long Status,String Summary,String StarDate,String OverDate,Long number,Long priID);
	//查询模块
	public Projectmodel findModelIdToModel(Long Model);
	//按模块ID查询其BUG
	public List<Bug> findBugByBUGID(Long Model,Long bugid);
	//获取用户项目中的身份
	public Long TypeFindByProIDAndUser(Long ProID,Long User);
	//解析字段
	public String findByDateIdAndValue(String DataID,String ItemValue);
	//修改责任人(BUGid,责任人,操纵人)
	public void setPrincipal(Long bugid,Long principal,Long userid);
	
	public Map<String, List<Userinfo>> findPerson(Long ProID,Long type);
	public Map<String, List<Userinfo>> addPerson(Long userid,List<String> PersonAll,List<String> Person);
	public Map<String, List<Userinfo>> delPerson(Long userid,List<String> PersonAll,List<String> Person);
	//通过模块名返回当前模块以及父模块
	public Map<String,Projectmodel> findModelIdToModels(Long Model);
	//批量处理项目人员添加删除
	public String gobatchProjectUser(Long proID,Long type,List<String> Person);
}
