------------------------------------------------Câu l?nh quan tr?ng----------------------------------------------
alter session set "_ORACLE_SCRIPT"=true; 

------------------------------------------------Nh?p mã t? ??ng----------------------------------------------
create sequence STT_ACCOUNT minvalue 1 increment by 1 start with 1;
create sequence STT_SACH minvalue 1 increment by 1 start with 1;
create sequence STT_PHIEUTRA minvalue 1 increment by 1 start with 1;
create sequence STT_PHIEUMUON minvalue 1 increment by 1 start with 1;

--DROP SEQUENCE STT_ACCOUNT;
--DROP SEQUENCE STT_SACH;
--DROP SEQUENCE STT_PHIEUTRA;
--DROP SEQUENCE STT_PHIEUMUON;
--DROP SEQUENCE STT_sequence;
CREATE OR REPLACE PROCEDURE Resources_profile 
is
begin 
    execute immediate 'CREATE PROFILE RESOURCES_PROFILE_USER LIMIT
    SESSIONS_PER_USER 3
    CPU_PER_SESSION 600
    IDLE_TIME 60
    CONNECT_TIME 500
    ';
END;

------------------------------------------------Thêm thông tin account----------------------------------------------
CREATE OR REPLACE PROCEDURE AddAccount (
    p_username IN VARCHAR2,
    p_email IN VARCHAR2,
    p_fullname IN VARCHAR2
) 
IS
BEGIN
    INSERT INTO Account (madocgia, TenDangNhap, email, FullName)
    VALUES (STT_Account.nextval, p_username, p_email, p_fullname);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20001, 'An error occurred while adding the account: ' || SQLERRM);
