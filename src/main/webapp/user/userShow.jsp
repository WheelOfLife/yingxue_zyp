<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    function updateonee(id,status) {
        if (status==1){
            $.ajax({
                url:"${path}/user/edit",
                type:"post",
                data:{"id":id,"status":"0","oper":"status"},
                success:function () {
                    $("#userTable").trigger("reloadGrid");
                }
            })
        }else {
            $.ajax({
                url:"${path}/user/edit",
                type:"post",
                data:{"id":id,"status":"1","oper":"status"},
                success:function () {
                    $("#userTable").trigger("reloadGrid");
                }
            })
        }
    }


    $(function (){
        pageInit();
    });


    function pageInit(){
        //初始化一个表格
        $("#userTable").jqGrid({
                url : "${path}/user/show",//后台接收到得 page：当前页 rows：每页展示条数
               editurl: "${path}/user/edit",

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
                colNames : [ 'ID', '用户名', '电话', '头像', '签名','微信', '状态' ,'创建时间' ],
                colModel : [
                    {name : 'id',index : 'id',width : 55,align : "center"},
                    {name : 'username',width : 80,align : "center",editable:true},
                    {name : 'phone',width : 80,align : "center",editable:true},
                    {name : 'headImg',width : 80,align : "center",editable:true,edittype:"file",
                        formatter:function(cellvalue, options, rowObject){
                            return "<img src='"+cellvalue+"' width='100px' height='100px'/>";
                            }
                        },
                    {name : 'sign',width : 80,align : "center",editable:true},
                    {name : 'wechat',width : 80,align : "center",editable:true},
                    {name : 'status',width : 80,align : "center",
                        formatter:function(cellvalue, options, rowObject){
                        if(cellvalue=="1"){//1正常 0冻结
                            return "<button onclick='updateonee(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-danger'>冻结</button>";
                        }else {
                            return "<button onclick='updateonee(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-success'>解除冻结</button>";
                        }

                        }
                    },
                    {name : 'createDate',width : 90,align : "center"},
                ]

            });

            //表格工具栏
        jQuery("#userTable").jqGrid('navGrid', '#userPage', {edit : true,add : true,del : true,addtext:"添加",deltext:"删除",edittext:"修改"},
            {
                closeAfterEdit:true,


                //文件上传
                afterSubmit:function (response) {

                    // 数据的入库 图片路径不对
                    //在提交后文件上传
                    //修改图片路径
                    //fileElementId　　　　　  需要上传的文件域的ID，即<input type="file">的ID。
                    //  console.log(response);     添加完返回了id   responseText: "b0f88c00442a481dba06c9764b2a8892"


                    if (response.responseText==""){
                        console.log("我是空字符");
                    }else {
                        $.ajaxFileUpload({
                            url: "${path}/user/upload",
                            type:"post",
                            datatype: "json",
                            fileElementId:"headImg",//上传框id
                            data : {id:response.responseText},
                            success:function () {

                                $("#userTable").trigger("reloadGrid");

                            }
                        })
                    }


                    //想添加完关必须有返回值
                    return "hell";

                }


            },       //修改之后的额外操作
            {
                closeAfterAdd:true,
                //文件上传
             afterSubmit:function (response) {

                   // 数据的入库 图片路径不对
                     //在提交后文件上传
                 //修改图片路径
                 //fileElementId　　　　　  需要上传的文件域的ID，即<input type="file">的ID。
               //  console.log(response);     添加完返回了id   responseText: "b0f88c00442a481dba06c9764b2a8892"


                 $.ajaxFileUpload({
                     url: "${path}/user/upload",
                     type:"post",
                     datatype: "json",
                     fileElementId:"headImg",//上传框id
                     data : {id:response.responseText},
                     success:function () {

                        $("#userTable").trigger("reloadGrid");

                     }
                 })
                 //想添加完关必须有返回值
                 return "helli";
             }
            },//添加

            {}
            );
    }

</script>
<script>
        $(function () {
            $("#PhoneCode").click(function () {
           var phone=  $("#codeId").val();

               $.ajax({
                   url:"${path}/user/sendPhoneCode",
                   type:"post",
                   datatype:"json",
                   data:{"phoneNum":phone},
                   success:function (data) {

                        alert(data);
                   }
               })
            });
        });
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



        <div class="row">
            <div class="col-sm-4">
                <button class="btn btn-info" >导出用户信息</button>
            </div>


        <div class="col-sm-4 col-sm-offset-4" >
            <div class="input-group right" >
                <input id="codeId" type="text" class="form-control" placeholder="请输入手机号...">
                <span class="input-group-btn">
        <button id="PhoneCode" class="btn btn-success" type="button">发送验证码</button>
      </span>
            </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->



        </div>
        <%--初始化一个表单--%>
    <table id="userTable"/>
    <div id="userPage"/>
</div>