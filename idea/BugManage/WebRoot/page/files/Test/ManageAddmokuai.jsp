<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.bugManage.entity.Projectmodel"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script language="javascript" type="text/javascript" 
src="<%=request.getContextPath() %>/js/WdatePicker.js"></script>

<link href="<%=request.getContextPath()%>/page/css/xtree.css"
 rel="stylesheet" type="text/css"/>
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
		<style>
a {
	color: #000000;
	text-decoration: none;
}

a:hover {
	color: #ff0000;
	text-decoration: underline;
}

body {
	font-size: 12px;
}

.p {
	margin-left: 10px;
}

.ps {
	margin-left: 10px;
	display: none;
}

.pss {
	margin-left: 10px;
	display: block;
}

.t {
	cursor: pointer;
	background: url(images/ico080426_close.gif) no-repeat;
	line-height: 20px;
	padding-left: 20px;
	height: 20px;
}

.f {
	cursor: pointer;
	background: url(images/ico080426_open.gif) no-repeat;
	line-height: 20px;
	padding-left: 20px;
	height: 20px;
}

.b {
	cursor: pointer;
	background: url(images/ico080426_dot.gif) no-repeat;
	line-height: 20px;
	padding-left: 20px;
	height: 20px;
}

.fblod {
	font-weight: bold;
}

.padtb8 {
	padding: 8px 0;
}

.fontred {
	color: #f00;
}
</style>
  <script type='text/javascript' src='/BugManage/dwr/interface/showPerson.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/engine.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/util.js'></script>
<link href="<%=request.getContextPath() %>/page/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">

</script>
<link href="<%=request.getContextPath() %>/page/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language=javascript
			src="<%=request.getContextPath()%>/page/js/xtree.js">
</script>
		<link href="<%=request.getContextPath()%>/page/css/xtree.css"
			rel="stylesheet" type="text/css">


<style type="text/css">
.STYLE1 {font-size: 18px}
</style>
</head>
		<script language="javascript" type="text/javascript">
	function clicks(son, fa) {
		var ob = document.getElementById(son);
		if (ob.style.display == "block" || ob.style.display == "") {
			ob.style.display = "none";
			//更换son图片
			var ob2 = document.getElementById(fa);
			ob2.style.backgroundImage = "url(images/ico080426_open.gif)";
		} else {
			ob.style.display = "block";
			var ob2 = document.getElementById(fa);
			ob2.style.backgroundImage = "url(images/ico080426_close.gif)";
		}
	}
	function show(modelid) {
	showPerson.findModelIdToModels(modelid,callBackFun)
	}
	function callBackFun(date){
	var son=document.getElementById("sonmodel");
	var father=document.getElementById("fathermodel");
	son.value=date.model.projectmodelid;
	father.value=date.fathermodel.projectmodelid;
	document.getElementById("modifyfather").value=date.fathermodel.projectmodelname;
	document.getElementById("modifymodel").value=date.model.projectmodelname;
	document.getElementById("addfather").value=date.fathermodel.projectmodelname;
	}
</script>
<script type="text/javascript">
			function show2(num){
				var proID=$("proid").value;
				var type=num;
				$("type").value=num;
				showPerson.findPerson(proID,type,callBackFunas);
			}
			function callBackFunas(data){
			
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
				showPerson.addPerson(userid,PersonAllList,PersonList,callBackFunas);
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
				showPerson.delPerson(userid,PersonAllList,PersonList,callBackFunas);
			}
			function submits2(){
			//proid,string[],type
				var proid=$("proid").value;
				var type=$("type").value;
				var Per=$("showPer");
				var PersonList=new Array();
				for(var j=0;j<Per.length;j++){
					PersonList[j]=Per.options[j].value;
				}
				showPerson.gobatchProjectUser(proid,type,PersonList,callBackFuns);
			}
			function callBackFuns(date){
				alert(date);
			}
	</script>
<SCRIPT language=JavaScript>
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

function modifyModel(){
    var father = document.getElementById("modifyfather").value;
    var model = document.getElementById("modifymodel").value;
    if(father==null){
    	alert("未选择模块！");
    }else {
	document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=ModifyProjectModel";
    document.getElementById("fom").submit();
	}
}
function deleteModel(){
    var father = document.getElementById("modifyfather").value;
    var model = document.getElementById("modifymodel").value;
	if(father==null){
    	alert("未选择模块！");
    }
    document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=DelProjectModel";
    document.getElementById("fom").submit();
    
}
function addModel(){
    var father = document.getElementById("addfather").value;
    var model = document.getElementById("addmodel").value;
	if(father==null){
    	alert("未选择模块！");
    }
    document.getElementById("fom").action="<%=request.getContextPath()%>/model.do?method=AddProjectModel";
    document.getElementById("fom").submit();
}
</script>
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
<script language="javascript" type="text/javascript" 
src="<%=request.getContextPath() %>/page/js/WdatePicker.js">
</script>
<link href="<%=request.getContextPath() %>/page/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.STYLE1 {font-size: 18px}
</style>
</head>
<SCRIPT language=JavaScript>
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

