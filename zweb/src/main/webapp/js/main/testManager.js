var testManager = (function(){
	/**
		查询用例
		用例详情 -> 可以修改
		新增用例
	*/
	var basePath = null;
	var testCases = null;
	/**
	 * [{
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
	
	function init(path) {
		basePath = path;
		
		$("#addTestCase").click(function() {
			modifier.show();
		});
		
		// 查询测试用例
		$("#queryTestCase").click(queryTestCase);
		
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
				renderTestCase($.parseJSON(data));
			}
		});
	}
	
	function renderTestCase(data) {
		var html = "";
		
		for (var i in data) {
			html += '<a class="col-sm-2 bg-danger testCaseBox" tabindex="1" role="button" title="">' + data[i].name + '</a>'
		}
		
		testCases = data;
		$("#testCaseBody").html(html);
	}
	
	
	function getDetailManager() {
		var disabled = true;
		var testCase = null;
		
		init();
		
		return {
			show: show
		};
		
		function show(val) {
			disabled = false;
			
			if (val == undefined) {
				$("#testCaseDetail").show();
				return;
			}
			testCase = val;
			
			$("#testCaseDetail").show();
			$("#testCaseList").hide();
			$("#testCase_name input").val(testCase.name);
			
			var exps = testCase.expression.split("\n");
			var html = "";
			for (var i in exps)
				html += '<a class="col-sm-2 bg-danger testCaseBox" tabindex="1" role="button" title="">天下之大</a>'
			
			$(".testCase_process_body").html(html);
		}
		
		function init() {
			$("#testCase_process").on("click", ".testCaseBox", function() {
				if (disabled) return;
				
				var index = $(this).index("#testCase_process .testCaseBox");
				modifier.show(testCase, index);
			});
			
			$("#testCase_execute").click(function() {
				if (disabled) return;
				
				
			});
			
			$("#testCase_back").click(function() {
				if (disabled) return;
				
				disabled = true;
				testCase = null;
				$("#testCaseDetail").hide();
				$("#testCaseList").show();
				
				$("#testCase_process_body").html("");
				$("#testCase_name input").val("");
			});
		}
	}
	
	
	function getModifer() {
		var DEFAULT = {
			separator: " "
		};
		var disabled = true;
		
		// 接口缓存
		var interfaceCache = {};
		
		// testCase相关数据
		var id,
			index,
			beModifiedTestCase,
			testCaseData;
		/**
		 * testCaseData: [{
		 * 		interfaceId: "",
		 * 		params: [],
		 * 		expect: ""
		 * }]
		 */
		
		init();
		
		/**
			暴露的接口
		*/
		return {
			show: show
		};
		
		
		function init() {
			// 选择接口
			$("#selectInterface").click(function() {
				if (disabled) return;	
				
				interfaceManager.search(function(data) {
					cacheInterfaceData(data);
					inject(data);
				});
			});
			
			// 新增流程
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
					$("#testCaseAdd").hide();
					disabled = true;
				});
			});
			
			/**
			 * 修改流程
			 */
			$("#testCase_ensure").click(function() {
				persistTestCase(function(data) {
					beModifiedTestCase.expression = data.exp;
					$("#testCaseAdd").hide();
					disabled = true;
					detailManager.show();
				});
			});
			$("#testCase_cancel").click(function() {
				$("#testCaseAdd").hide();
				detailManager.show();
			});
		}
		
		function persistTestCase(callback) {
			// 默认执行下一步
			_goto();
			
			var data = {exp: getExpression()};
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
			beModifiedTestCase = null;
			testCaseData = [];
			
			// 控制html的显示
			$("#testCaseList").hide();
			$("#testCaseDetail").hide();
			$("#testCaseAdd").show();
			
			// 新增
			if (!testCase) {
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
		
		// 用于修改时, 加载数据
		function loadData(testCase, idx) {
			id = testCase.id;
			beModifiedTestCase = testCase;
			var expressions = testCase.expression.split("\n");
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
				getInterfaceData(cur.interfaceId, function(data) {
					inject(data, cur.params, cur.expect);
				});
			}
		}
		
		/** 
		 * 通过id获取接口相关数据
		 * 首先从缓存中拿, 如果不存在则通过ajax异步拿
		 */
		function getInterfaceData(id, callback) {
			if (interfaceCache[id])
				callback(interfaceCache[id])
			else {
				// ajax to get interface data
				$.ajax({
					url: basePath + "/my/interface/" + id,
					type: "get",
					success: function(data) {
						cacheInterfaceData(data);
						callback(data);
					}
				});
			}
		}
		
		// 保存测试相关数据
		function saveTestCaseData(expression) {
			var interfaceId, params, expectExp;
			if (expression) {
				//  修改时, 手动注入
				var curExps = expression.split(DEFAULT.separator);
				interfaceId = curExps[0];
				params = curExps[1].split(",");
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
			addParams(data.params, values);
			if (expect) $("#testCase_expect input").val(expect);
		}
		
		// 缓存接口数据
		function cacheInterfaceData(data) {
			if (!interfaceCache[data.id])
				interfaceCache[data.id] = data;
		}
		
		// 根据参数信息, 渲染html对应的页面
		function addParams(params, values) {
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
				arr.push(cur.params.join(","));
				arr.push(cur.expect);
				result.push(arr.join(DEFAULT.separator));
			}
			return result.join("\n");
		}
	}
})();