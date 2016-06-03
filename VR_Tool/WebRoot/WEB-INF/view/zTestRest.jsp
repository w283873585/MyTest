<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
        <title>VRShow - 测试接口</title>
        <link rel="stylesheet"  href="../css/bootstrap.min.css" />
        <script src="../js/jquery.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/md5.js"></script>
    	<style type="text/css">
    		body {font-family: '微软雅黑'}
    		.row {padding-left: 100px;}
    		.highlight {
   		        padding: 20px;
			    margin: 20px 0;
			    border: 1px solid #eee;
			    border-left-width: 5px;
			    border-radius: 3px;
			    border-left-color: #aa6708;
			}
			.result {
				 border-left-color: #1b809e;
				 min-height: 120px;
				 word-wrap: break-word;
			}
			.params {
				border-left-color: #5cb85c;
				min-height: 80px;
			}
			.processor {
				height: 32px;
				line-height: 32px;
				border: 1px solid #ccc;
				border-radius: 4px;
				padding: 0 10px;
				position: relative;
				font-size: 5px;
    			width: 80px;
			}
			.processor ._close {
				position: absolute;
				top: 4px;
				right : 4px;
				height: 7px;
				line-height: 7px;
				color: black;
				font-size: 10px;
				
			}
    	</style>
    </head>
    <body>
    <div class="container">
    	<div class="h3">测试参数</div>
    	<div class="highlight">
	    	<div class="row">
	    		<div class="form-group col-sm-8">
					<input type="text" class="form-control"  placeholder="输出接口地址" id="resourceUrl">
				</div>
				<div class="form-group col-sm-2">
					<a href="javascript:;" class="btn btn-warning " role="button" id="addParam">添加参数</a>
				</div>
				<div class="form-group col-sm-2">
		    		<select class="form-control" id="serverUrl">
		    		  <option value="http://192.168.200.148:8080/VR_Service/">default</option>
					  <option value="http://127.0.0.1:8080/VR_Service/">local</option>
					  <!-- <option value="http://120.76.79.49:38080/VR_Service/">online</option> -->
					</select>
				</div>
	    	</div>
	    	<div class="row" id="sendContainer">
	    		<div class="form-group col-sm-10">
					<a href="javascript:;" class="btn btn-success" id="doSubmit" role="button">发起请求</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:;" class="btn btn-warning" role="button" id="clearParams">清空参数</a>
	    		</div>
	    		<div class="form-group col-sm-2 text-right">
					<a href="javascript:;" class="btn btn-info" role="button" data-toggle="modal" data-target="#myModal">高级设置</a>
				</div>
	    	</div>
    	</div>
    	<div class="h3">结果</div>
    		<div class="highlight result">
    	</div>
    	<div class="h3">参数</div>
    		<div class="highlight params">
    	</div>
    </div>
   	<div class="modal fade" id="myModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">高级设置</h4>
	      </div>
	      <div class="modal-body">
        	<div class="form-group col-sm-8">
        		<label for="manualSetting">手动设置URL</label>
        		<input type="text" class="form-control"  placeholder="输出接口名称" id="manualServerUrl">
        	</div>
        	<div class="clearfix"></div>
        	<div class="form-group col-sm-8">
        		<label for="clients">选择客户端类型</label>
	        	<select class="form-control" id="clients">
	        	</select>
			</div>
        	<div class="clearfix"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div>
