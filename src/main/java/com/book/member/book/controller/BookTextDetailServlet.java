package com.book.member.book.controller;

import com.book.member.book.dao.BookReplyDao;
import com.book.member.book.dao.BookTextDao;
import com.book.member.book.dao.LikeDao;
import com.book.member.user.vo.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//작성된 독후감 세부 사항
@WebServlet("/book/detail")
public class BookTextDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookTextDetailServlet() {
        super();

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bt_no = Integer.parseInt(request.getParameter("bt_no"));
        List<Map<String, String>> list = new BookTextDao().detailBookText(bt_no);

        List<Map<String, String>> bkReplyList = new BookReplyDao().bkReplySelectList(bt_no);
        int btReplyCnt = new BookReplyDao().btCount(bt_no);

        // 좋아요 수
        int lkCnt = 0;
        // 해당 유저가 좋아요를 눌렀는지
        int likeChecked = 0;
        // 하트 색
        String color = null;
        HttpSession session = request.getSession(false);
        // 사용자가 로그인을 했다면
        if (session != null && session.getAttribute("user") != null) {
            User user_like = (User) session.getAttribute("user");
            // 좋아요를 눌렀는지 확인하는 메소드
            likeChecked = new LikeDao().likeChecked(user_like.getUser_no(), bt_no);

            // 좋아요를 눌렀다면
            if(likeChecked == 1) {
                color = "red";
                // 안 눌렀다면
            } else {
                color = "gray";
            }
            // 사용자가 로그인을 안 했다면 하트를 누를 수 없으므로
        } else {
            color = "gray";
            likeChecked = 0;
        }
        // 좋아요 수 메소드
        lkCnt = new LikeDao().countLike(bt_no);
        // 가져온 값 보내기
        request.setAttribute("lkCnt", lkCnt);
        request.setAttribute("likeChecked", likeChecked);
        request.setAttribute("color", color);
        request.setAttribute("btReplyCnt", btReplyCnt);
        request.setAttribute("bkReplyList", bkReplyList);
        request.setAttribute("resultList", list);

        RequestDispatcher rd = request.getRequestDispatcher("/views/member/book/booktextDetail.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}