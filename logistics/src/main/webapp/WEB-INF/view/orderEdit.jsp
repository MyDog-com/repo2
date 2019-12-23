<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>    
    
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE HTML>
<html>
<head>

<base href="<%=basePath%>">

<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<style type="text/css">
	.order_detail{
		background-color: durkgray;
		border: 1px solid black;
		border-right: 0; 
		height: 40px;
		line-height: 40px;
	}
	.order_detail input{
		height: 30px;
		text-align: center;
	}
	.order_add{
		border: 1px solid black;
	}
	#order_detail{
		text-align: center;
	}

</style>
<!-- 引入头部公共页面 -->
<!-- 静态包含公共头部页面 -->
<%-- <jsp:include page="/WEB-INF/view/commons/head.jsp"></jsp:include> --%>
<%@ include file="/WEB-INF/view/commons/head.jsp" %>

</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" method="post" action="${empty customer ? 'customer/insert.do' : 'customer/update.do'}" id="orderForm">
	<!-- 隐藏域 ，订单状态-->
	<input type="hidden" name="orderStatus" value="1">
	
	<div class="row cl">	
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">业务员：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="userId">
					<c:forEach items="${users}" var="obj">
						<option value="${obj.userId}" ${order.userId == obj.userId ? "selected":"" }>${obj.realname}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">客户：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select  name="customerId" id="customer">
					<c:forEach items="${customers}" var="obj">
					
						<!-- 
							html5 标签 支持自定义属性
							规则 ：
								data- 固定语法 
								data- 自定义属性名称（见名知意）
								
								data-id
								data-name
						 -->
						<option data-base_id="${obj.baseId}" value="${obj.customerId}" ${order.customerId == obj.customerId ? "selected":"" }>${obj.customerName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">到达国家：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select id="interval" name="intervalId">
					<c:forEach items="${intervals}" var="obj">
						<option value="${obj.baseId}" ${order.intervalId==obj.baseId ? "selected":"" }>${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">收货地址：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${order.shippingAddress }" required="required"  placeholder="" id="shippingAddress" name="shippingAddress">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">收货人：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${order.shippingName }" required="required"   placeholder="" id="shippingName" name="shippingName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">联系电话：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${order.shippingPhone }" required="required"  placeholder="" id="shippingPhone" name="shippingPhone">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">付款方式：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="paymentMethodId">
					<c:forEach items="${payments}" var="obj">
						<option value="${obj.baseId}" ${order.paymentMethodId == obj.baseId ? "selected":"" }>${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">货运方式：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="freightMethodId">
					<c:forEach items="${freights}" var="obj">
						<option value="${obj.baseId}" ${order.freightMethodId == obj.baseId ? "selected":"" }>${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">取件方式：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="takeMethodId">
					<c:forEach items="${fetchTypes}" var="obj">
						<option value="${obj.baseId}" ${order.takeMethodId == obj.baseId ? "selected":"" }>${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">物流公司：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="xx物流公司"  disabled="disabled"  placeholder="" id="customerName" name="customerName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">物流单号：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="243242343" disabled="disabled"   placeholder="" id="customerName" name="customerName">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">收件人：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="张三" disabled="disabled"   placeholder="" id="customerName" name="customerName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">收货地址：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="广州市天河区xxx" disabled="disabled"   placeholder="" id="customerName" name="customerName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">联系电话：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="135xxxxx"  disabled="disabled"  placeholder="" id="customerName" name="customerName">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">取件地址：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${order.takeAddress }"   placeholder="" id="customerName" name="takeAddress">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">联系电话：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${order.takePhone }"   placeholder="" id="takePhone" name="takePhone">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">取件联系人：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${order.takeName }"   placeholder="" id="takeName" name="takeName">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">订单备注：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<textarea name="orderRemark" cols="" rows="" class="textarea"  placeholder="请描述一下订单信息!!!" >${order.orderRemark }</textarea>
			</div>
		</div>
	</div>
	
	
	
	<div  id="order_detail" class="row cl">
		<div>
			<div  class="col-xs-2 col-sm-2 order_detail">货物名称</div>
			<div  class="col-xs-2 col-sm-1 order_detail">数量</div>
			<div  class="col-xs-2 col-sm-1 order_detail">单位</div>
			<div  class="col-xs-2 col-sm-2 order_detail">单价</div>
			<div  class="col-xs-2 col-sm-2 order_detail">总价值</div>
			<div  class="col-xs-2 col-sm-3 order_detail">备注</div>
			<div  class="col-xs-2 col-sm-1 order_detail order_add">
				<span style="font: 30px;cursor: pointer;color: green" 
					class="glyphicon glyphicon-plus"
					onclick="add_goods_detail();"
					></span>
				</div>
		</div>
		
		<div id="goods_detail">
			<div  class="col-xs-2 col-sm-2 order_detail">
				<input type="text" name="orderDetails[][goodsName]">
			</div>
			<div  class="col-xs-2 col-sm-1 order_detail">
				<input onkeyup="operationTotalMoney(this)" type="text" size="3" name="orderDetails[][goodsNumber]">
			</div>
			<div  class="col-xs-2 col-sm-1 order_detail">
				<select name="orderDetails[][goodsUnit]">
					<c:forEach items="${units}" var="obj">
						<option value="${obj.baseId}">${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
			<div  class="col-xs-2 col-sm-2 order_detail">
				<input onkeyup="operationTotalMoney(this)" type="text" name="orderDetails[][goodsUnitPrice]">
			
			</div>
			<div  class="col-xs-2 col-sm-2 order_detail">
				<input type="text" name="orderDetails[][goodsTotal]">
			</div>
			<div  class="col-xs-2 col-sm-3 order_detail">
				<input type="text" name="orderDetails[][goodsRemark]">
			</div>
			<div  class="col-xs-2 col-sm-1 order_detail order_add">
				<span style="font: 30px;cursor: pointer;color: red" 
				class="glyphicon glyphicon-remove"
				onclick="remove_goods_detail(this);"
				></span>
			</div>
		</div>
		
	</div>
	
	
	<div class="row cl">
		<div class="col-xs-12 col-sm-12 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<jsp:include page="/WEB-INF/view/commons/footer.jsp"></jsp:include>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript" src="lib/jquery/jquery.serializejson.min.js"></script>
<script type="text/javascript">

//当每一行的商品数量或者商品单价输入完毕以后计算总价自动添加到总价的表单中
function operationTotalMoney(obj){
	var parentDiv = $(obj).parent().parent();
	var inputs = parentDiv.find("input");
	var goodsNumber = inputs.eq(1).val();
	var goodsUnitPrice = inputs.eq(2).val();
	if(goodsNumber>0 && goodsUnitPrice){
		inputs.eq(3).val(goodsNumber*goodsUnitPrice);
	}	
}


//添加商品详情
function add_goods_detail(){
	//克隆商品详情
	var goodsDetail = $("#goods_detail").clone();
	
	//清除表单的数据
	goodsDetail.find("input").val("");
	 
	
	var order_detail = $("#order_detail");
	
	
	order_detail.append(goodsDetail);
}
//删除商品详情
function remove_goods_detail(obj){
	
	$(obj).parent().parent().remove();
}

$(function(){
	//文档加载完毕以后立马执行区间和客户区间保持一致的函数
		changeCustomer();
	
	//为客户下拉框绑定更改客户自动选择对应区间的方法
	$("#customer").change(function(){
		changeCustomer();
	})
	
	
})

//客户改变 对应的区间改变函数
function changeCustomer(){
	var selectedOption = $("#customer option:selected")
	var baseId = selectedOption.data("base_id");
	var intervals = $("#interval option")
	for(var i = 0;i<intervals.length;i++){
		var interval = intervals[i];
		var intervalId = interval.value;
		if(baseId==intervalId){
			$(interval).prop("selected",true);
		}
	}
}





$(function(){

	$("#orderForm").validate({
		/* rules:{
			
		},
		messages:{
			
		}, */
		submitHandler:function(form){
			//将表单使用Jquery序列化成JSON字符串
			var serializeJSON = $(form).serializeJSON();
			//将JSON字符串转换成JSON对象
			var jsonData = JSON.stringify(serializeJSON);
			console.log(jsonData);
			$.ajax({
				type: "POST",
				   url: "order/insert.do",
				   data: jsonData,
				   contentType: "application/json; charset=utf-8",
				success:function(data){
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
							time : 2000
						}, function() {
						})
					}
				}
			})
			/* 
			$.ajax({
			   type: "POST",
			   url: "order/insert.do",
			   data: jsonData,
			   contentType: "application/json; charset=utf-8",
			   success: function(data){
				   layer.msg(data.msg, {icon: data.code,time: 1000 },function(){
						//一定要先刷新再关闭
						//1让父层页面重新刷新一下（重新加载一下）
						window.parent.refreshTable();
						//2关闭当前弹出层
						parent.layer.closeAll();
					}); 
			   }

				
			}) */
			
		}
	});

});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>