<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<link rel="Bookmark" href="/favicon.ico">
<link rel="Shortcut Icon" href="/favicon.ico" />

<jsp:include page="/WEB-INF/view/commons/head.jsp"></jsp:include>
<title>基础数据列表</title>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		基础数据管理 <span class="c-gray en">&gt;</span> 基础数据列表 
	</nav>
			<span id="toolbar" class="l"><a href="javascript:;" onclick="deleteBatches()"
				class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>
					批量删除</a> <a href="javascript:;"
				onclick="baseData_add()"
				class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>
					添加基础数据</a></span> 
				
		</div>
		<table id="baseDataTable"></table>
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript"
		src="lib/My97DatePicker/4.8/WdatePicker.js"></script>
	<script type="text/javascript"
		src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
	<script type="text/javascript"
		src="lib/bootstrap-table/bootstrap-table.min.js"></script>
	<script type="text/javascript"
		src="lib/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>

	<script type="text/javascript">
		$(function() {
			$('#baseDataTable').bootstrapTable({
				url : 'baseData/list.do',//ajax请求的url地址
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
					field : 'baseId',
					title : '编号'
				}, {
					field : 'baseName',
					title : '基础数据名称'
				}, {
					field : 'baseDesc',
					title : '基础数据描述'
				}, {
					field : 'parentName',
					title : '所属类型'
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
		
		function menuFormatter(value,row,index){
			var html = "<span style='color:green'>普通操作基础数据</span>"
			if(row.type == 'menu'){
				html= "<span style='color:red'>菜单基础数据</span>";
			}
			return html;
		}
		
		function operationFormatter(value,row,index) {
			var html = "<span onclick='baseData_edit("+row.baseId+")' class='glyphicon glyphicon-pencil' style='color:green;cursor: pointer;'>&nbsp;&nbsp;&nbsp;</span>";
			html += "<span onclick='baseData_del("+row.baseId+")' class='glyphicon glyphicon-trash' style='color:red;cursor: pointer;'></span>";
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
		/*基础数据-增加*/
		function baseData_add() {
			layer_show("添加基础数据","baseData/edit.do","800", "500");
		}
		/*基础数据-增加*/
		function baseData_edit(baseId) {
			layer_show("修改基础数据","baseData/edit.do?baseId="+baseId,"800","500");
		}
		
		/*基础数据-删除*/
		function baseData_del(baseId) {
			layer.confirm('确认要删除吗？', function(index) {
				$.get("baseData/delete.do?baseId="+baseId,function(data){
					
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
		/* 基础数据批量删除 */
		function deleteBatches(){
			var users = getBatches()
			console.log(users)
			layer.confirm('确认要删除所选基础数据吗？', function(index) {
				$.get("admin/deleteBatches.do?users="+users,function(data){
					
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
			return $("#adminTable").bootstrapTable("getSelections");
		}
		function refreshTable(){
			$("#baseDataTable").bootstrapTable("refresh");
		}

		/*基础数据-编辑*/
		function admin_edit(title, url, id, w, h) {
			layer_show(title, url, w, h);
		}
		/*基础数据-停用*/
		function admin_stop(obj, id) {
			layer
					.confirm(
							'确认要停用吗？',
							function(index) {
								//此处请求后台程序，下方是成功后的前台处理……

								$(obj)
										.parents("tr")
										.find(".td-manage")
										.prepend(
												'<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
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

		/*基础数据-启用*/
		function admin_start(obj, id) {
			layer
					.confirm(
							'确认要启用吗？',
							function(index) {
								//此处请求后台程序，下方是成功后的前台处理……

								$(obj)
										.parents("tr")
										.find(".td-manage")
										.prepend(
												'<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
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