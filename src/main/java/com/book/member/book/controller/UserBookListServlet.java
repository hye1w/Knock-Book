package com.book.member.book.controller;

import com.book.member.book.dao.UserBookDao;
import com.book.admin.book.vo.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//사용자들이 보는 도서 전체 목록
@WebServlet("/user/bookList")
public class UserBookListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserBookListServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String content = request.getParameter("bk_content");

        if (content == null) {
            content = ""; // null 대신 빈 문자열로 설정
        }

        // Book객체를 생성하고 현재 페이지를 설정합니다.
        Book b = new Book();
        String nowPage = request.getParameter("nowPage");
        if (nowPage != null) {
            b.setNowPage(Integer.parseInt(nowPage));
        }

        // 전체 목록 개수를 구하고 페이징바를 구성합니다.
        b.setTotalData(new UserBookDao().selectBoardCount(b, content));

        // 검색어와 추천도를 이용해 책 목록을 조회합니다.
        List<Map<String, String>> list = new UserBookDao().selectBookAll(b, content);

        // 요청 객체에 결과를 설정합니다.
        request.setAttribute("paging", b);
        request.setAttribute("resultList", list);
        request.setAttribute("searchContent", content); // 검색어를 다시 JSP로 전달

        RequestDispatcher rd = request.getRequestDispatcher("/views/member/book/userBookList.jsp");
        rd.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}