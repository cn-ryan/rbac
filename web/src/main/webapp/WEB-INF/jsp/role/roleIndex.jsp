<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .red{
            color: red;
        }
        body{
            padding-top: 0px;
        }
    </style>
</head>

<body>
<div>
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="
" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" onclick="deleteUsers()" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/role/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <form id="userForm">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th width="30">#</th>
                                    <th width="30"><input type="checkbox" id="allSelBox"></th>
                                    <th>名称</th>
                                    <th>创建人</th>
                                    <th>创建时间</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>

                                <tbody id="userData">
                                </tbody>

                                <tfoot>
                                <tr >
                                    <td colspan="6" align="center">
                                        <ul class="pagination">
                                        </ul>
                                    </td>
                                </tr>

                                </tfoot>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/layer/layer.js"></script>
<script type="text/javascript">
    var likeflg = false;
    $(function () {
        $("#menuList li").click(function () {
            $(this).addClass("red");
        });
        pageQuery(1);

        $("#queryBtn").click(function(){
            var queryText = $("#queryText").val();
            if ( queryText == "" ) {
                likeflg = false;
            } else {
                likeflg = true;
            }
            pageQuery(1);
        });

        $("#allSelBox").click(function(){
            var flg = this.checked;

            $("#userData :checkbox").each(function(){
                this.checked = flg;
            });
        });
    });
    $("tbody .btn-success").click(function(){
        window.location.href = "assignRole.html";
    });
    $("tbody .btn-primary").click(function(){
        window.location.href = "edit.html";
    });

    // 分页查询
    function pageQuery( pageno ) {
        var loadingIndex = null;

        var jsonData = {"pageNo" : pageno, "pageSize" : 10};
        if ( likeflg == true ) {
            jsonData.queryText = $("#queryText").val();
        }

        $.ajax({
            type : "POST",
            url  : "${APP_PATH}/role/pageQuery",
            data : jsonData,
            beforeSend : function(){
                loadingIndex = layer.msg('处理中', {icon: 16});
            },
            success : function(result) {
                layer.close(loadingIndex);
                if ( result.success ) {
                    // 局部刷新页面数据
                    var tableContent = "";
                    var pageContent = "";

                    var userPage = result.page;
                    var roles = userPage.list;

                    $.each(roles, function(i, role){
                        tableContent += '<tr>';
                        tableContent += '  <td>'+(i+1)+'</td>';
                        tableContent += '  <td><input type="checkbox" name="userid" value="'+role.id+'"></td>';
                        tableContent += '  <td>'+role.rolename+'</td>';
                        tableContent += '  <td>'+role.createUser+'</td>';
                        tableContent += '  <td>'+role.createDate+'</td>';
                        tableContent += '  <td>';
                        tableContent += '      <button type="button" onclick="goAssignPage('+role.id+')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                        tableContent += '      <button type="button" onclick="goUpdatePage('+role.id+')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                        tableContent += '	  <button type="button" onclick="deleteUser('+role.id+','+role.rolename+')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                        tableContent += '  </td>';
                        tableContent += '</tr>';
                    });

                    if ( pageno > 1 ) {
                        pageContent += '<li><a href="#" onclick="pageQuery('+(pageno-1)+')">上一页</a></li>';
                    }

                    for ( var i = 1; i <= userPage.totalNo; i++ ) {
                        if ( i == pageno ) {
                            pageContent += '<li class="active"><a  href="#">'+i+'</a></li>';
                        } else {
                            pageContent += '<li ><a href="#" onclick="pageQuery('+i+')">'+i+'</a></li>';
                        }
                    }

                    if ( pageno < userPage.totalNo ) {
                        pageContent += '<li><a href="#" onclick="pageQuery('+(pageno+1)+')">下一页</a></li>';
                    }

                    $("#userData").html(tableContent);
                    $(".pagination").html(pageContent);
                } else {
                    layer.msg("用户信息分页查询失败", {time:2000, icon:5, shift:6}, function(){

                    });
                }
            }
        });
    }

    function goUpdatePage(id) {
        window.location.href = "${APP_PATH}/user/edit?id="+id;
    }

    function goAssignPage(id) {
        window.location.href = "${APP_PATH}/role/assign?id="+id;
    }


    function deleteUsers() {
        var boxes = $("#userData :checkbox:checked");
        if ( boxes.length == 0 ) {
            layer.msg("请选择需要删除的用户信息", {time:2000, icon:5, shift:6}, function(){

            });
        } else {
            layer.confirm("删除选择的用户信息, 是否继续",  {icon: 3, title:'提示'}, function(cindex){
                var ids = [];
                boxes.each(function () {
                    var id = $(this).val();
                    console.log(id);
                    ids.push(id);
                })
                // 删除选择的用户信息
                $.ajax({
                    type : "POST",
                    url  : "${APP_PATH}/user/delete",
                    data : {ids:ids.join(',')},
                    success : function(result) {
                        if ( result.success ) {
                            pageQuery(1);
                        } else {
                            layer.msg("用户信息删除失败", {time:2000, icon:5, shift:6}, function(){

                            });
                        }
                    }
                });

                layer.close(cindex);
            }, function(cindex){
                layer.close(cindex);
            });
        }
    }

    function deleteUser( id, username ) {
        layer.confirm("删除用户信息【"+username+"】, 是否继续",  {icon: 3, title:'提示'}, function(cindex){

            // 删除用户信息
            $.ajax({
                type : "POST",
                url  : "${APP_PATH}/user/delete",
                data : { ids : id },
                success : function(result) {
                    if ( result.success ) {
                        pageQuery(1);
                    } else {
                        layer.msg("用户信息删除失败", {time:2000, icon:5, shift:6}, function(){

                        });
                    }
                }
            });

            layer.close(cindex);
        }, function(cindex){
            layer.close(cindex);
        });
    }
</script>
</body>
</html>
