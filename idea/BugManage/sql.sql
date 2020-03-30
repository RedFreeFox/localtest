--1.�����û���UserInfo
CREATE TABLE UserInfo
(
	UserID INT PRIMARY KEY,    ---�û����
	Name varchar2(50)  NOT NULL, ---�û���
	Password varchar2(50) ,  ---�û�����
  Email varchar2(30), ---����
	Telephone varchar2(20),---�绰
  Department varchar2(20),---����
	Type INT, --0=ϵͳ����Ա��1=��Ŀ����2=������Ա��3=������Ա��4=�����Ա
  Actived INT,  --�Ƿ����
	RegistTime date, --�Ǽ�ʱ��
  LastLoginTime date --���Ǽ�ʱ��
)
select * from UserInfo
--2.������Ŀ��Project
CREATE TABLE Project
(
	ProjectID INT PRIMARY KEY, ---��Ŀ���
	ProjectName varchar2(50),  ---��Ŀ����
  StartTime DATE, ---��ʼʱ��
	ForeFinishTime DATE, ---Ԥ�����ʱ��
	FinishTime DATE,---���ʱ��
	Status INT   ---״̬ 1δ������2δ��ɣ�3����ɣ�4ע��
)

--3.������Ŀ��Ա��ProjectUser
CREATE TABLE ProjectUser
(
	PU_ID INT PRIMARY KEY, --��Ŀ��Ա����
	ProjectID INT REFERENCES Project(ProjectID), ---��Ŀ���
	ProjectUserID INT REFERENCES UserInfo(UserID), --��Ŀ�˱��
	ProjectUserType INT --0=��Ŀ����,1=������Ա,2=������Ա,3=�����Ա   ---�û���ɫ
)
select * from Project
--4.������Ŀģ���ProjectModel
CREATE TABLE ProjectModel
(
	ProjectModelID INT PRIMARY KEY , --��Ŀģ����
	ProjectID INT REFERENCES Project(ProjectID), --��Ŀ���
	FatherModelID INT REFERENCES ProjectModel(ProjectModelID) NUll,---��ģ��
	ProjectModelName varchar2(100),---��Ŀģ������
  Status int  ---״̬(ɾ����ʶ)
)

--5.����Bug��Ϣ��Bug
CREATE TABLE Bug
(
	BugID int PRIMARY KEY,---bug���
	ProjectID int NULL references Project(ProjectID),--��Ŀ���
	ModuleID int NULL  references ProjectModel(ProjectModelID),---ģ��ID
	TestPhase int NULL,---���Խ׶�
	TestVersion date NULL,---���Եİ汾
	CloseVersion date NULL,---�رյİ汾
	CreateTime date NULL,---����ʱ��
	CloseTime date NULL,---����ʱ��
	Tester integer NULL references UserInfo(UserID),---������
	Principal integer NULL references UserInfo(UserID),---������
	Environment varchar2(200) NULL,---���Ի���
	Summary varchar2(50) NULL,---��Ҫ
	Detail varchar2(200) NULL,---��ϸ
	Analyse varchar2(200) NULL,---����
	AffixPath varchar2(200) NULL,---ͼƬճ��·��
	GravityLevel int NULL,---���س̶�
	Reappearance int NULL,---���̶ֳ�
	QualityCharacter int NULL,---��������
	Phase int NULL,---�׶�
	Status int NULL,---״̬
	ModifyTimes date NULL,---����ʱ��
	Priority int NULL,---��ѡȨ
	LastModifyTime date NULL,---�����޸�ʱ��
	LastReply int NULL---���ķ���״̬
)
GO

--6.����Bug�汾��BugEdition
CREATE TABLE BugEdition
(
	BugEditionID INT PRIMARY KEY ,---ID
	ProjectID INT REFERENCES Project(ProjectID),---��ĿID
	BugEditionName Date---���԰汾��
)
GO
drop table bugedition;

