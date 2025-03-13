<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
    /* Navigation Bar */
    .navbar {
        background-color: #4CAF50; /* Green */
        color: white;
        padding: 10px 20px;
        display: flex;
        justify-content: flex-end; /* Align items to the right */
        align-items: center;
        position: sticky;
        top: 0;
        z-index: 1; /* Ensure navbar is above other content */
    }

    .navbar-links {
        list-style: none;
        margin: 0;
        padding: 0;
        display: flex;
        align-items: center;
    }

    .navbar-links li {
        margin-left: 10px;
    }

    .navbar-links li a {
        text-decoration: none;
        color: white;
        background-color: #4CAF50;
        padding: 8px 12px;
        border-radius: 5px;
        border: none;
    }
</style>
<nav class="navbar">
    <ul class="navbar-links">
        <c:if test="${not empty loginUser}">
            <li>${loginUser.name}</li>
            <li><a href='/auth/logout'>로그아웃</a></li>
        </c:if>
        <c:if test="${empty loginUser}">
            <li><a href='/auth/login-form'>로그인</a></li>
        </c:if>
    </ul>
</nav>