END;
------------------------------------------------Tao Role------------------------------------------------
Create role khachhang;
grant create session to khachhang;
grant select on DAT_ADMIN.PHIEUMUON to khachhang;
grant select on DAT_ADMIN.PHIEUTRA to khachhang;
grant select on DAT_ADMIN.SACH to khachhang;
grant select on DAT_ADMIN.LSTLOGIN to khachhang;
GRANT EXECUTE ON DAT_ADMIN.my_security_pkg TO khachhang;
GRANT EXECUTE ON DAT_ADMIN.ShowDocGia TO khachhang;
GRANT EXECUTE ON DAT_ADMIN.ShowPhieuMuon TO khachhang;
GRANT EXECUTE ON DAT_ADMIN.ShowSach TO khachhang; 
GRANT EXECUTE ON DAT_ADMIN.ShowPhieuTra TO khachhang;
-----------------------------------------------Test Create User-----------------------------------------------
create user mr_dam identified by 123;
grant create session to mr_dam;
grant khachhang to mr_dam;
------------------------------------------------Kill session----------------------------------------------
CREATE OR REPLACE PROCEDURE KillSession(
    p_sid IN NUMBER,
    p_serial IN NUMBER
) AS
BEGIN
    FOR i IN (SELECT sid, serial# FROM v$session WHERE sid = p_sid AND serial# = p_serial) LOOP
        BEGIN
            EXECUTE IMMEDIATE 'ALTER SYSTEM KILL SESSION ''' || i.sid || ',' || i.serial# || ''' IMMEDIATE';
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error while killing session: ' || SQLERRM);
        END;
    END LOOP;
END;
/
------------------------------------------------------L?y th?i gian ??ng nh?p-------------------------------------------------------------
CREATE OR REPLACE PACKAGE my_security_pkg AS
    PROCEDURE get_last_login(p_userName IN NVARCHAR2, p_lastLogin OUT DATE);
    PROCEDURE Take_Last_lgINF (p_userName IN NVARCHAR2);
    PROCEDURE ShowUserLast_LG(p_cursor OUT SYS_REFCURSOR, p_userName IN NVARCHAR2);
END my_security_pkg;
/

CREATE OR REPLACE PACKAGE BODY my_security_pkg AS
    PROCEDURE get_last_login(p_userName IN NVARCHAR2, p_lastLogin OUT DATE) IS
    BEGIN
        SELECT last_login INTO p_lastLogin
        FROM dba_users
        WHERE username = p_userName;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_lastLogin := NULL;
    END get_last_login;
    
    PROCEDURE Take_Last_lgINF (
    p_userName IN NVARCHAR2
    )
    AS
        p_last_lg DATE;
        p_now_lg DATE;
        p_count NUMBER;
    BEGIN
        -- L?y th?i gian ??ng nh?p hi?n t?i
        my_security_pkg.get_last_login(p_userName, p_now_lg);
        -- Ki?m tra xem user có t?n t?i trong b?ng LASTLOGIN hay không
        SELECT COUNT(*) INTO p_count
        FROM LASTLOGIN
        WHERE userName = p_userName;
        
        -- Th?c hi?n các thao tác c?p nh?t ho?c chèn
        IF p_count = 0 THEN
            -- N?u user ch?a t?n t?i, chèn m?i
            INSERT INTO LASTLOGIN (userName, LastLogin, NowLogin)
            VALUES (p_userName, p_now_lg, p_now_lg);
        ELSE
            -- N?u user ?ã t?n t?i, c?p nh?t th?i gian ??ng nh?p
            SELECT NowLogin INTO p_last_lg
            FROM LASTLOGIN
            WHERE userName = p_userName;
            
            UPDATE LASTLOGIN
            SET 
                LASTLOGIN.LastLogin = LASTLOGIN.NowLogin,
                NowLogin = p_now_lg
            WHERE userName = p_userName;
        END IF;
        -- Commit thay ??i
        COMMIT;
    END Take_Last_lgINF;
    
    PROCEDURE ShowUserLast_LG(
    p_cursor OUT SYS_REFCURSOR,
    p_userName IN NVARCHAR2
    ) AS
    BEGIN
        BEGIN
            Take_Last_lgINF(p_userName);
        END;
        OPEN p_cursor FOR
        select LastLogin
        from LSTLOGIN
        WHERE userName = p_userName;
    END ShowUserLast_LG;
END my_security_pkg;
/
---------------------------------------Xem role dang co--------------------------------------------------
create or replace procedure showRole(
    p_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN p_cursor FOR
    SELECT DBA_ROLES.ROLE AS ROLE_NAME
    FROM DBA_SYS_PRIVS
    RIGHT JOIN DBA_ROLES ON DBA_SYS_PRIVS.GRANTEE = DBA_ROLES.ROLE
    GROUP BY DBA_ROLES.ROLE;
END;
/
---------------------------------------Xem tao role--------------------------------------------------
CREATE OR REPLACE PROCEDURE creRole (
    p_roleName IN NVARCHAR2
)
AS
    v_sql VARCHAR2(32767);
BEGIN
    -- Sanitize the role name to prevent SQL injection
    IF p_roleName IS NULL OR p_roleName = '' THEN
        RAISE_APPLICATION_ERROR(-20001, 'Role name cannot be NULL or empty');
    END IF;
    
    -- Construct the dynamic SQL statement safely
    v_sql := 'CREATE ROLE "' || REPLACE(p_roleName, '"', '""') || '"';
    
    -- Execute the dynamic SQL statement
    EXECUTE IMMEDIATE v_sql;
END;

---------------------------------------Xoa role--------------------------------------------------
CREATE OR REPLACE PROCEDURE DrpRole (
    p_roleName IN NVARCHAR2
)
AS
    l_sql VARCHAR2(32767);
BEGIN
    -- Check if the role name is NULL or empty
    IF p_roleName IS NULL OR p_roleName = '' THEN
        RAISE_APPLICATION_ERROR(-20001, 'Role name cannot be NULL or empty');
    END IF;
    
    -- Construct the dynamic SQL statement safely
    l_sql := 'DROP ROLE "' || REPLACE(p_roleName, '"', '""') || '"';
    
    -- Execute the dynamic SQL statement
    EXECUTE IMMEDIATE l_sql;
EXCEPTION
    WHEN OTHERS THEN
        -- Handle any errors that occur during role dropping
        RAISE_APPLICATION_ERROR(-20002, 'An error occurred: ' || SQLERRM);
END;
/

-------------------------------------------------Them thong tin dang ky---------------------------------
Create or replace procedure ThemThongTinDangKy(
    p_TenDangNhap in nvarchar2,
    p_FullName in nvarchar2,
    p_email in nvarchar2
)
AS
BEGIN
    INSERT INTO ACCOUNT VALUES(STT_ACCOUNT.nextval, p_TenDangNhap, p_FullName, p_email);
END;
/
------------------------------------------------TriggerAddDOCGIA------------------------------------
Create or Replace Trigger AddMADOCGIA
AFTER INSERT ON ACCOUNT
FOR EACH ROW
BEGIN
    INSERT INTO DOCGIA(MaDocGia, TenDangNhap) VALUES (:NEW.MaDocGia, :NEW.TenDangNhap);
END;
/
-----------------------------------------------Cap Quyen--------------------------------------------------
CREATE OR REPLACE PROCEDURE grant_select_privilege(
    p_table_name   IN VARCHAR2,
    p_user_name    IN VARCHAR2
) AS
BEGIN
    -- Th?c hi?n câu l?nh c?p quy?n SELECT cho b?ng p_table_name cho ng??i dùng p_user_name
    EXECUTE IMMEDIATE 'GRANT SELECT ON ' || p_table_name || ' TO ' || p_user_name;
EXCEPTION
    WHEN OTHERS THEN
        -- In ra thông báo l?i n?u có l?i x?y ra
        RAISE_APPLICATION_ERROR(-20002,'Loi cap quyen');
END;
/
CREATE OR REPLACE PROCEDURE grant_update_delete_privilege(
    p_table_name   IN VARCHAR2,
    p_user_name    IN VARCHAR2
) AS
BEGIN
    -- Th?c hi?n câu l?nh c?p quy?n UPDATE và DELETE cho b?ng p_table_name cho ng??i dùng p_user_name
    EXECUTE IMMEDIATE 'GRANT UPDATE, DELETE ON ' || p_table_name || ' TO ' || p_user_name;
EXCEPTION
    WHEN OTHERS THEN
        -- In ra thông báo l?i n?u có l?i x?y ra
        RAISE_APPLICATION_ERROR(-20002,'Loi cap quyen');
END;
/
CREATE OR REPLACE PROCEDURE revoke_privilege_change(
    p_table_name   IN VARCHAR2,
    p_user_name    IN VARCHAR2
) AS
BEGIN
    -- Th?c hi?n câu l?nh thu h?i quy?n p_privilege cho b?ng p_table_name t? ng??i dùng p_user_name
    EXECUTE IMMEDIATE 'REVOKE UPDATE, DELETE ON ' || p_table_name || ' FROM ' || p_user_name;
EXCEPTION
    WHEN OTHERS THEN
        -- In ra thông báo l?i n?u có l?i x?y ra
        RAISE_APPLICATION_ERROR(-20002,'Loi tuoc quyen');
END;
/
------------------------------------------------Them Package dang ky------------------------------------
CREATE OR REPLACE PACKAGE QuanLyUser AS
    PROCEDURE ThemThongTinDangKy(
        p_TenDangNhap IN NVARCHAR2,
        p_FullName IN NVARCHAR2,
        p_email IN NVARCHAR2
    );

    PROCEDURE Them_User(
        TaiKhoan IN VARCHAR2,
        MatKhau IN VARCHAR2
    );

    FUNCTION KiemTra_UserTonTai(
        TaiKhoan IN VARCHAR2
    ) RETURN BOOLEAN;
END QuanLyUser;
/
CREATE OR REPLACE PACKAGE BODY QuanLyUser AS

PROCEDURE Them_User(
        TaiKhoan IN VARCHAR2,
        MatKhau IN VARCHAR2
    )
    IS
    BEGIN
        BEGIN
            -- Create the user with the provided username and password
            EXECUTE IMMEDIATE 'CREATE USER ' || TaiKhoan || ' IDENTIFIED BY ' || MatKhau;
        EXCEPTION
            WHEN OTHERS THEN
                RAISE_APPLICATION_ERROR(-20002, 'Khong tao duoc user');
                RAISE;
        END;
        
        BEGIN
            -- Grant the khachhang role to the newly created user
            EXECUTE IMMEDIATE 'GRANT khachhang TO ' || TaiKhoan;
        EXCEPTION
            WHEN OTHERS THEN
                RAISE_APPLICATION_ERROR(-20002, 'Khong gan duoc quyen');
                RAISE;
        END;
        COMMIT;
    END Them_User;

    PROCEDURE ThemThongTinDangKy(
        p_TenDangNhap IN NVARCHAR2,
        p_FullName IN NVARCHAR2,
        p_email IN NVARCHAR2
    )
    AS
    BEGIN
        INSERT INTO ACCOUNT VALUES(STT_ACCOUNT.NEXTVAL, p_TenDangNhap, p_FullName, p_email);
    END ThemThongTinDangKy;

    FUNCTION KiemTra_UserTonTai(
        TaiKhoan IN VARCHAR2
    ) RETURN BOOLEAN
    IS
        UserCount INTEGER;
    BEGIN
        SELECT COUNT(*)
        INTO UserCount
        FROM all_users    
        WHERE username = UPPER(TaiKhoan);

        IF UserCount > 0 THEN
            RETURN TRUE;
        ELSE
            RETURN FALSE;
        END IF;
    END KiemTra_UserTonTai;

END QuanLyUser;
/
---------------------------------------------------------------Ki?m tra policy--------------------------------------------------------
create or replace procedure add_policy(
    p_cursor OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_cursor FOR
    SELECT OBJECT_NAME
    FROM ALL_POLICIES;
END;

-----------------------------------------------------------------------------Sach---------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_book_policy (
    schema_name IN VARCHAR2,
    table_name IN VARCHAR2
) RETURN VARCHAR2 IS
    predicate VARCHAR2(4000);
BEGIN
    -- Construct the predicate to allow only rows with non-negative price and quantity
    predicate := 'gia >= 0 AND soluong >= 0';
    RETURN predicate;
END;
/
BEGIN
    DBMS_RLS.ADD_POLICY(
        object_schema   => 'dat_admin',
        object_name     => 'SACH',
        policy_name     => 'check_book_policy',
        function_schema => 'dat_admin',
        policy_function => 'check_book_policy',
        statement_types => 'INSERT, UPDATE',
        update_check    => TRUE
    );
END;
/
--BEGIN
--    DBMS_RLS.DROP_POLICY(
--        object_schema => 'dat_admin',
--        object_name   => 'SACH',
--        policy_name   => 'CHECK_PRICE_AND_QUANTITY'
--    );
--END;
--/
--SELECT * FROM dba_policies;
-------------------------------------------------------Thêm Privs-----------------------------------------------
create or replace procedure add_privs(
    p_cursor OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_cursor FOR
    SELECT NAME 
    FROM SYSTEM_PRIVILEGE_MAP 
    ORDER BY NAME;
END;
/
SELECT ROLE FROM DBA_ROLES ORDER BY ROLE;


-------------------------------------------------------Thêm Privs s? h?u-----------------------------------------------
create or replace procedure add_Own_privs(
    p_cursor OUT SYS_REFCURSOR,
    p_userName IN nvarchar2
)
IS
BEGIN
    OPEN p_cursor FOR
    SELECT * 
    FROM DBA_SYS_PRIVS 
    WHERE GRANTEE = p_userName;
END;
------------------------------------------------C?p quy?n------------------------------------------------
create or replace Procedure grant_User(
    p_username IN VARCHAR2,
    p_role_name IN VARCHAR2
)
AS
BEGIN
    EXECUTE IMMEDIATE 'GRANT '  || p_role_name || ' TO ' || p_username;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        COMMIT;
END;
    --------------------------------------------------Thu quy?n-------------------------------------------------
create or replace procedure revoke_Privilege(
    p_username IN VARCHAR2,
    p_privilege_name IN VARCHAR2
)
AS
BEGIN
    FOR priv IN (
        select granted_role
        from dba_role_privs
        where grantee = p_username
            AND granted_role = p_privilege_name
        UNION
        SELECT privilege
        FROM dba_sys_privs
        where grantee = p_username
            AND privilege = p_privilege_name
    ) LOOP
        execute immediate 'revoke ' || priv.granted_role || ' FROM ' || p_username;
    END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        commit;
END;
-----------------------------------------------Thêm âm thanh----------------------------------------------
--CREATE OR REPLACE PROCEDURE StoreAudioFile (
--    p_id IN NUMBER,
--    p_audio_name IN VARCHAR2,
--    p_audio_directory_name IN VARCHAR2,
--    p_audio_file_name IN VARCHAR2
--) AS
--    l_audio_bfile BFILE;
--    l_audio_blob  BLOB;
--BEGIN
--    -- X? lý t?p âm thanh
--    l_audio_bfile := BFILENAME(p_audio_directory_name, p_audio_file_name);
--    DBMS_LOB.OPEN(l_audio_bfile, DBMS_LOB.LOB_READONLY);
--    DBMS_LOB.CREATETEMPORARY(l_audio_blob, TRUE);
--    DBMS_LOB.LOADFROMFILE(l_audio_blob, l_audio_bfile, DBMS_LOB.GETLENGTH(l_audio_bfile));
--    DBMS_LOB.CLOSE(l_audio_bfile);
--
--    -- Chèn ho?c c?p nh?t d? li?u âm thanh vào b?ng
--    MERGE INTO MediaFiles mf
--    USING (SELECT p_id AS id FROM DUAL) src
--    ON (mf.id = src.id)
--    WHEN MATCHED THEN
--        UPDATE SET audio_data = l_audio_blob, media_name = p_audio_name
--    WHEN NOT MATCHED THEN
--        INSERT (id, media_name, audio_data) VALUES (p_id, p_audio_name, l_audio_blob);
--
--    -- Gi?i phóng BLOB t?m th?i
--    DBMS_LOB.FREETEMPORARY(l_audio_blob);
--END;
/

----------------------------------------L?u tr? ?nh-------------------------------------000000
CREATE OR REPLACE PROCEDURE UpdateBookImage (
    p_MaSach IN CHAR,
    p_ImageData IN CLOB
) AS
    l_image_blob  BLOB;
    l_clob_len    INTEGER;
BEGIN
    -- Get the length of the CLOB
    l_clob_len := DBMS_LOB.getlength(p_ImageData);

    -- Initialize the BLOB
    DBMS_LOB.createtemporary(l_image_blob, TRUE);

    -- Write the CLOB data to the BLOB
    DBMS_LOB.LOADFROMFILE(dest_lob => l_image_blob,
                          src_clob => p_ImageData,
                          amount   => l_clob_len);

    -- Update image data
    UPDATE SACH
    SET AnhSach = l_image_blob
    WHERE MaSach = p_MaSach;

    -- Commit the transaction
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        -- Rollback the transaction
        ROLLBACK;
        -- Raise an application error
        RAISE_APPLICATION_ERROR(-20001, 'Error updating book image: ' || SQLERRM);
END;
/

--------------------------------------------------------T?O TK ADMIN--------------------------------------------------Ch? ch?y ? sys
--CREATE USER DAT_ADMIN IDENTIFIED BY 123;
--GRANT DBA TO DAT_ADMIN;
--GRANT ALL PRIVILEGES TO DAT_ADMIN;
--GRANT SELECT ANY DICTIONARY TO DAT_ADMIN;
/
---------------------------------------------------- PACKAGE ?? T?O PROFILE M?I -------------------------------
create or replace package pkg_add_profile
is
  function ProFile_TonTai(p_name varchar) return BOOLEAN;
  Procedure Add_Profile(p_name varchar);
  function main_add_profile(p_name varchar) return BOOLEAN ;
end pkg_add_profile;
-- BODY
create or replace package body pkg_add_profile is
  function ProFile_TonTai(p_name varchar) return BOOLEAN is
    v_count NUMBER;
  begin
    -- Check if the profile exists
    SELECT COUNT(*)
    INTO v_count
    FROM dba_profiles
    WHERE profile = UPPER(p_name);

    -- If count > 0, profile exists, return TRUE, else return FALSE
    IF v_count > 0 THEN
      RETURN TRUE;
    ELSE
      RETURN FALSE;
    END IF;
  end ProFile_TonTai;

  procedure Add_Profile(p_name varchar) is
begin
  -- Insert a new profile
  execute immediate 'create profile ' || p_name || ' LIMIT 
    SESSIONS_PER_USER DEFAULT 
    CPU_PER_SESSION DEFAULT 
    CPU_PER_CALL DEFAULT 
    CONNECT_TIME DEFAULT 
    IDLE_TIME DEFAULT 
    LOGICAL_READS_PER_SESSION DEFAULT 
    LOGICAL_READS_PER_CALL DEFAULT 
    PRIVATE_SGA DEFAULT 
    FAILED_LOGIN_ATTEMPTS DEFAULT 
    PASSWORD_LIFE_TIME DEFAULT 
    PASSWORD_REUSE_TIME DEFAULT 
    PASSWORD_REUSE_MAX DEFAULT 
    PASSWORD_LOCK_TIME DEFAULT 
    PASSWORD_GRACE_TIME DEFAULT';
end Add_Profile;


  function main_add_profile(p_name varchar) return BOOLEAN is
  begin
    -- Check if profile exists
    if ProFile_TonTai(p_name) then
      return FALSE; -- Profile already exists
    else
      -- Profile doesn't exist, add it
      Add_Profile(p_name);
      return TRUE; -- Added successfully
    end if;
  end main_add_profile;
end pkg_add_profile;
-------------------------------------------------- HAM CAP NHAT PROFILE -----------------------------------------------
/
create or replace procedure alter_profile
(
  p_name varchar2,
  p_role varchar2
)
is
begin
  execute immediate 'ALTER PROFILE ' || p_name || ' LIMIT ' || p_role  ;
exception
  when others then
    dbms_output.put_line('Error occurred: ' || sqlerrm);
end;
/
-------------------------------- CÁC PROCEDURE CHO COMBOBOX -------------------------------------------
CREATE OR REPLACE PROCEDURE LayDS_ProFile(out_cursor OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN out_cursor FOR
    SELECT PROFILE, RESOURCE_NAME, LIMIT 
    FROM dba_profiles;
END LayDS_ProFile;
---------------------------------------------------------------------------------------------------------
create or replace procedure LayDS_DSTableSpace(out_cursor out SYS_REFCURSOR)
is
begin
  OPEN out_cursor for
  select tablespace_name from dba_tablespaces;
end LayDS_DSTableSpace;
----------------------------------------------------------------------------------------------
create or replace procedure LayDS_DSUser(out_cursor out SYS_REFCURSOR)
is
begin
  OPEN out_cursor for
  select USERNAME , DEFAULT_TABLESPACE , PROFILE from dba_users where ACCOUNT_STATUS = 'OPEN';
end LayDS_DSUser;
/
-------------------------------------------------------------Add user------------------------------------------------------------------------
create or replace procedure add_user_1
(
  p_name varchar2,
  p_tablespace varchar2,
  p_quota INT,
  p_profile varchar2
)
is 
begin
  execute immediate 'Create User ' || p_name || ' identified by ' || p_name
  || ' default tablespace ' || p_tablespace || ' quota ' || p_quota || 'M on ' || p_tablespace  || ' Profile ' || p_profile ;
end;

-------------------------------------------------------------Dang Ky-------------------------------------------------------------
CREATE OR REPLACE FUNCTION KiemTra_UserTonTai (
    TaiKhoan IN VARCHAR2
) RETURN BOOLEAN
IS
    UserCount INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO UserCount
    FROM all_users    
    WHERE username = UPPER(TaiKhoan);

    IF UserCount > 0 THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
/
-- procedure thêm user m?i 
CREATE OR REPLACE PROCEDURE Them_User (
    TaiKhoan IN VARCHAR2,
    MatKhau IN VARCHAR2
)
IS
BEGIN
    BEGIN
        -- Create the user with the provided username and password
        EXECUTE IMMEDIATE 'CREATE USER ' || TaiKhoan || ' IDENTIFIED BY ' || MatKhau;
        
    EXCEPTION
        WHEN OTHERS THEN
             RAISE_APPLICATION_ERROR(-20002, 'Khong tao duoc user');
            RAISE;
    END;
    
    BEGIN
        -- Grant the khachhang role to the newly created user
        EXECUTE IMMEDIATE 'GRANT khachhang TO ' || TaiKhoan;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Khong gan duoc quyen');
            RAISE;
    END;
    COMMIT;
END;
/
