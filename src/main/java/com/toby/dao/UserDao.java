package com.toby.dao;

import com.toby.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;
    private final JdbcContext jdbcContext;
    //생성자에서 초기화
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);

    }


    public void deleteAll(){
        this.jdbcContext.workJdbcContextWithStatementStrategy(connection -> {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users;");
            return pstmt;
        });
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
        this.jdbcContext.workJdbcContextWithStatementStrategy(connection -> {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            return pstmt;
        });
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
