package com.book.member.user.dao;

import com.book.member.user.vo.ApplyBook;
import com.book.member.user.vo.Booktext;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplyBookDao {
    public int inputApply(ApplyBook ab){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int result = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/book_reviews";
            String user = "chaedud02";
            String pwd ="0000";
            conn = DriverManager.getConnection(url,user,pwd);
            conn.setAutoCommit(false);


            String sql = "SELECT COUNT(*) FROM book WHERE book_title =? AND book_author =? AND book_publisher = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ab.getApply_bk_title());
            pstmt.setString(2, ab.getApply_bk_author());
            pstmt.setString(3, ab.getApply_bk_publisher());
            rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("책이 이미 존재합니다.");
                result = 0;
            } else {
                String sql2 = "SELECT COUNT(*) FROM book_apply WHERE apply_bk_title =? AND apply_bk_author =? AND apply_bk_publisher = ?";
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, ab.getApply_bk_title());
                pstmt.setString(2, ab.getApply_bk_author());
                pstmt.setString(3, ab.getApply_bk_publisher());
                rs = pstmt.executeQuery();
                rs.next();
                int count2 = rs.getInt(1);

                if (count2 > 0) {
                    System.out.println("책 신청이 이미 존재합니다.");
                    result = 0;
                }else {
                    String sql3 = "INSERT INTO book_apply (apply_bk_title, apply_bk_author, apply_bk_publisher) VALUES (?,?,?)";
                    pstmt = conn.prepareStatement(sql3);
                    pstmt.setString(1, ab.getApply_bk_title());
                    pstmt.setString(2, ab.getApply_bk_author());
                    pstmt.setString(3, ab.getApply_bk_publisher());
                    result = pstmt.executeUpdate();
                    System.out.println(result);
                }
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }

        return result;

    }
    public List<ApplyBook> selectBooktext(ApplyBook ab, String title) {

        List<ApplyBook> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/book_reviews";
            String user = "chaedud02";
            String pwd ="0000";
            conn = DriverManager.getConnection(url,user,pwd);

            String sql = "SELECT a.booktext_no AS 독후감번호,b.user_name AS 작성자 ,d.book_img AS 책이미지, d.book_title AS 책제목, c.description AS 추천도 ,a.upload_text AS 업로드 FROM `booktext` a \n" +
                    "JOIN users b ON a.user_no = b.user_no \n" +
                    "JOIN recommendation c ON a.recommendation_no = c.recommendation_no\n" +
                    "JOIN book d ON d.book_no = a.book_no";

            if(title != null) {
                sql += " WHERE d.book_title LIKE CONCAT('%','"+title+"','%')";
            }
            sql += " LIMIT "+ab.getLimitPageNo()+", "+ab.getNumPerPage();

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

                //list.add(row);
                //여기 단일 리스트 사용하게 하기

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }

    public int selectBoardCount(ApplyBook option, String content) {
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

            String sql = "SELECT COUNT(*) AS cnt FROM booktext a JOIN book b ON a.book_no = b.book_no ";
            if(content != null) {
                sql += " WHERE b.book_title LIKE CONCAT('%','"+content+"','%')";
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                result = rs.getInt("cnt");
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