function checkChanage(){
	var rad = document.getElementById("rad").checked;
	var rad = document.getElementById("rid").checked;
	if(rad){
		alert("rad!");
	}else if(rid){
		alert("rid!");
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
function link3(){
    document.getElementById("fom").action="<%=request.getContextPath()%>/project.do?method=ModifyProjectInfo";
    document.getElementById("fom").submit();
}

function link3(){
    document.getElementById("fom").action="TestBugView.jsp";
    document.getElementById("fom").submit();
}


</SCRIPT>

<body>
<form action="" method="post" enctype="multipart/form-data" name="fom" id="fom">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="<%=request.getContextPath() %>/page/images/nav04.gif"><span class="newfont07"><div align="center">
            <input name="Submit1" type="button" class="right-button08" value="Bug管理" onclick="link();"/>
			<input name="Submit2" type="button" class="right-button08" value="统计报表"onclick="link1();" />
            <input name="Submit3" type="button" class="right-button08" value="项目管理" onclick="link2();"/>
          </div></span>
    </table></td></tr>
  <tr>
    <td><div class="MainDiv">
<table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
    <td class="CPanel">
		
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>项目基本信息</legend>
						<input type="hidden" name="project.projectid" id="proid" value="${project.projectid }" />
					  <table width="78%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  					 <tr>
					 <td width="5%" align="right" nowrap><span class="red">*</span> 开始时间:</td>
					 <td width="13%" align="left" nowrap><label>
					   <input type="text" name="startTime" id="start" value="${project.starttime }" 
onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})"/>
					 </label></td>
					 <td width="6%" align="right" nowrap><span class="red">*</span> 预计完成时间:</td>
					 <td colspan="2" align="left" nowrap><label>
					   <input type="text" name="endTime" value="${project.forefinishtime }" 
