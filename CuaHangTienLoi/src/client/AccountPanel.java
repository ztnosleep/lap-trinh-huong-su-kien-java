package client;

import java.awt.EventQueue;


import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.AccountDAO;
import dao.EmployeeDAO;
import entity.AccountCurrent;
import entity.Employee;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Scrollbar;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;

public class AccountPanel extends JFrame implements ActionListener{
	static JTextField txtMaNV;
	private JTextField txtHoTen;
	private JTextField txtEmail;
	private JDateChooser ngaySinh;
	private JDateChooser ngayVaoLam;
	private JTextField txtMaTK;
	private JPasswordField passwordField;
	private JTextField pwdNew;
	private JTextField pwdNewConfirm;
	private JButton btnExit;
	private JComboBox comboBoxGT;
	private JTextField txtSDT;
	private JTextArea textAreaDC;


	public AccountPanel() {
		initialize();
		loadDataNhanVien();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(900,600);
		setTitle("Thông tin");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/icon.png"));
		setBackground(new Color(240,255,255));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);


		btnExit = new JButton("Đóng");
		btnExit.setBackground(new Color(255, 255, 255));
		btnExit.setBounds(772, 512, 102, 38);
		getContentPane().add(btnExit);

		JPanel pnNhanVien = new JPanel();
		pnNhanVien.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnNhanVien.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
		pnNhanVien.setBounds(10, 32, 427, 497);
		getContentPane().add(pnNhanVien);
		pnNhanVien.setLayout(null);

		JLabel lblTen = new JLabel("Mã nhân viên:");
		lblTen.setBounds(21, 35, 91, 21);
		pnNhanVien.add(lblTen);
		lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		txtMaNV = new JTextField();
		txtMaNV.setEditable(false);
		txtMaNV.setBounds(173, 35, 222, 23);
		pnNhanVien.add(txtMaNV);
		txtMaNV.setColumns(10);

		JLabel lblTen_1 = new JLabel("Họ Tên:");
		lblTen_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblTen_1.setBounds(21, 87, 72, 21);
		pnNhanVien.add(lblTen_1);

		txtHoTen = new JTextField();
		txtHoTen.setEditable(false);
		txtHoTen.setColumns(10);
		txtHoTen.setBounds(173, 89, 222, 23);
		pnNhanVien.add(txtHoTen);

		JLabel lblGiiTnh = new JLabel("Giới tính:");
		lblGiiTnh.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblGiiTnh.setBounds(21, 136, 72, 21);
		pnNhanVien.add(lblGiiTnh);

		comboBoxGT = new JComboBox();
		comboBoxGT.setEnabled(false);
		comboBoxGT.setBounds(173, 138, 91, 22);
		comboBoxGT.addItem("Nam");
		comboBoxGT.addItem("Nữ");
		pnNhanVien.add(comboBoxGT);

		JLabel lblNgySinh = new JLabel("Ngày sinh:");
		lblNgySinh.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNgySinh.setBounds(21, 190, 72, 21);
		pnNhanVien.add(lblNgySinh);

		ngaySinh = new JDateChooser();
		ngaySinh.setBounds(173, 245, 222, 23);
		ngaySinh.setEnabled(false);
		pnNhanVien.add(ngaySinh);

		JLabel lblNgyVoLm = new JLabel("Ngày vào làm:");
		lblNgyVoLm.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNgyVoLm.setBounds(21, 247, 106, 21);
		pnNhanVien.add(lblNgyVoLm);

		ngayVaoLam = new JDateChooser();
		ngayVaoLam.setBounds(173, 188, 222, 23);
		ngayVaoLam.setEnabled(false);
		pnNhanVien.add(ngayVaoLam);

		JLabel lblSinThoi = new JLabel("Số điện thoại:");
		lblSinThoi.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblSinThoi.setBounds(21, 303, 106, 21);
		pnNhanVien.add(lblSinThoi);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblEmail.setBounds(21, 357, 72, 21);
		pnNhanVien.add(lblEmail);

		JLabel lblGiiTnh_4_1 = new JLabel("Địa chỉ:");
		lblGiiTnh_4_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblGiiTnh_4_1.setBounds(21, 410, 72, 21);
		pnNhanVien.add(lblGiiTnh_4_1);

		txtSDT = new JTextField();
		txtSDT.setEditable(false);
		txtSDT.setColumns(10);
		txtSDT.setBounds(173, 301, 222, 23);
		pnNhanVien.add(txtSDT);

		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(173, 355, 222, 23);
		pnNhanVien.add(txtEmail);

		textAreaDC = new JTextArea();
		textAreaDC.setEditable(false);
		textAreaDC.setBounds(173, 411, 222, 74);
		pnNhanVien.add(textAreaDC);

