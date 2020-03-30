package org.bugManage.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bugManage.dao.BasedataDAO;
import org.bugManage.dao.BasedataitemDAO;
import org.bugManage.dao.BugDAO;
import org.bugManage.dao.BugeditionDAO;
import org.bugManage.dao.BughistoryDAO;
import org.bugManage.dao.ProjectmodelDAO;
import org.bugManage.dao.ProjectuserDAO;
import org.bugManage.dao.UserinfoDAO;
import org.bugManage.entity.Basedata;
import org.bugManage.entity.Basedataitem;
import org.bugManage.entity.Bug;
import org.bugManage.entity.Bugedition;
import org.bugManage.entity.Bughistory;
import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;
import org.bugManage.entity.Projectuser;
import org.bugManage.entity.Userinfo;
import org.bugManage.format.dateFormat;


import org.bugManage.service.BugService;

public class BugServiceImpl implements BugService {
	private List<Projectmodel> Molist=new ArrayList<Projectmodel>();
	private dateFormat df=new dateFormat();
	private UserinfoDAO userinfoDao;
	private BugDAO bugDao;
	private BughistoryDAO bughistoryDao;
	private BugeditionDAO bugeditionDao;
	private ProjectmodelDAO projectmodelDao;
	private BasedataDAO basedataDao;
	private BasedataitemDAO basedataitemDao;
	private ProjectuserDAO projectuserDao;
	public UserinfoDAO getUserinfoDao() {
		return userinfoDao;
	}
	public void setUserinfoDao(UserinfoDAO userinfoDao) {
		this.userinfoDao = userinfoDao;
	}
	public ProjectuserDAO getProjectuserDao() {
		return projectuserDao;
	}
	public void setProjectuserDao(ProjectuserDAO projectuserDao) {
		this.projectuserDao = projectuserDao;
	}
	public ProjectmodelDAO getProjectmodelDao() {
		return projectmodelDao;
	}
	public void setProjectmodelDao(ProjectmodelDAO projectmodelDao) {
		this.projectmodelDao = projectmodelDao;
	}
	public BugeditionDAO getBugeditionDao() {
		return bugeditionDao;
	}
	public void setBugeditionDao(BugeditionDAO bugeditionDao) {
		this.bugeditionDao = bugeditionDao;
	}
	public BugDAO getBugDao() {
		return bugDao;
	}
	public BughistoryDAO getBughistoryDao() {
		return bughistoryDao;
	}
	public void setBugDao(BugDAO bugDao) {
		this.bugDao = bugDao;
	}
	public void setBughistoryDao(BughistoryDAO bughistoryDao) {
		this.bughistoryDao = bughistoryDao;
	}

	@SuppressWarnings({ "unchecked", "unused", "null" })
	private void setTimeAndStatusToBug(Bug bug){
		//更改 最后修改时间
		bug.setModifytimes(df.dateToDate(new Date()));
		//读取最后反馈状态
		Bughistory bh=bughistoryDao.findTimeMax(bug.getBugid());
		if(bh!=null){
			bug.setStatus(bh.getStatus());
			bug.setLastreply(new Long(bh.getReply()));
			bug.setLastmodifytime(bh.getCreatetime());
		}else{
			bug.setStatus(new Long(2));
			bug.setLastreply(new Long(bh.getReply()));
			bug.setLastmodifytime(df.dateToDate(new Date()));
		}

		//持久化
		bugDao.attachDirty(bug);
	}
	
