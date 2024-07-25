<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Knock Book</title>
</head>
<body>
    <style> 
        .container {
	        display: grid;
		    grid-template-columns: repeat(2, 1fr);
		    grid-gap: 20px;
		    max-width: 1000px;
		    margin: 150px auto 0;
		    padding: 20px;
		    background: white;
		    border-radius: 20px;
        }

        .box {
            background-color: #f9f6eb;
            padding: 20px;
            border: 1px solid #ffffff;
            border-radius: 10px;
            text-align: center;
        }

        .box h2 {
            font-size: 30px;
            margin-bottom: 30px;
            color: #333333;
        }

        .list {
            list-style: none;
            padding: 0;
        }

        .list-item {
            margin-bottom: 10px;
        }

        .list-item a {
            text-decoration: none;
            color: #666666;
            font-size: 16px;
            font-family: 'BMHANNAPro';
        }

        .list-item a:hover {
            color: #000000;
            font-weight: bold;
        }
    </style>
      <%@ include file="../include/header.jsp" %>
<div class="container">
    <div class="box">
        <h2>도서</h2>
        <ul class="list">
            <li class="list-item"><a href="/book/list">도서 목록</a></li>
            <li class="list-item"><a href="/book/create">도서 추가</a></li>
            <li class="list-item"><a href="/book/applyStatusList">도서 신청 목록</a></li>
        </ul>
    </div>
    <div class="box">
        <h2>이벤트</h2>
        <ul class="list">
            <li class="list-item"><a href="/event/list">이벤트 목록</a></li>
            <li class="list-item"><a href="/event/create">이벤트 추가</a></li>
            <li class="list-item"><a href="/event/parList">참여자 목록</a></li>
        </ul>
    </div>
    <div class="box">
        <h2>문의사항</h2>
        <ul class="list">
            <li class="list-item"><a href="/admin/sg/list">문의사항 목록 조회</a></li>
            <li class="list-item"><a href="/admin/sg/basic">문의사항 기본 답변 추가</a></li>
        </ul>
    </div>
    <div class="box">
        <h2>회원</h2>
        <ul class="list">
            <li class="list-item"><a href="/user/check_table">회원 목록</a></li> 
        </ul>
    </div>
</div>
</body>
</html>