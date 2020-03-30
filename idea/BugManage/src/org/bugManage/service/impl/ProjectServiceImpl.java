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

	//�����Ŀ��Ϣ
	public void addProject(Project project) {
		//����Ŀ�����һ�����ݵ�ͬʱҲ��ģ������һ������
		projectDao.save(project);
		//����Ŀģ��ʵ���������
		Projectmodel pm=new Projectmodel();
		pm.setProject(project);
		pm.setProjectmodelname(project.getProjectname());
		pm.setProjectmodel(null);
		pm.setStatus(project.getStatus());
		//ģ����������
		projectmodelDao.save(pm);
	}


	//��ѯ��Ŀ��Ϣ(1.������Ŀ����״̬һ��� 2.������Ŀ���� 3.����״̬��)
	public List<Project> findProject(Project project) {
		List<Project> list = new ArrayList<Project>();
		if (project.getStatus() == -1) {
			list = projectDao.findByName(project.getProjectname());
		}else {
			list = projectDao.findByNameAndStatus(project.getProjectname(), project.getStatus());
		}
		return list;
	}

	//�޸���Ŀ��Ϣ(ɾ����Ŀֻ�Ǹı��ʶ��״̬)
	public void modifyProject(Project project) {
		//merge()�б�ž�ִ���޸ģ�û�б��ִ�����
		projectDao.merge(project);

	}

	//������Ŀģ���Ų�ѯ��Ŀ��Ϣ
	public Project findByModelId(Long modelid) {
		Project project = null;
		project = (Project) projectDao.findByModelId(modelid).get(0);
		return project;
	}
	
	//������Ŀ��Ų�ѯ��Ŀ��Ϣ
	public Project findById(Long projectid){
		Project project = null;
		project = projectDao.findById(projectid);
		return project;
	}
	
	//��ѯ����
	public List<Project> findAll(){
		List<Project> list = new ArrayList<Project>();
		list = projectDao.findAll();
		return list;
	}
	
	//ͨ����Ŀ״̬���˲�ѯ������Ŀ��Ϣ
	public List<Project> findAllProjectByStatus(){
		List<Project> list = new ArrayList<Project>();
		list = projectDao.findAllProjectByStatus();
		return list;
	}

}
