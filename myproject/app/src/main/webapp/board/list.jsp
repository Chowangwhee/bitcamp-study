<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 목록</title>
    <style>
        body {
            font-family: sans-serif;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f0f0f0;
        }
        .button-container {
            text-align: right;
            width: 80%;
            margin: 0 auto 10px;
        }
        .button-container button {
            background-color: #4CAF50;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>게시글 목록</h1>
    <div class="button-container">
        <a href="/board/add"><button>글 쓰기</button></a>
    </div>
    <table>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>생성일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${list}">
                <tr>
                    <td><a href="/board/view?no=${board.no}">${board.no}</td>
                    <td><a href="/board/view?no=${board.no}">${board.title}</td>
                    <td><a href="/board/view?no=${board.no}">${board.writer.name}</td>
                    <td><a href="/board/view?no=${board.no}"><fmt:formatDate value="${board.createDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${board.viewCount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>