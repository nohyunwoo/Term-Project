package termproject;
import java.sql.*;
public class Main
{
	public static void main(String args[]){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://192.168.201.3:4567/madang","hyunwoo","database2024!!");
			Statement stmt=con.createStatement(); // stmt: 데이터베이스에 sql쿼리를 보내고 실행하는 역할
			
			// 데이터 삽입
			String insertSQL = "INSERT INTO Book (bookid, bookname, publisher) VALUES (20, 'Databasae란', '노현우출판사')";
            int insertResult = stmt.executeUpdate(insertSQL);
            if (insertResult > 0) { // 데이터 삽입이 성공 된다면 1
                System.out.println("<데이터 삽입 성공>");
            }
			// 삽입 후 데이터 조회
            System.out.println("=삽입 후 데이터 현황=");
            ResultSet rs=stmt.executeQuery("SELECT * FROM Book");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
			}
			rs.close();
			
            // 데이터 삭제
            String deleteSQL = "DELETE FROM Book WHERE bookid = 20";
            int deleteResult = stmt.executeUpdate(deleteSQL); 
            if (deleteResult > 0) { // 데이터 삭제가 성공 된다면 1
                System.out.println("<데이터 삭제 성공>");
            }
            
            // 삭제 후 데이터 조회
            System.out.println("=삭제 후 데이터 현황=");
			rs=stmt.executeQuery("SELECT * FROM Book");
			while(rs.next())
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
			con.close();
			}catch(Exception e){ System.out.println(e);}
	}
	
}