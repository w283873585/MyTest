<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
        <title>VRShow - Command</title>
        <link rel="stylesheet"  href="../css/bootstrap.min.css" />
        <script src="../js/jquery.min.js"></script>
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
    	<div class="h3">command</div>
    	<div class="highlight">
	    	<div class="row">
	    		<div class="form-group col-sm-8">
		    		<select class="form-control" id="interfaceKey">
					</select>
				</div>
	    	</div>
	    	<div class="row paramBox">
				<div class="form-group col-sm-8">
					<input type="text" class="form-control" id="paramsInfo" placeholder="参数值">
				</div>
	    	</div>
	    	<div class="row" id="sendContainer">
	    		<div class="form-group col-sm-4">
					<a href="javascript:;" class="btn btn-success" id="submit" role="button">执行命令</a>
	    		</div>
	    	</div>
    	</div>
    	<div class="h3">结果</div>
    	<div class="highlight result">
    	</div>
    </div>
    <script type="text/javascript">
    	function utils(cacheInfo) {
    		var isBusy = false;
    		if (cacheInfo) {
    			var that = $("#interfaceKey");
    			for (var key in cacheInfo) {
    				that.append("<option value='" + cacheInfo[key] + "'>" + cacheInfo[key] + "</option>");
    			}
    		}
    		function submit() {
    			isBusy = true;
    			// 拦截验证参数格式是否正确
    			try {
    				var data = getData();
    			} catch (e) {
    				alert("参数错误");
    				isBusy = false;
    				return;
    			}
    			$(".result").html("等待中...");
    			$.ajax({
    				url: "${pageContext.request.contextPath}/command/doCommand",
    				type: "post",
    				data: data,
    				success: function(str) {
   						$(".result").html(str);
    					isBusy = false;
    				},
    				error: function() {
    					$(".result").html("ajax请求出现了一点小错误。。。");
    					isBusy = false;
    				}
    			});
    		}
    		function getData() {
    			var commandName = $("#interfaceKey").val();
    			var paramsInfo = $("#paramsInfo").val();
    			return {
    				commandName: commandName,
    				paramsInfo: getParamsObj(paramsInfo)
    			};
    		}
    		function getParamsObj(paramsInfo) {
    			// 单一参数，纯string格式
    			if (paramsInfo.indexOf("=") == -1) {
    				return paramsInfo;
    			}
    			// 复合参数，json格式
    			var arr = paramsInfo.split("&");
    			var result = {};
    			for (var key in arr) {
    				var val = arr[key];
    				var valArr = val.split("=");
    				result[valArr[0]] = valArr[1];
    			}
    			return JSON.stringify(result);
    		}
    		$("#submit").click(function() {
    			if (!isBusy) submit();
    		});
    	}
    	utils(${commands});
	</script>
    </body>
</html>

