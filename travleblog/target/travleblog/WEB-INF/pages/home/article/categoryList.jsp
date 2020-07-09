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


<section class="container">
    <div class="content-wrap">
        <div class="content">


            <div class="title">
                <h3>${category.name}</h3>
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
            <div class="ias_trigger"><a href="javascript:;" id="more">查看更多</a></div>
        </div>
    </div>

    <%@ include file="../public/aside.jsp" %>
</section>

<%@ include file="../public/footer.jsp" %>
<script>
    var page = 2;

    $(document).ready(function(){
        $("#more").click(function(){
            if($("#more").attr('data-key') == 'all')return;
            $("#more").text('查看更多');
            $.ajax({
                url:'${pageContext.request.contextPath}/home/article/categoryList',
                type:'post',
                data:{rows:10,page:page++,Id:'${category.id}'},
                dataType:'json',
                success:function(data){
                    if(data.type == 'success'){
                        $("#more").text('查看更多!');
                        var articleList = data.articleList;
                        if(articleList.length == 0){
                            $("#more").text('没有更多了');
                            $("#more").attr('data-key','all');
                        }
                        var html = '';
                        for(var i=0;i<articleList.length;i++){

                            var article = '<article class="excerpt excerpt-1" style="">';
                            article +='<a class="focus" href="${pageContext.request.contextPath}/home/article/article_detail?id=${article.id }" title="'+articleList[i].title+'" target="_blank" >';
                            article +='<img class="thumb" data-original="'+articleList[i].photo+'" src="'+articleList[i].photo+'" alt="'+articleList[i].title+'"  style="display: inline;"></a>';
                            article +='<header><a class="cat" href="${pageContext.request.contextPath}/home/article/categoryList?Id='+articleList[i].categoryId+'" title="'+articleList[i].categoryId.name+'" >'+articleList[i].category.name+'<i></i></a>';
                            article +='<h2><a href="#" title="'+articleList[i].title+'" target="_blank" >'+articleList[i].title+'</a></h2></header>';
                            article +='<p class="meta"><time class="time"><i class="glyphicon glyphicon-time"></i>'+format(articleList[i].createTime)+'</time>';
                            article +='<span class="views"><i class="glyphicon glyphicon-eye-open"></i> '+articleList[i].scannerNumber+'</span>';
                            article +='<a class="comment" href="${pageContext.request.contextPath}/home/article/article_detail?id=${article.id }#comment" title="评论" target="_blank" ><i class="glyphicon glyphicon-comment"></i>'+articleList[i].commentNumber+'</a></p>';
                            article +='<p class="note">'+articleList[i].description+'</p>';
                            article +='</article>';
                            html += article;
                        }
                        $("#more").parent("div").before(html);
                    }
                }
            });
        });

    });
</script>