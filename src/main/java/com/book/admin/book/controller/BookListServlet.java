package com.book.admin.book.controller;


import com.book.admin.book.dao.BookDao;
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

//관리자 도서 목록
@WebServlet("/book/list")
public class BookListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookListServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 검색어를 받아옵니다.
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
        b.setTotalData(new BookDao().selectBoardCount(b, content));

        // 검색어와 추천도를 이용해 책 목록을 조회합니다.
        List<Map<String, String>> list = new BookDao().selectBook(b, content);

        // 요청 객체에 결과를 설정합니다.
        request.setAttribute("paging", b);
        request.setAttribute("resultList", list);
        request.setAttribute("searchContent", content); // 검색어를 다시 JSP로 전달

        RequestDispatcher rd = request.getRequestDispatcher("/views/admin/book/book_list.jsp");
        rd.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}