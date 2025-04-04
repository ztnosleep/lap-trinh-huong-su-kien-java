package client;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dao.BillDAO;
import dao.BillDetailsDAO;
import dao.BillExporter;
import dao.CustomerDAO;
import dao.ProductDAO;
import dao.ProductTypeDAO;
import entity.Customer;
import entity.Employee;
import entity.Product;
import entity.ProductType;

public class HomePanel extends JPanel implements ActionListener, MouseListener{
	private JTextField txtSearch;
	private JTable tbCart;
	private JTextField txtSoLuong;
	private JTextField txtMaKH;
	private JTextField txtTenKH;
	private JTextField txtSoDienThoai;
	private JTextField txtDiemTichLuy;
	private JTextField txtTongTien;
	private JTextField txtTienTra;
	private JTextField txtTienThua;
	private TableModel cartTableModel;
	private JPanel pnProduct;
	private JButton btnAddToCart;
	//private JTable productTable;
	private JRadioButton rdbtnTienMat;
	private JRadioButton rdbtnCK;
	private JButton btnThem;
	private JButton btnThanhToan;
	private JButton btnBot;
	private JButton btnXoaSP;
	private JButton btnXoaCart;
	private JButton btnTnhTin;
	private JButton btn1K;
	private JButton btn2K;
	private JButton btn5K;
	private JButton btn10K;
	private JButton btn20K;
	private JButton btn50K;
	private JButton btn100K;
	private JButton btn200K;
	private JButton btn500K;
	private ButtonGroup buttonGroup;
	private static JTable tbProduct;
	private static DefaultTableModel tbProductModel;
	private static JComboBox<String> comboSearch;
	//	private static DefaultTableModel productTableM;
	//	private static DefaultTableModel productTableModel;
	private static ArrayList<String> dsMSP;

