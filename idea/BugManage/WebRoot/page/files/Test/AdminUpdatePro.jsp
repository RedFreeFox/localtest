<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.bugManage.entity.Projectmodel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目管理系统 by www.mycodes.net</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.tabfont01 {	
	font-family: "宋体";
	font-size: 9px;
	color: #555555;
	text-decoration: none;
	text-align: center;
}
.font051 {font-family: "宋体";
	font-size: 12px;
	color: #333333;
	text-decoration: none;
	line-height: 20px;
}
.font201 {font-family: "宋体";
	font-size: 12px;
	color: #FF0000;
	text-decoration: none;
}
.button {
	font-family: "宋体";
	font-size: 14px;
	height: 37px;
}
html { overflow-x: auto; overflow-y: auto; border:0;} 
-->
</style>
		<style>
a {
	color: #000000;
	text-decoration: none;
}

a:hover {
	color: #ff0000;
	text-decoration: underline;
}

body {
	font-size: 12px;
}

.p {
	margin-left: 10px;
}

.ps {
	margin-left: 10px;
	display: none;
}

.pss {
	margin-left: 10px;
	display: block;
}

.t {
	cursor: pointer;
	background: url(images/ico080426_close.gif) no-repeat;
	line-height: 20px;
	padding-left: 20px;
	height: 20px;
}

.f {
	cursor: pointer;
	background: url(images/ico080426_open.gif) no-repeat;
	line-height: 20px;
	padding-left: 20px;
	height: 20px;
}

.b {
	cursor: pointer;
	background: url(images/ico080426_dot.gif) no-repeat;
	line-height: 20px;
	padding-left: 20px;
	height: 20px;
}

.fblod {
	font-weight: bold;
}

.padtb8 {
	padding: 8px 0;
}

.fontred {
	color: #f00;
}
</style>
  <script type='text/javascript' src='/BugManage/dwr/interface/showPerson.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/engine.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/util.js'></script>
<link href="<%=request.getContextPath() %>/page/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">

</script>
<link href="<%=request.getContextPath() %>/page/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language=javascript
			src="<%=request.getContextPath()%>/page/js/xtree.js">
</script>
		<link href="<%=request.getContextPath()%>/page/css/xtree.css"
			rel="stylesheet" type="text/css">
</link>

<style type="text/css">
.STYLE1 {font-size: 18px}
</style>
</head>
<script language="javascript" type="text/javascript">
			function show(num){
				var proID=document.getElementById("proid").value;
				var type=num;
				document.getElementById("type").value=num;
				showPerson.findPerson(proID,type,callBackFun);
			}
			function callBackFun(data){
				DWRUtil.removeAllOptions("showAll");
				DWRUtil.addOptions("showAll",data.PersonAll,"userid","name");
				
				DWRUtil.removeAllOptions("showPer");
				DWRUtil.addOptions("showPer",data.Person,"userid","name");
			}
			function addPerson(){
				//获取选中项
				var userid=$("showAll").value;
				//遍历列表
				var All=$("showAll");
				var PersonAllList=new Array();
				for(var i=0;i<All.length;i++){
					PersonAllList[i]=All.options[i].value;
				}
				//遍历列表
				var Per=$("showPer");
				var PersonList=new Array();
				for(var j=0;j<Per.length;j++){
					PersonList[j]=Per.options[j].value;
				}
				showPerson.addPerson(userid,PersonAllList,PersonList,callBackFun);
			}
			function delPerson(){
				//获取选中项
				var userid=$("showPer").value;
				//遍历列表
				var All=$("showAll");
				var PersonAllList=new Array();
				for(var i=0;i<All.length;i++){
					PersonAllList[i]=All.options[i].value;
				}
				//遍历列表
				var Per=$("showPer");
				var PersonList=new Array();
				for(var j=0;j<Per.length;j++){
					PersonList[j]=Per.options[j].value;
				}
				showPerson.delPerson(userid,PersonAllList,PersonList,callBackFun);
			}
			function submits(){
			//proid,string[],type
				var proid=$("proid").value;
				var type=$("type").value;
				var Per=$("showPer");
				var PersonList=new Array();
				for(var j=0;j<Per.length;j++){
					PersonList[j]=Per.options[j].value;
				}
				showPerson.gobatchProjectUser(proid,type,PersonList,callBackFuns);
			}
			function callBackFuns(date){
				alert(date);
			}
	function clicks(son, fa) {
		var ob = document.getElementById(son);
		if (ob.style.display == "block" || ob.style.display == "") {
			ob.style.display = "none";
			//更换son图片
			var ob2 = document.getElementById(fa);
			ob2.style.backgroundImage = "url(images/ico080426_open.gif)";
		} else {
			ob.style.display = "block";
			var ob2 = document.getElementById(fa);
			ob2.style.backgroundImage = "url(images/ico080426_close.gif)";
		}
	}
	function show(modelid) {
	showPerson.findModelIdToModels(modelid,callBackFun)
	}
	function callBackFun(date){
	var son=document.getElementById("sonmodel");
	var father=document.getElementById("fathermodel");
	son.value=date.model.projectmodelid;
	father.value=date.fathermodel.projectmodelid;
	document.getElementById("modifyfather").value=date.fathermodel.projectmodelname;
	document.getElementById("modifymodel").value=date.model.projectmodelname;
	document.getElementById("addfather").value=date.fathermodel.projectmodelname;
	}
