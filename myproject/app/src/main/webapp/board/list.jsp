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
    </style>
</head>
<body>
    <h1>게시글 목록</h1>
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
                    <td>${board.no}</td>
                    <td>${board.title}</td>
                    <td>${board.writer.name}</td>
                    <td><fmt:formatDate value="${board.createDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${board.viewCount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>