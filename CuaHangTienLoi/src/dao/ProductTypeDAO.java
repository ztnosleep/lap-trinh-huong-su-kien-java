package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Product;
import entity.ProductType;

public class ProductTypeDAO {
	private static Connection con;
	static ProductType pt;
	public ProductTypeDAO() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<ProductType> getProductType() {
		ArrayList<ProductType> dsML = new ArrayList<ProductType>();
		try {
			ConnectDB .getInstance();
			try {
				con = ConnectDB.getConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sql = "SELECT * FROM LoaiSanPham";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
            	String maLoai = rs.getString("maLoai");
                String tenLoai = rs.getString("tenLoai");
            	pt = new ProductType(maLoai, tenLoai);
            	dsML.add(pt);
            	
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dsML;
	}

	public String getNextProductTypeId() {
        String nextId = "LSP01"; 
      
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
 
            String sql = "SELECT MAX(maLoai) AS MaxId FROM LoaiSanPham"; 
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String maxId = rs.getString("MaxId");
                if (maxId != null) {
                    // Lấy phần số từ mã khách hàng
                    String numberPart = maxId.substring(3); 
                    int nextNumber = Integer.parseInt(numberPart) + 1; 
                    nextId = String.format("LSP%02d", nextNumber); 
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
    public boolean addPType(ProductType pt) {
        String sql = "INSERT INTO LoaiSanPham (maLoai, tenLoai) VALUES (?, ?)";

        try {
        	 ConnectDB.getInstance();
             con = ConnectDB.getConnection();
        	PreparedStatement statement = con.prepareStatement(sql);
            // Set parameters
            statement.setString(1, pt.getMaLoai());
            statement.setString(2, pt.getTenLoai());
             // Adjust if needed

            // Execute the statement
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if a product was added

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately in a real application
            return false; // Return false if there's an error
        }
    }

    public boolean deleteType(String maSP) {
        String sql = "DELETE FROM LoaiSanPham WHERE maLoai = ?";
        try {
        	ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maSP);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Trả về true nếu có bản ghi bị xóa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
    public boolean updateType(ProductType pt) {
    	String sql = "Update LoaiSanPham Set tenLoai = ? where maLoai = ?";
    	try {
			ConnectDB.getInstance();
			con = ConnectDB.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(2,pt.getMaLoai());
			ps.setString(1,pt.getTenLoai());
			
			int affectedRows = ps.executeUpdate();
			return affectedRows>0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
    	
    }

}
