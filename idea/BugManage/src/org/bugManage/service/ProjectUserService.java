package org.bugManage.service;

import java.util.List;

import org.bugManage.entity.Projectuser;


/**
 * @title ProjectUserService
 * @author gikoukou
 *
 */
public interface ProjectUserService {
	
	//�����û���ɫ
	public void updateProjectUser(Projectuser per);
	

	
	//������Ŀ��Ų�ѯ
	public List<Object[]> findByProjectId(Long projectid,Long projectusertype);

}
