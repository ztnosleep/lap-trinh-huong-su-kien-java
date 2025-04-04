package entity;

import java.util.Date;
import java.util.Objects;

public class Product {
	private String maSP;
	private String tenSP;
	private double donGia;
	private Date ngaySX;
	private Date hanSD;
	private String donViTinh;
	private String moTa;
	private int soLuongTon;
	private String xuatXu;
	private String maLoai;
	private byte[] image;
	

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public Product(String maSP, String tenSP, double donGia, Date ngaySX, Date hanSD, String donViTinh, String moTa,
//			int soLuongTon, String xuatXu, String maLoai) {
//		super();
//		this.maSP = maSP;
//		this.tenSP = tenSP;
//		this.donGia = donGia;
//		this.ngaySX = ngaySX;
//		this.hanSD = hanSD;
//		this.donViTinh = donViTinh;
//		this.moTa = moTa;
//		this.soLuongTon = soLuongTon;
//		this.xuatXu = xuatXu;
//		this.maLoai = maLoai;
//		//this.imagePath = imagePath;
//	}
	

	public Product(String maSP, String tenSP, double donGia, Date ngaySX, Date hanSD, String donViTinh, String moTa,
			int soLuongTon, String xuatXu, String maLoai, byte[] image) {
		super();
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.donGia = donGia;
		this.ngaySX = ngaySX;
		this.hanSD = hanSD;
		this.donViTinh = donViTinh;
		this.moTa = moTa;
		this.soLuongTon = soLuongTon;
		this.xuatXu = xuatXu;
		this.maLoai = maLoai;
		this.image = image;
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public Date getNgaySX() {
		return ngaySX;
	}

	public void setNgaySX(Date ngaySX) {
		this.ngaySX = ngaySX;
	}

	public Date getHanSD() {
		return hanSD;
	}

	public void setHanSD(Date hanSD) {
		this.hanSD = hanSD;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	public String getXuatXu() {
		return xuatXu;
	}

	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}

	public String getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}

	
	public boolean reduceQuantity(int quantity) {
	    if (quantity <= soLuongTon) {
	        soLuongTon -= quantity;
	        return true; // Giảm số lượng thành công
	    } else {
	        return false; // Không đủ số lượng
	    }
	}
	@Override
	public String toString() {
		return "Product [maSP=" + maSP + ", tenSP=" + tenSP + ", donGia=" + donGia + ", ngaySX=" + ngaySX + ", hanSD="
				+ hanSD + ", donViTinh=" + donViTinh + ", moTa=" + moTa + ", soLuongTon=" + soLuongTon + ", xuatXu="
				+ xuatXu + ", maLoai=" + maLoai + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maSP);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(maSP, other.maSP);
	}
	
	
}