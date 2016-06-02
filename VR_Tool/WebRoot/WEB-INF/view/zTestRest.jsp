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
    	</style>
    </head>
    <body>
    <div class="container">
    	<div class="h3">测试参数</div>
    	<div class="highlight">
	    	<div class="row">
	    		<div class="form-group col-sm-4">
		    		<select class="form-control" id="interfaceKey">
					  <option value="">手动配置</option>
					</select>
				</div>
				<div class="form-group col-sm-4">
					<input type="text" class="form-control"  placeholder="输出接口名称" id="inteName">
				</div>
				<div class="form-group col-sm-2">
					<a href="javascript:;" class="btn btn-warning canDisabled" role="button" id="addParam">添加参数</a>
				</div>
				<div class="form-group col-sm-2">
		    		<select class="form-control" id="interfaceUrl">
		    		  <option value="192.168.200.148:8080">default</option>
					  <option value="127.0.0.1:8080">local</option>
					  <!-- <option value="120.76.79.49:38080">online</option> -->
					</select>
				</div>
	    	</div>
	    	<div class="row paramBox">
				<div class="form-group col-sm-4">
					<input type="text" class="form-control canDisabled"  placeholder="参数名">
				</div>
				<div class="form-group col-sm-4">
					<input type="text" class="form-control"  placeholder="参数值">
				</div>
				<div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox">	rsa</label></div>
		        <div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox">	url</label></div>
		        <div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox">	md5</label></div>
	    	</div>
	    	<div class="row paramBox">
				<div class="form-group col-sm-4">
					<input type="text" class="form-control canDisabled"  placeholder="参数名">
				</div>
				<div class="form-group col-sm-4">
					<input type="text" class="form-control"  placeholder="参数值">
				</div>
				<div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox">	rsa </label></div>
		        <div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox">	url</label></div>
		        <div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox">	md5</label></div>
	    	</div>
	    	<div class="row" id="sendContainer">
	    		<div class="form-group col-sm-8">
					<a href="javascript:;" class="btn btn-success" id="submit" role="button">发起请求</a>
	    		</div>
	    		<div class="form-group col-sm-2">
					<label><input class="canDisabled" id="isDev" type="checkbox">dev模式</label>
	    		</div>
	    		<div class="form-group col-sm-2 text-right">
					<a href="javascript:;" class="btn btn-info canDisabled" role="button" data-toggle="modal" data-target="#myModal">高级设置</a>
				</div>
	    	</div>
    	</div>
    	<div class="h3">结果</div>
    	<div class="highlight result">
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
        		<input type="text" class="form-control"  placeholder="输出接口名称" id="manualSetting">
        	</div>
        	<div class="clearfix"></div>
        	<div class="form-group col-sm-8">
	        	<div class="checkbox">
				  <label>
				    <input type="checkbox" value="">
				    	开启自由模式
				  </label>
				</div>
			</div>
        	<div class="clearfix"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div>
    <script type="text/javascript">
    	function utils(cacheInfo) {
    		var isManual = true;
    		var isBusy = false;
    		if (cacheInfo) {
    			var that = $("#interfaceKey");
    			for (var key in cacheInfo) {
    				that.append("<option value='" + key + "'>" + key + "</option>");
    			}
    		}
    		function addParam(key, needEncrypt, needEncode) {
    			var html = '<div class="row paramBox">' +
						'<div class="form-group col-sm-4">' +
							'<input type="text" class="form-control canDisabled" value="' + (key || '') + '"' + (key != undefined ? 'disabled' : '') + 'placeholder="参数名">' +
						'</div>' +
						'<div class="form-group col-sm-4">' +
							'<input type="text" class="form-control"  placeholder="参数值">' +
						'</div>' + 
						'<div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox"' + (needEncrypt ? 'checked="checked"' : '')  + '">rsa密 </label></div>' +
		        		'<div class="form-group col-sm-1"><label><input class="canDisabled" type="checkbox" ' + (needEncode ? 'checked="checked"' : '')  + '">url</label></div>' +
		        		'<div class="form-group col-sm-1"><label><input type="checkbox" ' + (needEncode ? 'checked="checked"' : '')  + '">md5</label></div>' +
			    	'</div>';
    			$(html).insertBefore("#sendContainer");
    		}
    		function render(name) {
   				$(".paramBox").remove();
    			if (!name) {
    				isManual = true;
    				addParam();
    				addParam();
    				return;
    			}
    			var obj = cacheInfo[name];
    			if (typeof obj == 'string') {
    				obj = eval("(" + obj + ")");
    			}
    			for (var key in obj) {
    				addParam(obj[key].value, obj[key].needEncrypt, obj[key].needEncode);
    			}
    			$("input.canDisabled").attr("disabled", true);
    			isManual = false;
    		}
    		function submit() {
    			isBusy = true;
    			var data = getData();
    			$(".result").html("等待中...");
    			$.ajax({
    				url: "${pageContext.request.contextPath}/my/send",
    				type: "post",
    				data: data,
    				success: function(str) {
    					$(".result").html("");
    					$(".result").html(str);
    					isBusy = false;
    				},
    				error: function(a, b, c, d) {
    					$(".result").html("ajax请求出现了一点小错误。。。");
    					isBusy = false;
    				}
    			});
    		}
    		function getData() {
    			var result = {};
    			var keyWord = $("#interfaceKey").val() || $("#inteName").val();
    			if (!keyWord) {
    				return "";
    			}
    			// 去除首部的/
    			keyWord = keyWord.replace(/^\//, "");
    			result.keyWord = keyWord;
    			result.interfaceUrl = $("#interfaceUrl").val();
    			result.isDev = $("#isDev").is(":checked");
    			
    			ret = "[";
    			$(".paramBox").each(function() {
    				var arr = $(this).find("input");
    				var paramName = arr.eq(0).val();
    				var paramValue = arr.eq(1).val();
    				if (paramName === '' || paramValue == '') {
    					return true;
    				}
    				// js md5加密
    				if (arr.eq(4).is(":checked")) {
    					paramValue = md5(paramValue);
    				}
    				ret += '{"key":"' 
	    				+ paramName + '","value":"' 
	    				+ paramValue + '","needEncrypt": ' 
	    				+ arr.eq(2).is(":checked") + ',"needEncode": '
	    				+ arr.eq(3).is(":checked") + '},';
    			});
    			ret = ret.replace(/,$/, "");
    			ret += "]";
    			result.paramsInfo = ret;
    			return result;
    		}
    		// 事件注册
    		$("#addParam").click(function() {
    			if (isManual) { addParam(); }
    		});
    		$("#submit").click(function() {
    			if (!isBusy) submit();
    		});
    		$("#interfaceKey").change(function() {
	    		render(this.value);
	    	});
    		
    		function toJsonFormatHtml(str) {
    			str = str.replace(/<br\/?>/ig, "");
    			var curX = 0;
    			var arr = str.split("");
    			for (var i = 0; i < arr.length; i++) {
    				if (arr[i] == "{") {
    					curX++;
    					arr[i] = getPadding() + "{";
    				}
    				if (arr[i] == "}") {
    					curX--;
    					arr[i] =  getPadding() + "}<br/><br/>";
    				}
    			}
    			return arr.join("");
    			
    			function getPadding() {
    				var result = "<BR/>";
    				var space = "<div class='form-group col-sm-1'></div>";
    				for (var i = 0; i < curX; i++) {
    					result += space;
    				}
    				return result;
    			}
    		}
    	}
    	utils(${params});
	</script>
    </body>
</html>

