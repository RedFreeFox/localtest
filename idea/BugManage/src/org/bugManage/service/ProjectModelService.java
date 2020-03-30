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
	
	
	//����ģ���Ų�ѯ
	public Projectmodel toProjectModel(Long projectmodelid);
	//���ģ����Ϣ
	public void addProjectModel(Projectmodel model);
	//�޸�ģ����Ϣ
	public void modifyProjectModel(Projectmodel model);
	//��ѯ����ģ����Ϣ
	public List<Projectmodel> findAll();
	//��ѯ��Ŀģ����Ϣ
	public List<Projectmodel> findByProID(Long ProID);
	//��ѯ������Ŀ������
	public List<Projectmodel> findAllProjectModel();
	//�����û���ɫ������������Ŀģ����Ϣ
	public List<Projectmodel> findByUserID(Long UserId);
	//������Ŀ��Ų�ѯģ����Ϣ(������)
	public List<Projectmodel> ModelByProjectId(Long projectid);
}
