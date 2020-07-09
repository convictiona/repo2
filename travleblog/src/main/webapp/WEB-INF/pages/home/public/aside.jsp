<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/9
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
    function add0(m){return m<10?'0'+m:m }
    function format(shijianchuo){

        var time = new Date(shijianchuo);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }

    $(document).ready(function () {
         $.ajax({
             url:'${pageContext.request.contextPath}/home/article/commentList',
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
                          li += format(articleList[i].createTime) + '</span><span class="muted"><i class="glyphicon glyphicon-eye-open"></i>'+articleList[i].scannerNumber+'</span></a></li>';
                          html += li;
                      }
                    $("#commentList").append(html);
                }
             }
         });

        $.ajax({
            url:'${pageContext.request.contextPath}/index/get_site_info',
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.type == 'success'){
                    $("#total-article-span").text(data.totalArticle);
                   /* $("#sitetime").text(data.siteDays);*/
                }
            }
        });
    })
</script>
<aside class="sidebar">
    <div class="fixed">
        <div class="widget widget-tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#notice" aria-controls="notice" role="tab" data-toggle="tab" >统计信息</a></li>
                <li role="presentation"><a href="#contact" aria-controls="contact" role="tab" data-toggle="tab" >关于我们</a></li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane contact active" id="notice">
                    <h2>文章总数:
                        <span id="total-article-span"></span>篇
                    </h2>
             <%--       <h2>文章:
                        <span id="sitetime">88天 </span></h2>--%>
                </div>
                <div role="tabpanel" class="tab-pane contact" id="contact">
                    <h2>QQ:
                        <a href="" target="_blank" rel="nofollow" data-toggle="tooltip" data-placement="bottom" title=""  data-original-title="QQ:"></a>
                    </h2>
                    <h2>Email:
                        <a href="#" target="_blank" data-toggle="tooltip" rel="nofollow" data-placement="bottom" title=""  data-original-title="#"></a></h2>
                </div>
            </div>
        </div>

        <div class="widget widget_search">
            <form class="navbar-form" action="${pageContext.request.contextPath}/home/search/SearchList" method="get">
                <div class="input-group">
                    <input type="text" name="Word" class="form-control" size="35" placeholder="请输入关键字" maxlength="15" autocomplete="off" value="${Word}">
                    <span class="input-group-btn">
		<button class="btn btn-default btn-search" name="search" type="submit">搜索</button>
		</span> </div>
            </form>
        </div>

    </div>

    <div class="widget widget_hot">
        <h3>最新评论文章</h3>
        <ul id="commentList">
        </ul>
    </div>
<%--<div id="tagsList">
    <div class="widget widget_sentence">
        <div class="thumbnail">
            <img src="${article.photo}">
            <div class="caption">
                <h3><a class="focus" href="${pageContext.request.contextPath}/home/article/article_detail?id=${article.id}" title= "${article.title}">${article.title} </a></h3>
                <p>${article.description}</p>
                <p>
                    <a href="${pageContext.request.contextPath}/home/search/SearchList?Word=${article.tags}" class="btn btn-primary" rel="tag" role="button">${article.tags}</a>
                </p>
            </div>
        </div>
    </div>
</div>--%>



</aside>


