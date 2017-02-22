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
        <script src="../js/jsonToHtml.js"></script>
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
			.params {
				border-left-color: #5cb85c;
				min-height: 80px;
				word-wrap: break-word;
			}
			.processor {
				height: 32px;
				line-height: 32px;
				border: 1px solid #ccc;
				border-radius: 4px;
				padding: 0 10px;
				position: relative;
				font-size: 5px;
    			width: 80px;
			}
			.processor ._close {
				position: absolute;
				top: 4px;
				right : 4px;
				height: 20px;
				line-height: 7px;
				color: black;
				font-size: 10px;
				width: 30px;
				text-align: right;
				
			}
			.interfaceBox {
				border: 0;
				height: 34px;
				line-height: 34px;
				margin-right: 25px;
				margin-top: 20px;
				border-radius: 4px;
				color: #c7254e;
				cursor: pointer;
			}
			.interfaceBox:hover {
				color: #c7254e;
				text-decoration: none;
			}
			.table_container{
				margin-top: 20px;
				margin-right: 0;
			    margin-left: 0;
			    background-color: #fff;
			    border-radius: 4px 4px 0 0;
			    -webkit-box-shadow: none;
			    box-shadow: none;
		        padding: 15px 15px 5px;
		        position: relative;
				font-size: 12px;
		        font-family: '微软雅黑';
			}
			.table_container .title{
				font-family: '微软雅黑';
				font-size: 12px;
				position: absolute;
			    top: -15px;
			    left: 16px;
			    font-size: 12px;
			    font-weight: 700;
			    color: #959595;
			    text-transform: uppercase;
			    letter-spacing: 1px;
			}
			.param_kv {
				margin-bottom: 15px;
			}
    	</style>
    </head>
    <body>
    <div class="container">
    
    	<!-- 参数相关 -->
    	<div class="h3">测试参数</div>
    	<div class="highlight">
	    	<div class="row">
	    		<div class="form-group col-sm-8">
					<input type="text" class="form-control"  placeholder="输出接口地址" id="resourceUrl">
				</div>
				<div class="form-group col-sm-2">
					<a href="javascript:;" class="btn btn-warning " role="button" id="addParam">添加参数</a>
				</div>
				<div class="form-group col-sm-2">
		    		<select class="form-control" id="serverUrl">
		    		  <option value="http://192.168.200.148:8080">default</option>
					  <option value="http://127.0.0.1:8080">local</option>
					  <!-- <option value="http://120.76.79.49:38080">online</option> -->
					</select>
				</div>
	    	</div>
	    	<div class="row" id="sendContainer">
	    		<div class="form-group col-sm-8">
					<a href="javascript:;" class="btn btn-success" id="doSubmit" role="button">发起请求</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:;" class="btn btn-warning" role="button" id="clearParams">清空参数</a>
	    		</div>
	    		<div class="form-group col-sm-4 text-right">
	    			<a href="javascript:;" class="btn btn-info" role="button" data-toggle="modal" data-target="#myModal">高级设置</a>
	    			&nbsp;
	    			<a href="javascript:;" class="btn btn-warning" role="button" data-toggle="modal" data-target="#interfaceManager">接口管理</a>
				</div>
	    	</div>
    	</div>
    	
    	<!-- 最终请求结果 -->
    	<div class="h3">结果</div>
    		<div class="highlight result">
    	</div>
    	
    	<!-- 最终请求参数 -->
    	<div class="h3">参数</div>
    		<div class="highlight params">
    	</div>
    	
    	<!-- 历史记录 -->
    	<div class="panel panel-default">
		    <div class="panel-heading" role="tab" id="headingOne">
		      <h4 class="panel-title">
		        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
		          	历史记录
		        </a>
		      </h4>
		    </div>
		    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
		        <ul class="list-group"></ul>
		    </div>
		</div>
    </div>
    
    <!-- 高级设置 -->
   	<div class="modal fade" id="myModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	        	<span aria-hidden="true">&times;</span>
	        </button>
	        <h4 class="modal-title">高级设置</h4>
	      </div>
	      <div class="modal-body">
        	<div class="form-group col-sm-8">
        		<label for="manualServerUrl">手动设置URL</label>
        		<input type="text" class="form-control"  placeholder="输出接口名称" id="manualServerUrl">
        	</div>
        	<div class="clearfix"></div>
        	<div class="form-group col-sm-8">
        		<label for="clients">选择客户端类型</label>
	        	<select class="form-control" id="clients"></select>
			</div>
        	<div class="clearfix"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div>
	
	<!-- 接口管理 -->
   	<div class="modal fade" id="interfaceManager" >
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">接口管理</h4>
	      </div>
	      <div class="modal-body">
	      
	      	<!-- 接口修改 -->
        	<div id="interfaceModify" style="padding: 0 10px;display: none"></div>
        	
        	<!-- 接口查询 -->
        	<div id="interfaceSearch">
	        	<div class="form-group col-sm-4">
	        		<input type="text" class="form-control"  placeholder="输出接口名称" id="queryKeyword">
	        	</div>
	        	<div class="form-group col-sm-6">
	        		<button type="button" class="btn btn-info" id="queryInterface">&nbsp;查找&nbsp;</button>
	        	</div>
	        	<div class="form-group col-sm-2">
	        		<button type="button" class="btn btn-success" id="toInterfaceTestCase" style="margin-left:75px">测试</button>
	        	</div>
	        	<div class="clearfix"></div>
        		<div class="form-group col-sm-12" id="interfaceBody" style="min-height: 200px; font-size:12px;"></div>
	        	<div class="clearfix"></div>
	        </div>
	        
	        <!-- 接口测试用例 -->
        	<div id="interfaceTestCase" style="padding: 0 10px;display: none;overflow:hidden;">
        		<div id="testCaseList">
        			<div class="form-group col-sm-4">
		        		<input type="text" class="form-control"  placeholder="输出测试用例名称" id="queryTestCase">
		        	</div>
		        	<div class="form-group col-sm-6">
		        		<button type="button" class="btn btn-info" id="queryTestCase">&nbsp;查找&nbsp;</button>
		        		&nbsp;&nbsp;&nbsp;&nbsp;
		        		<button type="button" class="btn btn-warning" id="addTestCase">&nbsp;新增&nbsp;</button>
		        	</div>
	        		<div class="clearfix"></div>
	        		<div class="form-group col-sm-12" id="testCaseBody" style="min-height: 200px; font-size:12px;"></div>
		        	<div class="clearfix"></div>
        		</div>
        		<div id="testCaseDetail" style="display: none;"></div>
        		<div id="testCaseAdd" style="display: none;">
        			<div class="form-group col-sm-12" style="min-height: 50px;">
						<h5>接口：</h5>
							<button type="button" class="btn btn-primary" id="selectInterface">选择接口</button>
					</div>
					<div class="form-group col-sm-12" id="testCase_param" style="display: none;">
						<h5>参数：</h5>
					</div>
					<div class="form-group col-sm-12" id="testCase_expect">
						<h5>期望：</h5>
						<div class="form-group">
					      <input type="text" class="form-control" id="exampleInputAmount" placeholder="请输入你的期望表达式">
						</div>
					</div>
					<div class="form-group col-sm-12" id="test_btnGroup_add" style="display: none;">
						<button type="button" class="btn btn-info" id="testCase_nextStep">&nbsp;下一步&nbsp;</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-success" id="testCase_complete">&nbsp;完成&nbsp;</button>
					</div>
					
					<div class="form-group col-sm-12" id="test_btnGroup_edit" style="display: none;">
						<button type="button" class="btn btn-info" id="testCase_ensure">&nbsp;确定&nbsp;</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-success" id="testCase_cancel">&nbsp;取消&nbsp;</button>
					</div>
        		</div>
        	</div>
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div>
<script src="../js/main/requestManager.js"></script>	
<script src="../js/main/interfaceManager.js"></script>	
<script src="../js/main/testManager.js"></script>	
<script type="text/javascript">
$(function() {
	/**
		请求管理器
	*/
	var requestManager = new RequestManager({
		basePath: "${pageContext.request.contextPath}",
		clients: $.parseJSON('${clients}'),
		processors: $.parseJSON('${processors}')
	});
 	
	/** -----------------------------------------接口管理------------------------------------------ **/
 	// 初始化
 	interfaceManager.init("${pageContext.request.contextPath}");
 	
 	// 初始化
 	testManager.init("${pageContext.request.contextPath}");
});
</script>
</body>
</html>

