package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class BillDetailsDAO {
    public static void saveBillDetails(String maHD, String maSP, String tenSP, int soLuong, double donGia) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ConnectDB.getInstance();
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO ChiTietHoaDon (maHD, maSP, tenSP, soLuong, donGia) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maHD);
            pstmt.setString(2, maSP);
            pstmt.setString(3, tenSP);
            pstmt.setInt(4, soLuong);
            pstmt.setDouble(5, donGia);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        }
    }

    public static List<Object[]> getSalesReport(Date startDate, Date endDate) throws SQLException {
        List<Object[]> reports = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT h.ngayLap AS Ngay, " +
                     "l.tenLoai AS DanhMucSanPham, " +
                     "SUM(ct.soLuong) AS SoLuongBanDuoc, " +
                     "SUM(ct.soLuong * ct.donGia) AS DoanhThu " +
                     "FROM HoaDon h " +
                     "JOIN ChiTietHoaDon ct ON h.maHD = ct.maHD " +
                     "JOIN SanPham s ON ct.maSP = s.maSP " +
                     "JOIN LoaiSanPham l ON s.maLoai = l.maLoai " +
                     "WHERE h.ngayLap BETWEEN ? AND ? " +  // Lọc theo khoảng thời gian
                     "GROUP BY h.ngayLap, l.tenLoai " +
                     "ORDER BY h.ngayLap, l.tenLoai";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            // Thiết lập tham số cho khoảng thời gian
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Lấy dữ liệu từ resultSet và thêm vào danh sách
                    Date ngayLap = resultSet.getDate("Ngay");
                    String danhMucSanPham = resultSet.getString("DanhMucSanPham");
                    int soLuongBanDuoc = resultSet.getInt("SoLuongBanDuoc");
                    double doanhThu = resultSet.getDouble("DoanhThu");
                    reports.add(new Object[] {ngayLap, danhMucSanPham, soLuongBanDuoc, doanhThu});
                }
            }
        }
        return reports;
    }
    
    // Phương thức để lấy tổng doanh thu theo ngày
    public static List<Object> tongTienTheoNgay(java.util.Date startDate, java.util.Date endDate) {
        List<Object> danhSachTongTien = new ArrayList<>();
        String query = "SELECT ngayLap, SUM(tongTien) AS tongTien FROM HoaDon WHERE ngayLap BETWEEN ? AND ? GROUP BY ngayLap ORDER BY ngayLap";
        try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(query)) {
            // Đặt tham số ngày bắt đầu và ngày kết thúc
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date ngayLap = rs.getDate("ngayLap");
                float tongTien = rs.getFloat("tongTien");
                danhSachTongTien.add(new Object[] {ngayLap, tongTien});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachTongTien;
    }
}