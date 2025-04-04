
package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import dao.EmployeeDAO;
import entity.Employee;

public class EmployeePanel extends JPanel implements MouseListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMaNhanVien, txtTenNhanVien, txtSdt, txtDiaChi, txtEmail, txtSearch, txtMaTaiKhoan;
    private JComboBox<String> cboGioi, cbbTrangThai;
    private JDateChooser txtDateOfBirth, txtNgayVaoLam;
    private JButton btnTim, btnCapNhatNV, btnThemNV, btnLamMoi;
    private static JTable employeeTable;
    private DefaultTableModel employeeTableModel;

    public EmployeePanel() {
        setLayout(null);
        initialize();
        loadDataNhanVien();
    }

    private void initialize() {
        JPanel pnProduct = new JPanel();
        pnProduct.setBackground(new Color(240, 255, 255));
        pnProduct.setBorder(new LineBorder(Color.BLACK));
        pnProduct.setBounds(0, 0, 1644, 1052);
        add(pnProduct);
        pnProduct.setLayout(null);

        // Nút tìm kiếm
        btnTim = new JButton("");
        btnTim.setBackground(Color.WHITE);
        btnTim.setIcon(new ImageIcon("./img/find.png"));
        btnTim.setBounds(585, 32, 66, 37);
        pnProduct.add(btnTim);
        btnTim.addActionListener(e -> searchEmployee());

        txtSearch = new JTextField("Nhập tên nhân viên...");
        txtSearch.setColumns(10);
        txtSearch.setBounds(221, 32, 367, 37);
        pnProduct.add(txtSearch);

        JLabel lblSearchEmployee = new JLabel("Tìm nhân viên:");
        lblSearchEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblSearchEmployee.setBounds(34, 32, 177, 37);
        pnProduct.add(lblSearchEmployee);

        // Panel thông tin nhân viên
        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelInfo.setBackground(new Color(255, 255, 255));
        panelInfo.setBounds(49, 655, 1060, 386);
        panelInfo.setLayout(null);
        pnProduct.add(panelInfo);

        // Các thành phần thông tin trong panel
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblGioiTinh.setBounds(54, 185, 146, 27);
        panelInfo.add(lblGioiTinh);

        cboGioi = new JComboBox<>(new String[] {"Nam", "Nữ"});
        cboGioi.setBounds(235, 188, 102, 21);
        panelInfo.add(cboGioi);

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblMaNV.setBounds(54, 74, 157, 27);
        panelInfo.add(lblMaNV);
        txtMaNhanVien = new JTextField();
        txtMaNhanVien.setBounds(235, 81, 345, 23);
        panelInfo.add(txtMaNhanVien);

        JLabel lblTenNV = new JLabel("Tên nhân viên:");
        lblTenNV.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTenNV.setBounds(54, 112, 146, 27);
        panelInfo.add(lblTenNV);
        txtTenNhanVien = new JTextField();
        txtTenNhanVien.setBounds(235, 115, 345, 23);
        panelInfo.add(txtTenNhanVien);

        JLabel lblSdt = new JLabel("Số điện thoại:");
        lblSdt.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSdt.setBounds(54, 150, 133, 27);
        panelInfo.add(lblSdt);
        txtSdt = new JTextField();
        txtSdt.setBounds(235, 149, 345, 23);
        panelInfo.add(txtSdt);

        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblDiaChi.setBounds(54, 225, 133, 27);
        panelInfo.add(lblDiaChi);
        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(235, 229, 345, 84);
        panelInfo.add(txtDiaChi);

        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblNgaySinh.setBounds(623, 44, 133, 27);
        panelInfo.add(lblNgaySinh);
        txtDateOfBirth = new JDateChooser();
        txtDateOfBirth.getCalendarButton().setBackground(new Color(255, 255, 255));
        txtDateOfBirth.setBounds(760, 42, 248, 23);
        txtDateOfBirth.setDateFormatString("dd-MM-yyyy");
        txtDateOfBirth.getDateEditor().setEnabled(false);
        panelInfo.add(txtDateOfBirth);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblEmail.setBounds(623, 81, 133, 27);
        panelInfo.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(760, 79, 248, 23);
        panelInfo.add(txtEmail);

        JLabel lblNgayVaoLam = new JLabel("Ngày vào làm:");
        lblNgayVaoLam.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblNgayVaoLam.setBounds(623, 185, 133, 27);
        panelInfo.add(lblNgayVaoLam);
        txtNgayVaoLam = new JDateChooser();
        txtNgayVaoLam.getCalendarButton().setBackground(new Color(255, 255, 255));
        txtNgayVaoLam.setBounds(760, 188, 248, 23);
        txtNgayVaoLam.setDateFormatString("dd-MM-yyyy");
        txtNgayVaoLam.getDateEditor().setEnabled(false);
        panelInfo.add(txtNgayVaoLam);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblTrangThai.setBounds(623, 112, 107, 27);
        panelInfo.add(lblTrangThai);
        cbbTrangThai = new JComboBox<>(new String[] {"Đang làm", "Nghỉ làm"});
        cbbTrangThai.setBounds(760, 117, 102, 22);
        panelInfo.add(cbbTrangThai);

        JLabel lblMaTaiKhoan = new JLabel("Mã tài khoản:");
        lblMaTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblMaTaiKhoan.setBounds(54, 36, 146, 27);
        panelInfo.add(lblMaTaiKhoan);
        txtMaTaiKhoan = new JTextField();
        txtMaTaiKhoan.setBounds(235, 44, 345, 20);
        panelInfo.add(txtMaTaiKhoan);

        // Tạo bảng nhân viên
        employeeTableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Ngày sinh", "Giới tính", "Địa chỉ", "Email", "Ngày vào làm", "Trạng thái"}
        );
        employeeTable = new JTable(employeeTableModel);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.addMouseListener(this);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(49, 128, 1542, 495);
        pnProduct.add(scrollPane);

        btnCapNhatNV = new JButton("Chỉnh sửa thông tin nhân viên");
        btnCapNhatNV.setBackground(Color.WHITE);
        btnCapNhatNV.setBounds(1169, 816, 220, 44);
        pnProduct.add(btnCapNhatNV);
        btnCapNhatNV.addActionListener(e -> updateEmployee());

        btnThemNV = new JButton("Thêm nhân viên");
        btnThemNV.setBackground(Color.WHITE);
        btnThemNV.setBounds(1169, 736, 220, 44);
        pnProduct.add(btnThemNV);
        btnThemNV.addActionListener(e -> addEmployee());

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setBounds(1169, 896, 220, 44);
        pnProduct.add(btnLamMoi);
        btnLamMoi.addActionListener(e -> {
            clearForm(); // Xóa dữ liệu trên form
            loadDataNhanVien(); // Làm mới bảng
        });
    }

    private void loadDataNhanVien() {
        EmployeeDAO proDao = new EmployeeDAO();
        ArrayList<Employee> dssp = proDao.getAllEmployees();
        employeeTableModel.setRowCount(0);

        for (Employee emp : dssp) {
            employeeTableModel.addRow(new Object[]{
                emp.getMaNV(), emp.getTenNV(), emp.getSoDienThoai(), emp.getNgaySinh(),
                emp.isGioiTinh() ? "Nam" : "Nữ", emp.getDiaChi(), emp.getEmail(),
                emp.getNgayVaoLam(), emp.isTrangThai() ? "Đang làm" : "Nghỉ làm"
            });
        }
    }

    private void searchEmployee() {
        String tuKhoa = txtSearch.getText();
        if (!tuKhoa.isEmpty()) {
            EmployeeDAO empDao = new EmployeeDAO();
            ArrayList<Employee> employeeList = empDao.findEmployeeByName(tuKhoa);
            updateEmployeeTable(employeeList);
        } else {
            JOptionPane.showMessageDialog(employeeTable, "Vui lòng nhập tên nhân viên");
            loadDataNhanVien();
        }
    }

    private static void updateEmployeeTable(ArrayList<Employee> employeeList) {
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);

        for (Employee emp : employeeList) {
            model.addRow(new Object[]{
                emp.getMaNV(), emp.getTenNV(), emp.getSoDienThoai(), emp.getNgaySinh(),
                emp.isGioiTinh() ? "Nam" : "Nữ", emp.getDiaChi(), emp.getEmail(),
                emp.getNgayVaoLam(), emp.isTrangThai() ? "Đang làm" : "Nghỉ làm"
            });
        }
    }

