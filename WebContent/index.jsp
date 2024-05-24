<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>在线考试系统</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<c:set var="path" value="<%=basePath%>"></c:set>
<link href='${path }/images/reception/index2.png' rel='shortcut icon' type='image/x-icon'>
<link href="${path }/css/bootstrap/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${path }/js/zeroModal/zeroModal.css" />

<%-- 增加首页按钮效果 --%>
<link rel="stylesheet" href="reception/button/style.css">

</head>
<body style="background-color: #EEEEEE;">
	<div style="width: 100%; height: 100%;">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
						<div class="navbar-header">
							 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
							 <span class="sr-only">Toggle navigation</span>
							 <span class="icon-bar"></span>
							 <span class="icon-bar"></span>
							 <span class="icon-bar"></span>
							 </button>
<%--							 <a class="navbar-brand" href="index.jsp">猿来入此</a>--%>
						</div>
						
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<img src="images/reception/logo_index.png" alt="logo" style="width: 255px;height:54px;margin: 2px 1px 1px 5px;"/>
<%--							<ul class="nav navbar-nav" style="padding-top: 5px;">--%>
<%--								<li>--%>
<%--									 <a href="index.jsp">首页</a>--%>
<%--								</li>--%>
<%--								<li>--%>
<%--									 <a id="examCenter-link" target="home" style="cursor: pointer;" href="willexams?classId=${sessionScope.loginStudent.classInfo.classId }&gradeId=${sessionScope.loginStudent.grade.gradeId }&studentId=${sessionScope.loginStudent.studentId }" studentId="${sessionScope.loginStudent.studentId }">我的试卷</a>--%>
<%--								</li>--%>
<%--								<li>--%>
<%--									 <a id="mineCenter-link" target="home" style="cursor: pointer;" href="history/${sessionScope.loginStudent.studentId }" studentId="${sessionScope.loginStudent.studentId }">历史试卷</a>--%>
<%--								</li>--%>
<%--							</ul>--%>
							<ul class="nav navbar-nav navbar-right" style="margin-right: 10px;">
								<li class="dropdown">
									 <c:if test="${sessionScope.loginStudent != null }">
									 	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
										 	<img class="img-circle" src="images/reception/photo.png" alt="Photo" style="width: 30px; height: 30px;" />
										 	<strong class="caret"></strong>
										 </a>
										<ul class="dropdown-menu">
											<li>
												<a href="self/${sessionScope.loginStudent.studentId }" id="self">${sessionScope.loginStudent.studentName }</a>
											</li>
											<li>
												<a href="index.jsp">系统首页</a>
											</li>
											<li>
												<a id="examCenter-link" target="home" style="cursor: pointer;" href="willexams?classId=${sessionScope.loginStudent.classInfo.classId }&gradeId=${sessionScope.loginStudent.grade.gradeId }&studentId=${sessionScope.loginStudent.studentId }" studentId="${sessionScope.loginStudent.studentId }">我的试卷</a>
											</li>
											<li>
												<a id="mineCenter-link" target="home" style="cursor: pointer;" href="history/${sessionScope.loginStudent.studentId }" studentId="${sessionScope.loginStudent.studentId }">历史试卷</a>
											</li>
											<li class="divider"></li>
											<li>
												<a href="exit">Log out</a>
											</li>
										</ul>
									 </c:if>
									 <c:if test="${sessionScope.loginStudent == null }">
										<div class="btn-group" style="margin-top: 3px;">
											<div class="btn-holder">
												<button type="button" class="btn btn-1 hover-filled-slide-right" id="studentLogin">
													<span>登录</span>
												</button>
												<button type="button" class="btn btn-2 hover-slide-right" id="studentRegister">
													<span>注册</span>
												</button>
												<button class="btn btn-3 hover-slide-right">
													<span><a href="admin/login.jsp" target="_blank" id="studentLogin">后台管理</a></span>
												</button>
											</div>
											<%--<button type="button" class="btn btn-default btn-sm" id="studentLogin">登录</button>
											<button type="button" class="btn btn-default btn-sm" id="studentRegister">注册</button>
											<a href="admin/login.jsp" target="_blank" class="btn btn-default btn-sm" id="studentLogin">后台管理</a>--%>
										</div>
									 </c:if>
								</li>
							</ul>
						</div>
					</nav>
				</div>
			</div>
		</div>
		<div style="margin-top: 0px;width: 100%;height: 100vh;">
			<iframe src="reception/home.jsp" width="100%" height="100%" name="home"></iframe>

		</div>
	</div>


	<!-- js引入 -->
	<script src="${path }/js/jquery.js"></script>
	<script src="${path }/js/bootstrap/bootstrap.min.js"></script>
	<script src="${path }/js/zeroModal/zeroModal.min.js"></script>
	<script src="${path }/js/login.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#examCenter-link, #mineCenter-link").click(function() {
				//判断是否登录
				var studetnId = $(this).attr("studentId");
				if(studetnId.trim() == "" || studetnId == null) {
					zeroModal.show({
						title: "Error",
						content: "你还没有登陆！",
						width : '200px',
						height : '70px',
						overlay : false,
						ok : false,
						onClosed : function() {
							location.reload();
						}
					});
					return false;
				}
			});
			
			$("#self").click(function() {
				var href = $(this).attr("href");
				zeroModal.show({
					title: "Self",
					content: "个人信息",
					width : '400px',
					height : '200px',
					top : '100px',
					left : '430px',
					url: href,
					overlay : false,
					ok : true,
					onClosed : function() {
						location.reload();
					}
				});
				return false;
			});
		});
	</script>
</body>
</html>