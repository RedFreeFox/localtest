package org.bugManage.service;

import java.util.List;

import org.bugManage.entity.Projectuser;


/**
 * @title ProjectUserService
 * @author gikoukou
 *
 */
public interface ProjectUserService {
	
	//更新用户角色
	public void updateProjectUser(Projectuser per);
	

	
	//根据项目编号查询
	public List<Object[]> findByProjectId(Long projectid,Long projectusertype);

}
