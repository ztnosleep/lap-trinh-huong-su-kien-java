package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.Account;

public class AccountDAO {
	private Connection con;

	private ResultSet rs;

	public AccountDAO() {
		// TODO Auto-generated constructor stub
	}

    public Account getAllTaiKhoan(String username, String pass) {
        Account tk = null;

        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM TaiKhoan where TK = ? and MK = ?" ;
            
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, username);
            p.setString(2, pass);
            
            rs = p.executeQuery();

            while (rs.next()) {
                String maTk = rs.getString(1);
                Boolean role = rs.getBoolean(3);
                tk = new Account(maTk, rs.getString(2), role);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Có thể cải thiện thông báo lỗi ở đây
        }
        return tk;
    }
    public static void updatePassword(String maTK, String newPassword) throws SQLException {
        String sql = "UPDATE TaiKhoan SET MK = ? WHERE TK = ?";
        ConnectDB.getInstance();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, newPassword);
            pstmt.setString(2, maTK);
            pstmt.executeUpdate();
        }
    }
}