--7.����Bug������BugHistory
CREATE TABLE BugHistory
(
	HistoryID INT PRIMARY KEY,---����ID
	BugID INT REFERENCES Bug(BugID),---BUG��ID
	StatusBefore INT, --֮ǰ�ķ���״̬
	describe varchar2(100),--����
	Status INT, ---���ڵķ���״̬
	Creator INT REFERENCES UserInfo(UserID),---��Ŀ����
	Reply varchar2(100),---����״̬
	CreateTime Date---����ʱ��
)
GO

--8.����Bug�����ֶα�BaseData
CREATE TABLE BaseData
(
	DataID varchar2(30) NOT NULL primary key,---������
	Info varchar2(200) NULL---�������
)

--9.����Bug�����ֶα�BaseDataItem
CREATE TABLE BaseDataItem
(
	ItemID int  NOT NULL,---���
	DataID varchar2(30) REFERENCES BaseData(DataID)  NOT NULL,---��������
	ItemName varchar2(30) NOT NULL,---������
	ItemValue varchar2(200) NOT NULL---����ֵ
)


--�û��Լ�Ȩ�ޱ�
create table role
(
 RoleID int not null primary key,
 LimitID int not null ,---Ȩ��ID
 MuenName varchar(50)null,---����
 Action varchar(50) null,--action��
 Param varchar(50) null--����
)--0=��Ŀ����,1=������Ա,2=������Ա,3=�����Ա,4=����Ա,5=��Ŀ��Ա(δ�����Ŀ)
---ģ�鷽��

--�û��Լ�Ȩ�ޱ�
insert into role values(1,0,'��Ŀչʾ',null,'showProject');
insert into role values(2,1,'��Ŀչʾ',null,'showProject');
insert into role values(3,2,'��Ŀչʾ',null,'showProject');
insert into role values(4,3,'��Ŀչʾ',null,'showProject');
insert into role values(5,4,'����ϵͳ',null,'manageSystem');
insert into role values(6,0,'BUG����',null,'showBug');
insert into role values(7,1,'BUG����',null,'showBug');
insert into role values(8,2,'BUG����',null,'showBug');
insert into role values(9,3,'BUG����',null,'showBug');
insert into role values(10,0,'ͳ�Ʊ���',null,'showReport');
insert into role values(11,1,'ͳ�Ʊ���',null,'showReport');
insert into role values(12,2,'ͳ�Ʊ���',null,'showReport');
insert into role values(13,3,'ͳ�Ʊ���',null,'showReport');
insert into role values(14,0,'��Ŀ�鿴',null,'ProjectView');
insert into role values(15,1,'��Ŀ�鿴',null,'ProjectView');
insert into role values(16,2,'��Ŀ�鿴',null,'ProjectView');
insert into role values(17,3,'��Ŀ�鿴',null,'ProjectView');
insert into role values(18,0,'��Ŀ����',null,'manageProject');
insert into role values(19,2,'���԰汾',null,'bugEdition');
insert into role values(20,2,'�½�BUG',null,'addBug');
insert into role values(21,5,'��Ŀչʾ',null,'showProject');
insert into role values(22,0,'BUGɾ��',null,'delBug');
insert into role values(23,0,'��Ŀ����BUG����',null,'addHistoriesByManager');

select *  from Role as model where model.limitid=


--���û������������
insert into UserInfo values(10000,'admin','admin','180163984@qq.com','123456789','��Ŀ����',0,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10001,'kity','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10002,'pig','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10003,'�ܽ���','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10004,'���»�','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10005,'������','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10006,'������','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10007,'κ��','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10008,'�����㱦','123','21316456@163.com','546546552','������',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10009,'�ι�˫','123','21316456@163.com','546546552','�г���',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100010,'������','123','21316456@163.com','546546552','���۲�',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100011,'�����','123','21316456@163.com','546546552','�г���',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100012,'����','123','21316456@163.com','546546552','���²�',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100013,'���','123','21316456@163.com','546546552','�з���',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));



select * from UserInfo

--����Ŀ�����������
insert into Project values(10001,'BMS',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),3);
insert into Project values(10002,'���ҹ���ϵͳ',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),2);
insert into Project values(10003,'Office',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),3);
insert into Project values(10004,'���ɹ���ϵͳ',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),null,2);
insert into Project values(10005,'BUS',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),null,1);
insert into Project values(10006,'Educate',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),null,4);


