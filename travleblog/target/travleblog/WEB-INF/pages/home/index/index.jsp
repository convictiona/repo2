<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/9
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../public/header.jsp" %>
<script>
    $(document).ready(function () {
        $.ajax({
            url:'${pageContext.request.contextPath}/home/article/scannerList',
            type:'post',
            dataType:'json',
            success:function (data) {
                if (data.type =='success'){
                    var articleList=data.articleList;
                    var  html='';
                    for ( var i=0;i<articleList.length;i++){
                        var li ='<li><a title="'+articleList[i].title+'" href="${pageContext.request.contextPath}/home/article/article_detail?id='+articleList[i].id+'"><span class="thumbnail">';
                        li += '<img class="thumb" data-original="${pageContext.request.contextPath}/resources/home/images/201610181739277776.jpg" src="'+articleList[i].photo+'" alt="'+articleList[i].id+'"  style="display: block;">';
                        li += '</span><span class="text">'+articleList[i].title+'</span><span class="muted"><i class="glyphicon glyphicon-time"></i>';
                        li += format(articleList[i].createTime) + '</span><span class="muted"><i class="glyphicon glyphicon-eye-open"></i>'+articleList[i].scannerNumber+'</span>';
                        li +='<span class="muted"><i class="glyphicon glyphicon-comment"></i>'+articleList[i].commentNumber+'</span></li>';
                        html += li;
                    }
                    $("#scannerList").append(html);
                }
            }
        });
    })
</script>

<section class="container">

    <div class="content-wrap">
        <div class="content">
            <div id="focusslide" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#focusslide" data-slide-to="0" class="active"></li>
                    <li data-target="#focusslide" data-slide-to="1"></li>
                    <li data-target="#focusslide" data-slide-to="2"></li>
                    <li data-target="#focusslide" data-slide-to="3"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                            <img src="../resources/home/images/d.jpg" alt="First slide" class="img-responsive">
                    </div>
                    <div class="item">
                            <img src="../resources/home/images/d.jpg" alt="Second slide" class="img-responsive">

                    </div>
                    <div class="item">
                        <img src="../resources/home/images/d.jpg" alt="Third slide" class="img-responsive">

                    </div>
                    <div class="item">
                        <img src="../resources/home/images/d.jpg" alt="Third slide" class="img-responsive">

                    </div>
                </div>

                <a class="left carousel-control" href="#focusslide" role="button" data-slide="prev" >
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">上一个</span>
                </a>
                <a class="right carousel-control" href="#focusslide" role="button" data-slide="next" >
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">下一个</span>
                </a>
            </div>


            <div class="widget widget_hot">
                <h3>推荐</h3>
                <ul id="scannerList">
                </ul>
            </div>

            <div class="title">
                <h3>最新发布</h3>
            </div>
            <c:forEach items="${articleList }" var="article">
                <article class="excerpt excerpt-1" style="">
                    <a class="focus" href="${pageContext.request.contextPath}/home/article/article_detail?id=${article.id }" title="${article.title }" target="_blank" ><img class="thumb" data-original="${article.photo }" src="${article.photo }" alt="${article.title }"  style="display: inline;"></a>
                    <header><a class="cat" href="${pageContext.request.contextPath}/home/article/CategoryList?Id=${article.categoryId }" title="${article.category.name }" >${article.category.name }<i></i></a>
                        <h2><a href="${pageContext.request.contextPath}/home/article/article_detail?id=${article.id }" title="${article.title }" target="_blank" >${article.title }</a>
                        </h2>
                    </header>
                    <p class="meta">
                        <time class="time"><i class="glyphicon glyphicon-time"></i> <fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></time>
                        <span class="views"><i class="glyphicon glyphicon-eye-open"></i> ${article.scannerNumber }</span>
                        <a class="comment" href="${pageContext.request.contextPath}/home/article/article_detail?id=${article.id }#comment" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i>${article.commentNumber }</a>
                    </p>
                    <p class="note">${article.description }</p>
                </article>
            </c:forEach>
            <div class="ias_trigger" style="display:none;"><a href="javascript:;">查看更多</a></div>
        </div>
    </div>
    <%@ include file="../public/aside.jsp" %>
</section>

<%@ include file="../public/footer.jsp" %>
