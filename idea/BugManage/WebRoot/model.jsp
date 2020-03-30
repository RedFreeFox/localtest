<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.bugManage.entity.Projectmodel"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'model.jsp' starting page</title>
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
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
 		<script type='text/javascript' src='/BugManage/dwr/interface/showPerson.js'></script>
  		<script type='text/javascript' src='/BugManage/dwr/engine.js'></script>
  		<script type='text/javascript' src='/BugManage/dwr/util.js'></script>
		<script type="text/javascript">
	function click(Model) {
		//获取模块对应的父模块以及名字
		//将值反映到界面
	}
</script>

	</head>

	<body>

		<%!private List<Projectmodel> model=new ArrayList(); %>
		<%!private String Str=""; 
	%>
		<%
		model=(List<Projectmodel>)request.getAttribute("model");
		Str="";
		System.out.println(model.size());
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
						Str+="<a onclick='show("+list.get(i).getProjectmodelid()+")'>";		
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
	alert("this"+date.model.projectmodelname);
	alert("father"+date.fathermodel.projectmodelname);
	}
</script>
	</body>
</html>