select * from Project

select * from Project where projectname like '%%'
select * from project where status in (2,3)
update project set status=3 where projectid=10003
update project set status=2 where projectid=10004
delete from project where projectid=10006

--����Ŀ��Ա�����������
insert into ProjectUser values(1,10001,10001,0);
insert into ProjectUser values(2,10001,10002,1);
insert into ProjectUser values(3,10001,10003,2);
insert into ProjectUser values(4,10001,10004,3);
insert into ProjectUser values(5,10001,10005,3);
insert into ProjectUser values(6,10001,10006,2);
insert into ProjectUser values(7,10001,10007,2);
insert into ProjectUser values(8,10001,10008,3);
insert into ProjectUser values(9,10001,10009,1);
insert into ProjectUser values(10,10001,100010,3);
insert into ProjectUser values(11,10002,10009,0);
insert into ProjectUser values(12,10002,10008,1);
insert into ProjectUser values(13,10002,100012,2);
insert into ProjectUser values(14,10002,100013,3);
insert into ProjectUser values(15,10002,10005,1);
insert into ProjectUser values(16,10002,10007,3);
insert into ProjectUser values(17,10002,100011,2);
insert into ProjectUser values(18,10002,100010,3);
insert into ProjectUser values(19,10003,10007,0);
insert into ProjectUser values(20,10003,10006,1);
insert into ProjectUser values(21,10003,10005,2);
insert into ProjectUser values(22,10003,10004,3);
insert into ProjectUser values(23,10003,10003,3);
insert into ProjectUser values(24,10003,10002,3);
insert into ProjectUser values(25,10003,100013,2);
insert into ProjectUser values(26,10003,100011,1);
insert into ProjectUser values(27,10004,10006,0);
insert into ProjectUser values(28,10004,100012,2);
insert into ProjectUser values(29,10004,100010,1);
insert into ProjectUser values(30,10004,100011,3);
select * from ProjectUser
select * from userinfo;

select * from projectuser where projectid=10001;

--��Ŀ��Ա����û��������ѯ
select p.projectusertype,u.name from userinfo u ,projectuser p 
where u.userid = p.projectuserid
and p.projectid=10002 and p.projectusertype=0
group by p.projectusertype,u.name


--������Ŀ�˱�������ѯ
select * from project where projectid in
(select  p.projectid from userinfo u,projectuser p
where u.userid = p.projectuserid and u.userid=10001)

select * from project p,projectuser u
where p.projectid = u.projectid 
and u.projectuserid = 10002


select projectusertype,name from projectuser u,project p,userinfo s 
where u.projectid = p.projectid  and s.userid = u.projectuserid and p.projectid=10001
group by projectusertype,name



--����Ŀģ������������
insert into ProjectModel values(100,10001,null,'BMS',1);
insert into ProjectModel values(101,10001,100,'BUG����',1);
insert into ProjectModel values(102,10001,100,'��Ŀ����',1);
insert into ProjectModel values(103,10001,100,'��Ŀ����',1);
insert into ProjectModel values(104,10001,100,'�û�����',1);
insert into ProjectModel values(105,10002,null,'��ʦ����ϵͳ',1);
insert into ProjectModel values(106,10002,105,'���ڹ���',1);
insert into ProjectModel values(107,10002,105,'�γ̹���',1);
insert into ProjectModel values(108,10002,105,'�༶����',1);
insert into ProjectModel values(109,10002,105,'ѧԱ����',1);
insert into ProjectModel values(1010,10003,null,'Office',1);
insert into ProjectModel values(1011,10003,1010,'���ڹ���',1);
insert into ProjectModel values(1012,10003,1010,'�ճ̹���',1);
insert into ProjectModel values(1013,10003,1010,'�ĵ�����',1);
insert into ProjectModel values(1014,10003,1010,'��Ϣ����',1);


select * from ProjectModel 
select * from project where projectid in (select projectid from projectmodel where projectmodelid=104)
select * from projectmodel where projectid=10001

select * from Projectmodel where ProjectID=10001 and Status=1 and FatherModelID is null

