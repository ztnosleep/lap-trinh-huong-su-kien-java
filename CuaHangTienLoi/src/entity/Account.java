package entity;

import java.util.Objects;

public class Account {
	 private String maTK;
	 private String matKhau;
	 private boolean role;
	 
	public boolean isRole() {
		return role;
	}
	public void setRole(boolean role) {
		this.role = role;
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Account(String maTK, String matKhau, boolean role) {
		super();
		this.maTK = maTK;
		this.matKhau = matKhau;
		this.role = role;
	}
	public Account(String maTK) {
		super();
		this.maTK = maTK;
	}
	public String getMaTK() {
		return maTK;
	}
	public void setMaTK(String maTK) {
		this.maTK = maTK;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maTK);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(maTK, other.maTK);
	}
	@Override
	public String toString() {
		return "Account [maTK=" + maTK + ", matKhau=" + matKhau + ", role=" + role + "]";
	}
	
}
