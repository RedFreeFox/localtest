<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.bugManage.entity.Projectmodel"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>项目管理系统 by www.mycodes.net</title>
		<style type="text/css">

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(../../images/left.gif);
}

</style>
		<script type="text/javascript" language=javascript
			src="<%=request.getContextPath()%>/page/js/xtree.js">
</script>
		<link href="<%=request.getContextPath()%>/page/css/xtree.css"
			rel="stylesheet" type="text/css">
		<script type="text/javascript">
	</script>
		<link href="<%=request.getContextPath()%>/page/css/css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<SCRIPT language=JavaScript>
function tupian(idt){
    var nametu="xiaotu"+idt;
    var tp = document.getElementById(nametu);
    tp.src="../../images/ico05.gif";//图片ico04为白色的正方形
	
	for(var i=1;i<30;i++)
	{
	  
	  var nametu2="xiaotu"+i;
	  if(i!=idt*1)
	  {
	    var tp2=document.getElementById('xiaotu'+i);
		if(tp2!=undefined)
	    {tp2.src="../../images/ico06.gif";}//图片ico06为蓝色的正方形
	  }
	}
}

function list(idstr){
	var name1="subtree"+idstr;
	var name2="img"+idstr;
	var objectobj=document.all(name1);
	var imgobj=document.all(name2);
	
	
	//alert(imgobj);
	
	if(objectobj.style.display=="none"){
		for(i=1;i<10;i++){
			var name3="img"+i;
			var name="subtree"+i;
			var o=document.all(name);
			if(o!=undefined){
				o.style.display="none";
				var image=document.all(name3);
				//alert(image);
				image.src="../../images/ico04.gif";
			}
		}
		objectobj.style.display="";
		imgobj.src="../../images/ico03.gif";
	}
	else{
		objectobj.style.display="none";
		imgobj.src="../../images/ico04.gif";
	}
}

</SCRIPT>


<body>
<table width="198" border="0" cellpadding="0" cellspacing="0" class="left-table01">
  <tr>
    <TD>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			<td width="207" height="55" background="<%=request.getContextPath() %>/page/images/nav01.gif">
				<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="25%" rowspan="2"><img src="<%=request.getContextPath() %>/page/images/ico02.gif" width="35" height="35" /></td>
					<td width="75%" height="22" class="left-font01">您好，<span class="left-font02">${sessionScope.user.name }</span></td>
				  </tr>
				  <tr>
					<td height="22" class="left-font01">
						[&nbsp;<a href="<%=request.getContextPath() %>/user.do?method=ExitLogin" target="_top" class="left-font01">退出</a>&nbsp;]</td>
				  </tr>
				</table>
			</td>
		  </tr>
		</table>
		
		
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
						class="left-table03">

					<!-- 项目展示开始    -->
					<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'showProject'}">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
								class="left-table03">
								<tr>
									<td height="29">

										<table width="85%" border="0" align="center" cellpadding="0"
											cellspacing="0">
											<tr>
												<td width="8%">
													<img name="img8" id="img8" src="../../images/ico04.gif"
														width="8" height="11" />
												</td>
												<td width="92%">
													<a target="mainFrame" class="left-font03"
													href="javascript:"	onClick="list('10');">项目展示</a>
												</td>
											</tr>
										</table>
							</TABLE>
						</c:if>
					</c:forEach>
						<tr><td>
					<table id="subtree10" style="DISPLAY: none" width="80%" border="0"
						align="center" cellpadding="0" cellspacing="0"
						class="left-table02">
						<tr><td>
						<script type="text/javascript">
	var tree=new WebFXTree("项目管理");//主目录
	tree.icon='../../images/ico06.gif';
	tree.openIcon='../../images/ico05.gif';
	<%!private List<Projectmodel> model = new ArrayList();%>
	<%!private String Str = "";%>
	<%model = (List<Projectmodel>) session.getAttribute("models");
			Str = "";
			searModel(null, model);%>

	
	<%!public void searModel(Long ModelId, List<Projectmodel> list) {
		if (ModelId == null) {
			//将当前添加进Str
			list.size();
			for (int i = 0; i < list.size(); i++) {
				Str += ("var ID" + list.get(i).getProjectmodelid()
						+ "=new WebFXTreeItem('"
						+ list.get(i).getProjectmodelname() + "');");
				Str += "ID" + list.get(i).getProjectmodelid()
						+ ".action='../../../bug.do?method=findByModel&Model="
						+ list.get(i).getProjectmodelid() + "';";
				Str += "ID" + list.get(i).getProjectmodelid()
						+ ".icon='../../images/ico06.gif';";
				Str += "ID" + list.get(i).getProjectmodelid()
						+ ".openIcon='../../images/ico05.gif';";
				Str += "ID" + list.get(i).getProjectmodelid()
						+ ".target='mainFrame';";
				if (list.get(i).getProjectmodels().size() != 0) {
					List<Projectmodel> li = new ArrayList(list.get(i)
							.getProjectmodels());
					Collections.sort(li, new Comparator() {
						public int compare(Object a, Object b) {
							Long i = ((Projectmodel) a).getProjectmodelid();
							Long j = ((Projectmodel) b).getProjectmodelid();
							if (j > i) {
								return 1;
							} else if (j < i) {
								return -1;
							} else {
								return 0;
							}
						}
					});
					for (Projectmodel p : li) {
						searModel(p.getProjectmodelid(), li);
					}
					Str += ("tree.add(ID" + list.get(i).getProjectmodelid() + ");");
				}
			}
		} else {
			//将当前添加进Str
			for (int i = 0; i < list.size(); i++) {
				if (ModelId == list.get(i).getProjectmodelid()) {
					Str += ("var ID" + list.get(i).getProjectmodelid()
							+ "=new WebFXTreeItem('"
							+ list.get(i).getProjectmodelname() + "');");
					Str += "ID"
							+ list.get(i).getProjectmodelid()
							+ ".action='../../../bug.do?method=findByModel&Model="
							+ list.get(i).getProjectmodelid() + "';";
					Str += "ID" + list.get(i).getProjectmodelid()
							+ ".icon='../../images/ico06.gif';";
					Str += "ID" + list.get(i).getProjectmodelid()
							+ ".openIcon='../../images/ico05.gif';";
					Str += "ID" + list.get(i).getProjectmodelid()
							+ ".target='mainFrame';";
					if (list.get(i).getProjectmodels().size() != 0) {
						List<Projectmodel> li = new ArrayList(list.get(i)
								.getProjectmodels());
						Collections.sort(li, new Comparator() {
							public int compare(Object a, Object b) {
								Long i = ((Projectmodel) a).getProjectmodelid();
								Long j = ((Projectmodel) b).getProjectmodelid();
								if (j > i) {
									return 1;
								} else if (j < i) {
									return -1;
								} else {
									return 0;
								}
							}
						});
						for (Projectmodel p : li) {
							searModel(p.getProjectmodelid(), li);
						}
					}
					if (list.get(i).getProjectmodel() != null) {
						Str += ("ID"
								+ list.get(i).getProjectmodel()
										.getProjectmodelid() + ".add(ID"
								+ list.get(i).getProjectmodelid() + ");");
					}
				}
			}
		}
	}%>
	<%out.print(Str);%>
	document.write(tree);
