<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目管理系统 by www.mycodes.net</title>
<style type="text/css">

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

</style>

<link href="<%=request.getContextPath() %>/page/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">

</script>
<link href="<%=request.getContextPath() %>/page/css/style.css" rel="stylesheet" type="text/css" />
</head>
<script language="javascript" type="text/javascript">
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

function link1(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/project.do?method=FindProjectInfo";
   document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="AdminUserInfoManage.jsp";
   document.getElementById("fom").submit();
}
function link3(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/page/files/Test/AdminAddProject.jsp";
   document.getElementById("fom").submit();
}
function linkUser(projectid){
    document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=ToAdminProUser&projectid="+projectid;
   document.getElementById("fom").submit();
}
function linkMOkuai(projectid){
    document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=ToAdminProModel&projectid="+projectid;
   document.getElementById("fom").submit();
}
function linkc(){
	document.getElementById("fom").action="<%=request.getContextPath()%>/project.do?method=FindProjectByNameAndStatus";
	document.getElementById("fom"
	).submit();
}
</script>

<body>
<form name="fom" id="fom" action="" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="<%=request.getContextPath() %>/page/images/nav04.gif">
            
		   <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		    <tr>
			  <td width="22"><img src="<%=request.getContextPath() %>/page/images/ico07.gif" width="20" height="18" /></td>
			  <td width="565">
			 项目名称： 
			   <input name="project.projectname" type="text" size="12" />
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   状态：
			   <label>
			 <input type="radio" name="project.status" value="-1" checked="checked" />
			 </label>
			 	
			   全部
			   <label>
			   <input type="radio" name="project.status" value="2" />
			   未完成
			   <input type="radio" name="project.status" value="3" />
			   已完成
			   </label>
			   </td>
			   <td width="169" align="left">
			   <input name="Submit" type="button" class="right-button02" value="查 询" onclick="linkc()"/></td>	
		    </tr>
          </table></td>
        </tr>
    </table></td></tr>
  <tr>
    <td><table id="subtree1" style="DISPLAY: " width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
          	 <tr>
               <td height="20"  align="center">
<input name="Submit" type="button" class="right-button08" value="项目管理"onclick="link1();"/>
<input name="Submit" type="button" class="right-button08" value="用户管理" onclick="link2();" />
<input name="Submit" type="button" class="right-button08" value="新建项目" onclick="link3();" />
		           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	              </td>
          </tr>
              <tr>
                <td height="40" class="font42"><table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#464646" class="newfont03">

					                  <tr>
                    <td height="20" colspan="15" align="center" bgcolor="#EEEEEE"class="tablestyle_title"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 项目详细列表 &nbsp;</td>
                    </tr>
                  <tr>
				    <td width="7%" align="center" bgcolor="#EEEEEE">序号</td>
					 <td width="21%" height="20" align="center" bgcolor="#EEEEEE">项目名称</td>
                     <td width="10%" align="center" bgcolor="#EEEEEE">项目状态</td>
                     <td width="16%" align="center" bgcolor="#EEEEEE">开始时间</td>
					 <td width="16%" align="center" bgcolor="#EEEEEE">预计完成时间</td>
					<td width="14%" align="center" bgcolor="#EEEEEE">设置</td>
                    <td width="16%" align="center" bgcolor="#EEEEEE">管理</td>
                  </tr>
					<c:forEach items="${projects}" var="project">
                  <tr>
				    <td bgcolor="#FFFFFF">${project.projectid }</td>
				    <td height="20" bgcolor="#FFFFFF"><a href="<%=request.getContextPath() %>/page/files/Test/listyuangongmingxi.html">${project.projectname }</a></td>
                    <td bgcolor="#FFFFFF">
						<a href="<%=request.getContextPath() %>/page/files/Test/listyuangongmingxi.html">
							<c:if test="${project.status == '2'}">未完成</c:if>
							<c:if test="${project.status == '3'}">已完成</c:if>
						</a>
					</td>
                    <td bgcolor="#FFFFFF">${project.starttime }</td>
                    <td bgcolor="#FFFFFF">${project.forefinishtime }</td>
                    <td bgcolor="#FFFFFF">
                      <input name="image" type="image" title="设置项目成员"  onclick="linkUser('${project.projectid}')" src="<%=request.getContextPath() %>/page/images/ico24.gif"; />
                      <a href="<%=request.getContextPath()%>/model.do?method=ToAdminProModel&projectid=${project.projectid}">
					设置项目模块</a>
				</td>
                    
					<td bgcolor="#FFFFFF"><a href="<%=request.getContextPath() %>/project.do?method=ToModifyProjectInfo&projectid=${project.projectid }">编辑</a>&nbsp;|&nbsp;<a href="<%=request.getContextPath() %>/project.do?method=DeleteProject&projectid=${project.projectid }">删除</a></td>
                  </tr>
					</c:forEach>
                  
                </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="6"><img src="<%=request.getContextPath() %>/page/images/spacer.gif" width="1" height="1" /></td>
        </tr>
        <tr>
          <td height="33"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="right-font08">
              <tr>
                <td width="50%">共 <span class="right-text09">5</span> 页 | 第 <span class="right-text09">1</span> 页</td>
                <td width="49%" align="right">[<a href="#" class="right-font08">首页</a> | <a href="#" class="right-font08">上一页</a> | <a href="#" class="right-font08">下一页</a> | <a href="#" class="right-font08">末页</a>] 转至：</td>
                <td width="1%"><table width="20" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="1%"><input name="textfield3" type="text" class="right-textfield03" size="1" /></td>
                      <td width="87%"><input name="Submit23222" type="submit" class="right-button06" value=" " />
                      </td>
                    </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
