package com.toby.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workJdbcContextWithStatementStrategy(StatementStrategy stmt){
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
}
