/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package org.bugManage.web.struts.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.bugManage.entity.Basedataitem;
import org.bugManage.entity.Bug;
import org.bugManage.entity.Bugedition;
import org.bugManage.entity.Bughistory;
import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;
import org.bugManage.entity.Role;
import org.bugManage.entity.Userinfo;
import org.bugManage.format.dateFormat;
import org.bugManage.service.BugService;
import org.bugManage.service.UserInfoService;
import org.bugManage.web.struts.form.BugForm;

/** 
 * MyEclipse Struts
 * Creation date: 02-23-2012
 * 
 * XDoclet definition:
 * @struts.action path="/bug" name="bugForm" input="/index.jsp" parameter="method" scope="request" validate="true"
 */
public class BugAction extends DispatchAction {
	private dateFormat df=new dateFormat();
	private BugService bugService;
	private UserInfoService userinfoService;
	private List<Bug> BugList=new ArrayList<Bug>();
	public BugService getBugService() {
		return bugService;
	}
	public void setBugService(BugService bugService) {
		this.bugService = bugService;
	}
	public UserInfoService getUserinfoService() {
		return userinfoService;
	}
	public void setUserinfoService(UserInfoService userinfoService) {
		this.userinfoService = userinfoService;
	}
	private HttpSession session;
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	//��ѯ���
	public ActionForward requestType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//ͨ��session��ֵ���жϽ�����ʹ�õķ���
		session=request.getSession();
		String requestType=session.getAttribute("requestType").toString();
		if(requestType.equals("model")){
			return findByModel(mapping, form, request, response);
		}else if(requestType.equals("sort")){
			return sortBug(mapping, form, request, response);
		}else if(requestType.equals("sear")){
			return findByType(mapping, form, request, response);
		}else{
			return null;
		}
	}
	
	//	��ҳ��ģ�����
	public ActionForward findByModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		session=request.getSession();
		session.setAttribute("requestType", "model");

		//----------------------------------------------------
		//��ȡѡ��ģ��
		Long Model;
		if(request.getParameter("Model")==null){
			Model=new Long(request.getAttribute("Model").toString());
		}else{
			Model=new Long(request.getParameter("Model"));
		}
		//ÿҳ��ʾ����
		Long number=new Long(5);
		//��ȡ��ҳ
		Long page=new Long(1);
		//��ȡҳ��
		Long MaxPage=bugService.pageByModel(Model,number);
		if(request.getParameter("page")!=null){
			page=new Long(request.getParameter("page"));
			if(page<=0){
				page=new Long (1);
			}else if(page>=MaxPage){
				page=MaxPage;
			}
		}
		request.setAttribute("page", page);
		request.setAttribute("MaxPage", MaxPage);
		request.setAttribute("Model", Model);
		//----------------------------------------------------------
		BugList=bugService.findByModel(Model,page, number);
		BugRole(mapping, form, request, response);
		Projectmodel pm=bugService.findModelIdToModel(Model);
		request.setAttribute("ProID",pm.getProject().getProjectid());
		request.setAttribute("buglist", BugList);
		//----
		//---ÿ�ΰ�ģ���ѯ��ʱ���ȡȨ��
		Userinfo ui=(Userinfo)session.getAttribute("user");
		List<Role> role=userinfoService.findRoleByProIDAndUserId(pm.getProject().getProjectid(), ui.getUserid());
		session.setAttribute("role", role);
		
		//��ȡҳ��UI
		queryaUI(mapping, form, request, response);
		//ת����ʾҳ��action
		return mapping.findForward("showBugShow");
	}
	//��ID����
	public ActionForward findByIdsear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long bugid=new Long(request.getParameter("bugid"));
		Long Model=new Long(request.getParameter("Model"));
		BugList=bugService.findBugByBUGID(Model,bugid);
		BugRole(mapping, form, request, response);
		request.setAttribute("page", 1);
		request.setAttribute("MaxPage", 1);
		request.setAttribute("Model", Model);
		Projectmodel pm=bugService.findModelIdToModel(Model);
		request.setAttribute("ProID",pm.getProject().getProjectid());
		request.setAttribute("buglist", BugList);
		request.setAttribute("BugIDs", bugid);
		//��ȡҳ��UI
		queryaUI(mapping, form, request, response);
		return mapping.findForward("showBugShow");
	}
	//����������
	public ActionForward findByType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		session=request.getSession();
		session.setAttribute("requestType", "sear");
		//��ȡѡ��ģ��
		//�����ȡ�ж�
		Long BugID=null;
		if(request.getParameter("BugID")==null||request.getParameter("BugID").equals("")){
			BugID=null;
		}else{
			BugID=new Long(request.getParameter("bugid"));
			request.setAttribute("BugIDs", BugID);
		}
		Long Tester=null;
		if(request.getParameter("Tester")==null||request.getParameter("Tester").equals("---��ѡ��---")){
			Tester=null;
		}else{
			Tester=new Long(request.getParameter("Tester"));
			request.setAttribute("Testers", Tester);
		}
		Long Principal=null;
		if(request.getParameter("Principal")==null||request.getParameter("Principal").equals("---��ѡ��---")){
			Principal=null;
		}else{
			Principal=new Long(request.getParameter("Principal"));
			request.setAttribute("Principals", Principal);
		}
		Long Status=null;
		if(request.getParameter("Status")==null||request.getParameter("Status").equals("---��ѡ��---")){
			Status=null;
		}else{
			Status=new Long(request.getParameter("Status"));
			request.setAttribute("Statuss", Status);
		}
		String Summary=null;
		if(request.getParameter("Summary")==null||request.getParameter("Summary").equals("")){
			Summary=null;
		}else{
			Summary=request.getParameter("Summary");
			request.setAttribute("Summarys", Summary);
		}
		String StarDate=null;
		if(request.getParameter("StarDate")==null||request.getParameter("StarDate").equals("2000-01-01 12:00:00")){
			StarDate=null;
		}else{
			StarDate=request.getParameter("StarDate");
			request.setAttribute("StarDates", StarDate);
		}
		String OverDate=null;
		if(request.getParameter("OverDate")==null||request.getParameter("OverDate").equals("2000-01-01 12:00:00")){
			OverDate=null;
		}else{
			OverDate=request.getParameter("OverDate");
			request.setAttribute("OverDates", OverDate);
		}
		//----------------------------------------------------
		//��ȡѡ��BUGid
		Long Model=new Long(request.getParameter("Model"));

		//ÿҳ��ʾ����
		Long number=new Long(5);
		//��ȡ��ҳ
		Long page=new Long(1);
		//��ȡҳ��
		Long MaxPage=bugService.pageByType(BugID, Tester, Principal, Status, Summary, StarDate, OverDate,number,Model);
		if(request.getParameter("page")!=null){
			page=new Long(request.getParameter("page"));
			if(page<=0){
				page=new Long (1);
			}else if(page>=MaxPage){
				page=MaxPage;
			}
		}
		request.setAttribute("page", page);
		request.setAttribute("MaxPage", MaxPage);
		//----------------------------------------------------------
		BugList=bugService.findByType(BugID, Tester, Principal, Status, Summary, StarDate, OverDate, page, number, Model);
		BugRole(mapping, form, request, response);
		Projectmodel ProModels=bugService.findModelIdToModel(Model);
		request.setAttribute("ProID", ProModels.getProject().getProjectid());
		request.setAttribute("buglist", BugList);
		//��ȡҳ��UI
		queryaUI(mapping, form, request, response);
		return mapping.findForward("showBugShow");
		
	}
	
	//�鿴BUG��ϸ �Լ�����
	public ActionForward findByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//��ȡѡ��BUGid
		Long bugId;
		if(request.getParameter("id")!=null){
			bugId=new Long(request.getParameter("id"));
		}else{
			bugId=new Long(request.getAttribute("id").toString());
		}
		Bug bug=bugService.findByID(bugId);
		//---��ȡ�û��ٸ�BUG��Ȩ��
		HttpSession session = request.getSession();
		//��ȡ�û���
		Userinfo ui=(Userinfo)session.getAttribute("user");
		if(bug.getUserinfoByTester().getUserid()==ui.getUserid()||bug.getUserinfoByTester().getUserid().equals(ui.getUserid())){
			bug.setTesterif(true);
		}
		if(bug.getUserinfoByPrincipal().getUserid()==ui.getUserid()||bug.getUserinfoByPrincipal().getUserid().equals(ui.getUserid())){
			bug.setPrincipalif(true);
		}
		//����BUG�ֶ�
		
			if(bug.getLastreply()!=null){
				bug.setLastreplystr(bugService.findByDateIdAndValue("Reply", bug.getLastreply().toString()));
			}
			if(bug.getStatus()!=null){
				bug.setStatusstr(bugService.findByDateIdAndValue("Status", bug.getStatus().toString()));
			}
			if(bug.getTestphase()!=null){
				bug.setTestphasestr(bugService.findByDateIdAndValue("TestPhase", bug.getTestphase().toString()));
			}
			if(bug.getGravitylevel()!=null){
				bug.setGravitylevelstr(bugService.findByDateIdAndValue("GravityLevel", bug.getGravitylevel().toString()));
			}
			if(bug.getReappearance()!=null){
				bug.setReappearancestr(bugService.findByDateIdAndValue("Reappearance", bug.getReappearance().toString()));
			}
			if(bug.getPhase()!=null){
				bug.setPhasestr(bugService.findByDateIdAndValue("Phase", bug.getPhase().toString()));
			}
			if(bug.getPriority()!=null){
				bug.setPrioritystr(bugService.findByDateIdAndValue("Priority", bug.getPriority().toString()));
			}
			if(bug.getQualitycharacter()!=null){
				bug.setQualitycharacterstr(bugService.findByDateIdAndValue("QualityCharacter", bug.getQualitycharacter().toString()));
			}
		
		
		//����BUG��BUG���з��������BUG.Bughistories��
		request.setAttribute("bug", bug);

		//����UI
		//��ȡ���԰汾
		List<Bugedition> Bugedition=bugService.findEdition(bug.getProject().getProjectid());
		request.setAttribute("Bugedition", Bugedition);
		//����׶�
		request.setAttribute("Phase", bugService.findByBasedataitem("Phase"));
	//���Խ׶�
		request.setAttribute("TestPhase", bugService.findByBasedataitem("TestPhase"));
	//���س̶�
		request.setAttribute("GravityLevel", bugService.findByBasedataitem("GravityLevel"));
	//��������
		request.setAttribute("QualityCharacter", bugService.findByBasedataitem("QualityCharacter"));
	//���̶ֳ�
		request.setAttribute("Reapperarance", bugService.findByBasedataitem("Reapperarance"));
	//��������
		//�������Ŀ������������Ա����ʾǰ0-7���ݡ�
		List<Basedataitem> Reply=bugService.findByBasedataitem("Reply");
		if(Reply.size()>7){
			Reply=Reply.subList(0, 6);
		}
		request.setAttribute("Reply", Reply);
	//���ȼ�
		request.setAttribute("Priority", bugService.findByBasedataitem("Priority"));
	//BUG״̬
		List<Basedataitem> Status=bugService.findByBasedataitem("Status");
		request.setAttribute("Status", Status.subList(1, Status.size()));
		//������
		request.setAttribute("Principal", bugService.UserInfoFindByProjectID(bug.getProject().getProjectid(),new Long(1)));
		return mapping.findForward("showBugView");
	}

	//����
	public ActionForward sortBug(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		session=request.getSession();
		session.setAttribute("requestType", "sort");
		//------------------------
		//��ȡ��������
		Long Type=new Long(request.getParameter("Type"));
		request.setAttribute("Type", Type);
		//��ȡѡ��BUGid
		Long Model=new Long(request.getParameter("Model"));
		//ÿҳ��ʾ����
		Long number=new Long(5);
		//��ȡ��ҳ
		Long page=new Long(1);
		//��ȡҳ��
		Long MaxPage=bugService.pagesoftBug(Model, number, Type);
		if(request.getParameter("page")!=null){
			page=new Long(request.getParameter("page"));
			if(page<=0){
				page=new Long (1);
			}else if(page>=MaxPage){
				page=MaxPage;
			}
		}
		request.setAttribute("page", page);
		request.setAttribute("MaxPage", MaxPage);
		BugList=bugService.softBug(Model, page, number, Type);
		BugRole(mapping, form, request, response);
		//----------------------------------------------------------
		Projectmodel ProModels=bugService.findModelIdToModel(Model);
		request.setAttribute("ProID", ProModels.getProject().getProjectid());
		request.setAttribute("buglist", BugList);
		//��ȡҳ��UI
		queryaUI(mapping, form, request, response);
		return mapping.findForward("showBugShow");
	}
	//ת���½�BUGҳ��
	public ActionForward addBugUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//��ȡ����ģ��
			//��ȡ��ĿID
			Long Model=new Long(request.getParameter("Model").toString());
			Long ProID=bugService.findModelIdToModel(Model).getProject().getProjectid();
			List<Projectmodel> modellist=bugService.ProjectmodelfindByModel(ProID);
			request.setAttribute("modellist", modellist);
			request.setAttribute("Model", Model);
		//��ȡ���԰汾
			List<Bugedition> Bugedition=bugService.findEdition(ProID);
			request.setAttribute("Bugedition", Bugedition);
			
		//���Խ׶�
			request.setAttribute("TestPhase", bugService.findByBasedataitem("TestPhase"));
		//���س̶�
			request.setAttribute("GravityLevel", bugService.findByBasedataitem("GravityLevel"));
		//��������
			request.setAttribute("QualityCharacter", bugService.findByBasedataitem("QualityCharacter"));
		//���̶ֳ�
			request.setAttribute("Reapperarance", bugService.findByBasedataitem("Reapperarance"));
		//������
			request.setAttribute("Principal", bugService.UserInfoFindByProjectID(ProID,new Long(1)));
		//������(ҳ����ȡsession)
			HttpSession session=request.getSession();
			Userinfo ui=(Userinfo)session.getAttribute("user");
			request.setAttribute("user", ui.getName());
		//����ʱ��
			request.setAttribute("CreateTime", df.dateToString(new Date()));
		//ת���½�BUGҳ��
		return mapping.findForward("showBugAdd");
	}
	//ΪBUG��������//ΪBUG�ֶθ������
	public ActionForward BugRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		//��ȡ�û���
		Userinfo ui=(Userinfo)session.getAttribute("user");
		//ΪBUG��������
		for(Bug b:BugList){
			if(b.getUserinfoByTester().getUserid()==ui.getUserid()||b.getUserinfoByTester().getUserid().equals(ui.getUserid())){
				b.setTesterif(true);
			}
			if(b.getUserinfoByPrincipal().getUserid()==ui.getUserid()||b.getUserinfoByPrincipal().getUserid().equals(ui.getUserid())){
				b.setPrincipalif(true);
			}
		}
		//����BUG�ֶ�
		for(Bug b:BugList){
			if(b.getLastreply()!=null){
				b.setLastreplystr(bugService.findByDateIdAndValue("Reply", b.getLastreply().toString()));
			}
			if(b.getStatus()!=null){
				b.setStatusstr(bugService.findByDateIdAndValue("Status", b.getStatus().toString()));
			}
			if(b.getTestphase()!=null){
				b.setTestphasestr(bugService.findByDateIdAndValue("TestPhase", b.getTestphase().toString()));
			}
			if(b.getGravitylevel()!=null){
				b.setGravitylevelstr(bugService.findByDateIdAndValue("GravityLevel", b.getGravitylevel().toString()));
			}
			if(b.getReappearance()!=null){
				b.setReappearancestr(bugService.findByDateIdAndValue("Reappearance", b.getReappearance().toString()));
			}
			if(b.getPhase()!=null){
				b.setPhasestr(bugService.findByDateIdAndValue("Phase", b.getPhase().toString()));
			}
			if(b.getPriority()!=null){
				b.setPrioritystr(bugService.findByDateIdAndValue("Priority", b.getPriority().toString()));
			}
			if(b.getQualitycharacter()!=null){
				b.setQualitycharacterstr(bugService.findByDateIdAndValue("QualityCharacter", b.getQualitycharacter().toString()));
			}
		}
		return null;
	}
	
	//����BUG
	public ActionForward addBug(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BugForm bf=(BugForm)form;
		Bug bug=bf.getBug();
		HttpSession session=request.getSession();
		//��ȡ������
		Long Principal=new Long(request.getParameter("Principal"));
		Userinfo ui=(Userinfo)session.getAttribute("user");
		//��ѯBUG����ģ��
		Projectmodel Model=bugService.findModelIdToModel(bug.getProjectmodel().getProjectmodelid());
		request.setAttribute("Model", Model.getProjectmodelid());
		bug.getUserinfoByPrincipal().setUserid(Principal);
		bug.getProject().setProjectid(Model.getProject().getProjectid());
		bug.getUserinfoByTester().setUserid(ui.getUserid());
		bug.setCreatetime(df.dateToDate(new Date()));
		bugService.addBug(bug);
		//��Ϣ�����ӳɹ�
		return findByModel(mapping, form, request, response);
	}
	//ɾ��BUG
	public ActionForward delBug(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long Model=new Long(request.getParameter("model"));
		request.setAttribute("Model", Model);
		Long bugid=new Long(request.getParameter("bugid"));
		HttpSession session=request.getSession();
		Userinfo ui=(Userinfo)session.getAttribute("user");
		bugService.delBug(bugid, ui.getUserid());
		//��Ϣ��ɾ���ɹ�
		return findByModel(mapping, form, request, response);
	}
	//ת�� ����BUG ҳ��
	public ActionForward SetBugUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//��ȡ����Ϣ
		Long bugid=new Long(request.getParameter("bugid"));
		Bug bug=bugService.findByID(bugid);
		Long Model=bug.getProjectmodel().getProjectmodelid();
		request.setAttribute("Model", Model);
		request.setAttribute("bug", bug);
		//��ȡ��Ŀģ����Ϣ
		Long ProID=bugService.findModelIdToModel(Model).getProject().getProjectid();
		List<Projectmodel> modellist=bugService.ProjectmodelfindByModel(ProID);
		request.setAttribute("modellist", modellist);
		request.setAttribute("Model", Model);
	//��ȡ���԰汾
		List<Bugedition> Bugedition=bugService.findEdition(ProID);
		request.setAttribute("Bugedition", Bugedition);
		
	//���Խ׶�
		request.setAttribute("TestPhase", bugService.findByBasedataitem("TestPhase"));
	//���س̶�
		request.setAttribute("GravityLevel", bugService.findByBasedataitem("GravityLevel"));
	//��������
		request.setAttribute("QualityCharacter", bugService.findByBasedataitem("QualityCharacter"));
	//���̶ֳ�
		request.setAttribute("Reapperarance", bugService.findByBasedataitem("Reapperarance"));
	//������
		request.setAttribute("Principal", bugService.UserInfoFindByProjectID(ProID,new Long(1)));
	//������(ҳ����ȡsession)
		HttpSession session=request.getSession();
		Userinfo ui=(Userinfo)session.getAttribute("user");
		request.setAttribute("user", ui.getName());
		return mapping.findForward("showBugUpdate");
	}
	//�޸�BUG
	public ActionForward setBug(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BugForm bf=(BugForm)form;
		Long Model=new Long(request.getParameter("bug.projectmodel.projectmodelid"));
		request.setAttribute("Model", Model);
		//��ȡ֮ǰ
		Long bugid=new Long(bf.getBug().getBugid());
		Bug bug=bugService.findByID(bugid);
		//��ȡ���԰汾
		String testversion=request.getParameter("bugeditionid");
		bug.setTestversion(df.stringToDate(testversion));
		//�����˲���
		bf.getBug();
		bug.setProjectmodel(bf.getBug().getProjectmodel());
		bug.setTestphase(bf.getBug().getTestphase());
		bug.setEnvironment(bf.getBug().getEnvironment());
		bug.setSummary(bf.getBug().getSummary());
		bug.setDetail(bf.getBug().getDetail());
		bug.setAnalyse(bf.getBug().getAnalyse());
		bug.setGravitylevel(bf.getBug().getGravitylevel());
		bug.setQualitycharacter(bf.getBug().getQualitycharacter());
		bug.setReappearance(bf.getBug().getReappearance());
		//��ȡ������
		Long Principal=new Long(request.getParameter("Principal"));
		Userinfo Principals=new Userinfo();
		Principals.setUserid(Principal);
		bug.setUserinfoByPrincipal(Principals);
		bugService.setBug(bug);
		//��Ϣ���޸ĳɹ�
		return findByModel(mapping, form, request, response);
	}
	//BUG����
	public ActionForward addHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BugForm bf=(BugForm)form;
		Bug bug=bf.getBug();
		Bug bugtemp=bugService.findByID(bug.getBugid());
		HttpSession session=request.getSession();
		Userinfo user=(Userinfo)session.getAttribute("user");
		Long Pro=bugtemp.getProject().getProjectid();
		//��ȡ�û���Ŀ������
		Long Type=bugService.TypeFindByProIDAndUser(Pro, user.getUserid());
		//��ȡ������Ϣ
		Bughistory bh=new Bughistory();
		//BUGID
		bh.setBug(bug);
		//������
		bh.getUserinfo().setUserid(user.getUserid());
		//����
		bh.setDescribe(request.getParameter("describe"));
		//����ʱ��
		bh.setCreatetime(df.dateToDate(new Date()));

		if(Type==0||Type.equals(0)){
			//��Ŀ����
			//��������
			bh.setReply(bug.getLastreply().toString());
			bh.setStatus(new Long(3));
			bugService.addHistoryByItemManager(bug, bh);
		}else if(Type==1||Type.equals(1)){
			//����
			//��������
			bh.setReply(bug.getLastreply().toString());
			bh.setStatus(new Long(3));
			bugService.addHistoryByDeveloper(bug, bh);
		}else if(Type==2||Type.equals(2)){
			//����,�޸�״̬
			bh.setStatus(bug.getStatus());
			//��ȡ״̬
			bugService.addHistoryByConnerPer(bug, bh);
		}
		request.setAttribute("id", bug.getBugid());
		//��Ϣ�����ӳɹ�
		return findByID(mapping, form, request, response);
	}
	
	//������ʾUI
	public ActionForward queryaUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BugList=(List<Bug>)request.getAttribute("buglist");
		if(request.getParameter("Model")==null){
			request.setAttribute("Model", request.getAttribute("Model"));
		}else{
			request.setAttribute("Model", request.getParameter("Model"));
		}
		
		//�������ݽ�ȡ
