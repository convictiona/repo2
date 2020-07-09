<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/7
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/6
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp"%>
<div class="easyui-panel" title="修改页面" iconCls="icon-add" fit="true" >
    <div style="padding:10px 60px 20px 60px">
        <form id="edit-form" method="post">
            <input type="hidden" name="id" value="${article.id }">
            <table cellpadding="5">
                <tr>
                    <td>标题:</td>
                    <td><input class="wu-text easyui-textbox easyui-validatebox" type="text" name="title" data-options="required:true,missingMessage:'请填写标题'" value="${article.title }"></input></td>
                </tr>
                <tr>
                    <td width="60" align="right">分类:</td>
                    <td>
                        <select name="categoryId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择所属分类'">
                            <c:forEach items="${CategoryList }" var="category">
                                <c:if test="${article.categoryId == category.id }">
                                    <option value="${category.id }" selected >${category.name }</option>
                                </c:if>
                                <c:if test="${article.categoryId != category.id }">
                                    <option value="${category.id }" >${category.name }</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>摘要:</td>
                    <td>
                        <textarea name="description" rows="6" class="wu-textarea easyui-validatebox" style="width:260px" data-options="required:true,missingMessage:'请填写摘要'">${article.description }</textarea>
                    </td>
                </tr>
                <tr>
                    <td>新闻标签:</td>
                    <td><input class="wu-text easyui-textbox easyui-validatebox" type="text" name="tags" data-options="required:true,missingMessage:'请填写标签'" value="${article.tags }"></input></td>
                </tr>
                <tr>
                    <td>文章封面:</td>
                    <td>
                        <input class="wu-text easyui-textbox easyui-validatebox" type="text" id="add-photo" name="photo" readonly="readonly" value="/travleblog_war_exploded/resources/upload/1586155665096.jpg" data-options="required:true,missingMessage:'请上传封面'"></input>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()">上传</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-photo" onclick="preview()">预览</a>
                    </td>
                </tr>
                <tr>
                    <td>博主:</td>
                    <td><input class="wu-text easyui-textbox easyui-validatebox" type="text" name="author" data-options="required:true,missingMessage:'请填写新闻作者'" value="${article.author }"></input></td>
                </tr>
                <tr>
                    <td>内容:</td>
                    <td>
                        <textarea id="edit-content" name="content"  style="width:760px;height:300px;" >${article.content }</textarea>
                    </td>
                </tr>
            </table>
        </form>
        <div style="padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="submitForm()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back"  onclick="back()">返回</a>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp"%>
<!-- 预览图片弹窗 -->
<div id="preview-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:330px; padding:10px;">
    <table>
        <tr>
            <td><img id="preview-photo" src="${article.photo}" width="300px"></td>
        </tr>
    </table>
</div>

<div id="process-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-upload',title:'正在上传图片'" style="width:450px; padding:10px;">
    <div id="p" class="easyui-progressbar" style="width:400px;" data-options="text:'正在上传中...'"></div>
</div>


<input type="file" id="photo-file" style="display: none;" onchange="upload()">
<script type="text/javascript" src="../../resources/admin/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="../../resources/admin/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
    var ue = UE.getEditor('edit-content');
    function back(){
        window.history.back(-1);
    }
    function preview(){
        $('#preview-dialog').dialog({
            closed: false,
            modal:true,
            title: "预览封面图片",
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#preview-dialog').dialog('close');
                }
            }],
        });
    }

    function start(){
        var value = $('#p').progressbar('getValue');
        if (value < 100){
            value += Math.floor(Math.random() * 10);
            $('#p').progressbar('setValue', value);
        }else{
            $('#p').progressbar('setValue',0)
        }
    };
    function upload(){
        if($("#photo-file").val() == '')return;
        var formData = new FormData();
        formData.append('photo',document.getElementById('photo-file').files[0]);
        $("#process-dialog").dialog('open');
        var interval = setInterval(start,200);
        $.ajax({
            url:'upload_photo',
            type:'post',
            data:formData,
            contentType:false,
            processData:false,
            success:function(data){
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                if(data.type == 'success'){
                    $("#preview-photo").attr('src',data.filepath);
                    $("#edit-photo").val(data.filepath);
                }else{
                    $.messager.alert("消息提醒",data.msg,"warning");
                }
            },
            error:function(data){
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                $.messager.alert("消息提醒","上传失败!","warning");
            }
        });
    }

    function uploadPhoto(){
        $("#photo-file").click();
    }

    function submitForm(){
        var validate = $("#edit-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var content = ue.getContent();
        if(content == ''){
            $.messager.alert("消息提醒","请输入新闻内容!","warning");
            return;
        }
        var data = $("#edit-form").serialize();
        $.ajax({
            url:'edit',
            type:'post',
            dataType:'json',
            data:data,
            success:function(rst){
                if(rst.type == 'success'){
                    $.messager.alert("消息提醒","修改成功!","warning");
                    setTimeout(function(){
                        window.history.go(-1);
                    },500);
                }else{
                    $.messager.alert("消息提醒",rst.msg,"warning");
                }
            }
        });
    }
</script>
