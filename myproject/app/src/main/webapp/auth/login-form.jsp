<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 화면</title>
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
    <div class="login-container">
        <h2>로그인</h2>
        <form action="/auth/login" method="post">
            <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" id="email" name="email" value="${cookie.email.value}" required>
            </div>
            <div class="form-group">
                <label for="password">암호:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <input type="checkbox" id="saveEmail" name="saveEmail">
                <label for="saveEmail">이메일 저장</label>
            </div>
            <button type="submit">로그인</button>
        </form>
    </div>
</body>
</html>