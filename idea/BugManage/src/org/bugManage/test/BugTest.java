package org.bugManage.test;



import java.util.Date;
import java.util.List;

import org.bugManage.entity.Basedataitem;
import org.bugManage.entity.Bug;
import org.bugManage.entity.Bughistory;
import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;
import org.bugManage.entity.Userinfo;
import org.bugManage.entity.Projectuser;
import org.bugManage.service.BugService;
import org.bugManage.service.ProjectModelService;
import org.bugManage.service.UserInfoService;
import org.bugManage.service.ProjectService;
import org.bugManage.service.ProjectUserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BugTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	

//	@Test//��ҳģ���ѯ����-���Գɹ�
//	public void sort(){
//		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
//		BugService bs=(BugService)ctx.getBean("bugService");
//		List<Bug> Bug=bs.softBug(new Long(100), 1, 20, 1);
//		if(Bug!=null){
//			for(Bug b:Bug){
//				System.out.println(b.getSummary());
//			}
//		}else{
//			System.out.println("�����Ϊ��");
//		}
//	}
//	

//	@Test//��ҳģ���ѯ����-���Գɹ�
//	public void sort(){
//		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
//		BugService bs=(BugService)ctx.getBean("bugService");
//		List<Bug> Bug=bs.softBug(new Long(100), 1, 20, 1);
//		if(Bug!=null){
//			for(Bug b:Bug){
//				System.out.println(b.getSummary());
//			}
//		}else{
//			System.out.println("�����Ϊ��");
//		}
//	}
	

	
	@SuppressWarnings("unchecked")
//	@Test//��ѯ��ϸBUG��Ϣ
//	public void findByID(){
//		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
//		BugService bs=(BugService)ctx.getBean("bugService");
//		Bug bug=bs.findByID(new Long(10001));
//		System.out.println("BUG��Ϣ");
//		System.out.println(bug.getSummary());
//		List<Bughistory> bh=new ArrayList<Bughistory>(bug.getBughistories());
//		for(Bughistory bugh:bh){
//			System.out.println(bugh.getHistoryid());
//		}
//	}
	
	
//	@Test
//	//ɾ��
//	public void del(){
//		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
//		BugService bs=(BugService)ctx.getBean("bugService");
//		bs.delBug(new Long(10012),new Long(10002));
//	}
//	
	@Test//�½�
	public void add(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		BugService bs=(BugService)ctx.getBean("bugService");
		Bug bug=new Bug();
		bug.getProject().setProjectid(new Long(10001));
		bug.getProjectmodel().setProjectmodelid(new Long(101));
		bug.setTestphase(new Long(1));
		bug.setCreatetime(new Date());
		bug.setClosetime(new Date());
		bug.getUserinfoByTester().setUserid(new Long(10002));
		bug.getUserinfoByPrincipal().setUserid(new Long(10004));
		bug.setSummary("-------");
		bug.setDetail("-.-");
		bug.setAffixpath("1");
		bug.setGravitylevel(new Long(1));
		bug.setReappearance(new Long(1));
		bug.setQualitycharacter(new Long(1));
		bug.setPhase(new Long(1));
		bug.setStatus(new Long(1));
		bs.addBug(bug);
	}

	//��ѯ������-�������
