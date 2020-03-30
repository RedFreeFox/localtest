<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目管理系统 by www.mycodes.net</title>
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

function link(){
   document.getElementById("fom").action="<%=request.getContextPath() %>/project.do?method=AddProjectInfo";
   document.getElementById("fom").submit();
}
function link1(){
   document.getElementById("fom").action="<%=request.getContextPath() %>/project.do?method=FindProjectInfo";
   document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="../../../user.do?method=FindAll";
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
      <th class="tablestyle_title"  align="center">新建项目</th>
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
				<legend></legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 
					  <tr>
					    <td nowrap align="right" width="43%">项目名称:</td>
					    <td width="57%" colspan="3"><input name="project.projectname" type="text" class="text" style="width:174px"  /></td>
					    </tr>
					    
					    
					  <tr>
					    <td nowrap="nowrap" align="right"><span class="red">*</span>开始时间:</td>
					    <td colspan="3"><input class="text" name="startTime" style="width:174px" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})"/></td>
					    </tr>
					  
					  
					  <tr>
					    <td align="right"><span class="red">*</span>预计完成时间:</td>
					    <td colspan="3"><input class="text" name="endTime" style="width:174px" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})"/></td>
					    </tr>
					  <tr>
					    <td align="right">项目状态:</td>
					    <td colspan="3"><label>
					      <input type="radio" name="project.status" value="2" checked="checked" />
					    未完成
					    <input type="radio" name="project.status" value="3" />
					    已完成 
					    </label></td>
					  </tr>
					  </table>
			  <br />
				</fieldset>			</TD>
			
		</TR>
		

		
		
		
		</TABLE>
	
	
	 </td>
  </tr>
  
  
		
		
		
		<TR>
			<TD colspan="2" align="center" height="50px">
			<input type="button" name="Submit" value="提交" class="button" onclick="link();"/>　
		</TD>
		</TR>
		</TABLE>
	
	
	 </td>
  </tr>
  
  
  
  </table>

</div>
</form>
</body>
</html>
