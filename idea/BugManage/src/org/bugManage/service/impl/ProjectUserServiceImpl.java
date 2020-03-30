package org.bugManage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bugManage.dao.ProjectuserDAO;
import org.bugManage.entity.Projectuser;
import org.bugManage.service.ProjectUserService;


/**
 * @title ProjectUserServiceImpl
 * @author gikoukou
 *
 */
public class ProjectUserServiceImpl implements ProjectUserService {
	
	private ProjectuserDAO projectuserDao;
	
	






	public void setProjectuserDao(ProjectuserDAO projectuserDao) {
		this.projectuserDao = projectuserDao;
	}



	//�����û���ɫ
	public void updateProjectUser(Projectuser per) {
		projectuserDao.merge(per);
	}
	

	
	//������Ŀ��Ų�ѯ
	public List<Object[]> findByProjectId(Long projectid,Long projectusertype){
		List<Object[]> list = new ArrayList<Object[]>();
		list = projectuserDao.findByProjectIds(projectid,projectusertype);
		return list;
	}

}
