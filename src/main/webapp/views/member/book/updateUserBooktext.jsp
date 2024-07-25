<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Knock Book</title>
        <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous"
        />
        <style>
           * {
                     font-family: 'Ownglyph_meetme-Rg';
                 }
                 .write_container {
                     display: flex;
                     justify-content: center;
                     align-items: flex-start;
                     padding: 2vw;
                 }
                 .form_write {
                     display: flex;
                     flex-direction: row;
                     gap: 2vw;
                     width: 60vw;
                     border: 1px solid black;
                     border-radius: 10px;
                     padding: 2vw;
                 }
                 .editLeft {
                   display: flex;
                    flex-direction: row;
                    gap:2vw;

                 }
                 .editRight {
                    display: flex;
                    flex-direction: row;
                    gap:2vw;
                 }
                 .form_left {
                     flex: 2;
                     display: flex;
                     flex-direction: column;
                     gap: 1.5vw;
                     width:6vw;
                 }
                 .form_right {
                    width:3vw;
                     flex: 1;
                     display: flex;
                     flex-direction: column;
                     align-items: center;
                 }
                 .form_right img {
                     max-width: 100%;
                     border-radius: 10px;
                 }
                 .input-field {
                     height: 50px;
                     border-radius: 10px;
                     border: 2px solid #3232321f;
                     padding: 0 10px;
                     width: 100%;
                 }
                 textarea {
                     width: 100%;
                     height: 20rem;
                     border-radius: 10px;
                     border: 2px solid #3232321f;
                     resize: none;
                     padding: 10px;
                 }
                 .bw_btn {
                     display: flex;
                     flex-direction: column;
                     gap: 10px;
                     margin-top: 20px;
                     width: 100%;
                 }
                 .btn {
                     padding: 10px 20px;
                     font-size: 16px;
                     width: 100%;
                 }
                  .form_right img {
                     max-width: 100%;
                     border-radius: 10px;
                  }
        </style>
</head>
<body>

   <%@ include file="../../include/header.jsp" %>
<h1 style="text-align:center">책 정보 수정</h1>
    <div class="write_container">

        <form action="/user/booktextEditEnd" method="post" class="form_write">
                <div class="form_left">
                    <div class="editLeft">
                        <input type="hidden" name="bt_no" value="<%= request.getAttribute("bt_no") %>">
                        <div>
                            <label for="bk_title">도서이름
                            <input type="text" style="width: 19vw" class="form-control" id="bk_title" name="bk_title" value="<%= request.getAttribute("bk_title") %>" readonly>
                        </div>
                        <div>
                            <label for="bt_writer">작성자
                            <input type="text" style="width: 6vw"  class="form-control" id="bt_writer" name="bt_writer" value="<%= request.getAttribute("bt_writer") %>" readonly>
                        </div>
                        <div>
                            <label for="bk_publisher">출판사
                            <input type="text" style="width: 7vw"  class="form-control" id="bk_publisher" name="bk_publisher" value="<%= request.getAttribute("bk_publisher") %>" readonly>
                        </div>
                    </div>
                    <div class="editRight">
                        <div>
                            <label for="bk_cate">추천도
                            <select class="form-control" id="bk_cate" name="bk_cate" value="<%= request.getAttribute("bk_cate") %>" style="width: 8vw">
                               <option value="">추천도</option>
                               <option value="1">매우좋음</option>
                               <option value="2">좋음</option>
                               <option value="3">보통</option>
                               <option value="4">나쁘지 않음</option>
                               <option value="5">별로</option>
                            </select>

                        </div>
                        <div>
                            <label for="bt_start">읽기 시작한 날짜
                            <input type="date" class="form-control" id="bt_start" name="bt_start" value="<%= request.getAttribute("bt_start") %>" style="width: 12vw">
                        </div>
                        <div>
                            <label for="bt_end">읽기 종료한 날짜
                            <input type="date" class="form-control" id="bt_end" name="bt_end" value="<%= request.getAttribute("bt_end") %>" style="width: 12vw">
                        </div>
                    </div>

                    <textarea class="form-control" id="bt_content" name="bt_content" rows="5"><%= request.getAttribute("bt_content") %></textarea>
                </div>
                <div class="form_right">
                    <img class="form-control" id="bk_img" name="bk_img" src="<%= request.getAttribute("bk_img") %>" alt="Book Image" width="0.5vw">
                     <button class="btn btn-primary">저장</button>
                </div>
            </form>


    </div>
</body>
</html>
