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
		//���� ����޸�ʱ��
		bug.setModifytimes(df.dateToDate(new Date()));
		//��ȡ�����״̬
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

		//�־û�
		bugDao.attachDirty(bug);
	}
	
	public void addBug(Bug bug) {
		//��ʼʱ��ȡϵͳʱ��
		bug.setCreatetime(df.dateToDate(new Date()));
		//������ʼֵ
		bug.setPhase(new Long(1));
		bug.setPriority(new Long(2));
		bug.setStatus(new Long(2));
		bug.setLastmodifytime(df.dateToDate(new Date()));
		bugDao.save(bug);
		
		//��ӷ���
		Bughistory bh=new Bughistory();
		bh.setBug(bug);
		bh.setCreatetime(df.dateToDate(new Date()));
		bh.setDescribe("�½�BUG");
		bh.setReply("11");
		bh.setStatus(new Long(2));
		bh.setStatusbefore(new Long(2));
		bh.setUserinfo(bug.getUserinfoByTester());
		
		bughistoryDao.save(bh);
	}
	public void addHistoryByConnerPer(Bug bug,Bughistory bh) {
		//������
		//����״̬
		Long Status=bug.getStatus();
		Long Testphase= bug.getTestphase();
		Date Testversion=bug.getTestversion();
		//�޸�Bug����׶κ͹رհ汾
		bug=bugDao.findById(bug.getBugid());
		bug.setTestphase(Testphase);
		bug.setTestversion(Testversion);
		//����״̬�޸�
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
		//���� ����޸�ʱ��
		bug.setModifytimes(new Date());
		bug.setLastmodifytime(new Date());
		bug.setStatus(Status);
		bugDao.set(bug);
		//����״̬
		//��ȡ֮ǰ��״̬
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
		//����ʱ��
		bh.setCreatetime(new Date());
		bughistoryDao.save(bh);
	}
	public void addHistoryByDeveloper(Bug bug,Bughistory bh) {
		//��������ӷ���
		//�޸�Bug����׶�
		Long Phase=bug.getPhase();
		bug=bugDao.findById(bug.getBugid());
		//״̬
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
		//����״̬
		//��ȡ֮ǰ��״̬
		//��ȡ֮ǰ��״̬
		Bughistory bhs=bughistoryDao.findTimeMax(bug.getBugid());
		if(bhs.getStatus()!=null){
			bh.setStatusbefore(bhs.getStatus());
		}else{
		bh.setStatusbefore(bug.getStatus());
		}
		
		//����ʱ��
		bh.setCreatetime(df.dateToDate(new Date()));
		bughistoryDao.save(bh);
		
		//����BUGʱ��״̬��������
		setTimeAndStatusToBug(bug);
	}
	public void addHistoryByItemManager(Bug bug,Bughistory bh) {
		//��Ŀ����ӷ���
		Long Priority=bug.getPriority();
		//�޸�Bug���ȼ�
		bug=bugDao.findById(bug.getBugid());
		if(!bug.getPriority().equals(Priority)){
			bug.setLastreply(new Long(8));
			bh.setReply("8");
			bug.setPriority(Priority);
		}
		bugDao.set(bug);
		//����״̬
		//��ȡ֮ǰ��״̬
		//��ȡ֮ǰ��״̬
		Bughistory bhs=bughistoryDao.findTimeMax(bug.getBugid());
		if(bhs.getStatus()!=null){
			bh.setStatusbefore(bhs.getStatus());
		}
		bh.setStatus(new Long(6));
		//����ʱ��
		bh.setCreatetime(df.dateToDate(new Date()));
		bughistoryDao.save(bh);
		
		//����BUGʱ��״̬��������
		setTimeAndStatusToBug(bug);
	}
	//ɾ��BUG
	public void delBug(Long bugid,Long userid) {
		// TODO Auto-generated method stub
		//��ȡBUG
		Bug bug=bugDao.findById(bugid);	
		//��ӷ���
		Bughistory bh=new Bughistory();
		bh.setBug(bug);
		bh.setCreatetime(df.dateToDate(new Date()));
		bh.setDescribe("ע��BUG");
		bh.setReply("13");
		bh.setStatus(new Long(4));
		//��ȡ֮ǰ��״̬
		Bughistory bhs=bughistoryDao.findTimeMax(bug.getBugid());
		bh.setStatusbefore(bhs.getStatus());
		bh.getUserinfo().setUserid(userid);
		
		//״̬����Ϊ4��ע��״̬
		bug.setStatus(new Long(4));
		//���� ����޸�ʱ��
		Date temp=df.dateToDate(new Date());
		bug.setModifytimes(temp);
		bug.setLastmodifytime(temp);
		//�־û�
		bug.setDetail("-.-");
		bug.setClosetime(df.dateToDate(new Date()));
		bugDao.set(bug);
		bughistoryDao.save(bh);
	}
	
	//��ѯBUG�Լ�����
	public Bug findByID(Long id) {
		//��ѯBUG��Ϣ
		Bug bug =bugDao.findById(id);
		//�ײ��ж�-BUG״̬�����Ƿ�ע������δ����
		if(bug.getStatus()==null){
			//��ѯ����
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
			//��ѯ����
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
	//�����Ӹ�ģ�飬����������ģ�鼯��
	public Map<String, List<Projectmodel>> searModelToThree(Long Model){
		Map<String, List<Projectmodel>>  map=new HashMap<String, List<Projectmodel>>();
		//������ǰģ�飬�Լ���ǰ��ģ��
		//����������ģ�飬����Ǳ�ɾ���ģ��򷵻�NULL
		List<Projectmodel> father=new ArrayList<Projectmodel>();
		List<Projectmodel> son=new ArrayList<Projectmodel>();
		Projectmodel pm=projectmodelDao.findById(Model);
		//��ѯ��ģ��(��ע��δ�������ѱ�����)
		List<Projectmodel> modelList=projectmodelDao.findByFatherModelID(Model);
		//��ģ�����list
		if(modelList!=null){
			pm.setFlag(true);
			for(Projectmodel b:modelList){
				//��Ѱ��ģ��
				Projectmodel sons=projectmodelDao.findById(b.getProjectmodelid());
				List<Projectmodel> sonList=projectmodelDao.findByFatherModelID(b.getProjectmodelid());
				if(sonList.size()>0){
					sons.setFlag(true);
				}else{
					sons.setFlag(false);
				}
				//��������ӽ�list
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
		//��NameΪ�գ��򵱸�ģ��Ϊ��Ŀģ���ѯ
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
	
	
	//��������ģ���Լ���ģ��
	public List<Bug> findByModel(Long Model,Long page,Long number) {
		List<Bug> bugList=new ArrayList<Bug>();
		//��ѯģ����������(��ע��δ�����ѱ�����)
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
	//ͨ��ģ�飬�Լ�BUGID�鿴
	public List<Bug> findBugByBUGID(Long Model,Long bugid){
		return bugDao.findByModelAndBugId(Model, bugid);
	}
	

	
	public void setBug(Bug bug) {
		//��ʼʱ��ȡϵͳʱ��
		bug.setCreatetime(df.dateToDate(new Date()));
		//������ʼֵ
		bug.setPhase(new Long(1));
		bug.setPriority(new Long(2));
		//����BUGʱ��״̬��������
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
		//������
		Long Maxlist=new Long(bugList.size());
		//���ҳ��
		Long Maxpage;
		if(Maxlist%number!=0){
			Maxpage=(Maxlist-(Maxlist%number))/number+1;
		}else{
			Maxpage=Maxlist/number;
		}
		return Maxpage;
	}
	
	//�޸�������(BUGid,�������ͣ�״̬������)
	public void setPrincipal(Long bugid,Long principal,Long userid) {
		// TODO Auto-generated method stub
		//����ID��ȡBUG
		Bug bug=bugDao.findById(bugid);
		//��ȡ�µ�������
		Userinfo user=userinfoDao.findById(principal);
		//�޸��µ�������
		bug.setUserinfoByPrincipal(user);
		bug.setLastreply(new Long(7));
		bug.setLastmodifytime(new Date());
		//����
		bugDao.merge(bug);
		//�����µķ���
		//�޸� �޸�����
		Bughistory bh=new Bughistory();
		//����
		//֮ǰ�ķ���״̬
		bh.getBug().setBugid(bug.getBugid());
		bh.setReply("7");
		bh.setDescribe("�޸�������");
		bh.setStatus(new Long(6));
		bh.setStatusbefore(bug.getStatus());
		user=userinfoDao.findById(userid);
		//������
		bh.setUserinfo(user);
		//��������
		bh.setCreatetime(df.dateToDate(new Date()));
		//�־û�
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
	//�������ģ��
	public List<Projectmodel> ProjectmodelfindByModel(Long ProID){
		return projectmodelDao.findByProjectIDAll(ProID);
	}
	public List<Userinfo> UserInfoFindByProjectID(Long ProID,Long type){
		//������������
		//������Ŀ�û���Ϣ
		List<Userinfo> ui=projectuserDao.findProAndType(ProID, type);
		
		return ui;
	}
	public Userinfo userFindByID(Long ID){
		return userinfoDao.findById(ID);
	}
	//���԰汾
	public void addBugEdition(Bugedition bugEdition) {
		bugeditionDao.save(bugEdition);
		
	}
	public List<Projectmodel> ProjectfindByAll(){
		return projectmodelDao.findByFatherModelID(null);
	}
	//��ȡҳ��
	public Long pageByModel(Long Model,Long number){
		List<Bug> bugList=new ArrayList<Bug>();
		//��ѯģ����������(��ע��δ�����ѱ�����)
		bugList=bugDao.findByModel(Model);
		//������
		Long Maxlist=new Long(bugList.size());
		//���ҳ��
		Long Maxpage;
		if(Maxlist%number!=0){
			Maxpage=(Maxlist-(Maxlist%number))/number+1;
		}else{
			Maxpage=Maxlist/number;
		}
		return Maxpage;
	}
	//��ȡҳ��
	public Long pageBySort(Long Model,Long number){
		List<Bug> bugList=new ArrayList<Bug>();
		//��ѯģ����������(��ע��δ�����ѱ�����)
		bugList=bugDao.findByModel(Model);
		//������
		Long Maxlist=new Long(bugList.size());
		//���ҳ��
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
	
	//������Ա���ɾ��
	public void batchProjectUser(List<Userinfo> user,Long proID,Long Type){
			//�����ݿ�Աȣ����ݿ��в�����,�ύ�����д���,��������ݿⲿ��
				//��������û�ID�� ��ѯ���ݿ�,�����ݿ��в����ڵĲ��ַ���
				for(Userinfo ui:user){
					boolean b=true;
					List<Projectuser> puserlist=projectuserDao.findProAndTypes(proID, Type);
					for(Projectuser pu:puserlist){
						//��ѯ�������ǰ�����ݿ�����ȵĲ��֣��򷵻�flase
						if(pu.getUserinfo().getUserid()==ui.getUserid()||pu.getUserinfo().getUserid().equals(ui.getUserid())){
							b=false;
						}
					}
					//���Ϊtrue,�������ݿ��д��ڣ������ݿ��в�����
					if(b==true){
						//��������ݿ��иò���
						Projectuser puesr=new Projectuser();
						Project pro=new  Project();
						pro.setProjectid(proID);
						puesr.setProject(pro);
						puesr.setProjectusertype(Type);
						puesr.setUserinfo(ui);
						projectuserDao.save(puesr);
					}
				}
			//�����ݿ�Աȣ����ݿ��д��ڣ��ύ�����в����ڣ���ɾ�����ݿⲿ��
				List<Projectuser> puserlist=projectuserDao.findProAndTypes(proID, Type);
				for(Projectuser pu:puserlist){
					boolean b=true;
					for(Userinfo ui:user){
						//��ѯ�������ǰ�����ݿ�����ȵĲ��֣��򷵻�flase
						if(pu.getUserinfo().getUserid()==ui.getUserid()||pu.getUserinfo().getUserid().equals(ui.getUserid())){
							b=false;
						}
					}
					//���Ϊtrue,�������ݿ��д��ڣ��������в�����
					if(b==true){
						//��ɾ�����ݿ��иò���
						Projectuser prouser=projectuserDao.findById(pu.getPuId());
						projectuserDao.delete(prouser);
					}
				}
	}
	//��ȡ�ⲿ��Ա
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
	//�����ֶ�
	public String findByDateIdAndValue(String DataID,String ItemValue){
		List<Basedataitem> list=basedataitemDao.findByDateIdAndValue(DataID, ItemValue);
		if(list.size()!=0){
			return list.get(0).getItemname();
		}else{
			return null;
		}
	}
	
	//---------------------------------------------------------------------------
	//�ұ���
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
	//�޸���Ա
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#findPerson(java.lang.Long, java.lang.Long)
	 */
	public Map<String, List<Userinfo>> findPerson(Long ProID,Long type){
		Map<String, List<Userinfo>> map=new HashMap<String, List<Userinfo>>();
//		//��ȡ��ǰ��Ŀ���õ���Ա
		Personlist=this.UserInfoFindByProjectID(ProID,type);

		
		//��ȡ����Ŀ֮�����Ա
		PersonAlllist=this.findAllByNoProID(ProID);
		
		
		//����������ȥ�����е���Ա
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
	
	//�����Ա
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#addPerson(java.lang.Long, java.util.List, java.util.List)
	 */
	public Map<String, List<Userinfo>> addPerson(Long userid,List<String> PersonAll,List<String> Person){

		//----δѡ�е���
		PersonAlllist=new ArrayList<Userinfo>();
		for(int i=0;i<PersonAll.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(PersonAll.get(i)));
			PersonAlllist.add(ui);
		}
		//----��ѡ�е���
		Personlist=new ArrayList<Userinfo>();
		for(int i=0;i<Person.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(Person.get(i)));
			Personlist.add(ui);
		}
		//��ӵ���ѡ�е���
		Personlist.add(this.userFindByID(userid));
		//��δѡ�е���ɾ��
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
	
	//ɾ����Ա
	/* (non-Javadoc)
	 * @see org.bugManage.service.impl.itemPerson#delPerson(java.lang.Long, java.util.List, java.util.List)
	 */
	public Map<String, List<Userinfo>> delPerson(Long userid,List<String> PersonAll,List<String> Person){
		//----δѡ�е���
		PersonAlllist=new ArrayList<Userinfo>();
		for(int i=0;i<PersonAll.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(PersonAll.get(i)));
			PersonAlllist.add(ui);
		}
		//----��ѡ�е���
		Personlist=new ArrayList<Userinfo>();
		for(int i=0;i<Person.size();i++){
			Userinfo ui=new Userinfo();
			ui=this.userFindByID(new Long(Person.get(i)));
			Personlist.add(ui);
		}
		//��ӵ�δѡ�е���
		PersonAlllist.add(this.userFindByID(userid));
		//�ӱ�ѡ�е���ɾ��
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
		//��������Ϊ��
		promodel.setBugs(null);
		//��������
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
		return "��ӳɹ�";
	}
}

