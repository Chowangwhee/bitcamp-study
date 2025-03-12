<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bitcamp.myapp.vo.Member" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>홈</title>
    <style>
        body {
            font-family: sans-serif;
        }
        .login-container {
            width: 300px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }
        button[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div>
<div>
<%
Member member = (Member) request.getAttribute("loginUser");
if (member != null) {
    out.println("<p>" + member.getName() + "</p>");
    out.println("<a href='/auth/logout'>로그아웃</a>");
} else {
    out.println("<a href='/auth/login-form'>로그인</a>");
}
%>
</div>
    <div class="login-container">
        <h1>강의 관리 시스템</h1>
        <p>환영합니다!</p>
    </div>
</div>
</body>
</html>