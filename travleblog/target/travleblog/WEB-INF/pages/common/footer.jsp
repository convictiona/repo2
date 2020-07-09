<%--
  Created by IntelliJ IDEA.
  User: 吴
  Date: 2020/4/1
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#FFFFFF;text-align :center;padding-top:20%;">
    <img src="../../resources/admin/easyui/images/3.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
</body>
<script>
    var pc;
    //不要放在$(function(){});中
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 1000);
    }
    function closes() {
        $('#loading').fadeOut('normal', function () {
            $(this).remove();
        });
    }
</script>
