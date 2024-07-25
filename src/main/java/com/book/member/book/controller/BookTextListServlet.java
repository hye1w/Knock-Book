package com.book.member.book.controller;

import com.book.member.book.dao.BookTextDao;
import com.book.member.book.vo.BookText;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//사용자이 작성한 독후감 전체목록
@WebServlet("/book/textList")
public class BookTextListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookTextListServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 검색어를 받아옵니다.
        String content = request.getParameter("bw_content");

        // 추천도 값을 받아옵니다.
        String recommendation = request.getParameter("recommendation");

        if (content == null) {
            content = ""; // null 대신 빈 문자열로 설정
        }
        // 예외 처리: recommendation 값이 없을 경우 기본값 설정
        if (recommendation == null || recommendation.isEmpty()) {
            recommendation = "0"; // 기본값
        }

        // BookText 객체를 생성하고 현재 페이지를 설정합니다.
        BookText bt = new BookText();
        String nowPage = request.getParameter("nowPage");
        if (nowPage != null) {
            bt.setNowPage(Integer.parseInt(nowPage));
        }

        // 전체 목록 개수를 구하고 페이징바를 구성합니다.
        bt.setTotalData(new BookTextDao().selectBoardCount(bt, content, recommendation));

        // 검색어와 추천도를 이용해 책 목록을 조회합니다.
        List<Map<String, String>> list = new BookTextDao().selectBooktext(bt, content, recommendation);

        // 요청 객체에 결과를 설정합니다.
        request.setAttribute("paging", bt);
        request.setAttribute("resultList", list);
        request.setAttribute("searchContent", content); // 검색어를 다시 JSP로 전달
        request.setAttribute("selectedRecommendation", recommendation); // 선택된 추천도를 다시 JSP로 전달

        // JSP로 포워딩합니다.
        RequestDispatcher rd = request.getRequestDispatcher("/views/member/book/booktextList.jsp");
        rd.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}