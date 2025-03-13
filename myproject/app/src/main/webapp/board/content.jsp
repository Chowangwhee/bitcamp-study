<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 상세 보기</title>
    <style>
        body {
            font-family: sans-serif;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            border: 2px solid #777; /* Add dark gray border */
            border-radius: 10px;
        }

        .board-info {
            margin-bottom: 20px;
        }

        .board-info p {
            margin-bottom: 5px;
        }

        .board-content {
            border-top: 1px solid #ccc;
            padding-top: 20px;
        }

        .button-container {
            margin-top: 20px;
            text-align: center;
        }

        .button-container button {
            padding: 10px 15px;
            margin: 0 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .button-container .list-button {
            background-color: #4CAF50; /* Green */
            color: white;
        }

        .button-container .modify-button {
            background-color: #008CBA; /* Blue */
            color: white;
        }

        .button-container .delete-button {
            background-color: #f44336; /* Red */
            color: white;
        }

        .button-container .confirm-button {
            background-color: #4CAF50; /* Green */
            color: white;
        }

        .button-container .cancel-button {
            background-color: #6c757d; /* Grey */
            color: white;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 5px;
            margin-bottom: 5px;
            border: none; /* Remove border */
            outline: none; /* Remove outline on focus */
            box-sizing: border-box;
            background-color: transparent; /* Set background to transparent */
            font-size: inherit; /* Inherit font size */
            font-family: inherit; /* Inherit font family */
        }

        textarea {
            resize: none;
        }

        #confirmCancelButtons {
            display: none; /* Initially hide confirm/cancel buttons */
        }

        .board-info.readonly-mode input[readonly] {
            background-color: transparent; /* Keep transparent */
            cursor: default;
        }

        .board-info.edit-mode input[readonly] {
            background-color: transparent; /* Keep transparent */
        }

        .board-info div {
            display: flex;
            align-items: center;
            cursor: pointer;
            border-bottom: 1px solid #ccc; /* Add bottom border */
            padding: 5px 0; /* Add padding to top and bottom */
        }
         .board-content {
            border-top: 1px solid #ccc;
            padding-top: 20px;
            border-bottom: 1px solid #ccc;
            padding-bottom: 20px;
        }

        .board-info strong {
            width: 100px;
        }
    </style>
</head>
<body>
<jsp:include page="/common/navbar.jsp"/>
<div class="container">
    <div class="button-container">
        <a href="/home"><button class="list-button">게시판 목록</button></a>
    </div>
    <h1>게시글 상세 보기</h1>
    <form id="modifyForm" action="/board/modify" method="post">
        <div class="board-info readonly-mode">
            <div onclick="focusInput(this)">
                <strong>번호:</strong>
                <input type="text" value="${board.no}" readonly>
                <input type="hidden" name="no" value="${board.no}">
            </div>
            <div onclick="focusInput(this)">
                <strong>제목:</strong>
                <input type="text" id="title" name="title" value="${board.title}" readonly>
            </div>
            <div onclick="focusInput(this)">
                <strong>작성자:</strong>
                <input type="text" value="${board.writer.name}" readonly>
            </div>
            <div onclick="focusInput(this)">
                <strong>생성일:</strong>
                <input type="text" value="<fmt:formatDate value="${board.createDate}" pattern="yyyy-MM-dd"/>" readonly>
            </div>
            <div onclick="focusInput(this)">
                <strong>조회수:</strong>
                <input type="text" value="${board.viewCount}" readonly>
            </div>
        </div>
        <div class="board-content">
            <textarea id="content" name="content" style="width: 100%; height: 300px;"
                      readonly>${board.content}</textarea>
        </div>
        <div class="button-container">
            <c:if test="${board.writer.no == loginUser.no}">
                <button type="button" class="modify-button" id="modify">게시글 수정</button>
                <div id="confirmCancelButtons">
                    <button type="button" class="confirm-button" id="confirm">확인</button>
                    <button type="button" class="cancel-button" id="cancel">취소</button>
                </div>
                <button type="button" class="delete-button" id="delete">게시글 삭제</button>
            </c:if>
        </div>
    </form>
</div>
</body>
<script>
    function focusInput(div) {
        const input = div.querySelector('input');
        if (input) {
            input.focus();
        }
    }

    let modify = document.getElementById('modify');
    let deleteBtn = document.getElementById('delete');
    let confirm = document.getElementById('confirm');
    let cancel = document.getElementById('cancel');
    let confirmCancelButtons = document.getElementById('confirmCancelButtons');
    let modifyForm = document.getElementById("modifyForm");

    let titleInput = document.getElementById("title");
    let contentTextarea = document.getElementById("content");

    const boardInfo = document.querySelector('.board-info');

    modify.addEventListener('click', function () {
        modify.style.display = 'none';
        deleteBtn.style.display = 'none';

        confirmCancelButtons.style.display = 'inline-block';

        titleInput.readOnly = false;
        contentTextarea.readOnly = false;
        titleInput.focus();

        boardInfo.classList.remove('readonly-mode');
        boardInfo.classList.add('edit-mode');
    });

    confirm.addEventListener('click', function () {
        modifyForm.submit();
    });

    cancel.addEventListener('click', function () {
        modify.style.display = 'inline-block';
        deleteBtn.style.display = 'inline-block';
        confirmCancelButtons.style.display = 'none';
        titleInput.value = "${board.title}";
        contentTextarea.value = "${board.content}";
        titleInput.readOnly = true;
        contentTextarea.readOnly = true;
        boardInfo.classList.add('readonly-mode');
        boardInfo.classList.remove('edit-mode');
    });

    deleteBtn.addEventListener('click', function () {
        location.href = `/board/delete?no=${board.no}`;
    });
</script>
</html>