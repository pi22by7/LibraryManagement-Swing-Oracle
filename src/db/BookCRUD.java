package db;

import models.Book;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BookCRUD {

    public boolean addBook(Book book)
    {
        String sql = "insert into BOOKS(book_isbn, book_title, publisher, available) values(?, ?, ?, ?)";
        int rows = 0;

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, book.getIsbn());
            ps.setString(2,  book.getTitle());
            ps.setString(3, book.getCategory());
            ps.setInt(4,  book.getQuantity());

            rows = ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return rows > 0;
    }
    public DefaultTableModel getByTitle(String title)
    {
        String sql = "select b.BOOK_ISBN, b.BOOK_TITLE, b.AUTHOR, b.PUBLISHER, b.AVAILABLE from books b where LOWER(b.BOOK_TITLE) like ?";
        Vector<String> colNames = new Vector<>();
        colNames.add("Book ISBN");
        colNames.add("Book Title");
        colNames.add("Publisher");
        colNames.add("Available");
        colNames.add("Author");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + title.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next())
            {
                Vector<String> row = new Vector<>();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(String.valueOf(rs.getInt(4)));
                row.add(rs.getString(5));
                data.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DefaultTableModel(data, colNames);
    }

//    public boolean issueBook(Issue bi)
//    {
//        String sql = "insert into issue_record(issue_id, u_id, issue_date, EXPIRY_DATE, book_isbn) values(?, ?, ?, ?, ?)";
//        String sqlCount = "update book set no_of_books = no_of_books - 1 where book_isbn = ?";
//        int rows = 0;
//        int rowsCount = 0;
//        java.sql.Date idate = new java.sql.Date(bi.getIssueDate().getTime());
//        java.sql.Date rdate = new java.sql.Date(bi.getReturnDate().getTime());
//
//        try(Connection conn = DBConnectionManager.getConnection())
//        {
//            PreparedStatement psCount = conn.prepareStatement(sqlCount);
//            psCount.setString(1, bi.getBook_isbn());
//            rowsCount = psCount.executeUpdate();
//
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, bi.getId());
//            ps.setString(2, bi.getuid());
//            ps.setDate(3, idate);
//            ps.setDate(4, rdate);
//            ps.setString(5, bi.getBook_isbn());
//            rows = ps.executeUpdate();
//        }
//        catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return rows > 0 && rowsCount > 0;
//    }

    public DefaultTableModel listIssuedBooks()
    {
        String sql = "select bi.issue_id, b.book_title, bi.u_id, s.name, bi.issue_date, bi.expiry_date, bi.book_isbn  from books b, member s, issue_record bi where b.book_isbn = bi.book_isbn and bi.u_id = s.u_id";
        Vector<String> colNames = new Vector<>();
        colNames.add("ID");
        colNames.add("Book Title");
        colNames.add("u_id");
        colNames.add("Students Name");
        colNames.add("Issue Date");
        colNames.add("Return Date");
        colNames.add("ISBN");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs != null && rs.next())
            {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(rs.getInt(1)));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(String.valueOf(rs.getDate(5)));
                row.add(String.valueOf(rs.getDate(6)));
                row.add(rs.getString(7));
                data.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new DefaultTableModel(data, colNames);
    }

}
