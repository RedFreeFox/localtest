package org.bugManage.service;

import java.util.Date;
import java.util.List;
import java.util.Map;


import org.bugManage.entity.Basedata;
import org.bugManage.entity.Basedataitem;
import org.bugManage.entity.Bug;
import org.bugManage.entity.Bugedition;
import org.bugManage.entity.Bughistory;
import org.bugManage.entity.Project;
import org.bugManage.entity.Projectmodel;
import org.bugManage.entity.Userinfo;


public interface BugService {
//	�����Ա
	//	�鿴BUG��ϸ �Լ�����
	public Bug findByID(Long id);
	//	����������
	public List<Bug> findByType(Long BugID,Long Tester,Long Principal,Long Status,String Summary,String StarDate,String OverDate,Long page,Long number,Long Model);
	//	��ģ�����
	public List<Bug> findByModel(Long Model,Long page,Long number);
	//��ȡ����ҳ��
	public Long pagesoftBug(Long Model,Long number,Long Type);
	//	����(���ȣ�����ʱ�䣬״̬����������)
	public List<Bug> softBug(Long Model,Long page,Long number,Long Type);
//������Ա(���Լ���BUG)
	//�½�BUG
	public void addBug(Bug bug);

//������Ա(�Լ���BUG)
	//	�޸�BUG
	public void setBug(Bug bug);
	//	ɾ��BUG������״̬Ϊ�� ע����
	public void delBug(Long bugid,Long userid);
	//	BUG�������޸�������,����׶�,�رհ汾,�޸�Bug״̬��
	public void addHistoryByConnerPer(Bug bug,Bughistory bh);
//������Ա(������)
	//	BUG����(�޸�������,����׶�)
	public void addHistoryByDeveloper(Bug bug,Bughistory bh);
//��Ŀ����
	//	ɾ��BUG������״̬Ϊ�� ע����
	//��ͬ������Ա
	//	BUG�������޸�������,����׶�,���ȼ���
	public void addHistoryByItemManager(Bug bug,Bughistory bh);
	

	//��ʾ����׶κͰ汾
	public List<Bugedition> findEdition(Long ProID);
	//��ʾBUG��Ϣ
	public List<Basedataitem> findByBasedataitem(String Basedataitem);
	//�������ģ��(��Ŀ��)
	public List<Projectmodel> ProjectmodelfindByModel(Long ProID);
	//�������ģ��
	public List<Projectmodel> ProjectfindByAll();
	//��ȡ��Ŀ����/����/���/������Ա
	public List<Userinfo> UserInfoFindByProjectID(Long ProID,Long type);
	//��ȡ������
	public List<Userinfo> findAllUserInfo();
	//��ȡ�ⲿ��Ա
	public List<Userinfo> findAllByNoProID(Long ProID);
	//�½����԰汾
	public void addBugEdition(Bugedition bugEdition);
	//ͨ���û�ID��ѯ�ض���
	public Userinfo userFindByID(Long ID);
	//����������Ŀ��Ա���ɾ��
	public void batchProjectUser(List<Userinfo> user,Long ProID,Long Type);
	//ͨ��ģ�������ص�ǰģ���Լ���ģ��
	public Map<String, List<Projectmodel>> modelFindByModel(Long Model,Long ProID);
	//��ȡ��ģ���ҳ��
	public Long pageByModel(Long Model,Long number);
	//��ȡ�������ҳ��
	public Long pageBySort(Long Model,Long number);
	//��ȡ�����ҵ�ҳ��
	public Long pageByType(Long Model,Long Tester,Long Principal,Long Status,String Summary,String StarDate,String OverDate,Long number,Long priID);
	//��ѯģ��
	public Projectmodel findModelIdToModel(Long Model);
	//��ģ��ID��ѯ��BUG
	public List<Bug> findBugByBUGID(Long Model,Long bugid);
	//��ȡ�û���Ŀ�е����
	public Long TypeFindByProIDAndUser(Long ProID,Long User);
	//�����ֶ�
	public String findByDateIdAndValue(String DataID,String ItemValue);
	//�޸�������(BUGid,������,������)
	public void setPrincipal(Long bugid,Long principal,Long userid);
	
	public Map<String, List<Userinfo>> findPerson(Long ProID,Long type);
	public Map<String, List<Userinfo>> addPerson(Long userid,List<String> PersonAll,List<String> Person);
	public Map<String, List<Userinfo>> delPerson(Long userid,List<String> PersonAll,List<String> Person);
	//ͨ��ģ�������ص�ǰģ���Լ���ģ��
	public Map<String,Projectmodel> findModelIdToModels(Long Model);
	//����������Ŀ��Ա���ɾ��
	public String gobatchProjectUser(Long proID,Long type,List<String> Person);
}
