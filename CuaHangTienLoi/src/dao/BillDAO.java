package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Bill;
import entity.Customer;

public class BillDAO {
    public static void saveBill(String maHD, String maNV, String maKH, double totalAmount) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ConnectDB.getInstance();
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO HoaDon (maHD, ngayLap, tongTien, maNV, maKH) VALUES (?, getDate(), ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maHD);
            pstmt.setDouble(2, totalAmount);
            pstmt.setString(3, maNV);
            pstmt.setString(4, maKH);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        }
    }
    public ArrayList<Bill> getAllBill() {
        ArrayList<Bill> dsHD = new ArrayList<Bill>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * from HoaDon";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
            	String maHD = rs.getString(1);
            	String maKH = rs.getString(5);
            	String maNV = rs.getString(4);
            	Date ngayLap = rs.getDate(2);
            	double tongTien = rs.getDouble(3);
            	
            	Bill b = new Bill(maHD, maKH, maNV, ngayLap, tongTien);
            	dsHD.add(b);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }
}