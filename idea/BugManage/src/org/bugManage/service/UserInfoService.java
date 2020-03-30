package org.bugManage.service;

import java.util.List;
import org.bugManage.entity.Role;
import org.bugManage.entity.Userinfo;

public interface UserInfoService {
	
	//��¼���ж��Ƿ��Ǳ�ֹͣ���û���
	public List<Userinfo> login(Userinfo user);
	
	//��ѯ������Ϣ(���֣����䣬���ţ��绰)
	public Userinfo findPersonUserinfo(long userid);
	
	//�޸ĸ�����Ϣ(���֣����䣬���ţ��绰)
	public void updatePersonUserifo(Userinfo user);
	
	//�޸�����
	public void updatePwd(Userinfo user);
	
	//����Ż��߲��� ��ѯ
	public List<Userinfo> findByType(String Id,Long page,Long number,String Dep);
	
	
	//��ʾ�����û�����
	public List<Userinfo> findAll();
	
	//�޸��û�(�绰���ʼ������ţ�������)
	public void updateUserInfo(Userinfo user);
	
	//ɾ�� ��1Ϊ��,0δ�����ã�
	public void del(Userinfo user);
	
	//���ݱ�Ų���
	public Userinfo findById(long userid);
	
	//�½��û�
	public void addUser(Userinfo user);


	//��ѯȨ��
	public List<Role> findRole(Long LimitID);
	//ͨ����Ŀ�����û�����ѯȨ��
	public List<Role> findRoleByProIDAndUserId(Long ProID,Long UserID);

	
	
	//��ҳ
	//��ȡ    page--��ǰҳ��  number--һҳ����ʾ�����ݸ���
	public List<Userinfo> findPage(int page,int number);
	//��ȡ����ҳ��
	public Long findPageMax(long number) ;
	public List<Userinfo> findByUser(Long userid,Long page,Long number);

}