//	@Test
//	public void findByType2(){
//		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
//		BugService bs=(BugService)ctx.getBean("bugService");
//		List<Bug> list=bs.findByType(null,null,null,null,null,null, null,1,10,new Long(10001));
//		for(Bug b:list){
//			System.out.println(b.getSummary());
//		}
//	}
//	@Test//����
//	@Test
//	public void findByType2(){
//		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
//		BugService bs=(BugService)ctx.getBean("bugService");
//		List<Bug> list=bs.findByType(null,null,null,null,null,null, null,1,10,new Long(10001));
//		for(Bug b:list){
//			System.out.println(b.getSummary());
//		}
//	}
	@Test//����
	public void addHd1(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		BugService bs=(BugService)ctx.getBean("bugService");
		//ID,����׶Σ��汾����������(���޸�������֤���ѽ����ע��)
		//����׶�,�رհ汾,�޸�Bug״̬
		Bug bug=new Bug();
		bug.setStatus(new Long(1));
		bug.setBugid(new Long(10001));
		bug.setPhase(new Long(1));
		bug.setTestversion(new Date());
		Bughistory bh=new Bughistory();
		bh.setBug(bug);
		bh.setReply("1");
		bh.getUserinfo().setUserid(new Long(10002));
		bh.setDescribe("-.-");
		bh.setStatus(new Long(3));
		bs.addHistoryByConnerPer(bug, bh);
		//�����ˣ���������
	}
	@Test//����
	public void addHd2(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		BugService bs=(BugService)ctx.getBean("bugService");
		//ID,����׶Σ��汾����������(���޸�������֤���ѽ����ע��)
		Bug bug=new Bug();
		bug.setBugid(new Long(10001));
		bug.setPhase(new Long(1));
		Bughistory bh=new Bughistory();
		bh.setBug(bug);
		bh.setReply("1");
		bh.getUserinfo().setUserid(new Long(10002));
		bh.setDescribe("-.-!!!");
		bh.setStatus(new Long(3));
		bs.addHistoryByDeveloper(bug, bh);
		//�����ˣ���������
	}
	@Test//����
	public void addHd3(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		BugService bs=(BugService)ctx.getBean("bugService");
		//ID,����׶�,���ȼ����汾����������(���޸�������֤���ѽ����ע��)
		Bug bug=new Bug();
		bug.setBugid(new Long(10001));
		bug.setPhase(new Long(1));
		bug.setPriority(new Long(1));
		Bughistory bh=new Bughistory();
		bh.setBug(bug);
		bh.setReply("1");
		bh.getUserinfo().setUserid(new Long(10002));
		bh.setDescribe("-.-!!!");
		bh.setStatus(new Long(3));
		bs.addHistoryByItemManager(bug, bh);
		//�����ˣ���������
	}
	@Test//�ֶ���Ϣ��
	public void finddata(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		BugService bs=(BugService)ctx.getBean("bugService");
		List<Basedataitem> list =bs.findByBasedataitem("Phase");
		
		for(Basedataitem b:list){
			System.out.println(b.getItemname());
		}
	}
	@Test//��Ϣ��
	public void UserInfoFindByProjectID(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		BugService bs=(BugService)ctx.getBean("bugService");
		List<Userinfo> list=bs.UserInfoFindByProjectID(new Long(10001),new Long(1));
		
		for(Userinfo b:list){
			System.out.println(b.getName());
		}
	}

	@Test
	public void findPersonUserinfo(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
		Userinfo user=Us.findById(new Long(10001));
		System.out.println("�û���Ϣ"+user.getName()+user.getDepartment());
	}
	@Test
	public void updatePersonUserifo(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
		Userinfo user=Us.findById(new Long(10001));
		user.setName("kity");
		user.setDepartment("������");
		Us.updatePersonUserifo(user);
		System.out.println(user.getDepartment());
	}
	@Test
	public void updatePwd(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
		Userinfo user=Us.findById(new Long(10001));
		user.setPassword("123");
		Us.updatePwd(user);
		System.out.println(user.getPassword());
	}
	@Test
	public void addUser(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
		Userinfo user=new Userinfo();
		user.setName("nana");
		user.setPassword("123");
		user.setDepartment("������");
		user.setEmail("123@qq.com");
		Us.addUser(user);
	}




	@Test
	public void findAll(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
		List<Userinfo> list=Us.findAll();
		if(list!=null){
			for(Userinfo u:list){
				System.out.println(u.getName());
			}
		}
	}
	   @Test
	    public void updateUserInfo(){
			ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
			UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
			Userinfo user=Us.findById(new Long(10001));
			user.setTelephone("15897651367");
			user.setDepartment("������");
			user.setEmail("21316456@164.com");
			user.setPassword("456");
			Us.updateUserInfo(user);
			System.out.println(user.getTelephone());
	    }
	   @Test
	   public void delUser(){
			ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
			UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
			Userinfo user=Us.findById(new Long(10001));
			user.setActived(new Long(1));
			Us.del(user);
			System.out.println(user.getActived());
	   }

		@Test//��ҳ---User
		public void Page(){
			ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
			UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
			List<Userinfo> list=Us.findPage(4,3);
			Long pageMax=Us.findPageMax(3);
			if(list!=null){
				for(Userinfo u:list){
					System.out.println(u.getName());
				}
			}else{
				System.out.println("��ҳ��");
			}
	        System.out.println(pageMax);
		}
	   
	   @Test
	   public void login(){
			ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
			UserInfoService Us=(UserInfoService)ctx.getBean("userinfoService");
			Userinfo user = new Userinfo();
			user.setName("admin");
			user.setPassword("admin");
			user.setActived(new Long(1));
			List<Userinfo> list = Us.login(user);
			if (list.size()==0) {
				System.out.println("ddddd");
			}
	   }

	
	@Test
	public void addproject(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectService ps = (ProjectService) ctx.getBean("projectService");
		Project project = new Project();
		project.setProjectname("���¹���ϵͳ");
		project.setStarttime(null);
		project.setForefinishtime(null);
		project.setFinishtime(null);
		project.setStatus(new Long(0));
		ps.addProject(project);
		System.out.println("successful!");
	}
	
	@Test
	public void modfiyproject(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectService ps = (ProjectService) ctx.getBean("projectService");
		Project project = new Project();
		project.setProjectid(new Long(10005));
		project.setProjectname("Person Resouces");
		project.setStarttime(null);
		project.setForefinishtime(null);
		project.setFinishtime(null);
		project.setStatus(new Long(1));
		ps.modifyProject(project);
		System.out.println("successful!");
	}
	
	@Test
	public void findproject(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectService ps = (ProjectService) ctx.getBean("projectService");
		Project project = new Project();
		project.setProjectname("B");
//		project.setStatus(new Long(1));
		ps.findProject(project);
		System.out.println("successful!");
	}
	
	
	
	
	
	
	@Test
	public void findprojectuser(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectUserService ps = (ProjectUserService) ctx.getBean("projectuserService");
		ProjectService pe = (ProjectService) ctx.getBean("projectService");
		Projectuser user = null;
//		user = ps.findByProjectUserId(new Long(5)).get(0);
		System.out.println(user.getProjectusertype());
//		Project project = pe.findById(user.getProject().getProjectid());
//		System.out.println(project.getProjectname());
		System.out.println("successful!");
	}
	
	
	//�����û���Ų�ѯ��Ŀ��Ϣ
	@Test
	public void findByUserId(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectUserService ps = (ProjectUserService) ctx.getBean("projectuserService");
		ProjectService pe = (ProjectService) ctx.getBean("projectService");
		Project user = null;
		user = pe.findByModelId(new Long(10002));
		System.out.println(user.getProjectname());
	}
	
	//������Ŀ��Ų�ѯ��Ŀ��Ա��Ϣ
	@Test
	public void findByprojectid(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectUserService ps = (ProjectUserService) ctx.getBean("projectuserService");
		ProjectUserService pe = (ProjectUserService) ctx.getBean("projectuserService");
		Projectuser user = null;
		for (int i = 0; i < 4; i++) {
			String str = "";
			List<Object[]> pu = (List<Object[]>)pe.findByProjectId(new Long(10001),new Long(i));
			for (Object[] objects : pu) {
				str += objects[1].toString()+";";
			}
			System.out.println(str);
		}
	}
	
	//������Ŀ��Ų�ѯ��Ŀ��Ա��Ϣ
	@Test
	public void findByNameAndStatus(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectUserService ps = (ProjectUserService) ctx.getBean("projectuserService");
		ProjectService pe = (ProjectService) ctx.getBean("projectService");
		Project pro =new Project();
		pro.setProjectname("f");
//		pro.setStatus(null);
		List<Project> list = pe.findProject(pro);
		System.out.println(list.size());
		for (Project project : list) {
			System.out.println(""+project.getStatus());
		}
	}
	
	//������Ŀ��Ų�ѯ��Ŀ��Ա��Ϣ
	@Test
	public void modelByProjectId(){
		ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ProjectUserService ps = (ProjectUserService) ctx.getBean("projectuserService");
		ProjectModelService pe = (ProjectModelService) ctx.getBean("projectmodelService");

		List<Projectmodel> list = pe.ModelByProjectId(new Long(10001));
		System.out.println(list.size());
		for (Projectmodel model : list) {
			System.out.println(model.getProjectmodelname());
		}
	}


}
 
