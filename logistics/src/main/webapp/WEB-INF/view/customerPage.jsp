<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://shiro.apache.org/tags"  prefix="shiro"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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
<link rel="Bookmark" href="/favicon.ico">
<link rel="Shortcut Icon" href="/favicon.ico" />
<jsp:include page="/WEB-INF/view/commons/head.jsp"></jsp:include>

<title>客户列表</title>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		客户管理 <span class="c-gray en">&gt;</span> 客户列表 
	</nav>
			<span id="toolbar" class="l">
				<shiro:hasPermission name="customer:delete">
			<a href="javascript:;" onclick="deleteBatches()"
				class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>
					批量删除</a> </shiro:hasPermission>
						<shiro:hasPermission name="customer:insert">
					<a href="javascript:;"
				onclick="customer_add()"
				class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>
					添加客户</a></shiro:hasPermission></span> 
				
		</div>
		<table id="customerTable"></table>
<jsp:include page="/WEB-INF/view/commons/footer.jsp"></jsp:include>

	<script type="text/javascript">
		$(function() {
			$('#customerTable').bootstrapTable({
				url : 'customer/list.do',//ajax请求的url地址
				/*
					ajax请求以后回调函数的处理
					后台使用返回的PageInfo对象中的 结果 级的key是list，总条数是total
					而前台bootstrapTable插件需要的数据的key叫做rows ，总条数也是叫做total
					那么出现一个问题 : 总条数的key能对上，结果集对不上，就需要在ajax请求完成回调
					responseHandler 这个函数方法处理一下
					并且在自定义一个 json,rows做为key，返回json的 list作为值
						total：还是total
					这样才能满足 bootstrapTable插件数据的需要
				 */
				 method:"post",
				responseHandler : function(res) {
					/*
						res: 后台分页对象PageInfo返回对应的json对象
						res.list : 结果集
						res.total : 总记录数
					 */
					var data = {
						rows : res.list,
						total : res.total
					};
					return data;
				},
				pagination : true,
				toolbar : "#toolbar",//顶部显示的工具条（添加和批量删除的）
				contentType : 'application/x-www-form-urlencoded',//条件搜索的时候ajax请求给后台数据的数据类型（条件搜索post提交必须设置）
				search : true,//是否显示搜索框
				pageNumber : 1,//默认的页面 第一页
				pageSize : 10,//默认的每页条数
				//pageList:[10,25,50,100],//每页能显示的条数
				sidePagination : "server",//是否是服务器分页，每次请求都是对应的10条数据，下一页发送ajax请求
				paginationHAlign : 'right', //底部分页条
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				showColumns : true, //是否显示所有的列
				showRefresh : true, //是否显示刷新按钮
				columns : [ //表格显示数据对应的表头设置，
				{
					checkbox : true
				},//是否显示前台的复选框（多选）
				/*
					每列数据的表头的设置
					filed:返回json数据对应数据的key
					title:表头要显示的名
				 */
				{
					field : 'customerId',
					title : '编号'
				}, {
					field : 'customerName',
					title : '客户名称'
				}, {
					field : 'phone',
					title : '电话'
				}, {
					field : 'email',
					title : '邮箱'
				},{
					field : 'address',
					title : '地址'
				},{
					field : 'gender',
					title : '性别',formatter:genderFormatter
				},{
					field : 'idCard',
					title : '身份证'
				},{
					field : 'realname',
					title : '业务员'
				},{
					field : 'baseName',
					title : '客户区间'
				},{
					field : 'remark',
					title : '客户备注'
				},
				//操作列的设置（删除，修改）
				/*
				formatter: 格式化这一行，回调一个函数
				 */
				{
					field : 'userId',
					title : '操作',
					align : 'center',
					formatter : operationFormatter
				} ],
				queryParams : function(params) {
					//此方法在用户分页或者搜索的时候回自动发送ajax请求调用，并把对应的参数传递给后台
					return {
						pageNum : params.offset / params.limit + 1,
						pageSize : params.limit, //页面大小
						keyword : params.search
					};
				}
			})

		});
		
		function genderFormatter(value,row,index){
			if(row.gender==1){
				return "男";
			
			}
			return "女";
		}
		
		
		function menuFormatter(value,row,index){
			var html = "<span style='color:green'>普通操作客户</span>"
			if(row.type == 'menu'){
				html= "<span style='color:red'>菜单客户</span>";
			}
			return html;
		}
		
		function operationFormatter(value,row,index) {
			var html="<shiro:hasPermission name='customer:update'>";
			html += "<span onclick='customer_edit("+row.customerId+")' class='glyphicon glyphicon-pencil' style='color:green;cursor: pointer;'>&nbsp;&nbsp;&nbsp;</span>";
			html+="</shiro:hasPermission>";
			html+="<shiro:hasPermission name='customer:delete'>";
			html += "<span onclick='customer_del("+row.customerId+")' class='glyphicon glyphicon-trash' style='color:red;cursor: pointer;'></span>";
			html+="</shiro:hasPermission>";
			return html;
		}
		/*
		 参数解释：
		 title	标题
		 url		请求的url
		 id		需要操作的数据id
		 w		弹出层宽度（缺省调默认值）
		 h		弹出层高度（缺省调默认值）
		 */
		/*客户-增加*/
		function customer_add() {
			layer_show("添加客户","customer/edit.do","800", "500");
		}
		/*客户-增加*/
		function customer_edit(customerId) {
			layer_show("修改客户","customer/edit.do?customerId="+customerId,"800","500");
		}
		
		/*客户-删除*/
		function customer_del(customerId) {
			layer.confirm('确认要删除吗？', function(index) {
				$.get("customer/delete.do?customerId="+customerId,function(data){
					
					if(data.code==1){
						layer.msg(data.msg,{time:2000,icon:6});
						refreshTable();
					}else if(data.code==0){
						layer.msg(data.msg,{time:2000,icon:5});
						
					}else if(data.code==2){
						layer.msg(data.msg,{time:2000,icon:5});
						
					}
				})
			});
		}
		/* 客户批量删除 */
		function deleteBatches(){
			var users = getBatches()
			console.log(users)
			layer.confirm('确认要删除所选客户吗？', function(index) {
				$.get("customer/deleteBatches.do?users="+users,function(data){
					
					if(data.code==1){
						layer.msg(data.msg,{time:2000,icon:6});
						refreshTable();
					}else if(data.code==0){
						layer.msg(data.msg,{time:2000,icon:5});
						
					}
				})
			});
		}
		function getBatches(){
			return $("#customerTable").bootstrapTable("getSelections");
		}
		function refreshTable(){
			$("#customerTable").bootstrapTable("refresh");
		}

		
		/*客户-停用*/
		function customer_stop(obj, id) {
			layer
					.confirm(
							'确认要停用吗？',
							function(index) {
								//此处请求后台程序，下方是成功后的前台处理……

								$(obj)
										.parents("tr")
										.find(".td-manage")
										.prepend(
												'<a onClick="customer_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
								$(obj)
										.parents("tr")
										.find(".td-status")
										.html(
												'<span class="label label-default radius">已禁用</span>');
								$(obj).remove();
								layer.msg('已停用!', {
									icon : 5,
									time : 1000
								});
							});
		}

		/*客户-启用*/
		function customer_start(obj, id) {
			layer
					.confirm(
							'确认要启用吗？',
							function(index) {
								//此处请求后台程序，下方是成功后的前台处理……

								$(obj)
										.parents("tr")
										.find(".td-manage")
										.prepend(
												'<a onClick="customer_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
								$(obj)
										.parents("tr")
										.find(".td-status")
										.html(
												'<span class="label label-success radius">已启用</span>');
								$(obj).remove();
								layer.msg('已启用!', {
									icon : 6,
									time : 1000
								});
							});
		}
	</script>
</body>
</html>