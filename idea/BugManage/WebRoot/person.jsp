<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <script type='text/javascript' src='/BugManage/dwr/interface/showPerson.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/engine.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/util.js'></script>


	<script type="text/javascript">
			function show(){
				var proID=$("proID").value;
				var type=$("type").value;
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
				var Per=$("showPer");
				var PersonList=new Array();
				for(var j=0;j<Per.length;j++){
					Per.options[j].selected=true;
				}
			}
	</script>

  </head>
  
  <body>
    <form action="bug.do?method=batchProjectUser" method="post">
		当前项目:<input name="proID"  type="text" id="proID" value="10001">
		<br>
		<select id="type" name="type">
			<option value="0">项目经理</option>
			<option value="1">开发人员</option>
			<option value="2">测试人员</option>
			<option value="3">浏览人员</option>
		</select>
		<input type="button" value="点我出现" onclick="show()" >

		<table>
	<tr><td>
			<select id="showAll" name="All" multiple="multiple" size="10">
			<option>---请选择------</option>
			</select>
	</td><td>
		<input type="button" value="==>" onclick="addPerson()" ><br>
		<input type="button" value="<==" onclick="delPerson()">
	</td><td>
		<select id="showPer" name="showPer" multiple="multiple" size="10">
			<option>---请选择------</option>
		</select>
	</td></tr><tr><td colspan="3" align="center"><input type="submit" value="提交" onclick="submits()"></td></tr>
</table>
	</form></body>
</html>
