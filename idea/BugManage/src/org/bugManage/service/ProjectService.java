package org.bugManage.service;

import java.util.List;

import org.bugManage.entity.Project;

/**
 * @title ProjectService
 * @author gikoukou
 *
 */
public interface ProjectService {
	
	
	//�����Ŀ��Ϣ
	public void addProject(Project project);
	
	//�޸���Ŀ��Ϣ
	public void modifyProject(Project project);
	
	
	//��ѯ��Ŀ��Ϣ
	public List<Project> findProject(Project project);
	
	//������Ŀģ���Ų�ѯ��Ŀ��Ϣ
	public Project findByModelId(Long modelid);
	
	//������Ŀ��Ų�ѯ��Ŀ��Ϣ
	public Project findById(Long projectid);
	
	//��ѯ����
	public List<Project> findAll();
	
	//ͨ����Ŀ״̬���˲�ѯ������Ŀ��Ϣ
	public List<Project> findAllProjectByStatus();
	
	
	

}
