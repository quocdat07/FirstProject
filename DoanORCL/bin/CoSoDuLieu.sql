-- T?o b?ng ACCOUNT
CREATE TABLE ACCOUNT
(
    MaDocGia nchar(10) PRIMARY KEY,
    TenDangNhap nchar(30) unique,
    FullName nvarchar2(30),
    email nvarchar2(30) 
);
--Tao Doc Gia
CREATE TABLE DOCGIA
(
    MaDocGia nchar(10),
    TenDangNhap nchar(30) UNIQUE,
    HoTen nchar(30),
    GioiTinh nchar(5),
    NamSinh date,
    DiaChi nchar(100),
    AnhThe BLOB,
    NhacNen BLOB,
    PRIMARY KEY (MaDocGia),
    CONSTRAINT FK_DOCGIA_ACCOUNT FOREIGN KEY (MaDocGia) REFERENCES ACCOUNT(MaDocGia)
);

-- T?o b?ng SACH
CREATE TABLE SACH
(
    MaSach nchar(10),
    TenSach nchar(50),
    TacGia nchar(30),
    TheLoai nchar(30),
    NhaXuatBan nchar(50),
    GiaSach int,
    SoLuong int,
    TinhTrang nchar(10),
    ghichu nvarchar2(255),
    AnhSach BLOB,
    PRIMARY KEY (MaSach)
);
  

-- T?o b?ng PHIEUMUON
CREATE TABLE PHIEUMUON
(
    MaPhieu nchar(10),
    MaDocGia nchar(10),
    MaSach nchar(10),
    NgayMuon date,
    NgayPhaiTra date,
    PRIMARY KEY (MaPhieu),
    CONSTRAINT FK_PHIEUMUON_DOCGIA FOREIGN KEY (MaDocGia) REFERENCES DOCGIA (MaDocGia),
    CONSTRAINT FK_PHIEUMUON_SACH FOREIGN KEY (MaSach) REFERENCES SACH (MaSach)
);

-- T?o b?ng PHIEUTRA
CREATE TABLE PHIEUTRA
(
    MaPhieu nchar(10),
    MaDocGia nchar(10),
    MaSach nchar(10),
    NgayTra date,
    PRIMARY KEY (MaPhieu),
    CONSTRAINT FK_PHIEUTRA_DOCGIA FOREIGN KEY (MaDocGia) REFERENCES DOCGIA (MaDocGia),
    CONSTRAINT FK_PHIEUTRA_SACH FOREIGN KEY (MaSach) REFERENCES SACH (MaSach)
);

-- Thêm d? li?u vào b?ng ACCOUNT
INSERT INTO ACCOUNT (MaDocGia, TenDangNhap, FullName, email)
VALUES
(STT_ACCOUNT.nextval, 'nguyenvana', 'Nguy?n V?n A', 'nguyenvana@example.com');

INSERT INTO ACCOUNT (MaDocGia, TenDangNhap, FullName, email)
VALUES
(STT_ACCOUNT.nextval, 'tranthib', 'Tr?n Th? B', 'tranthib@example.com');

INSERT INTO ACCOUNT (MaDocGia, TenDangNhap, FullName, email)
VALUES
(STT_ACCOUNT.nextval, 'lequangc', 'Lê Quang C', 'lequangc@example.com');

-- Thêm d? li?u vào b?ng DOCGIA
INSERT INTO DOCGIA (MaDocGia, TenDangNhap, HoTen, GioiTinh, NamSinh, DiaChi)
VALUES
(1, 'nguyenvana', 'Nguy?n V?n A', 'Nam', TO_DATE('15/01/1998', 'DD/MM/YYYY'), '123 ???ng A, Qu?n B, TP C');

INSERT INTO DOCGIA (MaDocGia, TenDangNhap, HoTen, GioiTinh, NamSinh, DiaChi)
VALUES
(2, 'tranthib', 'Tr?n Th? B', 'N?', TO_DATE('20/02/1990', 'DD/MM/YYYY'), '456 ???ng D, Qu?n E, TP F');

INSERT INTO DOCGIA (MaDocGia, TenDangNhap, HoTen, GioiTinh, NamSinh, DiaChi)
VALUES
(3, 'lequangc', 'Lê Quang C', 'Nam', TO_DATE('25/03/1995', 'DD/MM/YYYY'), '789 ???ng G, Qu?n H, TP I');

-- Thêm d? li?u vào b?ng SACH
INSERT INTO SACH (MaSach, TenSach, TacGia, TheLoai, NhaXuatBan, GiaSach, SoLuong, TinhTrang, GhiChu)
VALUES
(STT_SACH.nextval, 'L?p Trình C++', 'Nguy?n V?n A', 'Công ngh? thông tin', 'NXB A', 100000, 10, 'M?i', 'Không có ghi chú');

