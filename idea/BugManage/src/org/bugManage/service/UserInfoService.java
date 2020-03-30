package org.bugManage.service;

import java.util.List;
import org.bugManage.entity.Role;
import org.bugManage.entity.Userinfo;

public interface UserInfoService {
	
	//登录（判断是否是被停止的用户）
	public List<Userinfo> login(Userinfo user);
	
	//查询个人信息(名字，邮箱，部门，电话)
	public Userinfo findPersonUserinfo(long userid);
	
	//修改个人信息(名字，邮箱，部门，电话)
	public void updatePersonUserifo(Userinfo user);
	
	//修改密码
	public void updatePwd(Userinfo user);
	
	//按编号或者部门 查询
	public List<Userinfo> findByType(String Id,Long page,Long number,String Dep);
	
	
	//显示所有用户资料
	public List<Userinfo> findAll();
	
	//修改用户(电话，邮件，部门，新密码)
	public void updateUserInfo(Userinfo user);
	
	//删除 （1为用,0未不可用）
	public void del(Userinfo user);
	
	//根据编号查找
	public Userinfo findById(long userid);
	
	//新建用户
	public void addUser(Userinfo user);


	//查询权限
	public List<Role> findRole(Long LimitID);
	//通过项目名，用户名查询权限
	public List<Role> findRoleByProIDAndUserId(Long ProID,Long UserID);

	
	
	//分页
	//获取    page--当前页数  number--一页中显示的数据个数
	public List<Userinfo> findPage(int page,int number);
	//获取最大的页数
	public Long findPageMax(long number) ;
	public List<Userinfo> findByUser(Long userid,Long page,Long number);

}
