package GUIs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaContrasenias extends JFrame {

    private JTable tablaContrasenias;
    private JTextField campoBusqueda;
    private JButton botonCrear;
    private JButton botonModificar;
    private JButton botonEliminar;

    public VentanaListaContrasenias() {
        setTitle("Gestor de Contraseñas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(new Color(210, 240, 240)); // Fondo celeste claro
        add(panelPrincipal);

        // Panel superior con el campo de búsqueda
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(210, 240, 240));

        JLabel etiquetaBuscar = new JLabel(new ImageIcon("ruta_icono_buscar.png")); // Ruta del icono de búsqueda
        campoBusqueda = new JTextField(20);
        panelSuperior.add(etiquetaBuscar);
        panelSuperior.add(campoBusqueda);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Tabla para mostrar contraseñas
        String[] columnas = {"Plataforma", "Contraseña"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        tablaContrasenias = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaContrasenias);

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Panel lateral con botones
        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBackground(new Color(210, 240, 240));

        botonCrear = new JButton("Crear");
        botonModificar = new JButton("Modificar");
        botonEliminar = new JButton("Eliminar");

        botonCrear.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonModificar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelLateral.add(Box.createVerticalStrut(20));
        panelLateral.add(botonCrear);
        panelLateral.add(Box.createVerticalStrut(10));
        panelLateral.add(botonModificar);
        panelLateral.add(Box.createVerticalStrut(10));
        panelLateral.add(botonEliminar);
        panelLateral.add(Box.createVerticalGlue());

        panelPrincipal.add(panelLateral, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaListaContrasenias ventana = new VentanaListaContrasenias();
            ventana.setVisible(true);
        });
    }
}