//		for(int i=0;i<buglist.size();i++){
//			if(buglist.get(i).getDetail().length()>24){
//				buglist.get(i).setDetail(buglist.get(i).getDetail().substring(0, 25)+"����");
//			}else{
//				buglist.get(i).setDetail(buglist.get(i).getDetail().substring(0, buglist.get(i).getDetail().length())+"����");
//			}
//		}
		
		request.setAttribute("buglist", BugList);
		Long ProID=new Long(request.getAttribute("ProID").toString());
		//������Ա
		List<Userinfo> Tester=bugService.UserInfoFindByProjectID(ProID, new Long(2));
		request.setAttribute("Tester", Tester);
		//������Ա/������
		List<Userinfo> Principal=bugService.UserInfoFindByProjectID(ProID, new Long(3));
		request.setAttribute("Principal", Principal);
		//״̬
		List<Basedataitem> Status=bugService.findByBasedataitem("Status");
		request.setAttribute("Status", Status);
		return null;
	}
	//�鿴���԰汾
	public ActionForward findEdition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long Model=new Long(request.getParameter("Model"));
		request.setAttribute("Model", Model);
		Projectmodel Promodel=bugService.findModelIdToModel(Model);
		//��ȡ���԰汾
		List<Bugedition> Bugedition=bugService.findEdition(Promodel.getProject().getProjectid());
		request.setAttribute("BugEdition", Bugedition);
		return mapping.findForward("edition");
	}
	//���Ӳ��԰汾
	public ActionForward addEdition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long Model=new Long(request.getParameter("Model"));
		request.setAttribute("Model", Model);
		Projectmodel Promodel=bugService.findModelIdToModel(Model);
		String BugEditionName=request.getParameter("name");
		dateFormat df=new dateFormat();
		//ʱ��ת��
		Date BugEditionNameDate=df.stringToDate(BugEditionName);
		Bugedition bugEdition=new Bugedition();
		bugEdition.setBugeditionname(BugEditionNameDate);
		Project pro=new Project();
		pro.setProjectid(Promodel.getProject().getProjectid());
		bugEdition.setProject(pro);
		bugService.addBugEdition(bugEdition);
		return findEdition(mapping, form, request, response);
	} 
	//����������Ŀ��Ա����ɾ��
	public ActionForward batchProjectUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long ProID=new Long(request.getParameter("proID"));
		request.setAttribute("projectid", ProID);
		Long Type=new Long(request.getParameter("type"));
		String[] showPer=request.getParameterValues("showPer");
		List<Userinfo> user=new ArrayList<Userinfo>();
		if(showPer!=null){
			for(int i=0;i<showPer.length;i++){
				Userinfo ui=new Userinfo();
				ui.setUserid(new Long(showPer[i]));
				user.add(ui);
			}
		}else{
			user=new ArrayList<Userinfo>();
		}

		bugService.batchProjectUser(user, ProID, Type);
		return mapping.findForward("ToAdminProUsers");
	} 
	
	//��ȡ������Ŀ
	public ActionForward showProjectAndModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("model", bugService.ProjectfindByAll());
		return mapping.findForward("test");
	}
	//�޸�������
	public ActionForward updatePrincipal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long bugid=new Long(request.getParameter("bugid"));
		Long principal= new Long(request.getParameter("principal"));
		HttpSession session = request.getSession();
		//��ȡ�û���
		Userinfo ui=(Userinfo)session.getAttribute("user");
		bugService.setPrincipal(bugid, principal, ui.getUserid());
		request.setAttribute("id", bugid);
		return findByID(mapping, form, request, response);
	}
}