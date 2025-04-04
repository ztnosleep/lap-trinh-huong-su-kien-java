
CREATE DATABASE CuaHangTienLoi_VF;
GO

USE CuaHangTienLoi_VF;
Go

CREATE TABLE TaiKhoan (
    TK VARCHAR(20) PRIMARY KEY,
    MK VARCHAR(30) NOT NULL,
	role smallint check (role in (0,1))
);


CREATE TABLE NhanVien (
    maNV varchar(20) PRIMARY KEY,
    tenNV NVARCHAR(100) NOT NULL,
    gioiTinh smallint check (gioiTinh in (0,1)) NOT NULL,  
    ngaySinh DATE NOT NULL,
    ngayVaoLam DATE NOT NULL,
    soDienThoai VARCHAR(10) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    diaChi NVARCHAR(200) NOT NULL,
    maTK varchar(20) NOT NULL,
	trangThai bit
    FOREIGN KEY (maTK) REFERENCES TaiKhoan(TK)
);


CREATE TABLE KhachHang (
    maKH varchar(20) PRIMARY KEY,
    soDienThoai VARCHAR(10) not null,
    tenKH NVARCHAR(100) NOT NULL,
	ngaySinh Date,
    gioiTinh smallint check (gioiTinh in (0,1)),
	diaChi nvarchar(100),
    diemTichLuy INT DEFAULT 0
);



CREATE TABLE LoaiSanPham (
    maLoai varchar(20) PRIMARY KEY,
    tenLoai NVARCHAR(100) NOT NULL
);


CREATE TABLE SanPham (
    maSP varchar(20) PRIMARY KEY,
    tenSP NVARCHAR(100) NOT NULL,
    donGia float NOT NULL,
    ngaySX DATE NOT NULL,
    hanSD DATE NOT NULL,
    donViTinh nvarchar(20) NOT NULL,
    moTa nvarchar(255) NOT NULL,            
    xuatXu NVARCHAR(100) NOT NULL,  
    soLuongTon INT NOT NULL,       
	anh varbinary(max),
    maLoai varchar(20) NOT NULL,
    FOREIGN KEY (maLoai) REFERENCES LoaiSanPham(maLoai) on delete cascade on update cascade
);

CREATE TABLE HoaDon (
    maHD varchar(20) PRIMARY KEY,
    ngayLap DATE not null,
    tongTien FLOAT not null,
    maNV varchar(20) not null,
    maKH varchar(20),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    FOREIGN KEY (maKH) REFERENCES KhachHang(maKH)
);

-- Bảng ChiTietHoaDon (không có cột thanhTien tính tự động)
CREATE TABLE ChiTietHoaDon (
    maHD varchar(20) not null,
    maSP varchar(20) not null,
	tenSP nvarchar(100) not null,
    soLuong INT not null,
    donGia FLOAT not null,
	ThanhTien AS (SoLuong * DonGia),
    PRIMARY KEY (maHD, maSP),
    FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
    FOREIGN KEY (maSP) REFERENCES SanPham(maSP)
);



-- Thêm dữ liệu vào bảng TaiKhoan
INSERT INTO TaiKhoan (TK, MK, role) VALUES
('admin', 'admin',1),
('TK01', '123456',0),
('TK02', '123456',0),
('TK03', '123456',0);


-- Thêm dữ liệu vào bảng NhanVien
INSERT INTO NhanVien (maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam, soDienThoai, email, diaChi, maTK,trangThai)
VALUES 
('NV01', N'Hồ Vĩnh Thái', 1, '2004-12-26', '2024-01-01', '0123456789', N'vinhthaia@gmail.com', N'Bình Phước', 'admin',1),
('NV02', N'Nguyễn Văn Hùng', 1, '2003-11-15', '2023-11-15', '0987654321', N'nguyenhung@gmail.com', N'Hồ Chí Minh', 'TK01', 1),
('NV03', N'Trần Thị Minh Châu', 0, '2002-08-10', '2022-06-01', '0935123456', N'chauminh@gmail.com', N'Hà Nội', 'TK02', 0),
('NV04', N'Phạm Quốc Bảo', 1, '1999-05-20', '2023-09-10', '0912345678', N'quocbao@gmail.com', N'Đà Nẵng', 'TK03', 1);



