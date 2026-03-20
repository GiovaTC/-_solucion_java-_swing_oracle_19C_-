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
    }


}