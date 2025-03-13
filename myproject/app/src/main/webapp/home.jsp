<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="bitcamp.myapp.vo.Member" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>홈</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 0; /* Remove default body margins */
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* Ensure full viewport height */
        }

        .login-container {
            width: 300px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            text-align: center;
        }

        /* Main Content Container */
        .main-content {
            flex-grow: 1; /* Allow content to take up remaining space */
            display: flex;
            justify-content: center; /* Center content horizontally */
            align-items: start; /* Align content to start vertically */
            width: 100%;
            padding-top: 20px; /* Add padding-top */
        }
        .board-container{
            width: 100%;
        }
         /* Other Styles (existing) */
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
    <!-- Navigation Bar -->
    <jsp:include page="/common/navbar.jsp" />

    <!-- Main Content -->
    <div class="main-content">
        <c:if test="${not empty loginUser}">
            <div class="board-container">
                <jsp:include page="/board/list" />
            </div>
        </c:if>
        <c:if test="${empty loginUser}">
             <div class="login-container">
                <h1>강의 관리 시스템</h1>
                <p>환영합니다!</p>
            </div>
        </c:if>
    </div>
</body>
</html>