<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.6.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/js/echarts.js">
    </script>
    <script type="text/javascript">



        /*初始化Goueasy对象*/
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-de3f12ff1feb4992a6b5792b20b0250c", //替换为您的应用appkey
        });




        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));



            //接收消息
            goEasy.subscribe({
                channel: "186yingxxxx", //替换为您自己的channel
                onMessage: function (message) {
                    //    alert(message)
                    //    console.log("Channel:" + message.channel + " content:" + message.content);
                    //获取Goeasy信息
                    var datas=message.content;
                    //将json字符串转对象javaScript对象
                    var data = JSON.parse(datas);

                    var option = {
                        title: {
                            text: '用户注册统计',    //标题
                            subtext:'纯属虚构',   //副标题
                            link:'${path}/main/main.jsp',//主标题跳转路径
                            target:'self',//主标题跳转方式
                            sublink:'${path}/main/main.jsp'
                        },
                        tooltip: {},    //鼠标提示
                        legend: {
                            data:['男孩','女孩']    //上面选项卡
                        },
                        xAxis: {   //横轴
                            data: data.month
                        },
                        yAxis: {}, //纵轴  自适应
                        series: [{
                            name: '男孩',          //对应上面
                            type: 'line',  //bar柱状图  'line'
                            data: data.boy
                        },
                            {
                                name: '女孩',          //对应上面
                                type: 'bar',  //bar柱状图  'line'
                                data: data.girl
                            }

                        ]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);

                }
            });



            //    $.get("${path}/echarts/queryByUserNum",function (data) {

            //从这里剪切


            //     },"json")

            // 指定图表的配置项和数据

        });

    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 1000px;height:600px;"></div>

</body>
</html>