<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>


    $(function (){
        pageInit();
    });


    function pageInit(){
        //初始化一个表格
        $("#userTable").jqGrid({
            url : "${path}/log/queryAll",//后台接收到得 page：当前页 rows：每页展示条数


            datatype : "json",//后台返回得           page：当前页 rows：数据（list） total：总页数     records:总条数
            rowNum : 3,
            rowList : [3, 5,10, 20, 30 ],
            pager : '#userPage',//工具栏
            /*  sortname : 'id',
              mtype : "post",*/
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            viewrecords : true,//是否显示总条数
            colNames : [ 'ID', '用户名', '时间', '操作', '状态'],
            colModel : [
                {name : 'id',index : 'id',width : 55,align : "center"},
                {name : 'name',width : 80,align : "center"},
                {name : 'date',width : 80,align : "center"},
                {name : 'operation',width : 80,align : "center"},
                {name : 'status',width : 80,align : "center"}

            ]

        });

        //表格工具栏
        jQuery("#userTable").jqGrid('navGrid', '#userPage', {edit : false,add : false,del : false,addtext:"添加",deltext:"删除",edittext:"修改"},
            {},//修改之后的额外操作
            {},//添加
            {}
        );
    }

</script>


<%--初始化一个面板--%>
<div class="panel panel-warning">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h3>用户信息</h3>
    </div>

    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="#">用户信息 </a></li>
    </div>




    <%--初始化一个表单--%>
    <table id="userTable"/>
    <div id="userPage"/>
</div>