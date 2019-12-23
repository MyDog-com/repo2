<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>

<base href="<%=basePath%>">
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css">
<title>Insert title here</title>
<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript">

var setting = {	
		check:{enable:true}
};

var zNodes=[
	{name:"管理员管理",id:1,open:true,children:[
		{name:"管理员管理",url:"admin/adminPage.do",children:[
			{name:"管理员列表",id:2,children:[{name:"向思文"}]},
			{name:"新增管理员",id:3},
			{name:"删除管理员",id:4},
			{name:"修改管理员",id:5}
		]},
		{name:"角色管理",url:"role/rolePage.do"},
		{name:"权限管理",url:"permission/permissionPage.do"}
		]}
]




$(function(){
	$.fn.zTree.init($("#permissionTree"), setting, zNodes);
});
</script>
</head>
<body>
<ul id="permissionTree" class="ztree"></ul>


</body>
</html>