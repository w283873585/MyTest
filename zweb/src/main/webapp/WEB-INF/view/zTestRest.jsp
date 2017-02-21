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
        <script src="../js/jsonToHtml.js"></script>
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
				word-wrap: break-word;
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
				height: 20px;
				line-height: 7px;
				color: black;
				font-size: 10px;
				width: 30px;
				text-align: right;
				
			}
			.interfaceBox {
				border: 0;
				height: 34px;
				line-height: 34px;
				margin-right: 25px;
				margin-top: 20px;
				border-radius: 4px;
				color: #c7254e;
				cursor: pointer;
			}
			.interfaceBox:hover {
				color: #c7254e;
				text-decoration: none;
			}
			.table_container{
				margin-top: 20px;
				margin-right: 0;
			    margin-left: 0;
			    background-color: #fff;
			    border-radius: 4px 4px 0 0;
			    -webkit-box-shadow: none;
			    box-shadow: none;
		        padding: 15px 15px 5px;
		        position: relative;
				font-size: 12px;
		        font-family: '微软雅黑';
			}
			.table_container .title{
				font-family: '微软雅黑';
				font-size: 12px;
				position: absolute;
			    top: -15px;
			    left: 16px;
			    font-size: 12px;
			    font-weight: 700;
			    color: #959595;
			    text-transform: uppercase;
			    letter-spacing: 1px;
			}
			.param_kv {
				margin-bottom: 15px;
			}
    	</style>
    </head>
    <body>
    <div class="container">
    
    	<!-- 参数相关 -->
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
		    		  <option value="http://192.168.200.148:8080">default</option>
					  <option value="http://127.0.0.1:8080">local</option>
					  <!-- <option value="http://120.76.79.49:38080">online</option> -->
					</select>
				</div>
	    	</div>
	    	<div class="row" id="sendContainer">
	    		<div class="form-group col-sm-8">
					<a href="javascript:;" class="btn btn-success" id="doSubmit" role="button">发起请求</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:;" class="btn btn-warning" role="button" id="clearParams">清空参数</a>
	    		</div>
	    		<div class="form-group col-sm-4 text-right">
	    			<a href="javascript:;" class="btn btn-info" role="button" data-toggle="modal" data-target="#myModal">高级设置</a>
	    			&nbsp;
	    			<a href="javascript:;" class="btn btn-warning" role="button" data-toggle="modal" data-target="#interfaceManager">接口管理</a>
				</div>
	    	</div>
    	</div>
    	
    	<!-- 最终请求结果 -->
    	<div class="h3">结果</div>
    		<div class="highlight result">
    	</div>
    	
    	<!-- 最终请求参数 -->
    	<div class="h3">参数</div>
    		<div class="highlight params">
    	</div>
    	
    	<!-- 历史记录 -->
    	<div class="panel panel-default">
		    <div class="panel-heading" role="tab" id="headingOne">
		      <h4 class="panel-title">
		        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
		          	历史记录
		        </a>
		      </h4>
		    </div>
		    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
		        <ul class="list-group"></ul>
		    </div>
		</div>
    </div>
    
    <!-- 高级设置 -->
   	<div class="modal fade" id="myModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	        	<span aria-hidden="true">&times;</span>
	        </button>
	        <h4 class="modal-title">高级设置</h4>
	      </div>
	      <div class="modal-body">
        	<div class="form-group col-sm-8">
        		<label for="manualServerUrl">手动设置URL</label>
        		<input type="text" class="form-control"  placeholder="输出接口名称" id="manualServerUrl">
        	</div>
        	<div class="clearfix"></div>
        	<div class="form-group col-sm-8">
        		<label for="clients">选择客户端类型</label>
	        	<select class="form-control" id="clients"></select>
			</div>
        	<div class="clearfix"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div>
	
	<!-- 接口管理 -->
   	<div class="modal fade" id="interfaceManager" >
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">接口管理</h4>
	      </div>
	      <div class="modal-body">
	      
	      	<!-- 接口修改 -->
        	<div id="interfaceModify" style="padding: 0 10px;display: none"></div>
        	
        	<!-- 接口查询 -->
        	<div id="interfaceSearch">
	        	<div class="form-group col-sm-4">
	        		<input type="text" class="form-control"  placeholder="输出接口名称" id="queryKeyword">
	        	</div>
	        	<div class="form-group col-sm-6">
	        		<button type="button" class="btn btn-info" id="queryInterface">&nbsp;查找&nbsp;</button>
	        	</div>
	        	<div class="form-group col-sm-1">
	        		<button type="button" class="btn btn-success" id="toInterfaceTestCase">&nbsp;+&nbsp;测试用例&nbsp;</button>
	        	</div>
	        	<div class="clearfix"></div>
        		<div class="form-group col-sm-12" id="interfaceBody" style="min-height: 200px; font-size:12px;"></div>
	        	<div class="clearfix"></div>
	        </div>
	        
	        <!-- 接口测试用例 -->
        	<div id="interfaceTestCase" style="padding: 0 10px;display: none;overflow:hidden;">
        		<div class="form-group col-sm-12" style="min-height: 50px;">
        			<h5>接口：</h5>
       				<button type="button" class="btn btn-primary" id="selectInterface">&nbsp;选择接口&nbsp;</button>
        		</div>
        		<div class="form-group col-sm-12" style="min-height: 200px;">
        			<h5>参数：</h5>
        			<div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon" style="width: initial;">username</div>
					      <input type="text" class="form-control" id="exampleInputAmount" placeholder="用户名">
					      <div class="input-group-addon" style="width: initial;">非空，字符串</div>
					    </div>
					</div>
					<div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon" style="width: initial;">password</div>
					      <input type="text" class="form-control" id="exampleInputAmount" placeholder="密码">
					      <div class="input-group-addon" style="width: initial;">非空，正整数</div>
					    </div>
					</div>
					<div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon" style="width: initial;">username</div>
					      <input type="text" class="form-control" id="exampleInputAmount" placeholder="用户名">
					      <div class="input-group-addon" style="width: initial;">非空，字符串</div>
					    </div>
					</div>
					<div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon" style="width: initial;">password</div>
					      <input type="text" class="form-control" id="exampleInputAmount" placeholder="密码">
					      <div class="input-group-addon" style="width: initial;">非空，正整数</div>
					    </div>
					</div>
					<div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon" style="width: initial;">username</div>
					      <input type="text" class="form-control" id="exampleInputAmount" placeholder="用户名">
					      <div class="input-group-addon" style="width: initial;">非空，字符串</div>
					    </div>
					</div>
					<div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon" style="width: initial;">password</div>
					      <input type="text" class="form-control" id="exampleInputAmount" placeholder="密码">
					      <div class="input-group-addon" style="width: initial;">非空，正整数</div>
					    </div>
					</div>
        		</div>
        		<div class="form-group col-sm-12">
        			<button type="button" class="btn btn-info" id="testCase_nextStep">&nbsp;下一步&nbsp;</button>
        			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        			<button type="button" class="btn btn-success" id="testCase_complete">&nbsp;完成&nbsp;</button>
        		</div>
        	</div>
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div>
	
