<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<script language="JavaScript" type="text/javascript">

function link(){
    document.getElementById("fom").action="register.jsp";
   document.getElementById("fom").submit();
}



</script>




<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目管理系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="page/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE2 {color: #3366FF}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="147" background="page/images/top02.gif"><img src="page/images/top03.jpg" width="776" height="147" /></td>
  </tr>
</table>
<table width="562" border="0" align="center" cellpadding="0" cellspacing="0" class="right-table03">
  <tr>
    <td width="221"><table width="95%" border="0" cellpadding="0" cellspacing="0" class="login-text01">
      
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="login-text01">
          <tr>
            <td align="center"><img src="page/images/ico13.gif" width="107" height="97" /></a></td>
          </tr>
          <tr>
            <td height="40" align="center"><label><a href="<%=request.getContextPath() %>/page/files/Test/register.jsp" class="STYLE2">点此注册</a></label></td>
          </tr>
          
        </table>
          <p class="newfont08">&nbsp;</p></td>
        <td><img src="page/images/line01.gif" width="5" height="292" /></td>
      </tr>
    </table></td>
    <td>
		<html:form action="/user" method="post">
		<html:hidden property="method" value="login"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="31%" height="35" class="login-text02">用户名称：<br /></td>
        <td width="69%"><html:text property="user.name"  size="22" /></td>
      </tr>
      <tr>
        <td height="35" class="login-text02">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：<br /></td>
        <td><html:password property="user.password"  size="24"/></td>
      </tr>
      <tr>
        <th colspan="2"><html:submit styleClass="right-button01">确认登录</html:submit>
			<html:reset styleClass="right-button02">重置</html:reset>
        </th>
      </tr>
    </table>
	</html:form>
	</td>
  </tr>
</table>
</body>
</html>