	public void addBug(Bug bug) {
		//开始时间取系统时间
		bug.setCreatetime(df.dateToDate(new Date()));
		//其他初始值
		bug.setPhase(new Long(1));
		bug.setPriority(new Long(2));
		bug.setStatus(new Long(2));
		bug.setLastmodifytime(df.dateToDate(new Date()));
		bugDao.save(bug);
		
		//添加反馈
		Bughistory bh=new Bughistory();
		bh.setBug(bug);
		bh.setCreatetime(df.dateToDate(new Date()));
		bh.setDescribe("新建BUG");
		bh.setReply("11");
		bh.setStatus(new Long(2));
		bh.setStatusbefore(new Long(2));
		bh.setUserinfo(bug.getUserinfoByTester());
		
		bughistoryDao.save(bh);
	}
	public void addHistoryByConnerPer(Bug bug,Bughistory bh) {
		//测试人
		//反馈状态
		Long Status=bug.getStatus();
		Long Testphase= bug.getTestphase();
		Date Testversion=bug.getTestversion();
		//修改Bug引入阶段和关闭版本
		bug=bugDao.findById(bug.getBugid());
		bug.setTestphase(Testphase);
		bug.setTestversion(Testversion);
		//反馈状态修改
		if(Status==5){
			bh.setReply("9");
			bug.setLastreply(new Long(9));
		}else if(Status==6){
			bh.setReply("10");
			bug.setLastreply(new Long(10));
		}else if(Status==2){
			bh.setReply("11");
			bug.setLastreply(new Long(11));
		}else if(Status==3){
			bh.setReply("12");
			bug.setLastreply(new Long(12));
		}else if(Status==4){
			bh.setReply("13");
			bug.setLastreply(new Long(13));
		}else{
			bh.setReply("1");
			bug.setLastreply(new Long(1));
		}
		//更改 最后修改时间
		bug.setModifytimes(new Date());
		bug.setLastmodifytime(new Date());
		bug.setStatus(Status);
		bugDao.set(bug);
		//反馈状态
		//获取之前的状态
		Bughistory bhs=bughistoryDao.findTimeMax(bug.getBugid());
		if(bhs.getStatus()!=null){
			bh.setStatusbefore(bhs.getStatus());
		}else{
			bh.setStatusbefore(new Long(1));
		}
		if(bug.getStatus()==null){
			bh.setStatus(bhs.getStatus());
		}else{
			bh.setStatus(bug.getStatus());
		}
		Userinfo ui=userinfoDao.findById(bh.getUserinfo().getUserid());
		bh.setUserinfo(ui);
		//反馈时间
		bh.setCreatetime(new Date());
		bughistoryDao.save(bh);
	}
	public void addHistoryByDeveloper(Bug bug,Bughistory bh) {
		//开发人添加反馈
		//修改Bug引入阶段
		Long Phase=bug.getPhase();
		bug=bugDao.findById(bug.getBugid());
		//状态
		String Reply=bh.getReply();
		if(Reply.equals("2")){
			bh.setStatus(new Long(6));
			bug.setStatus(new Long(6));
		}else if(Reply.equals("3")){
			bh.setStatus(new Long(3));
			bug.setStatus(new Long(3));
		}else if(Reply.equals("3")){
			bh.setStatus(new Long(3));
			bug.setStatus(new Long(3));
		}else if(Reply.equals("6")){
			bh.setStatus(new Long(5));
			bug.setStatus(new Long(5));
		}
		bugDao.set(bug);
		//反馈状态
		//获取之前的状态
		//获取之前的状态
		Bughistory bhs=bughistoryDao.findTimeMax(bug.getBugid());
		if(bhs.getStatus()!=null){
			bh.setStatusbefore(bhs.getStatus());
		}else{
		bh.setStatusbefore(bug.getStatus());
		}
		
		//反馈时间
		bh.setCreatetime(df.dateToDate(new Date()));
		bughistoryDao.save(bh);
		
		//调用BUG时间状态调整方法
		setTimeAndStatusToBug(bug);
	}
	public void addHistoryByItemManager(Bug bug,Bughistory bh) {
		//项目人添加反馈
		Long Priority=bug.getPriority();
		//修改Bug优先级
		bug=bugDao.findById(bug.getBugid());
		if(!bug.getPriority().equals(Priority)){
			bug.setLastreply(new Long(8));
			bh.setReply("8");
			bug.setPriority(Priority);
		}
		bugDao.set(bug);
		//反馈状态
		//获取之前的状态
		//获取之前的状态
		Bughistory bhs=bughistoryDao.findTimeMax(bug.getBugid());
		if(bhs.getStatus()!=null){
			bh.setStatusbefore(bhs.getStatus());
		}
		bh.setStatus(new Long(6));
		//反馈时间
		bh.setCreatetime(df.dateToDate(new Date()));
		bughistoryDao.save(bh);
		
		//调用BUG时间状态调整方法
		setTimeAndStatusToBug(bug);
	}
	//删除BUG
	public void delBug(Long bugid,Long userid) {
		// TODO Auto-generated method stub
		//获取BUG
		Bug bug=bugDao.findById(bugid);	
		//添加反馈
		Bughistory bh=new Bughistory();
		bh.setBug(bug);
		bh.setCreatetime(df.dateToDate(new Date()));
		bh.setDescribe("注销BUG");
		bh.setReply("13");
		bh.setStatus(new Long(4));
		//获取之前的状态
		Bughistory bhs=bughistoryDao.findTimeMax(bug.getBugid());
		bh.setStatusbefore(bhs.getStatus());
		bh.getUserinfo().setUserid(userid);
		
		//状态设置为4，注销状态
		bug.setStatus(new Long(4));
		//更改 最后修改时间
		Date temp=df.dateToDate(new Date());
		bug.setModifytimes(temp);
		bug.setLastmodifytime(temp);
		//持久化
		bug.setDetail("-.-");
		bug.setClosetime(df.dateToDate(new Date()));
		bugDao.set(bug);
		bughistoryDao.save(bh);
	}
	
