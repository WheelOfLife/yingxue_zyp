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
    <script src="${path}/js/echarts.js"></script>
    <script src="${path}/js/china.js"></script>
    <script type="text/javascript">
        $( $.get("${path}/echarts/queryByUserMap",function (data) {

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            var series=[];
            for (var i =0;i<data.length;i++){
                var e =data[i];
                series.push(
                    {
                        name: e.title,
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        }, data:e.city
                    })

            }

            var option = {
                title : {
                    text: '每月用户注册分布图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['男孩','女孩']
                },
                visualMap: {
                    min: 0,
                    max: 15,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],           // 文本，默认为数值文本
                    calculable : true,


                },
                toolbox: {
                    show: true,
                    orient : 'vertical',
                    left: 'right',
                    top: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : series
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

        },"json"))
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
                    //获取Goeasy信息
                    var datas=message.content;
                    //将json字符串转对象javaScript对象
                    var data = JSON.parse(datas);

                    var series=[];
                    for (var i =0;i<data.length;i++){
                        var e =data[i];
                        series.push(
                            {
                                name: e.title,
                                type: 'map',
                                mapType: 'china',
                                label: {
                                    normal: {
                                        show: false
                                    },
                                    emphasis: {
                                        show: true
                                    }
                                }, data:e.city
                            })

                    }

                    var option = {
                        title : {
                            text: '每月用户注册分布图',
                            subtext: '纯属虚构',
                            left: 'center'
                        },
                        tooltip : {
                            trigger: 'item'
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data:['男孩','女孩']
                        },
                        visualMap: {
                            min: 0,
                            max: 1500,
                            left: 'left',
                            top: 'bottom',
                            text:['高','低'],           // 文本，默认为数值文本
                            calculable : true,


                        },
                        toolbox: {
                            show: true,
                            orient : 'vertical',
                            left: 'right',
                            top: 'center',
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        series : series
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);



                }
            });





        });

    </script>

</head>
<body>
<!-- 为 ECharts 准 备一个具备大小（宽高）的 DOM -->
<div align="center">
    <div id="main" style="width: 1000px;height:600px;"></div>
</div>


</body>
</html>