package com.book.member.user.controller;

import java.io.IOException;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.book.member.user.dao.UserDao;
import com.book.member.user.vo.User;

@WebServlet("/user/check_table")
public class UserCheckTableServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   
    public UserCheckTableServlet() {
        super();
    }

   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       User u = new User();
       String nowPage = request.getParameter("nowPage");
       String order = request.getParameter("order");
       String name = request.getParameter("user_name");
       u.setUser_name(name);
       
       if(nowPage != null) {
          u.setNowPage(Integer.parseInt(nowPage));
       }
       //전체 목록 개수 조회 -> 페이징바 구성 
       u.setTotalData(new UserDao().selectBoardCount(u));
 
       List<User> list = new UserDao().selectBoardList(u,order);
       
       request.setAttribute("paging", u);
       request.setAttribute("resultList", list);

       RequestDispatcher view = request.getRequestDispatcher("/views/member/user/check_table.jsp");
      view.forward(request, response);
      
   }

   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
      doGet(request, response);
   }

} 