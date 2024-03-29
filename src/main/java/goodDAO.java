import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class goodDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public goodDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void goodUp() {
		try {
			con = dataFactory.getConnection();
			
			String query = "update gaechu set good=goodcount.nextval";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getGoodCount() {
		goodDTO goodDTO = new goodDTO();
		try {
			con = dataFactory.getConnection();
			
			String query = "select * from gaechu";
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			int goodCount = rs.getInt("good");
			
			pstmt.close();
			
			System.out.println(goodCount);
			return goodCount;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
