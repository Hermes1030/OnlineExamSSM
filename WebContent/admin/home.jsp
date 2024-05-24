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
    <style type="text/css">
        .totalDiv {
            height: 100px;
            border: 1px solid #DDDDDD;
            line-height: 100px;
            margin-left: 65px;
            margin-top: 31px;
            border-radius: 20px;
        }

        .totalTitle {
            float: left;
            width: 47%;
            height: 100%;
            line-height: 100px;
            text-align: center;
            margin-left: -16px;
            border-radius: 12px;
        }

        .totalValue {
            float: right;
            width: 50%;
            height: 100%;
            line-height: 100px;
            text-align: center;
        }

        .val {
            font-size: 30px;
            font-weight: 700;
        }

        h1 {
            color: #FFF;
            margin-top: 30px;
        }

        .totalTitle h1 {
            margin: 1.2em;
        }

    </style>
    <%--	clock  --%>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.1.0/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/FitText.js/1.1/jquery.fittext.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
    <link rel="stylesheet" href="./clock/dist/style.css">

</head>
<body style="text-align: center;">
<%--	<div class="alert alert-block alert-success">--%>
<%--		<button class="close" data-dismiss="alert" type="button" style="text-align: center;">--%>
<%--			<img src="${path }/images/admin/x.png" />--%>
<%--		</button>--%>
<%--		<i class="icon-ok green"></i>--%>
<%--		欢迎使用--%>
<%--		<strong>--%>
<%--			后台管理系统--%>
<%--			<small>(v1.0)</small>--%>
<%--		</strong>--%>
<%--		, 努力做教育服务--%>
<%--	</div>--%>
<div id="clock" class="clock">loading ...</div>
<%-- 个人信息页 --%>
<div>
    <%--    <div id="loginTeacher">--%>
    <%--        <c:if test="${sessionScope.loginTeacher != null }">--%>
    <%--            <a target="right" class="btn btn-default" href="../teachers" style="margin: 33px 1px 1px 1px;">--%>
    <%--                    ${sessionScope.loginTeacher.teacherName }--%>
    <%--            </a>--%>
    <%--        </c:if>--%>
    <%--        <c:if test="${sessionScope.loginTeacher == null }">--%>
    <%--            <a target="right" class="btn btn-default" href="#" style="margin-left: 9px;">--%>
    <%--                未登录--%>
    <%--            </a>--%>
    <%--        </c:if>--%>
    <%--    </div>--%>
    <div id="loginTeacher" style="
    border: 1px solid #ccc;
    padding: 10px;
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    box-shadow: 0 0 2px #222;
    text-shadow: 0 0 1px #fff;
    width: 89.2%;
    margin: 10px 1px 0px 60.9px;
    border-radius: 12px;">

        <c:if test="${sessionScope.adminPower == 1}">
            <img src="../images/admin_images/1.png" alt="管理员头像"
                 style="width: 15%;object-fit: cover;height: 170px;margin: 0px 23px 0px 23px;">
        </c:if>
        <c:if test="${sessionScope.adminPower == 0}">
            <img src="../images/teacher_images/1.png" alt="教师头像"
                 style="width: 15%;object-fit: cover;height: 170px;margin: 0px 23px 0px 23px;">
        </c:if>
        <div style="display: flex;flex-direction: column;justify-content: flex-start;padding: 15px;margin-bottom: 7px;text-align: left;margin-left: 12px;padding: 0px;">
            <c:if test="${sessionScope.loginTeacher != null }">
                <h3 style="margin: 0 0 25px;">当前用户</h3>
                <p style="margin: 0 0 14px;"><strong>用户名:</strong> <span
                        id="TeacherAccount">${sessionScope.loginTeacher.teacherAccount }</span>
                </p>
                <p style="margin: 0 0 14px;"><strong>姓名:</strong> <span
                        id="TeacherName">${sessionScope.loginTeacher.teacherName }</span>
                </p>
                <p style="margin: 0 0 14px;"><strong>角色:</strong> <span id="TeacherRole"><c:if
                        test="${sessionScope.adminPower == 1}">管理员</c:if><c:if
                        test="${sessionScope.adminPower == 0}">教师</c:if></span></p>
                <p style="margin: 0 0 0px;"><strong>负责班级：</strong><span id="TeacherClass">
                    <c:if test="${sessionScope.adminPower == 1}">
                        无
                    </c:if>
                    <c:if test="${sessionScope.className != null and sessionScope.adminPower == 0}">
                        ${sessionScope.className}
                    </c:if>
                </span></p>
            </c:if>
        </div>
    </div>
    <div class="state-overview clearfix">
        <div class="col-lg-3 col-sm-5 totalDiv">
            <div class="totalTitle" id="examPaperTotal">
                <h1></h1>
            </div>
            <div class="totalValue">
                <span class="val"></span>
            </div>
        </div>
        <div class="col-lg-3 col-sm-5 totalDiv">
            <div class="totalTitle" id="subjectTotal">
                <h1></h1>
            </div>
            <div class="totalValue">
                <span class="val"></span>
            </div>
        </div>
        <div class="col-lg-3 col-sm-5 totalDiv">
            <div class="totalTitle" id="teacherTotal">
                <h1></h1>
            </div>
            <div class="totalValue">
                <span class="val"></span>
            </div>
        </div>
        <div class="col-lg-3 col-sm-5 totalDiv">
            <div class="totalTitle" id="studentTotal">
                <h1></h1>
            </div>
            <div class="totalValue">
                <span class="val"></span>
            </div>
        </div>
    </div>

    <!-- js引入 -->
    <script src="${path }/js/jquery.js"></script>
    <script src="${path }/js/bootstrap/bootstrap.min.js"></script>
    <script src="./clock/dist/script.js"></script>
    <script type="text/javascript">
        /**
         * 页面加载完成后，通过getJSON方法获取后台提供的数据，
         * 并根据不同的数据键值更新页面相应的统计信息展示。
         */
        $(function () {
            // 从服务器获取整体统计信息
            $.getJSON("../homeInfo", function (data) {
                // 遍历获取的数据，并根据键值更新页面元素
                $.each(data, function (key, item) {
                    // 根据不同的键值，更新对应的统计信息展示
                    if ("examPaperTotal" == key.trim()) {
                        $("#examPaperTotal").css("background-color", "#6CCAC9");
                        $("#examPaperTotal").children("h1").text("试卷数量");
                        $("#examPaperTotal").siblings(".totalValue").children("span").text(item + "套");
                    } else if ("subjectTotal" == key.trim()) {
                        $("#subjectTotal").css("background-color", "#FF6C60");
                        $("#subjectTotal").children("h1").text("题目数量");
                        $("#subjectTotal").siblings(".totalValue").children("span").text(item + "道");
                    } else if ("teacherTotal" == key.trim()) {
                        $("#teacherTotal").css("background-color", "#F8D347");
                        $("#teacherTotal").children("h1").text("教师人数");
                        $("#teacherTotal").siblings(".totalValue").children("span").text(item + "人");
                    } else if ("studentTotal" == key.trim()) {
                        $("#studentTotal").css("background-color", "#57C8F2");
                        $("#studentTotal").children("h1").text("学生人数");
                        $("#studentTotal").siblings(".totalValue").children("span").text(item + "人");
                    }
                });
            });
        });
    </script>
</body>
</html>