//    private void addEmployee() {
//      
//        EmployeeDAO empDao = new EmployeeDAO();
//       
//        String maNv = txtMaNhanVien.getText().trim();
//        String tenNv = txtTenNhanVien.getText().trim();
//        boolean gioiTinh = cboGioi.getSelectedItem().toString().equals("Nam");
//        String sdt = txtSdt.getText().trim();
//        String diaChi = txtDiaChi.getText().trim();
//        java.util.Date dateOfBirth = txtDateOfBirth.getDate();
//        java.util.Date ngayVaoLam = txtNgayVaoLam.getDate();
//        String email = txtEmail.getText().trim();
//        boolean trangThai = cbbTrangThai.getSelectedItem().toString().equals("Đang làm");
//        String maTK = txtMaTaiKhoan.getText().trim();
//        String matKhau = "default_password";
//
//        // Kiểm tra thông tin bắt buộc
//        if (tenNv.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || maTK.isEmpty() || dateOfBirth == null || ngayVaoLam == null) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra trùng lặp trong cơ sở dữ liệu
//        if (empDao.isEmployeeExists(maNv)) {
//            JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (empDao.isDuplicateMaTaiKhoan(maTK)) {
//            JOptionPane.showMessageDialog(this, "Mã tài khoản đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (empDao.isDuplicateSoDienThoai(sdt)) {
//            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (empDao.isDuplicateEmail(email)) {
//            JOptionPane.showMessageDialog(this, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra ngày sinh lớn hơn 18 tuổi
//        java.util.Date today = new java.util.Date();
//        long ageInMillis = today.getTime() - dateOfBirth.getTime();
//        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
//        if (ageInYears < 18) {
//            JOptionPane.showMessageDialog(this, "Nhân viên phải lớn hơn 18 tuổi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra ngày sinh nhỏ hơn ngày vào làm
//        if (dateOfBirth.after(ngayVaoLam)) {
//            JOptionPane.showMessageDialog(this, "Ngày sinh phải nhỏ hơn ngày vào làm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra biểu thức chính quy
//        if (!sdt.matches("^\\d{10}$")) {
//            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
//            JOptionPane.showMessageDialog(this, "Email không hợp lệ! Vui lòng nhập đúng định dạng email.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Tạo đối tượng Employee
//        java.sql.Date ngaySinh = new java.sql.Date(dateOfBirth.getTime());
//        java.sql.Date ngayVaoLamDate = new java.sql.Date(ngayVaoLam.getTime());
//        Employee emp = new Employee(maNv, tenNv, gioiTinh, ngaySinh, ngayVaoLamDate, sdt, email, diaChi, maTK, trangThai);
//
//        // Thêm vào cơ sở dữ liệu
//        boolean result = empDao.addEmployeeAndAccount(emp, maTK, matKhau);
//
//        if (result) {
//            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            loadDataNhanVien();
//            clearForm();
//        } else {
//            JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void updateEmployee() {
//        EmployeeDAO empDao = new EmployeeDAO();
//
//        String maNv = txtMaNhanVien.getText();
//        String tenNv = txtTenNhanVien.getText().trim();
//        boolean gioiTinh = cboGioi.getSelectedItem().toString().equals("Nam");
//        String sdt = txtSdt.getText().trim();
//        String diaChi = txtDiaChi.getText().trim();
//        java.util.Date dateOfBirth = txtDateOfBirth.getDate();
//        java.util.Date ngayVaoLam = txtNgayVaoLam.getDate();
//        String email = txtEmail.getText().trim();
//        boolean trangThai = cbbTrangThai.getSelectedItem().toString().equals("Đang làm");
//
//        if (maNv.isEmpty() || tenNv.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || dateOfBirth == null || ngayVaoLam == null) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra ngày sinh lớn hơn 18 tuổi
//        java.util.Date today = new java.util.Date();
//        long ageInMillis = today.getTime() - dateOfBirth.getTime();
//        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
//        if (ageInYears < 18) {
//            JOptionPane.showMessageDialog(this, "Nhân viên phải lớn hơn 18 tuổi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra ngày sinh nhỏ hơn ngày vào làm
//        if (dateOfBirth.after(ngayVaoLam)) {
//            JOptionPane.showMessageDialog(this, "Ngày sinh phải nhỏ hơn ngày vào làm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra biểu thức chính quy
//        if (!sdt.matches("^\\d{10}$")) {
//            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
//            JOptionPane.showMessageDialog(this, "Email không hợp lệ! Vui lòng nhập đúng định dạng email.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        java.sql.Date ngaySinh = new java.sql.Date(dateOfBirth.getTime());
//        java.sql.Date ngayVaoLamDate = new java.sql.Date(ngayVaoLam.getTime());
//
//        Employee emp = new Employee(maNv, tenNv, gioiTinh, ngaySinh, ngayVaoLamDate, sdt, email, diaChi, "", trangThai);
//
//        boolean result = empDao.updateEmployee(emp);
//
//        if (result) {
//            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            loadDataNhanVien();
//        } else {
//            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private void addEmployee() {
        EmployeeDAO empDao = new EmployeeDAO();

        String maNv = txtMaNhanVien.getText().trim();
        String tenNv = txtTenNhanVien.getText().trim();
        boolean gioiTinh = cboGioi.getSelectedItem().toString().equals("Nam");
        String sdt = txtSdt.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        java.util.Date dateOfBirth = txtDateOfBirth.getDate();
        java.util.Date ngayVaoLam = txtNgayVaoLam.getDate();
        String email = txtEmail.getText().trim();
        boolean trangThai = cbbTrangThai.getSelectedItem().toString().equals("Đang làm");
        String maTK = txtMaTaiKhoan.getText().trim();
        String matKhau = "123456";

        // Kiểm tra mã NV bắt đầu bằng "NV"
        if (!maNv.startsWith("NV")) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên phải bắt đầu bằng 'NV'.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra mã TK bắt đầu bằng "TK"
        if (!maTK.startsWith("TK")) {
            JOptionPane.showMessageDialog(this, "Mã tài khoản phải bắt đầu bằng 'TK'.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra thông tin bắt buộc
        if (tenNv.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || maTK.isEmpty() || dateOfBirth == null || ngayVaoLam == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra trùng lặp trong cơ sở dữ liệu
        if (empDao.isEmployeeExists(maNv)) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empDao.isDuplicateMaTaiKhoan(maTK)) {
            JOptionPane.showMessageDialog(this, "Mã tài khoản đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empDao.isDuplicateSoDienThoai(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empDao.isDuplicateEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra ngày sinh lớn hơn 18 tuổi
        java.util.Date today = new java.util.Date();
        long ageInMillis = today.getTime() - dateOfBirth.getTime();
        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
        if (ageInYears < 18) {
            JOptionPane.showMessageDialog(this, "Nhân viên phải lớn hơn 18 tuổi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra ngày sinh nhỏ hơn ngày vào làm
        if (dateOfBirth.after(ngayVaoLam)) {
            JOptionPane.showMessageDialog(this, "Ngày sinh phải nhỏ hơn ngày vào làm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra biểu thức chính quy
        if (!sdt.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ! Vui lòng nhập đúng định dạng email.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng Employee
        java.sql.Date ngaySinh = new java.sql.Date(dateOfBirth.getTime());
        java.sql.Date ngayVaoLamDate = new java.sql.Date(ngayVaoLam.getTime());
        Employee emp = new Employee(maNv, tenNv, gioiTinh, ngaySinh, ngayVaoLamDate, sdt, email, diaChi, maTK, trangThai);

        // Thêm vào cơ sở dữ liệu
        boolean result = empDao.addEmployeeAndAccount(emp, maTK, matKhau);

        if (result) {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadDataNhanVien();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() {
        EmployeeDAO empDao = new EmployeeDAO();

        String maNv = txtMaNhanVien.getText().trim();
        String tenNv = txtTenNhanVien.getText().trim();
        boolean gioiTinh = cboGioi.getSelectedItem().toString().equals("Nam");
        String sdt = txtSdt.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        java.util.Date dateOfBirth = txtDateOfBirth.getDate();
        java.util.Date ngayVaoLam = txtNgayVaoLam.getDate();
        String email = txtEmail.getText().trim();
        boolean trangThai = cbbTrangThai.getSelectedItem().toString().equals("Đang làm");

        // Kiểm tra mã NV bắt đầu bằng "NV"
        if (!maNv.startsWith("NV")) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên phải bắt đầu bằng 'NV'.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra thông tin bắt buộc
        if (maNv.isEmpty() || tenNv.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || dateOfBirth == null || ngayVaoLam == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra ngày sinh lớn hơn 18 tuổi
        java.util.Date today = new java.util.Date();
        long ageInMillis = today.getTime() - dateOfBirth.getTime();
        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
        if (ageInYears < 18) {
            JOptionPane.showMessageDialog(this, "Nhân viên phải lớn hơn 18 tuổi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra ngày sinh nhỏ hơn ngày vào làm
        if (dateOfBirth.after(ngayVaoLam)) {
            JOptionPane.showMessageDialog(this, "Ngày sinh phải nhỏ hơn ngày vào làm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra biểu thức chính quy
        if (!sdt.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ! Vui lòng nhập đúng định dạng email.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.sql.Date ngaySinh = new java.sql.Date(dateOfBirth.getTime());
        java.sql.Date ngayVaoLamDate = new java.sql.Date(ngayVaoLam.getTime());

        Employee emp = new Employee(maNv, tenNv, gioiTinh, ngaySinh, ngayVaoLamDate, sdt, email, diaChi, "", trangThai);

        boolean result = empDao.updateEmployee(emp);

        if (result) {
            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadDataNhanVien();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtMaNhanVien.setText("");
        txtTenNhanVien.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtMaTaiKhoan.setText("");
        cboGioi.setSelectedIndex(0);
        cbbTrangThai.setSelectedIndex(0);
        txtDateOfBirth.setDate(null);
        txtNgayVaoLam.setDate(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }
    


    @Override
    public void mousePressed(MouseEvent e) {
        int row = employeeTable.getSelectedRow();
        if (row != -1) {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                
                String maNhanVien = employeeTable.getValueAt(row, 0).toString();
                String birthDateString = employeeTable.getValueAt(row, 3).toString();
                java.util.Date birthDate = inputFormatter.parse(birthDateString);

                String joinDateString = employeeTable.getValueAt(row, 7).toString();
                java.util.Date joinDate = inputFormatter.parse(joinDateString);

              
                txtMaNhanVien.setText(maNhanVien);
                txtTenNhanVien.setText(employeeTable.getValueAt(row, 1).toString());
                txtSdt.setText(employeeTable.getValueAt(row, 2).toString());
                txtDateOfBirth.setDate(birthDate);
                cboGioi.setSelectedItem(employeeTable.getValueAt(row, 4).toString());
                txtDiaChi.setText(employeeTable.getValueAt(row, 5).toString());
                txtEmail.setText(employeeTable.getValueAt(row, 6).toString());
                txtNgayVaoLam.setDate(joinDate);
                cbbTrangThai.setSelectedItem(employeeTable.getValueAt(row, 8).toString());

                // Lấy mã tài khoản từ cơ sở dữ liệu
                EmployeeDAO empDao = new EmployeeDAO();
                String maTaiKhoan = empDao.getAccountIdByEmployeeId(maNhanVien);

              
                if (maTaiKhoan.isEmpty()) {
                    txtMaTaiKhoan.setText("");
                    JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản cho nhân viên này.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    txtMaTaiKhoan.setText(maTaiKhoan);
                }

            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
