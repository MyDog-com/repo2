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
	
		<form class="form form-horizontal" id="baseDataForm"
			action="${baseData == null ? 'baseData/insert.do':'baseData/update.do' }">
			
			<input type="hidden" name="baseId" value="${baseData.baseId }">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>基础数据名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${baseData.baseName }"
						placeholder="" id="baseName" name="baseName">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>基础数据描述：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea name="baseDesc" id="baseDesc" class="textarea"
						placeholder="说点什么... " dragonfly="true">${baseData.baseDesc }</textarea>
				</div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">基础数据类型：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="select-box" style="width: 150px;"> <select
						class="select" name="parentId" size="1">
							<option value="0">--请选择--</option>
							<c:forEach items="${baseDatas }" var="parent">
								<option value="${parent.baseId }" ${parent.baseId == baseData.parentId ? "selected":"" } >--${parent.baseName }--</option>
							</c:forEach>

					</select>
					</span>
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
	

		$(function() {
			$("#baseDataForm").validate({
				rules : {
					baseName : {
						required : true

					},

					baseDesc : "required"
				},
				messages : {
					baseName : {
						required : "傻逼，基础数据名称不能为空！",

					},
					baseDesc : "基础数据描述不能为空" 

				},
				//form是dom对象
				submitHandler : function(form) {
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