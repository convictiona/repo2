<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/12
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../public/header.jsp" %>
<link href="${pageContext.request.contextPath }/css/zan.css" rel="stylesheet">
<section class="container">
    <div class="content-wrap">
        <div class="content">
            <header class="article-header">
                <h1 class="article-title"><a href="" title="${article.title }" >${article.title }</a></h1>
                <div class="article-meta">
		<span class="item article-meta-time">
	  		<time class="time" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="发表时间：2016-10-14"><i class="glyphicon glyphicon-time"></i> <fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></time>
	  	</span>
                    <span class="item article-meta-source" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="旅游博客">
	  		<i class="glyphicon glyphicon-globe"></i>
	  		旅游博客
	  	</span>
                    <span class="item article-meta-category" data-toggle="tooltip" data-placement="bottom" title="${article.title }" data-original-title="${article.title }">
	  		<i class="glyphicon glyphicon-list"></i>
	  		<a href="" title="${article.category.name }" >${article.category.name }</a>
	  	</span>
                    <span class="item article-meta-views" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="浏览量：${article.scannerNumber }">
	  		<i class="glyphicon glyphicon-eye-open"></i> ${article.scannerNumber }
	  	</span>
                    <span class="item article-meta-comment" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="评论量${article.commentNumber }">
	  		<i class="glyphicon glyphicon-comment"></i>${article.commentNumber }
	  	</span>
                </div>
            </header>
    <article class="article-content">
        ${article.content }
    </article>
            <hr>
    <div class="article-tags">标签：
        <c:forEach items="${tags }" var="tag">
            <a href="${pageContext.request.contextPath}/home/search/SearchList?Word=${tag }" rel="tag" >${tag }</a>
        </c:forEach>
    </div>



            <div class="title" id="comment">
        <h3>评论</h3>
    </div>
    <div id="respond">
        <form id="comment-form" name="comment-form" action="" method="POST">
            <div class="comment">
                <input type="hidden" name="articleId" value="${article.id }">
                <input name="commentName" id="commentName" class="form-control" size="22" placeholder="您的昵称（必填）" maxlength="15" autocomplete="off" tabindex="1" type="text">
                <div class="comment-box">
                    <textarea placeholder="您的评论或留言（必填）" name="content" id="comment-textarea" cols="100%" rows="3" tabindex="3"></textarea>
                    <div class="comment-ctrl">
                        <div class="comment-prompt" style="display: none;"> <i class="fa fa-spin fa-circle-o-notch"></i> <span class="comment-prompt-text">评论正在提交中...请稍后</span> </div>
                        <div class="comment-success" style="display: none;"> <i class="fa fa-check"></i> <span class="comment-prompt-text">评论提交成功...</span> </div>
                        <button type="button" id="comment-submit" tabindex="4">评论</button>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <div id="postcomments" style="padding-bottom:0px;">
        <ol id="comment_list" class="commentlist">
        </ol>
    </div>
    <div class="ias_trigger" style="margin-top:0px;"><a href="javascript:;" id="more">查看更多</a></div>
    </div>
    </div>
    <%@ include file="../public/aside.jsp" %>
</section>

<%@ include file="../public/footer.jsp" %>
<script>

    var page = 1;
    $(document).ready(function(){
        $("body").addClass('single');
        //评论文章
        $("#comment-submit").click(function(){
            if($("#commentName").val() == ''){
                alert('请填写昵称！');
                return;
            }
            if($("#comment-textarea").val() == ''){
                alert('请填写内容！');
                return;
            }
            $.ajax({
                url:'${pageContext.request.contextPath}/home/comment/saveComment',
                type:'post',
                data:$("#comment-form").serialize(),
                dataType:'json',
                success:function(data){
                    if(data.type == 'success'){

                        var li = '<li class="comment-content"><span class="comment-f">#' + ($("#comment_list").children('li').length + 1);
                        li += '</span><div class="comment-main"><p><a class="address" href="#" rel="nofollow" target="_blank">'+$("#commentName").val()+'</a><span class="time">('+format(data.createTime)+')</span><br>'+$("#comment-textarea").val()+'</p></div></li></ol>';
                        $("#comment_list").append(li);
                        $("#comment-textarea").val('');
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });



        //异步加载评论内容
        $.ajax({
            url:'${pageContext.request.contextPath}/home/comment/commentList',
            type:'post',
            data:{rows:10,page:page++,articleId:'${article.id}'},
            dataType:'json',
            success:function(data){
                if(data.type == 'success'){
                    var commentList = data.commentList;
                    var html = '';
                    for(var i=0;i<commentList.length;i++){
                        var li = '<li class="comment-content"><span class="comment-f">#' + (commentList.length -i);
                        li += '</span><div class="comment-main"><p><a class="address" href="#" rel="nofollow" target="_blank">'+commentList[i].commentName+'</a><span class="time">('+format(commentList[i].commentTime)+')</span><br>'+commentList[i].content+'</p></div></li></ol>';
                        html += li;
                    }
                    $("#comment_list").append(html);
                }
            }
        });

        $("#more").click(function(){
            if($("#more").attr('data-key') == 'all')return;
            $("#more").text('查看更多评论');
            //异步加载评论内容
            $.ajax({
                url:'${pageContext.request.contextPath}/home/comment/commentList',
                type:'post',
                data:{rows:10,page:page++,articleId:'${article.id}'},
                dataType:'json',
                success:function(data){
                    if(data.type == 'success'){

                        var commentList = data.commentList;
                        $("#more").text('查看更多评论');
                        if(commentList.length == 0){
                            $("#more").text('没有更多了 `');
                            $("#more").attr('data-key','all');
                        }
                        var html = '';
                        for(var i=0;i<commentList.length;i++){
                            var li = '<li class="comment-content"><span class="comment-f">#' + ($("#comment_list").children('li').length + i + 1);
                            li += '</span><div class="comment-main"><p><a class="address" href="#" rel="nofollow" target="_blank">'+commentList[i].commentName+'</a><span class="time">('+format(commentList[i].commentTime)+')</span><br>'+commentList[i].content+'</p></div></li></ol>';
                            html += li;
                        }
                        $("#comment_list").append(html);
                    }
                }
            });
        });
    });

</script>