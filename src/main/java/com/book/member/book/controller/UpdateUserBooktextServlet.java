package com.book.member.book.controller;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//회원별 본인 독후감 수정
@WebServlet("/user/booktextEdit")
public class UpdateUserBooktextServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateUserBooktextServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bt_no = request.getParameter("bt_no");
        String bk_title = request.getParameter("bk_title");
        String bk_cate = request.getParameter("bk_cate");
        String bt_writer = request.getParameter("bt_writer");
        String bk_publisher = request.getParameter("bk_publisher");
        String recommendation = request.getParameter("recommendation");
        String bt_start = request.getParameter("bt_start");
        String bt_end = request.getParameter("bt_end");
        String bt_content = request.getParameter("bt_content");
        String bk_img = request.getParameter("bk_img");

        // 각 파라미터를 리퀘스트 속성에 설정
        request.setAttribute("bt_no", bt_no);
        request.setAttribute("bk_title", bk_title);
        request.setAttribute("bk_cate", bk_cate);
        request.setAttribute("bt_writer", bt_writer);
        request.setAttribute("bk_publisher", bk_publisher);
        request.setAttribute("recommendation", recommendation);
        request.setAttribute("bt_start", bt_start);
        request.setAttribute("bt_end", bt_end);
        request.setAttribute("bt_content", bt_content);
        request.setAttribute("bk_img", bk_img);

        RequestDispatcher view = request.getRequestDispatcher("/views/member/book/updateUserBooktext.jsp");
        view.forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }
}



