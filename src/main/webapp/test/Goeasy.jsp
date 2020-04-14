<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.6.js"></script>
<script>
    /*初始化Goueasy对象*/
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-de3f12ff1feb4992a6b5792b20b0250c", //替换为您的应用appkey
    });

    //接收消息
    goEasy.subscribe({
        channel: "186yingxxxx", //替换为您自己的channel
        onMessage: function (message) {
alert(message)
            console.log("Channel:" + message.channel + " content:" + message.content);
        }
    });
</script>

<!doctype html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>

</body>
</html>