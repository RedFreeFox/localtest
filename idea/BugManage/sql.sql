--1.创建用户表UserInfo
CREATE TABLE UserInfo
(
	UserID INT PRIMARY KEY,    ---用户编号
	Name varchar2(50)  NOT NULL, ---用户名
	Password varchar2(50) ,  ---用户密码
  Email varchar2(30), ---邮箱
	Telephone varchar2(20),---电话
  Department varchar2(20),---部门
	Type INT, --0=系统管理员，1=项目经理，2=开发人员，3=测试人员，4=浏览人员
  Actived INT,  --是否可用
	RegistTime date, --登记时间
  LastLoginTime date --最后登记时间
)
select * from UserInfo
--2.创建项目表Project
CREATE TABLE Project
(
	ProjectID INT PRIMARY KEY, ---项目编号
	ProjectName varchar2(50),  ---项目名称
  StartTime DATE, ---开始时间
	ForeFinishTime DATE, ---预计完成时间
	FinishTime DATE,---完成时间
	Status INT   ---状态 1未创建，2未完成，3已完成，4注销
)

--3.创建项目人员表ProjectUser
CREATE TABLE ProjectUser
(
	PU_ID INT PRIMARY KEY, --项目人员表编号
	ProjectID INT REFERENCES Project(ProjectID), ---项目编号
	ProjectUserID INT REFERENCES UserInfo(UserID), --项目人编号
	ProjectUserType INT --0=项目经理,1=开发人员,2=测试人员,3=浏览人员   ---用户角色
)
select * from Project
--4.创建项目模块表ProjectModel
CREATE TABLE ProjectModel
(
	ProjectModelID INT PRIMARY KEY , --项目模块编号
	ProjectID INT REFERENCES Project(ProjectID), --项目编号
	FatherModelID INT REFERENCES ProjectModel(ProjectModelID) NUll,---父模块
	ProjectModelName varchar2(100),---项目模块名称
  Status int  ---状态(删除标识)
)

--5.创建Bug信息表Bug
CREATE TABLE Bug
(
	BugID int PRIMARY KEY,---bug编号
	ProjectID int NULL references Project(ProjectID),--项目编号
	ModuleID int NULL  references ProjectModel(ProjectModelID),---模块ID
	TestPhase int NULL,---测试阶段
	TestVersion date NULL,---测试的版本
	CloseVersion date NULL,---关闭的版本
	CreateTime date NULL,---创建时间
	CloseTime date NULL,---结束时间
	Tester integer NULL references UserInfo(UserID),---测试人
	Principal integer NULL references UserInfo(UserID),---责任人
	Environment varchar2(200) NULL,---测试环境
	Summary varchar2(50) NULL,---概要
	Detail varchar2(200) NULL,---详细
	Analyse varchar2(200) NULL,---分析
	AffixPath varchar2(200) NULL,---图片粘贴路径
	GravityLevel int NULL,---严重程度
	Reappearance int NULL,---再现程度
	QualityCharacter int NULL,---质量特性
	Phase int NULL,---阶段
	Status int NULL,---状态
	ModifyTimes date NULL,---更改时间
	Priority int NULL,---优选权
	LastModifyTime date NULL,---最后的修改时间
	LastReply int NULL---最后的反馈状态
)
GO

--6.创建Bug版本表BugEdition
CREATE TABLE BugEdition
(
	BugEditionID INT PRIMARY KEY ,---ID
	ProjectID INT REFERENCES Project(ProjectID),---项目ID
	BugEditionName Date---测试版本名
)
GO
drop table bugedition;

--7.创建Bug反馈表BugHistory
CREATE TABLE BugHistory
(
	HistoryID INT PRIMARY KEY,---反馈ID
	BugID INT REFERENCES Bug(BugID),---BUG的ID
	StatusBefore INT, --之前的反馈状态
	describe varchar2(100),--描述
	Status INT, ---现在的反馈状态
	Creator INT REFERENCES UserInfo(UserID),---项目经理
	Reply varchar2(100),---反馈状态
	CreateTime Date---创建时间
)
GO

--8.创建Bug父项字段表BaseData
CREATE TABLE BaseData
(
	DataID varchar2(30) NOT NULL primary key,---父项名
	Info varchar2(200) NULL---父项解释
)

--9.创建Bug子项字段表BaseDataItem
CREATE TABLE BaseDataItem
(
	ItemID int  NOT NULL,---编号
	DataID varchar2(30) REFERENCES BaseData(DataID)  NOT NULL,---所属父项
	ItemName varchar2(30) NOT NULL,---子项名
	ItemValue varchar2(200) NOT NULL---子项值
)


