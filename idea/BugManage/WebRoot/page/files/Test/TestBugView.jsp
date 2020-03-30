<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script language="JavaScript">
function change(){
	//修改责任人
	var userid= document.getElementById("userid").value;
	var bugid=document.getElementById("bugid").value;
	window.navigate("bug.do?method=updatePrincipal&principal="+userid+"&bugid="+bugid);
}
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

</SCRIPT>

<body>
<form action="bug.do?method=addHistory" method="post" enctype="multipart/form-data" name="fom" id="fom">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>

          <td height="62" background="<%=request.getContextPath() %>/page/images/nav04.gif"><span class="newfont07"><div align="center">
            <h3>${bug.summary}</h3>
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
				<legend>Bug信息</legend>
				<input  type="hidden" name="bug.bugid" value="${bug.bugid}"/>
					  <table width="78%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  					 <tr>
						
					 <td width="13%" align="right" nowrap="nowrap">BugID:</td>
					 <td width="11%" align="left" nowrap="nowrap"><input type="hidden" id="bugid" value="${bug.bugid}" /><label>${bug.bugid}</label></td>
					 <td width="18%" align="right" nowrap="nowrap">Bug状态:</td>
					 <td width="21%" align="left" nowrap="nowrap"><label>${bug.statusstr}</label></td>
					 <td width="12%" align="right" nowrap="nowrap">测试阶段:</td>
					 <td width="25%" align="left" nowrap="nowrap"><label>${bug.testphasestr}</label></td>
					 </tr>
					 					 <tr>
					 <td width="13%" align="right" nowrap="nowrap">所在模块:</td>
					 <td width="11%" align="left" nowrap="nowrap"><label>${bug.projectmodel.projectmodelname}</label></td>
					 <td width="18%" align="right" nowrap="nowrap">
					 <span class="red"> *</span>
					 测试版本:</td>
					 <td width="21%" align="left" nowrap="nowrap"><label>${bug.testversion}</label></td>
					 <td width="12%" align="right" nowrap="nowrap">&nbsp;</td>
					 <td width="25%" align="left" nowrap="nowrap">&nbsp;</td>
					 </tr>
					 					 <tr>
					 <td width="13%" align="right" nowrap="nowrap">测试环境:</td>
					 <td width="11%" align="left" nowrap="nowrap"><label>${bug.environment}</label></td>
					 <td width="18%" align="right" nowrap="nowrap">&nbsp;</td>
					 <td width="21%" align="left" nowrap="nowrap"><label></label></td>
					 <td width="12%" align="right" nowrap="nowrap">&nbsp;</td>
					 <td width="25%" align="left" nowrap="nowrap"><label></label></td>
					 </tr>
					 <tr>
					 <td width="13%" align="right" nowrap="nowrap">Bug说明:</td>
					 <td width="11%" align="left" nowrap="nowrap" colspan="5" rowspan="2" valign="top">
						<label>${bug.detail}</label></td>

					 </tr>
					 					 <tr>
					 <td width="13%" align="right" nowrap="nowrap">&nbsp;</td>

					 </tr>
					 					 <tr>
					 <td width="13%" align="right" nowrap="nowrap">Bug分析:</td>
					 <td width="11%" align="left" nowrap="nowrap"><label>${bug.analyse}</label></td>
					 <td width="18%" align="right" nowrap="nowrap">&nbsp;</td>
					 <td width="21%" align="left" nowrap="nowrap">&nbsp;</td>
					 <td width="12%" align="right" nowrap="nowrap">&nbsp;</td>
					 <td width="25%" align="left" nowrap="nowrap">&nbsp;</td>
					 					 </tr>
					 					 <tr>
					 <td width="13%" align="right" nowrap="nowrap">严重程度:</td>
					 <td width="11%" align="left" nowrap="nowrap"><label>${bug.gravitylevelstr}</label></td>
					 <td width="18%" align="right" nowrap="nowrap">质量特性:</td>
					 <td width="21%" align="left" nowrap="nowrap"><label>${bug.qualitycharacterstr}</label></td>
					 <td width="12%" align="right" nowrap="nowrap">再现程度:</td>
					 <td width="25%" align="left" nowrap="nowrap"><label>${bug.reappearancestr}</label></td>
					 </tr>
					 					 <tr>
					 <td width="13%" align="right" nowrap="nowrap">修改责任人:</td>
					 <td width="11%" align="left" nowrap="nowrap"><label>${bug.userinfoByPrincipal.name}</label></td>
					 <td width="18%" align="right" nowrap="nowrap">引入阶段:</td>
					 <td width="21%" align="left" nowrap="nowrap">${bug.phasestr}</td>
					 <td width="12%" align="right" nowrap="nowrap">Bug优先级:</td>
					 <td width="25%" align="left" nowrap="nowrap"><label>${bug.prioritystr}</label></td>
					 </tr>
					  <tr>
					    <td align="right" nowrap="nowrap" > 测试人员:</td>
			            <td align="left" nowrap="nowrap" >${bug.userinfoByTester.name}</td>
			            <td align="right" nowrap="nowrap" >创建时间:</td>
			            <td align="left" nowrap="nowrap" >${bug.createtime}</td>
			            <td align="left" nowrap="nowrap" >&nbsp;</td>
			            <td colspan="2" align="left" nowrap="nowrap" >&nbsp;</td>
		              </tr></table>
				 <br />
				</fieldset>			</TD>
		</TR>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr>
			<td width="100%">
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'manageProject'}">
				<fieldset style="height:100%;">
				<legend>Bug反馈-<c:if test="${bug.testerif==true}">测试人</c:if>
					<c:if test="${bug.principalif==true}">责任人</c:if>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'manageProject'}">
								项目经理
						</c:if></c:forEach>
				</legend>
						<table width="78%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  					 					 <tr>
					 <td width="8%" align="right" nowrap="nowrap">修改责任人:</td>
					 <td width="17%" align="left" nowrap="nowrap"><label>
					     <select name="bug.userinfoByPrincipal.userid" onchange="change()" id="userid">
							<c:forEach items="${Principal}" var="pri">
								<c:if test="${bug.userinfoByPrincipal.userid==pri.userid}">
								<option value="${pri.userid}" selected="selected">${pri.name}</option>
								</c:if>
					       		<c:if test="${bug.userinfoByPrincipal.userid!=pri.userid}">
								<option value="${pri.userid}" >${pri.name}</option>
								</c:if>
							</c:forEach>
				          </select>
					 </label></td>
					 <td width="17%" align="right" nowrap="nowrap">引入阶段:</td>
					 <td width="14%" align="left" nowrap="nowrap"><label>
					   <select name="bug.phase">
						<c:forEach items="${Phase}" var="phase">
							<c:if test="${bug.phase==phase.itemvalue}">
							<option value="${phase.itemvalue}" selected="selected">${phase.itemname}</option>
							</c:if>
							<c:if test="${bug.phase!=phase.itemvalue}">
							<option value="${phase.itemvalue}">${phase.itemname}</option>
							</c:if>
						</c:forEach>
				        </select>
					 </label></td>
					
					 <td width="18%" align="right" nowrap="nowrap">
						<c:if test="${bug.testerif==true}">
						关闭版本:
						</c:if>
					<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'addHistoriesByManager'}">
							优先级:
						</c:if>
					</c:forEach>
						
						</td>
					 <td width="26%" align="left" nowrap="nowrap"><label>
						<c:if test="${bug.testerif==true}">
					   <select name="closeversion">
							<c:forEach items="${Bugedition}" var="dition">
					     <option value="${dition.bugeditionid}">${dition.bugeditionname}</option>
							</c:forEach>
				        </select>
						</c:if>
				    <c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'addHistoriesByManager'}">
						<select name="bug.priority">
							<c:forEach items="${Priority}" var="pri">
					     <option value="${pri.itemvalue}">${pri.itemname}</option>
							</c:forEach>
				        </select>
						</c:if>
					</c:forEach>
					 </label></td>
					 <!--  
					 <td width="18%" align="right" nowrap="nowrap"></td>
					 <td width="26%" align="left" nowrap="nowrap"><label>

					 </label></td>
						-->	
					 </tr>
	 					 <tr>
					 <td width="8%" align="right" nowrap="nowrap">
						<c:if test="${bug.testerif!=true}">	反馈类型:</c:if>
						<c:if test="${bug.testerif==true}">修改状态:
						</c:if>
						
					</td>
					 <td width="17%" align="left" nowrap="nowrap">
						<c:if test="${bug.testerif!=true}">	
					<label>
					   <select name="bug.lastreply">
							 <option value="0">---请选择---</option>
						  <c:forEach items="${Reply}" var="re">
							<c:if test="${bug.lastreply==re.itemvalue}">
							<option value="${re.itemvalue}" selected="selected">${re.itemname}</option>
							</c:if>
							<c:if test="${bug.lastreply!=re.itemvalue}">
							<option value="${re.itemvalue}">${re.itemname}</option>
							</c:if>
						</c:forEach>
				        </select>
					 </label></c:if>
						<c:if test="${bug.testerif==true}">	 <label>
					   <select name="bug.lastreply">
						<c:forEach items="${Status}" var="sta">
							<c:if test="${bug.status==sta.itemvalue}">
					     <option value="${sta.itemvalue}" selected="selected">${sta.itemname}</option>
							</c:if>
							<c:if test="${bug.status!=sta.itemvalue}">
					     <option value="${sta.itemvalue}">${sta.itemname}</option>
							</c:if>
							</c:forEach>
				        </select>
					 </label></c:if>
						</td>
					 					 

					 <td width="17%" align="right" nowrap="nowrap">反馈人:</td>
					 <td width="14%" align="left" nowrap="nowrap"><label>刘德华</label></td>
					 <td width="18%" align="right" nowrap="nowrap">反馈时间:</td>
					 <td width="26%" align="left" nowrap="nowrap"><label>2010-2-14</label></td>
					 </tr>

					  <tr>
					    <td width="8%" align="right" nowrap="nowrap" >反馈描述:</td>
					    <td colspan="6"><label>
					      <textarea name="describe" cols="150"></textarea>
					    </label>						</td>
						</tr><tr>
					 <td width="8%" align="right" nowrap="nowrap">Bug附件:</td>
					 <td colspan="5" align="left" nowrap="nowrap" >&nbsp;<label>
					   <input name="file" type="file"   size="77" />
					 </label>
						</td></tr><tr>	
	
					<td colspan="6" align="center">  <input type="submit" class="right-button07" value="提交反馈"/></td>			 
					</tr>
					  </table>		 
				</fieldset>
				</c:if></c:forEach>

								<c:if test="${bug.testerif==true||bug.principalif==true}">
				<fieldset style="height:100%;">
				<legend>Bug反馈-<c:if test="${bug.testerif==true}">测试人</c:if>
					<c:if test="${bug.principalif==true}">责任人</c:if>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'manageProject'}">
								项目经理
