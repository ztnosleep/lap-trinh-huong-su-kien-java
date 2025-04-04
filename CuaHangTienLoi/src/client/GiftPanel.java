package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import dao.CustomerDAO;
import dao.ProductDAO;
import entity.Customer;
import entity.Product;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GiftPanel extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private DefaultTableModel modelDoiQua;
	private ArrayList<Product> dssp;
	private Customer updatedCustomer;
	/**
	 * Create the frame.
	 * @param kh 
	 */
	public GiftPanel(Customer kh) {
		
		getContentPane().setLayout(null);
		setTitle("Đổi quà");
        setIconImage(Toolkit.getDefaultToolkit().getImage("./img/icon.png"));
		setSize(1000,700);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(197, 528, 219, 28));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		CustomerDAO customerDAO = new CustomerDAO();
	    updatedCustomer = customerDAO.findCustomerByMaKh(kh.getMaKh());
	    
		String[] cotTable = {"Tên sản phẩm", "Số lượng", "Điểm đổi quà"};
		modelDoiQua = new DefaultTableModel(cotTable, 0);
		table = new JTable(modelDoiQua); 
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		updatable();		
		
		// Adding the table to a JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(21, 63, 926, 442);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Điểm hiện tại:");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel.setBounds(21, 542, 336, 42);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		textField.setForeground(new Color(0, 0, 0));

		textField.setText(updatedCustomer.getDiemTichLuy()+"");
		textField.setBounds(237, 544, 210, 41);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Đổi quà");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		        	handleGiftExchange();
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + ex.getMessage());
		            ex.printStackTrace();
		        }
		    }
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnNewButton.setBounds(698, 533, 249, 57);
		contentPane.add(btnNewButton);

		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnNewButton.setBounds(698, 533, 158, 57);
		contentPane.add(btnNewButton);
	}
	// Phương thức xử lý logic đổi quà
			private void handleGiftExchange() {
			    int selectedRow = table.getSelectedRow();
			    if (selectedRow == -1) {
			        JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần đổi");
			        return;
			    }

			    Product selectedProduct = dssp.get(selectedRow);
			    int requiredPoints = (int) selectedProduct.getDonGia()/10;
			    
			    try {
					Customer kh2 = CustomerDAO.findCustomerByMaKh(updatedCustomer.getMaKh());
					int currentPoints = kh2.getDiemTichLuy();

						    if (currentPoints < requiredPoints) {
						        int pointsNeeded = requiredPoints - currentPoints;
						        JOptionPane.showMessageDialog(null, "Không đủ điểm để đổi, cần " + pointsNeeded + " điểm nữa!");
						        return;
						    }

						    int remainingPoints = currentPoints - requiredPoints;
						    if (updateCustomerPoints1(updatedCustomer.getMaKh(), remainingPoints)) {
						    	if(updateProductStock(selectedProduct)) {
						    		JOptionPane.showMessageDialog(null, "Đổi quà thành công!");
							        refreshCustomerTable();
						    	}else {
						    		JOptionPane.showMessageDialog(null, "Không cập nhật được số lượng");
						    	}
						        
						    } else {
						        JOptionPane.showMessageDialog(null, "Đổi quà thất bại, vui lòng thử lại.");
						    }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    ProductPanel.loadDataProduct();
			}

			// Phương thức cập nhật điểm khách hàng
			private boolean updateCustomerPoints1(String customerId, int remainingPoints) {
			    CustomerDAO customerDAO = new CustomerDAO();
			    boolean isUpdated = customerDAO.updateCustomerPoints(customerId, remainingPoints);
			    if (isUpdated) {
			        textField.setText(String.valueOf(remainingPoints));
			        System.out.println("Cập nhật điểm khách hàng thành công");
			    } else {
			        System.out.println("Cập nhật điểm khách hàng thất bại");
			    }
			    return isUpdated;
			}

			// Phương thức cập nhật số lượng tồn kho sản phẩm
			private boolean updateProductStock(Product product) {
			    ProductDAO productDAO = new ProductDAO();
			    int updatedStock = product.getSoLuongTon() - 1;
			    boolean isUpdated = productDAO.updateProductStock(product.getMaSP(), updatedStock);
			    if (isUpdated) {
			        ProductPanel.loadDataProduct();
			    } else {
			        System.out.println("Cập nhật số lượng tồn kho thất bại");
			    }
			    return isUpdated;
			}

			// Phương thức làm mới bảng khách hàng
			private void refreshCustomerTable() {
			    CustomerDAO customerDAO = new CustomerDAO();
			    ArrayList<Customer> updatedCustomerList = customerDAO.getAllKhachHang();
			    CustomerPanel.updateCustomerTable(updatedCustomerList);
			    
			    updatable();
			}

	public void updatable() {
		ProductDAO proDao = new ProductDAO();
	    dssp = proDao.getAllProduct();
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // Xóa tất cả hàng hiện có
	    for (Product p : dssp) {
	        if(p.getSoLuongTon() > 0) {
	        	Object[] row = new Object[11];
		        row[0] = p.getTenSP() ;

		        row[1] = p.getSoLuongTon();
		        row[2] = (int)p.getDonGia()/10;
		        
		        model.addRow(row); 
	        }
	    }
	    table.setRowHeight(80); // Đặt chiều cao hàng
	    HomePanel.loadDataProductMain(); 
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
