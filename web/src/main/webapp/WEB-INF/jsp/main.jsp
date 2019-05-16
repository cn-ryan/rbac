<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/main.css">
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
        .main {
            padding: 0px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">控制面板</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-user"></i><shiro:principal property="username"></shiro:principal><span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                            <li class="divider"></li>
                            <li><a href="logout"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                        </ul>
                    </div>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
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
                <ul style="padding-left:0px;" class="list-group">
                    <c:forEach items="${rootPermission.children}" var="permission">
                        <c:if test="${empty permission.children}">
                            <li class="list-group-item tree-closed" >
                                <a href="${APP_PATH}${permission.url}"></i> ${permission.name}</a>
                            </li>
                        </c:if>
                        <c:if test="${not empty permission.children}">
                            <li class="list-group-item tree-closed">
                                <span class="menu-list">${permission.name} <span class="badge" style="float:right">${permission.children.size()}</span></span>
                                <ul style="margin-top:10px;display:none;">
                                    <c:forEach items="${permission.children}" var="child">
                                        <li style="height:30px;">
                                            <a href="javascript:void(0);" onclick="addTabs({menuid:'nav_mg_${child.id}',id:'menu_${child.id}',title: '${child.name}',close: true,url:'${APP_PATH}${child.url}'});"></i> ${child.name}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
                <%--<%@include file="/WEB-INF/jsp/common/menu.jsp"%>--%>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
            <div class="row placeholders">
                <div class="col-sm-12" style="padding-left:1px;">
                    <ul class="nav nav-tabs" role="tablist">
                        <li id="tab_mtab_welcome" class="active"><a href="#mtab_welcome" role="tab" data-toggle="tab">首页</a></li>
                    </ul>
                    <div class="tab-content tab-padding">
                        <div role="tabpanel" class="tab-pane active" id="mtab_welcome" style="height:100vh">
                            <iframe id="iframe_wp" style="overflow:auto;" width="100%" height="100%" frameborder="0" border="0" scrolling="yes" onload="window.parent"></iframe>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".menu-list").click(function(){
            // jquery对象的回调方法中的this关键字为DOM对象
            // $(DOM) ==> JQuery
            var li = $(this).parent();
            if ( li.find("ul") ) { // 3 li
                li.toggleClass("tree-closed");
                if ( li.hasClass("tree-closed") ) {
                    $("ul", li[0]).hide("fast");
                } else {
                    $("ul", li[0]).show("fast");
                }
            }
        });
    });
    var addTabs = function(options) {
        id = "mtab_" + options.id;
        jQuery(".active").removeClass("active");
        //如果TAB不存在，创建一个新的TAB
        if (!jQuery("#" + id)[0]) {
            //创建新TAB的title
            title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">'
                + options.title;
            //是否允许关闭
            if (options.close) {
                title += ' <i class="glyphicon glyphicon-remove" tabclose="' + id + '"></i>';
            }
            title += '</a></li>';
            //是否指定TAB内容
            if (options.content) {
                content = '<div role="tabpanel" class="tab-pane" id="' + id + '">'
                    + options.content + '</div>';
            } else {//没有内容，使用IFRAME打开链接
                content = '<div role="tabpanel" style="height: 100vh" class="tab-pane" id="' + id + '"><iframe id="iframe_wp_'+options.id+'" src="'
                    + options.url
                    + '" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
            }
            //加入TABS
            jQuery(".nav-tabs").append(title);
            jQuery(".tab-content").append(content);
        }
        //激活TAB
        jQuery("#tab_" + id).addClass('active');
        jQuery("#" + id).addClass("active");
        jQuery("#" + options.id).parent().parent().addClass("active");
        jQuery("#" + options.id).addClass("active");
    };
</script>
</body>
</html>
