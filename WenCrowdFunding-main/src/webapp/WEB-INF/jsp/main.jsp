<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/main.css">
	<style>
		.tree li {
			list-style-type: none;
			cursor:pointer;
		}
		.tree-closed {
			height : 40px;
		}
		.tree-expanded {
			height : auto;
		}
	</style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 控制面板</a></div>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">

			<!-- HTML注释:参与编译,会生成到源码中. 所以,不能使用html注释EL表达式和JSTL标签库 -->
			<%-- JSP注释:注释的内容不参与编译,不会生成到源码中. --%>

			<!-- 动态包含:被包含的页面也会独立编译,生成字节码文件. -->
				<%--<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>--%>
			<!-- 静态包含:被包含的页面不会生成独立字节码文件.
          			将所包含的页面内容加入到当前页面,一起进行编译.
           -->
				<%@ include file="/WEB-INF/jsp/common/top.jsp"%>
			</ul>
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control" placeholder="查询">
			</form>
		</div>
	</div>
</nav>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
			</div>
		</div>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">控制面板</h1>

			<div class="row placeholders">
				<div class="col-xs-6 col-sm-3 placeholder">
					<img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
					<h4>Label</h4>
					<span class="text-muted">Something else</span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
					<h4>Label</h4>
					<span class="text-muted">Something else</span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
					<h4>Label</h4>
					<span class="text-muted">Something else</span>
				</div>
				<div class="col-xs-6 col-sm-3 placeholder">
					<img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
					<h4>Label</h4>
					<span class="text-muted">Something else</span>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript">
	$(function () {
		$(".list-group-item").click(function(){
			if ( $(this).find("ul") ) {
				$(this).toggleClass("tree-closed");
				if ( $(this).hasClass("tree-closed") ) {
					$("ul", this).hide("fast");
				} else {
					$("ul", this).show("fast");
				}
			}
		});
	});
</script>
</body>
</html>