-- Thêm dữ liệu vào bảng KhachHang
INSERT INTO KhachHang (maKH, soDienThoai, tenKH, ngaySinh, gioiTinh, diemTichLuy, diaChi) VALUES
('KH01', '0123456789', N'LÊ VĂN CẢN', '1990-01-01', 1, 100, 'HCM'),
('KH02', '0987654321', N'HỒNG THỊ DUYÊN', '2000-09-09', 0, 200, 'BP'),
('KH03', '0912345678', N'NGUYỄN VĂN ANH', '1995-05-15', 1, 150, 'Hà Nội'),
('KH04', '0923456789', N'TRẦN THỊ BÍCH', '1992-12-22', 0, 250, 'Đà Nẵng'),
('KH05', '0934567890', N'PHẠM VĂN HẢI', '1988-03-03', 1, 300, 'HCM'),
('KH06', '0945678901', N'VŨ THỊ HƯƠNG', '1993-04-04', 0, 50, 'Cần Thơ'),
('KH07', '0956789012', N'ĐỖ VĂN QUANG', '1985-08-08', 1, 80, 'Hải Phòng'),
('KH08', '0967890123', N'NGÔ THỊ LAN', '1991-06-06', 0, 120, 'Nha Trang'),
('KH09', '0978901234', N'HOÀNG VĂN PHÚC', '1994-10-10', 1, 90, 'Biên Hòa'),
('KH10', '0989012345', N'ANH THỊ MAI', '1997-11-11', 0, 200, 'Vũng Tàu'),
('KH11', '0990123456', N'VŨ ĐÌNH LẬP', '1980-02-20', 1, 300, 'Hà Tĩnh'),
('KH12', '0901234567', N'TRẦN THỊ THẢO', '1986-07-07', 0, 400, 'Nam Định'),
('KH13', '0912345678', N'NGUYỄN VĂN TIẾN', '1982-09-09', 1, 350, 'Bắc Ninh'),
('KH14', '0923456789', N'PHẠM THỊ HÀ', '1996-03-03', 0, 150, 'Thái Bình'),
('KH15', '0934567890', N'ĐẶNG VĂN HƯNG', '1998-05-05', 1, 220, 'Hải Dương'),
('KH16', '0945678901', N'VŨ THỊ NGỌC', '1999-04-04', 0, 180, 'Hưng Yên'),
('KH17', '0956789012', N'NGÔ VĂN SƠN', '1987-08-08', 1, 200, 'Quảng Ninh'),
('KH18', '0967890123', N'TRẦN VĂN ĐẠT', '1989-11-11', 1, 130, 'Đà Lạt'),
('KH19', '0978901234', N'HOÀNG THỊ THÚY', '1990-06-06', 0, 170, 'Sóc Trăng'),
('KH20', '0989012345', N'LÊ VĂN HƯNG', '1984-12-12', 1, 300, 'Thành phố Hồ Chí Minh'),
('KH21', '0990123456', N'TRẦN THỊ LÊ', '1985-01-01', 0, 400, 'Bến Tre'),
('KH22', '0901234567', N'TÔN THỊ HÀ', '1992-02-02', 0, 90, 'Long An'),
('KH23', '0912345678', N'NGUYỄN VĂN KHANG', '1993-03-03', 1, 240, 'Khánh Hòa'),
('KH24', '0923456789', N'PHẠM THỊ BÍCH', '1991-04-04', 0, 160, 'Quảng Nam'),
('KH25', '0934567890', N'ĐỖ VĂN PHÚC', '1996-05-05', 1, 200, 'Ninh Bình'),
('KH26', '0945678901', N'VŨ THỊ HÀ', '1998-06-06', 0, 220, 'Hà Giang'),
('KH27', '0956789012', N'HOÀNG VĂN TIẾN', '1990-07-07', 1, 100, 'Lào Cai'),
('KH28', '0967890123', N'NGÔ THỊ ANH', '1994-08-08', 0, 180, 'Hà Nam'),
('KH29', '0978901234', N'TRẦN VĂN LONG', '1997-09-09', 1, 250, 'Hà Tĩnh'),
('KH30', '0989012345', N'VŨ THỊ DIỄM', '1995-10-10', 0, 200, 'Nam Định');

