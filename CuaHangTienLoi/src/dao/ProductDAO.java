package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import connectDB.ConnectDB;
import entity.Customer;
import entity.Product;

public class ProductDAO {
	private Connection con;

	public ProductDAO() {
		// Initialize the connection if needed
	}

	public ArrayList<Product> getAllProduct() {
		ArrayList<Product> productList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement p = null;

		try {
			ConnectDB.getInstance();
			con = ConnectDB.getConnection();
			String sql = "SELECT sp.maSP, sp.tenSP, sp.donGia, sp.ngaySX, sp.hanSD, sp.donViTinh, sp.moTa, sp.soLuongTon, sp.xuatXu, sp.anh, c.tenLoai FROM SanPham sp JOIN LoaiSanPham c ON sp.maLoai = c.maLoai";

			p = con.prepareStatement(sql);
			rs = p.executeQuery();

			while (rs.next()) {

				String maSP = rs.getString("maSP");
				String tenSP = rs.getString("tenSP");
				double donGia = rs.getDouble("donGia");
				Date ngaySX = rs.getDate("ngaySX");
				Date hanSD = rs.getDate("hanSD");
				String DVT = rs.getString("donViTinh");
				String moTa = rs.getString("moTa");
				String xuatXu = rs.getString("xuatXu");
				int slTon = rs.getInt("soLuongTon");
				String maLoai = rs.getString("tenLoai");
				byte[] imagePath = rs.getBytes("anh");

				// Create Product object
				Product product = new Product(maSP, tenSP, donGia, ngaySX, hanSD, DVT, moTa, slTon, xuatXu, maLoai, imagePath);
				productList.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Consider logging this in a real application
		} finally {
			closeResources(rs, p, con);
		}
		return productList;
	}
	public boolean addProduct(Product pro) {
		String sql = "INSERT INTO SanPham(maSP, tenSP, donGia, ngaySX, hanSD, donViTinh, moTa, soLuongTon, xuatXu, maLoai, anh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        con = ConnectDB.getConnection();
	    try (PreparedStatement p = con.prepareStatement(sql)) {
	        setProductParameters(p, pro);
	        return p.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace(); // Consider logging this
	        return false;
	    }
	}
	public String getNextProductId() {
        String nextId = "SP01"; // Mặc định là mã kh01
      
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
 
            String sql = "SELECT MAX(maSP) AS MaxId FROM SanPham"; // Giả sử bảng tên là Customers
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String maxId = rs.getString("MaxId");
                if (maxId != null) {
                   
                    String numberPart = maxId.substring(2); 
                    int nextNumber = Integer.parseInt(numberPart) + 1; // Tăng số lên 1
                    nextId = String.format("SP%02d", nextNumber); 
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
	public ArrayList<Product> findProductByName(String name){
		try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ArrayList<Product> productList = new ArrayList<Product>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT sp.maSP, sp.tenSP, sp.donGia, sp.ngaySX, sp.hanSD, sp.donViTinh, sp.moTa, sp.soLuongTon, sp.xuatXu, sp.anh, c.tenLoai FROM SanPham sp JOIN LoaiSanPham c ON sp.maLoai = c.maLoai WHERE sp.tenSP Like ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	String maSP = rs.getString("maSP");
				String tenSP = rs.getString("tenSP");
				double donGia = rs.getDouble("donGia");
				Date ngaySX = rs.getDate("ngaySX");
				Date hanSD = rs.getDate("hanSD");
				String DVT = rs.getString("donViTinh");
				String moTa = rs.getString("moTa");
				String xuatXu = rs.getString("xuatXu");
				int slTon = rs.getInt("soLuongTon");
				String maLoai = rs.getString("tenLoai");
				byte[] imagePath = rs.getBytes("anh");

				// Create Product object
				Product product = new Product(maSP, tenSP, donGia, ngaySX, hanSD, DVT, moTa, slTon, xuatXu, maLoai, imagePath);
				productList.add(product);
            }
        }catch (Exception e) {
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
        return productList;
	}
	public ArrayList<Product> searchProducts(String category) {
	    ArrayList<Product> productList = new ArrayList<>();
	    try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Ensure the connection is defined
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        con = ConnectDB.getConnection(); // Kết nối đến cơ sở dữ liệu
	        String sql = "SELECT sp.maSP, sp.tenSP, sp.donGia, sp.ngaySX, sp.hanSD, sp.donViTinh, sp.moTa, sp.soLuongTon, sp.xuatXu, sp.anh, c.tenLoai FROM SanPham sp JOIN LoaiSanPham c ON sp.maLoai = c.maLoai WHERE c.tenLoai = ?"; // Khởi tạo truy vấn
	        ArrayList<String> conditions = new ArrayList<>();

//	        if (!category.equals("")) { // Kiểm tra nếu không phải "Tất cả"
//	            sql += " AND sp.maLoai = ?"; // Thêm điều kiện cho loại sản phẩm
//	            conditions.add(category);
//	        }

//	        if (!productName.isEmpty()) { // Nếu có từ khóa tìm kiếm
//	            sql += " AND sp.tenSP LIKE ?"; // Thêm điều kiện cho tên sản phẩm
//	            conditions.add("%" + productName + "%");
//	        }

	        pstmt = con.prepareStatement(sql);

	        // Thiết lập tham số cho PreparedStatement
	        
	            pstmt.setString( 1, category); 
	        

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String maSP = rs.getString("maSP");
	            String tenSP = rs.getString("tenSP");
	            double donGia = rs.getDouble("donGia");
	            Date ngaySX = rs.getDate("ngaySX");
	            Date hanSD = rs.getDate("hanSD");
	            String DVT = rs.getString("donViTinh");
	            String moTa = rs.getString("moTa");
	            String xuatXu = rs.getString("xuatXu");
	            int slTon = rs.getInt("soLuongTon");
	            String maLoai = rs.getString("tenLoai");
	            byte[] imagePath = rs.getBytes("anh");

	            // Create Product object
	            Product product = new Product(maSP, tenSP, donGia, ngaySX, hanSD, DVT, moTa, slTon, xuatXu, maLoai, imagePath);
	            productList.add(product);
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

	    return productList;
	}
	public boolean updateProduct(Product product) {
	    String sql = "UPDATE SanPham SET tenSP = ?, donGia = ?, ngaySX = ?, hanSD = ?, donViTinh = ?, moTa = ?, soLuongTon = ?, xuatXu = ?, maLoai = ?, anh = ? WHERE maSP = ?";
	    try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        con = ConnectDB.getConnection();
	    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	    	//pstmt.setString(1, product.getMaSP());
	 	    pstmt.setString(1, product.getTenSP());
	 	    pstmt.setDouble(2, product.getDonGia());
	 	    pstmt.setDate(3,new java.sql.Date(product.getNgaySX().getTime()));
	 		pstmt.setDate(4, new java.sql.Date(product.getHanSD().getTime()));
	 	    pstmt.setString(5, product.getDonViTinh());
	 	    pstmt.setString(6, product.getMoTa());
	 	    pstmt.setInt(7, product.getSoLuongTon());
	 	    pstmt.setString(8, product.getXuatXu());
	 	    pstmt.setString(9, product.getMaLoai());
	 	    pstmt.setBytes(10, product.getImage()); 
	        pstmt.setString(11, product.getMaSP());
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace(); // Consider logging this
	        return false;
	    }
	}

	private void setProductParameters(PreparedStatement pstmt, Product product) throws SQLException {
	    pstmt.setString(1, product.getMaSP());
	    pstmt.setString(2, product.getTenSP());
	    pstmt.setDouble(3, product.getDonGia());
	    pstmt.setDate(4, new java.sql.Date(product.getNgaySX().getTime()));
		pstmt.setDate(5, new java.sql.Date(product.getHanSD().getTime()));
	    pstmt.setString(6, product.getDonViTinh());
	    pstmt.setString(7, product.getMoTa());
	    pstmt.setInt(8, product.getSoLuongTon());
	    pstmt.setString(9, product.getXuatXu());
	    pstmt.setString(10, product.getMaLoai());
	    pstmt.setBytes(11, product.getImage()); // Ensure this is set for addProduct only
	}
	
	public boolean deleteProduct(String maSP) {
		String sql = "DELETE FROM SanPham WHERE maSP = ?";

		try {
			ConnectDB.getInstance();
			con = ConnectDB.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, maSP);

			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources(null, null, con);
		}
	}
	public static boolean updateProductQuantity(String maSP, int quantitySold) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
			ConnectDB.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            conn = ConnectDB.getConnection(); // Kết nối đến cơ sở dữ liệu

            // Giảm số lượng sản phẩm trong cơ sở dữ liệu
            String sql = "UPDATE SanPham SET SoLuongTon = SoLuongTon - ? WHERE MaSP = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quantitySold);
            pstmt.setString(2, maSP);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi, trả về false
        } finally {
            // Đóng kết nối
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
	public boolean updateProductStock(String maSP, int newStockQuantity) {
	    String sql = "UPDATE SanPham SET soLuongTon = ? WHERE maSP = ?";
	    PreparedStatement pstmt = null;
	    try {
	        ConnectDB.getInstance();
	        con = ConnectDB.getConnection();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, newStockQuantity);
	        pstmt.setString(2, maSP);

	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0; // Return true if update was successful
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
	private void closeResources(ResultSet rs, PreparedStatement p, Connection c) {
		try {
			if (rs != null) rs.close();
			if (p != null) p.close();
			if (c != null) c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}