	public HomePanel() {
		setLayout(null);
		initComponents();
		loadDataProductMain();

		loadDataProductTypeMain();
		btnAddToCart.addActionListener(this);
		tbProduct.addMouseListener(this);
		pnProduct.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Nếu click ra ngoài bảng, đặt lại giá trị các trường
				if (!tbProduct.getBounds().contains(e.getPoint())) {
					txtSoLuong.setText(""); 
				}

			}
		});
	}

	private void initComponents() {
		pnProduct = new JPanel();
		pnProduct.setBackground(Color.WHITE);
		pnProduct.setBorder(new LineBorder(Color.BLACK));
		pnProduct.setBounds(0, 0, 1640, 1050);
		pnProduct.setBackground(new Color(240, 255, 255));
		pnProduct.setLayout(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setIcon(new ImageIcon("./img/find.png"));
		btnNewButton.setBounds(585, 32, 66, 37);
		pnProduct.add(btnNewButton);

		txtSearch = new JTextField("Nhập tên sản phẩm...");
		txtSearch.setColumns(10);
		txtSearch.setBounds(221, 32, 367, 37);
		pnProduct.add(txtSearch);
		txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Nhập tên sản phẩm...")) {
                    txtSearch.setText(""); // Xóa nội dung khi nhấp vào
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Nhập tên sản phẩm..."); // Đặt lại nội dung khi rời khỏi ô
                }
            }
        });
		txtSearch.addActionListener(e-> timKiem());

		tbProductModel= new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u1EA2nh", "T\u00EAn s\u1EA3n ph\u1EA9m", "Gi\u00E1"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
					ImageIcon.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		tbProduct = new JTable(tbProductModel);
		JScrollPane scrollPaneSP = new JScrollPane(tbProduct);
		scrollPaneSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneSP.setBounds(34, 166, 712, 737);
		pnProduct.add(scrollPaneSP);
		scrollPaneSP.setViewportView(tbProduct);

		btnAddToCart = new JButton("Thêm Vào Giỏ Hàng");
		btnAddToCart.setBackground(Color.WHITE);
		btnAddToCart.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnAddToCart.setBounds(272, 950, 225, 33);
		pnProduct.add(btnAddToCart);

		JLabel lblQuantity = new JLabel("Số Lượng");
		lblQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblQuantity.setBounds(34, 950, 105, 36);
		pnProduct.add(lblQuantity);

		txtSoLuong = new JTextField();
		txtSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		txtSoLuong.setBounds(136, 955, 44, 26);
		pnProduct.add(txtSoLuong);
		txtSoLuong.setColumns(10);

		// Thêm sự kiện nhấn phím Enter vào txtQuantity
		txtSoLuong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToCart();
			}});

		JLabel lblSearchProduct = new JLabel("Tìm sản phẩm");
		lblSearchProduct.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblSearchProduct.setBounds(34, 32, 164, 37);
		pnProduct.add(lblSearchProduct);

		comboSearch = new JComboBox<>();
		//comboSearch.addItem(" ");
		comboSearch.setBackground(Color.WHITE);
		comboSearch.setBounds(221, 97, 178, 37);
		pnProduct.add(comboSearch);
		
		comboSearch.addActionListener(e -> searchProducts());

		JLabel lblProductType = new JLabel("Loại sản phẩm");
		lblProductType.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblProductType.setBounds(34, 91, 177, 37);
		pnProduct.add(lblProductType);

		add(pnProduct);

		JPanel pnCart = new JPanel();
		pnCart.setBounds(763, 32, 840, 945);
		pnProduct.add(pnCart);
		pnCart.setBackground(Color.WHITE);
		pnCart.setBorder(new LineBorder(Color.BLACK));
		pnCart.setLayout(null);

		String[] cartColumnNames = {"Mã sản phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"};
		cartTableModel = new DefaultTableModel(cartColumnNames, 0);
		tbCart = new JTable(cartTableModel);
		tbCart.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		JScrollPane scrollPane = new JScrollPane(tbCart);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(24, 49, 778, 407);
		pnCart.add(scrollPane);

		JLabel lblCart = new JLabel("Sản phẩm đã thêm");
		lblCart.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblCart.setBounds(24, 0, 374, 38);
		pnCart.add(lblCart);

		//addCartButtons(pnCart);

		JPanel pnInfo = new JPanel();
		pnInfo.setBorder(new LineBorder(Color.BLACK));
		pnInfo.setBounds(24, 517, 778, 407);
		pnInfo.setLayout(null);
		pnCart.add(pnInfo);

		addCustomerInfoLabels(pnInfo);
		btnThanhToan = new JButton("Thanh toán");
		btnThanhToan.setBackground(Color.WHITE);
		btnThanhToan.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnThanhToan.setBounds(1120, 980, 140, 40);
		pnProduct.add(btnThanhToan);

		btnThem = new JButton("+");
		btnThem.setBackground(Color.WHITE);
		btnThem.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		btnThem.setBounds(24, 467, 52, 39);
		pnCart.add(btnThem);

		btnBot = new JButton("-");
		btnBot.setBackground(Color.WHITE);
		btnBot.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		btnBot.setBounds(84, 467, 52, 39);
		pnCart.add(btnBot);

		btnXoaSP = new JButton("Xóa khỏi giỏ hàng");
		btnXoaSP.setBackground(Color.WHITE);
		btnXoaSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnXoaSP.setBounds(144, 467, 195, 40);
		pnCart.add(btnXoaSP);

		btnXoaCart = new JButton("Xóa toàn bộ giỏ hàng");
		btnXoaCart.setBackground(Color.WHITE);
		btnXoaCart.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnXoaCart.setBounds(346, 467, 239, 40);
		pnCart.add(btnXoaCart);

		btnTnhTin = new JButton("Tính tiền");
		btnTnhTin.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnTnhTin.setBackground(Color.WHITE);
		btnTnhTin.setBounds(644, 467, 156, 40);
		pnCart.add(btnTnhTin);

		txtMaKH = new JTextField();
		txtMaKH.setColumns(8);
		txtMaKH.setBounds(286, 5, 347, 39);
		pnInfo.add(txtMaKH);

		txtTenKH = new JTextField();
		txtTenKH.setColumns(8);
		txtTenKH.setEditable(false);
		txtTenKH.setBounds(286, 52, 347, 39);
		pnInfo.add(txtTenKH);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setColumns(8);
		//txtSoDienThoai.setEnabled(false);
		txtSoDienThoai.setBounds(286, 102, 347, 39);
		pnInfo.add(txtSoDienThoai);

		txtDiemTichLuy = new JTextField();
		txtDiemTichLuy.setEditable(false);
		txtDiemTichLuy.setColumns(8);
		txtDiemTichLuy.setBounds(286, 152, 347, 39);
		pnInfo.add(txtDiemTichLuy);

		txtTongTien = new JTextField();
		txtTongTien.setEditable(false);
		txtTongTien.setColumns(8);
		txtTongTien.setBounds(286, 202, 347, 39);
		pnInfo.add(txtTongTien);

		txtTienTra = new JTextField();
		txtTienTra.setColumns(8);
		txtTienTra.setBounds(286, 252, 347, 39);
		txtTienTra.setEnabled(false);
		pnInfo.add(txtTienTra);

		txtTienThua = new JTextField();
		txtTienThua.setEnabled(false);
		txtTienThua.setColumns(8);
		txtTienThua.setBounds(286, 302, 347, 39);
		pnInfo.add(txtTienThua);

		rdbtnTienMat = new JRadioButton("Tiền mặt");
		rdbtnTienMat.setBounds(286,353,100,50);
		pnInfo.add(rdbtnTienMat);

		rdbtnCK = new JRadioButton("Chuyển khoản");
		rdbtnCK.setBounds(406,353,150,50);
		pnInfo.add(rdbtnCK);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnCK);
		buttonGroup.add(rdbtnTienMat);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("./img/find.png"));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(567, 5, 66, 39);
		pnInfo.add(btnNewButton_1);
		btnNewButton.addActionListener(e -> timKiem());

		btn1K = new JButton("1K");
		btn1K.setBackground(new Color(255, 255, 255));
		btn1K.setBounds(643, 8, 88, 24);
		btn1K.addActionListener(e -> addAmount(1000));
		pnInfo.add(btn1K);

		btn2K = new JButton("2K");
		btn2K.setBackground(new Color(255, 255, 255));
		btn2K.setBounds(643, 34, 88, 24);
		btn2K.addActionListener(e -> addAmount(2000));
		pnInfo.add(btn2K);

		btn5K = new JButton("5K");
		btn5K.setBackground(new Color(255, 255, 255));
		btn5K.setBounds(643, 60, 88, 24);
		btn5K.addActionListener(e -> addAmount(5000));
		pnInfo.add(btn5K);

		btn10K = new JButton("10K");
		btn10K.setBackground(new Color(255, 255, 255));
		btn10K.setBounds(643, 86,88, 24);
		btn10K.addActionListener(e -> addAmount(10000));
		pnInfo.add(btn10K);

		btn20K = new JButton("20K");
		btn20K.setBackground(new Color(255, 255, 255));
		btn20K.setBounds(643, 112,88, 24);
		btn20K.addActionListener(e -> addAmount(20000));
		pnInfo.add(btn20K);

		btn50K = new JButton("50K");
		btn50K.setBackground(new Color(255, 255, 255));
		btn50K.setBounds(643, 138, 88, 24);
		btn50K.addActionListener(e -> addAmount(50000));
		pnInfo.add(btn50K);

		btn100K = new JButton("100K");
		btn100K.setBackground(new Color(255, 255, 255));
		btn100K.setBounds(643, 164,88, 24);
		btn100K.addActionListener(e -> addAmount(100000));
		pnInfo.add(btn100K);

		btn200K = new JButton("200K");
		btn200K.setBackground(new Color(255, 255, 255));
		btn200K.setBounds(643, 190, 88, 24);
		btn200K.addActionListener(e -> addAmount(200000));
		pnInfo.add(btn200K);

		btn500K = new JButton("500K");
		btn500K.setBackground(new Color(255, 255, 255));
		btn500K.setBounds(643, 216, 88, 24);
		btn500K.addActionListener(e -> addAmount(500000));
		pnInfo.add(btn500K);
		setButtonEnabled(false);

		btnThem.addActionListener(this);
		btnBot.addActionListener(this);
		btnXoaCart.addActionListener(this);
		btnXoaSP.addActionListener(this);
		btnTnhTin.addActionListener(this);

		rdbtnTienMat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setButtonEnabled(true);
				txtTienTra.setText("");
				txtTienTra.setEnabled(true);
				// Khi chọn Tiền mặt, cho phép nhập tiền khách trả
				txtTienTra.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if(!txtTongTien.getText().isEmpty()) {
							double tongTien = Double.parseDouble(txtTongTien.getText());
							String currentText = txtTienTra.getText().trim();
							double tienKhachTra = currentText.isEmpty() ? 0 : Double.parseDouble(currentText);
							double tienThua = tienKhachTra - tongTien;
							txtTienThua.setText(String.valueOf(tienThua));
						}
						

					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						if(!txtTongTien.getText().isEmpty()) {
							double tongTien = Double.parseDouble(txtTongTien.getText());
							String currentText = txtTienTra.getText().trim();
							double tienKhachTra = currentText.isEmpty() ? 0 : Double.parseDouble(currentText);
							double tienThua = tienKhachTra - tongTien;
							txtTienThua.setText(String.valueOf(tienThua));
						}
						
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						if(!txtTongTien.getText().isEmpty()) {
							double tongTien = Double.parseDouble(txtTongTien.getText());
							String currentText = txtTienTra.getText().trim();
							double tienKhachTra = currentText.isEmpty() ? 0 : Double.parseDouble(currentText);
							double tienThua = tienKhachTra - tongTien;
							txtTienThua.setText(String.valueOf(tienThua));
						}
						
					}
				});
			}});

		rdbtnCK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Khi chọn Chuyển khoản, tự động đặt tiền khách trả bằng tổng tiền
				txtTienTra.setEnabled(false);
				setButtonEnabled(false);
				// Vô hiệu hóa nhập tiền
				double tongTien = 0;
				if(!txtTongTien.getText().isEmpty()) {
					tongTien = Double.parseDouble(txtTongTien.getText());
				}
				txtTienTra.setText(String.valueOf(tongTien)); // Đặt giá trị tiền khách trả
				txtTienThua.setText("");
			}
		});
		txtMaKH.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timKhachHang(); // Gọi hàm tìm kiếm khi nhấn Enter
			}
		});
		txtSoDienThoai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timKhachHangSDT();
			}
		});
		btnThanhToan.addActionListener(this);

	}
	private void timKiem() {
		String tuKhoa = txtSearch.getText();
		if(!tuKhoa.isEmpty()) {
			ProductDAO pDao = new ProductDAO();
			ArrayList<Product> productList = pDao.findProductByName(tuKhoa);
			updateProductTableMain(productList);
		}
		else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm");
			loadDataProductMain();
		}
	}
	public void updateProductTableMain(ArrayList<Product> productList) {
		ProductDAO proDao = new ProductDAO();
		//ArrayList<Product> dssp = proDao.findProductByName(tuKhoa);
		tbProductModel.setRowCount(0);

		dsMSP = new ArrayList<String>();
		DefaultTableModel model = (DefaultTableModel) tbProduct.getModel();
		Object[] row = new Object[11];
		for(int i = 0; i< productList.size();i++) {
			String msp = productList.get(i).getMaSP();
			row[1] = productList.get(i).getTenSP();
			row[2] = productList.get(i).getDonGia()*1.3;
			
			if(productList.get(i).getImage()!=null){
				ImageIcon imageIcon = new ImageIcon(new ImageIcon(productList.get(i).getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				row[0]=imageIcon;
			}
			else {
				row[0] = null;
			}
			model.addRow(row);
			dsMSP.add(msp);
			
		}
		tbProduct.setRowHeight(120);
	}
	
	
	private void searchProducts() {
         // Lấy tùy chọn được chọn
        //String searchTerm = txtSearch.getText().trim();
        if(!((String) comboSearch.getSelectedItem()).isEmpty()) {
        	String selectedCategory = (String) comboSearch.getSelectedItem();
        	ProductDAO productDao = new ProductDAO();
            ArrayList<Product> productList = productDao.searchProducts(selectedCategory);
            updateProductTableMain(productList);
        }
        else {
        	loadDataProductMain();
        }
        
    }
	private void addAmount(int amount) {
		String currentText = txtTienTra.getText().trim();
		double currentAmount = 0;

		if (!currentText.isEmpty()) {
			currentAmount = Double.parseDouble(currentText);
		}

		currentAmount += amount;
		txtTienTra.setText(String.valueOf(currentAmount));
	}
	private void setButtonEnabled(boolean enabled) {
		btn1K.setEnabled(enabled);
		btn2K.setEnabled(enabled);
		btn5K.setEnabled(enabled);
		btn10K.setEnabled(enabled);
		btn20K.setEnabled(enabled);
		btn50K.setEnabled(enabled);
		btn100K.setEnabled(enabled);
		btn200K.setEnabled(enabled);
		btn500K.setEnabled(enabled);
	}

	static void loadDataProductMain() {
		ProductDAO proDao = new ProductDAO();
		ArrayList<Product> dssp = proDao.getAllProduct();
		tbProductModel.setRowCount(0);

		dsMSP = new ArrayList<String>();
		
		DefaultTableModel model = (DefaultTableModel) tbProduct.getModel();
		Object[] row = new Object[11];
		for(int i = 0; i< dssp.size();i++) {
			if(dssp.get(i).getSoLuongTon()>0) {
				String msp = dssp.get(i).getMaSP();
				row[1] = dssp.get(i).getTenSP();
				row[2] = dssp.get(i).getDonGia()*1.3;

				if(dssp.get(i).getImage()!=null){
					ImageIcon imageIcon = new ImageIcon(new ImageIcon(dssp.get(i).getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
					row[0]=imageIcon;
				}
				else {
					row[0] = null;
				}
				java.util.Date currentDate = new java.util.Date();
				if(dssp.get(i).getHanSD().after(currentDate)) {
					model.addRow(row);
					dsMSP.add(msp);
				}
			}

		 
			
		}
		tbProduct.setRowHeight(120);
	}
	static void loadDataKhachHangMain() {
		CustomerDAO cDao = new CustomerDAO();
		ArrayList<Customer> dskh = cDao.getAllKhachHang();

	}
	private void addCustomerInfoLabels(JPanel pnInfo) {
		String[] labels = {"Mã khách hàng", "Tên", "Số điện thoại", "Điểm tích lũy", "Tổng tiền", "Tiền khách trả", "Tiền thừa", "Phương thức thanh toán"};
		for (int i = 0; i < labels.length; i++) {
			JLabel label = new JLabel(labels[i]);
			label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			label.setBounds(87, 5 + i * 50, 160, 39);
			pnInfo.add(label);
		}
	}
	private static Map<String, String> productTypeMap = new HashMap<>();
	static void loadDataProductTypeMain() {
		ProductTypeDAO typeDao = new ProductTypeDAO();
		ArrayList<ProductType> dsML = typeDao.getProductType();

		comboSearch.removeAllItems();
		productTypeMap.clear(); // Đảm bảo Map rỗng

		for (ProductType p : dsML) {
			comboSearch.addItem(p.getTenLoai()); // Thêm tên loại vào ComboBox
			productTypeMap.put(p.getTenLoai(), p.getMaLoai()); // Lưu mã loại tương ứng
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		txtSoLuong.setText("1");
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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnAddToCart)) {
			addToCart();
		}
		if(o.equals(btnThem)) {
			themSoLuong();
		}
		if(o.equals(btnBot)) {
			giamSoLuong();
		}
		if(o.equals(btnXoaSP)) {
			xoaKhoiGio();
		}
		if(o.equals(btnXoaCart)) {
			xoaToanBo();
			txtTongTien.setText("");
		}
		if(o.equals(btnTnhTin)) {
			tinhTien();
			xoaThanhTien();
		}
		if(o.equals(btnThanhToan)) {
			thanhToan();
		}
	}
	private void thanhToan() {

		double tienTra = 0;
		double tienPhaiTra = 0;
		String PTTT = rdbtnCK.isSelected()?"Chuyển khoản":"Tiền mặt";
		HomePanel parentComponent = this;
		if(txtTienTra.getText().isEmpty()) {
			JOptionPane.showMessageDialog(parentComponent,"Nhập số tiền khách phải trả");
		}
		else if(txtTongTien.getText().isEmpty()) {
			JOptionPane.showMessageDialog(parentComponent,"Thêm sản phẩm vào giỏ và tính tiền trước!!!");
		}
		else {
			tienTra = Double.parseDouble(txtTienTra.getText().trim());
			tienPhaiTra = Double.parseDouble(txtTongTien.getText().trim());
			if(tienTra<tienPhaiTra) {
				JOptionPane.showMessageDialog(parentComponent, "Khách chưa trả đủ tiền", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				int sel = JOptionPane.showConfirmDialog(parentComponent, "Xác nhận thanh toán","Xác nhận",JOptionPane.YES_NO_OPTION);
				
				if(sel == JOptionPane.YES_OPTION) {
					for (int i = 0; i < cartTableModel.getRowCount(); i++) {
						String maSP = (String) cartTableModel.getValueAt(i, 0);
						int soLuong = (int) cartTableModel.getValueAt(i, 2);
						if(!ProductDAO.updateProductQuantity(maSP,soLuong)) {
							System.out.println("Không thể cập nhật số lượng");
						}
						else {
							ProductPanel.loadDataProduct();
						}
					}
					JOptionPane.showMessageDialog(parentComponent, "Thanh toán thành công!\nSố tiền: " + tienPhaiTra + "VND" +"\nPhương thức: " + PTTT, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					
					
					int choice = JOptionPane.showOptionDialog(parentComponent, "Bạn muốn có muốn xuất hóa đơn?", "Chọn tùy chọn",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new Object[]{"Lưu hóa đơn", "Lưu và xuất hóa đơn"}, null);
					while (choice == -1) {
						choice = JOptionPane.showOptionDialog(parentComponent, 
								"Bạn muốn xuất hóa đơn?", 
								"Chọn tùy chọn",
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null,
								new Object[]{"Lưu hóa đơn", "Lưu và xuất hóa đơn"}, 
								null);

						// Nếu người dùng nhấn Cancel hoặc đóng hộp thoại, reset choice
						if (choice == JOptionPane.CLOSED_OPTION) {
							choice = -1; // Đặt lại để tiếp tục vòng lặp
						}
					}

					switch (choice) {
					case 0:{
						// Lưu hóa đơn
						try {
							String maHD = "HD" + System.currentTimeMillis();
							String maNV = AccountPanel.txtMaNV.getText(); 
							String maKH = null;

							if(txtMaKH.getText().isEmpty()) {
								maKH= null;
							}
							else {
								maKH =txtMaKH.getText().trim(); 
								
								CustomerPanel.loadDataKhachHang();
							}
							BillDAO.saveBill(maHD, maNV, maKH, tienPhaiTra);
							for(int i= 0; i<tbCart.getRowCount();i++) {

								String maSP = (String) tbCart.getValueAt(i, 0); // Mã sản phẩm
								String tenSP = (String) tbCart.getValueAt(i, 1); // Tên sản phẩm
								int soLuong = (Integer) tbCart.getValueAt(i, 2); // Số lượng
								double donGia = (Double) tbCart.getValueAt(i, 3); // Đơn giá
								CustomerDAO.themDiemTichLuy(maKH,500);
								
								// Lưu chi tiết hóa đơn
								BillDetailsDAO.saveBillDetails(maHD, maSP, tenSP, soLuong, donGia);

							}
							JOptionPane.showMessageDialog(parentComponent, "Hóa đơn đã được lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
							CustomerPanel.loadDataKhachHang();
							BillPanel.loadDataBill();
						} catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(parentComponent, "Lỗi khi lưu hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
						}
						break;
					}

					case 1: {
						// Xuất hóa đơn
						String maHD = "HD" + System.currentTimeMillis();
						String maNV = AccountPanel.txtMaNV.getText(); 
						String tenKH = null;
						String maKH = null;
						String[][] chiTietSP = new String[tbCart.getRowCount()][4];
						try {							
							if(txtMaKH.getText().isEmpty()) {
								maKH = null;
							}
							else {
								maKH =txtMaKH.getText().trim(); 
								
							}
							if(txtTenKH.getText().isEmpty()) {
								tenKH = null ;
							}else {
								tenKH = txtTenKH.getText().trim();
							}
							BillDAO.saveBill(maHD, maNV, maKH, tienPhaiTra);
							int index = 0;
							for (int i = 0; i < tbCart.getRowCount(); i++) {

								String maSP = (String) tbCart.getValueAt(i, 0);
								String tenSP = (String) tbCart.getValueAt(i, 1);
								int soLuong = (Integer) tbCart.getValueAt(i, 2);
								double donGia = (Double) tbCart.getValueAt(i, 3);
								CustomerDAO.themDiemTichLuy(maKH,500);
								
								// Lưu vào mảng chi tiết sản phẩm
								chiTietSP[index][0] = maSP; // Mã SP
								chiTietSP[index][1] = tenSP; // Tên SP
								chiTietSP[index][2] = String.valueOf(soLuong); // Số lượng
								chiTietSP[index][3] = String.valueOf(donGia); // Đơn giá
								BillDetailsDAO.saveBillDetails(maHD, maSP, tenSP, soLuong, donGia);
								index++;
							}
							JOptionPane.showMessageDialog(parentComponent, "Hóa đơn đã được lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
							CustomerPanel.loadDataKhachHang();
							BillPanel.loadDataBill();
						} catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(parentComponent, "Lỗi khi lưu hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
						}
						BillExporter.exportBill(maHD, maKH, tenKH, maNV, tienPhaiTra, PTTT,tienTra, chiTietSP);
						break;
					}


					default:
						break;
					}
					((DefaultTableModel) cartTableModel).setRowCount(0);
					txtTongTien.setText("");
					txtMaKH.setText("");
					txtTenKH.setText("");
					txtSoDienThoai.setText("");
					txtDiemTichLuy.setText("");
					txtTienTra.setText("");
					txtTienThua.setText("");
					buttonGroup.clearSelection();
				}
				else if(sel == JOptionPane.NO_OPTION) {
					return;
				}
			}
		}

	}



	private void timKhachHang() {
		if(txtMaKH.getText().isEmpty()) {
			txtTenKH.setText("");
			txtSoDienThoai.setText("");
			txtDiemTichLuy.setText(String.valueOf(""));
		}
		else {
			String maKhachHang = txtMaKH.getText().trim(); // Giả sử có một ô nhập mã khách hàng

			CustomerDAO customerDAO = new CustomerDAO();
			Customer customer = customerDAO.findCustomerByMaKh(maKhachHang);

			if (customer != null) {
				// Hiển thị thông tin khách hàng trong các trường tương ứng
				txtTenKH.setText(customer.getTenKh());
				txtSoDienThoai.setText(customer.getSoDienThoai());
				txtDiemTichLuy.setText(String.valueOf(customer.getDiemTichLuy()));
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với mã này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				txtMaKH.setText("");
				txtTenKH.setText("");
				txtSoDienThoai.setText("");
				txtDiemTichLuy.setText(String.valueOf(""));
			}
		}

	}
	private void timKhachHangSDT() {
		if(txtSoDienThoai.getText().isEmpty()) {
			txtTenKH.setText("");
			txtSoDienThoai.setText("");
			txtDiemTichLuy.setText(String.valueOf(""));
		}
		else {
			String sdt = txtSoDienThoai.getText().trim(); // Giả sử có một ô nhập mã khách hàng

			CustomerDAO customerDAO = new CustomerDAO();
			Customer customer = customerDAO.findCustomerByPhone(sdt);

			if (customer != null) {
				// Hiển thị thông tin khách hàng trong các trường tương ứng
				txtMaKH.setText(customer.getMaKh());
				txtTenKH.setText(customer.getTenKh());
				txtSoDienThoai.setText(customer.getSoDienThoai());
				txtDiemTichLuy.setText(String.valueOf(customer.getDiemTichLuy()));
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với số này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}
	private void addToCart() {
		int selectedRow = tbProduct.getSelectedRow();
		if (selectedRow != -1) {
			String maSP = dsMSP.get(selectedRow); // Giả sử dsMSP chứa mã sản phẩm
			String tenSP = (String) tbProduct.getValueAt(selectedRow, 1);
			double donGia = Double.parseDouble(tbProduct.getValueAt(selectedRow, 2).toString());
			int soLuong;
			try {
				soLuong = Integer.parseInt(txtSoLuong.getText());
				if (soLuong <= 0) {
					throw new NumberFormatException(); // Ném ngoại lệ nếu số lượng không hợp lệ
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int availableQuantity = soLuongSanPham(maSP); // Hàm này trả về số lượng còn lại của sản phẩm
			if (soLuong > availableQuantity) {
				JOptionPane.showMessageDialog(this, "Không đủ số lượng sản phẩm trong kho!\nSố lượng còn lại: " + availableQuantity, "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
			boolean productExists = false;
			for (int i = 0; i < cartTableModel.getRowCount(); i++) {
				if (cartTableModel.getValueAt(i, 0).equals(maSP)) { // So sánh mã sản phẩm
					int currentQuantity = (int) cartTableModel.getValueAt(i, 2);
					currentQuantity += soLuong; // Cộng dồn số lượng
					double thanhTien = donGia * currentQuantity; // Cập nhật thành tiền
					cartTableModel.setValueAt(currentQuantity, i, 2);
					cartTableModel.setValueAt(thanhTien, i, 4);
					productExists = true;
					break;
				}
			}

			// Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
			if (!productExists) {
				double thanhTien = donGia * soLuong; // Tính thành tiền
				((DefaultTableModel) cartTableModel).addRow(new Object[]{maSP, tenSP, soLuong, donGia, thanhTien});
			}

			txtSoLuong.setText(""); // Xóa trường nhập sau khi thêm
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
	}
	private int soLuongSanPham(String maSP) {
		// Tìm sản phẩm trong danh sách và trả về số lượng còn lại
		ProductDAO pDao = new ProductDAO();
		ArrayList<Product> productList = pDao.getAllProduct();
		for (Product product : productList) { // productList là danh sách sản phẩm
			if (product.getMaSP().equals(maSP)) {
				return product.getSoLuongTon(); // Trả về số lượng còn lại
			}
		}
		return 0; // Nếu không tìm thấy sản phẩm
	}
	private void themSoLuong() {
		int selectedRow = tbCart.getSelectedRow();
		if (selectedRow != -1) {
			int currentQuantity = (int) cartTableModel.getValueAt(selectedRow, 2);
			double donGia = (double) cartTableModel.getValueAt(selectedRow, 3);
			currentQuantity++; // Tăng số lượng lên 1

			// Cập nhật số lượng và thành tiền
			double thanhTien = donGia * currentQuantity;
			cartTableModel.setValueAt(currentQuantity, selectedRow, 2); // Cập nhật số lượng
			cartTableModel.setValueAt(thanhTien, selectedRow, 4); // Cập nhật thành tiền
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
		tinhTien();
	}
	private void giamSoLuong() {
		int selectedRow = tbCart.getSelectedRow();
		if (selectedRow != -1) {
			int currentQuantity = (int) cartTableModel.getValueAt(selectedRow, 2);
			double donGia = (double) cartTableModel.getValueAt(selectedRow, 3);
			currentQuantity--; // Tăng số lượng lên 1
			if(currentQuantity < 0) {
				JOptionPane.showMessageDialog(this,"Không thể giảm số lượng được nữa", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// Cập nhật số lượng và thành tiền
			double thanhTien = donGia * currentQuantity;
			cartTableModel.setValueAt(currentQuantity, selectedRow, 2); // Cập nhật số lượng
			cartTableModel.setValueAt(thanhTien, selectedRow, 4); // Cập nhật thành tiền
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
		tinhTien();
	}
	private void xoaKhoiGio() {
		int selectedRow = tbCart.getSelectedRow();
		if (selectedRow != -1) {
			// Xác nhận xóa sản phẩm
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				((DefaultTableModel) cartTableModel).removeRow(selectedRow); // Xóa sản phẩm khỏi giỏ hàng
			}
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
		tinhTien();
	}
	private void xoaToanBo() {
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			((DefaultTableModel) cartTableModel).setRowCount(0);
		}
	}
	private void tinhTien() {
		double total = 0;
		for (int i = 0; i < cartTableModel.getRowCount(); i++) {
			total += (double) cartTableModel.getValueAt(i, 4); // Cộng dồn thành tiền
		}
		if(total==0) {
			txtTongTien.setText("");
			JOptionPane.showMessageDialog(this,"Thêm sản phẩm vào giỏ và tính tiền trước!!!");
		}
		else {

			String tongTien = String.valueOf(total);
			txtTongTien.setText(tongTien);
		}
	}
	private void xoaThanhTien() {
		for (int i = cartTableModel.getRowCount() - 1; i >= 0; i--) {
			double thanhTien = (double) cartTableModel.getValueAt(i, 4); // Giả sử cột 4 chứa giá trị thành tiền
			if (thanhTien == 0) {
				// Xóa hàng nếu thành tiền bằng 0
				((DefaultTableModel) cartTableModel).removeRow(i);
			} 
		}
	}
}