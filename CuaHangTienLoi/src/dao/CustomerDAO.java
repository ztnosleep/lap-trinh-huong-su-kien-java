package dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import connectDB.ConnectDB;
import entity.Customer;

public class CustomerDAO {
	private Connection con;
	public CustomerDAO() {
    }

    public ArrayList<Customer> getAllKhachHang() {
        ArrayList<Customer> dsKH = new ArrayList<Customer>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM KhachHang";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
            	String maKh = rs.getString(1);
            	String sdt = rs.getString(2);
            	String tenKH = rs.getString(3);
            	Date ngaySinh = rs.getDate(4);
            	boolean gioiTinh = rs.getBoolean(5);
            	String diaChi = rs.getString(6);
            	int diemTL = rs.getInt(7);
            	
            	//String hienThiGioiTinh = gioiTinh?"Nam":"Nữ";
            	Customer c = new Customer(maKh, sdt, tenKH, diaChi, ngaySinh, gioiTinh, diemTL);
            	dsKH.add(c);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKH;
    }
    public boolean addCustomer(Customer customer) {
        
        PreparedStatement pstmt = null;
        try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            con = ConnectDB.getConnection(); 
            String sql = "INSERT INTO KhachHang (maKH, soDienThoai,tenKH, ngaySinh, gioiTinh, diaChi, diemTichLuy) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, customer.getMaKh());
            pstmt.setString(3, customer.getTenKh());
            pstmt.setString(2, customer.getSoDienThoai());
            pstmt.setDate(4, customer.getNgaySinh());
            pstmt.setBoolean(5, customer.isGioiTinh()); 
            pstmt.setString(6, customer.getDiaChi());
            pstmt.setInt(7, customer.getDiemTichLuy());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu có hàng được thêm
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        } finally {
            // Đóng kết nối và PreparedStatement
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean updateCustomer(Customer c) {
    	String sql = "update KhachHang set soDienThoai = ?, tenKH = ?, ngaySinh = ?, gioiTinh = ?, diaChi = ?, diemTichLuy = ? where maKH =?";
    	try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	con = ConnectDB.getConnection();
    	try(PreparedStatement ps = con.prepareStatement(sql)){
    		ps.setString(1, c.getSoDienThoai());
    		ps.setString(2, c.getTenKh());
    		ps.setDate(3, c.getNgaySinh());
    		ps.setBoolean(4, c.isGioiTinh());
    		ps.setString(5, c.getDiaChi());
    		ps.setInt(6, c.getDiemTichLuy());
    		ps.setString(7, c.getMaKh());
    		return ps.executeUpdate()>0;
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    	
    }
    public static Customer findCustomerByMaKh(String maKhachHang) {
    	try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Customer customer = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maKhachHang);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String maKh = rs.getString("maKH");
                String soDienThoai = rs.getString("soDienThoai");
                String tenKh = rs.getString("tenKH");
                Date ngaySinh = rs.getDate("ngaySinh");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                String diaChi = rs.getString("diaChi");
                int diemTichLuy = rs.getInt("diemTichLuy");

                customer = new Customer(maKh, soDienThoai, tenKh, diaChi, ngaySinh, gioiTinh, diemTichLuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }
    public Customer findCustomerByPhone(String sdt) {
    	try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Customer customer = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sdt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String maKh = rs.getString("maKH");
                String soDienThoai = rs.getString("soDienThoai");
                String tenKh = rs.getString("tenKH");
                Date ngaySinh = rs.getDate("ngaySinh");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                String diaChi = rs.getString("diaChi");
                int diemTichLuy = rs.getInt("diemTichLuy");

                customer = new Customer(maKh, soDienThoai, tenKh, diaChi, ngaySinh, gioiTinh, diemTichLuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    public ArrayList<Customer> findCustomerByName(String ten) {
    	try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ArrayList<Customer> customer = new ArrayList<Customer>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE tenKH Like ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + ten + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maKh = rs.getString("maKH");
                String soDienThoai = rs.getString("soDienThoai");
                String tenKh = rs.getString("tenKH");
                Date ngaySinh = rs.getDate("ngaySinh");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                String diaChi = rs.getString("diaChi");
                int diemTichLuy = rs.getInt("diemTichLuy");

                customer.add(new Customer(maKh, soDienThoai, tenKh, diaChi, ngaySinh, gioiTinh, diemTichLuy));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }
    public String getNextCustomerId() {
        String nextId = "KH01"; // Mặc định là mã kh01
      
        Statement stmt = null;
        ResultSet rs = null;
        try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
        	con= ConnectDB.getConnection();
            stmt = con.createStatement();
 
            String sql = "SELECT MAX(MaKH) AS MaxId FROM KhachHang"; // Giả sử bảng tên là Customers
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String maxId = rs.getString("MaxId");
                if (maxId != null) {
                    // Lấy phần số từ mã khách hàng
                    String numberPart = maxId.substring(2); // Bỏ qua "kh"
                    int nextNumber = Integer.parseInt(numberPart) + 1; // Tăng số lên 1
                    nextId = String.format("KH%02d", nextNumber); // Định dạng lại thành khXX
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return nextId;
    }
    public static void themDiemTichLuy(String maKH, int diemTL) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ConnectDB.getInstance();
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE KhachHang SET diemTichLuy = diemTichLuy + ? WHERE maKH = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, diemTL);
            pstmt.setString(2, maKH);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        }
    }
    public boolean isPhoneNumberExists(String phoneNumber) {
        boolean exists = false;
        try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con= ConnectDB.getConnection(); // Kết nối đến cơ sở dữ liệu
            String sql = "SELECT COUNT(*) FROM KhachHang WHERE soDienThoai = ?"; // Giả sử có cột phoneNumber trong bảng Customers
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, phoneNumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0; // Kiểm tra xem có số điện thoại nào trùng không
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return exists;
    }
    public boolean isPhoneNumberExistsUP(String phoneNumber, String maKH) {
        boolean exists = false;
        try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con= ConnectDB.getConnection(); // Kết nối đến cơ sở dữ liệu
            String sql = "SELECT COUNT(*) FROM KhachHang WHERE soDienThoai = ? and maKH <> ?"; // Giả sử có cột phoneNumber trong bảng Customers
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, phoneNumber);
            pstmt.setString(2, maKH);
            
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0; // Kiểm tra xem có số điện thoại nào trùng không
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return exists;
    }
    public static ArrayList<Object[]> tongKHTheoNgay(java.util.Date startDate, java.util.Date endDate) throws SQLException {
  	  // Khởi tạo danh sách để lưu kết quả
      ArrayList<Object[]> danhSachTongSLKhachHang = new ArrayList<>();
      
      // Kết nối với cơ sở dữ liệu
      ConnectDB.getInstance();
      Connection con = ConnectDB.getConnection();
      
      // SQL Query
      String sql = "SELECT HD.ngayLap AS Ngay, " +
              "COUNT(*) AS TongKhachHang, " +
              "SUM(HD.tongTien) AS TongTien " +  // Adding SUM for total amount
              "FROM HoaDon HD " +
              "JOIN KhachHang KH ON HD.maKH = KH.maKH " +
              "WHERE HD.ngayLap BETWEEN ? AND ? " + 
              "GROUP BY HD.ngayLap " +
              "ORDER BY HD.ngayLap";

      try (PreparedStatement statement = con.prepareStatement(sql)) {
          // Convert java.util.Date to java.sql.Date
          java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
          java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

          // Set tham số cho PreparedStatement
          statement.setDate(1, sqlStartDate);
          statement.setDate(2, sqlEndDate);

          // Thực thi câu lệnh và lấy kết quả
          try (ResultSet resultSet = statement.executeQuery()) {
              // Duyệt qua từng dòng kết quả và thêm vào danh sách
              while (resultSet.next()) {
                  // Lấy dữ liệu từ ResultSet
                  Date ngayLap = resultSet.getDate("Ngay");
                  int tongKH = resultSet.getInt("TongKhachHang");
                  Double tongTien = resultSet.getDouble("TongTien");
                  
                  
                  // Thêm vào danh sách kết quả
                  danhSachTongSLKhachHang.add(new Object[] {ngayLap, tongKH, tongTien});
              }
          }
      } catch (SQLException e) {
          // Xử lý lỗi nếu có
          e.printStackTrace();
          throw new SQLException("Error in executing query", e);
      }

      return danhSachTongSLKhachHang;
}
    public boolean updateCustomerPoints(String maKH, int newPoints) {
        String sql = "UPDATE KhachHang SET diemTichLuy = ? WHERE maKH = ?";
        PreparedStatement pstmt = null;
        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, newPoints);
            pstmt.setString(2, maKH);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }
}
