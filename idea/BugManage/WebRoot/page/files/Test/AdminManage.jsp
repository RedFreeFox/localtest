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

<link href="../../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">

</script>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
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

function link1(){
    document.getElementById("fom").action="AdminManage.jsp";
   document.getElementById("fom").submit();
}
function link2(){
    document.getElementById("fom").action="../../../user.do?method=FindAll";
   document.getElementById("fom").submit();
}
function link3(){
    document.getElementById("fom").action="AdminAddProject.jsp";
   document.getElementById("fom").submit();
}
function linkUser(){
    document.getElementById("fom").action="AdminManageProUser.jsp";
   document.getElementById("fom").submit();
}
function linkMOkuai(){
    document.getElementById("fom").action="AdminManageProMokuai.jsp";
   document.getElementById("fom").submit();
}

</SCRIPT>

<body>
<form name="fom" id="fom" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="30">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="62" background="../../images/nav04.gif">
            
		   <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		    <tr>
			  <td width="22"><img src="../../images/ico07.gif" width="20" height="18" /></td>
			  <td width="565">
			 项目名称： 
			   <input name="textfield" type="text" size="12" />
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   状态：
			   <label>
			 <input type="radio" name="radiobutton" value="radiobutton" />
			 </label>
			 	
			   全部
			   <label>
			   <input type="radio" name="radiobutton" value="radiobutton" />
			   未完成
			   <input type="radio" name="radiobutton" value="radiobutton" />
			   已完成
			   </label>
			   </td>
			   <td width="169" align="left">
			   <input name="Submit" type="button" class="right-button02" value="查 询" /></td>	
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
                    <td height="20" colspan="15" align="center" bgcolor="#EEEEEE"class="tablestyle_title"> <span class="STYLE1">Bug详细列表</span></td>
                    </tr>
                  <tr>
				    <td width="6%" align="center" bgcolor="#EEEEEE">序号</td>
					 <td width="20%" height="20" align="center" bgcolor="#EEEEEE">项目名称</td>
                     <td width="9%" align="center" bgcolor="#EEEEEE">项目状态</td>
                     <td width="20%" align="center" bgcolor="#EEEEEE">开始时间</td>
					 <td width="16%" align="center" bgcolor="#EEEEEE">预计完成时间</td>
					<td width="15%" align="center" bgcolor="#EEEEEE">设置</td>
                    <td width="14%" align="center" bgcolor="#EEEEEE">管理</td>
                  </tr>
                  <tr>
				    <td bgcolor="#FFFFFF">1</td>
				    <td height="20" bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">1235</a></td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">程序员</td>
                    <td bgcolor="#FFFFFF">实习</td>
                    <td bgcolor="#FFFFFF">
					
					<a href="AdminAddUser.htm">
<input title="设置项目成员" type="image" src="../../images/ico24.gif"  onclick="linkUser()"; />
<input name="image" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                  <tr>
				   	    <td bgcolor="#FFFFFF">2</td>
				   	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">程序员</td>
                    <td bgcolor="#FFFFFF">实习</td>
                    <td bgcolor="#FFFFFF">
					<a href="AdminAddUser.htm">
                      <input name="image2" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image2" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                  <tr>
				    	    <td bgcolor="#FFFFFF">&nbsp;</td>
				    	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">程序员</td>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><a href="AdminAddUser.htm">
                      <input name="image3" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image3" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                  <tr>
				    	    <td bgcolor="#FFFFFF">&nbsp;</td>
				    	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">程序员</td>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><a href="AdminAddUser.htm">
                      <input name="image4" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image4" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                  <tr>
				    	    <td bgcolor="#FFFFFF">&nbsp;</td>
				    	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">aaaa</td>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><a href="AdminAddUser.htm">
                      <input name="image5" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image5" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                  <tr>
				    	    <td bgcolor="#FFFFFF">&nbsp;</td>
				    	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">aaaa</td>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><a href="AdminAddUser.htm">
                      <input name="image6" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image6" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                  <tr>
				    	    <td bgcolor="#FFFFFF">&nbsp;</td>
				    	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">aaaa</td>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><a href="AdminAddUser.htm">
                      <input name="image7" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image7" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                  <tr>
				   	    <td bgcolor="#FFFFFF">&nbsp;</td>
				   	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">aaaa</td>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><a href="AdminAddUser.htm">
                      <input name="image8" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image8" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|<a href="../yuangongxiangmu.html"> 删除</a></td>
                  </tr>
                  <tr>
				   	    <td bgcolor="#FFFFFF">&nbsp;</td>
				   	    <td height="20" bgcolor="#FFFFFF">1235</td>
                    <td bgcolor="#FFFFFF"><a href="../listyuangongmingxi.html">张三</a></td>
                    <td bgcolor="#FFFFFF">aaaa</td>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                    <td bgcolor="#FFFFFF"><a href="AdminAddUser.htm">
                      <input name="image9" type="image" title="设置项目成员"  onclick="linkUser()" src="../../images/ico24.gif"; />
                      <input name="image9" type="image" title="设置项目模块"  onclick="linkMOkuai()" src="../../images/button08.gif" /></td>
                    <td bgcolor="#FFFFFF"><a href="AdminUpdatePro.jsp">编辑</a>&nbsp;|&nbsp;<a href="../yuangongxiangmu.html">删除</a></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="6"><img src="../../images/spacer.gif" width="1" height="1" /></td>
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
