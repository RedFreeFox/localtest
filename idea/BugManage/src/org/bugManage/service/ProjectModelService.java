package org.bugManage.service;

import java.util.List;

import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;


/**
 * @title ProjectService
 * @author gikoukou
 *
 */
public interface ProjectModelService {
	
	
	//根据模块编号查询
	public Projectmodel toProjectModel(Long projectmodelid);
	//添加模块信息
	public void addProjectModel(Projectmodel model);
	//修改模块信息
	public void modifyProjectModel(Projectmodel model);
	//查询所有模块信息
	public List<Projectmodel> findAll();
	//查询项目模块信息
	public List<Projectmodel> findByProID(Long ProID);
	//查询所有项目的名称
	public List<Projectmodel> findAllProjectModel();
	//根据用户角色，返回所属项目模块信息
	public List<Projectmodel> findByUserID(Long UserId);
	//根据项目编号查询模块信息(父摸快)
	public List<Projectmodel> ModelByProjectId(Long projectid);
}
