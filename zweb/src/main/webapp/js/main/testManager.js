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
		
		var disabled = true;
		
		var index, 
			testCaseData;
		/**
		 * [{
		 * 		interfaceId: "",
		 * 		params: [{
		 * 			key: "",
		 * 			value: "",
		 * 			desc: ""
		 * 		}],
		 * 		expect: ""
		 * }]
		 */
		
		init();
		
		function init() {
			// 选择接口
			$("#selectInterface").click(function() {
				if (disabled) 
					return;
				
				interfaceManager.search(function(data) {
					alert(JSON.stringify(data));
				});
			});
			
			$("#testCase_nextStep").click(function() {});
			$("#testCase_complete").click(function() {});
			$("#testCase_ensure").click(function() {});
			$("#testCase_cancel").click(function() {});
		}
		
		function show(testId, interfaceId) {
			// 初始化一些数据
			disabled = false;
			index = -1;
			testCaseData = [];
			
			// 控制html的显示
			$("#testCaseAdd").show();
			$("#testCaseList").hide();
			$("#testCaseDetail").show();
			$("#testCase_expect").show();
			
			if (!testId) {
				$("#test_btnGroup_add").show();
				return;
			}
			
			// 如果有testId，说明是修改数据，需要请求获取相关数据
			$("#test_btnGroup_edit").show();
			loadData(testId, interfaceId);
		}
		
		function loadData(testId, interfaceId) {
			
		}
		
		function addParam(name, desc, value) {
			var html = '<div class="form-group">' + 
			    '<div class="input-group">' + 
			      '<div class="input-group-addon" style="width: initial;">' + name + '</div>' +
			      	'<input type="text" class="form-control" placeholder="用户名" value="' + (value || '') + '">' +
			      	'<div class="input-group-addon" style="width: initial;">' + desc + '</div>' +
			      '</div>' +
			    '</div>';
			$("#testCase_param").append(html);
		}
		/*
		<div class="form-group col-sm-12" style="min-height: 50px;">
			<h5>接口：</h5>
				<button type="button" class="btn btn-primary" id="selectInterface">&nbsp;选择接口&nbsp;</button>
		</div>
		<div class="form-group col-sm-12" id="testCase_param">
			<h5>参数：</h5>
			<div class="form-group">
			    <div class="input-group">
			      <div class="input-group-addon" style="width: initial;">username</div>
			      <input type="text" class="form-control" id="exampleInputAmount" placeholder="用户名">
			      <div class="input-group-addon" style="width: initial;">非空，字符串</div>
			    </div>
			</div>
		</div>
		<div class="form-group col-sm-12">
			<h5>期望：</h5>
			<div class="form-group">
		      <input type="text" class="form-control" id="exampleInputAmount" placeholder="请输入你的期望表达式">
			</div>
		</div>
		<div class="form-group col-sm-12">
			<button type="button" class="btn btn-info" id="testCase_nextStep">&nbsp;下一步&nbsp;</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-success" id="testCase_complete">&nbsp;完成&nbsp;</button>
		</div>
		*/
		
		return { 
			show: show 
		};
	}
})();