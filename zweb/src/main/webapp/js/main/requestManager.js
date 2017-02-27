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
					try {
						var result = $.parseJSON(res.result);
						$(".result").html(toJsonHtml($.parseJSON(res.result)));
					} catch (e) {
						$(".result").html(res.result);
					}
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