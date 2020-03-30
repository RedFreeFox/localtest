<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.bugManage.entity.Projectmodel"%>
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

</SCRIPT>

<body>
<form action="" method="post" name="fom" id="fom">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="../../images/nav04.gif"><span class="newfont07"><div align="center">
<input name="Submit" type="button" class="right-button08" value="项目管理"onclick="link1();"/>
<input name="Submit" type="button" class="right-button08" value="用户管理" onclick="link2();" />
<input name="Submit2"type="button" value="返回" class="right-button08"onclick="window.history.go(-1);"/>
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
					<input type="hidden" name="admin" value="admin"/>
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
				</fieldset>			</TD>
		</TR>
                		<TR>
			
		</TR>
		</TABLE>
	
	 </td>
  </tr>	
	  </TABLE>
</form>
</body>
</html>
