CREATE TABLE REGISTROS_EJEMPLO (
    ID          NUMBER PRIMARY KEY,
    NOMBRE      VARCHAR2(100),
    APELLIDO    VARCHAR2(100),
    EDAD        NUMBER,
    CIUDAD      VARCHAR2(100),
    FECHA_REG   DATE
);

--? Generación masiva de 4000 registros (PL/SQL eficiente):

--?? Más óptimo que escribir 4000 INSERT manuales

BEGIN
    FOR i IN 1..4000 LOOP
        INSERT INTO REGISTROS_EJEMPLO (
            ID, NOMBRE, APELLIDO, EDAD, CIUDAD, FECHA_REG
        ) VALUES (
            i,
            'Nombre_' || i,
            'Apellido_' || i,
            TRUNC(DBMS_RANDOM.VALUE(18, 60)),
            'Ciudad_' || MOD(i, 10),
            SYSDATE - MOD(i, 365)
        );
    END LOOP;

    COMMIT;
END;
/

COMMIT;