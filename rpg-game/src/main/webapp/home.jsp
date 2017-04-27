<%@ page import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>works</title>
    <script>
        // click是关键字,不能作方法名
        function clickme(obj) {
            window.location.href = "<%=path%>/move.do?n=" + obj.value;
        }
    </script>
</head>
<body>
<h2>works!</h2>
<div>
    <input type="button" value="8" onclick="clickme(this);"/>
    <input type="button" value="4" onclick="clickme(this);"/>
    <input type="button" value="6" onclick="clickme(this);"/>
    <input type="button" value="2" onclick="clickme(this);"/>
</div>
<hr />
<div id="mapArea">

</div>
</body>
</html>