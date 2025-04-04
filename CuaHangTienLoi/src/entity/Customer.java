package entity;

import java.sql.Date;
import java.util.Objects;

public class Customer {
	private String maKh;
	private String soDienThoai;
	private String tenKh, diaChi;
	private Date ngaySinh;
	private boolean gioiTinh;
	private int diemTichLuy;
	
	@Override
	public String toString() {
		return "Customer [maKh=" + maKh + ", soDienThoai=" + soDienThoai + ", tenKh=" + tenKh + ", diaChi=" + diaChi
				+ ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", diemTichLuy=" + diemTichLuy + "]";
	}
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(String maKh, String soDienThoai, String tenKh, String diaChi, Date ngaySinh, boolean gioiTinh,
			int diemTichLuy) {
		super();
		this.maKh = maKh;
		this.soDienThoai = soDienThoai;
		this.tenKh = tenKh;
		this.diaChi = diaChi;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diemTichLuy = diemTichLuy;
	}
	public String getMaKh() {
		return maKh;
	}
	public void setMaKh(String maKh) {
		this.maKh = maKh;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public String getTenKh() {
		return tenKh;
	}
	public void setTenKh(String tenKh) {
		this.tenKh = tenKh;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public int getDiemTichLuy() {
		return diemTichLuy;
	}
	public void setDiemTichLuy(int diemTichLuy) {
		this.diemTichLuy = diemTichLuy;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKh);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(maKh, other.maKh);
	}
	public String getGioiTinhHienThi() {
		return gioiTinh ? "Nam" : "Nữ";
	}
	
}

