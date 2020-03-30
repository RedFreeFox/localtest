<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
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

function check()
{
document.getElementById("aa").style.display="";
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
    document.getElementById("fom").action="<%=request.getContextPath() %>/report.do?method=ModuleReport";
   document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="<%=request.getContextPath() %>/report.do?method=DateReport";
   document.getElementById("fom").submit();
}
function link3(){
    document.getElementById("fom").action="<%=request.getContextPath() %>/report.do?method=BackReport";
   document.getElementById("fom").submit();
}

</script>
<style type="text/css">

.atten {font-size:12px;font-weight:normal;color:#F00;}

</style>
</head>

<body class="ContentBody">
  <form action="" method="post" id="fom" name="form"  >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" >统计报表</th>
  </tr>
  <tr>
    <td class="CPanel">
		
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">　
			
			<input type="button" name="Submit2" value="返回" class="button" onclick="window.history.go(-1);"/>
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>新建信息</legend>
					 <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="10%">查看类型:</td>
					    <td width="15%">
						<input type="button" name="Submit3" value="Bug模块报表" class="button" onclick="link1()"/></td>
				        	
					    <td align="left" width="17%">
						<input type="button" name="Submit4" value="Bug时间报表" class="button" onclick="link2()"/></td>
					    <td width="58%" align="left">
						<input type="button" name="Submit5" value="Bug反馈报表" class="button" onclick="link3()"/></td>
				       </tr> 
					  <tr>
					    <td width="10%" align="right" nowrap>请选择项目:</td>
					    <td colspan="3">
					      <select name="modelid">
<!--							<option selected="selected">==选择==</option>-->
							<c:forEach items="${reports}" var="model">
								<option value="${model.projectmodelid}">${model.projectmodelname}</option>
							</c:forEach>
						</select></td></tr>
					  
					  
					  </table>
			  <br />
				</fieldset>			</TD>
			
		</TR>
		
		

		
		
		
		</TABLE>
	
	
	 </td>
  </tr>
  
  
		
		
		
		<TR>
		</TR>
		</TABLE>
	
	
	 </td>
  </tr>
  
  
  
  </table>

</div>
</form>
</body>
</html>
