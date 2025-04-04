package client;

import dao.AccountDAO;
import dao.EmployeeDAO;

import entity.Account;
import entity.AccountCurrent;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.prefs.Preferences;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
public class Login extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  JButton btnDangNhap;
    private  JLabel disable;
    private  JLabel jLabel1;
    private  JLabel jLabel2;
    private  JLabel jLabel6;
    private  JLabel jLabel7;
    private  JLabel jLabel8;
    private  JPanel jPanel2;
    private  JLabel lbExit;
    private  JLabel lbIcon;
    private  JLabel lbPassWord01;
    private  JLabel lbPassword02;
    private  JLabel lbQuenMatKhau;
    private  JLabel lbTieuDe01;
    private  JLabel lbUserName01;
    private  JLabel lbUserName2;
    private  JLabel show;
    private  JPasswordField txtPassWord;
    private  JTextField txtUserName;
    public Login() {
        // TODO Auto-generated constructor stub
    	initComponent();
    	
    }
    public void initComponent() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("./img/icon.png"));
        setUndecorated(true);
        setSize(800,500);
        setLocationRelativeTo(null);

        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel2.setBounds(500, 0, 560, 500);
        lbTieuDe01 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbExit = new javax.swing.JLabel();
        lbUserName01 = new javax.swing.JLabel();
        lbIcon = new javax.swing.JLabel();
        lbPassWord01 = new javax.swing.JLabel();
        lbUserName2 = new javax.swing.JLabel();
        disable = new javax.swing.JLabel();
        show = new javax.swing.JLabel();
        lbQuenMatKhau = new javax.swing.JLabel();
        btnDangNhap = new javax.swing.JButton();
        txtUserName = new javax.swing.JTextField();
        lbPassword02 = new javax.swing.JLabel();
        txtPassWord = new javax.swing.JPasswordField();

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tên tài khoản:");

        jLabel7.setIcon(new javax.swing.ImageIcon("./img/icons8_user_20px_1.png")); // NOI18N

        jLabel8.setText("_______________________________________________________");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(500, 500));
        getContentPane().setLayout(null);

        jPanel2.setBackground(new Color(0, 204, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTieuDe01.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        lbTieuDe01.setForeground(new java.awt.Color(255, 255, 255));
        lbTieuDe01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTieuDe01.setText("ĐĂNG NHẬP");
        lbTieuDe01.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(lbTieuDe01, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 500, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Chào mừng đến với cửa hàng tiện lợi Ten Station!!!");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 70, 500, -1));

        lbExit.setIcon(new javax.swing.ImageIcon("./img/close.png")); // NOI18N
        lbExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbExitMouseClicked(evt);
            }
        });
        jPanel2.add(lbExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));

        lbUserName01.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbUserName01.setForeground(new java.awt.Color(255, 255, 255));
        lbUserName01.setText("Tên đăng nhập");
        jPanel2.add(lbUserName01, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 360, -1));

        lbIcon.setForeground(new java.awt.Color(255, 255, 255));
        lbIcon.setIcon(new javax.swing.ImageIcon("./img/icons8_user_20px_1.png")); // NOI18N
        lbIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(lbIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, -1, 36));

        lbPassWord01.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbPassWord01.setForeground(new java.awt.Color(255, 255, 255));
        lbPassWord01.setText("Mật khẩu");
        jPanel2.add(lbPassWord01, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 360, -1));

        //lbUserName2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbUserName2.setForeground(new java.awt.Color(255, 255, 255));
        lbUserName2.setText("________________________________________________________");
        jPanel2.add(lbUserName2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 378, -1));

        disable.setIcon(new javax.swing.ImageIcon("./img/icons8_invisible_20px_1.png")); // NOI18N
        disable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        disable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disableMouseClicked(evt);
            }
        });
        jPanel2.add(disable, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 260, 30, -1));

        show.setIcon(new javax.swing.ImageIcon("./img/icons8_eye_20px_1.png")); // NOI18N
        show.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMouseClicked(evt);
            }
        });
        jPanel2.add(show, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 260, 31, -1));

        lbQuenMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbQuenMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        lbQuenMatKhau.setText("Quên mật khẩu?");
        lbQuenMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(lbQuenMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 190, -1));

        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(52, 152, 219));
        btnDangNhap.setBackground(Color.white);
        btnDangNhap.setText("ĐĂNG NHẬP");
        btnDangNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnDangNhapActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        jPanel2.add(btnDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 405, 47));

        txtUserName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtUserName.setForeground(Color.black);
        txtUserName.setBorder(null);
        
        jPanel2.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 360, 30));

        lbPassword02.setForeground(new java.awt.Color(255, 255, 255));
        lbPassword02.setText("________________________________________________________");
        jPanel2.add(lbPassword02, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 378, -1));

        txtPassWord.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtPassWord.setForeground(Color.black);
        txtPassWord.setBorder(null);
        jPanel2.add(txtPassWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 360, 30));

        getContentPane().add(jPanel2);
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setBounds(0, 0, 560, 500);
        getContentPane().add(jLabel1);
        
                jLabel1.setIcon(new ImageIcon("C:\\img\\logologin.jpg")); // NOI18N
                jLabel1.setPreferredSize(new java.awt.Dimension(400, 400));

        setSize(new java.awt.Dimension(1058, 496));
        setLocationRelativeTo(null);
        
        txtUserName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtPassWord.requestFocus();
			}
		});
        txtPassWord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					btnDangNhapActionPerformed(e);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
    }
    public static void main(String[] args) {
        new Login().setVisible(true);
    }

    private void lbExitMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }
    private void showMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showMouseClicked
        this.txtPassWord.setEchoChar((char) 8226);
        this.disable.setVisible(true);
        this.disable.setEnabled(true);
        this.show.setVisible(false);
        this.show.setEnabled(false);
    }
    private void disableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disableMouseClicked
        this.txtPassWord.setEchoChar((char) 0);
        this.disable.setVisible(false);
        this.disable.setEnabled(false);
        this.show.setVisible(true);
        this.show.setEnabled(true);
    }
    public Preferences pref = Preferences.userRoot().node("Rememberme");
    public void saveAccount(String userName, String password) {
        if (userName != null || password != null) {
            pref.put("userName", userName);
            pref.put("password", password);
        }
    }
    private boolean isEmployeeActive(String userName) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        return employeeDAO.isEmployeeActive(userName); // Giả sử userName là mã nhân viên
    }
   
	private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {//GEN-FIRST:event_btnDangNhapActionPerformed
    	

        AccountDAO daoTK = new AccountDAO();
        String userName = txtUserName.getText();
		String pwd = txtPassWord.getText();
        Account tk = daoTK.getAllTaiKhoan(userName, pwd);
       
        
        if(tk != null && isEmployeeActive(userName)) {
        	if(tk.isRole()) {
        		AccountCurrent.setCurrentAccount(tk);
            	this.dispose();
            	
            	Main window = new Main();
                window.frame.setVisible(true);
        	}
        	else {
        		AccountCurrent.setCurrentAccount(tk);
            	this.dispose();
            	
            	Main window = new Main();
                window.frame.setVisible(true);
                Main.btnNV.setEnabled(false);
        	}
            
        }
        else if (tk == null) {
            JOptionPane.showMessageDialog(this, "Nhập sai tài khoản hoặc mật khẩu");
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản này đã nghỉ làm và không thể đăng nhập.");
        }
    }
    
}
