<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        //初始化表格方法j
        pageInit();
    });
    function pageInit(){


        //父表 格
        $("#cateTable").jqGrid({
                url : "${path}/category/queryByCategory",
                editurl: "${path}/category/editParent",
                datatype : "json",
                rowNum : 8,
                styleUI:"Bootstrap",
                height:"auto",
                autowidth:true,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#catePage',
                viewrecords : true,
                colNames : [ 'Id', '类别名', '级别'  ],
                colModel : [
                    {name : "id",  index : "num",align : "center"},
                    {name : "cateName",index : "item",  align : "center",editable:true},
                    {name : "levels",index : "qty",align : "center"}
                ],
                subGrid : true,     //是否开启子表格
                subGridRowExpanded : function(subgrid_id, row_id) {//设置子表格相关属性

                    //复制子表格内容
                    addSunGrid(subgrid_id,row_id);

                }
            });
        //父表格分页工具栏
        $("#cateTable").jqGrid('navGrid', '#catePage', {edit : true,add : true,del : true,addtext:"添加",deltext:"删除",edittext:"修改"},
            {closeAfterEdit:true},
            {closeAfterAdd:true},
            {
                closeAfterDel:true,
                afterSubmit:function (response) {
                    if (response.responseText!=""){
                        alert(response.responseText);
                    }
                    return"dddd";
                }
            }

        );

        function addSunGrid(subgridId,rowId) {
                /*
                 * subgrid_id 点击一行时候会在父表格创建一个div，来容纳子表格的，subgrid_id就是div的id
                 * row_id  点击行的id
                 * */

            // we pass two parameters
            // subgrid_id is a id of the div tag created whitin a table data
            // the id of this elemenet is a combination of the "sg_" + id of the row
            // the row_id is the id of the row
            // If we wan to pass additinal parameters to the url we can use
            // a method getRowData(row_id) - which returns associative array in type name-value
            // here we can easy construct the flowing
            var subgridTableId=subgridId + "Table";//定义子表格的Table的id
            var pagerId="" + subgridId+"Page";//定义子表格工具栏的id
                //在子表格容器中创建子表格和子表格分页工具栏
            $("#" + subgridId).html("<table id='"+subgridTableId+"'/> <div id='"+pagerId+"'/>");



            //子表格
            $("#" + subgridTableId).jqGrid({
                url:"${path}/category/queryByCategory?categoryStatus="+rowId,
                editurl: "${path}/category/editSun?categoryStatus="+rowId,
                datatype : "json",
                rowNum : 20,
                pager : "#"+pagerId,
                styleUI:"Bootstrap",
                height:"auto",
                autowidth:true,
                viewrecords : true,
                colNames : [ 'Id', '类别名', '级别',"上级类别id" ],
                colModel : [
                    {name : "id",  index : "num",align : "center"},
                    {name : "cateName",index : "item",  align : "center",editable:true},
                    {name : "levels",index : "qty",align : "center"},
                    {name : "parentId",index : "qty",align : "center"}

                ],

            });
            jQuery("#" + subgridTableId).jqGrid('navGrid', "#" + pagerId, {edit : true,add : true,del : true,addtext:"添加",deltext:"删除",edittext:"修改"},
                {closeAfterEdit:true},//修改
                {closeAfterAdd:true},//添加
                {
                    closeAfterDel:true,
                    afterSubmit:function (response) {
                        if (response.responseText!=""){
                            alert(response.responseText);
                        }
                        return"del";
                    }
                }
            );


        }
    }
</script>


<%--初始化一个面板--%>
<div class="panel panel-info">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h3 >类别信息</h3>
    </div>

    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active "><a href="#">类别信息 </a></li>
    </div>


    <%--初始化一个表单--%>
    <table id="cateTable"/>
    <div id="catePage"/>
</div>