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
		check:{enable:true},data:{simpleData: {enable: true}},async: {
			enable: true,
			url:"permission/getAllPermissions.do",
			
		}
};


$(function(){
	$.fn.zTree.init($("#permissionTree"), setting);
});
</script>
</head>
<body>
<ul id="permissionTree" class="ztree"></ul>


</body>
</html>