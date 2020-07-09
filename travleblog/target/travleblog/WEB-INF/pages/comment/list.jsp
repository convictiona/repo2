<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/8
  Time: 10:28
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
            <label>昵称:</label><input id="search-nickname" class="wu-text" style="width:100px">
            <label>内容:</label><input id="search-content" class="wu-text" style="width:100px">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>

    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>

<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">评论文章:</td>
                <td>
                    <select name="articleId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择要评论的文章'">
                        <c:forEach items="${articleList }" var="article">
                            <option value="${article.id }">${article.title }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">昵称:</td>
                <td><input type="text" name="commentName" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写评论昵称'" /></td>
            </tr>
            <tr>
                <td>内容:</td>
                <td>
                    <textarea name="content" rows="6" class="wu-textarea easyui-validatebox" style="width:260px" data-options="required:true,missingMessage:'请填写评论内容'"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table>
            <tr>
                <td width="60" align="right">评论文章:</td>
                <td>
                    <select id="edit-articleId" name="articleId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择要评论的文章'">
                        <c:forEach items="${articleList }" var="article">
                            <option value="${article.id }">${article.title }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">昵称:</td>
                <td><input type="text" id="edit-commentName" name="commentName" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写评论昵称'" /></td>
            </tr>
            <tr>
                <td>内容:</td>
                <td>
                    <textarea id="edit-content" name="content" rows="6" class="wu-textarea easyui-validatebox" style="width:260px" data-options="required:true,missingMessage:'请填写评论内容'"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
function add() {
    var  validate=$("#add-form").form("validate");
    if (!validate){
        $.messager.alert("信息提示！","请检查你输入的数据");
        return ;
    }
    var data=$("#add-form").serialize();
    console.log("-----------------",data);
    $.ajax({
        url:'add',
        dataType:'json',
        type:'post',
        data:data,
        success:function (data) {
            if (data.type =='success'){
                $.messager.alert('信息提示！','添加成功','info');
                $('#add-dialog').dialog('close');
                $('#data-datagrid').datagrid('reload');
            }else{
                $.messager.alert('信息提示',data.msg,'warning');
            }
        }
    });
}
function edit(){
    var validate = $("#edit-form").form("validate");
    if(!validate){
        $.messager.alert("消息提醒","请检查你输入的数据!","warning");
        return;
    }
    var data = $("#edit-form").serialize();
    $.ajax({
        url:'edit',
        dataType:'json',
        type:'post',
        data:data,
        success:function(data){
            if(data.type == 'success'){
                $.messager.alert('信息提示','修改成功！','info');
                $('#edit-dialog').dialog('close');
                $('#data-datagrid').datagrid('reload');
            }else{
                $.messager.alert('信息提示',data.msg,'warning');
            }
        }
    });
}

function openAdd(){
    //$('#add-form').form('clear');
    $('#add-dialog').dialog({
        closed: false,
        modal:true,
        title: "添加分类信息",
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: add
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $('#add-dialog').dialog('close');
            }
        }],
        onBeforeOpen:function(){
            //$("#add-form input").val('');
        }
    });
}
function openEdit(){
    //$('#edit-form').form('clear');
    var item = $('#data-datagrid').datagrid('getSelections');
    if(item == null || item.length == 0){
        $.messager.alert('信息提示','请选择要修改的数据！','info');
        return;
    }
    if(item.length > 1){
        $.messager.alert('信息提示','请选择一条数据进行修改！','info');
        return;
    }
    item = item[0];
    $('#edit-dialog').dialog({
        closed: false,
        modal:true,
        title: "修改评论信息",
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: edit
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $('#edit-dialog').dialog('close');
            }
        }],
        onBeforeOpen:function(){
            $("#edit-id").val(item.id);
            $("#edit-commentName").val(item.commentName);
            $("#edit-content").val(item.content);
            $("#edit-articleId").combobox('setValue',item.articleId);
        }
    });
}

function remove(){
    $.messager.confirm('信息提示','确定要删除该记录？', function(result){
        if(result){
            var item = $('#data-datagrid').datagrid('getSelections');
            if(item == null || item.length == 0){
                $.messager.alert('信息提示','请选择要删除的数据！','info');
                return;
            }
            var ids = '';
            for(var i=0;i<item.length;i++){
                ids += item[i].id + ',';
            }
            $.ajax({
                url:'delete',
                dataType:'json',
                type:'post',
                data:{ids:ids},
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

$("#search-btn").click(function(){
    var option = {commentName:$("#search-commentName").val(),content:$("#search-content").val()};
    $('#data-datagrid').datagrid('reload',option);
});
function add0(m){return m<10?'0'+m:m }
function format(shijianchuo){
    //shijianchuo是整数，否则要parseInt转换
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}
$('#data-datagrid').datagrid({
    url:'list',
    rownumbers:true,
    singleSelect:false,
    pageSize:20,
    pagination:true,
    multiSort:true,
    fitColumns:true,
    idField:'id',
    treeField:'name',
    fit:true,
    columns:[[
        { field:'chk',checkbox:true},
        { field:'articleId',title:'评论文章',width:100,formatter:function(value,row,index){
                return row.article.title;
            }},
        { field:'commentName',title:'评论昵称',width:100,sortable:true},
        { field:'content',title:'评论内容',sortable:true,width:100},
        { field:'commentTime',title:'评论时间',sortable:true,width:100,formatter:function(value,row,index){
                return format(value);
            }},
    ]],
});
</script>