<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script>
   //单击搜索按钮
    $(function () {
        $("#btnId").click(function () {
            //清空表单
            $("#queryTable").empty();
            $("#queryTable").append("<tr> " +
                "<td>Id</td>" +
                "<td>标题</td> " +
                "<td>描述</td> " +
                "<td>封面</td> " +
                "<td>上传时间</td><" +
                "/tr>")

        //获取内容
         var content=$("#contentId").val();
          //向后台发送请求查询
            $.ajax({
                url:"${path}/video/querySearch",
                type:"post",
                datatype:"JSON",
                data:{"name":content },
                success:function (data) {
                    $.each(data,function (index,video) {
                      $("#queryTable").append("<tr> " +
                          "<td>"+video.id+"</td>" +
                          "<td>"+video.title+"</td> " +
                          "<td>"+video.brief+"</td> " +
                          "<td style='width: 103px'><img src='"+video.cover+"' style='height: 10%;width: 100%'/></td> " +
                          "<td>"+video.publishDate+"</td><" +
                          "/tr>")
                    })
                }
            })
            //获取数据渲染

        });
   });
</script>
<%--搜索框--%>
<div align="center">
    <div class="input-group" style="width: 350px" align="center">
        <input id="contentId" type="text" class="form-control" placeholder="请输入视频标题|简介" aria-describedby="basic-addon2">
        <span class="input-group-btn" id="basic-addon2">
            <button class="btn btn-info" id="btnId">点我查询视频</button>
            </span>
    </div>
</div>
<hr>

<%--展示搜寻内容--%>
<div class="panel panel-default">
    <!-- 面板标题 -->
    <div class="panel-heading"> 搜索</div>

    <!-- 搜索内容 -->
    <table class="table" id="queryTable">
   <%--     <tr>
            <td>Id</td>
            <td>标题</td>
            <td>描述</td>
            <td>封面</td>
            <td>上传时间</td>
        </tr>--%>

    </table>
</div>