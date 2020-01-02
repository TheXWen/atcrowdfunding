<%--
  Created by IntelliJ IDEA.
  User: xw
  Date: 2019/12/31
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${APP_PATH}/css/theme.css">
    <style>
        #footer {
            padding: 15px 0;
            background: #fff;
            border-top: 1px solid #ddd;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="navbar-wrapper">
    <div class="container">
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse" style="float:right;">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> 张三<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="member.html"><i class="glyphicon glyphicon-scale"></i> 会员中心</a></li>
                                <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                                <li class="divider"></li>
                                <li><a href="index.html"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

    </div>
</div>

<div class="container theme-showcase" role="main">
    <div class="page-header">
        <h1>实名认证 - 申请</h1>
    </div>

    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" ><a href="#"><span class="badge">1</span> 基本信息</a></li>
        <li role="presentation" class="active"><a href="#"><span class="badge">2</span> 资质文件上传</a></li>
        <li role="presentation"><a href="#"><span class="badge">3</span> 邮箱确认</a></li>
        <li role="presentation"><a href="#"><span class="badge">4</span> 申请确认</a></li>
    </ul>

    <form id="uploadCertFrom" style="margin-top:20px;" method="post" enctype="multipart/form-data">

        <c:forEach items="${queryCertByAccttype}" var="cert" varStatus="status">
            <div class="form-group">
                <label for="name">${cert.name}</label>
                <input type="hidden" name="certimgs[${status.index}].certid" value="${cert.id}">
                <input type="file" name="certimgs[${status.index}].fileImg" class="form-control" >
                <br>
                <img src="img/pic.jpg" style="display: none">
            </div>
        </c:forEach>

        <button type="button" onclick="window.location.href='apply.html'" class="btn btn-default">上一步</button>
        <button type="button" id="nextBtn" class="btn btn-success">下一步</button>
    </form>
    <hr>
</div> <!-- /container -->
<div class="container" style="margin-top:20px;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="footer">
                <div class="footerNav">
                    <a rel="nofollow" href="http://www.atguigu.com">关于我们</a> | <a rel="nofollow" href="http://www.atguigu.com">服务条款</a> | <a rel="nofollow" href="http://www.atguigu.com">免责声明</a> | <a rel="nofollow" href="http://www.atguigu.com">网站地图</a> | <a rel="nofollow" href="http://www.atguigu.com">联系我们</a>
                </div>
                <div class="copyRight">
                    Copyright ?2017-2017 atguigu.com 版权所有
                </div>
            </div>

        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/jquery-form/jquery-form.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script>
    $('#myTab a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    });

    $(":file").change(function(event){
        var files = event.target.files;
        var file;

        if (files && files.length > 0) {
            file = files[0];

            var URL = window.URL || window.webkitURL;
            // 本地图片路径
            var imgURL = URL.createObjectURL(file);

            var imgObj = $(this).next().next(); //获取同辈紧邻的下一个元素
            //console.log(imgObj);
            imgObj.attr("src", imgURL);
            imgObj.show();
        }
    });
    
    $("#nextBtn").click(function () {
        var options = {
            url:"${APP_PATH}/member/doUploadCert.do",
            beforeSubmit : function(){
                loadingIndex = layer.msg('数据正在保存中', {icon: 6});
                return true ; //必须返回true,否则,请求终止.
            },
            success : function(result){
                layer.close(loadingIndex);
                if(result.success){
                    layer.msg("数据保存成功", {time:1000, icon:6});
                    window.location.href="${APP_PATH}/member/apply.htm";
                }else{
                    layer.msg("数据保存失败", {time:1000, icon:5, shift:6});
                }
            }
        };

        $("#uploadCertFrom").ajaxSubmit(options); //异步提交
        return ;
    });
</script>
</body>
</html>
