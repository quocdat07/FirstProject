---------------------------------------Xem bang sach------------------------------------
create or replace procedure showSach(
    p_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM Sach;
END;
/
-------------------------------------------Them du lieu vao Sach-----------------------------
create or replace procedure insertSach(
    p_MaSach nchar,
    p_TenSach nchar,
    p_TacGia nchar,
    p_TheLoai nchar,
    p_NhaXuatBan nchar,
    p_GiaSach int,
    p_SoLuong int,
    p_TinhTrang nchar
    ) AS
    BEGIN
        INSERT INTO Sach (MaSach, TenSach, TacGia, TheLoai, NhaXuatBan, GiaSach, SoLuong, TinhTrang)
        VALUES (STT_SACH.nextVal, p_TenSach, p_TacGia, p_TheLoai, p_NhaXuatBan, p_GiaSach, p_SoLuong, p_TinhTrang);
    END;
/
----------------------------------------------Xoa du lieu Sach----------------------------------------------
CREATE OR REPLACE function delete_sach (
    p_MaSach IN VARCHAR2
)
    return boolean
AS
    p_count number;
BEGIN
    select count(*) into p_count from sach where MaSach = p_MaSach;
    IF p_count = 1 then
        select count(*) into p_count from phieumuon where MaSach = p_MaSach;
        if p_count = 1 then
            DELETE FROM phieumuon where MaSach = p_MaSach;
        end if;
        select count(*) into p_count from phieutra where MaSach = p_MaSach;
        if p_count = 1 then
            DELETE FROM phieutra where MaSach = p_MaSach;
        end if;
            DELETE FROM Sach WHERE MaSach = p_MaSach;
        select count(*) into p_count from sach where MaSach = p_MaSach;
        if p_count = 0 then
            COMMIT;
            return true;
        end if;
    ELSE
        return false;
    end if;
END;
/
DECLARE
    p_MaSach number := 1;
    p_count number;
BEGIN
    select count(*) into p_count from phieumuon where MaSach = p_MaSach;
    if p_count = 1 then
    DELETE FROM phieumuon where MaSach = p_MaSach;
    end if;
    select count(*) into p_count from phieutra where MaSach = p_MaSach;
    if p_count = 1 then
    DELETE FROM phieutra where MaSach = p_MaSach;
    end if;
    DELETE FROM Sach WHERE MaSach = p_MaSach;
END;

rollback;
----------------------------------------------Sua du lieu Sach----------------------------------------------
CREATE OR REPLACE PROCEDURE update_sach (
    p_MaSach IN number,
    p_TenSach IN VARCHAR2,
    p_TacGia IN VARCHAR2,
    p_TheLoai IN VARCHAR2,
    p_NhaXuatBan IN VARCHAR2,
    p_GiaSach IN INT,
    p_SoLuong IN INT,
    p_TinhTrang IN VARCHAR2
)
AS
BEGIN
    UPDATE Sach
    SET TenSach = p_TenSach,
        TacGia = p_TacGia,
        TheLoai = p_TheLoai,
        NhaXuatBan = p_NhaXuatBan,
        GiaSach = p_GiaSach,
        SoLuong = p_SoLuong,
        TinhTrang = p_TinhTrang
    WHERE MaSach = p_MaSach;
    COMMIT;
END;
/
exec update_sach(41, 'V?t Lý 34', 'Lê Quang X', 'Khoa hoc', 'NXB C', '120000', 7, 'M?i');
select * from sach where masach = 41;

UPDATE Sach
    SET TenSach = 'V?t Lý AV',
        TacGia = 'Lê Quang X',
        TheLoai =  'Khoa hoc',
        NhaXuatBan =  'NXB C',
        GiaSach = '120000',
        SoLuong = 7,
        TinhTrang = 'M?i'
    WHERE MaSach = 41;
---------------------------------------Xem bang DOCGIA------------------------------------
create or replace procedure showDOCGIA(
    p_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN p_cursor FOR
    SELECT madocgia, tendangnhap, hoten, gioitinh, to_char(namsinh,'dd/mm/yyyy'), diachi FROM DOCGIA;
END;
/
----------------------------------------------Xoa du lieu DOCGIA----------------------------------------------
CREATE OR REPLACE PROCEDURE deleteDOCGIA (
    p_MaDocGia IN NCHAR
)
AS
BEGIN
    DELETE FROM PHIEUMUON WHERE MaDocGia = p_MaDocGia;
    DELETE FROM PHIEUTRA WHERE MaDocGia = p_MaDocGia;
    DELETE FROM DOCGIA WHERE MaDocGia = p_MaDocGia;
    COMMIT;
END;
/
----------------------------------------------Sua du lieu DOCGIA----------------------------------------------
CREATE OR REPLACE PROCEDURE updateDOCGIA (
    p_MaDocGia IN NCHAR,
    p_TenDangNhap IN NCHAR,
    p_HoTen IN NCHAR,
    p_GioiTinh IN NCHAR,
    p_NamSinh IN NCHAR,
    p_DiaChi IN NCHAR
)
AS
BEGIN
    UPDATE DOCGIA
    SET TenDangNhap = p_TenDangNhap,
            HoTen = p_HoTen,
            GioiTinh = p_GioiTinh,
            NamSinh = TO_DATE(p_NamSinh),
            DiaChi = p_DiaChi
    WHERE MaDocGia = p_MaDocGia;
    COMMIT;
END;
/
------------------------------------------------Show PHIEUMUON----------------------------------------
Create or Replace Procedure ShowPhieuMuon(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT MaPhieu, MaDocGia, MaSach, TO_CHAR(NgayMuon, 'DD/MM/YYYY') AS "Ngày m??n", TO_CHAR(NgayPhaiTra, 'DD/MM/YYYY') AS "Ngày ph?i tr?"
    FROM PHIEUMUON;
END;
/

-- Th? t?c thêm d? li?u vào b?ng PHIEUMUON
CREATE OR REPLACE FUNCTION ThemPhieuMuon(
    p_MaPhieu IN PHIEUMUON.MaPhieu%TYPE,
    p_MaDocGia IN PHIEUMUON.MaDocGia%TYPE,
    p_MaSach IN PHIEUMUON.MaSach%TYPE,
    p_NgayMuon IN PHIEUMUON.NgayMuon%TYPE,
    p_NgayPhaiTra IN PHIEUMUON.NgayPhaiTra%TYPE
) 
RETURN BOOLEAN
IS
    p_chek_masach NUMBER;
    p_check_maDocGia NUMBER;
BEGIN
    -- Parameter validation
    IF p_MaPhieu IS NULL OR p_MaDocGia IS NULL OR p_MaSach IS NULL OR p_NgayMuon IS NULL OR p_NgayPhaiTra IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'Parameters cannot be null');
    END IF;

    -- Check if MaSach exists
    SELECT COUNT(*) INTO p_chek_masach FROM sach WHERE MaSach = p_MaSach;
    
    -- Check if MaDocGia exists
    SELECT COUNT(*) INTO p_check_maDocGia FROM docgia WHERE MaDocGia = p_MaDocGia;

    IF p_chek_masach = 0 AND p_check_maDocGia = 0 THEN
        RETURN FALSE; -- Both MaSach and MaDocGia do not exist
    ELSE
        BEGIN
            -- Attempt to insert data
            INSERT INTO PHIEUMUON (MaPhieu, MaDocGia, MaSach, NgayMuon, NgayPhaiTra)
            VALUES (STT_PHIEUMUON.nextval, p_MaDocGia, p_MaSach, p_NgayMuon, p_NgayPhaiTra);
            RETURN TRUE; -- Insert successful
        EXCEPTION
            WHEN OTHERS THEN
                -- Handle exceptions
                RAISE_APPLICATION_ERROR(-20002, 'Error occurred during insert: ' || SQLERRM);
        END;
    END IF;
END;

/

-- Th? t?c xóa d? li?u kh?i b?ng PHIEUMUON
CREATE OR REPLACE PROCEDURE XoaPhieuMuon(
    p_MaPhieu IN PHIEUMUON.MaPhieu%TYPE
) IS
BEGIN
    DELETE FROM PHIEUMUON WHERE MaPhieu = p_MaPhieu;
    COMMIT;
END;
/

-- Th? t?c s?a d? li?u trong b?ng PHIEUMUON
CREATE OR REPLACE PROCEDURE SuaPhieuMuon(
    p_MaPhieu IN number,
    p_MaDocGia IN nvarchar2,
    p_MaSach IN nvarchar2,
    p_NgayMuon IN nvarchar2,
    p_NgayPhaiTra IN nvarchar2
) IS
BEGIN
    UPDATE PHIEUMUON
    SET MaDocGia = p_MaDocGia,
            MaSach = p_MaSach,
            NgayMuon = p_NgayMuon,
            NgayPhaiTra = p_NgayPhaiTra
    WHERE MaPhieu = p_MaPhieu;
    COMMIT;
END;
/
--------------------------------------------------SHOWPHIEUTRA-------------------------------------------
Create or Replace Procedure ShowPhieuTra(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT MaPhieu, MaDocGia, MaSach, TO_CHAR(NgayTra, 'DD/MM/YYYY') AS "Ngày tr?"
    FROM PHIEUTRA;
END;
/

-- Th? t?c thêm d? li?u vào b?ng PHIEUTRA
CREATE OR REPLACE FUNCTION ThemPhieuTra(
    p_MaPhieu IN PHIEUTRA.MaPhieu%TYPE,
    p_MaDocGia IN PHIEUTRA.MaDocGia%TYPE,
    p_MaSach IN PHIEUTRA.MaSach%TYPE,
    p_NgayTra IN DATE
) 
RETURN BOOLEAN
IS
    p_chek_masach NUMBER;
    p_check_maDocGia NUMBER;
BEGIN
    -- Parameter validation
    IF p_MaPhieu IS NULL OR p_MaDocGia IS NULL OR p_MaSach IS NULL OR p_NgayTra IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'Parameters cannot be null');
    END IF;

    -- Check if MaSach exists
    SELECT COUNT(*) INTO p_chek_masach FROM sach WHERE MaSach = p_MaSach;

    -- Check if MaDocGia exists
    SELECT COUNT(*) INTO p_check_maDocGia FROM docgia WHERE MaDocGia = p_MaDocGia;

    IF p_chek_masach = 0 AND p_check_maDocGia = 0 THEN
        RETURN FALSE; -- Both MaSach and MaDocGia do not exist
    ELSE
        BEGIN
            -- Attempt to insert data
            INSERT INTO PHIEUTRA (MaPhieu, MaDocGia, MaSach, NgayTra)
            VALUES (STT_PHIEUTRA.nextval, p_MaDocGia, p_MaSach, TO_DATE(p_NgayTra, 'YYYY-MM-DD'));
            RETURN TRUE; -- Insert successful
        EXCEPTION
            WHEN OTHERS THEN
                -- Handle exceptions
                RAISE_APPLICATION_ERROR(-20002, 'Error occurred during insert: ' || SQLERRM);
        END;
    END IF;
END;
/

-- Th? t?c xóa d? li?u kh?i b?ng PHIEUTRA
CREATE OR REPLACE PROCEDURE XoaPhieuTra(
    p_MaPhieu IN PHIEUTRA.MaPhieu%TYPE
) IS
BEGIN
    DELETE FROM PHIEUTRA WHERE MaPhieu = p_MaPhieu;
END;
/

-- Th? t?c s?a d? li?u trong b?ng PHIEUTRA
CREATE OR REPLACE PROCEDURE SuaPhieuTra(
    p_MaPhieu IN number,
    p_MaDocGia IN nvarchar2,
    p_MaSach IN nvarchar2,
    p_NgayTra IN nvarchar2
) IS
BEGIN
    UPDATE PHIEUTRA
    SET MaDocGia = p_MaDocGia,
        MaSach = p_MaSach,
        NgayTra = p_NgayTra
    WHERE MaPhieu = p_MaPhieu;
    COMMIT;
END;
/

------------------------------------------------Show TAI KHOAN----------------------------------------
create or replace Procedure ShowACCOUNT(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT *
    FROM ACCOUNT;
END;
