var testManager = (function(){
	/**
		查询用例
		用例详情 -> 可以修改
		新增用例
	*/
	var CONSTANT = {
		separator: " ",
		expSeparator: "\n",
		paramSeparator: ","
	};
	
	var basePath = null;
	var testCases = null;
	/**
	 * testCases = [{
	 * 		id: "",
	 * 		name: "",
	 * 		expression: "",
	 * }]
	 */
	var modifier = getModifer();
	var detailManager = getDetailManager();
	
	return {
		init: init
	};
	
	function init(path, clients) {
		basePath = path;
		
		modifier.init();
		detailManager.init();
		renderClients(clients)
		
		// 新增用例
		$("#addTestCase").click(function() {
			modifier.show();
		});
		
		// 查询测试用例
		$("#queryTestCase").click(queryTestCase);
		
		// 测试用例详情
		$("#testCaseBody").on("click", ".testCaseBox", function() {
			var index = $(this).index("#testCaseBody .testCaseBox");
			detailManager.show(testCases[index]);
		});
	}
	
	function queryTestCase() {
		$.ajax({
			url: basePath + "/my/testCase/query",
			type: "post",
			success: function(data) {
				testCases = $.parseJSON(data);
				renderTestCaseList();
			}
		});
	}
	
	function renderTestCaseList() {
		var html = "";
		for (var i in testCases) {
			html += '<a class="col-sm-2 bg-danger testCaseBox" tabindex="1" role="button" title="">' + testCases[i].name + '</a>'
		}
		$("#testCaseBody").html(html);
	}
	
	function renderClients(clients) {
		var clientsHtml = "";
		for (var i = clients.length - 1; i >= 0; i--) {
			clientsHtml += "<option value='" + clients[i] + "'>" + clients[i] + "</option>";
		}
		$("#testCase_client select").html(clientsHtml);
	}
	
	function getDetailManager() {
		var disabled = true;
		var testCase = null;
		
		return {
			init: init,
			show: show
		};
		
		function show(val) {
			disabled = false;
			/**
			 * 在修改具体流程时, 提供给modifier的入口
			 * 因为在进入修改, 当前状态并未被清空, 只需要展示指定窗口就行
			 */
			if (val == undefined) {
				$("#testCaseDetail").show();
				return;
			}
			
			testCase = val;
			$("#testCaseDetail").show();
			$("#testCaseList").hide();
			$("#testCase_name input").val(testCase.name);
			$("#testCase_host input").val(testCase.host);
			$("#testCase_client select").val(testCase.client);
			
			var exps = testCase.expression.split(CONSTANT.expSeparator);
			var html = "";
			for (var i in exps)
				html += '<a class="col-sm-2 bg-danger testCaseBox" tabindex="1" role="button" title="">天下之大</a>'
			$(".testCase_process_body").html(html);
		}
		
		function init() {
			// 流程修改
			$("#testCase_process").on("click", ".testCaseBox", function() {
				if (disabled) return;
				
				// 临时退出
				exit();
				
				// 进入修改
				var index = $(this).index("#testCase_process .testCaseBox");
				modifier.show(testCase, index);
			});
			
			// 测试用例执行
			$("#testCase_execute").click(function() {
				if (disabled) return;
				
				var data = {
					id: testCase.id,
					name: $("#testCase_name input").val(),
					host: $("#testCase_host input").val(),
					client: $("#testCase_client select").val(),
				};
				
				if (!data.host) {
					alert("host can not be empty");
					return;
				}
				
				// 发送执行请求
				$.ajax({
					url:　basePath + "/my/testCase/execute",
					type: "post",
					data: data,
				}).done(function(data) {
					alert(data);
				});
			});
			
			// 返回列表
			$("#testCase_back").click(function() {
				if (disabled) return;
				
				// 完全退出
				exit(true);
			});
		}
		
		function exit(entire) {
			disabled = true;
			$("#testCaseDetail").hide();
			
			if (entire) {
				testCase = null;
				$("#testCaseList").show();
				$("#testCase_process_body").html("");
				$("#testCase_name input").val("");
			}
		}
	}
	
	function getModifer() {
		var disabled = true;
		
		// 接口缓存
		var interfaceCache = {};
		
		// testCase相关数据
		var id,
			index,
			beModified,
			testCaseData;
		/**
		 * testCaseData = [{
		 * 		interfaceId: "",
		 * 		params: [],
		 * 		expect: ""
		 * }]
		 */
		 
		/**
			暴露的接口
		*/
		return {
			init: init,
			show: show
		};
		
		function init() {
			// 选择接口
			$("#selectInterface").click(function() {
				if (disabled) return;	
				
				interfaceManager.search(function(data) {
					cacheInterface(data);
					inject(data);
				});
			});
			
			/**
			 * 新增流程
			 */
			$("#testCase_nextStep").click(function() {
				if (disabled) return;
				
				// 下一步
				_goto();
			});
			$("#testCase_complete").click(function() {
				if (disabled) return;
				
				persistTestCase(function (data) {
					if (data) alert("success"); 
					$("#testCaseList").show();
					exit();
				});
			});
			
			/**
			 * 修改流程
			 */
			$("#testCase_ensure").click(function() {
				persistTestCase(function(data) {
					beModified.expression = data.exp;
					exit();
					detailManager.show();
				});
			});
			$("#testCase_cancel").click(function() {
				exit();
				detailManager.show();
			});
		}
		
		function show(testCase, idx) {
			/**
			 * testCase: {
			 * 		id: "",
			 *		name: "",
			 *		expression: ""
			 * }
			 */
			// 初始化一些数据
			disabled = false;
			id = null;
			index = 0;
			beModified = null;
			testCaseData = [];
			
			// 控制html的显示
			$("#testCaseList").hide();
			$("#testCaseAdd").show();
			
			if (!testCase) {
				// 新增
				$("#test_btnGroup_add").show();
				$("#test_btnGroup_edit").hide();
			} else {
				// 修改
				$("#test_btnGroup_add").hide();
				$("#test_btnGroup_edit").show();
				loadData(testCase, idx);
			}
			
			// 跳转到指定流程
			_goto(idx || 0);
		}
		
		function exit() {
			disabled = true;
			$("#testCaseAdd").hide();
		}
		
		function persistTestCase(callback) {
			// 默认执行下一步
			_goto();
			
			var exp = getExpression();
			if (!exp) {
				callback();
				return;
			}
			
			var data = {exp: exp};
			if (id)	data.id = id;
			
			// 持久化测试用例
			$.ajax({
				url: basePath + "/my/testCase/update",
				type: "post",
				data: data
			}).done(function() {
				callback(data);
			});
		}
		
		// 用于修改时, 加载数据
		function loadData(testCase, idx) {
			id = testCase.id;
			beModified = testCase;
			var expressions = testCase.expression.split(CONSTANT.expSeparator);
			for (var i in expressions)
				saveTestCaseData(expressions[i]);
		}
		
		/**
			职责一: 跳转到下一页, 清除当前数据.
			职责二: 跳转到指定页, 清除当页数据, 注入数据
			职责三: 新开窗口时, 跳转至第一页, 会有清除页面效果
		*/
		function _goto(idx) {
			// 缓存在本地
			if (idx == undefined)
				saveTestCaseData();
			else
				index = idx;
			
			// 清除相关数据
			$("#selectInterface").val("");
			$("#selectInterface").html("选择接口");
			$("#testCase_param").hide();
			$("#testCase_param .form-group").remove();
			$("#testCase_expect input").val("");
			
			var cur;
			if (cur = testCaseData[index]) {
				// 获取接口数据
				getInterface(cur.interfaceId, function(data) {
					inject(data, cur.params, cur.expect);
				});
			}
		}
		
		/** 
		 * 通过id获取接口相关数据
		 * 首先从缓存中拿, 如果不存在则通过ajax异步拿
		 */
		function getInterface(id, callback) {
			if (interfaceCache[id])
				callback(interfaceCache[id])
			else {
				// ajax to get interface data
				$.ajax({
					url: basePath + "/my/interface/" + id,
					type: "get",
					success: function(data) {
						cacheInterface(data);
						callback(data);
					}
				});
			}
		}
		
		// 保存测试相关数据
		function saveTestCaseData(expression) {
			var interfaceId, params, expectExp;
			if (expression) {
				// 修改时, 手动注入
				var curExps = expression.split(CONSTANT.separator);
				interfaceId = curExps[0];
				params = curExps[1].split(CONSTANT.paramSeparator);
				expectExp = curExps[2];
			} else {
				// 通过页面获取
				interfaceId = $("#selectInterface").val();
				if (interfaceId) {
					params = [];
					$("#testCase_param input").each(function() {
						params.push($(this).val());
					});
					expectExp = $("#testCase_expect input").val();
				}
			}
			
			if (interfaceId) {
				testCaseData[index++] = {
					interfaceId: interfaceId,
					params: params,
					expect: expectExp
				};
			}
		}
		
		// 将对应数据注入到html页面, 并缓存相关数据
		function inject(data, values, expect) {
			$("#selectInterface").html(data.name);
			$("#selectInterface").val(data.id);
			renderParams(data.params, values);
			if (expect) $("#testCase_expect input").val(expect);
		}
		
		// 缓存接口数据
		function cacheInterface(data) {
			if (!interfaceCache[data.id])
				interfaceCache[data.id] = data;
		}
		
		// 根据参数信息, 渲染html对应的页面
		function renderParams(params, values) {
			var testCase_paramContainer = $("#testCase_param");
			testCase_paramContainer.find(".form-group").remove();
			
			var html = "";
			for (var i in params) {
				var cur = params[i];
				html += '<div class="form-group">' + 
				    '<div class="input-group">' + 
				      '<div class="input-group-addon" style="text-align: left;width: initial;min-width:150px">' + cur.key + '</div>' +
				      	'<input type="text" class="form-control" value="' + (values && values[i] || '') + '">' +
				      	'<div class="input-group-addon" style="width: initial;">' + cur.desc + '</div>' +
				      '</div>' +
				    '</div>'
			}
			
			testCase_paramContainer.append(html);
			testCase_paramContainer.show();
		}
		
		function getExpression() {
			var result = [];
			for (var i in testCaseData) {
				var cur = testCaseData[i];
				var arr = [];
				arr.push(cur.interfaceId);
				arr.push(cur.params.join(CONSTANT.paramSeparator));
				arr.push(cur.expect);
				result.push(arr.join(CONSTANT.separator));
			}
			return result.join(CONSTANT.expSeparator);
		}
	}
})();