--用户以及权限表
create table role
(
 RoleID int not null primary key,
 LimitID int not null ,---权限ID
 MuenName varchar(50)null,---功能
 Action varchar(50) null,--action名
 Param varchar(50) null--属性
)--0=项目经理,1=开发人员,2=测试人员,3=浏览人员,4=管理员,5=项目人员(未点击项目)
---模块方面

--用户以及权限表
insert into role values(1,0,'项目展示',null,'showProject');
insert into role values(2,1,'项目展示',null,'showProject');
insert into role values(3,2,'项目展示',null,'showProject');
insert into role values(4,3,'项目展示',null,'showProject');
insert into role values(5,4,'管理系统',null,'manageSystem');
insert into role values(6,0,'BUG管理',null,'showBug');
insert into role values(7,1,'BUG管理',null,'showBug');
insert into role values(8,2,'BUG管理',null,'showBug');
insert into role values(9,3,'BUG管理',null,'showBug');
insert into role values(10,0,'统计报表',null,'showReport');
insert into role values(11,1,'统计报表',null,'showReport');
insert into role values(12,2,'统计报表',null,'showReport');
insert into role values(13,3,'统计报表',null,'showReport');
insert into role values(14,0,'项目查看',null,'ProjectView');
insert into role values(15,1,'项目查看',null,'ProjectView');
insert into role values(16,2,'项目查看',null,'ProjectView');
insert into role values(17,3,'项目查看',null,'ProjectView');
insert into role values(18,0,'项目管理',null,'manageProject');
insert into role values(19,2,'测试版本',null,'bugEdition');
insert into role values(20,2,'新建BUG',null,'addBug');
insert into role values(21,5,'项目展示',null,'showProject');
insert into role values(22,0,'BUG删除',null,'delBug');
insert into role values(23,0,'项目经理BUG反馈',null,'addHistoriesByManager');

select *  from Role as model where model.limitid=


