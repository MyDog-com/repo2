<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
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
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
</head>
<body>
	<article class="page-container">
		<form class="form form-horizontal" id="adminForm" action="${user != null ? 'admin/update.do':'admin/insert.do' }">
		<input type="hidden" name="userId" value="${user.userId }">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>账号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" ${user == null ? "" : "disabled" } class="input-text" value="${user.username }" placeholder=""
						id="username" name="username">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>真实姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${user.realname }" placeholder=""
						id="realname" name="realname">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>初始密码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="password" class="input-text" autocomplete="off"
						value="" placeholder="密码" id="password" name="password">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>确认密码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="password" class="input-text" autocomplete="off"
						placeholder="确认新密码" id="password2" name="password2">
				</div>
			</div>
			
			
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">角色：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="select-box" style="width: 150px;"> <select
						class="select" name="roleId" size="1">
							<option value="0">--请选择--</option>
							<c:forEach items="${roles }" var="role">
								<option value="${role.roleId }" ${user.roleId == role.roleId ? "selected" : ""}>--${role.rolename }--</option>
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
	<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
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
	<script type="text/javascript">
		$(function() {
			$("#adminForm").validate({
				rules:{
					username:{
						required:true,
						minlength:3,
						remote:{
							url:"admin/checkUsername.do",
							type:"post",
							dataType:"json",
							data:{
								username:function(){
									return $("#username").val();
								}
							}
							
						}
					},
					realname:{required:true,isChinese:true},
					password:{required:true,minlength:3},
					password2:{equalTo:"#password",required:true},
					roleId:{
						min:1
					}
				},
				messages:{
					username:{
						required:"傻逼，账号不能为空！",
						minlength:"傻逼，账号最少三位！",
						remote:"狗卵账号已经存在，换一个试下！"
					},
					realname:{required:"傻逼，真实姓名不能为空！",isChinese:"傻逼，姓名只能是中文！"},
					password:{required:"傻逼，密码不能为空！",minlength:"傻逼，密码不能少于三位!"},
					password2:{required:"必须确认密码！",equalTo:"确认密码必须和初始密码一样！"},
					roleId:{
						min:"请选择角色！"
					}
				},
				//form是dom对象
				submitHandler:function(form){
					//使用jquery的方法必须先把dom对象转为jquery对象
					var jqForm = $(form)
					jqForm.ajaxSubmit(function(data){
						if(data.code==1){
							layer.msg(data.msg,{icon:6,time:2000},
									function(){
								//让父页面刷新列表
								parent.refreshTable();
								//关闭父窗口所有的弹出层
								parent.layer.closeAll();
							})
						}else if(data.code==0){
							layer.msg(data.msg,{icon:5,time:3000},
									function(){
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