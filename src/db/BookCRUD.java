package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Author;
import models.Book;
import models.Issue;
import models.Students;
import java.util.Calendar;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

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

//    public boolean addAuthor(Author author)
//    {
//        String sql = "insert into author(author_name, author_mail_id, book_isbn) values(?, ?, ?)";
//        int rows = 0;
//
//        try(Connection conn = DBConnectionManager.getConnection())
//        {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, author.getName());
//            ps.setString(2,  author.getEmail());
//            ps.setString(3, author.getBook_isbn());
//
//            rows = ps.executeUpdate();
//        }
//        catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return rows > 0;
//    }

    public boolean addStudents(Students stud)
    {
        String sql = "insert into Member(u_id, name) values(?, ?)";
        int rows = 0;

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, stud.getuid());
            ps.setString(2,  stud.getName());

            rows = ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return rows >= 0;
    }

    public boolean StudentsExist(Students stud)
    {
        String sql = "select u_id, name from MEMBER where u_id = ?";
        boolean flag = false;;

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, stud.getuid());
            ResultSet rs = ps.executeQuery();
            if(rs != null && rs.next())
            {
                flag = true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    public DefaultTableModel getStudentsbyu_id(String u_id)
    {
        String sql = "select u_id, name from member where LOWER(u_id) = ?";
        Vector<String> colNames = new Vector<>();
        colNames.add("u_id");
        colNames.add("Name");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u_id.toLowerCase());

            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next())
            {
                Vector<String> row = new Vector<>();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                data.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DefaultTableModel(data, colNames);
    }

    public DefaultTableModel getStudentsbyName(String name)
    {
        String sql = "select u_id, name from member where LOWER(name) = ?";
        Vector<String> colNames = new Vector<>();
        colNames.add("u_id");
        colNames.add("Name");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name.toLowerCase());

            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next())
            {
                Vector<String> row = new Vector<>();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                data.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DefaultTableModel(data, colNames);
    }

    public DefaultTableModel getByTitle(String title)
    {
        String sql = "select b.book_isbn, b.book_title, b.publisher, b.available, b.author from books b, where LOWER(b.book_title) like ? ";
        Vector<String> colNames = new Vector<>();
        colNames.add("Book ISBN");
        colNames.add("Book Title");
        colNames.add("Category");
        colNames.add("Quantity");
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

    public DefaultTableModel getByCategory(String category)
    {
        String sql = "select b.book_isbn, b.book_title, b.category, b.no_of_books, a.author_name from BOOKS b, author a where a.book_isbn = b.book_isbn and LOWER(b.category) like ?";
        Vector<String> colNames = new Vector<>();
        colNames.add("Book ISBN");
        colNames.add("Book Title");
        colNames.add("Category");
        colNames.add("Quantity");
        colNames.add("Author");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + category.toLowerCase() + "%");
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

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return new DefaultTableModel(data, colNames);
    }

    public DefaultTableModel getByAuthor(String name)
    {
        String sql = "select b.book_isbn, b.book_title, b.category, b.no_of_books, a.author_name from book b, author a where b.book_isbn = a.book_isbn and LOWER(a.author_name) like ?";
        Book b = null;
        Vector<String> colNames = new Vector<>();
        colNames.add("Book ISBN");
        colNames.add("Book Title");
        colNames.add("Category");
        colNames.add("Quantity");
        colNames.add("Author");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name.toLowerCase() + "%");
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

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return new DefaultTableModel(data, colNames);
    }

    public DefaultTableModel getByIsbn(String isbn)
    {
        String sql = "select b.book_isbn, b.book_title, b.category, b.no_of_books, a.author_name from book b, author a where a.book_isbn = b.book_isbn and LOWER(b.book_isbn) = ?";
        Vector<String> colNames = new Vector<>();
        colNames.add("Book ISBN");
        colNames.add("Book Title");
        colNames.add("Category");
        colNames.add("Quantity");
        colNames.add("Author");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, isbn.toLowerCase());
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

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return new DefaultTableModel(data, colNames);
    }

    public DefaultTableModel getTableBookAuthor()
    {
        String sql = "select b.book_isbn, b.book_title, b.category, b.no_of_books, a.author_name from book b, author a where a.book_isbn = b.book_isbn";
        Vector<String> colNames = new Vector<>();
        colNames.add("Book ISBN");
        colNames.add("Book Title");
        colNames.add("Category");
        colNames.add("Quantity");
        colNames.add("Author");

        Vector<Vector<String>> data = new Vector<>();

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
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

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return new DefaultTableModel(data, colNames);
    }

    public boolean issueBook(Issue bi)
    {
        String sql = "insert into book_issue(issue_id, u_id, issue_date, return_date, book_isbn) values(?, ?, ?, ?, ?)";
        String sqlCount = "update book set no_of_books = no_of_books - 1 where book_isbn = ?";
        int rows = 0;
        int rowsCount = 0;
        java.sql.Date idate = new java.sql.Date(bi.getIssueDate().getTime());
        java.sql.Date rdate = new java.sql.Date(bi.getReturnDate().getTime());

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement psCount = conn.prepareStatement(sqlCount);
            psCount.setString(1, bi.getBook_isbn());
            rowsCount = psCount.executeUpdate();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bi.getId());
            ps.setString(2, bi.getuid());
            ps.setDate(3, idate);
            ps.setDate(4, rdate);
            ps.setString(5, bi.getBook_isbn());
            rows = ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return rows > 0 && rowsCount > 0;
    }

    public DefaultTableModel listBookByu_id(String u_id)
    {
        String sql = "select bi.issue_id, b.book_title, bi.u_id, s.name, bi.issue_date, bi.return_date, bi.book_isbn  from book b, Students s, book_issue bi where b.book_isbn = bi.book_isbn and bi.u_id = s.u_id and LOWER(bi.u_id) = ?";
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
            ps.setString(1, u_id);
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

    public DefaultTableModel listIssuedBooks()
    {
        String sql = "select bi.issue_id, b.book_title, bi.u_id, s.name, bi.issue_date, bi.return_date, bi.book_isbn  from book b, Students s, book_issue bi where b.book_isbn = bi.book_isbn and bi.u_id = s.u_id";
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

    public DefaultTableModel getBookToReturn(Date curDate)
    {
        String sql = "select bi.issue_id, b.book_title, bi.u_id, s.name, bi.issue_date, bi.return_date, bi.book_isbn  from book b, Students s, book_issue bi where b.book_isbn = bi.book_isbn and bi.u_id = s.u_id and bi.return_date = ?";
        Vector<String> colNames = new Vector<>();
        colNames.add("ID");
        colNames.add("Book Title");
        colNames.add("u_id");
        colNames.add("Students Name");
        colNames.add("Issue Date");
        colNames.add("Return Date");
        colNames.add("ISBN");

        Vector<Vector<String>> data = new Vector<>();
        java.sql.Date cdate = new java.sql.Date(curDate.getTime());

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, cdate);
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

    public boolean returnBook(int id, String isbn)
    {
        String sql = "Delete from book_issue where issue_id = ? ";

        String sqlCount = "update book set no_of_books = no_of_books+1 where book_isbn = ? ";

        int rows = 0;
        int rowsCount = 0;

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rows = ps.executeUpdate();

            PreparedStatement psCount = conn.prepareStatement(sqlCount);
            psCount.setString(1, isbn);
            rowsCount = psCount.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ((rows > 0) && (rowsCount > 0));
    }

    public String[] getAllCategory()
    {
        String sql = "Select unique(publisher) from book";
        List<String> list = new ArrayList<>();
        list.add("Select");
        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            while(rs != null && rs.next())
            {
                list.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] category = new String[list.size()];

        for(int i=0; i<list.size();i++)
        {
            category[i] = list.get(i);
        }

        return category;
    }


}