		JPanel pnTaiKhoan = new JPanel();
		pnTaiKhoan.setBounds(466, 32, 408, 465);
		pnTaiKhoan.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));
		getContentPane().add(pnTaiKhoan);
		pnTaiKhoan.setLayout(null);

		JLabel lblTnngNhp = new JLabel("Tên đăng nhập:");
		lblTnngNhp.setBounds(21, 31, 118, 21);
		lblTnngNhp.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		pnTaiKhoan.add(lblTnngNhp);

		txtMaTK = new JTextField();
		txtMaTK.setEditable(false);
		txtMaTK.setBounds(191, 34, 177, 20);
		txtMaTK.setColumns(10);
		pnTaiKhoan.add(txtMaTK);

		JLabel lblMtKhu = new JLabel("Mật khẩu: ");
		lblMtKhu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblMtKhu.setBounds(21, 80, 118, 21);
		pnTaiKhoan.add(lblMtKhu);

		passwordField = new JPasswordField();
		passwordField.setEditable(false);
		passwordField.setBounds(191, 83, 177, 20);
		pnTaiKhoan.add(passwordField);

		JPanel panelDoiMatKhau = new JPanel();
		panelDoiMatKhau.setBounds(21, 171, 366, 268);
		pnTaiKhoan.add(panelDoiMatKhau);
		panelDoiMatKhau.setLayout(null);
		panelDoiMatKhau.setBorder(BorderFactory.createTitledBorder("Đổi mật khẩu"));

		JLabel lblNhpMtKhu = new JLabel("Nhập mật khẩu mới:");
		lblNhpMtKhu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNhpMtKhu.setBounds(10, 50, 155, 21);
		panelDoiMatKhau.add(lblNhpMtKhu);
		

		JLabel lblXcNhnMt = new JLabel("Xác nhận mật khẩu mới:");
		lblXcNhnMt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblXcNhnMt.setBounds(10, 131, 199, 21);
		panelDoiMatKhau.add(lblXcNhnMt);

		JButton btnConfirm = new JButton("Xác nhận");
		btnConfirm.setBackground(new Color(255, 255, 255));
		btnConfirm.setBounds(255, 234, 89, 23);
		btnConfirm.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            changePassword();
	        }
	    });
		panelDoiMatKhau.add(btnConfirm);

		pwdNew = new JTextField();
		pwdNew.setColumns(10);
		pwdNew.setBounds(10, 92, 334, 20);
		pwdNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pwdNewConfirm.requestFocus();
			}
		});
		panelDoiMatKhau.add(pwdNew);

		pwdNewConfirm = new JTextField();
		pwdNewConfirm.setColumns(10);
		pwdNewConfirm.setBounds(10, 173, 334, 20);
		pwdNewConfirm.addActionListener(e -> changePassword());
		panelDoiMatKhau.add(pwdNewConfirm);

		btnExit.addActionListener(this);
		
	}
	void loadDataNhanVien() {
		EmployeeDAO eDao = new EmployeeDAO();
		String maTK = ((String) AccountCurrent.getCurrentAccount().getMaTK());
		txtMaTK.setText((String) AccountCurrent.getCurrentAccount().getMaTK());
		passwordField.setText((String) AccountCurrent.getCurrentAccount().getMatKhau());
		
		try {
            Employee employee = null;
			try {
				employee = EmployeeDAO.getEmployeeByUsername(maTK);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (employee != null) {
                txtMaNV.setText(employee.getMaNV());
                txtHoTen.setText(employee.getTenNV());
                comboBoxGT.setSelectedItem((employee.isGioiTinh() ? "Nam" : "Nữ"));
                
                ngaySinh.setDate(employee.getNgaySinh());
                ngayVaoLam.setDate(employee.getNgayVaoLam());
                txtSDT.setText(employee.getSoDienThoai());
                txtEmail.setText(employee.getEmail());
                textAreaDC.setText(employee.getDiaChi());
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
		
	}
	private void changePassword() {
	    String newPassword = pwdNew.getText().trim();
	    String confirmPassword = pwdNewConfirm.getText().trim();

	    // Kiểm tra tính hợp lệ của mật khẩu mới
	    if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (!newPassword.equals(confirmPassword)) {
	        JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Thực hiện cập nhật mật khẩu trong cơ sở dữ liệu
	    String maTK = txtMaTK.getText().trim();
	    try {
	        AccountDAO.updatePassword(maTK, newPassword);
	        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        
	        // Reset các trường nhập liệu
	        pwdNew.setText("");
	        pwdNewConfirm.setText("");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật mật khẩu", "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnExit)) {
			this.dispose();
		}
	}
}
