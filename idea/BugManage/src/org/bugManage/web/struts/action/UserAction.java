/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package org.bugManage.web.struts.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.bugManage.entity.Projectmodel;
import org.bugManage.entity.Role;
import org.bugManage.entity.Userinfo;
import org.bugManage.service.ProjectModelService;
import org.bugManage.service.UserInfoService;
import org.bugManage.web.struts.form.UserForm;


/**
 * MyEclipse Struts Creation date: 08-26-2004
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/user" name="userForm" input="/form/user.jsp"
 *                parameter="method" scope="request"
 * @struts.action-forward name="error" path="/error.jsp"
 */
public class UserAction extends DispatchAction {
	private UserInfoService userinfoService;
	private ProjectModelService projectmodelService;
	

	public void setProjectmodelService(ProjectModelService projectmodelService) {
		this.projectmodelService = projectmodelService;
	}
	public void setUserinfoService(UserInfoService userinfoService) {
		this.userinfoService = userinfoService;
	}
	private HttpSession session;
	
	//界面显示入口
	public ActionForward Index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		session=request.getSession();
		String Index=session.getAttribute("Index").toString();
		if(Index.equals("FindAll")){
			return FindAll(mapping, form, request, response);
		}else if(Index.equals("findByType")){
			return findByType(mapping, form, request, response);
		}
		return null;
	}
	// 加载登录后的主页
	public ActionForward LoadIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
				 session = request.getSession();
				//获取用户名
				Userinfo ui=(Userinfo)session.getAttribute("user");
				List<Projectmodel> list = projectmodelService.findByUserID(ui.getUserid());
				session.setAttribute("models", list);
				return mapping.findForward("LoadIndex");
	}
	
	// 登录
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm = (UserForm) form;
		//获得UserInfo实例
		Userinfo user = userForm.getUser();
		//获得Session实例
		HttpSession session = request.getSession();
		List<Userinfo> list = userinfoService.login(user);
		try {
			if (list.size() != 0) {
				// 登录成功
				session.setAttribute("user", list.get(0));
				//获取用户是否是管理员
				if(list.get(0).getType()==0||list.get(0).getType().equals(0)){
					//查询权限表
					List<Role> role=userinfoService.findRole(new Long(4));
					session.setAttribute("role", role);
				}else{
					List<Role> role=userinfoService.findRole(new Long(5));
					session.setAttribute("role", role);
				}
				return mapping.findForward("login");
			} else {
				// 登录失败
				request.setAttribute("error", "用户名或密码错误！");
				return mapping.findForward("error");
			}
		} catch (Exception e) {
		   request.setAttribute("error", "用户名或密码错误！");
		   return mapping.findForward("error");
		}
	}
	
	// 注销
	public ActionForward ExitLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//获得Session实例
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.invalidate();
		return mapping.findForward("ExitLogin");
	}
	//新建用户
	public ActionForward addUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			UserForm userForm = (UserForm) form;
			Userinfo user=userForm.getUser();
			user.setActived(new Long(1));
			user.setType(new Long(1));
			user.setRegisttime(new Date());
			user.setLastlogintime(new Date());
			user.setDepartment("未加入");
			userinfoService.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return mapping.findForward("error");
		}
		if(request.getParameter("register")==null){
			//显示所有
			return mapping.findForward("show");
		}else{
			return mapping.findForward("error");
		}

	}
	

	// 管理员--修改用户UI
	public ActionForward updateUI(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Long id=new Long(request.getParameter("Id"));
			Userinfo user=userinfoService.findById(id);
			request.setAttribute("users", user);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		//显示要修改的界面
		return mapping.findForward("updateUI");
	}
	//管理员--修改用户
	public ActionForward updateUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserForm userForm = (UserForm) form;
		try {
			Long id=new Long(request.getParameter("Id"));
			Userinfo user=userForm.getUser();
			user.setUserid(id);
			user.setActived(new Long(1));
			user.setDepartment("未加入");
			user.setName(userForm.getUser().getName());
			if(userForm.getUser().getPassword().endsWith("")||userForm.getUser().getPassword()==user.getPassword()){
				this.userinfoService.updateUserInfo(user);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		//显示所有
		return mapping.findForward("show");
	}

	// 管理员登录---查看所有的用户信息
	public ActionForward FindAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		session=request.getSession();
		session.setAttribute("Index", "findAll");
		//每页显示的条数
		int number=5;
		//获取分页
		int page=1;
		//获取总共的页数
		Integer maxPage=new Integer(userinfoService.findPageMax(number).toString());
		if(request.getParameter("page")!=null){
			page=new Integer(request.getParameter("page"));
			if(page<=0){
				page=1;
			}else if(page>=maxPage){
				page=maxPage;
			}
		}
		List<Userinfo> userList=userinfoService.findPage(page, number);
		request.setAttribute("page", page);
		request.setAttribute("MaxPage", maxPage);
		//显示查找所有的界面
		request.setAttribute("userList", userList);
		return mapping.findForward("FindAll");
	}

	// 根据编号或者部门查找  并获取页码
	public ActionForward findByType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		session=request.getSession();
		session.setAttribute("Index", "findByType");
		UserForm userForm = (UserForm) form;
//		//获取界面值
		String dep = userForm.getUser().getDepartment();
		String Id=userForm.getUser().getName();
//		//每页显示的条数
		Long number=new Long(5);
//		//获取分页
		Long page=new Long(1);
		//获取总共的页数
		Long maxPage=userinfoService.findPageMax(number);
		//显示查找所有的界面
		if(request.getParameter("page")!=null){
			page=new Long(request.getParameter("page"));
			if(page<=0){
				page=new Long(1);
			}else if(page>=maxPage){
				page=maxPage;
			}
		}
		List<Userinfo> userList=userinfoService.findByType(Id, maxPage, number, dep);
		request.setAttribute("userList",userList );
		request.setAttribute("page", page);
		request.setAttribute("MaxPage", maxPage);
		return mapping.findForward("FindAll");
	}

	// 编辑用户---.修改用户(电话，邮件，部门，新密码)
	public ActionForward updateUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm = (UserForm) form;
		try {
			Long id=new Long(request.getParameter("Id"));
            Userinfo users=this.userinfoService.findById(id);
			Userinfo user=userForm.getUser();
			user.setUserid(users.getUserid());
			user.setActived(new Long(1));
			user.setName(users.getName());
			user.setType(users.getType());
			this.userinfoService.updatePersonUserifo(user);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		//显示修改用户界面
		return mapping.findForward("updateUserInfo");
	}

	// 修改密码
	public ActionForward updatePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm = (UserForm) form;
		try {
			//获得要修改的密码用户编号
			Long id=new Long(request.getParameter("Id"));
		    Userinfo users=this.userinfoService.findById(id);
			Userinfo user=userForm.getUser();
			user.setUserid(id);
			user.setName(users.getName());
			user.setDepartment(users.getDepartment());
			user.setEmail(users.getEmail());
			user.setActived(users.getActived());
			user.setTelephone(users.getTelephone());
			user.setType(users.getType());
			this.userinfoService.updatePwd(user);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		//显示修改密码页面
		return mapping.findForward("updatePwd");
	}

	// 删除用户（修改用户的活动状态）
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Long id=new Long(request.getParameter("Id"));
			Userinfo user=userinfoService.findById(id);
			userinfoService.del(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
			
		}
		//显示所有
		return mapping.findForward("show");
	}
}