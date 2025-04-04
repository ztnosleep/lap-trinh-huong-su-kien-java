package client;

//import java.awt.EventQueue;




import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.CardLayout;

import javax.swing.border.LineBorder;

import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

    JFrame frame;
    private JPanel mainContentPanel;
    private HomePanel homePanel;
    private ProductPanel productPanel;
    private AccountPanel accountPanel;
   // private AccountPanel accountPanel;
    private CustomerPanel memberPanel;
	private BillPanel billPanel;
	private StatPanel statPanel;
	private EmployeePanel employeePanel;
	static JButton btnNV;

    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Main window = new Main();
//                    window.frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
        accountPanel = new AccountPanel();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(248, 248, 255));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("./img/icon.png"));
        frame.setBounds(100, 100, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        JPanel menu = new JPanel();
        menu.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        menu.setBackground(new Color(240, 255, 255));
        menu.setBounds(0, 220, 240, 1181);
        frame.getContentPane().add(menu);
        menu.setLayout(null);

        JButton btnHomePage = new JButton("Trang chủ");
        btnHomePage.setIcon(new ImageIcon("./img/homepage.png"));
        btnHomePage.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnHomePage.setBackground(Color.WHITE);
        btnHomePage.setBounds(0, 0, 240, 66);
        menu.add(btnHomePage);

        JButton btnProd = new JButton("Sản phẩm");
        btnProd.setIcon(new ImageIcon("./img/product.png"));
        btnProd.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnProd.setBackground(Color.WHITE);
        btnProd.setBounds(0, 72, 240, 66);
        menu.add(btnProd);

        JButton btnStat = new JButton("Hóa đơn");
        btnStat.setIcon(new ImageIcon("./img/bill.png"));
        btnStat.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnStat.setBackground(Color.WHITE);
        btnStat.setBounds(0, 144, 240, 66);
        menu.add(btnStat);

        JButton btnSupport = new JButton("Thống kê");
        btnSupport.setIcon(new ImageIcon("./img/stat.png"));
        btnSupport.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnSupport.setBackground(Color.WHITE);
        btnSupport.setBounds(0, 216, 240, 66);
        menu.add(btnSupport);

        JButton btnDKTV = new JButton("Khách hàng");
        btnDKTV.setIcon(new ImageIcon("./img/member.png"));
        btnDKTV.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnDKTV.setBackground(Color.WHITE);
        btnDKTV.setBounds(0, 288, 240, 66);
        menu.add(btnDKTV);
        
        btnNV = new JButton("Nhân viên");
        btnNV.setIcon(new ImageIcon("./img/staff.png"));
        btnNV.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnNV.setBackground(Color.WHITE);
        btnNV.setBounds(0, 360, 240, 66);
        menu.add(btnNV);

        JButton btnAccount = new JButton("Tài khoản");
        btnAccount.setIcon(new ImageIcon("./img/account.png"));
        btnAccount.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnAccount.setBackground(Color.WHITE);
        btnAccount.setBounds(0, 432, 240, 66);
        menu.add(btnAccount);

        JLabel lblNewLabel = new JLabel("NHÓM 10");
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblNewLabel.setBounds(58, 654, 132, 85);
        menu.add(lblNewLabel);
        
        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setIcon(new ImageIcon("./img/logout.png"));
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setBounds(0, 504, 240, 66);
        menu.add(btnLogout);

        JLabel lblNewLabel_1 = new JLabel("Ten Station");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
        lblNewLabel_1.setBounds(72, 30, 114, 43);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("10");
        lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNewLabel_2.setBounds(72, 84, 49, 22);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel logo = new JLabel("");
        logo.setIcon(new ImageIcon("./img/logo.jpg"));
        logo.setBounds(0, 0, 240, 220);
        frame.getContentPane().add(logo);

        // Main content panel with CardLayout
        mainContentPanel = new JPanel(new CardLayout());
        mainContentPanel.setBounds(250, 0, 1644, 1080);
        frame.getContentPane().add(mainContentPanel);

        // Initializing the different panels
        homePanel = new HomePanel();
        productPanel = new ProductPanel();
        billPanel = new BillPanel();
        statPanel = new StatPanel();
        employeePanel = new EmployeePanel();
        memberPanel = new CustomerPanel();

        
        
        

        // Adding panels to the CardLayout
        mainContentPanel.add(homePanel, "Trang chủ");
        mainContentPanel.add(productPanel, "Sản phẩm");
        mainContentPanel.add(billPanel, "Hóa đơn");
        mainContentPanel.add(statPanel, "Thống kê");
        mainContentPanel.add(employeePanel, "Nhân viên");
        mainContentPanel.add(memberPanel, "Khách hàng");

        // Adding action listeners to buttons
        btnHomePage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Trang chủ");
            }
        });

        btnProd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Sản phẩm");
            }
        });

        btnStat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Hóa đơn");
            }
        });

        btnSupport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Hướng dẫn");
            }
        });

        btnDKTV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Khách hàng");
            }
        });
        btnNV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("Nhân viên");
            }
        });
        
        btnSupport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switchPanel("Thống kê");
			}
		});

        btnAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AccountPanel a = new AccountPanel();
                a.setVisible(true);
            }
        });
        btnLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int sl = JOptionPane.showConfirmDialog(mainContentPanel, "Bạn có muốn đăng xuất?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
				if (sl == JOptionPane.YES_OPTION) {
		            frame.dispose(); // Đóng cửa sổ Main hiện tại
		            // Mở lại Form_Login
		            SwingUtilities.invokeLater(() -> {
		                Login formlogin = new Login(); 
		                formlogin.setVisible(true); 
		            });
		        }
				
			}
		});
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) (mainContentPanel.getLayout());
        cl.show(mainContentPanel, panelName);
    }

}