--向用户表中添加数据
insert into UserInfo values(10000,'admin','admin','180163984@qq.com','123456789','项目主管',0,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10001,'kity','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10002,'pig','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10003,'周杰伦','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10004,'刘德华','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10005,'周星星','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10006,'刘辉明','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10007,'魏厚江','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10008,'苏林秀宝','123','21316456@163.com','546546552','开发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(10009,'游贵双','123','21316456@163.com','546546552','市场部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100010,'董先文','123','21316456@163.com','546546552','销售部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100011,'缪祥兵','123','21316456@163.com','546546552','市场部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100012,'宋武','123','21316456@163.com','546546552','人事部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into UserInfo values(100013,'朱光','123','21316456@163.com','546546552','研发部',1,1,to_date('1999-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-08 14-12:00:00','yyyy-MM-dd hh24:mi:ss'));



select * from UserInfo

--向项目表中添加数据
insert into Project values(10001,'BMS',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),3);
insert into Project values(10002,'教室管理系统',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),2);
insert into Project values(10003,'Office',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),3);
insert into Project values(10004,'网吧管理系统',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),null,2);
insert into Project values(10005,'BUS',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),null,1);
insert into Project values(10006,'Educate',to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),to_date('2011-09-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),null,4);


select * from Project

select * from Project where projectname like '%%'
select * from project where status in (2,3)
update project set status=3 where projectid=10003
update project set status=2 where projectid=10004
delete from project where projectid=10006

--向项目人员表中添加数据
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

--项目人员表和用户表连表查询
select p.projectusertype,u.name from userinfo u ,projectuser p 
where u.userid = p.projectuserid
and p.projectid=10002 and p.projectusertype=0
group by p.projectusertype,u.name


--根据项目人编号连表查询
select * from project where projectid in
(select  p.projectid from userinfo u,projectuser p
where u.userid = p.projectuserid and u.userid=10001)

select * from project p,projectuser u
where p.projectid = u.projectid 
and u.projectuserid = 10002


select projectusertype,name from projectuser u,project p,userinfo s 
where u.projectid = p.projectid  and s.userid = u.projectuserid and p.projectid=10001
group by projectusertype,name



--向项目模块表中添加数据
insert into ProjectModel values(100,10001,null,'BMS',1);
insert into ProjectModel values(101,10001,100,'BUG管理',1);
insert into ProjectModel values(102,10001,100,'项目管理',1);
insert into ProjectModel values(103,10001,100,'项目报表',1);
insert into ProjectModel values(104,10001,100,'用户管理',1);
insert into ProjectModel values(105,10002,null,'教师管理系统',1);
insert into ProjectModel values(106,10002,105,'考勤管理',1);
insert into ProjectModel values(107,10002,105,'课程管理',1);
insert into ProjectModel values(108,10002,105,'班级管理',1);
insert into ProjectModel values(109,10002,105,'学员管理',1);
insert into ProjectModel values(1010,10003,null,'Office',1);
insert into ProjectModel values(1011,10003,1010,'考勤管理',1);
insert into ProjectModel values(1012,10003,1010,'日程管理',1);
insert into ProjectModel values(1013,10003,1010,'文档管理',1);
insert into ProjectModel values(1014,10003,1010,'消息管理',1);


select * from ProjectModel 
select * from project where projectid in (select projectid from projectmodel where projectmodelid=104)
select * from projectmodel where projectid=10001

select * from Projectmodel where ProjectID=10001 and Status=1 and FatherModelID is null

select projectmodelname from bug b,projectmodel p 
where b.moduleid=p.projectmodelid 
group by projectmodelname  


--按模块查询
select projectmodelname,count(*) as total 
from bug b,projectmodel p 
where b.moduleid=p.projectmodelid 
and p.fathermodelid=100
group by projectmodelname 

--按日查询
select to_char(createtime,'yyyy-mm-dd') ,count(*) 
from bug group by createtime;

select * from bug

select * from projectmodel

--按月查询
select to_char(createtime,'yyyy-mm') ,count(*) 
from bughistory group by to_char(createtime,'yyyy-mm');

select to_char(sysdate,'dd') from dual;

select * from bughistory;







--向父项字段表中添加数据
insert into BaseData values('GravityLevel','BUG严重程度');
insert into BaseData values('Phase','BUG引入阶段');
insert into BaseData values('Priority','BUG优先级');
insert into BaseData values('QualityCharacter','BUG质量特性');
insert into BaseData values('Reapperarance','BUG再现程度');
insert into BaseData values('Reply','修改反馈');
insert into BaseData values('Report','BUG统计');
insert into BaseData values('Status','BUG状态');
insert into BaseData values('TestPhase','测试阶段');
select * from BaseData

--向子项字段表中添加数据
insert into BaseDataItem values(1,'GravityLevel','致命',1);
insert into BaseDataItem values(2,'GravityLevel','严重',2);
insert into BaseDataItem values(3,'GravityLevel','一般',3);
insert into BaseDataItem values(4,'GravityLevel','微小',4);
insert into BaseDataItem values(5,'Phase','需求',1);
insert into BaseDataItem values(6,'Phase','编码',2);
insert into BaseDataItem values(7,'Phase','设计',3);
insert into BaseDataItem values(8,'Phase','UI',4);
insert into BaseDataItem values(9,'Priority','优先',1);
insert into BaseDataItem values(10,'Priority','一般',2);
insert into BaseDataItem values(11,'QualityCharacter','功能性',1);
insert into BaseDataItem values(12,'QualityCharacter','可靠性',2);
insert into BaseDataItem values(13,'QualityCharacter','易用性',3);
insert into BaseDataItem values(14,'QualityCharacter','效率',4);
insert into BaseDataItem values(15,'Reapperarance','每次再现',1);
insert into BaseDataItem values(16,'Reapperarance','经常再现',2);
insert into BaseDataItem values(17,'Reapperarance','偶尔再现',3);
insert into BaseDataItem values(18,'Reapperarance','出现一次',4);
insert into BaseDataItem values(19,'Reply','未反馈',1);
insert into BaseDataItem values(20,'Reply','推迟修改',2);
insert into BaseDataItem values(21,'Reply','已经修改',3);
insert into BaseDataItem values(22,'Reply','描述不清',4);
insert into BaseDataItem values(23,'Reply','外部BUG',5);
insert into BaseDataItem values(24,'Reply','存在异议',6);
insert into BaseDataItem values(25,'Reply','修改责任人',7);
insert into BaseDataItem values(26,'Reply','修改优先级',8);
insert into BaseDataItem values(27,'Reply','遗留',9);
insert into BaseDataItem values(28,'Reply','待修复',10);
insert into BaseDataItem values(29,'Reply','待验证',11);
insert into BaseDataItem values(30,'Reply','已解决',12);
insert into BaseDataItem values(31,'Reply','注销',13);
insert into BaseDataItem values(32,'Report','BUG模块','ModuleReport');
insert into BaseDataItem values(33,'Report','BUG状态','StatusReport');
insert into BaseDataItem values(34,'Status','未创建',1);
insert into BaseDataItem values(35,'Status','待验证',2);
insert into BaseDataItem values(36,'Status','已解决',3);
insert into BaseDataItem values(37,'Status','注销',4);
insert into BaseDataItem values(38,'Status','遗留',5);
insert into BaseDataItem values(39,'Status','待修复',6);
insert into BaseDataItem values(40,'TestPhase','单元测试',1);
insert into BaseDataItem values(41,'TestPhase','集成测试',2);
insert into BaseDataItem values(42,'TestPhase','确认测试',3);
insert into BaseDataItem values(43,'TestPhase','系统测试',4);
select * from BaseDataItem;
select * from BaseData;
--根据状态分组查询
select a.info,itemname from BaseDataItem m,BaseData a 
where m.dataid = a.dataid 
group by a,info,itemname

select dataid,itemname,count(*) from BaseDataItem
group by dataid,itemname







--向Bug表中添加数据
insert into Bug values(10001,10001,100,1,null,null,to_date('2011-08-14 09:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','没什么问题','真没什么问题',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10002,10001,101,1,null,null,to_date('2011-08-21 08:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-21 14:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','没什么问题','真没什么问题',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10003,10001,101,1,null,null,to_date('2011-08-25 08:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-25 10:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第二条数据','我是第二条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10004,10001,101,1,null,null,to_date('2011-09-01 07:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-09-02 09:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第三条数据','我是第三条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10005,10001,102,1,null,null,to_date('2011-09-09 08:30:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-08-14 12:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第四条数据','我是第四条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10006,10001,102,1,null,null,to_date('2011-09-10 07:30:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-09-14 11:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第五条数据','我是第五条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10007,10001,103,1,null,null,to_date('2011-08-14 9:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-09-24 13:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第六条数据','我是第六条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10008,10001,103,1,null,null,to_date('2011-09-24 04:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-10-14 11:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第七条数据','我是第七条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(10009,10001,103,1,null,null,to_date('2011-10-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-10-29 14:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第八条数据','我是第八条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100010,10001,104,1,null,null,to_date('2011-11-01 13:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-15 15:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100011,10002,105,1,null,null,to_date('2011-11-15 12:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-27 15:30:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100012,10002,106,1,null,null,to_date('2011-11-27 08:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-27 18:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100013,10002,107,1,null,null,to_date('2011-11-28 13:20:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-29 16:10:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100014,10002,108,1,null,null,to_date('2011-11-29 14:25:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-11-30 17:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100015,10002,109,1,null,null,to_date('2011-11-30 13:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-12-01 18:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
insert into Bug values(100016,10003,1010,1,null,null,to_date('2011-12-01 13:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2011-12-10 15:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
select * from bug;
select * from projectmodel;
/*
insert into Bug values(100015,10003,1015,1,null,null,to_date('2011-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),
to_date('2012-08-14 12:00:00','yyyy-MM-dd hh24:mi:ss'),10002,10004,'IE8','我是第九条数据','我是第九条数据',null,1,1,2,3,4,2,null,1,null,null
);
*/
select * from Bug

--向Bug版本表中添加数据
insert into BugEdition values(10000,10001,to_date('2011-08-14 08:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugEdition values(10001,10001,to_date('2011-09-02 08:30:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugEdition values(10002,10001,to_date('2011-10-12 12:00:00','yyyy-MM-dd hh24:mi:ss'));
select * from BugEdition
delete from bugedition;


--向Bug反馈表中添加数据
insert into BugHistory values(10000,10001,2,'无',3,10001,4,to_date('2011-08-14 08:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10001,10001,2,'无',1,10001,5,to_date('2011-08-15 09:30:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10002,10001,2,'无',3,10001,3,to_date('2011-08-24 07:50:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10003,10001,2,'无',4,10001,8,to_date('2011-08-27 12:15:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10004,10001,2,'无',5,10001,10,to_date('2011-08-30 09:45:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10005,10001,2,'无',3,10001,7,to_date('2011-09-14 10:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10006,10001,2,'无',5,10001,5,to_date('2011-09-25 14:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10007,10001,2,'无',3,10001,4,to_date('2011-09-30 16:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10008,10001,2,'无',6,10001,6,to_date('2011-10-05 13:00:00','yyyy-MM-dd hh24:mi:ss'));
insert into BugHistory values(10009,10001,2,'无',3,10001,3,to_date('2011-10-19 14:00:00','yyyy-MM-dd hh24:mi:ss'));
select * from BugHistory;

--递归查询(按模块查询)
select a.* from  bug a left join  ProjectModel b
on(b.ProjectModelID=a.ModuleID)
where b.ProjectModelID in
(
select a.ProjectModelID from ProjectModel a
start with a.FatherModelID=100
connect by nocycle prior a.FatherModelID=a.FatherModelID
)