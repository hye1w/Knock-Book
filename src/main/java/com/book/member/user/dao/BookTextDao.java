package com.book.member.user.dao;

import com.book.member.user.vo.Booktext;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.book.common.sql.JDBCTemplate.getConnection;
import static com.book.common.sql.JDBCTemplate.close;

public class BookTextDao {

    public int inputBookText(Booktext bt){
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        try{
            String sql = "INSERT INTO `booktext` (book_first_read, book_end_read, book_content, recommendation_no, book_no, user_no) " +
                    "VALUES (?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(bt.getBook_first_read()));
            pstmt.setDate(2, Date.valueOf(bt.getBook_end_read()));
            pstmt.setString(3,bt.getBook_content());
            pstmt.setInt(4, bt.getRecommendation_no());
            pstmt.setInt(5, bt.getBook_no());
            pstmt.setInt(6, bt.getUser_no());

            result = pstmt.executeUpdate();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public  List<Map<String, String>> selectBooktext(Booktext bt, String content, String recommendation) {

        List<Map<String, String>> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try{
            String sql = "SELECT a.booktext_no AS 독후감번호, b.user_nickname AS 작성자 ,d.books_img AS 책이미지, d.books_title AS 책제목, c.description AS 추천도 ,a.upload_text AS 업로드 FROM `booktext` a \n" +
                    "JOIN users b ON a.user_no = b.user_no \n" +
                    "JOIN recommendation c ON a.recommendation_no = c.recommendation_no\n" +
                    "JOIN book d ON d.books_no = a.book_no";

            if(content != null) {
                sql += " WHERE d.books_title LIKE CONCAT('%','"+content+"','%')";
            }
            // 추천도가 선택된 경우 SQL에 조건을 추가합니다.
            System.out.println(recommendation);
            if (recommendation != null && !recommendation.isEmpty() && !"0".equals(recommendation)) {
                sql += " AND c.recommendation_no = '"+recommendation+"'";

            }
            sql += " LIMIT "+bt.getLimitPageNo()+", "+bt.getNumPerPage();


            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while(rs.next()){
                Timestamp upload = rs.getTimestamp("업로드");

                Map<String, String> row = new HashMap<>();
                row.put("bt_no", rs.getString("독후감번호"));
                row.put("bt_writer", rs.getString("작성자"));
                row.put("bk_title", rs.getString("책제목"));
                row.put("recommendation", rs.getString("추천도"));
                row.put("bk_img", rs.getString("책이미지"));
                row.put("upload", sdf.format(upload) );

                list.add(row);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    return list;
    }

    public  List<Map<String, String>> detailBookText(int bt_no) {

        List<Map<String, String>> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT a.booktext_no AS 독후감번호, a.book_first_read AS 읽기시작, a.book_end_read AS 읽기종료, a.book_content AS 독후감내용,  c.description AS 추천도, d.books_title AS 도서제목, b.user_name AS 작성자,e.book_category_name AS 카테고리명, e.book_category_no AS 카테고리번호, d.books_img AS 이미지, d.books_publisher AS 출판사, b.user_no AS 사용자번호 FROM booktext a\n" +
                    "JOIN users b ON a.user_no = b.user_no \n" +
                    "JOIN recommendation c ON a.recommendation_no = c.recommendation_no\n" +
                    "JOIN book d ON d.books_no = a.book_no " +
                    "JOIN book_category e ON e.book_category_no = d.books_category_no WHERE a.booktext_no = ? ;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,bt_no);
            rs = pstmt.executeQuery();

            while(rs.next()){

                Map<String, String> row = new HashMap<>();
                row.put("bt_start", rs.getString("읽기시작"));
                row.put("bt_end", rs.getString("읽기종료"));
                row.put("bt_content", rs.getString("독후감내용"));
                row.put("bk_title", rs.getString("도서제목"));
                row.put("recommendation", rs.getString("추천도"));
                row.put("bt_writer", rs.getString("작성자"));
                row.put("bk_img", rs.getString("이미지"));
                row.put("bk_cate", rs.getString("카테고리명"));
                row.put("bk_publisher", rs.getString("출판사"));
                row.put("user_no",rs.getString("사용자번호"));
                row.put("bt_no",rs.getString("독후감번호"));
                row.put("bk_cate_no",rs.getString("카테고리번호"));
                list.add(row);


            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return list;
    }

    public int selectBoardCount(Booktext option, String content, String recommendation) {
        int result = 0;
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT COUNT(*) AS cnt FROM booktext a JOIN book b ON a.book_no = b.books_no ";
            if(content != null) {
                sql += " WHERE b.books_title LIKE CONCAT('%','"+content+"','%')";
            }
            // 추천도가 선택된 경우 SQL에 조건을 추가합니다.
            if (recommendation != null && !recommendation.isEmpty() && !"0".equals(recommendation)) {
                sql += " AND a.recommendation_no = '"+recommendation+"'";
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                result = rs.getInt("cnt");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return result;
    }

    public  List<Map<String, String>> userBooktext(Booktext bt, String content, String recommendation, int user_no) {

        List<Map<String, String>> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try{
            String sql = "SELECT a.booktext_no AS 독후감번호,b.user_nickname AS 작성자 ,d.books_img AS 책이미지, d.books_title AS 책제목, c.description AS 추천도 ,a.upload_text AS 업로드 FROM `booktext` a \n" +
                    "JOIN users b ON a.user_no = b.user_no \n" +
                    "JOIN recommendation c ON a.recommendation_no = c.recommendation_no\n" +
                    "JOIN book d ON d.books_no = a.book_no WHERE b.user_no = ?";

            if(content != null) {
                sql += " AND d.books_title LIKE CONCAT('%','"+content+"','%')";
            }
            // 추천도가 선택된 경우 SQL에 조건을 추가합니다.
            if (recommendation != null && !recommendation.isEmpty() && !"0".equals(recommendation)) {
                sql += " AND c.recommendation_no = '"+recommendation+"'";

            }
            sql += " LIMIT "+bt.getLimitPageNo()+", "+bt.getNumPerPage();


            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,user_no);
            rs = pstmt.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while(rs.next()){
                Timestamp upload = rs.getTimestamp("업로드");

                Map<String, String> row = new HashMap<>();
                row.put("bt_no", rs.getString("독후감번호"));
                row.put("bt_writer", rs.getString("작성자"));
                row.put("bk_title", rs.getString("책제목"));
                row.put("recommendation", rs.getString("추천도"));
                row.put("bk_img", rs.getString("책이미지"));
                row.put("upload", sdf.format(upload) );

                list.add(row);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }
    public int userBooktextCount(Booktext option, String content, String recommendation, int user_no) {
        int result = 0;
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT COUNT(*) AS cnt FROM booktext a JOIN book b ON a.book_no = b.books_no WHERE a.user_no = ?";
            if(content != null) {
                sql += " AND b.books_title LIKE CONCAT('%','"+content+"','%')";
            }
            // 추천도가 선택된 경우 SQL에 조건을 추가합니다.
            if (recommendation != null && !recommendation.isEmpty() && !"0".equals(recommendation)) {
                sql += " AND a.recommendation_no = '"+recommendation+"'";
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,user_no);
            rs = pstmt.executeQuery();
            if(rs.next()) {

                result = rs.getInt("cnt");
                System.out.println(result);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
            close(conn);
        }
        return result;
    }



}
