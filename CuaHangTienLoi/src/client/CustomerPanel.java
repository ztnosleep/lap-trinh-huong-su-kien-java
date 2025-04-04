package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.CustomerDAO;
import dao.ProductDAO;
import entity.Customer;
import entity.Product;

public class CustomerPanel extends JPanel implements MouseListener{
	private JTextField textField;
	private JComboBox cboGioi;
	private JTextField txtMaKhachHang;
	private JTextField txtTenKhachHang;
	private JTextField txtSdt;
	private JTextField txtDiaChi;
	private JDateChooser dateOfBirth;
	private JTextField txtDiemTL;
	private DefaultTableModel tableModel;
	private static JTextField txtSearch;
	private JButton btnTim;
	private static JTable customerTable;
	private static DefaultTableModel customerTableModel;
	private static ArrayList<Customer> dssp;
	public CustomerPanel() {
		setLayout(null);
		initialize();
		loadDataKhachHang();
	}
	private void initialize() {
		JPanel pnProduct = new JPanel();
		pnProduct.setBackground(new Color(240,255,255));
		pnProduct.setBorder(new LineBorder(Color.BLACK));
		pnProduct.setBounds(0, 0, 1644, 1070);
		add(pnProduct);
		pnProduct.setLayout(null);


		btnTim = new JButton("");
		btnTim.setBackground(Color.WHITE);
		btnTim.setIcon(new ImageIcon("./img/find.png"));
		btnTim.setBounds(585, 32, 66, 37);
		pnProduct.add(btnTim);

		txtSearch = new JTextField("Nhập tên khách hàng...");
		txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Nhập tên khách hàng...")) {
                    txtSearch.setText(""); // Xóa nội dung khi nhấp vào
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Nhập tên khách hàng..."); // Đặt lại nội dung khi rời khỏi ô
                }
            }
        });
		txtSearch.setColumns(10);
		txtSearch.setBounds(221, 32, 367, 37);
		pnProduct.add(txtSearch);

		JLabel lblSearchProduct = new JLabel("Tìm khách hàng:");
		lblSearchProduct.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblSearchProduct.setBounds(34, 32, 177, 37);
		pnProduct.add(lblSearchProduct);

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(255, 255, 255));
		panelInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInfo.setBounds(49, 655, 1018, 375);
		panelInfo.setLayout(null);
		pnProduct.add(panelInfo);

		JLabel lblLoaiSP = new JLabel("Giới tính:");
		lblLoaiSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblLoaiSP.setBounds(54, 185, 146, 27);
		panelInfo.add(lblLoaiSP);

		cboGioi = new JComboBox<>();
		cboGioi.setBounds(235, 188, 102, 21);
		panelInfo.add(cboGioi);
		cboGioi.addItem("Nam");
		cboGioi.addItem("Nữ");
		
		//	loadDataProductType();


		// Mã sản phẩm
		JLabel lblMaSP = new JLabel("Mã khách hàng:");
		lblMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblMaSP.setBounds(54, 74, 157, 27);
		panelInfo.add(lblMaSP);
		txtMaKhachHang = new JTextField();
		txtMaKhachHang.setEditable(false);
		txtMaKhachHang.setBounds(235, 81, 345, 23);
		panelInfo.add(txtMaKhachHang);

		// Tên sản phẩm
		JLabel lblTenSP = new JLabel("Tên khách hàng:");
		lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblTenSP.setBounds(54, 112, 146, 27);
		panelInfo.add(lblTenSP);
		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setBounds(235, 115, 345, 23);
		panelInfo.add(txtTenKhachHang);

		// Giá
		JLabel lblGia = new JLabel("Số điện thoại:");
		lblGia.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblGia.setBounds(54, 150, 133, 27);
		panelInfo.add(lblGia);
		txtSdt = new JTextField();
		txtSdt.setBounds(235, 149, 345, 23);
		panelInfo.add(txtSdt);

		// Mô tả
		JLabel lblMoTa = new JLabel("Địa chỉ:");
		lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblMoTa.setBounds(54, 225, 133, 27);
		panelInfo.add(lblMoTa);
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(235, 229, 345, 84);
		panelInfo.add(txtDiaChi);


		JLabel lblNgaySX = new JLabel("Ngày sinh:");
		lblNgaySX.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNgaySX.setBounds(623, 74, 133, 27);
		panelInfo.add(lblNgaySX);

		dateOfBirth = new JDateChooser();
		dateOfBirth.getCalendarButton().setBackground(new Color(255, 255, 255));
		dateOfBirth.setBounds(760, 78, 248, 23);
		dateOfBirth.setEnabled(true);
		dateOfBirth.getDateEditor().setEnabled(false);
		panelInfo.add(dateOfBirth);


		JLabel lblXuatXu = new JLabel("Điểm tích lũy:");
		lblXuatXu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblXuatXu.setBounds(623, 115, 133, 27);
		panelInfo.add(lblXuatXu);

		txtDiemTL = new JTextField();
		txtDiemTL.setEditable(false);
		txtDiemTL.setBounds(760, 119, 248, 23);
		panelInfo.add(txtDiemTL);
		
		
		customerTableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"M\u00E3 kh\u00E1ch h\u00E0ng", "T\u00EAn kh\u00E1ch h\u00E0ng", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "Ng\u00E0y sinh", "Gi\u1EDBi t\u00EDnh", "\u0110\u1ECBa ch\u1EC9", "\u0110i\u1EC3m t\u00EDch l\u0169y"
				}
				);
		customerTable = new JTable(customerTableModel);
		customerTable.setFillsViewportHeight(true);
		customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(customerTable);
		scrollPane.setBounds(49, 128, 1542, 495);
		pnProduct.add(scrollPane);;
		scrollPane.setViewportView(customerTable);
		// Add some sample data
		//loadDataProduct();

		JButton btnCapNhatKH = new JButton("Chỉnh sửa thông tin khách hàng");
		btnCapNhatKH.setBackground(new Color(255, 255, 255));
		btnCapNhatKH.setBounds(1169, 816, 220, 44);
		btnCapNhatKH.addActionListener(e -> updateKH());
		pnProduct.add(btnCapNhatKH);



		JButton btnThemKH = new JButton("Đăng ký thành viên");
		btnThemKH.setBackground(new Color(255, 255, 255));
		btnThemKH.setBounds(1169, 736, 220, 44);
		btnThemKH.addActionListener(e -> addNewKH());
		pnProduct.add(btnThemKH);

		JButton btnXoa = new JButton("Làm mới");
		btnXoa.setBackground(Color.WHITE);
		btnXoa.setBounds(1169, 896, 220, 44);
		btnXoa.addActionListener(e -> xoa());
		pnProduct.add(btnXoa);
		
		JButton btnDoiDiem = new JButton("Đổi điểm tích lũy");
		btnDoiDiem.setBackground(Color.WHITE);
		btnDoiDiem.setBounds(1169, 977, 220, 44);
		pnProduct.add(btnDoiDiem);
		btnDoiDiem.addActionListener(e -> doiDiem());
		
		
		btnTim.addActionListener(e -> timKiem());
		txtSearch.addActionListener(e -> timKiem());
		customerTable.addMouseListener(this);
	}
	static void timKiem()  {
		String tuKhoa = txtSearch.getText();
		if(!tuKhoa.isEmpty()) {
			CustomerDAO cDao = new CustomerDAO();
			ArrayList<Customer> customerList = cDao.findCustomerByName(tuKhoa);
			updateCustomerTable(customerList);
		}
		else {
			JOptionPane.showMessageDialog(customerTable, "Vui lòng nhập tên khách hàng");
			loadDataKhachHang();
		}
	}
	static void updateCustomerTable(ArrayList<Customer> customerList) {
	    DefaultTableModel model = (DefaultTableModel) customerTable.getModel(); // Giả sử bạn có customerTable
	    model.setRowCount(0); // Xóa dữ liệu cũ

	    for (Customer customer : customerList) {
	        model.addRow(new Object[]{customer.getMaKh(), customer.getTenKh(), customer.getSoDienThoai(),customer.getNgaySinh(),customer.getGioiTinhHienThi(),customer.getDiaChi(),customer.getDiemTichLuy()}); // Thêm hàng mới vào bảng
	    }
	}
	static void loadDataKhachHang() {
		CustomerDAO proDao = new CustomerDAO();
		dssp = proDao.getAllKhachHang();
		customerTableModel.setRowCount(0);

		DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
		Object[] row = new Object[11];
		for(int i = 0; i< dssp.size();i++) {
			row[0] = dssp.get(i).getMaKh();
			row[1] = dssp.get(i).getTenKh();
			row[2] = dssp.get(i).getSoDienThoai();
			row[3] = dssp.get(i).getNgaySinh();
			row[4] = dssp.get(i).getGioiTinhHienThi();
			row[5] = dssp.get(i).getDiaChi();
			row[6] = dssp.get(i).getDiemTichLuy();
			
			model.addRow(row);
		}

		customerTable.setRowHeight(50);
		HomePanel.loadDataProductMain();
	}
	private void updateKH() {
		int sel = customerTable.getSelectedRow();
		if(sel == -1 ) {
			JOptionPane.showMessageDialog(this,"Chọn khách hàng cần chỉnh sửa");
		}
		else {
			CustomerDAO customerDAO = new CustomerDAO();
			String maKhachHang = txtMaKhachHang.getText().trim();
		    String tenKhachHang = txtTenKhachHang.getText().trim();
		    String soDienThoai = txtSdt.getText().trim();
		    String diaChi = txtDiaChi.getText().trim();
		    java.util.Date ngaySinhDate = dateOfBirth.getDate();
		    String gioiTinh = (String) cboGioi.getSelectedItem();
		    int diemTichLuy = Integer.parseInt( txtDiemTL.getText().trim());
		    boolean gioiTinhBoolean = gioiTinh.equals("Nam");
		    
		    if (maKhachHang.isEmpty() || tenKhachHang.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty() || ngaySinhDate == null || gioiTinh == null) {
		        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    if(!tenKhachHang.matches("^[A-ZÀ-ỹ][a-zà-ỹ]*(?: [A-ZÀ-ỹ][a-zà-ỹ]*)*$")) {
		    	JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng tên khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    if (!soDienThoai.matches("0[0-9]{9}")) {
		        JOptionPane.showMessageDialog(this, "Số điện thoại phải đủ 10 số, bắt đầu từ 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
		    if (customerDAO.isPhoneNumberExistsUP(soDienThoai, maKhachHang)) {
		        JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại. Vui lòng nhập số khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        return; // Dừng quá trình nếu số điện thoại đã tồn tại
		    }

		    // Kiểm tra tuổi (phải trên 8 tuổi)
		    java.util.Calendar cal = java.util.Calendar.getInstance();
		    cal.setTime(ngaySinhDate);
		    int yearOfBirth = cal.get(java.util.Calendar.YEAR);
		    int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		    if (currentYear - yearOfBirth <= 8) {
		        JOptionPane.showMessageDialog(this, "Khách hàng phải trên 8 tuổi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    // Chuyển đổi java.util.Date sang java.sql.Date
		    java.sql.Date ngaySinh = new java.sql.Date(ngaySinhDate.getTime());

		    // Tạo đối tượng Customer
		   
		    // Gọi dao để thêm khách hàng
		    try {
		    	
		    	Customer cUpdate = new Customer(maKhachHang, soDienThoai, tenKhachHang, diaChi, ngaySinh, gioiTinhBoolean, diemTichLuy);
		    	cUpdate.setTenKh(tenKhachHang);
		    	cUpdate.setSoDienThoai(soDienThoai);
		    	cUpdate.setGioiTinh(gioiTinhBoolean);
		    	cUpdate.setDiaChi(diaChi);
		    	cUpdate.setDiemTichLuy(diemTichLuy);
		    	cUpdate.setNgaySinh(ngaySinh);
		    	if(customerDAO.updateCustomer(cUpdate)) {
		    		JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					xoa();
					loadDataKhachHang();
		    	}
		    
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	    
	    
	}
	private void doiDiem() {
		if(customerTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng!");
		}else {
			Customer kh = dssp.get(customerTable.getSelectedRow());
			
			GiftPanel newFr = new GiftPanel(kh);
			newFr.setVisible(true);
		}

	}
	private void addNewKH() {
	    // Lấy thông tin từ các trường nhập liệu
	  //  String maKhachHang = txtMaKhachHang.getText().trim();
	    String tenKhachHang = txtTenKhachHang.getText().trim();
	    String soDienThoai = txtSdt.getText().trim();
	    String diaChi = txtDiaChi.getText().trim();
	    java.util.Date ngaySinhDate = dateOfBirth.getDate();
	    String gioiTinh = (String) cboGioi.getSelectedItem();
	    txtDiemTL.setEnabled(false);
	    boolean gioiTinhBoolean = gioiTinh.equals("Nam");

	   
	    // Tạo đối tượng Customer
	    CustomerDAO customerDAO = new CustomerDAO();
	    String maKhachHang = customerDAO.getNextCustomerId();
	    // Điểm tích lũy mặc định là 0
	    int diemTichLuy = 0;

	    // Kiểm tra thông tin hợp lệ
	    if (maKhachHang.isEmpty() || tenKhachHang.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty() || ngaySinhDate == null || gioiTinh == null) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    if(!tenKhachHang.matches("^[A-ZÀ-ỹ][a-zà-ỹ]*(?: [A-ZÀ-ỹ][a-zà-ỹ]*)*$")) {
	    	JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng tên khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }


	    if (!soDienThoai.matches("0[0-9]{9}")) {
	        JOptionPane.showMessageDialog(this, "Số điện thoại phải đủ 10 số, bắt đầu từ 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    if (customerDAO.isPhoneNumberExists(soDienThoai)) {
	        JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại. Vui lòng nhập số khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return; // Dừng quá trình nếu số điện thoại đã tồn tại
	    }
	    // Kiểm tra tuổi (phải trên 8 tuổi)
	    java.util.Calendar cal = java.util.Calendar.getInstance();
	    cal.setTime(ngaySinhDate);
	    int yearOfBirth = cal.get(java.util.Calendar.YEAR);
	    int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	    if (currentYear - yearOfBirth <= 8) {
	        JOptionPane.showMessageDialog(this, "Khách hàng phải trên 8 tuổi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Chuyển đổi java.util.Date sang java.sql.Date
	    java.sql.Date ngaySinh = new java.sql.Date(ngaySinhDate.getTime());

	    Customer newCustomer = new Customer(maKhachHang, soDienThoai, tenKhachHang, diaChi, ngaySinh, gioiTinhBoolean, diemTichLuy);

	    
	   ;
	    boolean result = customerDAO.addCustomer(newCustomer);

	    if (result) {
	        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        loadDataKhachHang(); // Tải lại dữ liệu để cập nhật bảng
	    } else {
	        JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void xoa() {
		txtMaKhachHang.setText("");
		txtTenKhachHang.setText("");
		txtSdt.setText("");
		dateOfBirth.setDate(null);
		cboGioi.setSelectedIndex(0);;
		txtDiaChi.setText("");
		txtDiemTL.setText("");
	}
	
	private void chonDong(int row) {
		txtMaKhachHang.setText(customerTableModel.getValueAt(row,0).toString());
		txtTenKhachHang.setText(customerTableModel.getValueAt(row,1).toString());
		txtSdt.setText(customerTableModel.getValueAt(row,2).toString());
		dateOfBirth.setDate((Date) customerTableModel.getValueAt(row,3));
		cboGioi.setSelectedItem(customerTableModel.getValueAt(row,4));
		txtDiaChi.setText(customerTableModel.getValueAt(row, 5).toString());
		txtDiemTL.setText(customerTableModel.getValueAt(row,6).toString());
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int sel = customerTable.getSelectedRow();
		chonDong(sel);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
