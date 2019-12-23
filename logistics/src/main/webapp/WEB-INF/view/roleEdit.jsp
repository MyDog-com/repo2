<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>

<base href="<%=basePath%>">

<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css"
	href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css"
	href="static/h-ui.admin/css/style.css" />
	
<link rel="stylesheet" type="text/css"
	href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
</head>
<body>
	<article class="page-container">
	
		<form class="form form-horizontal" id="roleForm"
			action="${role == null ? 'role/insert.do':'role/update.do' }">
			
			<input type="hidden" name="roleId" value="${role.roleId }">
			<input type="hidden" name="permissionIds" id="permissionIds" value="">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>角色名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${role.rolename }"
						placeholder="" id="rolename" name="rolename">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>角色描述：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea name="remark" id="remark" class="textarea"
						placeholder="说点什么... " dragonfly="true">${role.remark }</textarea>
				</div>
			</div>

			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>角色权限：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<ul id="permissionTree" class="ztree"></ul>
				</div>
			</div>

			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input class="btn btn-primary radius" type="submit"
						value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</article>

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript"
		src="lib/jquery.validation/1.14.0/jquery.validate.js"></script>
	<script type="text/javascript"
		src="lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript"
		src="lib/jquery.validation/1.14.0/messages_zh.js"></script>
	<script type="text/javascript"
		src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

	<script type="text/javascript">
	var setting = {	
			check:{enable:true},data:{simpleData: {enable: true}},async: {
				enable: true,
				url:"permission/getAllPermissions.do",
				
			},callback:{
				onAsyncSuccess:zTreeOnAsyncSuccess
			} 
	};
	
	function zTreeOnAsyncSuccess(event,treeId,treeNode,msg){
		var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
		var permissionIds = "${role.permissionIds}";
		
		var permissionIdsArr = permissionIds.split(",");
		for (var i=0; i<permissionIdsArr.length;i++){
			var permissionId = permissionIdsArr[i];
			var node = treeObj.getNodeByParam("id", permissionId);
			var children = node.children;
			if(children==null){
				treeObj.checkNode(node,true,true);
			}
			
		}
	} 


	$(function(){
		$.fn.zTree.init($("#permissionTree"), setting);
	});

		$(function() {
			$("#roleForm").validate({
				rules : {
					rolename : {
						required : true

					},

					remark : "required"
				},
				messages : {
					rolename : {
						required : "傻逼，角色名称不能为空！",

					},
					remark : "角色描述不能为空"

				},
				//form是dom对象
				submitHandler : function(form) {
					
					
					/*
					问题：如何把zTree的数据拼接到roleForm这个表单的数据中？
					方式一：
					1.先将表单数据序列化$("form").serialize()
						数据序列化成 key1=value1&key2 = value2
					2.获取zTree选中的数据id拼接成permissionIds=1,2,8，5，
					3.将表单序列化的数据和zTree的数据拼接起来
					4.将拼接的完整的表单对象用ajax发送给后台
					
					方式二：
					1.获取zTree选中的数据id拼接成permissionIds=1,2,8，5，
					2.使用dom操作，将获取的值设置给表单隐藏域permissionIds为值
					3.提交表单
					*/
					var formData = $(form).serialize();
					var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
					var nodes = treeObj.getCheckedNodes(true);
					var ids=[];
					for(var i = 0;i<nodes.length;i++){
						var node = nodes[i];
						var id = node.id;
						ids.push(id)
					}
					var permissionIds = ids.toString();
					formData += "&permissionIds"+permissionIds;
					 $('#permissionIds').val(permissionIds);
					 
					//使用jquery的方法必须先把dom对象转为jquery对象
					var jqForm = $(form)
					jqForm.ajaxSubmit(function(data) {
						if (data.code == 1) {
							layer.msg(data.msg, {
								icon : 6,
								time : 2000
							}, function() {
								//让父页面刷新列表
								parent.refreshTable();
								//关闭父窗口所有的弹出层
								parent.layer.closeAll();
							})
						} else if (data.code == 0) {
							layer.msg(data.msg, {
								icon : 5,
								time : 3000
							}, function() {
							})
						}

					}); 

				}
			})

		})
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>