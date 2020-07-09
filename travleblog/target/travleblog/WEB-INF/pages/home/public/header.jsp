<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/9
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>旅游博客网站</title>
    <meta name="keywords" content="">
    <meta name="description" content="">


<%--    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home/css/iconfont.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/home/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/home/css/nprogress.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/home/css/font-awesome.min.css">
    <link el="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/resources/home/images/icon.png">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/home/images/favicon.ico">
    <script src="${pageContext.request.contextPath}/resources/home/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/home/js/nprogress.js"></script>
    <script src="${pageContext.request.contextPath}/resources/home/js/jquery.lazyload.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/home/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/home/js/html5shiv.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/home/js/respond.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/home/js/selectivizr-min.js" type="text/javascript"></script>

    <script>
        function AddFavorite(url, title) {
            try {
                window.external.addFavorite(url, title);
            } catch (e){
                try {
                    window.sidebar.addPanel(title, url, '');
                } catch (e) {
                    alert("请按 Ctrl+D 键添加到收藏夹", 'notice');
                }
            }
        }

    </script>

    <style type="text/css">
        a:link,a:visited{
            text-decoration:none;  /*超链接无下划线*/
        }
        a:hover{
            text-decoration:underline;  /*鼠标放上去有下划线*/
        }
    </style>


</head>
<body class="user-select">
<header class="header">
    <nav class="navbar navbar-default" id="navbar">
        <div class="container">
            <div class="header-topbar hidden-xs link-border">
                <ul class="site-nav topmenu">
                    <li><a href="${pageContext.request.contextPath}/system/login" >
                        <i class="iconfont icon-bixin" ></i>
                        博主登录</a></li>
                    <li><a href="#" onclick="AddFavorite('http://localhost:8080/travleblog_war_exploded/index/index','收藏本站')"  title="收藏本站" >
                        <i class="iconfont icon-lunkuodasan-" ></i> 收藏本站
                    </a></li>
                    <li><a href="#" onclick="AddFavorite('http://localhost:8080/travleblog_war_exploded/index/index','收藏本站')"  title="收藏本站" >
                        <i class="iconfont icon-lunkuodasan-" ></i> 友情链接
                    </a></li>
                </ul>
                加油啊
            </div>
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#header-navbar" aria-expanded="false"> <span class="sr-only"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
                <h1 class="logo hvr-bounce-in">
                    <a href="#" title="旅游博客">旅游博客</a>
                </h1>
            </div>

            <div class="collapse navbar-collapse" id="header-navbar">
                <form class="navbar-form visible-xs" action="${pageContext.request.contextPath}/home/search/SearchList" method="GET">
                    <div class="input-group">
                        <input type="text" name="Word" class="form-control" placeholder="请输入关键字" maxlength="20" autocomplete="off" value="${Word}">
                        <span class="input-group-btn">
		<button class="btn btn-default btn-search" name="search" type="submit">搜索</button>
		</span> </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li><a data-cont="旅游博客" title="旅游博客" href="/travleblog_war_exploded/index/index">首页</a></li>
                  <c:forEach items="${categoryList}" var="category">
                      <li><a data-cont="${category.name}" title="${category.name}" href="${pageContext.request.contextPath}/home/article/CategoryList?Id=${category.id}">${category.name}</a></li>
                  </c:forEach>

                </ul>
            </div>
        </div>
    </nav>
</header>