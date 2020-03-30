package org.bugManage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bugManage.dao.ProjectuserDAO;
import org.bugManage.dao.RoleDAO;
import org.bugManage.dao.UserinfoDAO;
import org.bugManage.entity.Projectuser;
import org.bugManage.entity.Role;
import org.bugManage.entity.Userinfo;
import org.bugManage.service.UserInfoService;

public class UserInfoServiceImpl implements UserInfoService {
	private RoleDAO roleDao;
	private ProjectuserDAO projectuserDao;
	public ProjectuserDAO getProjectuserDao() {
		return projectuserDao;
	}

	public void setProjectuserDao(ProjectuserDAO projectuserDao) {
		this.projectuserDao = projectuserDao;
	}

	public RoleDAO getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	private UserinfoDAO userinfoDao;

	public UserinfoDAO getUserinfoDao() {
		return userinfoDao;
	}

	public void setUserinfoDao(UserinfoDAO userinfoDao) {
		this.userinfoDao = userinfoDao;
	}

	public List<Userinfo> findAll() {
		// TODO Auto-generated method stub
		return this.userinfoDao.findAll();
	}
////通过用户编号或用户的部门名称查找
//	public List<Userinfo> findByType(String Id,String Dep) {
//		List<Userinfo> list=new ArrayList<Userinfo>();
//		Userinfo users=new Userinfo();
//		list=userinfoDao.findSelect(Id, Dep);
//		return list;
//		
//	}
		
	public Userinfo findPersonUserinfo(long userid) {
		// TODO Auto-generated method stub
		return this.userinfoDao.findById(userid);
	}

	public List<Userinfo> login(Userinfo user) {
		// TODO Auto-generated method stub
		return this.userinfoDao.login(user);
	}

	public void updatePersonUserifo(Userinfo user) {
		// TODO Auto-generated method stub
		this.userinfoDao.merge(user);

	}

	public void updatePwd(Userinfo user) {
		// TODO Auto-generated method stub
		this.userinfoDao.merge(user);
	}

	public void updateUserInfo(Userinfo user) {
		// TODO Auto-generated method stub
		this.userinfoDao.merge(user);

	}

	public void del(Userinfo user) {
		// 通过userid 查询用户信息 修改用户的活动状态
		long id = user.getUserid();
		Userinfo users=this.userinfoDao.findById(id);
		users.setActived(new Long(0));
		this.userinfoDao.merge(users);
	}

	public Userinfo findById(long userid) {
		// TODO Auto-generated method stub
		return this.userinfoDao.findById(userid);

	}

	public void addUser(Userinfo user) {
		// TODO Auto-generated method stub
		this.userinfoDao.save(user);
		
	}


	public List<Role> findRole(Long LimitID) {
		return roleDao.findByLimitid(LimitID);
	}

	//通过项目名，用户名查询权限
	public List<Role> findRoleByProIDAndUserId(Long ProID,Long UserID){
		List<Role> role=new ArrayList<Role>();
		List<Projectuser> prouser=projectuserDao.findProAndUser(ProID, UserID);
		for(Projectuser p:prouser){
			List<Role> roleList=roleDao.findByLimitid(p.getProjectusertype());
			for(Role r: roleList){
				role.add(r);
			}
		}
		return role;
	}


	public List<Userinfo> findPage(int page,int number) {
	   List<Userinfo> userList=new ArrayList<Userinfo>();
	   userList=this.userinfoDao.findPage(page,number);
	   return userList;	
	}
	public Long findPageMax(long number) {
		   //多少页
		   Long	pageMax;
		   //多数条数据
		   Long size=userinfoDao.findPageMax();
		   if(size%number==0){
			   pageMax=size/number;
		   }else{
			   pageMax=((size-(size%number))/number)+1;
		   }
		   
		   return pageMax;	
		}


	public List<Userinfo> findByUser(Long userid, Long page, Long number) {
		// TODO Auto-generated method stub
		List<Userinfo> userList=new ArrayList<Userinfo>();
		page--;
		userList=userinfoDao.findByUser(userid, page, number);
		return userList;
	}

	public List<Userinfo> findByType(String Id,Long page,Long number,String Dep) {
		// TODO Auto-generated method stub
		List<Userinfo> userList=new ArrayList<Userinfo>();
		page--;
		userList=userinfoDao.findSelect(Id, page*number, number, Dep);
		return userList;
	}

}