</script>
<SCRIPT language=JavaScript>
function sousuo(){
	window.open("gaojisousuo.htm","","depended=0,alwaysRaised=1,width=800,height=510,location=0,menubar=0,resizable=0,scrollbars=0,status=0,toolbar=0");
}
function selectAll(){
	var obj = document.fom.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid"){
			obj[i].checked = true;
		}
	}
}

function unselectAll(){
	var obj = document.fom.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid"){
			if (obj[i].checked==true) obj[i].checked = false;
			else obj[i].checked = true;
		}
	}
}

function link(){
    document.getElementById("fom").action="TestBugShow.jsp";
    document.getElementById("fom").submit();
}
function link1(){
    document.getElementById("fom").action="TestBugView.jsp";
    document.getElementById("fom").submit();
}

function modifyModel(){
    var father = document.getElementById("modifyfather").value;
    var model = document.getElementById("modifymodel").value;
    if(father==null){
    	alert("未选择模块！");
    }else {
	document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=ModifyProjectModel";
    document.getElementById("fom").submit();
	}
}
function deleteModel(){
    var father = document.getElementById("modifyfather").value;
    var model = document.getElementById("modifymodel").value;
	if(father==null){
    	alert("未选择模块！");
    }
    document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=DelProjectModel";
    document.getElementById("fom").submit();
    
}
function addModel(){
    var father = document.getElementById("addfather").value;
    var model = document.getElementById("addmodel").value;
	if(father==null){
    	alert("未选择模块！");
    }
    document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=AddProjectModel";
    document.getElementById("fom").submit();
}

</SCRIPT>
<link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath() %>/page/css/style.css" type="text/css" media="all" />
<script language="javascript" type="text/javascript" 
src="<%=request.getContextPath() %>/js/WdatePicker.js"></script>

<script language="JavaScript" type="text/javascript">
function tishi()
{
  var a=confirm('数据库中保存有该人员基本信息，您可以修改或保留该信息。');
  if(a!=true)return false;
  window.open("冲突页.htm","","depended=0,alwaysRaised=1,width=800,height=400,location=0,menubar=0,resizable=0,scrollbars=0,status=0,toolbar=0");
}

function selectAll(){
	var obj = document.fom.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid"){
			obj[i].checked = true;
		}
	}
}

function unselectAll(){
	var obj = document.fom.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid"){
			if (obj[i].checked==true) obj[i].checked = false;
			else obj[i].checked = true;
		}
	}
}

function links(){
    document.getElementById("fom").action="<%=request.getContextPath() %>/project.do?method=ModifyProjectInfo";
   document.getElementById("fom").submit();
}

function link1(){
    document.getElementById("fom").action="<%=request.getContextPath() %>/project.do?method=FindProjectInfo";
   document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="AdminUserInfoManage.jsp";
   document.getElementById("fom").submit();
}


</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
-->
</style>
</head>

<body class="ContentBody">
<form name="fom" id="fom" method="post">
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title"  align="center">修改项目</th>
  </tr>
  <tr>
    <td class="CPanel">
		
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
<input name="Submit2" type="button" class="button" value="项目管理"onclick="link1();"/>
<input name="Submit2" type="button" class="button" value="用户管理" onclick="link2();" />　			
<input name="Submit2"type="button" value="返回" class="button" onclick="window.history.go(-1);"/>
		</td></tr>
				<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>修改项目</legend>
						<input type="hidden" name="project.projectid" value="${project.projectid }" />
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 
					  <tr>
					    <td nowrap align="right" width="43%">项目名称:</td>
					    <td width="57%" colspan="3"><input name='project.projectname' type="text" class="text" style="width:174px" value="${project.projectname }" /></td>
					    </tr>
					    
					    
					  <tr>
					    <td nowrap="nowrap" align="right"><span class="red">*</span>开始时间:</td>
					    <td colspan="3"><input class="text" name="startTime" style="width:174px" value="${project.starttime }" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})"/></td>
					    </tr>
					  
					  
					  <tr>
					    <td align="right"><span class="red">*</span>预计完成时间:</td>
					    <td colspan="3"><input class="text" name="endTime" style="width:174px" value="${project.forefinishtime }" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})"/></td>
					    </tr>
					  <tr>
					    <td align="right">项目状态:</td>
					    <td colspan="3">
						<label>
						<c:if test="${project.status=='2'}">
                       <input type="radio" name="project.status" id="rad"  value="2" checked="checked"/>
                         	未完成
                       <input type="radio" name="project.status" id="rad"  value="3" />
                        	已完成
						</c:if>
						<c:if test="${project.status=='3'}">
                       <input type="radio" name="project.status" id="rad"  value="2" />
                         	未完成
                       <input type="radio" name="project.status" id="rad"  value="3" checked="checked"/>
                        	已完成
						</c:if>
						</label>
						</td>
					  </tr>
						<tr><td colspan="2" align="center">
<input type="button" name="Submit" value="提交" class="button" onclick="links()"/>　
						</td></tr>
					  </table>
			  <br />
				</fieldset>			</TD>
			
		</TR>
  </table>

</div>
</form>
</body>
</html>
