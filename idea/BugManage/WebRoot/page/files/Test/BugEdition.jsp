<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目管理系统 by www.mycodes.net</title>
<script language="javascript" type="text/javascript" 
src="<%=request.getContextPath() %>/js/WdatePicker.js"></script>
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

<link href="<%=request.getContextPath() %>/page/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">

</script>
<link href="<%=request.getContextPath() %>/page/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {font-size: 18px}
-->
</style>
</head>
<script language=JavaScript>
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
    document.getElementById("fom").action="TestProjectView.jsp";
    document.getElementById("fom").submit();
}

</script>

<body>
<form action="bug.do?method=addEdition" method="post" enctype="multipart/form-data" name="fom" id="fom">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="<%=request.getContextPath() %>/page/images/nav04.gif"><span class="newfont07"><div align="center">
           <h3>项目版本</h3>
          </div></span>
    </table></td></tr>
  <tr>
    <td><div class="MainDiv">
<table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
    <td class="CPanel">
		
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr>
			<td width="100%">
				<fieldset style="height:100%;">
				<legend>测试版本</legend>
					  <table width="78%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 <tr>
					 <td width="10%" align="right" nowrap><input type="hidden" value="${Model}" name="Model"></input></td>
					 <td width="10%" align="center" nowrap>版本编号</td>
					 <td width="15%" align="center" nowrap>
					 测试版本:</td>
					 <td width="10%" align="right" nowrap>&nbsp;</td>
					 </tr>
					<c:forEach items="${BugEdition}" var="ed">
					<tr>
					 <td width="10%" align="right" nowrap>&nbsp;</td>
					 <td width="10%" align="center" nowrap>${ed.bugeditionid}</td>
					 <td width="15%" align="center" nowrap>
					 ${ed.bugeditionnameStr}</td>
					 <td width="10%" align="right" nowrap>&nbsp;</td>
					 </tr>
					</c:forEach>
			</table>
				 <br />
				</fieldset>			</td>
		</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr>
			<td width="100%">
				<fieldset style="height:100%;">
				<legend>新增测试版本</legend>
					  <table width="78%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 <tr>
					 <td width="15%" align="right" nowrap="nowrap"> <span class="red"> *</span>Bug版本</td>
					 <td width="25%" align="left" nowrap="nowrap">
					 <input name="name" class="text" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})" style="width:250px" type="text" size="50" />
						</td>
					<td width="40%"><input type="submit" value="提交"/></td>
					<td width="20%">&nbsp;</td>
					 </tr>
					  </table>
					  				 <br />
				</fieldset>			</td>
		</tr>
                		<tr>
  </tr>	
	  </table>

</body>
</html>
