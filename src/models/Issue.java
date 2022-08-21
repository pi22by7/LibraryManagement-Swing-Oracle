package models;

import java.util.Date;

public class Issue {

    private int id;
    public String uid;
    public Date issue_date;
    public Date return_date;
    public String book_isbn;

    public Issue(String uid, Date issue_date, Date return_date, String book_isbn) {
        this.uid = uid;
        this.issue_date = issue_date;
        this.return_date = return_date;
        this.book_isbn = book_isbn;
    }

    public int getId() {
        return id;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public Date getIssueDate() {
        return issue_date;
    }

    public void setIssueDate(Date issue_date) {
        this.issue_date = issue_date;
    }

    public Date getReturnDate() {
        return return_date;
    }

    public void setReturnDate(Date return_date) {
        this.return_date = return_date;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    @Override
    public String toString() {
        return "" + id + "\t" + uid + "\t" + issue_date + "\t" + return_date + "\t" + book_isbn;
    }
}
