<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
  <script type='text/javascript' src='/BugManage/dwr/interface/showPerson.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/engine.js'></script>
  <script type='text/javascript' src='/BugManage/dwr/util.js'></script>


<script type="text/javascript">
			function show(num){
				var proID=document.getElementById("proid").value;
				var type=num;
				document.getElementById("type").value=num;
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
<script type="text/javascript">
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
    document.getElementById("fom").action="<%=request.getContextPath()%>/project.do?method=FindProjectInfo";
    document.getElementById("fom").submit();
}

</script>
</head>
<body>
<form action="" method="post" enctype="multipart/form-data" name="fom" id="fom">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="<%=request.getContextPath() %>/page/images/nav04.gif"><span class="newfont07"><div align="center">
<input name="Submit" type="button" class="right-button08" value="项目管理" onclick="link1();" />
<input name="Submit" type="button" class="right-button08" value="用户管理" onclick="link2();" />
<input name="Submit2"type="button" value="返回" class="right-button08"onclick="window.history.go(-1);"/>
          </div></span>
    </table></td></tr>
  <tr>
    <td><div class="MainDiv">
<table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
    <td class="CPanel">
		<input type="hidden" name="proID" id="proid" value="${projectid}" />
		<input type="hidden" name="type" id="type"  />
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
           <TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>项目人员设置</legend>
					  <table width="60%" border="0" cellpadding="2" cellspacing="1" style="width:100%">
                        <tr>
                          <td colspan="3" align="right" nowrap>项目经理:
                            <label></label>                            <label>
                            <input name="textfield6" type="text" value="${p0}" size="40" />
                            </label></td>
							<td align="left" nowrap>
							<a href="#" onclick="show(0)">
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
							<a href="#" onclick="show(1)">
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
							<a href="#" onclick="show(2)">
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
							<a href="#" onclick="show(3)">
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
							<a href="#" onclick="addPerson()" style="background-image:url(page/images/dhbutton05.gif);width:81px;height:40px ">==>>></a>
                          <br />
                          <br />
							<a href="#" onclick="delPerson()" style="background-image:url(page/images/dhbutton05.gif);width:81px;height:40px "><<<==</a>
                          </label></td>
                          <td width="37%" align="left" nowrap><label>
                            <select id="showPer" name="showPer" multiple="multiple" style="width:150px;height: 300px;">

							</select>
                          </label></td>
                        </tr>
						                        <tr>
                          <td align="center" nowrap><label></label>                            <label></label>                            <label></label></td>
                          <td align="center" nowrap>&nbsp;</td>
                          <td align="center" nowrap>
<input type="button" name="Submit8" class="right-button08" value="设置" onclick="submits()" />

</td>
                          <td align="center" nowrap>&nbsp;</td>
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
