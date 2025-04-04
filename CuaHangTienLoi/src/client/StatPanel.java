package client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import dao.BillDetailsDAO;
import dao.CustomerDAO;
import static dao.BillDetailsDAO.getSalesReport;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.border.LineBorder;

public class StatPanel extends JPanel implements ActionListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private Properties dateProperties; // Renamed properties
    private JDatePickerImpl startDatePicker; // Renamed picker
    private JDatePickerImpl endDatePicker; // Renamed picker
	private JTable table;
	private DefaultTableModel modelDoanhThu;
	private JTable tableDoanhThu;
	private Container statisticsChartPanel;
	private JScrollPane scr;
	private JButton btnNewButton;
	private JComboBox<String> statisticsComboBox;

	private JLabel lblTongDoanhThu;
	private JLabel lab_tongTien;

    
    public StatPanel() {
    	setLayout(null);
    	initComponent();
    // Call method to create initial chart
    }

    public void initComponent() {
        // Main Content Area
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        mainPanel.setBackground(new Color(240, 255, 255));
        mainPanel.setBounds(0, 0, 1650, 1067);
        mainPanel.setLayout(null);
        add(mainPanel);

        JLabel lblTitle = new JLabel("Chọn loại thống kê");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        lblTitle.setBounds(34, 8, 301, 63);
        mainPanel.add(lblTitle);

        // Khởi tạo date pickers
        UtilDateModel startDateModel = new UtilDateModel();
        dateProperties = new Properties();
        dateProperties.put("text.today", "Today");
        dateProperties.put("text.month", "Month");
        dateProperties.put("text.year", "Year");
        
        Calendar calendar = Calendar.getInstance();
        startDateModel.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        startDateModel.setSelected(true); // Chọn ngày hiện tại làm ngày mặc định

        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, dateProperties);
        startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        startDatePicker.getJFormattedTextField().setBackground(new Color(255, 255, 255));
        startDatePicker.setBackground(new Color(240, 255, 255));
        startDatePicker.setBounds(128, 136, 207, 49);
        mainPanel.add(startDatePicker);

        // Khởi tạo model cho endDatePicker với ngày hiện tại
        UtilDateModel endDateModel = new UtilDateModel();
        endDateModel.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        endDateModel.setSelected(true); // Chọn ngày hiện tại làm ngày mặc định

        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, dateProperties);
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
        endDatePicker.getJFormattedTextField().setBackground(new Color(255, 255, 255));
        endDatePicker.setBackground(new Color(240, 255, 255));
        endDatePicker.setBounds(540, 136, 196, 46);
        mainPanel.add(endDatePicker);
        
        // Khởi tạo panel cho biểu đồ thống kê
        statisticsChartPanel = new JPanel(); // Khởi tạo statisticsChartPanel
        statisticsChartPanel.setBackground(new Color(240, 255, 255));
        statisticsChartPanel.setBounds(33, 560, 1589, 620); // Đặt kích thước cho panel
        mainPanel.add(statisticsChartPanel);
        
        JLabel lblFromDate = new JLabel("Từ ngày:");
        lblFromDate.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblFromDate.setBounds(128, 94, 109, 31);
        mainPanel.add(lblFromDate);
        
        JLabel lblToDate = new JLabel("Tới ngày:");
        lblToDate.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblToDate.setBounds(540, 91, 148, 37);
        mainPanel.add(lblToDate);
        
        btnNewButton = new JButton("Thống kê");
        btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.setBounds(834, 136, 140, 37);
        mainPanel.add(btnNewButton);
        
        statisticsComboBox = new JComboBox<>();
        statisticsComboBox.setBounds(311, 18, 425, 49);
        statisticsComboBox.addItem("Thống kê doanh thu");
        statisticsComboBox.addItem("Thống kê khách hàng");
        statisticsComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        mainPanel.add(statisticsComboBox);
        
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        table.setBounds(478, 235, 1, 1);
        mainPanel.add(table);
        
        tableDoanhThu = new JTable(modelDoanhThu);
        tableDoanhThu.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 20));
        tableDoanhThu.setRowHeight(50);
        scr = new JScrollPane(tableDoanhThu);
        mainPanel.add(scr);
        scr.setBounds(33, 264, 1589, 273);
        tableDoanhThu.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        
        String [] cotTable1 = {"Ngày", "Danh mục sản phẩm", "Số lượng bán","Tổng số lượng bán"};
        String[] cotTable2 = {"Ngày", "Số lượng khách hàng", "Số tiền"};
        modelDoanhThu = new DefaultTableModel(cotTable1, 0);
        modelDoanhThu = new DefaultTableModel(cotTable2, 0);
        
        try {
			
			java.util.Date startDateUtil = (java.util.Date) startDatePicker.getModel().getValue();
			java.util.Date endDateUtil = (java.util.Date) endDatePicker.getModel().getValue();
			java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());
			java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());
			List<Object[]> ds1 = getSalesReport(startDate,endDate);
			modelDoanhThu.setRowCount(0);

			for (Object[] record : ds1) {
	            // Tạo một mảng Object đại diện cho một hàng dữ liệu
	            Object[] row = new Object[4];
	            row[0] = record[0]; // Ngày
	            row[1] = record[1]; // Danh mục sản phẩm
	            row[2] = record[2]; // Số lượng bán
	            row[3] = record[3]; // Doanh thu

	            modelDoanhThu.addRow(row);
	        }
	        tableDoanhThu.setModel(modelDoanhThu);
	        
	        lblTongDoanhThu = new JLabel("Tổng doanh thu:");
	        lblTongDoanhThu.setHorizontalAlignment(SwingConstants.LEFT);
	        lblTongDoanhThu.setForeground(new Color(0, 0, 0));
	        lblTongDoanhThu.setFont(new Font("Tahoma", Font.PLAIN, 20));
	        lblTongDoanhThu.setBounds(34, 229, 286, 30);
	        mainPanel.add(lblTongDoanhThu);
	        
	        lab_tongTien = new JLabel("0");
	        lab_tongTien.setForeground(new Color(0, 0, 0));
	        lab_tongTien.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	        lab_tongTien.setBounds(320, 231, 223, 30);
	        mainPanel.add(lab_tongTien);
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        createRevenueChart(); 

        btnNewButton.addActionListener(this);
        tableDoanhThu.addMouseListener(this);
        statisticsComboBox.addActionListener(this);

        }
    
    private void createRevenueChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart("Biểu đồ doanh thu", "Ngày", "Mức doanh thu", dataset);
        ChartPanel chartPanelComponent = new ChartPanel(chart);
        chartPanelComponent.setBackground(new Color(255, 255, 255));
        chartPanelComponent.setForeground(new Color(255, 255, 255));
        chartPanelComponent.setBounds(0, 11, 1589, 417);
        statisticsChartPanel.removeAll();
        statisticsChartPanel.setLayout(null);
        statisticsChartPanel.add(chartPanelComponent);
        statisticsChartPanel.revalidate();
        statisticsChartPanel.repaint();
    }

    
    // Date Formatter
    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String datePattern = "yyyy-MM-dd";
        private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
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

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	  	if (e.getSource() == btnNewButton) {
	  		java.util.Date startDate = (java.util.Date) startDatePicker.getModel().getValue();
	        java.util.Date endDate = (java.util.Date) endDatePicker.getModel().getValue();

		  	if((startDate.after(endDate))) {
		  		JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày bắt đâu sau ngày kết thúc");
		  	}else if ((startDate != null && endDate != null)) {
		            if (statisticsComboBox.getSelectedIndex() == 0) {
		            	updateTableDoanhThu(startDate, endDate);
		            	updateBieuDoDoanhThu(startDate, endDate);
		            } else if (statisticsComboBox.getSelectedIndex() == 1) {
		               loadCustomerStatistics(startDate, endDate);
		               updateCustomerBieuDo(startDate, endDate);
		            }
		    }
	        else {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu và kết thúc!");
	        }		   
	  	}

		    // ComboBox selection change event
		    if (e.getSource() == statisticsComboBox) {
		    	java.util.Date startDate = (java.util.Date) startDatePicker.getModel().getValue();
		        java.util.Date endDate = (java.util.Date) endDatePicker.getModel().getValue();
		        if (statisticsComboBox.getSelectedIndex() == 0) {
		            // Update UI components for revenue statistics
		        	updateTableDoanhThu(startDate, endDate);
		        	updateBieuDoDoanhThu(startDate, endDate);
		        } else if (statisticsComboBox.getSelectedIndex() == 1) {
		            // Update UI components for customer statistics
		        	loadCustomerStatistics(startDate, endDate);
		        	updateCustomerBieuDo(startDate, endDate);
		        }
		    } 
	}
		 
	private void updateCustomerBieuDo(java.util.Date startDate, java.util.Date endDate) {
		 // Lấy dữ liệu từ DAO
	    List<Object[]> danhSachTongSLKhachHang;
	    try {
	        danhSachTongSLKhachHang = CustomerDAO.tongKHTheoNgay(startDate, endDate);
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        int tongKH = 0; 

	        for (Object[] data : danhSachTongSLKhachHang) {
	            Date ngayLap = (Date) data[0];
	            int tongKhachHang = (int) data[1];  
	            tongKH += tongKhachHang; 
	            
	            String dateString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(ngayLap);
	            dataset.addValue(tongKhachHang, "Khách hàng", dateString);
	        }
	        lblTongDoanhThu.setText("Tổng số lượng khách hàng:");
	        lab_tongTien.setText(String.valueOf(tongKH));

	        JFreeChart chart = ChartFactory.createBarChart(
	                "Biểu đồ khách hàng",    // Tiêu đề
	                "Ngày",                  // Trục X
	                "Tổng khách hàng",       // Trục Y
	                dataset                  // Dữ liệu
	        );

	        ChartPanel chartPanelComponent = new ChartPanel(chart);
	        chartPanelComponent.setBounds(0, 11, 1613, 432);

	        statisticsChartPanel.removeAll();   
	        statisticsChartPanel.add(chartPanelComponent);   
	        statisticsChartPanel.revalidate();   
	        statisticsChartPanel.repaint();      
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private void loadCustomerStatistics(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		 try {
		        java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());
		        java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());

		        List<Object[]> customerStats = CustomerDAO.tongKHTheoNgay(startDateSql, endDateSql);
		        
		        
		        String [] cotTable = {"Ngày", "Số lượng khách hàng", "Tổng tiền"};
		        modelDoanhThu = new DefaultTableModel(cotTable, 0);
		        modelDoanhThu.setRowCount(0);

		        for (Object[] record : customerStats) {
		        	if (record.length == 3) {
		            Object[] row = new Object[3];
		            row[0] = record[0];
		            row[1] = record[1]; 
		            row[2] = record[2];  
		            System.out.println(row.toString());
		            modelDoanhThu.addRow(row);
		        }
		    }
		        tableDoanhThu.setModel(modelDoanhThu);

		    } catch (SQLException e) {
		        e.printStackTrace();  // Handle SQL exceptions
		    }
	}

	private void updateBieuDoDoanhThu(java.util.Date startDate, java.util.Date endDate) {
        // Lấy dữ liệu từ DAO
        List<Object> danhSachTongTien;
		try {
			danhSachTongTien = BillDetailsDAO.tongTienTheoNgay(startDate, endDate);
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			int tongtien = 0;
	        for (Object obj : danhSachTongTien) {
	            Object[] data = (Object[]) obj;
	            Date ngayLap = (Date) data[0];
	            float tongTien = (float) data[1];
	            tongtien += tongTien;
	            String dateString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(ngayLap);
	            dataset.addValue(tongTien, "Doanh thu", dateString);
	        }
	        lblTongDoanhThu.setText("Tổng doanh thu:");
	        lab_tongTien.setText(String.valueOf(tongtien)+" VND");


	        JFreeChart chart = ChartFactory.createBarChart(
	                "Biểu đồ doanh thu",    // Tiêu đề
	                "Ngày",                  // Trục X
	                "Mức doanh thu",         // Trục Y
	                dataset                  // Dữ liệu
	        );

	        // Tạo và cấu hình ChartPanel để hiển thị biểu đồ
	        ChartPanel chartPanelComponent = new ChartPanel(chart);
	        chartPanelComponent.setBounds(0, 11, 1613, 432);


	        statisticsChartPanel.removeAll();   // Xóa biểu đồ cũ
	        statisticsChartPanel.add(chartPanelComponent);   // Thêm biểu đồ mới
	        statisticsChartPanel.revalidate();   // Đảm bảo JPanel được làm mới
	        statisticsChartPanel.repaint();      // Vẽ lại JPanel
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Tạo một dataset cho biểu đồ
        
    }
	public void updateTableDoanhThu(java.util.Date startDateUtil, java.util.Date endDateUtil) {
	    try {
	        // Chuyển đổi java.util.Date sang java.sql.Date
	        java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());
	        java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());
	        String [] cotTable1 = {"Ngày", "Danh mục sản phẩm", "Số lượng bán","Tổng số lượng bán"};
	        modelDoanhThu = new DefaultTableModel(cotTable1, 0);
	        tableDoanhThu.setModel(modelDoanhThu);
			// Lấy dữ liệu từ cơ sở dữ liệu trong khoảng thời gian đã chọn
	        List<Object[]> ds1 = BillDetailsDAO.getSalesReport(startDate, endDate);

	        // Xóa các hàng cũ trong model trước khi thêm dữ liệu mới
	        modelDoanhThu.setRowCount(0);

	        // Thêm từng bản ghi từ danh sách vào modelDoanhThu
	        for (Object[] record : ds1) {
	            modelDoanhThu.addRow(new Object[]{record[0], record[1], record[2], record[3]});
	        }
	        
	        // Cập nhật lại bảng tự động khi model thay đổi
	        tableDoanhThu.repaint();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
