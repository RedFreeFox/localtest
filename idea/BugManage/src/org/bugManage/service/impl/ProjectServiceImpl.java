package org.bugManage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bugManage.dao.ProjectDAO;
import org.bugManage.dao.ProjectmodelDAO;
import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;
import org.bugManage.service.ProjectService;


/**
 * @title ProjectService
 * @author gikoukou
 *
 */
public class ProjectServiceImpl implements ProjectService {

	private ProjectDAO projectDao;
	private ProjectmodelDAO projectmodelDao;
	
	
	
	public void setProjectDao(ProjectDAO projectDao) {
		this.projectDao = projectDao;
	}

	public void setProjectmodelDao(ProjectmodelDAO projectmodelDao) {
		this.projectmodelDao = projectmodelDao;
	}

	//添加项目信息
	public void addProject(Project project) {
		//向项目表插入一条数据的同时也向模块表插入一条数据
		projectDao.save(project);
		//给项目模块实体添加数据
		Projectmodel pm=new Projectmodel();
		pm.setProject(project);
		pm.setProjectmodelname(project.getProjectname());
		pm.setProjectmodel(null);
		pm.setStatus(project.getStatus());
		//模块表插入数据
		projectmodelDao.save(pm);
	}


	//查询项目信息(1.根据项目名和状态一起查 2.根据项目名查 3.根据状态查)
	public List<Project> findProject(Project project) {
		List<Project> list = new ArrayList<Project>();
		if (project.getStatus() == -1) {
			list = projectDao.findByName(project.getProjectname());
		}else {
			list = projectDao.findByNameAndStatus(project.getProjectname(), project.getStatus());
		}
		return list;
	}

	//修改项目信息(删除项目只是改变标识即状态)
	public void modifyProject(Project project) {
		//merge()有编号就执行修改，没有编号执行添加
		projectDao.merge(project);

	}

	//根据项目模块编号查询项目信息
	public Project findByModelId(Long modelid) {
		Project project = null;
		project = (Project) projectDao.findByModelId(modelid).get(0);
		return project;
	}
	
	//根据项目编号查询项目信息
	public Project findById(Long projectid){
		Project project = null;
		project = projectDao.findById(projectid);
		return project;
	}
	
	//查询所有
	public List<Project> findAll(){
		List<Project> list = new ArrayList<Project>();
		list = projectDao.findAll();
		return list;
	}
	
	//通过项目状态过滤查询所有项目信息
	public List<Project> findAllProjectByStatus(){
		List<Project> list = new ArrayList<Project>();
		list = projectDao.findAllProjectByStatus();
		return list;
	}

}
