<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>Bug主页——mian窗体</title>
<script language="javascript" type="text/javascript" 
src="<%=request.getContextPath() %>/js/WdatePicker.js"></script>
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
<style type="text/css">

.STYLE1 {font-size: 18px}

</style>
</head>
<script language="javascript" type="text/javascript" 
src="<%=request.getContextPath() %>/js/WdatePicker.js"></script>
<SCRIPT language=JavaScript>
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
	var Model=document.getElementById("Model").value;
    document.getElementById("fom").action="<%=request.getContextPath()%>/bug.do?method=findByModel&Model="+Model+"";
    document.getElementById("fom").submit();
}
function link1(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/report.do?method=LoadReport";
    document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/project.do?method=LookProject";
    document.getElementById("fom").submit();
}
function linkp(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/project.do?method=LookProjectInfo";
    document.getElementById("fom").submit();
}
function link5(){
	var Model=document.getElementById("Model").value;
    document.getElementById("fom").action="bug.do?method=findEdition&Model="+Model;
    document.getElementById("fom").submit();
}
function link3(){
	var Model=document.getElementById("Model").value;
    document.getElementById("fom").action="bug.do?method=addBugUI&Model="+Model;
    document.getElementById("fom").submit();
}
function link4(){
	var bugid=document.getElementById("bugid").value;
    document.getElementById("fom").action="bug.do?method=findByIdsear&bugid="+bugid;
    document.getElementById("fom").submit();
}
function linkx(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/project.do?method=LookProject";
    document.getElementById("fom").submit();
}

function linknxet(){
	var page=document.getElementById("page").value;
	page++;
    document.getElementById("fom").action="bug.do?method=requestType&page="+page;
    document.getElementById("fom").submit();
}
function linkreturn(){
	var page=document.getElementById("page").value;
	page--;
    document.getElementById("fom").action="bug.do?method=requestType&page="+page;
    document.getElementById("fom").submit();
}
function linkstart(){
    document.getElementById("fom").action="bug.do?method=requestType&page=1";
    document.getElementById("fom").submit();
}
function linkover(){
	var MaxPage=document.getElementById("MaxPage").value;
    document.getElementById("fom").action="bug.do?method=requestType&page="+MaxPage;
    document.getElementById("fom").submit();
}
function sort(type){
    document.getElementById("fom").action="bug.do?method=sortBug&Type="+type;
    document.getElementById("fom").submit();
}
</SCRIPT>

<body>
<form id="fom" method="post" action="bug.do?method=findByType">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="<%=request.getContextPath()%>/page/images/nav04.gif">
			<input type="hidden" name="Model" id="Model" value="${Model}" />
			<input type="hidden" name="page" id="page" value="${page}" />
			<input type="hidden" name="Maxpage" id="Maxpage" value="${MaxPage}" />
			<input type="hidden" name="Type" value="${Type}"/>
		   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
		    <tr>
			  <td width="15%" align="center" height="60px"  valign="middle">
				
			    Bug 编号:
					<c:if test="${BugIDs!=null}">
					<input name="bugid" type="text" id="bugid" size="12"  value="${BugIDs}"/>
					</c:if>
			      	<c:if test="${BugIDs==null}">
					<input name="bugid" type="text" id="bugid" size="12"  "/>
					</c:if>
				<input  type="hidden"/>
					<br />
				<input name="Submit4" type="button" class="right-button02" onclick="link4()" value="查 询" />
				
				</td>
				<td width="25%" align="right" valign="middle" >| 测试人员：
				  <select name="Tester" size="1">
                    <option>---请选择---</option>
					<c:if test="${Testers!=null}">
					<c:forEach items="${Tester}" var="test">
						<c:if test="${test.userid==Testers}">
							<option value="${test.userid}" selected="selected">${test.name}</option>
						</c:if>
                    	<c:if test="${test.userid!=Testers}">
							<option value="${test.userid}">${test.name}</option>
						</c:if>
					</c:forEach>
					</c:if>
					<c:if test="${Testers==null}">
					<c:forEach items="${Tester}" var="test">
						<option value="${test.userid}">${test.name}</option>
					</c:forEach>
					</c:if>
                  </select>
				  <br />
				 | 责任人员：
				  <select name="Principal" size="1">
                    <option>---请选择---</option>
					<c:forEach items="${Principal}" var="test">
						<c:if test="${test.userid==Principals}">
							<option value="${test.userid}" selected="selected">${test.name}</option>
						</c:if>
                    	<c:if test="${test.userid!=Principals}">
							<option value="${test.userid}">${test.name}</option>
						</c:if>
					</c:forEach>
                  </select>
			    </td>

				<td width="%40" align="center" valign="middle">
				 创建日期：
				<c:if test="${StarDates!=null}">

				<input name="StarDate" type="text" size="14" style="font-size: 8pt;" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})" value="${StarDates }"/>
				</c:if>
			    <c:if test="${StarDates==null}">
				<input name="StarDate" type="text" size="14" style="font-size: 8pt;" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})" value="2000-01-01 12:00:00" />
				</c:if>
                    <span class="newfont06">至</span>
				<c:if test="${OverDates!=null}">
				<input name="OverDate" type="text" size="14" style="font-size: 8pt;" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})" value="${OverDates }"/>
				</c:if>
			    <c:if test="${OverDates==null}">
				<input name="OverDate" type="text" size="14" style="font-size: 8pt;" onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})" value="2000-01-01 12:00:00"/>
				</c:if>
					<br />
				Bug 标题：
				<input name="Summary" type="text" size="30" />
				</td>
				<td width="10%"  align="center" valign="middle">
									Bug 状态:

					<select name="Status" size="1">
								      	<option>---请选择--- </option>
						<c:forEach items="${Status}" var="sta">
							<c:if test="${sta.itemvalue==Statuss}">
								<option value="${sta.itemvalue}" selected="selected">${sta.itemname}</option>
							</c:if>
							<c:if test="${sta.itemvalue!=Statuss}">
								<option value="${sta.itemvalue}">${sta.itemname}</option>
							</c:if>
						</c:forEach>
					</select>


				</td>
			    <td width="10%" align="right" valign="middle">
			     <input name="Submit3" type="submit" class="right-button07" value="高级搜索" />
			   </td>