-- Thêm dữ liệu vào bảng LoaiSanPham

INSERT INTO LoaiSanPham (maLoai, tenLoai) VALUES 
('LSP01', N'Thực Phẩm'), 
('LSP02', N'Đồ Uống'), 
('LSP03', N'Rau'), 
('LSP04', N'Vệ Sinh Cá Nhân'), 
('LSP05', N'Hàng Tiêu Dùng'),
('LSP06', N'Dụng Cụ Học Tập'), 
('LSP07', N'Trái cây'), 
('LSP08', N'Đồ Gia Dụng'), 
('LSP09', N'Đồ Dùng Văn Phòng'), 
('LSP10', N'Hàng Nhập Khẩu');

-- Thêm dữ liệu vào bảng SanPham
--LSP01: thực phẩm


INSERT INTO [dbo].[SanPham] ([maSP], [tenSP], [donGia], [ngaySX], [hanSD], [donViTinh], [moTa], [xuatXu], [soLuongTon], [anh], [maLoai]) 
VALUES
('SP01', N'Bột gạo lứt', 50000, '2024-01-01', '2025-12-31', N'Hộp', N'Bột gạo lứt dinh dưỡng', N'Việt Nam', 100, 
    (SELECT * FROM OPENROWSET(BULK '/img/botgaolut.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP02', N'Gạo ST25', 120000, '2024-02-01', '2025-06-30', N'Túi', N'Gạo ST25 thơm ngon.', N'Việt Nam', 200, 
    (SELECT * FROM OPENROWSET(BULK '/img/gaost25.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP03', N'Mì Kokomi', 3000, '2024-03-01', '2025-05-15', N'Gói', N'Mì Kokomi.', N'Việt Nam', 150, 
    (SELECT * FROM OPENROWSET(BULK '/img/goimi.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP04', N'Gạo An An', 150000, '2024-01-15', '2025-01-15', N'Túi', N'Gạo thơm ngon', N'Thái Lan', 80, 
    (SELECT * FROM OPENROWSET(BULK '/img/gaoanan.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP05', N'Thùng mì Kokomi', 120000, '2024-01-10', '2025-01-10', N'Thùng', N'Thùng mì 30 gói', N'Việt Nam', 150, 
    (SELECT * FROM OPENROWSET(BULK '/img/thungmikokomi.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP06', N'Thùng mì Hảo Hảo', 120000, '2024-01-20', '2025-07-20', N'Thùng', N'Thùng mì 30 gói', N'Việt Nam', 80, 
    (SELECT * FROM OPENROWSET(BULK '/img/thungmihaohao.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP07', N'Xúc xích', 20000, '2024-03-05', '2025-09-05', N'Bịch', N'Xúc xích siêu ngon.', N'Na Uy', 40, 
    (SELECT * FROM OPENROWSET(BULK '/img/xucxich.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP08', N'Phở bò 3 miền', 9000, '2024-01-25', '2025-07-25', N'Gói', N'Phở hảo hạng', N'Mỹ', 70, 
    (SELECT * FROM OPENROWSET(BULK '/img/phobo3mien.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP09', N'Phở gà 3 miền', 9000, '2024-01-30', '2025-12-30', N'Gói', N'Phở hảo hạng', N'Việt Nam', 90, 
    (SELECT * FROM OPENROWSET(BULK '/img/phoga3mien.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP10', N'Thùng phở 3 miền', 140000, '2024-03-15', '2025-03-15', N'Thùng', N'Thùng phở đặc biệt', N'Việt Nam', 50, 
    (SELECT * FROM OPENROWSET(BULK '/img/thungpho3mien.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01'),
('SP11', N'Cá hộp BCG', 15000, '2024-02-22', '2025-06-22', N'Hộp', N'4 khúc', N'Việt Nam', 130, 
    (SELECT * FROM OPENROWSET(BULK '/img/cahop.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP01');

--LSP02 Đồ uống
INSERT INTO [dbo].[SanPham] ([maSP], [tenSP], [donGia], [ngaySX], [hanSD], [donViTinh], [moTa], [xuatXu], [soLuongTon], [anh], [maLoai]) 
VALUES
('SP12', N'Sữa TH True milk', 7000, '2024-02-15', '2025-05-15', N'Hộp', N'Sữa tươi nguyên chất.', N'Việt Nam', 120, 
    (SELECT * FROM OPENROWSET(BULK '/img/suathtruemilk.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP02'),
('SP13', N'Sữa Vinamilk', 7000, '2024-02-15', '2025-05-15', N'Hộp', N'Sữa tươi nguyên chất.', N'Việt Nam', 120, 
    (SELECT * FROM OPENROWSET(BULK '/img/suavinamilk.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP02'),
('SP14', N'Pepsi', 10000, '2024-01-05', '2025-12-31', N'Lon', N'Nước có gas', N'Việt Nam', 160, 
    (SELECT * FROM OPENROWSET(BULK '/img/pepsi.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP02'),
('SP15', N'Cocacola', 10000, '2024-01-05', '2025-12-31', N'Lon', N'Nước có gas', N'Việt Nam', 160, 
    (SELECT * FROM OPENROWSET(BULK '/img/cocacola.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP02'),
('SP16', N'7 up', 10000, '2024-01-05', '2025-12-31', N'Lon', N'Nước có gas', N'Việt Nam', 160, 
    (SELECT * FROM OPENROWSET(BULK '/img/7up.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP02');

--LSP03 Rau
INSERT INTO [dbo].[SanPham] ([maSP], [tenSP], [donGia], [ngaySX], [hanSD], [donViTinh], [moTa], [xuatXu], [soLuongTon], [anh], [maLoai]) 
VALUES
('SP17', N'Rau xà lách', 10000, '2024-11-05', '2024-11-30', N'Bó', N'Rau sạch', N'Việt Nam', 20, 
    (SELECT * FROM OPENROWSET(BULK '/img/xalach.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP03'),
('SP18', N'Rau cải cúc', 10000, '2024-11-05', '2024-11-30', N'Bó', N'Rau sạch', N'Việt Nam', 20, 
    (SELECT * FROM OPENROWSET(BULK '/img/caicuc.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP03');
--LSP04 VSCN
INSERT INTO [dbo].[SanPham] ([maSP], [tenSP], [donGia], [ngaySX], [hanSD], [donViTinh], [moTa], [xuatXu], [soLuongTon], [anh], [maLoai]) 
VALUES
('SP19', N'Bột giặt Omo', 150000, '2024-11-05', '2024-11-30', N'Túi', N'Thơm, sạch', N'Việt Nam', 20, 
    (SELECT * FROM OPENROWSET(BULK '/img/botgiatomo.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP04'),
('SP20', N'Bột giặt Aba', 150000, '2024-11-05', '2024-11-30', N'Túi', N'Thơm, sạch', N'Việt Nam', 20, 
    (SELECT * FROM OPENROWSET(BULK '/img/botgiataba.png', SINGLE_BLOB) AS ImageData), 
    'LSP04'),
('SP21', N'Kem đánh răng Colgatec', 10000, '2024-11-05', '2024-11-30', N'Tuýp', N'Trắng răng', N'Việt Nam', 20, 
    (SELECT * FROM OPENROWSET(BULK '/img/kemdanhrang.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP04');
--LSP06 Dụng cụ học tập
INSERT INTO [dbo].[SanPham] ([maSP], [tenSP], [donGia], [ngaySX], [hanSD], [donViTinh], [moTa], [xuatXu], [soLuongTon], [anh], [maLoai]) 
VALUES
('SP22', N'Bút bi Thiên Long', 5000, '2024-01-01', '2025-12-31', N'Cái', N'Bút bi viết đẹp, viết mượt mà.', N'Việt Nam', 200, 
    (SELECT * FROM OPENROWSET(BULK '/img/caybutthienlong.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP06'),
('SP23', N'Hộp bút Thiên Long', 50000, '2024-02-01', '2025-12-31', N'Hộp', N'Sổ tay bìa cứng, 100 trang.', N'Việt Nam', 150, 
    (SELECT * FROM OPENROWSET(BULK '/img/hopbutthienlong.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP06'),
('SP24', N'Tẩy', 3000, '2024-03-01', '2025-12-31', N'Cái', N'Tẩy cao su, dễ dàng xóa sạch.', N'Việt Nam', 250, 
    (SELECT * FROM OPENROWSET(BULK '/img/tay.jpg', SINGLE_BLOB) AS ImageData), 
    'LSP06');

-- Thêm dữ liệu vào bảng HoaDon
INSERT INTO HoaDon (maHD, ngayLap, tongTien, maNV, maKH)
VALUES 
('HD01', '2024-11-01', 0, 'NV01', 'KH01'),
('HD02', '2024-11-02', 0, 'NV02', 'KH02'),
('HD03', '2024-11-03', 0, 'NV01', 'KH03'),
('HD04', '2024-11-04', 0, 'NV01', 'KH04'),
('HD05', '2024-11-05', 0, 'NV02', 'KH05'),
('HD06', '2024-11-06', 0, 'NV01', 'KH06'),
('HD07', '2024-11-07', 0, 'NV02', 'KH07'),
('HD08', '2024-11-08', 0, 'NV01', 'KH08');

-- Thêm dữ liệu vào bảng ChiTietHoaDon
INSERT INTO [dbo].[ChiTietHoaDon] ([maHD], [maSP], [tenSP], [soLuong], [donGia])
VALUES
('HD01', 'SP01', N'Bột gạo lứt', 2, 50000),  
('HD01', 'SP12', N'Sữa TH True milk', 5, 7000),  
('HD02', 'SP14', N'Pepsi', 3, 10000), 
('HD02', 'SP16', N'7 up', 4, 10000),  
('HD03', 'SP03', N'Mì Kokomi', 10, 3000),
('HD04', 'SP02', N'Gạo ST25', 3, 120000),  
('HD04', 'SP04', N'Gạo An An', 1, 150000),  
('HD05', 'SP05', N'Thùng mì Kokomi', 1, 120000),  
('HD05', 'SP06', N'Thùng mì Hảo Hảo', 2, 120000), 
('HD06', 'SP07', N'Xúc xích', 4, 20000),  
('HD06', 'SP08', N'Phở bò 3 miền', 5, 9000),  
('HD07', 'SP09', N'Phở gà 3 miền', 2, 9000),  
('HD07', 'SP10', N'Thùng phở 3 miền', 1, 140000),  
('HD08', 'SP12', N'Sữa TH True milk', 6, 7000),  
('HD08', 'SP14', N'Pepsi', 3, 10000);

-- Cập nhật tổng tiền cho HD01
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD01'
)
WHERE maHD = 'HD01';

-- Cập nhật tổng tiền cho HD02
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD02'
)
WHERE maHD = 'HD02';

-- Cập nhật tổng tiền cho HD03
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD03'
)
WHERE maHD = 'HD03';
-- Cập nhật tổng tiền cho HD04
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD04'
)
WHERE maHD = 'HD04';

-- Cập nhật tổng tiền cho HD05
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD05'
)
WHERE maHD = 'HD05';

-- Cập nhật tổng tiền cho HD06
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD06'
)
WHERE maHD = 'HD06';

-- Cập nhật tổng tiền cho HD07
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD07'
)
WHERE maHD = 'HD07';

-- Cập nhật tổng tiền cho HD08
UPDATE HoaDon
SET tongTien = (
    SELECT SUM(soLuong * donGia) 
    FROM ChiTietHoaDon 
    WHERE maHD = 'HD08'
)
WHERE maHD = 'HD08';
