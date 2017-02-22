var testManager = (function(){
	/**
		查询用例
		用例详情 -> 可以修改
		新增用例
	*/
	var basePath = null;
	var modifier = getModifer();
	
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
	}
	
	function queryTestCase() {}
	
	function renderTestCase(data) {}
	
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
				
				// 持久化测试用例
			});
			
			/**
			 * 修改流程
			 */
			$("#testCase_ensure").click(function() {});
			$("#testCase_cancel").click(function() {});
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
			id = null,
			index = 0;
			testCaseData = [];
			
			// 控制html的显示
			$("#testCaseList").hide();
			$("#testCaseDetail").hide();
			$("#testCaseAdd").show();
			
			// 新增
			if (!testCase) {
				$("#test_btnGroup_add").show();
				return;
			}
			
			// 修改
			$("#test_btnGroup_edit").show();
			loadData(testCase, idx);
		}
		
		// 用于修改时, 加载数据
		function loadData(testCase, idx) {
			id = testCase.id;
			var expressions = testCase.split("\n");
			for (var i in expressions)
				saveTestCaseData(expressions[i]);
			
			// 跳转到指定流程
			_goto(idx);
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
			}
		}
		
		// 将对应数据注入到html页面, 并缓存相关数据
		function inject(data, values, expect) {
			$("#selectInterface").html(data.name);
			$("#selectInterface").val(data.id);
			
			cacheInterfaceData(data);
			addParams(data.params, values);
			
			if (expect) $("#testCase_expect input").val(expect);
		}
		
		// 缓存接口数据
		function cacheInterfaceData(data) {
			if (!interfaceCache[data.id])
				interfaceCache[data.id] = data;
		}
		
		// 清除页面上的数据, 主要用再一次的流程设置
		function _goto(idx) {
			// 缓存在本地
			if (!idx)
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
					inject(data.params, cur.params, cur.expectExp);
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
	}
})();