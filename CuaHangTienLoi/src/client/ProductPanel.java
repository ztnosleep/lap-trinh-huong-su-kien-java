package client;


import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.ProductDAO;
import dao.ProductTypeDAO;
import entity.Product;
import entity.ProductType;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JFrame frame;
	private JTextField txtMaSanPham;
	private JTextField txtTenSanPham;
	private JTextField txtGia;
	private JTextField txtMoTa;
	private JLabel lblImage; 
	private static DefaultTableModel tableModel;
	private static JTable productTable;
	private JTextField txtSL;
	private JTextField txtXuatXu;
	private JTextField txtDVT;
	private JDateChooser dateChooserSX;
	private JDateChooser dateChooserSD;
	//private JTextField textField;
	private static JComboBox<Object> comboBox;
	private static JComboBox<String> comboBoxMaLoai;
	private File file;
	//private JFileChooser fileChooser;
	private BufferedImage bufferedImage;
	//private BufferedImage tempImage;
	private byte[] imageData;
	private JTextField txtSearch;

	public ProductPanel() {
		setLayout(null);
		initialize();

	}

	private void initialize() {
		JPanel pnProduct = new JPanel();
		pnProduct.setBackground(new Color(240,255,255));
		pnProduct.setBorder(new LineBorder(Color.BLACK));
		pnProduct.setBounds(0, 0, 1644, 1070);
		add(pnProduct);
		pnProduct.setLayout(null);


		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setIcon(new ImageIcon("./img/find.png"));
		btnNewButton.setBounds(585, 32, 66, 37);
		pnProduct.add(btnNewButton);
		btnNewButton.addActionListener(e -> timKiem());

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

		JLabel lblSearchProduct = new JLabel("Tìm sản phẩm");
		lblSearchProduct.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblSearchProduct.setBounds(34, 32, 164, 37);
		pnProduct.add(lblSearchProduct);

		comboBox = new JComboBox<>();
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(927, 32, 178, 37);
		comboBox.addItem("");
		pnProduct.add(comboBox);
		comboBox.addActionListener(e -> searchProducts());


		JLabel lblProductType = new JLabel("Loại sản phẩm");
		lblProductType.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblProductType.setBounds(740, 32, 177, 37);
		pnProduct.add(lblProductType);

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(Color.white);
		panelInfo.setBorder(BorderFactory.createLineBorder(Color.black));
		panelInfo.setBounds(49, 655, 1018, 370);
		panelInfo.setLayout(null);
		pnProduct.add(panelInfo);

		JLabel lblLoaiSP = new JLabel("Loại Sản Phẩm:");
		lblLoaiSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblLoaiSP.setBounds(54, 260, 146, 38);
		panelInfo.add(lblLoaiSP);

		comboBoxMaLoai = new JComboBox<>();
		comboBoxMaLoai.setBounds(235, 274, 345, 21);
		panelInfo.add(comboBoxMaLoai);
		loadDataProductType();


		// Mã sản phẩm
		JLabel lblMaSP = new JLabel("Mã Sản Phẩm:");
		lblMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblMaSP.setBounds(54, 74, 133, 27);
		panelInfo.add(lblMaSP);
		txtMaSanPham = new JTextField();
		txtMaSanPham.setBounds(235, 81, 345, 23);
		txtMaSanPham.setEnabled(false);
		panelInfo.add(txtMaSanPham);

		// Tên sản phẩm
		JLabel lblTenSP = new JLabel("Tên Sản Phẩm:");
		lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblTenSP.setBounds(54, 112, 133, 27);
		panelInfo.add(lblTenSP);
		txtTenSanPham = new JTextField();
		txtTenSanPham.setBounds(235, 115, 345, 23);
		panelInfo.add(txtTenSanPham);

		// Giá
		JLabel lblGia = new JLabel("Giá:");
		lblGia.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblGia.setBounds(54, 150, 133, 27);
		panelInfo.add(lblGia);
		txtGia = new JTextField();
		txtGia.setBounds(235, 149, 345, 23);
		panelInfo.add(txtGia);

		// Mô tả
		JLabel lblMoTa = new JLabel("Mô Tả:");
		lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblMoTa.setBounds(54, 188, 133, 27);
		panelInfo.add(lblMoTa);
		txtMoTa = new JTextField();
		txtMoTa.setBounds(235, 183, 345, 84);
		panelInfo.add(txtMoTa);

		lblImage = new JLabel();
		lblImage.setBackground(new Color(255, 255, 255));
		lblImage.setBounds(623, 237, 100, 100);
		panelInfo.add(lblImage);

		JButton btnChonAnh = new JButton("Chọn Ảnh");
		btnChonAnh.setBackground(new Color(255, 255, 255));
		btnChonAnh.setBounds(235, 35, 120, 30);
		btnChonAnh.addActionListener(e -> selectImage());
		panelInfo.add(btnChonAnh);

		JLabel lblAnhSP = new JLabel("Ảnh Sản Phẩm:");
		lblAnhSP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblAnhSP.setBounds(54, 35, 133, 27);
		panelInfo.add(lblAnhSP);

		JLabel lblSLng = new JLabel("Số lượng:");
		lblSLng.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblSLng.setBounds(54, 299, 146, 38);
		panelInfo.add(lblSLng);

		txtSL = new JTextField();
		txtSL.setBounds(235, 306, 345, 23);
		panelInfo.add(txtSL);

		JLabel lblNgaySX = new JLabel("Ngày sản xuất: ");
		lblNgaySX.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNgaySX.setBounds(623, 49, 133, 27);
		panelInfo.add(lblNgaySX);

		dateChooserSX = new JDateChooser();
		dateChooserSX.getCalendarButton().setBackground(new Color(255, 255, 255));

        dateChooserSX.getDateEditor().setEnabled(false);
		dateChooserSX.setBounds(760, 49, 248, 23);
		panelInfo.add(dateChooserSX);

		JLabel lblHnSDng = new JLabel("Hạn sử dụng:");
		lblHnSDng.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblHnSDng.setBounds(623, 99, 133, 27);
		panelInfo.add(lblHnSDng);

		dateChooserSD = new JDateChooser();
		dateChooserSD.getCalendarButton().setBackground(new Color(255, 255, 255));
        dateChooserSD.getDateEditor().setEnabled(false);
		dateChooserSD.setBounds(760, 99, 248, 23);
		panelInfo.add(dateChooserSD);

		JLabel lblXuatXu = new JLabel("Xuất xứ:");
		lblXuatXu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblXuatXu.setBounds(623, 150, 133, 27);
		panelInfo.add(lblXuatXu);

		txtXuatXu = new JTextField();
		txtXuatXu.setBounds(760, 154, 248, 23);
		panelInfo.add(txtXuatXu);

		JLabel lblDVT = new JLabel("Đơn vị tính:");
		lblDVT.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblDVT.setBounds(623, 199, 133, 27);
		panelInfo.add(lblDVT);

		txtDVT = new JTextField();
		txtDVT.setBounds(760, 195, 248, 23);
		panelInfo.add(txtDVT);

		// Table setup
		//		String[] columnNames = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Ngày sản xuất", "Hạn sử dụng", "Đơn vị tính", "Mô Tả", "Số lượng", "Xuất Xứ", "Mã loại","Ảnh"};
		//		tableModel = new DefaultTableModel(columnNames, 0);
		tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"M\u00E3 S\u1EA3n Ph\u1EA9m", "T\u00EAn S\u1EA3n Ph\u1EA9m", "Gi\u00E1", "Ng\u00E0y s\u1EA3n xu\u1EA5t", "H\u1EA1n s\u1EED d\u1EE5ng", "\u0110\u01A1n v\u1ECB t\u00EDnh", "M\u00F4 T\u1EA3", "S\u1ED1 l\u01B0\u1EE3ng", "Xu\u1EA5t X\u1EE9", "M\u00E3 lo\u1EA1i", "\u1EA2nh"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, ImageIcon.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		productTable = new JTable(tableModel);

		productTable.setFillsViewportHeight(true);
		productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(productTable);
		scrollPane.setBounds(49, 128, 1542, 495);
		pnProduct.add(scrollPane);;
		scrollPane.setViewportView(productTable);
		// Add some sample data
		loadDataProduct();

		JButton btnCapNhatSP = new JButton("Cập Nhật Sản Phẩm");
		btnCapNhatSP.setBackground(new Color(255, 255, 255));
		btnCapNhatSP.setBounds(1169, 816, 157, 44);
		btnCapNhatSP.addActionListener(e -> updateProduct());
		pnProduct.add(btnCapNhatSP);

		JButton btnXoaSP = new JButton("Làm mới");
		btnXoaSP.setBackground(new Color(255, 255, 255));
		btnXoaSP.setBounds(1169, 895, 157, 44);

		btnXoaSP.addActionListener(e -> deleteProduct());
		pnProduct.add(btnXoaSP);

		JButton btnThemSP = new JButton("Thêm Sản Phẩm");
		btnThemSP.setBackground(new Color(255, 255, 255));
		btnThemSP.setBounds(1169, 736, 157, 44);
		btnThemSP.addActionListener(e -> {
		});
		pnProduct.add(btnThemSP);

		JButton btnThemLoai = new JButton("Quản lí loại sản phẩm");
		btnThemLoai.setBackground(Color.WHITE);
		btnThemLoai.setBounds(1169, 969, 157, 44);
		pnProduct.add(btnThemLoai);
		btnThemLoai.addActionListener(e -> addTypeProduct());

		btnThemSP.addActionListener(e -> addNewProduct());
		productTable.addMouseListener(this);
		
		txtSearch.addActionListener(e -> timKiem());
	}
	private void timKiem() {
		String tuKhoa = txtSearch.getText();
		if(!tuKhoa.isEmpty()) {
			ProductDAO pDao = new ProductDAO();
			ArrayList<Product> productList = pDao.findProductByName(tuKhoa);
			updateProductTable(productList);
		}
		else {
			JOptionPane.showMessageDialog(productTable, "Vui lòng nhập tên sản phẩm");
			loadDataProduct();
		}
	}
	private void searchProducts() {
        String selectedCategory = (String) comboBox.getSelectedItem(); // Lấy tùy chọn được chọn
        //String searchTerm = txtSearch.getText().trim();
        if(!selectedCategory.isEmpty()) {
        	ProductDAO productDao = new ProductDAO();
            ArrayList<Product> productList = productDao.searchProducts(selectedCategory);
            
            // Cập nhật bảng sản phẩm với danh sách tìm được
            updateProductTable(productList);
        }
        else {
        	loadDataProduct();
        }
        
    }
	public void updateProductTable(ArrayList<Product> productList) {
		DefaultTableModel model = (DefaultTableModel) productTable.getModel(); // Giả sử bạn có customerTable
	    model.setRowCount(0); // Xóa dữ liệu cũ

	    for (Product p : productList) {
	    	Object[] row = new Object[11];
	        row[0] = p.getMaSP();
	        row[1] = p.getTenSP();
	        row[2] = p.getDonGia(); // Tính giá bán
	        row[3] = p.getNgaySX();
	        row[4] = p.getHanSD();
	        row[5] = p.getDonViTinh();
	        row[6] = p.getMoTa();
	        row[7] = p.getSoLuongTon(); // Số lượng tồn kho
	        row[8] = p.getXuatXu();
	        row[9] = p.getMaLoai();
	        
	        if (p.getImage() != null) {
	            ImageIcon imageIcon = new ImageIcon(new ImageIcon(p.getImage()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
	            row[10] = imageIcon;
	        } else {
	            row[10] = null;
	        }

	        model.addRow(row);
	    }
	}
	public void selectImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);

		// Thiết lập bộ lọc cho loại file hình ảnh
		fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				// Chấp nhận thư mục và các file hình ảnh
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") || 
						f.getName().toLowerCase().endsWith(".jpeg") || 
						f.getName().toLowerCase().endsWith(".png") || 
						f.getName().toLowerCase().endsWith(".gif");
			}

			@Override
			public String getDescription() {
				return "Hình ảnh (*.jpg, *.jpeg, *.png, *.gif)";
			}
		});

		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();

			// Kiểm tra loại file
			if (file != null && (file.getName().toLowerCase().endsWith(".jpg") || 
					file.getName().toLowerCase().endsWith(".jpeg") || 
					file.getName().toLowerCase().endsWith(".png") || 
					file.getName().toLowerCase().endsWith(".gif"))) {
				try {
					// Đọc file ảnh
					bufferedImage = ImageIO.read(file);
					// Tạo ImageIcon từ BufferedImage
					ImageIcon imageIcon = new ImageIcon(bufferedImage);
					// Hiển thị hình ảnh trong JLabel
					lblImage.setIcon(imageIcon);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Lỗi khi mở file ảnh: " + e.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn một file hình ảnh hợp lệ.");
			}
		}
	}

	private void addTypeProduct() {
		// TODO Auto-generated method stub
		AddTypeProductPanel newFr = new AddTypeProductPanel();
		newFr.setVisible(true);
		
	}

	static void loadDataProduct() {
	    ProductDAO proDao = new ProductDAO();
	    ArrayList<Product> dssp = proDao.getAllProduct(); // Lấy danh sách sản phẩm từ cơ sở dữ liệu
	    DefaultTableModel model = (DefaultTableModel) productTable.getModel();
	    model.setRowCount(0); // Xóa tất cả hàng hiện có

	    // Thêm từng sản phẩm vào bảng
	    for (Product p : dssp) {
	        Object[] row = new Object[11];
	        row[0] = p.getMaSP();
	        row[1] = p.getTenSP();
	        row[2] = p.getDonGia(); // Tính giá bán
	        row[3] = p.getNgaySX();
	        row[4] = p.getHanSD();
	        row[5] = p.getDonViTinh();
	        row[6] = p.getMoTa();
	        row[7] = p.getSoLuongTon(); // Số lượng tồn kho
	        row[8] = p.getXuatXu();
	        row[9] = p.getMaLoai();
	        
	        // Xử lý hình ảnh
	        if (p.getImage() != null) {
	            ImageIcon imageIcon = new ImageIcon(new ImageIcon(p.getImage()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
	            row[10] = imageIcon;
	        } else {
	            row[10] = null;
	        }
	        
	        java.util.Date hetHan = p.getHanSD();
	        java.util.Date currentDate = new java.util.Date();
			if (hetHan.before(currentDate)) {
				row[7] = 0;
				p.setSoLuongTon(0);
			}

	        model.addRow(row); // Thêm hàng vào bảng
	    }

	    productTable.setRowHeight(80); // Đặt chiều cao hàng
	    HomePanel.loadDataProductMain(); // Tải lại dữ liệu chính (nếu cần)
	}
	private static Map<String, String> productTypeMap = new HashMap<>();

	static void loadDataProductType() {
		ProductTypeDAO typeDao = new ProductTypeDAO();
		ArrayList<ProductType> dsML = typeDao.getProductType();

		comboBoxMaLoai.removeAllItems();
		productTypeMap.clear(); // Đảm bảo Map rỗng

		for (ProductType p : dsML) {
			comboBoxMaLoai.addItem(p.getTenLoai()); // Thêm tên loại vào ComboBox
			comboBox.addItem(p.getTenLoai()); // Thêm tên loại vào ComboBox
			productTypeMap.put(p.getTenLoai(), p.getMaLoai()); // Lưu mã loại tương ứng

		}
	}
	private void addNewProduct() {
		// Gather data from input fields
		//String maSP = txtMaSanPham.getText().trim();
		ProductDAO proDao = new ProductDAO();
		String maSP = proDao.getNextProductId();
		
		String tenSP = txtTenSanPham.getText().trim();
		String giaStr = txtGia.getText().trim();
		String moTa = txtMoTa.getText().trim();
		String soLuongStr = txtSL.getText().trim();
		String xuatXu = txtXuatXu.getText().trim();
		String dvt = txtDVT.getText().trim();
		String tenLoai = (String) comboBoxMaLoai.getSelectedItem();
		String maLoai = productTypeMap.get(tenLoai);


		// Get dates from date choosers
		java.util.Date dateSX = dateChooserSX.getDate();
		java.util.Date dateSD = dateChooserSD.getDate();

		// Validate input data
		if (maSP.isEmpty() || tenSP.isEmpty() || giaStr.isEmpty() || soLuongStr.isEmpty() || xuatXu.isEmpty() || dvt.isEmpty() || dateSX == null || dateSD == null || file == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		java.util.Date currentDate = new java.util.Date();
		if (!dateSX.before(currentDate)) {
			JOptionPane.showMessageDialog(this, "Ngày sản xuất phải trước ngày hiện tại.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (!dateSD.after(currentDate)) {
			JOptionPane.showMessageDialog(this, "Hạn sử dụng phải sau ngày hiện tại.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if( dateSX.after(dateSD)) {
			JOptionPane.showMessageDialog(this, "Ngày sản xuất phải trước hạn sử dụng.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}


		// Parse numeric values
		double gia;
		int soLuong;
		try {
			gia = Double.parseDouble(giaStr);
			soLuong = Integer.parseInt(soLuongStr);
			if(gia <0) {
				JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(soLuong < 0) {
				JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Create a new Product object

		try {
			
			Product newProduct = new Product();
			newProduct.setMaSP(maSP);
			newProduct.setTenSP(tenSP);
			newProduct.setDonGia(gia);
			newProduct.setDonViTinh(dvt);
			newProduct.setHanSD(dateSD);
			newProduct.setNgaySX(dateSX);
			newProduct.setMaLoai(maLoai);
			newProduct.setMoTa(moTa);
			newProduct.setSoLuongTon(soLuong);
			newProduct.setXuatXu(xuatXu);
			if(file!=null)
				newProduct.setImage(Files.readAllBytes(this.file.toPath()));
			if (proDao.addProduct(newProduct)) {
				JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				loadDataProduct(); 
				HomePanel.loadDataProductMain();
			} else {
				JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clearFields();

	}
	private void updateProduct() {
		// Lấy thông tin từ các trường nhập
		String maSP = txtMaSanPham.getText().trim();
		String tenSP = txtTenSanPham.getText().trim();
		String giaStr = txtGia.getText().trim();
		String moTa = txtMoTa.getText().trim();
		String soLuongStr = txtSL.getText().trim();
		String xuatXu = txtXuatXu.getText().trim();
		String dvt = txtDVT.getText().trim();
		String tenLoai = (String) comboBoxMaLoai.getSelectedItem();
		String maLoai = productTypeMap.get(tenLoai);
		System.out.println(maSP);
		// Get dates from date choosers
		java.util.Date dateSX = dateChooserSX.getDate();
		java.util.Date dateSD = dateChooserSD.getDate();

		// Validate input data
		if (maSP.isEmpty() || tenSP.isEmpty() || giaStr.isEmpty() || soLuongStr.isEmpty() || xuatXu.isEmpty() || dvt.isEmpty() || dateSX == null || dateSD == null || imageData == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}


		java.util.Date currentDate = new java.util.Date();
		if (!dateSX.before(currentDate)) {
			JOptionPane.showMessageDialog(this, "Ngày sản xuất phải trước ngày hiện tại.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (!dateSD.after(currentDate)) {
			JOptionPane.showMessageDialog(this, "Hạn sử dụng phải sau ngày hiện tại.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if( dateSX.after(dateSD)) {
			JOptionPane.showMessageDialog(this, "Ngày sản xuất phải trước hạn sử dụng.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Parse numeric values
		double gia;
		int soLuong;
		try {
			gia = Double.parseDouble(giaStr);
			soLuong = Integer.parseInt(soLuongStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			ProductDAO proDao = new ProductDAO();
			Product newProduct = new Product();
			newProduct.setMaSP(maSP);
			newProduct.setTenSP(tenSP);
			newProduct.setDonGia(gia);
			newProduct.setDonViTinh(dvt);
			newProduct.setHanSD(dateSD);
			newProduct.setNgaySX(dateSX);
			newProduct.setMaLoai(maLoai);
			newProduct.setMoTa(moTa);
			newProduct.setSoLuongTon(soLuong);
			newProduct.setXuatXu(xuatXu);
			
			if (file != null) 
	            newProduct.setImage(Files.readAllBytes(this.file.toPath())); // Cập nhật hình ảnh mới
			else if(imageData!=null)
				newProduct.setImage(imageData);
			if (proDao.updateProduct(newProduct)) {
				JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				clearFields();
				loadDataProduct(); 
				HomePanel.loadDataProductMain();// Refresh the table data
			} else {
				JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void deleteProduct() {
		clearFields();
	}

	public void chonDong(int row) throws IOException {
		txtMaSanPham.setText(tableModel.getValueAt(row, 0).toString());
		txtTenSanPham.setText(tableModel.getValueAt(row, 1).toString());

		double donGiaBan = Double.parseDouble(tableModel.getValueAt(row, 2).toString());
		//double donGiaNhap = donGiaBan / 1.3;
		txtGia.setText(String.valueOf(tableModel.getValueAt(row, 2).toString()));

		dateChooserSX.setDate((java.sql.Date) tableModel.getValueAt(row, 3));
		dateChooserSD.setDate((java.sql.Date) tableModel.getValueAt(row, 4));
		txtDVT.setText(tableModel.getValueAt(row, 5).toString());
		txtMoTa.setText(tableModel.getValueAt(row, 6).toString());
		txtSL.setText(tableModel.getValueAt(row, 7).toString());
		txtXuatXu.setText(tableModel.getValueAt(row, 8).toString());
		ImageIcon imageIcon = (ImageIcon) tableModel.getValueAt(row, 10); 
		lblImage.setIcon(imageIcon);

		if (imageIcon != null) {
	        // Chuyển đổi Image thành BufferedImage
	        BufferedImage tempImage = convertToBufferedImage(imageIcon.getImage());
	        // Chuyển đổi BufferedImage thành mảng byte
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(tempImage, "png", baos); // Ghi hình ảnh vào ByteArrayOutputStream
	        baos.flush();
	        imageData = baos.toByteArray(); // Lưu mảng byte vào biến
	    }else {
	    	imageIcon= null;
	    }
		// Cài đặt cho combo box
		comboBoxMaLoai.setSelectedItem((String) tableModel.getValueAt(row, 9)); // Cột 10 chứa maLoai
	}
	private BufferedImage convertToBufferedImage(Image img) {
	    if (img instanceof BufferedImage) {
	        return (BufferedImage) img; // Nếu đã là BufferedImage, trả về ngay
	    }

	    // Tạo BufferedImage mới và vẽ hình ảnh vào đó
	    BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics g = bufferedImage.getGraphics();
	    g.drawImage(img, 0, 0, null); // Vẽ hình ảnh vào BufferedImage
	    g.dispose(); // Giải phóng tài nguyên Graphics
	    return bufferedImage;
	}
	private void clearFields() {
	    txtMaSanPham.setText(""); 
	    txtTenSanPham.setText(""); 
	    txtGia.setText(""); 
	    txtMoTa.setText(""); 
	    txtSL.setText(""); 
	    txtXuatXu.setText(""); 
	    txtDVT.setText(""); 
	    comboBoxMaLoai.setSelectedIndex(0); 
	    dateChooserSX.setDate(null); 
	    dateChooserSD.setDate(null); 
	    lblImage.setIcon(null);
	    imageData = null;
	    file = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int select = productTable.getSelectedRow();
		try {
			chonDong(select);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