<script type="text/javascript">
$(function() {
	var clients = $.parseJSON('${clients}');
   	var processors = $.parseJSON('${processors}');
   	var isBusy = false;
  
   	// init 
   	renderClients();
   	
	var manager = paramsManager();
   	manager.init();
   	
   	// 提交请求
   	$("#doSubmit").click(function() {
   		if (isBusy) {
   			return;
   		}
   		// 参数信息
   		var data = {
   			url: getUrl(),
   			clientName: getClient(),
   			paramsInfo: manager.getData()
   		};
   		
   		isBusy = true;
   		$(".result").html("等待中...");
		$.ajax({
			url: "${pageContext.request.contextPath}/my/send",
			type: "post",
			data: data,
			success: function(res) {
				res = $.parseJSON(res);
				$(".result").html("");
				$(".params").html("");
				$(".result").html(res.result);
				$(".params").html(res.params);
				isBusy = false;
			},
			error: function(a, b, c, d) {
				$(".result").html("ajax请求出现了一点小错误。。。<br/>" + JSON.stringify(a));
				isBusy = false;
			}
		});
   		
   	});
   	// 清空参数
   	$("#clearParams").click(function() {
   		manager.init();
   	});
   	
   	function getUrl() {
   		var server = $("#manualServerUrl").val() || $("#serverUrl").val();
   		server = server.replace(/\/$/, "");
   		var resourceUrl = $("#resourceUrl").val();
   		resourceUrl = resourceUrl.replace(/^\//, "");
   		return server + "/" + resourceUrl;
   	}
   	
   	function getClient() {
   		return $("#clients").val();
   	}
   	
	function paramsManager() {
   		
   		var // 用来放处理者数据的容器，注意，删除某参数对象时该数据不变，该数组的索引作为每个参数对象的id
   			data = [],		// 结构如 [['a', 'b', 'c'], ['a', 'b', 'c']]
   			// 实际生效的id列表，每个参数对象会有一个对应的唯一的id，用来维护目前还生效的id列表
   			validIds = [],
   			// 处理者html
   			processorsHtml = getProcessorsHtml();
   		
   		// 事件注册
   		// 添加参数行
   		$("#addParam").click(addParam);
   		
   		// 添加参数处理
   		$(".highlight").on("click", ".paramBox ul li a", addProcessor);
   		
   		// 删除参数处理
  		$(".highlight").on("click", ".paramBox ._close", removeProcessor);
  		
   		
   		// 暴漏的接口
   		return {
   			init: function() {
   				$(".paramBox").remove();
   				// 默认为两个参数
   		  		addParam();
   		  		addParam();
   			},
   			getData: getData
   			/**
   	   		paramsInfo : [{
   				key: ""
   				value: ""
   				processorKeys: "a,b,c"
   			}]
   	   		*/
   		};
  		
  		
   		function addParam() {
   			var index = data.push([]) - 1;
   			validIds.push(index);
   			
   			var html = "<div class='row paramBox' value='" + index +"' id='paramBox_" + index + "'>"
					+ "<div class='form-group col-sm-4'>"
						+ "<input type='text' class='form-control'  placeholder='参数名'>"
					+ "</div>"
					+ "<div class='form-group col-sm-4'>"
						+ "<input type='text' class='form-control'  placeholder='参数值'>"
					+ "</div>"
					+ "<div class='form-group col-sm-1'>"
						+ "<div class='btn-group'>"
							+ "<button type='button' class='btn btn-info dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
							+ "add&nbsp;&nbsp;<span class='caret'></span>"
							+ "</button>"
							+ "<ul class='dropdown-menu'>"
								+ processorsHtml
							+ " </ul>"
						+ "</div>"
					+ "</div>"
				+ "</div>";
   			$("#sendContainer").before(html);
   		}
   		
   		function addProcessor() {
   			var that = $(this);
   			var id = getParamId(that);
   			var key = that.attr("value");
   			data[id].push(key);
   			
   			var proHtml = "<div class='form-group col-sm-1'>"
			+ "<div class='processor'>" + key
				+ "<a class='_close' href='javascript:;'>x</a></div>"
			+ "</div>";
   			$("#paramBox_" + id).append(proHtml);
   		}
   		
   		function removeProcessor() {
   			var that = $(this);
   			var id = getParamId(that);
   			var index = that.index(that.parent().parent().parent().find("._close"));
   			data[id].splice(index, 1);
   			that.parent().parent().remove();
   		}
   		
   		function getParamId(obj) {
   			return +obj.parents(".paramBox").attr("value");
   		}
   		
   		function getProcessorsHtml() {
   			var html = "";
   			for (var i = 0; i < processors.length; i++) {
	   			html += "<li><a href='javascript:;' value='" + processors[i] + "'>" + processors[i] + "</a></li>";
   			}
   			return html;
   		}
   		
   		function getData() {
   			var result = [];
   			for (var i = 0; i < validIds.length; i++) {
   				var id = validIds[i];
   				var processArr = data[id];
   				var textObj = getKeyValue(id);
   				if (textObj.key) {
   					result.push({
   						key: textObj.key,
   						value: textObj.value,
   						processorKeys: processArr.join(",")
   					});
   				}
   			}
   			return JSON.stringify(result);
   		}
   		
   		function getKeyValue(id) {
   			var arr = $("#paramBox_" + id).find("input[type='text']");
   			return {
   				key: arr.eq(0).val(),
   				value: arr.eq(1).val()
   			};
   		}
   	}
   	
   	function renderClients() {
	   	var clientsHtml = "";
	   	for (var i = clients.length - 1; i >= 0; i--) {
	   		clientsHtml += "<option value='" + clients[i] + "'>" + clients[i] + "</option>";
	   	}
	   	$("#clients").html(clientsHtml);
   	}
});
</script>
</body>
</html>

