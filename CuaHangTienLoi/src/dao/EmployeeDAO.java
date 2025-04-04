package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Employee;

public class EmployeeDAO {
    private static final Statement DatabaseConnection = null;
	private Connection con;

    
    public EmployeeDAO() {
        // Constructor
    }

    public String getAccountIdByEmployeeId(String maNhanVien) {
        String maTaiKhoan = "";
        String query = "SELECT maTK FROM NhanVien WHERE maNV = ?";
        try (PreparedStatement stmt = ConnectDB.getConnection().prepareStatement(query)) {
            stmt.setString(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maTaiKhoan = rs.getString("maTK");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maTaiKhoan;
    }



    /**
     * Kiểm tra trùng email.
     *
     * @param email Email cần kiểm tra.
     * @return True nếu email đã tồn tại, ngược lại là False.
     */
    public boolean isDuplicateEmail(String email) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kiểm tra trùng mã tài khoản.
     *
     * @param maTaiKhoan Mã tài khoản cần kiểm tra.
     * @return True nếu mã tài khoản đã tồn tại, ngược lại là False.
     */
    public boolean isDuplicateMaTaiKhoan(String maTaiKhoan) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE TK = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maTaiKhoan);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kiểm tra trùng số điện thoại.
     *
     * @param soDienThoai Số điện thoại cần kiểm tra.
     * @return True nếu số điện thoại đã tồn tại, ngược lại là False.
     */
    public boolean isDuplicateSoDienThoai(String soDienThoai) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE soDienThoai = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, soDienThoai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

  

    public String generateEmployeeID() {
        String sql = "SELECT MAX(maNV) FROM NhanVien"; // Tìm mã lớn nhất trong cơ sở dữ liệu
        String newId = "NV01"; // Mã mặc định nếu không có nhân viên nào

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String maxId = rs.getString(1); // Lấy mã lớn nhất
                if (maxId != null) {
                    // Tách phần số từ mã lớn nhất và tăng lên 1
                    int idNumber = Integer.parseInt(maxId.substring(2)) + 1;
                    newId = "NV" + String.format("%02d", idNumber); // Sinh mã mới
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }


    public boolean addEmployeeAndAccount(Employee emp, String maTK, String matKhau) {
    	Connection conn = ConnectDB.getConnection();

        boolean isAdded = false;
        
        try {
            conn.setAutoCommit(false);  // Bắt đầu giao dịch

            // Thêm tài khoản vào bảng TaiKhoan nếu chưa tồn tại
            String checkAccountSql = "SELECT * FROM TaiKhoan WHERE TK = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkAccountSql);
            checkStmt.setString(1, maTK);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {  // Nếu tài khoản không tồn tại
                String addAccountSql = "INSERT INTO TaiKhoan (TK, MK) VALUES (?, ?)";
                PreparedStatement addAccountStmt = conn.prepareStatement(addAccountSql);
                addAccountStmt.setString(1, maTK);
                addAccountStmt.setString(2, matKhau);
                addAccountStmt.executeUpdate();
            }

            // Thêm nhân viên vào bảng NhanVien
            String addEmployeeSql = "INSERT INTO NhanVien (maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, email, diaChi, maTK, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement addEmployeeStmt = conn.prepareStatement(addEmployeeSql);
            addEmployeeStmt.setString(1, emp.getMaNV());
            addEmployeeStmt.setString(2, emp.getTenNV());
            addEmployeeStmt.setBoolean(3, emp.isGioiTinh());
            addEmployeeStmt.setDate(4, emp.getNgaySinh());
            addEmployeeStmt.setDate(5, emp.getNgayVaoLam());
            addEmployeeStmt.setString(6, emp.getSoDienThoai());
            addEmployeeStmt.setString(7, emp.getEmail());
            addEmployeeStmt.setString(8, emp.getDiaChi());
            addEmployeeStmt.setString(9, maTK);
            addEmployeeStmt.setBoolean(10, emp.isTrangThai());
            addEmployeeStmt.executeUpdate();

            conn.commit();  // Hoàn tất giao dịch
            isAdded = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();  // Hoàn tác nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return isAdded;
    }

    /**
     * Lấy danh sách tất cả nhân viên
     * @return danh sách nhân viên
     */
    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();

            String sql = "SELECT * FROM NhanVien";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                Date ngaySinh = rs.getDate("ngaySinh");
                Date ngayVaoLam = rs.getDate("ngayVaoLam");
                String soDienThoai = rs.getString("soDienThoai");
                String email = rs.getString("email");
                String diaChi = rs.getString("diaChi");
                String maTK = rs.getString("maTK");
                boolean trangThai =rs.getBoolean("trangThai");

                Employee employee = new Employee(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, email, diaChi, maTK,trangThai);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    /**
     * Kiểm tra xem mã nhân viên đã tồn tại hay chưa
     * @param maNV mã nhân viên
     * @return true nếu nhân viên tồn tại, false nếu không tồn tại
     */
    public boolean isEmployeeExists(String maNV) {
        boolean exists = false;
        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM NhanVien WHERE maNV = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, maNV);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        exists = rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Thêm nhân viên mới vào cơ sở dữ liệu
     * @param employee nhân viên cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean addEmployee(Employee employee) {
        if (isEmployeeExists(employee.getMaNV())) {
            return false; // Mã nhân viên đã tồn tại
        }

        PreparedStatement pstmt = null;
        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO NhanVien (maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, email, diaChi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, employee.getMaNV());
            pstmt.setString(2, employee.getTenNV());
            pstmt.setBoolean(3, employee.isGioiTinh());
            pstmt.setDate(4, employee.getNgaySinh());
            pstmt.setDate(5, employee.getNgayVaoLam());
            pstmt.setString(6, employee.getSoDienThoai());
            pstmt.setString(7, employee.getEmail());
            pstmt.setString(8, employee.getDiaChi());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cập nhật thông tin nhân viên
     * @param employee nhân viên cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
//    public boolean updateEmployee(Employee employee) {
//        if (!isEmployeeExists(employee.getMaNV())) {
//            return false; // Nếu nhân viên không tồn tại thì không thể cập nhật
//        }
//
//        String sql = "UPDATE NhanVien SET tenNV = ?, gioiTinh = ?, ngaySinh = ?, ngayVaoLam = ?, soDienThoai = ?, email = ?, diaChi = ? WHERE maNV = ?";
//        try {
//            ConnectDB.getInstance();
//            con = ConnectDB.getConnection();
//            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//                pstmt.setString(1, employee.getTenNV());
//                pstmt.setBoolean(2, employee.isGioiTinh());
//                pstmt.setDate(3, employee.getNgaySinh());
//                pstmt.setDate(4, employee.getNgayVaoLam());
//                pstmt.setString(5, employee.getSoDienThoai());
//                pstmt.setString(6, employee.getEmail());
//                pstmt.setString(7, employee.getDiaChi());
//                pstmt.setString(8, employee.getMaNV());
//                return pstmt.executeUpdate() > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    
    public boolean updateEmployee(Employee employee) {
        if (!isEmployeeExists(employee.getMaNV())) {
            return false; // Nếu nhân viên không tồn tại thì không thể cập nhật
        }

        String sql = "UPDATE NhanVien SET tenNV = ?, gioiTinh = ?, ngaySinh = ?, ngayVaoLam = ?, soDienThoai = ?, email = ?, diaChi = ?, trangThai = ? WHERE maNV = ?";
        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, employee.getTenNV());
                pstmt.setBoolean(2, employee.isGioiTinh());
                pstmt.setDate(3, employee.getNgaySinh());
                pstmt.setDate(4, employee.getNgayVaoLam());
                pstmt.setString(5, employee.getSoDienThoai());
                pstmt.setString(6, employee.getEmail());
                pstmt.setString(7, employee.getDiaChi());
                pstmt.setBoolean(8, employee.isTrangThai()); // Đảm bảo trạng thái được truyền đúng
                pstmt.setString(9, employee.getMaNV());
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tìm kiếm nhân viên theo mã nhân viên
     * @param maNV mã nhân viên
     * @return nhân viên tìm được, hoặc null nếu không tìm thấy
     */
    public Employee findEmployeeByMaNV(String maNV) {
        Employee employee = null;
        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, maNV);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String tenNV = rs.getString("tenNV");
                        boolean gioiTinh = rs.getBoolean("gioiTinh");
                        Date ngaySinh = rs.getDate("ngaySinh");
                        Date ngayVaoLam = rs.getDate("ngayVaoLam");
                        String soDienThoai = rs.getString("soDienThoai");
                        String email = rs.getString("email");
                        String diaChi = rs.getString("diaChi");
                        String maTK = rs.getString("maTK");
                        boolean trangThai=rs.getBoolean("trangThai");
                        employee = new Employee(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, email, diaChi, maTK,trangThai);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * Tìm kiếm nhân viên theo tên
     * @param ten tên nhân viên
     * @return danh sách nhân viên tìm được
     */
    public ArrayList<Employee> findEmployeeByName(String ten) {
        ArrayList<Employee> empList = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM NhanVien WHERE tenNV LIKE ?")) {
            pstmt.setString(1, "%" + ten + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String maNV = rs.getString("maNV");
                    String tenNV = rs.getString("tenNV");
                    boolean gioiTinh = rs.getBoolean("gioiTinh");
                    Date ngaySinh = rs.getDate("ngaySinh");
                    Date ngayVaoLam = rs.getDate("ngayVaoLam");
                    String soDienThoai = rs.getString("soDienThoai");
                    String email = rs.getString("email");
                    String diaChi = rs.getString("diaChi");
                    String maTK = rs.getString("maTK");
                    boolean trangThai=rs.getBoolean("trangThai");
                    Employee employee = new Employee(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, email, diaChi, maTK,trangThai);
                    empList.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empList;
    }

    /**
     * Kiểm tra số điện thoại đã tồn tại trong hệ thống chưa
     * @param phoneNumber số điện thoại
     * @return true nếu số điện thoại đã tồn tại, false nếu chưa
     */
    public boolean isPhoneNumberExists(String phoneNumber) {
        boolean exists = false;
        try {
            ConnectDB.getInstance();
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM NhanVien WHERE soDienThoai = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, phoneNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        exists = rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Lấy thông tin nhân viên từ tài khoản người dùng
     * @param maTK mã tài khoản
     * @return đối tượng Employee nếu tìm thấy, null nếu không tìm thấy
     * @throws SQLException lỗi truy vấn SQL
     */
    public static Employee getEmployeeByUsername(String maTK) throws SQLException {
        Employee employee = null;

        try {
            ConnectDB.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Connection con = ConnectDB.getConnection();
        String sql = "SELECT nv.maNV, nv.tenNV, nv.gioiTinh, nv.ngaySinh, nv.ngayVaoLam, "
                   + "nv.soDienThoai, nv.email, nv.diaChi, tk.TK, tk.MK "
                   + "FROM NhanVien nv JOIN TaiKhoan tk ON nv.maTK = tk.TK "
                   + "WHERE tk.TK = ?"; // Thêm điều kiện WHERE

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, maTK); // Gán giá trị cho tham số

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) { // Chỉ cần lấy thông tin của 1 nhân viên
            String maNV = rs.getString(1);
            String tenNV = rs.getString(2);
            Date ngaySinh = rs.getDate(4);
            Date ngayVaoLam = rs.getDate(5);
            boolean gioiTinh = rs.getBoolean(3);
            String email = rs.getString(7);
            String diaChi = rs.getString(8);
            String sdt = rs.getString(6);
            String TK = rs.getString(9); // Cần đảm bảo Account có phương thức khởi tạo phù hợp
            boolean trangThai = rs.getBoolean(10);
            employee = new Employee(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, sdt, email, diaChi, TK,trangThai);
        }

        return employee; // Trả về nhân viên hoặc null nếu không tìm thấy
    }
    // Phương thức kiểm tra trạng thái của nhân viên
    public boolean isEmployeeActive(String maNv) throws SQLException {
        String query = "SELECT trangThai FROM NhanVien WHERE maTK = ?";
        boolean isActive = false;
        
        try (Connection conn = ConnectDB.getConnection(); // Giả sử bạn có ConnectDB.getConnection() để lấy kết nối
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, maNv); // Gán mã nhân viên vào câu lệnh truy vấn
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                isActive = rs.getBoolean("trangThai"); // Lấy trạng thái của nhân viên
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return isActive;
    }
}

