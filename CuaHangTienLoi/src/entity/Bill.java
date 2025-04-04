package entity;

import java.sql.Date;
import java.util.Objects;

public class Bill {
	private String maHD, maKH, maNV;
	
	private Date ngayLap;
	private double tongtien;
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public Date getNgayLap() {
		return ngayLap;
	}
	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}
	public double getTongtien() {
		return tongtien;
	}
	public void setTongtien(double tongtien) {
		this.tongtien = tongtien;
	}
	
	public Bill(String maHD, String maKH, String maNV, Date ngayLap, double tongtien) {
		super();
		this.maHD = maHD;
		this.maKH = maKH;
		this.maNV = maNV;
		this.ngayLap = ngayLap;
		this.tongtien = tongtien;
	}
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Bill [maHD=" + maHD + ", maKH=" + maKH + ", maNV=" + maNV +  ", ngayLap=" + ngayLap
				+ ", tongtien=" + tongtien + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maHD);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		return Objects.equals(maHD, other.maHD);
	}
	
}
