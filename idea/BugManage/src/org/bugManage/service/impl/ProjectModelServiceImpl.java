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
	
	
	//����ģ���Ų�ѯ
	public Projectmodel toProjectModel(Long projectmodelid) {
		//��ʼ��
		Projectmodel pm = null;
		//���ݱ�Ų�ѯ
		pm = projectmodelDao.findById(projectmodelid);
		return pm;

	}
	
	//������Ŀ��Ų�ѯģ����Ϣ
	public List<Projectmodel> ModelByProjectId(Long projectid) {
		//��ʼ��
		List<Projectmodel> list = new ArrayList<Projectmodel>();
		//���ݱ�Ų�ѯ
		list = projectmodelDao.findByProjectID(projectid);
		
		return list;

	}

	//���ģ����Ϣ
	public void addProjectModel(Projectmodel model) {
		//��ʼ��
		Projectmodel pm = null;
		//��õ�ǰ�ڵ�ĸ��ڵ���
		Long mid = model.getProjectmodel().getProjectmodelid();
		//�ж��Ƿ��Ǹ��ڵ�
		if ("".equals(mid)||mid==null) {
			pm.getProjectmodel().setProjectmodelid(null);
		}else {
			//�����¼��ڵ�ĸ��ڵ���
			pm.getProjectmodel().setProjectmodelid(model.getProjectmodelid());
		}
		//������Ŀ������Ŀ��š�״̬
		pm.setProjectmodelname(model.getProjectmodelname());
		pm.setProject(model.getProject());
		pm.setStatus(model.getStatus());
		//���
		projectmodelDao.save(pm);

	}

	//�޸�ģ����Ϣ���β��б����б�ţ�����Ҫ�޸ĵ��ֶε�ֵ��
	public void modifyProjectModel(Projectmodel model) {
//		Projectmodel pm = projectmodelDao.findById(model.getProjectmodelid());
//		pm.setProjectmodelname(model.getProjectmodelname());
		//ʹ��merge()����
		projectmodelDao.upName(model.getProjectmodelid(), model.getProjectmodelname());

	}
	
	
	//��ѯ����ģ����Ϣ
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

	//��ѯ������Ŀ������
	public List<Projectmodel> findAllProjectModel(){
		List<Projectmodel> list = new ArrayList<Projectmodel>();
		list = projectmodelDao.findAllProjectModel();
		return list;
	}

	//���ݱ��
	public List<Projectmodel> findByProID(Long ProID) {
		return projectmodelDao.findByProID(ProID);
	}

	//�����û���ɫ������������Ŀģ����Ϣ
	public List<Projectmodel> findByUserID(Long UserId) {
		List<Projectmodel> promodel=new ArrayList<Projectmodel>();
		//ͨ���û���ѯ��Ŀ
		List<Long> userlist=(List<Long>)projectuserDao.findByUserId(UserId);
		//ͨ����Ŀ��ѯģ��
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