	//查询BUG以及反馈
	public Bug findByID(Long id) {
		//查询BUG信息
		Bug bug =bugDao.findById(id);
		//底层判断-BUG状态必须是非注销，非未创建
		if(bug.getStatus()==null){
			//查询反馈
			Bughistory bughistory=new Bughistory();
			bughistory.setBug(bug);
			List<Bughistory> bughistoryList=bughistoryDao.findByProperty("bug.bugid", bug.getBugid());
			for(Bughistory hi:bughistoryList){
				bug.getBughistories().add(hi);
			}
				return bug;
		}else 
		if(bug.getStatus()==1||bug.getStatus()==4){
			return null;
		}else{
			//查询反馈
			Bughistory bughistory=new Bughistory();
			bughistory.setBug(bug);
			List<Bughistory> bughistoryList=bughistoryDao.findByBugID(bug.getBugid());
			for(Bughistory hi:bughistoryList){
				if(hi.getStatus()!=null){
					hi.setStatusstr(findByDateIdAndValue("Status", hi.getStatus().toString()));
				}
				if(hi.getStatusbefore()!=null){
					hi.setStatusbeforestr(findByDateIdAndValue("Status", hi.getStatusbefore().toString()));
				}
				if(hi.getReply()!=null){
					hi.setReplystr(findByDateIdAndValue("Reply", hi.getReply()));
				}
			}
			bug.setBughistorieslist(bughistoryList);
			return bug;
		}
	}

	private int number=0;
	//检索子父模块，返回所有子模块集合
	public Map<String, List<Projectmodel>> searModelToThree(Long Model){
		Map<String, List<Projectmodel>>  map=new HashMap<String, List<Projectmodel>>();
		//检索当前模块，以及当前子模块
		//检索搜索的模块，如果是被删除的，则返回NULL
		List<Projectmodel> father=new ArrayList<Projectmodel>();
		List<Projectmodel> son=new ArrayList<Projectmodel>();
		Projectmodel pm=projectmodelDao.findById(Model);
		//查询子模块(被注销未创建的已被过滤)
		List<Projectmodel> modelList=projectmodelDao.findByFatherModelID(Model);
		//子模块放入list
		if(modelList!=null){
			pm.setFlag(true);
			for(Projectmodel b:modelList){
				//搜寻子模块
				Projectmodel sons=projectmodelDao.findById(b.getProjectmodelid());
				List<Projectmodel> sonList=projectmodelDao.findByFatherModelID(b.getProjectmodelid());
				if(sonList.size()>0){
					sons.setFlag(true);
				}else{
					sons.setFlag(false);
				}
				//将子类添加进list
				son.add(sons);
			}
		}else{
			pm.setFlag(false);
		}
		father.add(pm);
		map.put("father", father);
		map.put("son", son);
		return map;
	}
	
	public Projectmodel findModelIdToModel(Long model){
		return projectmodelDao.findById(model);
	}
	
