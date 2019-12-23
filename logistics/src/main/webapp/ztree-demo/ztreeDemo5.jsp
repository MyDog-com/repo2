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
		check:{enable:true},data:{simpleData: {enable: true}},
		async: {
			enable: true,
			url:"permission/getAllPermissions.do",
		},callback:{
			onAsyncSuccess:zTreeOnAsyncSuccess
		}
};

function zTreeOnAsyncSuccess(event,treeId,treeNode,msg){
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	var permissionIds = "10,31,32,33,34,35,40";
	var permissionIdsArr = permissionIds.split(",");
	for (var i=0; i<permissionIdsArr.length;i++){
		var permissionId = permissionIdsArr[i];
		var node = treeObj.getNodeByParam("id", permissionId, null);
		var children = node.children;
		if(children==null){
			treeObj.checkNode(node,true,true);
		}
		
	}
}

$(function(){
	$.fn.zTree.init($("#permissionTree"), setting);
});

function getCheckedNodes(){
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	var nodes = treeObj.getCheckedNodes(true);
	console.log(nodes)
	var ids=[];
	for(var i = 0;i<nodes.length;i++){
		var node = nodes[i];
		var id = node.id;
		ids.push(id)
		
	}
}
</script>
</head>
<body>
<ul id="permissionTree" class="ztree"></ul>
<button onclick="getCheckedNodes()">获取ztree选中的idzhi</button>


</body>
</html>