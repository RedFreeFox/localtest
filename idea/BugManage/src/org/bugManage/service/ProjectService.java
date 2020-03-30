package org.bugManage.service;

import java.util.List;

import org.bugManage.entity.Project;

/**
 * @title ProjectService
 * @author gikoukou
 *
 */
public interface ProjectService {
	
	
	//添加项目信息
	public void addProject(Project project);
	
	//修改项目信息
	public void modifyProject(Project project);
	
	
	//查询项目信息
	public List<Project> findProject(Project project);
	
	//根据项目模块编号查询项目信息
	public Project findByModelId(Long modelid);
	
	//根据项目编号查询项目信息
	public Project findById(Long projectid);
	
	//查询所有
	public List<Project> findAll();
	
	//通过项目状态过滤查询所有项目信息
	public List<Project> findAllProjectByStatus();
	
	
	

}
