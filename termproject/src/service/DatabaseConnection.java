package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://192.168.201.3:4567/project";
    private static final String USER = "hyunwoo";
    private static final String PASSWORD = "database2024!!";

    // 데이터베이스 연결 메서드
    public static Connection getConnection() throws SQLException {
        try {	
            Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        }

        // 연결 생성
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 연결 종료 메서드
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("데이터베이스 연결이 종료되었습니다.");
            } catch (SQLException e) {
                System.err.println("데이터베이스 연결 종료 중 오류 발생.");
                e.printStackTrace();
            }
        }
    }
}