</c:if></c:forEach>
				</legend>
						<table width="78%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  					 					 <tr>
					 <td width="8%" align="right" nowrap="nowrap">修改责任人:</td>
					 <td width="17%" align="left" nowrap="nowrap"><label>
					     <select name="bug.userinfoByPrincipal.userid" id="userid" onchange="change()">
							<c:forEach items="${Principal}" var="pri">
								<c:if test="${bug.userinfoByPrincipal.userid==pri.userid}">
								<option value="${pri.userid}" selected="selected">${pri.name}</option>
								</c:if>
					       		<c:if test="${bug.userinfoByPrincipal.userid!=pri.userid}">
								<option value="${pri.userid}" >${pri.name}</option>
								</c:if>
							</c:forEach>
				          </select>
					 </label></td>
					 <td width="17%" align="right" nowrap="nowrap">引入阶段:</td>
					 <td width="14%" align="left" nowrap="nowrap"><label>
					   <select name="bug.phase">
						<c:forEach items="${Phase}" var="phase">
							<c:if test="${bug.phase==phase.itemvalue}">
							<option value="${phase.itemvalue}" selected="selected">${phase.itemname}</option>
							</c:if>
							<c:if test="${bug.phase!=phase.itemvalue}">
							<option value="${phase.itemvalue}">${phase.itemname}</option>
							</c:if>
						</c:forEach>
				        </select>
					 </label></td>
					
					 <td width="18%" align="right" nowrap="nowrap">
						<c:if test="${bug.testerif==true}">
						关闭版本:
						</c:if>
					<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'addHistoriesByManager'}">
							优先级:
						</c:if>
					</c:forEach>
						
						</td>
					 <td width="26%" align="left" nowrap="nowrap"><label>
						<c:if test="${bug.testerif==true}">
					   <select name="closeversion">
							<c:forEach items="${Bugedition}" var="dition">
					     <option value="${dition.bugeditionid}">${dition.bugeditionname}</option>
							</c:forEach>
				        </select>
						</c:if>
				    <c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'addHistoriesByManager'}">
						<select name="bug.priority">
							<c:forEach items="${Priority}" var="pri">
					     <option value="${pri.itemvalue}">${pri.itemname}</option>
							</c:forEach>
				        </select>
						</c:if>
					</c:forEach>
					 </label></td>
					 <!--  
					 <td width="18%" align="right" nowrap="nowrap"></td>
					 <td width="26%" align="left" nowrap="nowrap"><label>

					 </label></td>
						-->	
					 </tr>
	 					 <tr>
					 <td width="8%" align="right" nowrap="nowrap">
						<c:if test="${bug.testerif!=true}">	反馈类型:</c:if>
						<c:if test="${bug.testerif==true}">修改状态:
						</c:if>
						
					</td>
					 <td width="17%" align="left" nowrap="nowrap">
						<c:if test="${bug.testerif!=true}">	
					<label>
					   <select name="bug.lastreply">
							 <option value="0">---请选择---</option>
						  <c:forEach items="${Reply}" var="re">
							<c:if test="${bug.lastreply==re.itemvalue}">
							<option value="${re.itemvalue}" selected="selected">${re.itemname}</option>
							</c:if>
							<c:if test="${bug.lastreply!=re.itemvalue}">
							<option value="${re.itemvalue}">${re.itemname}</option>
							</c:if>
						</c:forEach>
				        </select>
					 </label></c:if>
						<c:if test="${bug.testerif==true}">	 <label>
					   <select name="bug.lastreply">
						<c:forEach items="${Status}" var="sta">
							<c:if test="${bug.status==sta.itemvalue}">
					     <option value="${sta.itemvalue}" selected="selected">${sta.itemname}</option>
							</c:if>
							<c:if test="${bug.status!=sta.itemvalue}">
					     <option value="${sta.itemvalue}">${sta.itemname}</option>
							</c:if>
							</c:forEach>
				        </select>
					 </label></c:if>
						</td>
					 					 

					 <td width="17%" align="right" nowrap="nowrap">反馈人:</td>
					 <td width="14%" align="left" nowrap="nowrap"><label>刘德华</label></td>
					 <td width="18%" align="right" nowrap="nowrap">反馈时间:</td>
					 <td width="26%" align="left" nowrap="nowrap"><label>2010-2-14</label></td>
					 </tr>

					  <tr>
					    <td width="8%" align="right" nowrap="nowrap" >反馈描述:</td>
					    <td colspan="6"><label>
					      <textarea name="describe" cols="150"></textarea>
					    </label>						</td>
						</tr><tr>
					 <td width="8%" align="right" nowrap="nowrap">Bug附件:</td>
					 <td colspan="5" align="left" nowrap="nowrap" >&nbsp;<label>
					   <input name="file" type="file"   size="77" />
					 </label>
						</td></tr><tr>	
	
					<td colspan="6" align="center">  <input type="submit" class="right-button07" value="提交反馈"/></td>			 
					</tr>
					  </table>		 
				</fieldset>
				</c:if>



				</td>
		</tr>
                		<tr>
			<td width="100%">
				<fieldset style="height:100%;">
				<legend>Bug操作记录</legend>
					  <table width="60%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
                        <tr>
                          <td align="center">序号</td>
                          <td align="center">操作前的状态</td>
                          <td align="center">操作后的状态</td>
                          <td align="center">反馈类型</td>
                          <td align="center">反馈描述</td>
                          <td align="center">操作人</td>
                          <td>操作时间</td>
                        </tr>
					<c:forEach items="${bug.bughistories}" var="histories" >
                        <tr>
                          <td width="10%" align="center" nowrap="nowrap">${histories.historyid}</td>
                          <td width="10%" align="center" nowrap="nowrap"><label>${histories.statusbeforestr}</label></td>
                          <td width="10%" align="center" nowrap="nowrap">${histories.statusstr}</td>
                          <td width="10%" align="center" nowrap="nowrap"><label>${histories.replystr}</label></td>
                          <td align="center" width="25%"  nowrap="nowrap">${histories.describe}</td>
                          <td width="10%" align="center" nowrap="nowrap"><label>${histories.userinfo.name}</label></td>
                          <td width="25%" align="center" nowrap="nowrap"><label>${histories.createtimeStr}</label></td>
                        </tr>
					</c:forEach>
                      </table>
					  <br />
				</fieldset>			</td>
		</tr>
		</table>
	
	 </td>
  </tr>	
	  </table>
    
</div></td></tr></table></form>
</body>
</html>
