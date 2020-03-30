
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core"%>
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

.font051 {
	font-family: "宋体";
	font-size: 12px;
	color: #333333;
	text-decoration: none;
	line-height: 20px;
}

.font201 {
	font-family: "宋体";
	font-size: 12px;
	color: #FF0000;
	text-decoration: none;
}

.button {
	font-family: "宋体";
	font-size: 14px;
	height: 37px;
}

html {
	overflow-x: auto;
	overflow-y: auto;
	border: 0;
}
-->
</style>

		<link href="<%=request.getContextPath()%>/page/css/css.css"
			rel="stylesheet" type="text/css" />
		<script type="text/JavaScript">

</script>
		<link href="<%=request.getContextPath()%>/page/css/style.css"
			rel="stylesheet" type="text/css" />
	</head>
	<script type="text/JavaScript">
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
    document.getElementById("fom").action="<%=request.getContextPath()%>/page/files/Test/AdminManage.jsp";
   document.getElementById("fom").submit();
}
function link2(){
   document.getElementById("fom").action="user.do?method=FindAll";
   document.getElementById("fom").submit();
}
function link3(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/page/files/Test/AdminAddUser.jsp";
   document.getElementById("fom").submit();
}
function updateUserUI(userid){
    document.getElementById("fom").action="user.do?method=updateUI&Id="+userid;
   document.getElementById("fom").submit();
}
function delUser(userid){
     document.getElementById("fom").action="user.do?method=del&Id="+userid;
   document.getElementById("fom").submit();
}
function find(){
     document.getElementById("fom").action="user.do?method=findByType";
   document.getElementById("fom").submit();
}
</SCRIPT>

	<body>
		<form name="fom" id="fom" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">

				<tr>
					<td height="30">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="62"
									background="<%=request.getContextPath()%>/page/images/nav04.gif">

									<table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="22">
												<img
													src="<%=request.getContextPath()%>/page/images/ico07.gif"
													width="20" height="18" />
											</td>
											<td>
												用户ID：
												<input type="text" name=user.name size="20" />
												部门名称：
												<input type="text" name=user.department size="20" />
												<input name="Submit" type="button" class="right-button02"
													value="查 询"  onclick="find();"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>

					<td>
						<table id="subtree1" style="DISPLAY: " width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="95%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td height="20" align="center">
												<input name="Submit" type="button" class="right-button08"
													value="项目管理" onclick="link1();" />
												<input name="Submit" type="button" class="right-button08"
													value="用户管理" onclick="link2();" />
												<input name="Submit" type="button" class="right-button08"
													value="新建用户" onclick="link3();" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td height="40" class="font42">
												<table width="100%" border="0" cellpadding="4"
													cellspacing="1" bgcolor="#464646" class="newfont03">

													<tr>
														<td height="20" colspan="15" align="center"
															bgcolor="#EEEEEE" class="tablestyle_title">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															项目详细列表 &nbsp;
														</td>
													</tr>
													<tr>
														<td width="6%" align="center" bgcolor="#EEEEEE">
															序号
														</td>
														<td width="10%" height="20" align="center"
															bgcolor="#EEEEEE">
															用户ID
														</td>
														<td width="20%" align="center" bgcolor="#EEEEEE">
															邮件地址
														</td>
														<td width="15%" align="center" bgcolor="#EEEEEE">
															联系电话
														</td>
														<td width="15%" align="center" bgcolor="#EEEEEE">
															所在部门
														</td>
														<td width="10%" align="center" bgcolor="#EEEEEE">
															类型
														</td>
														<td width="8%" align="center" bgcolor="#EEEEEE">
															活动权限</br>(0为该用户不可用)
														</td>
														<td width="19%" align="center" bgcolor="#EEEEEE">
															管理
														</td>
													</tr>
													<s:forEach items="${userList}" var="user" varStatus="s">
														<tr>
															<td bgcolor="#FFFFFF">
																${s.count}
															</td>
															<td height="20" bgcolor="#FFFFFF">

																${user.name }
															</td>
															<td bgcolor="#FFFFFF">
																${user.email }
															</td>
															<td bgcolor="#FFFFFF">
																${user.telephone }
															</td>
															<td bgcolor="#FFFFFF">
																${user.department }
															</td>
															<td bgcolor="#FFFFFF">
																${user.type }
															</td>
															<td bgcolor="#FFFFFF">
																${user.actived }
															</td>
															<td bgcolor="#FFFFFF">
																<input title="编辑用户" type="image"
																	src="<%=request.getContextPath()%>/page/images/ico24.gif"
																	onclick="updateUserUI(${user.userid})" ; />
																<input title="删除用户" type="image"
																	src="<%=request.getContextPath()%>/page/images/icoDel.gif"
																	onclick="delUser(${user.userid})" ; />
															</td>
														</tr>
													</s:forEach>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table width="95%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="6">
									<img src="<%=request.getContextPath()%>/page/images/spacer.gif"
										width="1" height="1" />
								</td>
							</tr>
							<tr>
								<td height="33">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0" class="right-font08">
										<tr>
											<td width="50%">
												共
												<span class="right-text09">${MaxPage}</span> 页 | 第
												<span class="right-text09">${page}</span> 页
											</td>
											<td width="49%" align="right">
												[
												<a href="#" class="right-font08">首页</a> |
												<a href="#" class="right-font08">上一页</a> |
												<a href="#" class="right-font08">下一页</a> |
												<a href="#" class="right-font08">末页</a>] 转至：
											</td>
											<td width="1%">
												<table width="20" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="1%">
															<input name="textfield3" type="text"
																class="right-textfield03" size="1" />
														</td>
														<td width="87%">
															<input name="Submit23222" type="submit"
																class="right-button06" value=" " />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
