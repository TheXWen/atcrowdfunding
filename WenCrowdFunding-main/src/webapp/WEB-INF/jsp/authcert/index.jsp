<%--
  Created by IntelliJ IDEA.
  User: xw
  Date: 2020/1/1
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" href="${APP_PATH}/jquery/pagination/pagination.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>流程定义名称</th>
                                <th>流程定义版本</th>
                                <th>任务名称</th>
                                <th>会员名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
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
<script src="${APP_PATH}/jquery/pagination/jquery.pagination.js"></script>
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
        pageQuery(0);
    });

    function pageQuery(pageindex) {
        var loadingIndex = -1;

        var obj = {
            "pageno" : pageindex+1,
            "pagesize" : 10
        };

        $.ajax({
            url : "${APP_PATH}/authcert/pageQuery.do",
            type : "POST",
            data : obj,
            beforeSend : function() {
                loadingIndex = layer.msg('数据查询中', {icon: 16});
                return true;
            },
            success : function(result) {
                layer.close(loadingIndex);
                if (result.success) {
                    // 将查询结果循环遍历，将数据展现出来
                    var page = result.page;
                    var taskList = page.datas;

                    var content = "";
                    $.each(taskList, function(i, task){
                        content += '<tr>';
                        content += '  <td>' + (i+1)+'</td>';
                        content += '  <td>' + task.procDefName+'</td>';
                        content += '  <td>' + task.procDefVersion+'</td>';
                        content += '  <td>' + task.taskName+'</td>';
                        content += '  <td>' + task.member.username+'</td>';
                        content += '  <td>';
                        content += '      <button type="button" onclick="window.location.href=\'${APP_PATH}/authcert/show.htm?taskid='+task.taskid+'&memberid='+task.member.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                        content += '  </td>';
                        content += '</tr>';
                    });
                    $("tbody").html(content);

                    // 创建分页
                    var num_entries = page.totalsize ;
                    $("#Pagination").pagination(num_entries, {
                        num_edge_entries: 2, //边缘页数
                        num_display_entries: 4, //主体页数
                        callback: pageQuery, //查询当前页的数据.
                        items_per_page:page.pagesize, //每页显示1项
                        current_page:(page.pageno-1), //当前页,索引从0开始
                        prev_text:"上一页",
                        next_text:"下一页"
                    });
                } else {
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            }
        });
    }

</script>
<script type="text/javascript" src="${APP_PATH}/script/menu.js"></script>
</body>
</html>

