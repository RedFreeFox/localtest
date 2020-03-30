<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.bugManage.entity.Projectmodel"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>树形菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script type="text/javascript" language=javascript src="page/js/xtree.js">
</script>
<link href="page/css/xtree.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
	</script>


  </head>
  
  <body>

<script type="text/javascript">
	var tree=new WebFXTree("项目管理");//主目录
	<%!private List<Projectmodel> model=new ArrayList(); %>
	<%!private String Str=""; %>
	<%
		model=(List<Projectmodel>)request.getAttribute("model");
		Str="";
		searModel(null,model);
	%>

	
	<%! public void searModel(Long ModelId,List<Projectmodel> list){
		if(ModelId==null){
			//将当前添加进Str
			for(int i=0;i<list.size();i++){
					Str+=("var ID"+list.get(i).getProjectmodelid()+"=new WebFXTreeItem('"+list.get(i).getProjectmodelname()+"');");
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
						for(Projectmodel p:li){
							searModel(p.getProjectmodelid(),li);
						}
						Str+=("tree.add(ID"+list.get(i).getProjectmodelid()+");");
				}
			}
		}else{
				//将当前添加进Str
				for(int i=0;i<list.size();i++){
					if(ModelId==list.get(i).getProjectmodelid()){
						Str+=("var ID"+list.get(i).getProjectmodelid()+"=new WebFXTreeItem('"+list.get(i).getProjectmodelname()+"');");
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
							for(Projectmodel p:li){
								searModel(p.getProjectmodelid(),li);
							}
						}
						if(list.get(i).getProjectmodel()!=null){
							Str+=("ID"+list.get(i).getProjectmodel().getProjectmodelid()+".add(ID"+list.get(i).getProjectmodelid()+");");
						}
					}
				}
		}
		} 
	%>
	<%
	out.print(Str); %>
	document.write(tree);
</script>


  </body>
</html>
