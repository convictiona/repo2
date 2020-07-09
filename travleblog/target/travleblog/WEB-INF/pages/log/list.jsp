<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/4
  Time: 16:38
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
            <label>日志内容：</label><input class="wu-text" id="search-name" style="width:100px">
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
                <td align="right">日志内容:</td>
                <td><textarea  id="add-content" name="content" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<%--   修改窗口--%>
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px;
        padding:10px;">
    <form id="edit-form" method="post">
        <table>
            <tr>
                <td align="right">日志内容:</td>
                <td><textarea name="content" id="edit-content" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
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
                    $("#add-content").val('');
                    $('#add-dialog').dialog('close');
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
                var ids='';
                for(var i=0;i<item.length;i++){
                    ids +=item[i].id+',';

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



    /*监听
    * */
    $("#search-btn").click(function(){
        var option = {content:$("#search-name").val()};
        $('#data-datagrid').datagrid('reload',option);
    });
    /*
    * 创建数据网格
    * */
    $('#data-datagrid').datagrid({
        url: 'list',
        rownumbers: true,
        singleSelect: false,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        idField: 'id',
        treeField: 'name',
        fit: true,
        columns: [[
            {field: 'chk',checkbox:true},
            {field: 'content', title: '日志内容', width: 100, sortable: true},
            {
                field: 'createTime', title: '日志时间', width: 100, formatter: function (value, row, index) {
                    return format(value);
                }
            }
        ]],
    })
</script>
