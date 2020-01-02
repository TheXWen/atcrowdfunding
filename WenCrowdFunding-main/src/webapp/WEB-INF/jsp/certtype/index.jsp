<%--
  Created by IntelliJ IDEA.
  User: xw
  Date: 2019/12/31
  Time: 15:04
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
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}

        input[type=checkbox] {
            width:18px;
            height:18px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <jsp:include page="/WEB-INF/jsp/common/userinfo.jsp"></jsp:include>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th>名称</th>
                                <th >商业公司</th>
                                <th >个体工商户</th>
                                <th >个人经营</th>
                                <th >政府及非营利组织</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${allCert}" var="cert">
                                    <tr>
                                        <td>${cert.name}</td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="0"></td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="1"></td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="2"></td>
                                        <td><input type="checkbox" certid="${cert.id}" accttype="3"></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
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
        showMenu();

        <c:forEach items="${certAccttypeList}" var="cert">
            $(":checkbox[certid='${cert.certid}'][accttype='${cert.accttype}']")[0].checked = true;
        </c:forEach>

    });

    $(":checkbox").click(function(){
        var flg = this.checked;
        //通过this.certid能否获取自定义的属性值?
        var certid = $(this).attr("certid");
        var accttype = $(this).attr("accttype");

        if ( flg ) {

            // 增加账户类型和资质的关系
            $.ajax({
                type : "POST",
                url  : "${APP_PATH}/certtype/insertAcctTypeCert.do",
                data : {
                    certid : certid,
                    accttype : accttype
                },
                success : function(result) {
                    if ( result.success ) {

                    } else {
                        layer.msg("分类关系保存失败", {time:1000, icon:5, shift:6});
                    }
                }
            });

        } else {

            // 删除账户类型和资质的关系
            $.ajax({
                type : "POST",
                url  : "${APP_PATH}/certtype/deleteAcctTypeCert.do",
                data : {
                    certid : certid,
                    accttype : accttype
                },
                success : function(result) {
                    if ( result.success ) {

                    } else {
                        layer.msg("分类关系删除失败", {time:1000, icon:5, shift:6});
                    }
                }
            });
        }
    });

</script>
<script type="text/javascript" src="${APP_PATH}/script/menu.js"></script>
</body>
</html>

