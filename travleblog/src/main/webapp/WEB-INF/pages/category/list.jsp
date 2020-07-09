<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/5
  Time: 16:17
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
            <label>分类名：</label><input class="wu-text" id="search-name" style="width:100px">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>

    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 添加窗口 -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px;
        padding:10px;">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">分类名:</td>
                <td><input type="text" name="name" class="wu-text easyui-validatebox" data-options="required:true,
        missingMessage:'请填写分类名'" /></td>
            </tr>
            <tr>
                <td width="60" align="right">排序:</td>
                <td><input type="text" name="sort" class="wu-text easyui-numberbox easyui-validatebox " value="0" data-options="required:true,
        missingMessage:'请填写排序'" /></td>
            </tr>
        </table>
    </form>
</div>
<%--   修改窗口--%>
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table>
            <tr>
                <td width="60" align="right">分类名:</td>
                <td><input type="text" name="name" id="edit-name" class="wu-text easyui-validatebox" data-options="required:true,
        missingMessage:'请填写分类名'" /></td>
            </tr>
            <tr>
                <td width="60" align="right">排序:</td>
                <td><input type="text" name="sort" id="edit-sort" class="wu-text  easyui-validatebox " value="0" data-options="required:true,
        missingMessage:'请填写排序'" /></td>
            </tr>
        </table>
    </form>
</div>

<script type="text/javascript">




    /**
     * 添加记录
     */
    function add(){
        var validate = $("#add-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url:'add',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','添加成功！','info');
                    $('#add-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }
    /*修改*/
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
// $('#add-form').form('clear');
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加用户信息",
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
            onBeforeOpen:function () {
                $("#add-form input").val('');

            }
        });
    }
    /*打开修改窗口*/
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
            title: "修改用户信息",
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
                $("#edit-name").val(item.name);
                $("#edit-sort").val(item.sort);
            }
        });
    }

    /*监听
    * */
    $("#search-btn").click(function(){
        var option = {name:$("#search-name").val()};
        $('#data-datagrid').datagrid('reload',option);
    });
    /*
    * 创建数据网格
    * */
    $('#data-datagrid').datagrid({
        url: 'list',
        rownumbers: true,
        singleSelect: true,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        idField: 'id',
        treeField: 'name',
        fit: true,
        columns: [[
            { field:'chk',checkbox:true},
            {field: 'name', title: '分类名', width: 100, sortable: true,formatter:function (value,row,index) {
                    return '<a href="${pageContext.request.contextPath}/home/article/CategoryList?Id='+row.id+'" target="_blank">'+value+'</a>'
                }},
            {field: 'sort', title: '排序', width: 100,sortable: true},
        ]],
    });
</script>