	public Map<String, List<Projectmodel>> modelFindByModel(Long Model,Long ProID){
		Map<String, List<Projectmodel>> maplist=new HashMap<String, List<Projectmodel>>();
		Projectmodel promodel=new Projectmodel();
		//当Name为空，则当该模块为项目模块查询
		if(Model==null||Model.equals("")||Model==0){
			Projectmodel pmodel=new Projectmodel();
			Project pro=new Project();
			pro.setProjectid(ProID);
			pmodel.setProjectmodel(null);
			List<Projectmodel> list=projectmodelDao.findByExample(pmodel);
			if(list.size()!=0){
				pmodel=list.get(0);
			}
			Model=pmodel.getProjectmodelid();
		}
			maplist=searModelToThree(Model);
		return maplist;
	}
	
	
	//将检索该模块以及子模块
	public List<Bug> findByModel(Long Model,Long page,Long number) {
		List<Bug> bugList=new ArrayList<Bug>();
		//查询模块下面数据(被注销未创建已被过滤)
		page--;
		bugList=bugDao.findByModel(Model,page*number,number);
		return bugList;
	}
	
	public List<Bug> findByType(Long BugID,Long Tester,Long Principal,Long Status,String Summary,String StarDate,String OverDate,Long page,Long number,Long Model) {
		page--;
		if((BugID==null||BugID==0)&&
				(Tester==null||Tester.equals(""))&&
				(Principal==null||Principal.equals(""))&&
				(Status==null||Status==0)&&
				(Summary==null||Summary.equals(""))
				&&(StarDate==null||StarDate.equals(""))&&(OverDate==null||OverDate.equals(""))){
				Long pages=page;
				Long numbers=number;
			return bugDao.findByModel(Model,(pages*numbers),numbers);
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date ODate=null;
			Date SDate=null;
			if(!(StarDate==null||OverDate==null)){
				if(!(StarDate.equals("")||OverDate.equals(""))){
					try {			
						SDate=sdf.parse(StarDate);
						ODate = sdf.parse(OverDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			List<Bug> bugList=bugDao.findByType(BugID,Tester,Principal,Status,Summary,SDate,ODate,Model,page*number,number);
			return bugList;
		}
	}
	//通过模块，以及BUGID查看
	public List<Bug> findBugByBUGID(Long Model,Long bugid){
		return bugDao.findByModelAndBugId(Model, bugid);
	}
	

	
	public void setBug(Bug bug) {
		//开始时间取系统时间
		bug.setCreatetime(df.dateToDate(new Date()));
		//其他初始值
		bug.setPhase(new Long(1));
		bug.setPriority(new Long(2));
		//调用BUG时间状态调整方法
		bugDao.attachDirty(bug);
	}
	

	public List<Bug> softBug(Long Model,Long page, Long number, Long Type) {
		List<Bug> bugList=new ArrayList<Bug>();
		page--;
		bugList=bugDao.findByModelAndType(Model,page*number,number,Type);
		return bugList;
	}
	
	public Long pagesoftBug(Long Model,Long number,Long Type){
		List<Bug> bugList=new ArrayList<Bug>();
		bugList=bugDao.findByModel(Model);
		//数据数
		Long Maxlist=new Long(bugList.size());
		//最大页码
		Long Maxpage;
		if(Maxlist%number!=0){
			Maxpage=(Maxlist-(Maxlist%number))/number+1;
		}else{
			Maxpage=Maxlist/number;
		}
		return Maxpage;
	}
	
	//修改责任人(BUGid,反馈类型，状态，描述)
	public void setPrincipal(Long bugid,Long principal,Long userid) {
		// TODO Auto-generated method stub
		//根据ID获取BUG
		Bug bug=bugDao.findById(bugid);
		//获取新的责任人
		Userinfo user=userinfoDao.findById(principal);
		//修改新的责任人
		bug.setUserinfoByPrincipal(user);
		bug.setLastreply(new Long(7));
		bug.setLastmodifytime(new Date());
		//保存
		bugDao.merge(bug);
		//建立新的反馈
		//修改 修改责任
		Bughistory bh=new Bughistory();
		//反馈
		//之前的反馈状态
		bh.getBug().setBugid(bug.getBugid());
		bh.setReply("7");
		bh.setDescribe("修改责任人");
		bh.setStatus(new Long(6));
		bh.setStatusbefore(bug.getStatus());
		user=userinfoDao.findById(userid);
		//反馈人
		bh.setUserinfo(user);
		//反馈类型
		bh.setCreatetime(df.dateToDate(new Date()));
		//持久化
		bughistoryDao.save(bh);


	}
	public List<Userinfo> findAllUserInfo(){
		return userinfoDao.findAll();
	}
	public BasedataDAO getBasedataDao() {
		return basedataDao;
	}
	public void setBasedataDao(BasedataDAO basedataDao) {
		this.basedataDao = basedataDao;
	}
	public BasedataitemDAO getBasedataitemDao() {
		return basedataitemDao;
	}
	public void setBasedataitemDao(BasedataitemDAO basedataitemDao) {
		this.basedataitemDao = basedataitemDao;
	}
	public List<Basedataitem> findByBasedataitem(String Basedataitem) {
		return 	basedataitemDao.findByDateId(Basedataitem);
	}
	public List<Bugedition> findEdition(Long ProID) {
		return bugeditionDao.findByProperty("project.projectid", ProID);
	}
	//获得所有模块
	public List<Projectmodel> ProjectmodelfindByModel(Long ProID){
		return projectmodelDao.findByProjectIDAll(ProID);
	}
	public List<Userinfo> UserInfoFindByProjectID(Long ProID,Long type){
		//设置搜索条件
		//返回项目用户信息
		List<Userinfo> ui=projectuserDao.findProAndType(ProID, type);
		
		return ui;
	}
	public Userinfo userFindByID(Long ID){
		return userinfoDao.findById(ID);
	}
	//测试版本
	public void addBugEdition(Bugedition bugEdition) {
		bugeditionDao.save(bugEdition);
		
	}
	public List<Projectmodel> ProjectfindByAll(){
		return projectmodelDao.findByFatherModelID(null);
	}
	//获取页码
	public Long pageByModel(Long Model,Long number){
		List<Bug> bugList=new ArrayList<Bug>();
		//查询模块下面数据(被注销未创建已被过滤)
		bugList=bugDao.findByModel(Model);
		//数据数
		Long Maxlist=new Long(bugList.size());
		//最大页码
		Long Maxpage;
		if(Maxlist%number!=0){
			Maxpage=(Maxlist-(Maxlist%number))/number+1;
		}else{
			Maxpage=Maxlist/number;
		}
		return Maxpage;
	}
	//获取页码
	public Long pageBySort(Long Model,Long number){
		List<Bug> bugList=new ArrayList<Bug>();
		//查询模块下面数据(被注销未创建已被过滤)
		bugList=bugDao.findByModel(Model);
		//数据数
		Long Maxlist=new Long(bugList.size());
		//最大页码
		Long Maxpage;
		if(Maxlist%number!=0){
			Maxpage=(Maxlist-(Maxlist%number))/number+1;
		}else{
			Maxpage=Maxlist/number;
		}
		return Maxpage;
	}
	public Long pageByType(Long BugID,Long Tester,Long Principal,Long Status,String Summary,String StarDate,String OverDate,Long number,Long Model) {
		Long Maxpage;
		if((BugID==null||BugID==0)&&
				(Tester==null||Tester.equals(""))&&
				(Principal==null||Principal.equals(""))&&
				(Status==null||Status==0)&&
				(Summary==null||Summary.equals(""))
				&&(StarDate==null||StarDate.equals(""))&&(OverDate==null||OverDate.equals(""))){
			Maxpage=new Long(bugDao.findByModel(Model).size());
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date ODate=null;
			Date SDate=null;
			if(!(StarDate==null||OverDate==null)){
				if(!(StarDate.equals("")||OverDate.equals(""))){
					try {			
						SDate=sdf.parse(StarDate);
						ODate = sdf.parse(OverDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			Maxpage=new Long(bugDao.findByTypeToPage(BugID,Tester,Principal,Status,Summary,SDate,ODate,Model).size());
		}
		if(Maxpage%number==0){
			Maxpage=Maxpage/number;
		}else{
			Maxpage=(Maxpage-(Maxpage%number))/number+1;
		}
		return Maxpage;
	}
	
	//批量人员添加删除
	public void batchProjectUser(List<Userinfo> user,Long proID,Long Type){
			//与数据库对比，数据库中不存在,提交请求中存在,则添加数据库部分
				//按请求的用户ID， 查询数据库,将数据库中不存在的部分返回
				for(Userinfo ui:user){
					boolean b=true;
					List<Projectuser> puserlist=projectuserDao.findProAndTypes(proID, Type);
					for(Projectuser pu:puserlist){
						//查询，如果当前与数据库有相等的部分，则返回flase
						if(pu.getUserinfo().getUserid()==ui.getUserid()||pu.getUserinfo().getUserid().equals(ui.getUserid())){
							b=false;
						}
					}
					//如果为true,表明数据库中存在，而数据库中不存在
					if(b==true){
						//则添加数据库中该部分
						Projectuser puesr=new Projectuser();
						Project pro=new  Project();
						pro.setProjectid(proID);
						puesr.setProject(pro);
						puesr.setProjectusertype(Type);
						puesr.setUserinfo(ui);
						projectuserDao.save(puesr);
					}
				}
			//与数据库对比，数据库中存在，提交请求中不存在，则删除数据库部分
				List<Projectuser> puserlist=projectuserDao.findProAndTypes(proID, Type);
				for(Projectuser pu:puserlist){
					boolean b=true;
					for(Userinfo ui:user){
						//查询，如果当前与数据库有相等的部分，则返回flase
						if(pu.getUserinfo().getUserid()==ui.getUserid()||pu.getUserinfo().getUserid().equals(ui.getUserid())){
							b=false;
						}
					}
					//如果为true,表明数据库中存在，而请求中不存在
					if(b==true){
						//则删除数据库中该部分
						Projectuser prouser=projectuserDao.findById(pu.getPuId());
						projectuserDao.delete(prouser);
					}
				}
	}
	//获取外部人员
	public List<Userinfo> findAllByNoProID(Long ProID){
		List<Projectuser> prouser=projectuserDao.findByProjectId(ProID);
		return userinfoDao.findAllByNoProID(prouser);
	}
	
	
	public Long TypeFindByProIDAndUser(Long ProID, Long User) {
		List<Projectuser> pu=projectuserDao.findProAndUser(ProID, User);
		if(pu.size()!=0){
			return pu.get(0).getProjectusertype();
		}
		return null;
	}
	//解析字段
	public String findByDateIdAndValue(String DataID,String ItemValue){
		List<Basedataitem> list=basedataitemDao.findByDateIdAndValue(DataID, ItemValue);
		if(list.size()!=0){
			return list.get(0).getItemname();
		}else{
			return null;
		}
	}
	
	//---------------------------------------------------------------------------
	//右边栏
	private List<Userinfo> Personlist;
	private List<Userinfo>  PersonAlllist;
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#getPersonlist()
	 */
	public List<Userinfo> getPersonlist() {
		return Personlist;
	}
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#setPersonlist(java.util.List)
	 */
	public void setPersonlist(List<Userinfo> personlist) {
		Personlist = personlist;
	}

	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#getPersonAlllist()
	 */
	public List<Userinfo> getPersonAlllist() {
		return PersonAlllist;
	}
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#setPersonAlllist(java.util.List)
	 */
	public void setPersonAlllist(List<Userinfo> personAlllist) {
		PersonAlllist = personAlllist;
	}
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#getBugService()
	 */
	private Map<String,List<Userinfo>> map;

	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#getMap()
	 */
	public Map<String, List<Userinfo>> getMap() {
		return map;
	}
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#setMap(java.util.Map)
	 */
	public void setMap(Map<String, List<Userinfo>> map) {
		this.map = map;
	}
	//修改人员
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#findPerson(java.lang.Long, java.lang.Long)
	 */
	public Map<String, List<Userinfo>> findPerson(Long ProID,Long type){
		Map<String, List<Userinfo>> map=new HashMap<String, List<Userinfo>>();
//		//获取当前项目所用的人员
		Personlist=this.UserInfoFindByProjectID(ProID,type);

		
		//获取该项目之外的人员
		PersonAlllist=this.findAllByNoProID(ProID);
		
		
		//在所有人中去掉已有的人员
		for(int i=0;i<Personlist.size();i++){
			for(int j=0;j<PersonAlllist.size();j++){
				if(Personlist.get(i).getUserid()==PersonAlllist.get(j).getUserid()){
					PersonAlllist.remove(j);
				}
			}
		} 
		delectLazy();
		map.put("Person", Personlist);
		map.put("PersonAll", PersonAlllist);
		return (map);
	}
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#delectLazy()
	 */
	public void delectLazy(){
		for(Userinfo u:Personlist){
			u.setBugsForTester(null);
			u.setBugsForPrincipal(null);
			u.setBughistories(null);
			u.setProjectusers(null);
		}
		for(Userinfo u:PersonAlllist){
			u.setBugsForTester(null);
			u.setBugsForPrincipal(null);
			u.setBughistories(null);
			u.setProjectusers(null);
		}
	}
	
	//添加人员
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#addPerson(java.lang.Long, java.util.List, java.util.List)
	 */
	public Map<String, List<Userinfo>> addPerson(Long userid,List<String> PersonAll,List<String> Person){

		//----未选中的人
		PersonAlllist=new ArrayList<Userinfo>();
		for(int i=0;i<PersonAll.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(PersonAll.get(i)));
			PersonAlllist.add(ui);
		}
		//----被选中的人
		Personlist=new ArrayList<Userinfo>();
		for(int i=0;i<Person.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(Person.get(i)));
			Personlist.add(ui);
		}
		//添加到被选中的人
		Personlist.add(this.userFindByID(userid));
		//从未选中的人删除
		for(int i=0;i<PersonAlllist.size();i++){
			if(PersonAlllist.get(i).getUserid()==userid||PersonAlllist.get(i).getUserid().equals(userid)){
				PersonAlllist.remove(i);
			}
		}
		map=new HashMap<String, List<Userinfo>>();
		delectLazy();
		map.put("Person", Personlist);
		map.put("PersonAll", PersonAlllist);
		return (map);
	}
	
	//删除人员
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#delPerson(java.lang.Long, java.util.List, java.util.List)
	 */
	public Map<String, List<Userinfo>> delPerson(Long userid,List<String> PersonAll,List<String> Person){
		//----未选中的人
		PersonAlllist=new ArrayList<Userinfo>();
		for(int i=0;i<PersonAll.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(PersonAll.get(i)));
			PersonAlllist.add(ui);
		}
		//----被选中的人
		Personlist=new ArrayList<Userinfo>();
		for(int i=0;i<Person.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(Person.get(i)));
			Personlist.add(ui);
		}
		//添加到未选中的人
		PersonAlllist.add(this.userFindByID(userid));
		//从被选中的人删除
		for(int i=0;i<Personlist.size();i++){
			if(Personlist.get(i).getUserid()==userid||Personlist.get(i).getUserid().equals(userid)){
				Personlist.remove(i);
			}
		}
		map=new HashMap<String, List<Userinfo>>();
		delectLazy();
		map.put("Person", Personlist);
		map.put("PersonAll", PersonAlllist);
		return (map);
	}
	public Map<String,Projectmodel> findModelIdToModels(Long Model){
		Map< String , Projectmodel> modelmap=new HashMap<String, Projectmodel>();
		Projectmodel promodel=findModelIdToModel(Model);
		//设置子项为空
		promodel.setBugs(null);
		//检索父类
		if(promodel.getProjectmodel()!=null){
			Projectmodel fatherpromodel=promodel.getProjectmodel();
			fatherpromodel.setBugs(null);
			modelmap.put("fathermodel", fatherpromodel);
		}
		modelmap.put("model", promodel);
		return modelmap;
	}
	public String gobatchProjectUser( Long ProID, Long Type,List<String> Person) {
		// TODO Auto-generated method stub
		Personlist=new ArrayList<Userinfo>();
		for(int i=0;i<Person.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(Person.get(i)));
			Personlist.add(ui);
		}
		this.batchProjectUser(Personlist, ProID, Type);
		return "添加成功";
	}
}

