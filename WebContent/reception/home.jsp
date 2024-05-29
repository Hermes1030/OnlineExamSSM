<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Insert title here</title>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    %>
    <c:set var="path" value="<%=basePath %>"></c:set>
    <link href="${path }/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Rosario:700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="index_bg/script-f.css">

</head>
<body style="background-color: #EEEEEE;">
<div class="jumbotron" style="height: 285px;
    padding-top: 120px;
    padding-bottom: 0px;
    margin-bottom: 0px;">
    <%--	首页背景 --%>
    <h1>My <span class="type-animation animating"> Examination</span> System</h1>
    <script src="index_bg/script-f.js"></script>
</div>
<%--	底部图片--%>
<div>
    <img style="width: 100vw;height: 50vh;margin: 0;margin-top: 45px;" src="../images/reception/svg.png">
</div>

<!-- js引入 -->
<script src="${path }/js/jquery.js"></script>
<script src="${path }/js/bootstrap/bootstrap.min.js"></script>
</body>
</html>