select projectmodelname from bug b,projectmodel p 
where b.moduleid=p.projectmodelid 
group by projectmodelname  


--��ģ���ѯ
select projectmodelname,count(*) as total 
from bug b,projectmodel p 
where b.moduleid=p.projectmodelid 
and p.fathermodelid=100
group by projectmodelname 

--���ղ�ѯ
select to_char(createtime,'yyyy-mm-dd') ,count(*) 
from bug group by createtime;

select * from bug

select * from projectmodel

--���²�ѯ
select to_char(createtime,'yyyy-mm') ,count(*) 
from bughistory group by to_char(createtime,'yyyy-mm');

select to_char(sysdate,'dd') from dual;

select * from bughistory;







--�����ֶα����������
insert into BaseData values('GravityLevel','BUG���س̶�');
insert into BaseData values('Phase','BUG����׶�');
insert into BaseData values('Priority','BUG���ȼ�');
insert into BaseData values('QualityCharacter','BUG��������');
insert into BaseData values('Reapperarance','BUG���̶ֳ�');
insert into BaseData values('Reply','�޸ķ���');
insert into BaseData values('Report','BUGͳ��');
insert into BaseData values('Status','BUG״̬');
insert into BaseData values('TestPhase','���Խ׶�');
select * from BaseData

--�������ֶα����������
insert into BaseDataItem values(1,'GravityLevel','����',1);
insert into BaseDataItem values(2,'GravityLevel','����',2);
insert into BaseDataItem values(3,'GravityLevel','һ��',3);
insert into BaseDataItem values(4,'GravityLevel','΢С',4);
insert into BaseDataItem values(5,'Phase','����',1);
insert into BaseDataItem values(6,'Phase','����',2);
insert into BaseDataItem values(7,'Phase','���',3);
insert into BaseDataItem values(8,'Phase','UI',4);
insert into BaseDataItem values(9,'Priority','����',1);
insert into BaseDataItem values(10,'Priority','һ��',2);
insert into BaseDataItem values(11,'QualityCharacter','������',1);
insert into BaseDataItem values(12,'QualityCharacter','�ɿ���',2);
insert into BaseDataItem values(13,'QualityCharacter','������',3);
insert into BaseDataItem values(14,'QualityCharacter','Ч��',4);
insert into BaseDataItem values(15,'Reapperarance','ÿ������',1);
insert into BaseDataItem values(16,'Reapperarance','��������',2);
insert into BaseDataItem values(17,'Reapperarance','ż������',3);
insert into BaseDataItem values(18,'Reapperarance','����һ��',4);
insert into BaseDataItem values(19,'Reply','δ����',1);
insert into BaseDataItem values(20,'Reply','�Ƴ��޸�',2);
insert into BaseDataItem values(21,'Reply','�Ѿ��޸�',3);
insert into BaseDataItem values(22,'Reply','��������',4);
insert into BaseDataItem values(23,'Reply','�ⲿBUG',5);
insert into BaseDataItem values(24,'Reply','��������',6);
insert into BaseDataItem values(25,'Reply','�޸�������',7);
insert into BaseDataItem values(26,'Reply','�޸����ȼ�',8);
insert into BaseDataItem values(27,'Reply','����',9);
insert into BaseDataItem values(28,'Reply','���޸�',10);
insert into BaseDataItem values(29,'Reply','����֤',11);
insert into BaseDataItem values(30,'Reply','�ѽ��',12);
insert into BaseDataItem values(31,'Reply','ע��',13);
insert into BaseDataItem values(32,'Report','BUGģ��','ModuleReport');
insert into BaseDataItem values(33,'Report','BUG״̬','StatusReport');
insert into BaseDataItem values(34,'Status','δ����',1);
insert into BaseDataItem values(35,'Status','����֤',2);
insert into BaseDataItem values(36,'Status','�ѽ��',3);
insert into BaseDataItem values(37,'Status','ע��',4);
insert into BaseDataItem values(38,'Status','����',5);
insert into BaseDataItem values(39,'Status','���޸�',6);
insert into BaseDataItem values(40,'TestPhase','��Ԫ����',1);
insert into BaseDataItem values(41,'TestPhase','���ɲ���',2);
insert into BaseDataItem values(42,'TestPhase','ȷ�ϲ���',3);
insert into BaseDataItem values(43,'TestPhase','ϵͳ����',4);
select * from BaseDataItem;
select * from BaseData;
--����״̬�����ѯ
select a.info,itemname from BaseDataItem m,BaseData a 
where m.dataid = a.dataid 
group by a,info,itemname

