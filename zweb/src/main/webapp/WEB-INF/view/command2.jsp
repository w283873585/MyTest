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
	    	<div class="row paramBox">
				<div class="form-group col-sm-8">
					<input type="text" class="form-control" id="commandInfo" placeholder="please enter the command">
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
    	$(function() {
    		$("#submit").click(function() {
    			var commandInfo = $("#commandInfo").val();
    			if (!commandInfo) {
    				$(".result").html("请填入command");
    				return;
    			}
    			$(".result").html("等待中...");
    			$.ajax({
    				url: "${pageContext.request.contextPath}/commands/result",
    				type: "post",
    				data: {commandInfo: commandInfo},
    				success: function(str) {
   						$(".result").html(str);
    				},
    				error: function() {
    					$(".result").html("ajax请求出现了一点小错误。。。");
    				}
    			});
    		});
    		
    		var commands = '${commands}';
    	
    	});
	</script>
    </body>
</html>

