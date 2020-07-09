<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/6
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp"%>
<%@include file="../common/footer.jsp"%>
<div class="easyui-layout" data-options="fit:true">
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <c:forEach items="${buttonMenus}" var="buttonMenu">
                <a href="#" class="easyui-linkbutton" iconCls="${buttonMenu.icon}" onclick="${buttonMenu.url}" plain="true">${buttonMenu.name}</a>
            </c:forEach>
        </div>
        <div class="wu-toolbar-search">
            <label>标题：</label> <input class="wu-text" id="search-title" style="width:100px">
            <label>所属分类：</label>
            <select id="search-category" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <c:forEach items="${CategoryList }" var="category">
                    <option value="${category.id }">${category.name }</option>
                </c:forEach>
            </select>
            <label>博主:</label><input id="search-author" class="wu-text" style="width:100px">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>

    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>



<script type="text/javascript">


    <%--删除操作--%>
    function remove(){
        $.messager.confirm('信息提示','确定要删除该记录？', function(result){
            if(result){
                var item = $('#data-datagrid').datagrid('getSelections');
                if(item == null || item.length == 0){
                    $.messager.alert('信息提示','请选择要修改的数据！','info');
                    return;
                }
                $.ajax({
                    url:'delete',
                    dataType:'json',
                    type:'post',
                    data:{id:item[0].id},
                    success:function(data){
                        if(data.type == 'success'){
                            $.messager.alert('信息提示','删除成功！','info');
                            $('#data-datagrid').datagrid('reload');
                        }else{
                            $.messager.alert('信息提示',data.msg,'warning');
                        }
                    }
                });
            }
        });
    }
    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        window.location.href='add_article';
    }
    /*打开修改窗口*/
    function openEdit(){
        var item = $('#data-datagrid').datagrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要修改的数据！','info');
            return;
        }
        window.location.href='edit_article?id=' + item.id;
    }

    /*监听
    * */
    $("#search-btn").click(function(){
        var option = {title:$("#search-title").val(),categoryId:$("#search-category").combobox('getValue'),author:$("#search-author").val()};
        $('#data-datagrid').datagrid('reload',option);
    });
    /*
    * 创建数据网格
    * */
    $('#data-datagrid').datagrid({
        url:'list',
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        idField:'id',
        treeField:'name',
        fit:true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'title',title:'标题',width:300,formatter:function (value,row,index) {
                      return '<a href="${pageContext.request.contextPath}/home/article/article_detail?id='+row.id+'" target="_blank">'+value+'</a>'
                }},
            { field:'categoryId',title:'分类',width:80,formatter:function(value,row,index){
                    return row.category.name;
                }},
            { field:'author',title:'作者',width:80},
            { field:'tags',title:'标签',width:100},
            { field:'scannerNumber',title:'浏览量',sortable:true,width:30},
            { field:'commentNumber',title:'评论数',sortable:true,width:30},
        ]],
    });
</script>

