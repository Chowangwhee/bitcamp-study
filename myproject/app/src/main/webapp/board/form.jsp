<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <style>
        body {
            font-family: sans-serif;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            border: 2px solid #777;
            border-radius: 10px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        textarea {
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
        .button-container {
            margin-top: 20px;
            text-align: center;
        }
        textarea {
            height: 300px;
            resize: none;
        }
    </style>
</head>
<body>
<jsp:include page="/common/navbar.jsp"/>
<div class="container">
    <h1>게시글 작성</h1>
    <form action="/board/add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="content">내용:</label>
            <textarea id="content" name="content" required></textarea>
        </div>
        <div class="form-group">
            <label for="files">사진:</label>
            <input type="file" id="files" name="files" multiple>
        </div>
        <div class="button-container">
            <button type="submit">저장</button>
            <a href="/home"><button type="button">취소</button></a>
        </div>
    </form>
</div>
</body>
</html>