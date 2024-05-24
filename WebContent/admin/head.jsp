<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>后台首页头部</title>
    <%
        // 获取当前web应用的根路径
        String path = request.getContextPath();
        // 构建当前web应用的完整URL地址
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    %>
    <c:set var="path" value="<%=basePath %>"></c:set>
    <link href="${path }/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="logout/style.css"/>
    <style type="text/css">
        a {
            text-decoration: none;
        }

        a:HOVER {
            text-decoration: none;
        }

        a:link {
            text-decoration: none;
        }

        a:visited {
            text-decoration: none;
        }

        a:active {
            text-decoration: none;
        }
    </style>
</head>
<body>
<div style="width: 100%;height: 100%;background-color: #333;">
    <div style="float: left; width: 380px; height: 100%;line-height:80px;margin-left: -40px;">
        <a href="index.jsp" target="_top">
            <img src="${path }/images/reception/logo_2.png" alt="Werido logo"
                 style="width: 91%;height: 85%;margin: 4px -1px 1px 15px;"/>
        </a>
    </div>
    <%--		<div id="head-nav-show" style="float: left; width: 300px; height: 100%;margin-left: 112px;">--%>
    <%--			<div style="margin: 71px 630px;width: 242px;height: 26px;line-height: 18px;text-align: center;">--%>
    <%--				<span id="date_time" style="color: #FFF;"></span>--%>
    <%--			</div>--%>
    <%--		</div>--%>

    <%--		<div id="loginTeacher" style="float:right;width: 260px;height:100%;line-height: 44px;">--%>
    <%--			<c:if test="${sessionScope.loginTeacher != null }">--%>
    <%--				<a target="right" class="btn btn-default" href="../teachers" style="margin: 33px 1px 1px 1px;">--%>
    <%--					${sessionScope.loginTeacher.teacherName }--%>
    <%--				</a>--%>
    <%--			</c:if>--%>
    <%--			<c:if test="${sessionScope.loginTeacher == null }">--%>
    <%--				<a target="right" class="btn btn-default" href="#" style="margin-left: 9px;">--%>
    <%--					未登录--%>
    <%--				</a>--%>
    <%--			</c:if>--%>
    <%--			<a class="btn btn-default" href="../exitTeacher" target="_parent">退出登录</a>--%>
    <div class="background background--dark" style="display: flex;justify-content: flex-end;width: 74%;">
        <a href="../exitTeacher" target="_parent">
        <button class="logoutButton logoutButton--light">
                <svg class="doorway" viewBox="0 0 100 100">
                    <path d="M93.4 86.3H58.6c-1.9 0-3.4-1.5-3.4-3.4V17.1c0-1.9 1.5-3.4 3.4-3.4h34.8c1.9 0 3.4 1.5 3.4 3.4v65.8c0 1.9-1.5 3.4-3.4 3.4z"/>
                    <path class="bang"
                          d="M40.5 43.7L26.6 31.4l-2.5 6.7zM41.9 50.4l-19.5-4-1.4 6.3zM40 57.4l-17.7 3.9 3.9 5.7z"/>
                </svg>
                <svg class="figure" viewBox="0 0 100 100">
                    <circle cx="52.1" cy="32.4" r="6.4"/>
                    <path d="M50.7 62.8c-1.2 2.5-3.6 5-7.2 4-3.2-.9-4.9-3.5-4-7.8.7-3.4 3.1-13.8 4.1-15.8 1.7-3.4 1.6-4.6 7-3.7 4.3.7 4.6 2.5 4.3 5.4-.4 3.7-2.8 15.1-4.2 17.9z"/>
                    <g class="arm1">
                        <path d="M55.5 56.5l-6-9.5c-1-1.5-.6-3.5.9-4.4 1.5-1 3.7-1.1 4.6.4l6.1 10c1 1.5.3 3.5-1.1 4.4-1.5.9-3.5.5-4.5-.9z"/>
                        <path class="wrist1"
                              d="M69.4 59.9L58.1 58c-1.7-.3-2.9-1.9-2.6-3.7.3-1.7 1.9-2.9 3.7-2.6l11.4 1.9c1.7.3 2.9 1.9 2.6 3.7-.4 1.7-2 2.9-3.8 2.6z"/>
                    </g>
                    <g class="arm2">
                        <path d="M34.2 43.6L45 40.3c1.7-.6 3.5.3 4 2 .6 1.7-.3 4-2 4.5l-10.8 2.8c-1.7.6-3.5-.3-4-2-.6-1.6.3-3.4 2-4z"/>
                        <path class="wrist2"
                              d="M27.1 56.2L32 45.7c.7-1.6 2.6-2.3 4.2-1.6 1.6.7 2.3 2.6 1.6 4.2L33 58.8c-.7 1.6-2.6 2.3-4.2 1.6-1.7-.7-2.4-2.6-1.7-4.2z"/>
                    </g>
                    <g class="leg1">
                        <path d="M52.1 73.2s-7-5.7-7.9-6.5c-.9-.9-1.2-3.5-.1-4.9 1.1-1.4 3.8-1.9 5.2-.9l7.9 7c1.4 1.1 1.7 3.5.7 4.9-1.1 1.4-4.4 1.5-5.8.4z"/>
                        <path class="calf1"
                              d="M52.6 84.4l-1-12.8c-.1-1.9 1.5-3.6 3.5-3.7 2-.1 3.7 1.4 3.8 3.4l1 12.8c.1 1.9-1.5 3.6-3.5 3.7-2 0-3.7-1.5-3.8-3.4z"/>
                    </g>
                    <g class="leg2">
                        <path d="M37.8 72.7s1.3-10.2 1.6-11.4 2.4-2.8 4.1-2.6c1.7.2 3.6 2.3 3.4 4l-1.8 11.1c-.2 1.7-1.7 3.3-3.4 3.1-1.8-.2-4.1-2.4-3.9-4.2z"/>
                        <path class="calf2"
                              d="M29.5 82.3l9.6-10.9c1.3-1.4 3.6-1.5 5.1-.1 1.5 1.4.4 4.9-.9 6.3l-8.5 9.6c-1.3 1.4-3.6 1.5-5.1.1-1.4-1.3-1.5-3.5-.2-5z"/>
                    </g>
                </svg>
                <svg class="door" viewBox="0 0 100 100">
                    <path d="M93.4 86.3H58.6c-1.9 0-3.4-1.5-3.4-3.4V17.1c0-1.9 1.5-3.4 3.4-3.4h34.8c1.9 0 3.4 1.5 3.4 3.4v65.8c0 1.9-1.5 3.4-3.4 3.4z"/>
                    <circle cx="66" cy="50" r="3.7"/>
                </svg>
                <span class="button-text">退出</span>
        </button>
        </a>
    </div>
