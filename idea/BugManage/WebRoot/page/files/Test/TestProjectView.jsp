<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目查看</title>
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
<script language="javascript">
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
    document.getElementById("fom").action="<%=request.getContextPath()%>/report.do?method=LoadReport";
    document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="TestProjectView.jsp";
    document.getElementById("fom").submit();
}

</SCRIPT>

<body>
<form name="fom" id="fom" method="post" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="../../images/nav04.gif"><span class="newfont07"><div align="center">
					<h3>${project.projectname}</h3>
          </div></span>
    </table></td></tr>
  <tr>
    <td><div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
    <td class="CPanel">
		
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>项目基本信息</legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 <tr>
					 <td nowrap align="right" width="38%">开始时间:</td>
					 <td colspan="4">
					 <input name="text" class="text" style="width:250px" type="text" size="40" value="${project.starttime }" readonly="readonly" />					 </tr>
					  <tr>
					    <td nowrap align="right" width="38%">预计完成时间:</td>
					    <td colspan="6"><input name="text" class="text" style="width:250px" type="text" size="40" value="${project.forefinishtime }" readonly="readonly"/></td>
					    </tr>
					  <tr>
					    <td nowrap align="right">项目状态:</td>
					    <td colspan="6">
						<c:if test="${project.status eq '2'}">
                       <input type="radio" name="radiobutton" id="rad"  value="2" checked="checked" readonly="readonly"/>
                         	未完成
                       <input type="radio" name="radiobutton" id="rad"  value="3" readonly="readonly" />
                        	已完成
						</c:if>
						<c:if test="${project.status eq '3'}">
                       <input type="radio" name="radiobutton" id="rad"  value="2" readonly="readonly" />
                         	未完成
                       <input type="radio" name="radiobutton" id="rad"  value="3" checked="checked" readonly="readonly"/>
                        	已完成
						</c:if>
					    </label></td>
					    </tr>
					   		<tr>
						    	<td nowrap align="right" width="38%">项目经理:</td>
								<td colspan="4">
						 			<input name="text" class="text" style="width:250px" type="text" size="40" value="${p0 }" readonly="readonly" />						</tr>
								</td>
							</tr>
							<tr>
					    		<td nowrap align="right" width="38%">开发人员:</td>
						 		<td colspan="4">
					 				<input name="text" class="text" style="width:250px" type="text" size="40" value="${p1 }" readonly="readonly" />						</tr>
								</td>			   
							</tr>
							<tr>
					  			<td nowrap align="right" width="38%">测试人员:</td>
								<td colspan="4">
					 				<input name="text" class="text" style="width:250px" type="text" size="40" value="${p2 }" readonly="readonly" />						</tr>
								</td>
							</tr>
							<tr>
					    		<td nowrap align="right" width="38%">浏览用户:</td>
						 		<td colspan="4">
					 				<input name="text" class="text" style="width:250px" type="text" size="40" value="${p3 }" readonly="readonly" />						</tr>
								</td>
							</tr>						
						 <tr>
					    <td nowrap align="right" width="38%">项目模块:</td>
						 <td width="11%">
						 <h5>BMS</h5>
						 <h5>项目管理</h5>
						 <td width="10%">
						 <h5>用户管理</h5>                        
						 <td width="10%">
						 <h5>个人设置</h5>                         
						 <td width="31%"> 
						 <h5>Bug列表</h5>                        
					    </tr>
					  </table>
			 <br />
				</fieldset>			</TD>
		</TR>
		</TABLE>
	
	
	 </td>
  </tr>
  

		
		
		
		</TABLE>
</form>
</body>
</html>
