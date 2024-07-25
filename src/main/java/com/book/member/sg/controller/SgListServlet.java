package com.book.member.sg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.member.sg.dao.SgMemDao;
import com.book.member.sg.vo.Suggestion;
import com.book.member.user.vo.User;

@WebServlet("/member/sg/list")
public class SgListServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SgListServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
      // user session 
      HttpSession session = request.getSession();
      User u = (User) session.getAttribute("user");
      // form 에서 가져온 값들
      String title = request.getParameter("sg_title");
      String sgSort = request.getParameter("sgSort");
      Suggestion sgOp = new Suggestion();
      sgOp.setSg_title(title);
      sgOp.setUser_no(u.getUser_no());
      // 페이징
      String nowPage = request.getParameter("nowPage");
      if (nowPage != null) {
         sgOp.setNowPage(Integer.parseInt(nowPage));
      }
      // sgOp에 페이징 추가 
      sgOp.setTotalData(new SgMemDao().selectSgCount(sgOp));
      // sgOp 에 list 추가
      List<Suggestion> list = new SgMemDao().selectSgList(sgOp,sgSort);
      // setAttribute로 값 보내주기
      request.setAttribute("paging", sgOp);
      request.setAttribute("resultList", list);
      RequestDispatcher view = request.getRequestDispatcher("/views/member/user/mypageSgList.jsp");
      view.forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   }

}
