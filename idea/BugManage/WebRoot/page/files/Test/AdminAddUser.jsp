<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目管理系统 by www.mycodes.net</title>
<link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath() %>/page/css/style.css" type="text/css" media="all" />


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

function link1(){
    document.getElementById("fom").action="AdminManage.jsp";
   document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="../../../user.do?method=FindAll";
   document.getElementById("fom").submit();
}
function alert(){
    document.getElementById("fom").action="../../../user.do?method=addUser";
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
      <th class="tablestyle_title"  align="center">新建用户</th>
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
				<legend>新建用户</legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 
					  <tr>
					    <td align="right"><span class="red">*</span>员工Id:</td>
					    <td colspan="3"><input class="text" type="text" name=user.name style="width:154px" value=""/></td>
					    </tr>
						<tr>
					    <td nowrap="nowrap" align="right"><span class="red">*</span>密码:</td>
					    <td colspan="3"> <input class="text" type="password" name=user.password style="width:154px" value=""/></td>
					    </tr>
						<tr>
					    <td nowrap="nowrap" align="right"><span class="red">*</span>重置密码:</td>
					    <td colspan="3"><input class="text" type="password" name=user.password style="width:154px" value=""/></td>
					    </tr>
					  <tr>
					    <td align="right">电话:</td>
					    <td colspan="3"><input class="text" type="text" name=user.telephone style="width:154px" value=""/></td>
					    </tr>
					  <tr>
					    <td align="right">邮件:</td>
					    <td colspan="3"><input class="text" type="text" name=user.email style="width:154px" value=""/></td>
					    </tr>
					  <tr>
					    <td align="right">部门:</td>
					    <td colspan="3"><input class="text" type="text" name=user.department style="width:154px" value=""/></td>
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
			<input type="button" name="Submit" value="提交" class="button" onclick="alert();"/>　　
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