select dataid,itemname,count(*) from BaseDataItem
group by dataid,itemname







--��Bug�����������
insert into Bug values(10001,10001,100,1,null,null,to_date('2011-08-14 09:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','ûʲô����','��ûʲô����',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10002,10001,101,1,null,null,to_date('2011-08-21 08:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-21 14:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','ûʲô����','��ûʲô����',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10003,10001,101,1,null,null,to_date('2011-08-25 08:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-25 10:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵڶ�������','���ǵڶ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10004,10001,101,1,null,null,to_date('2011-09-01 07:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-09-02 09:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵ���������','���ǵ���������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10005,10001,102,1,null,null,to_date('2011-09-09 08:30:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-14 12:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵ���������','���ǵ���������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10006,10001,102,1,null,null,to_date('2011-09-10 07:30:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-09-14 11:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵ���������','���ǵ���������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10007,10001,103,1,null,null,to_date('2011-08-14 9:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-09-24 13:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵ���������','���ǵ���������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10008,10001,103,1,null,null,to_date('2011-09-24 04:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-10-14 11:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵ���������','���ǵ���������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10009,10001,103,1,null,null,to_date('2011-10-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-10-29 14:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵڰ�������','���ǵڰ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100010,10001,104,1,null,null,to_date('2011-11-01 13:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-15 15:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100011,10002,105,1,null,null,to_date('2011-11-15 12:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-27 15:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100012,10002,106,1,null,null,to_date('2011-11-27 08:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-27 18:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100013,10002,107,1,null,null,to_date('2011-11-28 13:20:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-29 16:10:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100014,10002,108,1,null,null,to_date('2011-11-29 14:25:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-30 17:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100015,10002,109,1,null,null,to_date('2011-11-30 13:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-12-01 18:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100016,10003,1010,1,null,null,to_date('2011-12-01 13:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-12-10 15:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
select * from bug;
select * from projectmodel;
/*
insert into Bug values(100015,10003,1015,1,null,null,to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','���ǵھ�������','���ǵھ�������',null,1,1,2,3,4,2,null,1,null,null
);
*/
select * from Bug

--��Bug�汾�����������
insert into BugEdition values(10000,10001,to_date('2011-08-14 08:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugEdition values(10001,10001,to_date('2011-09-02 08:30:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugEdition values(10002,10001,to_date('2011-10-12 12:00:00','yyyy-MM-dd hh24:mi:ss'));
select * from BugEdition
delete from bugedition;


--��Bug���������������
insert into BugHistory values(10000,10001,2,'��',3,10001,4,to_date('2011-08-14 08:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10001,10001,2,'��',1,10001,5,to_date('2011-08-15 09:30:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10002,10001,2,'��',3,10001,3,to_date('2011-08-24 07:50:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10003,10001,2,'��',4,10001,8,to_date('2011-08-27 12:15:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10004,10001,2,'��',5,10001,10,to_date('2011-08-30 09:45:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10005,10001,2,'��',3,10001,7,to_date('2011-09-14 10:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10006,10001,2,'��',5,10001,5,to_date('2011-09-25 14:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10007,10001,2,'��',3,10001,4,to_date('2011-09-30 16:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10008,10001,2,'��',6,10001,6,to_date('2011-10-05 13:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10009,10001,2,'��',3,10001,3,to_date('2011-10-19 14:00:00','yyyy-MM-dd hh24:mi:ss'));
select * from BugHistory;

--�ݹ��ѯ(��ģ���ѯ)
select a.* from  bug a left join  ProjectModel b
on(b.ProjectModelID=a.ModuleID)
where b.ProjectModelID in
(
select a.ProjectModelID from ProjectModel a
start with a.FatherModelID=100
connect by nocycle prior a.FatherModelID=a.FatherModelID
)