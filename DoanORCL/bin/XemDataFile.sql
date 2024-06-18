------------------------------------------------Xem session----------------------------------------------
CREATE OR REPLACE PROCEDURE GetSessionInfo(p_cursor OUT SYS_REFCURSOR) IS
BEGIN
    OPEN p_cursor FOR
    SELECT sid, serial#, username, program
    FROM v$session;
END;
/
------------------------------------------------Xem thông tin user----------------------------------------------
CREATE OR REPLACE PROCEDURE GetUserInformation (
    p_username IN VARCHAR2,
    p_name OUT VARCHAR2,
    p_create_date OUT DATE,
    p_expire_date OUT DATE,
    p_status OUT VARCHAR2,
    p_last_login OUT TIMESTAMP,
    p_profile OUT VARCHAR2
)
AS
BEGIN
    select username, created, expiry_date, account_status, last_login, profile
    INTO p_name, p_create_date, p_expire_date, p_status, p_last_login, p_profile
    FROM dba_users
    WHERE username = p_username;
END;
/
------------------------------------------------Xen user----------------------------------------------
CREATE OR REPLACE PROCEDURE GetUserNames (p_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN p_cursor FOR
    SELECT username
    FROM dba_users;
END;
/
------------------------------------------------Xem session----------------------------------------------
CREATE OR REPLACE PROCEDURE GetSessionInfo(p_cursor OUT SYS_REFCURSOR) AS 
BEGIN
    OPEN p_cursor FOR 
    SELECT sid, serial#, username, program
    FROM v$session
    WHERE type != 'BACKGROUND';
END;
/
------------------------------------------------Xem session(noBackGround)----------------------------------------------
CREATE OR REPLACE PROCEDURE GetSessionInfoNBG(p_cursor OUT SYS_REFCURSOR) AS 
BEGIN
    OPEN p_cursor FOR 
    SELECT sid, serial#, username, program
    FROM v$session;
END;
/
------------------------------------------------Xem process----------------------------------------------
CREATE OR REPLACE PROCEDURE GetProcessesInfo(p_cursor OUT SYS_REFCURSOR) AS 
BEGIN
    OPEN p_cursor FOR 
    SELECT s.sid, s.serial#, s.username, s.program, p.pid, p.spid
    FROM v$process p, v$session s
    WHERE p.addr = s.paddr AND s.type != 'BACKGROUND';
END;
/
------------------------------------------------Xem tablespace----------------------------------------------
CREATE OR REPLACE PROCEDURE ShowTablespace(
    p_user IN VARCHAR2,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
    SELECT OWNER, TABLESPACE_NAME
    FROM DBA_SEGMENTS
    WHERE OWNER = p_user
    GROUP BY OWNER, TABLESPACE_NAME;
END;
/
------------------------------------------------Xem t?t c? tablespace----------------------------------------------
CREATE OR REPLACE PROCEDURE ShowAllTablespaces(p_cursor OUT SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT OWNER, TABLESPACE_NAME 
    FROM DBA_SEGMENTS 
    GROUP BY OWNER, TABLESPACE_NAME;
END;
/
------------------------------------------------Xem datafile c?a tablespace----------------------------------------------
CREATE OR REPLACE PROCEDURE ShowDatafiles(
    p_tablespace_name IN VARCHAR2,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
        OPEN p_cursor FOR
        SELECT FILE_NAME, FILE_ID, TABLESPACE_NAME
        FROM DBA_DATA_FILES
        WHERE TABLESPACE_NAME = p_tablespace_name;
END;
/
------------------------------------------------Xem ALLDataFIle----------------------------------------------
Create or Replace Procedure ShowAllDataFile(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT FILE_NAME, FILE_ID, TABLESPACE_NAME
    FROM DBA_DATA_FILES;
END;
/
---------------------------------------------------------------Show databaseInfo-----------------------------------------------
Create or Replace Procedure ShowDabaseInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT NAME, CREATED, LOG_MODE
    FROM v$database;
END;
/
-----------------------------------------------------Show SGAInfo----------------------------------------------------
Create or Replace Procedure ShowSGAInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM v$sga;
END;
/
-----------------------------------------------------Show PGAInfo-------------------------------------------------
Create or Replace Procedure ShowPGAInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM v$pgastat;
END;
/
--------------------------------------------------Show ProcessInfo----------------------------------------------
Create or Replace Procedure ShowProcessInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM v$process;
END;

---------------------------------------------------------Show InstanceInfo------------------------------------------------
Create or Replace Procedure ShowInstanceInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM v$instance;
END;
/
---------------------------------------------------------Show DatafileInfo-------------------------------------------------------
Create or Replace Procedure ShowDatafileInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT FILE_NAME, FILE_ID, TABLESPACE_NAME, BYTES, STATUS FROM dba_data_files;
END;
/
--------------------------------------------------------------------Show ControlFileInfo---------------------------------------------------------
Create or Replace Procedure ShowControlFileInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM v$controlfile;
END;
/
--------------------------------------------------------------Show SPFileInfo---------------------------------------------------------------
Create or Replace Procedure SPFileInfo(p_cursor Out SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM v$spparameter;
END;
/
