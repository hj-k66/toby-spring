package com.toby.dao;

import com.toby.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt){
        Connection c =  null;
        PreparedStatement pstmt = null;

        try {
            c = dataSource.getConnection();

            // Query문 작성
            pstmt = stmt.makePreparedStatement(c);

            // Query문 실행
            pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void deleteAll(){
        jdbcContextWithStatementStrategy(new DeleteAllStrategy());
    }

    public int getCount(){
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();

            // Query문 작성
            pstmt = c.prepareStatement("SELECT count(*) FROM users;");

            // Query문 실행
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public void add(User user) {
        jdbcContextWithStatementStrategy(new AddStrategy(user));
    }

    public User findById(String id) {

        try {
            Connection c = dataSource.getConnection();


            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();

            User user = null;
            if(rs.next()){
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }

            rs.close();
            pstmt.close();
            c.close();

            if(user==null){
                throw new EmptyResultDataAccessException(1);
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
