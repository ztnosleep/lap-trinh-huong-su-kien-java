package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;

//import com.toedter.calendar.JDateChooser;

import dao.BillDAO;
import entity.Bill;

public class BillPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DefaultTableModel billTableModel;
	private static JTable billTable;
	private JTextField txtSearch;
	private JDateChooser dateChooser;
	public BillPanel() {
		setLayout(null);
		initialize();
		loadDataBill();
	}
	private void initialize() {
		JPanel pnBill = new JPanel();
		pnBill.setBackground(new Color(240,255,255));
		pnBill.setBorder(new LineBorder(Color.BLACK));
		pnBill.setBounds(0, 0, 1644, 1070);
		add(pnBill);
		pnBill.setLayout(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setIcon(new ImageIcon("./img/find.png"));
		btnNewButton.setBounds(585, 32, 66, 37);
		pnBill.add(btnNewButton);
		
		JLabel lblPickDate = new JLabel("Chọn ngày");
		lblPickDate.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblPickDate.setBounds(47, 80, 164, 37);
		pnBill.add(lblPickDate);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.getCalendarButton().setBackground(new Color(255, 255, 255));
		dateChooser.setBounds(221, 80, 150, 37);
		pnBill.add(dateChooser);
		
		dateChooser.addPropertyChangeListener("date", e -> timKiemTheoNgay());
		
		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBounds(221, 32, 367, 37);
		pnBill.add(txtSearch);
		txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Nhập mã hóa đơn...")) {
                    txtSearch.setText(""); // Xóa nội dung khi nhấp vào
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Nhập mã hóa đơn..."); // Đặt lại nội dung khi rời khỏi ô
                }
            }
        });
		txtSearch.addActionListener(e-> timKiem());
		JLabel lblSearchProduct = new JLabel("Tìm hóa đơn");
		lblSearchProduct.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblSearchProduct.setBounds(47, 32, 164, 37);
		pnBill.add(lblSearchProduct);
		
		billTableModel = (new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"M\u00E3 h\u00F3a \u0111\u01A1n", "M\u00E3 kh\u00E1ch h\u00E0ng", "T\u1ED5ng ti\u1EC1n", "Ng\u00E0y l\u1EADp h\u00F3a \u0111\u01A1n", "M\u00E3 nh\u00E2n vi\u00EAn"
				}
			));
		billTable = new JTable(billTableModel);
		billTable.setFillsViewportHeight(true);
		billTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(billTable);
		scrollPane.setBounds(49, 128, 1542, 495);
		pnBill.add(scrollPane);;
		scrollPane.setViewportView(billTable);
	
	}
	public static void loadDataBill() {
		BillDAO bDao = new BillDAO();
		ArrayList<Bill> dshd = bDao.getAllBill();
		billTableModel.setRowCount(0);

		DefaultTableModel model = (DefaultTableModel) billTable.getModel();
		
		for(int i = 0; i< dshd.size();i++) {
			Object[] row = new Object[11];
			row[0] = dshd.get(i).getMaHD();
			row[1] = dshd.get(i).getMaKH();
			row[3] = dshd.get(i).getNgayLap();
			row[4] = dshd.get(i).getMaNV();
			row[2] = dshd.get(i).getTongtien();
			
			model.addRow(row);
		}

		billTable.setRowHeight(50);
		ProductPanel.loadDataProduct();
	}


	// Cập nhật hàm timKiem
	private void timKiem() {
	    String maHoaDon = txtSearch.getText().trim(); // Lấy mã hóa đơn từ ô tìm kiếm
	    BillDAO bDao = new BillDAO();
	    ArrayList<Bill> dshd = bDao.getAllBill(); // Lấy danh sách hóa đơn

	    // Xóa dữ liệu cũ trong bảng
	    billTableModel.setRowCount(0);

	    // Lọc và thêm các hóa đơn khớp với mã tìm kiếm
	    for (Bill bill : dshd) {
	        if (bill.getMaHD().toLowerCase().contains(maHoaDon.toLowerCase()) || maHoaDon.isEmpty()) {
	            Object[] row = new Object[5]; // Số lượng cột trong bảng
	            row[0] = bill.getMaHD();
	            row[1] = bill.getMaKH();
	            row[2] = bill.getTongtien();
	            row[3] = bill.getNgayLap();
	            row[4] = bill.getMaNV();
	            billTableModel.addRow(row); // Thêm hàng mới vào bảng
	        }
	    }
	}
	void timKiemTheoNgay() {
		
	}
}
