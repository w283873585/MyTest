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
	var basePath = null,
		container = null,
		interfaceData = null;
	 
	return {
		init: init,
		search: function(callback) {
			$("#interfaceTestCase").hide();
			$("#interfaceSearch").show();
			$("#toInterfaceTestCase").hide();
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
	
	function init(path) {
		
		basePath = path;
		
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
			url: basePath + "/my/interface/query",
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
				url: basePath + "/my/interface/update",
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