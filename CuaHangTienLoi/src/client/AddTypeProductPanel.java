package client;


import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

import dao.ProductTypeDAO;
import entity.ProductType;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import javax.swing.JTable;

public class AddTypeProductPanel extends JFrame implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMaLoai;
	private JTextField txtTenLoai;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public AddTypeProductPanel() {
		getContentPane().setLayout(null);
		setTitle("Quản lí loại sản phẩm");
        setIconImage(Toolkit.getDefaultToolkit().getImage("./img/icon.png"));
		setSize(1000,700);
		setLocationRelativeTo(null);
		setResizable(false);
		txtMaLoai = new JTextField();
		txtMaLoai.setBounds(197, 528, 219, 28);
		getContentPane().add(txtMaLoai);
		txtMaLoai.setEnabled(false);
		txtMaLoai.setColumns(10);
		
		txtTenLoai = new JTextField();
		txtTenLoai.setBounds(197, 577, 219, 28);
		getContentPane().add(txtTenLoai);
		txtTenLoai.setColumns(10);
		
		JLabel lblMaLoai = new JLabel("Nhập mã loại:");
		lblMaLoai.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblMaLoai.setBounds(35, 528, 142, 27);
		getContentPane().add(lblMaLoai);
		
		JLabel lblNhpTnLoi = new JLabel("Nhập tên loại:");
		lblNhpTnLoi.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNhpTnLoi.setBounds(35, 577, 142, 27);
		getContentPane().add(lblNhpTnLoi);
		
		JButton btnAddType = new JButton("Thêm loại mới");
		btnAddType.setBackground(new Color(255, 255, 255));
		btnAddType.setBounds(450, 545, 119, 44);
		btnAddType.addActionListener(e-> addType());
		getContentPane().add(btnAddType);
		
		JButton btnUpdate = new JButton("Chỉnh sửa");
		btnUpdate.setBackground(new Color(255, 255, 255));
		btnUpdate.setBounds(579, 545, 119, 44);
		btnUpdate.addActionListener(e->updatePType());
		getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Xóa");
		btnDelete.setBackground(new Color(255, 255, 255));
		btnDelete.setBounds(708, 545, 119, 44);
		btnDelete.addActionListener(e->deletePType());
		getContentPane().add(btnDelete);
		
		JButton btnExit = new JButton("Đóng");
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(837, 545, 119, 44);
		
		btnExit.addActionListener(e -> ExitAddType());
		getContentPane().add(btnExit);
		String[] columnNameTypes = {"Mã loại sản phẩm","Tên loại sản phẩm"};
		tableModel = new DefaultTableModel(columnNameTypes,0);
		table = new JTable(tableModel);
		//table.setBounds(35, 33, 900, 460);
		
		
		table.addMouseListener(this);
		JScrollPane scp= new JScrollPane(table);
		scp.setBounds(35, 33, 930, 460);
		getContentPane().add(scp);
		
		txtTenLoai.addActionListener(e -> addType());
		
		loadDataProductTypeTable();
	}
	private void deletePType() {
		txtMaLoai.setText("");
		txtTenLoai.setText("");
	}
	
	private void updatePType() {
		String maLoai = txtMaLoai.getText().trim();
		String tenLoai = txtTenLoai.getText().trim();
		
		if(maLoai.isEmpty() | tenLoai.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        return;
		}
		
		ProductType newType = new ProductType(maLoai, tenLoai);
		ProductTypeDAO tDao = new ProductTypeDAO();
		boolean check = tDao.updateType(newType);
		if(check) {
			JOptionPane.showMessageDialog(this, "Cập nhật loại sản phẩm thành công");
			loadDataProductTypeTable();
			ProductPanel.loadDataProductType();
			HomePanel.loadDataProductTypeMain();
			

			ProductPanel.loadDataProduct();
			txtMaLoai.setText("");
			txtTenLoai.setText("");
			txtMaLoai.requestFocus();
			
		}else {
			JOptionPane.showMessageDialog(this, "Cập nhật loại sản phẩm thất bại");
			return;
		}
	}
	
	private void addType() {
		ProductTypeDAO tDao = new ProductTypeDAO();
		String maLoai = tDao.getNextProductTypeId();
		String tenLoai = txtTenLoai.getText().trim();
		
		if(maLoai.isEmpty() || tenLoai.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		ProductType newType = new ProductType(maLoai, tenLoai);
	
		boolean check = tDao.addPType(newType);
		if(check) {
			JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thành công");
			loadDataProductTypeTable();
			ProductPanel.loadDataProductType();
			HomePanel.loadDataProductTypeMain();
			
			ProductPanel.loadDataProduct();
			txtMaLoai.setText("");
			txtTenLoai.setText("");
			txtMaLoai.requestFocus();
			
		}else {
			JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thất bại");
			return;
		}
	}
	private void ExitAddType() {
		this.dispose();
	}
	public void loadDataProductTypeTable() {
		ProductTypeDAO ptd = new ProductTypeDAO();
		ArrayList<ProductType> listPTD = ptd.getProductType();
		tableModel.setRowCount(0);
		
		for(ProductType pt : listPTD) {
			Object[] row = {
					pt.getMaLoai(),
					pt.getTenLoai()
			};
			tableModel.addRow(row);
		}
	}
	public void chon(int row) {
		txtMaLoai.setText(tableModel.getValueAt(row, 0).toString());
		txtTenLoai.setText(tableModel.getValueAt(row, 1).toString());
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int sel = table.getSelectedRow();
		chon(sel);
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
