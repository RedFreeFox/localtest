package org.bugManage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bugManage.dao.ProjectmodelDAO;
import org.bugManage.dao.ProjectuserDAO;
import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;
import org.bugManage.entity.Projectuser;
import org.bugManage.service.ProjectModelService;

/**
 * @title ProjectModelServiceImpl
 * @author gikoukou
 *
 */
public class ProjectModelServiceImpl implements ProjectModelService {
	
	private ProjectmodelDAO projectmodelDao;
	private ProjectuserDAO projectuserDao;
	

	public void setProjectuserDao(ProjectuserDAO projectuserDao) {
		this.projectuserDao = projectuserDao;
	}


	public void setProjectmodelDao(ProjectmodelDAO projectmodelDao) {
		this.projectmodelDao = projectmodelDao;
	}
	
	
	//根据模块编号查询
	public Projectmodel toProjectModel(Long projectmodelid) {
		//初始化
		Projectmodel pm = null;
		//根据编号查询
		pm = projectmodelDao.findById(projectmodelid);
		return pm;

	}
	
	//根据项目编号查询模块信息
	public List<Projectmodel> ModelByProjectId(Long projectid) {
		//初始化
		List<Projectmodel> list = new ArrayList<Projectmodel>();
		//根据编号查询
		list = projectmodelDao.findByProjectID(projectid);
		
		return list;

	}

	//添加模块信息
	public void addProjectModel(Projectmodel model) {
		//初始化
		Projectmodel pm = null;
		//获得当前节点的父节点编号
		Long mid = model.getProjectmodel().getProjectmodelid();
		//判断是否是根节点
		if ("".equals(mid)||mid==null) {
			pm.getProjectmodel().setProjectmodelid(null);
		}else {
			//设置下级节点的父节点编号
			pm.getProjectmodel().setProjectmodelid(model.getProjectmodelid());
		}
		//设置项目名、项目编号、状态
		pm.setProjectmodelname(model.getProjectmodelname());
		pm.setProject(model.getProject());
		pm.setStatus(model.getStatus());
		//添加
		projectmodelDao.save(pm);

	}

	//修改模块信息（形参中必须有编号，和需要修改的字段的值）
	public void modifyProjectModel(Projectmodel model) {
//		Projectmodel pm = projectmodelDao.findById(model.getProjectmodelid());
//		pm.setProjectmodelname(model.getProjectmodelname());
		//使用merge()方法
		projectmodelDao.upName(model.getProjectmodelid(), model.getProjectmodelname());

	}
	
	
	//查询所有模块信息
	public List<Projectmodel> findAll(){
		List<Projectmodel> list = new ArrayList<Projectmodel>();
		list = projectmodelDao.findByFatherModelID(null);
		searModel(list);
		return list;
	}
	
	public void searModel(List<Projectmodel> list){
		for(Projectmodel p:list){
			p.getProjectmodelname();
			if(p.getProjectmodels().size()!=0){
				searModel(new ArrayList<Projectmodel>(p.getProjectmodels()));
			}
		}
	}

	//查询所有项目的名称
	public List<Projectmodel> findAllProjectModel(){
		List<Projectmodel> list = new ArrayList<Projectmodel>();
		list = projectmodelDao.findAllProjectModel();
		return list;
	}

	//根据编号
	public List<Projectmodel> findByProID(Long ProID) {
		return projectmodelDao.findByProID(ProID);
	}

	//根据用户角色，返回所属项目模块信息
	public List<Projectmodel> findByUserID(Long UserId) {
		List<Projectmodel> promodel=new ArrayList<Projectmodel>();
		//通过用户查询项目
		List<Long> userlist=(List<Long>)projectuserDao.findByUserId(UserId);
		//通过项目查询模块
		for(int i=0;i<userlist.size();i++){

			Object o=userlist.get(i);
			Long lon=new Long(o.toString());
			System.out.println(lon);
			List<Projectmodel> promodeltemp=projectmodelDao.findByProjectID(lon);
			for(Projectmodel p:promodeltemp){
				promodel.add(p);
			}
			
		}
		searModel(promodel);
		return promodel;
	}

}
