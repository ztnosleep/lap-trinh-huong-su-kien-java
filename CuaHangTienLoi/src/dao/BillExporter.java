package dao;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillExporter {
    public static void exportBill(String maHD, String maKH, String tenKH, String maNV, double amount, String paymentMethod,
                                   double amountPaid, String[][] chiTietSP) {
        double change = (amountPaid - amount) - (amountPaid - amount) % 1000; // Calculate change
        DecimalFormat df = new DecimalFormat("#,###.##"); // Format for currency

        int width = 880; // Increased width
        int height = 600 + (chiTietSP.length * 30) + 120; // Increased height to accommodate more content
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Background and font setup
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);

        // Store name
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Ten Station", 350, 40);

        // Set font for the rest of the bill
        g2d.setFont(new Font("Arial", Font.BOLD, 16));

        // Drawing the bill with bold titles
        g2d.drawString("HÓA ĐƠN", 370, 80);

        // Adjust the position of the information
        int y = 120;
        g2d.drawString("Mã hóa đơn:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(maHD, 180, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Mã khách hàng:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        String maKhach = (maKH == null) ? "Khách vãng lai" : maKH;
        g2d.drawString(maKhach, 180, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Khách hàng:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        String tenKhach = (tenKH == null) ? "Khách vãng lai" : tenKH;
        g2d.drawString(tenKhach, 180, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Nhân viên:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(maNV, 180, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Ngày lập:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 180, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Tổng tiền:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(df.format(amount) + " VNĐ", 180, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Phương thức thanh toán:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(paymentMethod, 250, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Khách trả:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(df.format((int) amountPaid) + " VNĐ", 180, y);
        y += 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Tiền thối:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(df.format(change) + " VNĐ", 180, y);
        y += 60;

        // Product details table
        int startX = 50;
        int startY = y;
        int rowHeight = 30;
        int columnWidth = 180; // Adjusted column width for better spacing

        // Header of the table
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Mã sản phẩm", startX, startY);
        g2d.drawString("Tên sản phẩm", startX + columnWidth, startY);
        g2d.drawString("Số lượng", startX + 2 * columnWidth, startY);
        g2d.drawString("Đơn giá", startX + 3 * columnWidth, startY);
        g2d.drawString("Thành tiền", startX + 4 * columnWidth, startY);

        // Drawing table border
        g2d.drawLine(startX, startY + 5, startX + 5 * columnWidth, startY + 5);

        // Drawing product data rows
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        for (int i = 0; i < chiTietSP.length; i++) {
            String[] detail = chiTietSP[i];
            double quantity = Double.parseDouble(detail[2]); // Assuming the quantity is in the 3rd column
            double unitPrice = Double.parseDouble(detail[3]); // Assuming the unit price is in the 4th column
            double totalPrice = quantity * unitPrice; // Calculate total price

            g2d.drawString(detail[0], startX, startY + (i + 1) * rowHeight);
            g2d.drawString(detail[1], startX + columnWidth, startY + (i + 1) * rowHeight);
            g2d.drawString(String.valueOf(quantity), startX + 2 * columnWidth, startY + (i + 1) * rowHeight);
            g2d.drawString(df.format(unitPrice) + " VNĐ", startX + 3 * columnWidth, startY + (i + 1) * rowHeight);
            g2d.drawString(df.format(totalPrice) + " VNĐ", startX + 4 * columnWidth, startY + (i + 1) * rowHeight);
        }

        g2d.drawString("Cảm ơn quý khách!", 330, startY + (chiTietSP.length + 2) * rowHeight);

        g2d.dispose();

        // Exporting the image
        try {
            ImageIO.write(bufferedImage, "png", new File("./bill/hoadon_" + maHD + ".png"));
            System.out.println("Hóa đơn đã được xuất thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xuất hóa đơn.");
        }
    }
}