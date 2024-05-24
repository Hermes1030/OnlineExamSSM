<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台登录</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
	<c:set var="path" value="<%=basePath%>"></c:set>
	<link href='${path }/images/admin/admin_index.png' rel='shortcut icon' type='image/x-icon'>
	<link href="${path}/css/bootstrap/bootstrap.min.css" rel="stylesheet" />

	<style type="text/css">
		html {
			height: 100%;
		}
		body {
			margin:0;
			padding:0;
			font-family: sans-serif;
			background: linear-gradient(#141e30, #243b55);
		}
		.container {
			position: absolute;
			top: 50%;
			left: 50%;
			width: 400px;
			padding: 40px;
			transform: translate(-50%, -50%);
			background: rgba(0,0,0,.5);
			box-sizing: border-box;
			box-shadow: 0 15px 25px rgba(0,0,0,.6);
			border-radius: 10px;
		}
		.login-box {
			position: absolute;
			top: 50%;
			left: 50%;
			width: 400px;
			padding: 40px;
			transform: translate(-50%, -50%);
			background: rgba(0,0,0,.5);
			box-sizing: border-box;
			box-shadow: 0 15px 25px rgba(0,0,0,.6);
			border-radius: 10px;
		}

		.login-box h2 {
			margin: 0 0 30px;
			padding: 0;
			color: #fff;
			text-align: center;
		}

		.login-box .user-box {
			position: relative;
		}

		.login-box .user-box input {
			width: 100%;
			padding: 10px 0;
			font-size: 16px;
			color: #fff;
			margin-bottom: 30px;
			border: none;
			border-bottom: 1px solid #fff;
			outline: none;
			background: transparent;
		}
		.login-box .user-box label {
			position: absolute;
			top:0;
			left: 0;
			padding: 10px 0;
			font-size: 16px;
			color: #fff;
			pointer-events: none;
			transition: .5s;
		}

		.login-box .user-box input:focus ~ label,
		.login-box .user-box input:valid ~ label {
			top: -20px;
			left: 0;
			color: #03e9f4;
			font-size: 12px;
		}

		.login-box form a {
			position: relative;
			display: inline-block;
			padding: 10px 65px;
			color: #03e9f4;
			font-size: 16px;
			text-decoration: none;
			text-transform: uppercase;
			overflow: hidden;
			transition: 0.5s;
			margin-top: 20px;
			margin-left: 70px;
			letter-spacing: 5px;
		}

		.login-box a:hover {
			background: #03e9f4;
			color: #fff;
			border-radius: 5px;
			box-shadow: 0 0 5px #03e9f4,
			0 0 25px #03e9f4,
			0 0 50px #03e9f4,
			0 0 100px #03e9f4;
		}

		.login-box a span {
			position: absolute;
			display: block;
		}

		.login-box a span:nth-child(1) {
			top: 0;
			left: -100%;
			width: 100%;
			height: 2px;
			background: linear-gradient(90deg, transparent, #03e9f4);
			animation: btn-anim1 1s linear infinite;
		}

		@keyframes btn-anim1 {
			0% {
				left: -100%;
			}
			50%,100% {
				left: 100%;
			}
		}

		.login-box a span:nth-child(2) {
			top: -100%;
			right: 0;
			width: 2px;
			height: 100%;
			background: linear-gradient(180deg, transparent, #03e9f4);
			animation: btn-anim2 1s linear infinite;
			animation-delay: .25s
		}

		@keyframes btn-anim2 {
			0% {
				top: -100%;
			}
			50%,100% {
				top: 100%;
			}
		}

		.login-box a span:nth-child(3) {
			bottom: 0;
			right: -100%;
			width: 100%;
			height: 2px;
			background: linear-gradient(270deg, transparent, #03e9f4);
			animation: btn-anim3 1s linear infinite;
			animation-delay: .5s
		}

		@keyframes btn-anim3 {
			0% {
				right: -100%;
			}
			50%,100% {
				right: 100%;
			}
		}

		.login-box a span:nth-child(4) {
			bottom: -100%;
			left: 0;
			width: 2px;
			height: 100%;
			background: linear-gradient(360deg, transparent, #03e9f4);
			animation: btn-anim4 1s linear infinite;
			animation-delay: .75s
		}

		@keyframes btn-anim4 {
			0% {
				bottom: -100%;
			}
			50%,100% {
				bottom: 100%;
			}
		}
	</style>

</head>
<body>
	<div class="login-box">
      <form class="user-box" action="../teacherlogin" method="post" id="adminLogin">
        <h2 class="user-box-heading" style="color:white;" align="center">后台登录</h2>
       	<input style="border-radius:1px" type=text name="teacherAccount" class="form-control" placeholder="Userame" required autofocus />
        <br />
        <input style="border-radius:1px" type="password" name="teacherPwd" class="form-control" placeholder="Password" required />
        <br />
<%--        <button class="btn btn-lg btn-primary btn-block" type="submit" id="adminSign">登录</button>--%>
		<a>
		<span></span>
		<span></span>
		<span></span>
		<span></span>
		<button type="submit" id="adminSign" style="background-color:transparent;border-style:none;">登录</button>
		</a>
      </form>
    </div>

	<!-- js引入 -->
	<script src="${path}/js/jquery.js"></script>
	<script src="${path}/js/bootstrap/bootstrap.min.js"></script>
	<script src="${path}/js/login.js"></script>
</body>
</html>