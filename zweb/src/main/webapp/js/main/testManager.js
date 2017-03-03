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
	
	function removeTestCase(testCase) {
		testCases.splice(testCases.indexOf(testCase), 1);
		renderTestCaseList();
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
			$("#testCase_globalExp input").val(testCase.globalExp);
			$("#testCase_client select").val(testCase.client);
			$(".testCase_process_result").html("");
			
			var exps = testCase.expression.split(CONSTANT.expSeparator);
			var i = 0;
			var html = "";
			recursion(exps[i]);
			
			/**
			 * 递归实现嵌套回调 和 异步居后 
			 * 避免异步时步骤失序
			 */ 
			function recursion(data) {
				if (!data) {
					$(".testCase_process_body").html(html);
					return;
				}
				
				var id = data.split(CONSTANT.separator)[0];
				if (modifier.getInterface(id, function(data) {
					html += '<a class="col-sm-2 bg-danger testCaseBox" tabindex="1" role="button" title="">' + data.name + '</a>'
					recursion(exps[++i]);
				}));
			}
		}
		
		function init() {
			// 流程修改
			$("#testCase_process").on("click", ".testCaseBox", function() {
				if (disabled) return;
				
				// 临时退出
				exit();
				
				// 进入修改
				var index = $(this).index("#testCase_process .testCaseBox");
				obtainDataFromPage()
				modifier.show(testCase, index);
			});
			
			// 测试用例执行
			$("#testCase_execute").click(function() {
				if (disabled) return;
				
				obtainDataFromPage();
				if (!testCase.host) {
					alert("host can not be empty");
					return;
				}
				
				$(".testCase_process_result").html("等待中...");
				disabled = true;
				// 发送执行请求
				$.ajax({
					url:　basePath + "/my/testCase/execute",
					type: "post",
					data: testCase,
				}).done(function(data) {
					renderResult($.parseJSON(data));
					disabled = false;
				});
			});
			
			// 返回列表
			$("#testCase_back").click(function() {
				if (disabled) return;
				
				// 完全退出
				exit(true);
			});
			
			$("#testCase_del").click(function() {
				if (disabled) return;
				
				if (confirm("确认删除？")) {
					$.ajax({
						type: "post",
						url: basePath + "/my/testCase/del",
						data: {id: testCase.id},
						success: function() {
							removeTestCase(testCase);
							// 完全退出
							exit(true);
						}
					})
				}
			});
			
			
			// 返回列表
			$("#testCaseDetail").on("click", ".testCase_process_result .curResult a", function() {
				if (disabled) return;
				
				$(this).next().toggle();
			});
			
			function renderResult(data) {
				var index = 0;
				var className = ["bg-primary", "bg-success", "bg-info", "bg-warning", "bg-danger"];
				
				var html = "";
				for (var i in data) {
					var cur = data[i];
					html += "<div class='curResult " + nextClass() + "'>" + 
							getHtml(cur) + 
						"</div>";
				}
				$(".testCase_process_result").html(html);
				
				function getHtml(cur) {
					var html = "<span style='color: black; font-weight: bold;'>" + cur.name + "</span>&nbsp;:&nbsp;&nbsp;&nbsp;" + "<span>" + cur.msg + "</span><br/>"
					+ "<a>参数详情<br/></a><div style='display:none'>" + toJsonHtml(cur.params) + "</div>"
					+ "<a>结果详情<br/></a><div style='display:none'>" + toJsonHtml(cur.result) + "</div>";
					return html;
				}
				
				function nextClass() {
					index++;
					if (index == 5) index = 0;
					return className[index];
				}
			}
			
			function obtainDataFromPage() {
				testCase.name = $("#testCase_name input").val();
				testCase.host = $("#testCase_host input").val();
				testCase.client = $("#testCase_client select").val();
				testCase.globalExp = $("#testCase_globalExp input").val();
			}
		}
		
		function exit(entire) {
			disabled = true;
			$("#testCaseDetail").hide();
			
			if (entire) {
				testCase = null;
				$("#testCaseList").show();
				$("#testCase_process_body").html("");
				$("#testCase_name input").val("");
				$("#testCase_globalExp input").val("");
			}
		}
	}
	
	function getModifer() {
		var disabled = true,
			hasPopover = false;
		
		// 接口缓存
		var interfaceCache = {};
		
		var index = 0,
			// 更改状态下缓存的数据
			beModified,
			// 一定格式的testCase数据
			testCaseData = [];
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
			show: show,
			getInterface: getInterface
		};
		
		function init() {
			// 选择接口
			$("#selectInterface").click(function() {
				if (disabled) return;
				
				if (hasPopover) {
					$("#selectInterface").popover("toggle");
					return;
				}
				
				interfaceManager.search(function(data) {
					cacheInterface(data);
					inject(data);
				});
			});
			
			// 双击选择接口
			$("#selectInterface").dblclick(function() {
				if (disabled) return;	
				
				if (hasPopover)
					$("#selectInterface").popover("hide");
				
				interfaceManager.search(function(data) {
					cacheInterface(data);
					inject(data);
				});
			});
			
			$("#testCase_nextStep").click(function() {
				if (disabled) return;
				
				// 下一步
				_goto(true);
			});
			
			$("#testCase_lastStep").click(function() {
				if (disabled) return;
				
				// 上一步
				_goto();
			});
			
			$("#testCase_complete").click(function() {
				if (disabled) return;
				
				persistTestCase(function (data) {
					if (beModified) {
						beModified.expression = data.exp;
						detailManager.show(beModified);
						exit();
					} else {
						if (data) alert("success"); 
						$("#testCaseList").show();
						exit();
					}
				});
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
			disabled = false;
			
			// 控制html的显示
			$("#testCaseList").hide();
			$("#testCaseAdd").show();
			
			beModified = testCase;
			if (beModified) loadData();
			
			// 跳转到指定流程
			toPage(idx || 0);
		}
		
		function exit() {
			disabled = true;
			index = 0;
			beModified = null;
			testCaseData = [];
			$("#testCaseAdd").hide();
		}
		
		function persistTestCase(callback) {
			// 保存当前数据, 并且清除页面数据
			saveInternalData(true);
			clear();
			
			var exp = getExpression();
			if (!exp) {
				callback();
				return;
			}
			
			var data = {exp: exp};
			if (beModified)	data.id = beModified.id;
			
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
		function loadData() {
			var expressions = beModified.expression.split(CONSTANT.expSeparator);
			for (var i in expressions)
				saveInternalData(expressions[i]);
		}
		
		/**
			跳转上一页和跳转下一页
		*/
		function _goto(next) {
			saveInternalData(next);
			clear();
			toPage(index);
		}
		
		// 清除相关页面数据
		function clear() {
			$("#selectInterface").val("");
			$("#selectInterface").html("选择接口");
			clearPopver($("#selectInterface"));
			$("#testCase_param").hide();
			$("#testCase_param .form-group").remove();
			$("#testCase_expect input").val("");	
		}
		
		// 跳转到指定流程, 即将某流程的数据取出来显示
		function toPage(idx) {
			index = idx;
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
		function saveInternalData(expressionOrNext) {
			var interfaceId, params, expectExp;
			if (expressionOrNext && expressionOrNext != true) {
				// 修改时, 手动注入
				var curExps = expressionOrNext.split(CONSTANT.separator);
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
				testCaseData[index] = {
					interfaceId: interfaceId,
					params: params,
					expect: expectExp
				};
				if (expressionOrNext) 
					index++;	
			}
			
			if (!expressionOrNext && testCaseData[index - 1])
				index--;
		}
		
		// 将对应数据注入到html页面, 并缓存相关数据
		function inject(data, values, expect) {
			$("#selectInterface").val(data.id);
			renderPopover($("#selectInterface") , data);
			renderParams(data.params, values);
			if (expect) $("#testCase_expect input").val(expect);
		}
		
		function renderPopover(target, data) {
			hasPopover = true;
			target.attr("tabindex", 100);
			target.attr("role", "button");
			target.attr("data-toggle", "popover");
			target.attr("data-html", "true");
			target.attr("data-placement", "left");
			target.attr("data-trigger", "manual");
			target.attr("data-container", "#interfaceManager");
			target.attr("title", "<code>" + data.url + "</code>");
			
			var html = "<div class='table_container'>"
				+ "<div class='title'>接口描述:</div>"
				+ (data.desc ? data.desc : "无")
				+ "<br/><br/>"
			+ "</div>"
			
			+ "<div class='table_container bg-success'>"
    			+ "<div class='title'>请求参数:</div>"
    			+ "<table class='table table-condensed'>"
    				+ getTableBody(data.params)
    			+ "</table>"
			+ "</div>"
			
			+ "<div class='table_container bg-success'>"
				+ "<div class='title'>结果:</div>"
    			+ "<table class='table table-condensed'>"
    				+ getTableBody(data.results)
    			+ "</table>"
			+ "</div>";
			
			target.attr("data-content", html);
			target.html(data.name);		
			
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
		}
		
		function clearPopver(target) {
			hasPopover = false;
			target.removeAttr("tabindex");
			target.removeAttr("role");
			target.removeAttr("data-toggle");
			target.removeAttr("data-html");
			target.removeAttr("data-placement");
			target.removeAttr("data-trigger");
			target.removeAttr("title");
			target.removeAttr("data-content");
			target.removeAttr("data-container");
			target.popover("destroy");
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