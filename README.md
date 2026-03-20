# -_solucion_java-_swing_oracle_19C_- :.
🧩 Solución Java (Swing) + Oracle 19c:

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/e6f10175-e1cb-4758-80ab-b80655426e05" />  

```

Consulta eficiente de 4000 registros sin bloquear la UI (SwingWorker)

Solución completa y funcional con enfoque práctico para Java (Swing en IntelliJ) + Oracle 19c, optimizada para consultar grandes
volúmenes de datos sin afectar la experiencia de usuario .

🧩 1. Script Oracle 19c (Tabla + 4000 registros):
📌 Creación de tabla
CREATE TABLE REGISTROS_EJEMPLO (
    ID          NUMBER PRIMARY KEY,
    NOMBRE      VARCHAR2(100),
    APELLIDO    VARCHAR2(100),
    EDAD        NUMBER,
    CIUDAD      VARCHAR2(100),
    FECHA_REG   DATE
);

📌 Generación masiva de 4000 registros (PL/SQL eficiente):

✔️ Más óptimo que escribir 4000 INSERT manuales

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

🧩 2. Dependencia JDBC (Oracle):

Asegúrate de incluir el driver:
ojdbc8.jar
ojdbc11.jar (dependiendo de tu JDK)

🧩 3. Clase de conexion:
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "TU_USUARIO";
    private static final String PASS = "TU_PASSWORD";

    public static Connection getConexion() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

🧩 4. Interfaz gráfica (Swing):

✔️ Botón: consultar
✔️ Tabla: múltiples registros con scroll
✔️ Carga asincrónica (clave para 4000 registros)

📌 Formulario principal
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class Formulario extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnConsultar;

    public Formulario() {
        setTitle("Consulta Oracle 19c - 4000 registros");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{
                "ID", "NOMBRE", "APELLIDO", "EDAD", "CIUDAD", "FECHA"
        });

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        btnConsultar = new JButton("Consultar Registros");

        btnConsultar.addActionListener(e -> cargarDatos());

        add(scroll, BorderLayout.CENTER);
        add(btnConsultar, BorderLayout.SOUTH);
    }

    private void cargarDatos() {

        btnConsultar.setEnabled(false);
        modelo.setRowCount(0);

        SwingWorker<Void, Vector<Object>> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() throws Exception {

                try (Connection conn = ConexionOracle.getConexion()) {

                    String sql = "SELECT * FROM REGISTROS_EJEMPLO";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {

                        Vector<Object> fila = new Vector<>();
                        fila.add(rs.getInt("ID"));
                        fila.add(rs.getString("NOMBRE"));
                        fila.add(rs.getString("APELLIDO"));
                        fila.add(rs.getInt("EDAD"));
                        fila.add(rs.getString("CIUDAD"));
                        fila.add(rs.getDate("FECHA_REG"));

                        publish(fila);
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Vector<Object>> chunks) {
                for (Vector<Object> fila : chunks) {
                    modelo.addRow(fila);
                }
            }

            @Override
            protected void done() {
                btnConsultar.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Carga completa");
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Formulario().setVisible(true));
    }
}

🧩 5. Buenas prácticas (clave con 4000 registros):
🔹 1. Evitar congelamiento de UI

✔️ Resuelto mediante SwingWorker (ejecución en segundo plano)

🔹 2. Optimización SQL
SELECT /*+ FIRST_ROWS(100) */ * FROM REGISTROS_EJEMPLO;

🔹 3. Paginación (recomendado en producción)
SELECT * FROM (
    SELECT t.*, ROWNUM rn 
    FROM REGISTROS_EJEMPLO t 
    WHERE ROWNUM <= 100
) 
WHERE rn > 0;

🔹 4. Índices (mejora rendimiento)
CREATE INDEX IDX_REG_ID ON REGISTROS_EJEMPLO(ID);

🧩 Resultado final:

✔️ Botón → consulta datos
✔️ Tabla → muestra hasta 4000 registros
✔️ UI fluida (no se bloquea)
✔️ Datos generados automáticamente
✔️ Compatible con Oracle 19c :. . :/. .