INSERT INTO SACH (MaSach, TenSach, TacGia, TheLoai, NhaXuatBan, GiaSach, SoLuong, TinhTrang, GhiChu)
VALUES
(STT_SACH.nextval, 'Toán Cao C?p', 'Tr?n Th? B', 'Giáo d?c', 'NXB B', 150000, 5, 'M?i', 'Không có ghi chú');

INSERT INTO SACH (MaSach, TenSach, TacGia, TheLoai, NhaXuatBan, GiaSach, SoLuong, TinhTrang, GhiChu)
VALUES
(STT_SACH.nextval, 'V?t Lý ??i C??ng', 'Lê Quang C', 'Khoa h?c t? nhiên', 'NXB C', 120000, 7, 'M?i', 'Không có ghi chú');

-- Thêm d? li?u vào b?ng PHIEUMUON
INSERT INTO PHIEUMUON (MaPhieu, MaDocGia, MaSach, NgayMuon, NgayPhaiTra)
VALUES
(STT_PHIEUMUON.nextval, 1, 21, TO_DATE('2024-05-01', 'YYYY-MM-DD'), TO_DATE('2024-06-01', 'YYYY-MM-DD'));

INSERT INTO PHIEUMUON (MaPhieu, MaDocGia, MaSach, NgayMuon, NgayPhaiTra)
VALUES
(STT_PHIEUMUON.nextval, 1, 2, TO_DATE('2024-05-02', 'YYYY-MM-DD'), TO_DATE('2024-06-02', 'YYYY-MM-DD'));

INSERT INTO PHIEUMUON (MaPhieu, MaDocGia, MaSach, NgayMuon, NgayPhaiTra)
VALUES
(STT_PHIEUMUON.nextval, 3, 1, TO_DATE('2024-05-03', 'YYYY-MM-DD'), TO_DATE('2024-06-03', 'YYYY-MM-DD'));

-- Thêm d? li?u vào b?ng PHIEUTRA
INSERT INTO PHIEUTRA (MaPhieu, MaDocGia, MaSach, NgayTra)
VALUES
(STT_PHIEUTRA.nextval, 1, 21, TO_DATE('2024-05-10', 'YYYY-MM-DD'));

INSERT INTO PHIEUTRA (MaPhieu, MaDocGia, MaSach, NgayTra)
VALUES
(STT_PHIEUTRA.nextval, 1, 2,TO_DATE('2024-05-12', 'YYYY-MM-DD'));

INSERT INTO PHIEUTRA (MaPhieu, MaDocGia, MaSach, NgayTra)
VALUES
(STT_PHIEUTRA.nextval,  3, 1, TO_DATE('2024-05-14', 'YYYY-MM-DD'));

SELECT * FROM ACCOUNT;
SELECT * FROM DOCGIA;
SELECT * FROM SACH;
SELECT * FROM PHIEUMUON;
SELECT * FROM PHIEUTRA;

--DROP TABLE ACCOUNT;
--DROP TABLE DOCGIA;
--DROP TABLE SACH;
--DROP TABLE PHIEUMUON;
--DROP TABLE PHIEUTRA;


CREATE TABLE ADMINUSER(
    userName nvarchar2(30) primary key
);

CREATE TABLE PRIVSOFUSER(
    userName nvarchar2(30) primary key,
    PRIVS nvarchar2(100)
);

CREATE TABLE LASTLOGIN(
    userName nvarchar2(30) primary key,
    LastLogin date,
    NowLogin date
);
---T?o view LASTLOGIN
CREATE VIEW LSTLOGIN AS
SELECT userName, LastLogin
FROM LASTLOGIN;

select * from LASTLOGIN;

create table User_Privs(
    MaQuyen number primary key,
    TenQuyen nvarchar2(100)
);

insert into User_Privs values (1,  N'Xem và ch?nh s?a t?t c? các b?ng');
insert into User_Privs values (2,  N'Xem thông tin h? th?ng');
insert into User_Privs values (3,  N'Xem các session');
insert into User_Privs values (4,  N'Xem và kill các session');
insert into User_Privs values (5,  N'Xem và thêm tablespace');

create table User_Own_Privs(
   Ten_User nchar(30),
    TenQuyen nvarchar2(100)
);

create view User_Own_Privs_View as
select Ten_User, TenQuyen from User_Own_Privs;

create view User_Privs_View as
select TenQuyen from User_Privs;