</div>


<!-- js引入 -->
<script src="${path }/js/jquery.js"></script>
<script src="${path }/js/bootstrap/bootstrap.min.js"></script>
<%--    <script type="text/javascript">--%>
<%--    	date_time();--%>
<%--    	$(function() {--%>
<%--    		$("#loginTeacher").mouseover(function() {--%>
<%--    			$(this).css("border-left", "2px solid #EC870E");--%>
<%--    		}).mouseout(function() {--%>
<%--    			$(this).css("border-left", "0px solid #EC870E");    			--%>
<%--    		});--%>
<%--    		$("#head-nav-show").mouseover(function() {--%>
<%--    			if($(".index_title").size() <= 0) {--%>
<%--    				return false;--%>
<%--    			}--%>
<%--    			$(this).css("border-left", "2px solid #EC870E");--%>
<%--    		}).mouseout(function() {--%>
<%--    			$(this).css("border-left", "0px solid #EC870E");    			--%>
<%--    		});--%>
<%--    	});--%>
<%--    	--%>
<%--    	function removeParent(i) {--%>
<%--    		//当前页处于显示状态，无法删除--%>
<%--    		if ($("#it"+i).attr("show") == "t") {--%>
<%--    			return;--%>
<%--    		}--%>
<%--    		$("#it"+i).remove();--%>
<%--    	}--%>
<%--    	--%>
<%--    	function changeShow(i) {--%>
<%--    		$(".index_title").css("background-color", "#FFF").attr("show", "f");--%>
<%--    		$("#it"+i).css("background-color", "#CAE5E8").attr("show", "t");--%>
<%--    	}--%>
<%--    	--%>
<%--    	setInterval("date_time()", 1000);--%>
<%--    	--%>
<%--    	function date_time() {--%>
<%--    		var date = new Date();--%>
<%--    		var year = date.getFullYear();--%>
<%--    		var month = date.getMonth()+1;--%>
<%--    		var day = date.getDate();--%>
<%--    		var hour = date.getHours();--%>
<%--    		var minutes = date.getMinutes();--%>
<%--    		var seconds = date.getSeconds();--%>
<%--    		if(parseInt(seconds) >= 0 && parseInt(seconds) < 10) {--%>
<%--    			seconds = "0"+seconds;--%>
<%--    		}--%>
<%--    		$("#date_time").text(year+"年"+month+"月"+day+"日"+hour+"时"+minutes+"分"+seconds+"秒");--%>
<%--    	}--%>
<%--    </script>--%>
<script src="logout/script.js"></script>
</body>
</html>