onfocus="WdatePicker({readOnly:true,skin:'whyGreen'})"/>
					 </label></td>
					 <td width="14%" align="left" nowrap>项目状态:

						<c:if test="${project.status=='2'}">
                       <input type="radio" name="project.status" id="rad"  value="2" checked="checked"/>
                         	未完成
                       <input type="radio" name="project.status" id="rad"  value="3" />
                        	已完成
						</c:if>
						<c:if test="${project.status=='3'}">
                       <input type="radio" name="project.status" id="rad"  value="2" />
                         	未完成
                       <input type="radio" name="project.status" id="rad"  value="3" checked="checked"/>
                        	已完成
						</c:if>
						</td>
  					    <td width="47%" align="left" nowrap><label>
  					      <input type="submit" name="Submit2" value="提交" onclick="link3()"/>
  					    </label></td>
  					    </tr>
		              </table>
				 <br />
				</fieldset>			</TD>
		</TR>
		</TABLE>
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td>
		<fieldset style="height:100%;">
				<legend>项目模块设置</legend>
					  <table width="78%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  					 					 <tr>
					 <td width="50%" rowspan="7" align="center" nowrap>
		<%!private List<Projectmodel> model=new ArrayList(); %>
		<%!private String Str=""; 
	%>
		<%
		model=null;
		model=(List<Projectmodel>)request.getAttribute("model");
		Str="";
		Str+="<div class='padtb8'>";
		searModel(null,model);
		Str+="</div>";
	%>
		<a onclick="click()"></a>

		<%! public void searModel(Long ModelId,List<Projectmodel> list){
		if(ModelId==null){
			//将当前添加进Str
			for(int i=0;i<list.size();i++){
				if(list.get(i).getProjectmodels().size()!=0){
						//将子模块转换为list并排序
						List<Projectmodel> li=new ArrayList(list.get(i).getProjectmodels());
						Collections.sort(li,new Comparator(){public int compare(Object a ,Object b){
							Long i=((Projectmodel)a).getProjectmodelid();
							Long j=((Projectmodel)b).getProjectmodelid();
							if(j>i){
								return 1;
							}else if(j<i){
								return -1;
							}else{
								return 0;
							}
						}});
						//存在子模块的时候
						Str+="<div class='f fblod' id='id"+list.get(i).getProjectmodelid()+"' onclick=\"clicks('id"+list.get(i).getProjectmodelid()+"s','id"+list.get(i).getProjectmodelid()+"')\" style='background: url(images/ico080426_close.gif) no-repeat'>";
						Str+="<a >";		
						Str+=list.get(i).getProjectmodelname();
						Str+="</a>";
						Str+="</div>";
						//生成子模块div
						Str+="<div class='ps' id='id"+list.get(i).getProjectmodelid()+"s' style='display: block'>";
						for(Projectmodel p:li){
							searModel(p.getProjectmodelid(),li);
						}
						Str+="</div>";
				}else{
				Str+="<div class='f fblod' id='id"+list.get(i).getProjectmodelid()+"'  ";
				}

			}
		}else{
				//将当前添加进Str
				for(int i=0;i<list.size();i++){
					if(ModelId==list.get(i).getProjectmodelid()){
						if(list.get(i).getProjectmodels().size()!=0){
							List<Projectmodel> li=new ArrayList(list.get(i).getProjectmodels());
							Collections.sort(li,new Comparator(){public int compare(Object a ,Object b){
								Long i=((Projectmodel)a).getProjectmodelid();
								Long j=((Projectmodel)b).getProjectmodelid();
								if(j>i){
									return 1;
								}else if(j<i){
									return -1;
								}else{
									return 0;
								}
							}});
							//存在子模块的时候
							Str+="<div class='f fblod' id='id"+list.get(i).getProjectmodelid()+"' onclick=\"clicks('id"+list.get(i).getProjectmodelid()+"s','id"+list.get(i).getProjectmodelid()+"')\""+
							 "style='background: url(images/ico080426_close.gif) no-repeat'>";
							Str+="<a onclick=\"show("+list.get(i).getProjectmodelid()+")\">";		
							Str+=list.get(i).getProjectmodelname();
							Str+="</a>";
							Str+="</div>";
							//生成子模块div
							Str+="<div class='ps' id='id"+list.get(i).getProjectmodelid()+"s' style='display: block'>";
							for(Projectmodel p:li){
								searModel(p.getProjectmodelid(),li);
							}
							Str+="</div>";
							
						}else{
						Str+="<div  id='id"+list.get(i).getProjectmodelid()+"' >";
						Str+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='show("+list.get(i).getProjectmodelid()+")'>";		
						Str+=list.get(i).getProjectmodelname();
						Str+="</a>";
						Str+="</div>";
						}
					}
				}
				
		}
		} 
	%>
		<%
	out.print(Str); %>

					</td>
					 <td width="23%" align="right" nowrap><label>
					<!-- 隐藏表单域，父模块编号和模块编号 -->
					<input  type="hidden" id="fathermodel" name="fathermodel"/>
					<input  type="hidden" id="sonmodel" name="sonmodel"/>
					<input  type="hidden"  name="projectid" value="${projectid }"/>

					</label>
					   <label>父模块名称：<br />
					 </label></td>
					 <td width="27%" height="18" colspan="-2" align="left" nowrap><label>

					   <input type="text" name="projectmodelname" id="modifyfather" readonly="readonly"/>

					 </label></td>
 					    </tr>
					  					 					 <tr>
					  					 					   <td align="right" nowrap>模块名称：</td>
 					                                           <td width="27%" height="19" colspan="-2" align="left" nowrap><label>
 					                                             <input type="text" name="model.projectmodelname" id="modifymodel"/>
 					                                           </label></td>
 					    </tr>
					  					 					 <tr>
					  					 					   <td align="right" nowrap><label>
					  					 					     <input type="submit" name="Submit4" class="right-button08" value="修改模块" onclick="modifyModel()" />
					  					 					   </label></td>
					  					 					   <td colspan="-2" align="left" nowrap><label>
					  					 					     <input type="submit" name="Submit5" class="right-button08" value="删除模块" onclick="deleteModel()"/>
					  					 					   </label></td>
 					    </tr>
						
					  					 					 <tr>
					  					 					   <td align="right" nowrap>父模块名称：</td>
 					                                           <td width="27%" height="19" colspan="-2" align="left" nowrap><label>
 					                                             <input type="text" name="fathermodelname" id="addfather"  readonly="readonly"/>
 					                                           </label></td>
 					    </tr>
					 					 					 
					  <tr>
					    <td colspan="3">					    </td>
 					    <tr>
					 					    <td height="35"><div align="right">模块名称：</div></td>
 					                        <td colspan="-1"><label>
 					                          <input type="text" name="promodelname" id="addmodel" />
 					                        </label></td>
                        <tr>
                                            <td height="36" colspan="2"><label>
                                              <div align="center"> 
                                                <input type="submit" name="Submit6" value="新增模块" onclick="addModel()"/>
                                              </div>
                                            </label></td>
                        <tr>
					 					    <td colspan="2"></td>
                        <tr>
 					      <td colspan="3">&nbsp;</td>
			      </table>
					  				 <br />
				</fieldset>

		</td></tr>
		<tr><td>
			<fieldset style="height:100%;">
				<legend>项目人员设置</legend>
					  <table width="60%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
                        <tr>
                          <td colspan="3" align="right" nowrap>项目经理:
							<input type="hidden" id="type"/>
                            <label></label>                            <label>
                            <input name="textfield6" type="text" value="${p0}" size="40" />
                            </label></td>
							<td align="left" nowrap>
							<a  onclick="show2(0)">
							<img src="<%=request.getContextPath()%>/page/images/ico24.gif" alt="设置项目经理" border="none"/>
							</a>
							&nbsp;</td>
                          <td align="left" nowrap>&nbsp;</td>
                        </tr>
						                        <tr>
                          <td colspan="3" align="right" nowrap>开发人员
                            :
                            <input name="textfield62" type="text" size="40" value="${p1 }" />
                            <label></label>                            <label></label></td>
                          	<td align="left" nowrap>
							<a onclick="show2(1)">
							<img src="<%=request.getContextPath()%>/page/images/ico24.gif" alt="设置开发人员" border="none"/>
							</a>
							&nbsp;</td>
							</td>
                        </tr>
						                        <tr>
                          <td colspan="3" align="right" nowrap>测试人员:
                            <label></label>                            <label>
                            <input name="textfield63" type="text" size="40" value="${p2 }" />
                            </label></td>
                          	<td align="left" nowrap>
							<a  onclick="show2(2)">
							<img src="<%=request.getContextPath()%>/page/images/ico24.gif" alt="设置测试人员" border="none"/>
							</a>
							&nbsp;</td>
							</td>
                        </tr>
						                        <tr>
                          <td colspan="3" align="right" nowrap>浏览用户
                            <label></label>
                            :
                            <label></label>                            <label>
                            <input name="textfield64" type="text" size="40" value="${p3 }" />
                            </label></td>
                         	 <td align="left" nowrap>
							<a  onclick="show2(3)">
							<img src="<%=request.getContextPath()%>/page/images/ico24.gif" alt="设置浏览用户" border="none"/>
							</a>
							&nbsp;</td>
							</td>
                        </tr>
						                        <tr>
                          <td colspan="2" align="center" nowrap>待选人员</td>
                          <td colspan="2" align="center" nowrap><label></label>
                            <label>选择设置的角色</label></td>
                        </tr>
						                        <tr>
                          <td width="10%" align="center" nowrap><p>&nbsp;</p>
                            <p>人员设置</p>
                            <p>&nbsp;</p></td>
                          <td width="22%" align="right" nowrap><label>
							<select id="showAll" name="All" multiple="multiple" style="width:150px;height: 300px;">
							</select>
                          </label></td>
                          <td width="31%" align="center" nowrap><label></label><label>
							<a  onclick="addPerson()" style="background-image:url(page/images/dhbutton05.gif);width:81px;height:40px ">==>>></a>
                          <br />
                          <br />
							<a  onclick="delPerson()" style="background-image:url(page/images/dhbutton05.gif);width:81px;height:40px "><<<==</a>
                          </label></td>
                          <td width="37%" align="left" nowrap><label>
                            <select id="showPer" name="showPer" multiple="multiple" style="width:150px;height: 300px;">

							</select>
                          </label></td>
                        </tr>
						                        <tr>
                          <td align="center" nowrap><label></label>                            <label></label>                            <label></label></td>
                          <td align="center" nowrap>&nbsp;</td>
                          <td align="center" nowrap><input type="button" name="Submit8" class="right-button08" value="设置" onclick="submits2()" /></td>
                          <td align="center" nowrap>&nbsp;</td>
                        </tr>
                      </table>
					  <br />
				</fieldset>
		</td></tr>
		
		
		
		</TABLE>
	
	
	 </td>
  </tr>
  
  
		
		
		
		<TR>
			<TD colspan="2" align="center" height="50px">
			
		</TD>
		</TR>
		</TABLE>
	
	
	 </td>
  </tr>
		</TABLE>
	
	 </td>
  </tr>	
	  </TABLE>
</form>
</body>
</html>
