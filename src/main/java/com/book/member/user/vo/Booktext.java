package com.book.member.user.vo;

import com.book.common.Paging;

import java.time.LocalDate;

public class Booktext extends Paging {
    private int booktext_no;
    private LocalDate book_first_read;
    private LocalDate book_end_read;
    private String book_content;
    private int recommendation_no;
    private int book_no;
    private int user_no;

    public Booktext() {

    }

    public Booktext(int booktext_no, int user_no, int book_no, String book_content, LocalDate book_end_read, LocalDate book_first_read, int recommendation_no) {
        this.booktext_no = booktext_no;
        this.user_no = user_no;
        this.book_no = book_no;
        this.book_content = book_content;
        this.book_end_read = book_end_read;
        this.book_first_read = book_first_read;
        this.recommendation_no = recommendation_no;
    }

    public int getBooktext_no() {
        return booktext_no;
    }

    public void setBooktext_no(int booktext_no) {
        this.booktext_no = booktext_no;
    }

    public LocalDate getBook_first_read() {
        return book_first_read;
    }

    public void setBook_first_read(LocalDate book_first_read) {
        this.book_first_read = book_first_read;
    }

    public LocalDate getBook_end_read() {
        return book_end_read;
    }

    public void setBook_end_read(LocalDate book_end_read) {
        this.book_end_read = book_end_read;
    }

    public String getBook_content() {
        return book_content;
    }

    public void setBook_content(String book_content) {
        this.book_content = book_content;
    }

    public Integer getRecommendation_no() {
        return recommendation_no;
    }

    public void setRecommendation_no(int recommendation_no) {
        this.recommendation_no = recommendation_no;
    }

    public int getBook_no() {
        return book_no;
    }

    public void setBook_no(int book_no) {
        this.book_no = book_no;
    }

    public int getUser_no() {
        return user_no;
    }

    public void setUser_no(int user_no) {
        this.user_no = user_no;
    }

    @Override
    public String toString() {
        return "BookText{" +
                "booktext_no=" + booktext_no +
                ", book_first_read=" + book_first_read +
                ", book_end_read=" + book_end_read +
                ", book_content='" + book_content + '\'' +
                ", recommendation_no=" + recommendation_no +
                ", book_no=" + book_no +
                ", user_no=" + user_no +
                '}';
    }
}