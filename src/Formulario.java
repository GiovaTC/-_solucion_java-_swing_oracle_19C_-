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
        setTitle("consulta oracle 19c - 4000 registros");
        setSize(800,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{
                "ID","NOMBRE","APELLIDO","EDAD","CIUDAD","FECHA"
        });

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        btnConsultar = new JButton("consultar registros");
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
                        fila.add(rs.getString("EDAD"));
                        fila.add(rs.getString("CIUDAD"));
                        fila.add(rs.getString("FECHA_REG"));

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
                JOptionPane.showMessageDialog(null, "carga completa");
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Formulario().setVisible(true));
    }
}