</script></td></tr>
      </table>
		<!--  项目展示结束    -->

					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
						class="left-table03">
					<!--  管理员开始    -->
					<c:forEach items="${sessionScope.role}" var="ro">
						<c:if test="${ro.param eq 'manageSystem'}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="left-table03">
								<tr>
									<td height="29">
										<table width="85%" border="0" align="center" cellpadding="0"
											cellspacing="0">
											<tr>
												<td width="8%" height="12">
													<img name="img3" id="img3" src="../../images/ico04.gif"
														width="8" height="11" />
												</td>
												<td width="92%">
													<a href="javascript:" target="mainFrame"
														class="left-font03" onClick="list('3');">管理系统</a>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>

							<table id="subtree3" style="DISPLAY: none" width="80%" border="0"
								align="center" cellpadding="0" cellspacing="0"
								class="left-table02">
								<tr>
									<td width="9%" height="20">
										<img id="xiaotu8" src="<%=request.getContextPath() %>/page/images/ico06.gif" width="8"
											height="12" />
									</td>
									<td width="91%">
										<a href="<%=request.getContextPath() %>/project.do?method=FindProjectInfo" target="mainFrame"
											class="left-font03" onClick="tupian('8');">项目管理</a>
									</td>
								</tr>
								<tr>
									<td width="9%" height="20">
										<img id="xiaotu9" src="<%=request.getContextPath() %>/page/images/ico06.gif" width="8"
											height="12" />
									</td>
									<td width="91%">
										<a href="<%=request.getContextPath() %>/user.do?method=FindAll" target="mainFrame"
											class="left-font03" onClick="tupian('9');">用户管理</a>
									</td>
								</tr>
							</table>
						</c:if>
					</c:forEach>
					<!--  管理员结束    -->
</TABLE>





	  
	  

					<!--个人信息管理开始-->

					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
						class="left-table03">
						<tr>
							<td height="29">
								<table width="85%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="8%">
											<img name="img9" id="img9" src="../../images/ico04.gif"
												width="8" height="11" />
										</td>
										<td width="92%">
											<a href="javascript:" target="mainFrame" class="left-font03"
												onClick="list('9');">个人设置</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</TABLE>

					<table id="subtree9" style="DISPLAY: none" width="80%" border="0"
						align="center" cellpadding="0" cellspacing="0"
						class="left-table02">
						<tr>
							<td width="9%" height="22">
								<img id="xiaotu25" src="../../images/ico06.gif" width="8"
									height="12" />
							</td>
							<td width="91%">
								<a href="UpdateUser.jsp" target="mainFrame" class="left-font03"
									onClick="tupian('25');">修改个人设置</a>
							</td>
						</tr>
					</table>
					<!--  个人信息结束    -->

		
    
	 <!-- 用户注册开始 -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="left-table03">
						<tr>
							<td height="29">
								<table width="85%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="8%">
											<img name="img5" id="img5" src="../../images/ico04.gif"
												width="8" height="11" />
										</td>
										<td width="92%">
											<a href="javascript:" target="mainFrame" class="left-font03"
												onClick="list('5');">退出系统</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

					<table id="subtree5" style="DISPLAY: none" width="80%" border="0"
						align="center" cellpadding="0" cellspacing="0"
						class="left-table02">
						<tr>
							<td width="9%" height="20">
								<img id="xiaotu13" src="../../images/ico06.gif" width="8"
									height="12" />
							</td>
							<td width="91%">
								<a
									href="<%=request.getContextPath()%>/user.do?method=ExitLogin"
									target="_top" class="left-font03" onClick="tupian('13');">确认退出</a>
							</td>
						</tr>
					</table>
					<!-- 用户注册结束-->

    </td>
  </tr>
  
</table></TD></tr></table>
</body>
</html>
