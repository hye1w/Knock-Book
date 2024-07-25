package com.book.member.user.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.book.member.user.vo.Book;
import com.book.member.user.vo.Booktext;

import static com.book.common.sql.JDBCTemplate.*;

public class BookDao {

    public int createBook(Book bk) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            conn.setAutoCommit(false);
            String sql = "SELECT COUNT(*) FROM book WHERE books_title =? AND books_author =? AND books_publisher = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bk.getBooks_title());
            pstmt.setString(2, bk.getBooks_author());
            pstmt.setString(3, bk.getBooks_publisher_name());
            rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("책이 이미 존재합니다.");
                result = 0;
            } else {
                String sql2 = "INSERT INTO `book`(`books_title`, `books_author` , `books_category_no`, `books_publisher`, `books_img`) VALUES(?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, bk.getBooks_title());
                pstmt.setString(2, bk.getBooks_author());
                pstmt.setInt(3, bk.getBooks_category_no());
                pstmt.setString(4, bk.getBooks_publisher_name());
                pstmt.setString(5, bk.getBooks_image());
                result = pstmt.executeUpdate();
                System.out.println(result);
            }


            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                rollback(conn);
            }
            e.printStackTrace();
        } finally {
            try {
                close(rs);
               close(pstmt);
                if (conn != null) conn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
       return result;
    }

    public List<Map<String, String>> selectBook(Book b, String content) {
        List<Map<String, String>> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/book_reviews";
            String user = "chaedud02";
            String pwd ="0000";
            conn = DriverManager.getConnection(url,user,pwd);

            String sql = "SELECT a.books_title AS 도서이름, a.books_author AS 도서저자, a.books_publisher AS 출판사, a.books_img AS 도서이미지, b.book_category_name AS 카테고리 FROM book a \n" +
                    "JOIN book_category b ON a.books_category_no = b.book_category_no";

            if(content != null) {
                sql += " WHERE a.books_title LIKE CONCAT('%','"+content+"','%')";
            }

            sql += " LIMIT "+b.getLimitPageNo()+", "+b.getNumPerPage();

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Map<String, String> row = new HashMap<>();
                row.put("books_title", rs.getString("도서이름"));
                row.put("books_author", rs.getString("도서저자"));
                row.put("books_publisher", rs.getString("출판사"));
                row.put("books_img", rs.getString("도서이미지"));
                row.put("books_category", rs.getString("카테고리"));

                list.add(row);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }
    public int selectBoardCount(Book option, String content) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/book_reviews";
            String user = "chaedud02";
            String pwd ="0000";
            conn = DriverManager.getConnection(url,user,pwd);

            String sql = "SELECT COUNT(*) AS cnt FROM book";

            if(content != null) {
                sql += " WHERE books_title LIKE CONCAT('%','"+content+"','%')";
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                result = rs.getInt("cnt");
                System.out.println(result);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}