</tr>
          </table>
</td>
        </tr>
    </table></td></tr>
  <tr>
    <td><table id="subtree1" style="DISPLAY: " width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
          	 <tr>
               <td height="20"><span class="newfont07"></span>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'showBug'}">
	              <input name="Submit1" type="button" class="right-button08" value="BUG管理" onclick="link();"/>
						</c:if>
				</c:forEach>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'showReport'}">
				 			 <input name="Submit2" type="button" class="right-button08" value="统计报表"onclick="link1();" />
				  		</c:if>
				</c:forEach>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'ProjectView'}">
							<input name="Submit3" type="button" class="right-button08" value="项目查看" onclick="linkp();"/>
	              		</c:if>
	              </c:forEach>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'manageProject'}">
								<input name="Submit3" type="button" class="right-button08" value="项目管理" onclick="link2();"/>
	              		</c:if>
	              </c:forEach>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'addBug'}">
								<input name="Submit4" type="button" class="right-button08" value="新建BUG" onclick="link3();"/>
				  		</c:if>
				  </c:forEach>
				<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'bugEdition'}">
								<input name="Submit4" type="button" class="right-button08" value="测试版本" onclick="link5();"/>
						</c:if>
				</c:forEach>
					</td>

          	 </tr>
              <tr>
                <td height="40" class="font42">
				<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#464646" class="newfont03">
				 <tr class="CTitle" >
                    	<td height="22" colspan="12" align="center" style="font-size:12px"><span class="STYLE1">任务详细列表</span></td>
                  </tr>
                  <tr bgcolor="#EEEEEE">
				    <td width="4%" align="center" height="30">BugID</td>
                    <td width="15%">Bug标题</td>
					<td width="23%">描述</td>
					<td width="7%">所在模块</td>
					<td width="5%">测试人员</td>
					<td width="5%">责任人</td>
					<td width="8%"><a onclick="sort(0)" href="#">创建时间</a></td>
					<td width="5%"><a onclick="sort(1)" href="#">Bug状态</a></td>
                    <td width="5%"><a onclick="sort(2)" href="#">优先级</a></td>
					<td width="5%"><a onclick="sort(3)" href="#">反馈类型</a></td>
					<td width="8%"><a>最后更新时间</a></td>
					<td width="10%">操作</td>
                  </tr>
				<c:forEach items="${buglist}" var="list" >
                  <tr bgcolor="#FFFFFF">
				    <td height="20">${list.bugid}</td>
				    <td >${list.summary}</td>
					<td>${list.detail}</td>
                    <td>${list.projectmodel.projectmodelname}</td>
                    <td>${list.userinfoByTester.name}</td>
					<td>${list.userinfoByPrincipal.name}</td>
					<td>${list.createtimeStr}</td>
					<td>${list.statusstr}</td>
					<td>${list.prioritystr}</td>
                    <td>${list.lastreplystr}</td>
					<td>${list.lastmodifytime}</td>
                    <td align="center"><a href="bug.do?method=findByID&id=${list.bugid}">查看</a>
					<!-- 如果是测试人或者是项目经理 -->
					<c:if test="${list.testerif==true}">
						<a href="bug.do?method=delBug&bugid=${list.bugid}&model=${list.projectmodel.projectmodelid}">| 删除</a>
					</c:if>
					<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'delBug'}">
						<a href="bug.do?method=delBug&bugid=${list.bugid}&model=${list.projectmodel.projectmodelid}">| 删除</a>
						</c:if>
					</c:forEach>
					<c:if test="${list.testerif==true}">
					<a href="bug.do?method=SetBugUI&bugid=${list.bugid}&model=${list.projectmodel.projectmodelid}">| 修改</a>
					</c:if>
					</td>
                  </tr>
				</c:forEach>
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

			
                <td width="50%">共 <span class="right-text09">${MaxPage}</span> 页 | 第 <span class="right-text09">${page}</span> 页</td>
            <td width="49%" align="right">
            <c:if test="${page<=1}">
			[ 首页 | 上一页
			</c:if>
			<c:if test="${page>1}">
   			[ <a href="#" onclick="linkstart()" class="right-font08">首页</a> 
			| <a href="#" onclick="linkreturn()" class="right-font08">上一页</a> 
			</c:if>
			<c:if test="${page<MaxPage}">
			| <a href="#" onclick="linknxet()" class="right-font08">下一页</a> 
			| <a href="#" onclick="linkover()"  class="right-font08">末页</a>
            </c:if>
			<c:if test="${page>=MaxPage}">
			| 下一页 | 末页 
			</c:if>
			] 转至：
			</td>
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
</td></tr></table></form>
</body>
</html>
