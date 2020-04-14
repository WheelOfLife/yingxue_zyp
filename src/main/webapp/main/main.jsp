<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <script src="${path}/bootstrap/js/jquery.gradientify.min.js"></script>
    <script>
        $(document).ready(function() {
            $("body").gradientify({
                gradients: [
                    { start: [49,76,172], stop: [242,159,191] },
                    { start: [255,103,69], stop: [240,154,241] },
                    { start: [33,229,241], stop: [235,236,117] }
                ]
            });
        });
    </script>
<%--小人特效--%>
    <script src="https://eqcn.ajz.miesnfu.com/wp-content/plugins/wp-3d-pony/live2dw/lib/L2Dwidget.min.js"></script>
    <!--小帅哥： https://unpkg.com/live2d-widget-model-chitose@1.0.5/assets/chitose.model.json-->
    <!--萌娘：https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json-->
    <!--小可爱（女）：https://unpkg.com/live2d-widget-model-koharu@1.0.5/assets/koharu.model.json-->
    <!--小可爱（男）：https://unpkg.com/live2d-widget-model-haruto@1.0.5/assets/haruto.model.json-->
    <!--初音：https://unpkg.com/live2d-widget-model-miku@1.0.5/assets/miku.model.json-->
    <!-- 上边的不同链接显示的是不同的小人，这个可以根据需要来选择 下边的初始化部分，可以修改宽高来修改小人的大小，或者是鼠标移动到小人上的透明度，也可以修改小人在页面出现的位置。 -->
    <script>
        /*https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json*/
        L2Dwidget
            .init({ "model": { jsonPath:
                    "https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json",
                "scale": 1 }, "display": { "position": "left", "width": 110, "height": 150,
                "hOffset": 0, "vOffset": -20 }, "mobile": { "show": true, "scale": 0.5 },
            "react": { "opacityDefault": 0.8, "opacityOnHover": 0.1 }

        });
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">

            <a class="navbar-brand " href="#"><small>应学app后台管理系统</small></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">


            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎：<span class="text-info">${admin.name}</span></a></li>
                <li> <a href="${path}/admin/logout">退出<span class="glyphicon glyphicon-share"></span></a></li>
            </ul>

        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

    <!--顶部导航-->

    <!--栅格系统-->
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
        <%--右边手风琴--%>
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" align="center">

                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                               用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li> <button class="btn btn-info btn-block">
                                <a href="javascript:$('#MainId').load('${path}/user/userShow.jsp')">用户展示</a>

                                </button></li>
                                <li><button class="btn btn-info btn-block">
                                    <a href="javascript:$('#MainId').load('${path}/test/Echart.jsp')">用户统计</a>
                                </button></li>
                                <li> <button class="btn btn-info btn-block">
                                    <a href="javascript:$('#MainId').load('${path}/test/china.jsp')">用户分布</a>
                                    </button></li>

                            </ul>
                        </div>
                    </div>
                </div>
<HR>
                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                分类管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <button class="btn btn-success btn-block">
                                <a href="javascript:$('#MainId').load('${path}/category/category.jsp')">分类展示</a>
                            </button>

                        </div>
                    </div>
                </div>
        <hr>
                <div class="panel panel-warning">
                <div class="panel-heading" role="tab" id="headingThree">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            视频管理
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                    <div class="panel-body">
                        <button class="btn btn-warning btn-block">
                        <a href="javascript:$('#MainId').load('${path}/video/video.jsp')">视频管理</a>
                        </button> <button class="btn btn-warning btn-block">
                        <a href="javascript:$('#MainId').load('${path}/video/searchVideo.jsp')">视频搜索</a>
                        </button>
                    </div>
                </div>
            </div>
<hr>
                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" >
                               日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse " role="tabpanel" >
                        <div class="panel-body">
                           <button class="btn btn-danger btn-block">
                           <a href="javascript:$('#MainId').load('${path}/log/Log.jsp')">日志管理</a>
                           </button>
                        </div>
                    </div>
                </div>
<hr>
                <div class="panel panel-primary">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" >
                                意见反馈
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse " role="tabpanel" >
                        <div class="panel-body">
                            <button class="btn btn-primary btn-block">意见反馈</button>
                        </div>
                    </div>
                </div>


            </div>
        </div>

            <%--左边--%>

            <!--巨幕开始-->

        <div class="col-md-10" id="MainId">
            <div class="jumbotron" align="center">
                <h1>欢迎来到应学app后台管理系统</h1>
            </div>
            <!--右边轮播图部分-->
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators中间按钮 -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides图片 -->
                <div class="carousel-inner" role="listbox">
                    <div class="item  " align="center" style="align-content: center;height: 600px">
                        <img src="${path}/bootstrap/img/pic11.jpg" alt="">
                        <div class="carousel-caption">

                        </div>
                    </div>
                    <div class="item " align="center" style="align-content: center;height: 600px" >
                        <img  src="${path}/bootstrap/img/pic22.jpg" alt="">
                        <div class="carousel-caption">

                        </div>
                    </div>

                    <div class="item" align="center" style="align-content: center;height: 600px">
                        <img src="${path}/bootstrap/img/pic33.jpg" alt="">
                        <div class="carousel-caption">

                        </div>
                    </div>
                    <div class="item active" align="center" style="align-content: center;height: 600px">
                        <img src="http://yxue-zhao.oss-cn-beijing.aliyuncs.com/photo/%E8%BD%A6%E7%AA%97%E5%A4%96.jpg" alt="">
                        <div class="carousel-caption">

                        </div>
                    </div>
                </div>

                <!-- Controls 前一页后一页按钮-->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
</div>

<!--页脚-->
<div class="panel panel-footer" align="center">
    <div>@百知教育 zhangcn@zparkhr.com</div>
</div>
        <!--页脚-->

    <!--栅格系统-->
</div>
</body>
</html>