<script type="text/javascript">
$(function() {
	/**
		请求管理器
	*/
	var requestManager = new RequestManager({
		basePath: "${pageContext.request.contextPath}",
		clients: $.parseJSON('${clients}'),
		processors: $.parseJSON('${processors}')
	});
	
	function RequestManager(setting) {
		var isBusy = false, 
			manager = paramsManager();
			
		init();
		
		return {
			initialize: function(url, params) {
				$(".result").html("");
				$(".params").html("");
				$("#resourceUrl").val(url)
				manager.addParams(params);
			}
		};
		
		function init() {
			manager.init();		
			renderClients();
			
			// 清空参数
			$("#clearParams").click(function() {
				manager.init();
			});
			
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
					url: setting.basePath + "/my/send",
					type: "post",
					data: data,
					success: function(res) {
						isBusy = false;
						$(".result").html("");
						$(".params").html("");
						$(".result").html(toJsonHtml($.parseJSON(res.result)));
						$(".params").html(res.params);
					},
					error: function(a, b, c, d) {
						$(".result").html("ajax请求出现了一点小错误。。。<br/>" + JSON.stringify(a));
						isBusy = false;
					}
				});
			});
		}
		
		function renderClients() {
			var clients = setting.clients;
			var clientsHtml = "";
			for (var i = clients.length - 1; i >= 0; i--) {
				clientsHtml += "<option value='" + clients[i] + "'>" + clients[i] + "</option>";
			}
			$("#clients").html(clientsHtml);
		}

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
			
		// 解析url 分离server与resource
	 	function parseUrl(url) {
	 		var result = /^(\w+:\/\/[^:\/]*(?::[0-9]+)?)(\/.*)?$/.exec(url);
			return {
				server: result ? result[1] : "",
				resource: result ? result[2] : ""
			};
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
			$("#addParam").click(function() {
				addParam.call(this);
			});
			
			// 添加参数处理
			$(".highlight").on("click", ".paramBox ul li a", function() {
				addProcessor.call(this);
			});
			
			// 删除参数处理
			$(".highlight").on("click", ".paramBox ._close", removeProcessor);
			
			
			// 暴漏的接口
			return {
				// 初始化, 即默认添加两个参数
				init: function() {
					$(".paramBox").remove();
					// 默认为两个参数
					addParam();
					addParam();
				},
				
				// 获取最终参数string
				getData: getData,
				
				// 批量添加参数, 用来加载历史请求数据
				addParams: addParams
				/**
				paramsInfo : [{
					key: ""
					value: ""
					processorKeys: "a,b,c"
				}]
				*/
			};
			
			function addParams(paramsInfo) {
				// 清空参数信息
				$(".paramBox").remove();
				
				// 渲染参数信息
				for (var i = 0; i < paramsInfo.length; i++) {
					var id = addParam(paramsInfo[i].key, paramsInfo[i].value);
					var processorKeys = paramsInfo[i].processorKeys ? paramsInfo[i].processorKeys.split(",") : [];
					for (var j = 0; j < processorKeys.length; j++) {
						addProcessor(id, processorKeys[j]);
					}
				}
			}
			
			function addParam(key, value) {
				var index = data.push([]) - 1;
				validIds.push(index);
				
				var html = "<div class='row paramBox' value='" + index +"' id='paramBox_" + index + "'>"
						+ "<div class='form-group col-sm-4'>"
							+ "<input type='text' class='form-control' value='" + (key || "") + "'  placeholder='参数名'>"
						+ "</div>"
						+ "<div class='form-group col-sm-4'>"
							+ "<input type='text' class='form-control' value='" + (value || "") + "' placeholder='参数值'>"
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
				return index;
			}
			
			function addProcessor(id, key) {
				id = id || getParamId($(this));
				key = key || $(this).attr("value");
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
			
			// 根据某dom对象, 寻找所属参数对象的Id
			function getParamId(obj) {
				return +obj.parents(".paramBox").attr("value");
			}
			
			function getProcessorsHtml() {
				var processors = setting.processors;
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
			
			// 根据参数对象ID,获取参数名与参数值
			function getKeyValue(id) {
				var arr = $("#paramBox_" + id).find("input[type='text']");
				return {
					key: arr.eq(0).val(),
					value: arr.eq(1).val()
				};
			}
		}
	}
 	
 	/** -----------------------------------------接口管理------------------------------------------ **/
 	var interfaceManager = (function () {
 		/**
		 * 	接口
		 * 	{
		 * 		url: "",
		 * 		name: "",
		 * 		desc: "",
		 * 		params: [{
		 *			key: "",
		 *			desc: ""
		 *			constraint: ""
		 * 		}],
		 * 		result: [{
		 * 			key: "",
		 * 			desc: "",
		 * 		}]
		 * 	}
		 */
		var container = null,
			interfaceData = null,
			eventHandler = {};
		 
		return {
			init: init,
			search: function(callback) {
				$("#interfaceTestCase").hide();
				$("#interfaceSearch").show();
				query();
				
				var originDipatch = dispatch;
				dispatch = function() {
					var index = $(this).index(".interfaceEntity");
					var cur = interfaceData[index];
					callback(cur);
					dispatch = originDipatch;
					$("#interfaceTestCase").show();
					$("#interfaceSearch").hide();
				}
			}
		};
		
		/**
			利用一个引用函数中转一次事件绑定函数,
			然后通过修改此引用, 来改变事件绑定的指向
		*/
		function dispatch(callback) {
			callback.call(this);
		}
		
		function init() {
			$("#queryInterface").click(query);
			
			$("body").keydown(function(e){
				if (e.keyCode==13)
					query();
			});
			
			container = $("#interfaceBody");
			
			var modifier = getModifier();
			
			// 延迟策略双击阻止单击事件
			var timer = null;
			
			// 单击修改接口信息
			container.on("click", ".interfaceEntity", function() {
				dispatch.call(this, modifyInterface);
			});
			
			// 双击接口调用
			container.on("dblclick", ".interfaceEntity", function() {
				dispatch.call(this, loadInterface);
				return false;
			});	
			
			// 进入测试用例模式
			$("#toInterfaceTestCase").on("click", function() {
				$("#interfaceTestCase").show();
 				$("#interfaceSearch").hide();
			});
			
			// 将接口数据加载如请求器
			function loadInterface() {
				clearTimeout(timer);
				$('#interfaceManager').modal('hide');
				
				var index = $(this).index(".interfaceEntity");
				var cur = interfaceData[index];
				
				// 添加参数
				var params = [];
				for(var i in cur.params) {
					var curParam = cur.params[i];
					params.push({
						key: curParam.key,
						value: "",
						processorKeys: curParam.constraint
					});
				}
				
				// 请求器导入接口数据
				requestManager.initialize(cur.url, params);
			}
			
			// 修改接口相关信息
			function modifyInterface() {
				clearTimeout(timer);
				var that = $(this);
				timer = setTimeout(function(){
					var index = that.index(".interfaceEntity");
					modifier.start(index);
			    }, 200);
			}
		}
		
		function query() {
			var keyword = $("#queryKeyword").val();
			$.ajax({
				url: "${pageContext.request.contextPath}/my/interface/query",
				type: "post",
				data: {keyword: keyword},
				dataType: "json"
			}).done(function(data) {
				renderInterfaceData(data);
			});
		}
		
 		function renderInterfaceData(data) {
 			// 保存接口数据
 			interfaceData = data;
 			
 			container.html("");
 			
 			$(".interfaceEntity").popover("destroy");
 			
 			for (var i in data) {
 				var json = data[i];
	 			
 				var html = "<a class=\"col-sm-2 bg-danger interfaceBox interfaceEntity\""  
	    			+ "tabindex=\"1\" role=\"button\" data-toggle=\"popover\"" 
	    			+ "data-html=\"true\" data-placement=\"bottom\""
	    			+ "data-trigger=\"hover\" title=\"<code>" + json.url + "</code>\""
	    			+ "data-content=\""
	    				
	    			+ "<div class='table_container'>"
	    				+ "<div class='title'>接口描述:</div>"
	    				+ (json.desc ? json.desc : "无")
	    				+ "<br/><br/>"
	    			+ "</div>"
	    			
	    			+ "<div class='table_container bg-success'>"
		    			+ "<div class='title'>请求参数:</div>"
		    			+ "<table class='table table-condensed'>"
		    				+ getTableBody(json.params)
		    			+ "</table>"
	    			+ "</div>"
	    			
	    			+ "<div class='table_container bg-success'>"
	    				+ "<div class='title'>结果:</div>"
		    			+ "<table class='table table-condensed'>"
		    				+ getTableBody(json.results)
		    			+ "</table>"
	    			+ "</div>"
	    			+ "\">" + json.name + "</a>";
	    			
	 			container.append(html);
	 			
	 			$(".interfaceEntity").popover();
 			}
 		}
 		
 		function getTableBody(arr) {
 			if (!arr || !arr.length)
 				return "<tr><td>无</td><td>";
 				
 			var result = "";
 			for (var i = 0; i < arr.length; i++) {
 				var cur = arr[i];
 				result += "<tr><td>" + cur.key + "</td><td>" + cur.desc + "</td></tr>"
 			}
 			
 			return result;
 		}
 		
 		// 修改者
		function getModifier() {
			
 			var started = false;
 			
 			var container = $("#interfaceModify");
 			
 			var data = null;
 			
 			container.on("click", "#doModify", function() {
 				if (!started) return;
 				
 				var iName = $("#m_interfaceName").val();
 				
 				var iDesc = $("#m_interfaceDesc").val();
 				
 				data.name = iName || data.name;
 				data.desc = iDesc;
 				
 				$("#m_interfaceParam .param_v").each(function(index) {
 					data.params[index].desc = $(this).val();
 				});
 				
 				$("#m_interfaceResult .param_v").each(function(index) {
 					data.results[index].desc = $(this).val();
 				});
 				
 				$.ajax({
 					url: "${pageContext.request.contextPath}/my/interface/update",
 					data: {entityStr: JSON.stringify(data)},
 					type: "post",
 					success: function() {
 						close();
 					}
 				});
 			});
 			
			container.on("click", "#doClose", function() {
				if (!started) return;
				close();
			});
 			
			return {
 				start: start
 			};
			
 			function start(index) {
 				started = true;
 				container.show();
 				$("#interfaceSearch").hide();
 				data = interfaceData[index];
 				renderHtml();
 			}
 			
 			function close() {
 				data = null;
 				started = false;
 				container.html("");
 				container.hide();
 				$("#interfaceSearch").show();
 			}
 			
 			function renderHtml() {
 			  var html = "<div class='row' style='margin-left: -100px'>"
	        	  + "<div class='form-group col-md-12'>"
	        	  + "<code>" + data.url + "</code>"
	        	  + "</div>"
	        	  + "</div>"
	        	  + "<div class='row form-group' style='margin-left: -100px'>"
	        	  + "<div class='col-md-12'>"
	        	  + "<label>接口</label>"
	        	  + "</div>"
	        	  + "<div class='col-md-6 param_kv'>"
	        	  + "<input type='text' class='form-control' placeholder='名称' id='m_interfaceName' value='" + data.name + "'>"
	        	  + "</div>"
	        	  + "<div class='col-md-6 param_kv'>"
	        	  + "<input type='text' class='form-control' placeholder='描述' id='m_interfaceDesc' value='" + data.desc + "'>"
	        	  + "</div>"
	        	  + "</div>"
	        	  + "<div class='row form-group' style='margin-left: -100px' id='m_interfaceParam'>"
	        	  + "<div class='col-md-12'>"
	        	  + "<label>参数</label>"
	        	  + "</div>"
	        	  + getHtml(data.params)
	        	  + "</div>"
	        	  + "<div class='row form-group' style='margin-left: -100px' id='m_interfaceResult'>"
	        	  + "<div class='col-md-12'>"
	        	  + "<label>结果</label>"
	        	  + "</div>"
				  + getHtml(data.results)
	        	  + "</div>"
	        	  + "<div class='row' style='margin-left: -100px; margin-top: 30px;'>"
	        	  + "<div class='col-md-6'>"
	        	  + "<a href='javascript:;' class='btn btn-success' id='doModify' role='button'>&nbsp;&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;</a>"
	        	  + "&nbsp;&nbsp;&nbsp;"
	        	  + "<a href='javascript:;' class='btn btn-default' id='doClose' role='button'>&nbsp;&nbsp;返回&nbsp;&nbsp;</a>"
	        	  + "</div>"
	        	  + "</div>";
	        	  
 			 	container.html(html);
 			}
 			
 			function getHtml(arr) {
 				if (!arr || !arr.length)
 	 				return "<div class='col-md-12'>无</div>";
 	 				
 	 			var result = "";
 	 			for (var i = 0; i < arr.length; i++) {
 	 				var cur = arr[i];
 	 				result += "<div class='col-md-6 param_kv'>"
 		        	  + "<input type='text' class='form-control'  placeholder='名称' value='" + cur.key + "' readonly>"
 		        	  + "</div>"
 		        	  + "<div class='col-md-6 param_kv'>"
 		        	  + "<input type='text' class='form-control param_v' placeholder='描述' value='" + cur.desc + "'>"
 		        	  + "</div>";
 	 			}
 	 			return result;
 			}
		}
 	})();
 	
 	// 初始化
 	interfaceManager.init();
 	
 	/** -----------------------------------------测试相关------------------------------------------ **/
 	var testManager = (function(){
 		/**
 			查询用例
 			用例详情 -> 可以修改
 			新增用例
 		*/
 		return {
 			init: init
 		};
 		
 		function init() {
 			// 选择接口
 			$("#selectInterface").click(function() {
	 	 		interfaceManager.search(function(data) {
	 	 			alert(JSON.stringify(data));
	 	 		});
	 	 	});
 		}
 	})();
 	
 	// 初始化
 	testManager.init();
});
</script>
</body>
</html>

