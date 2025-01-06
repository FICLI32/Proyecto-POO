package GUIs;

import Controlador.GestorContrasenias;
import Modelo.Contrasenia;
import Utils.Cifrador;
import Utils.LimitarCaracteres;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class VentanaListaContrasenias extends JFrame {
    private JTable tablaContrasenias;
    private DefaultTableModel modeloTabla;
    private GestorContrasenias gestorContrasenias;
    private Cifrador cifrador;

    public VentanaListaContrasenias(GestorContrasenias gestorContrasenias, Cifrador cifrador) {
        this.gestorContrasenias = gestorContrasenias;
        this.cifrador = cifrador;

        setTitle("Gestor de Contraseñas");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        cargarContrasenias();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(210, 250, 210));
        add(panelPrincipal);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(210, 250, 210));
        JLabel lblBuscar = new JLabel("Buscar: ");
        JTextField txtBuscar = new JTextField(20); // Campo de búsqueda
        ((javax.swing.text.AbstractDocument) txtBuscar.getDocument()).setDocumentFilter(new LimitarCaracteres(20));
        panelSuperior.add(lblBuscar);
        panelSuperior.add(txtBuscar);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {"Plataforma", "Contraseña", "Fecha de Creación"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaContrasenias = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaContrasenias);

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");

        panelLateral.add(btnCrear);
        panelLateral.add(btnModificar);
        panelLateral.add(btnEliminar);

        panelPrincipal.add(panelLateral, BorderLayout.EAST);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaContrasenias.setRowSorter(sorter);

        sorter.setComparator(2, (fecha1, fecha2) -> {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                Date date1 = formatoFecha.parse(fecha1.toString());
                Date date2 = formatoFecha.parse(fecha2.toString());
                return date1.compareTo(date2);
            } catch (Exception e) {
                return 0;
            }
        });

        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabla();
            }

            private void filtrarTabla() {
                String texto = txtBuscar.getText();
                if (texto.trim().isEmpty()) {
                    sorter.setRowFilter(null); // Mostrar todas las filas si el campo está vacío
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto)); // Filtro por texto (ignora mayúsculas/minúsculas)
                }
            }
        });

        btnCrear.addActionListener(e -> abrirVentanaCrearContrasenia());
        btnModificar.addActionListener(e -> modificarContrasenia());
        btnEliminar.addActionListener(e -> eliminarContrasenia());
    }



    public void cargarContrasenias() {
        modeloTabla.setRowCount(0);
        Map<String, Contrasenia> contrasenias = gestorContrasenias.getUsuarioIniciado().getContrasenias();

        for (Map.Entry<String, Contrasenia> entry : contrasenias.entrySet()) {
            String plataforma = entry.getKey();
            String contraseniaDescifrada;
            String fechaCreacion;

            try {
                contraseniaDescifrada = entry.getValue().descifrarContrasenia(cifrador);
                fechaCreacion = entry.getValue().getFechaCreacion();
            } catch (Exception e) {
                contraseniaDescifrada = "Error al descifrar";
                fechaCreacion = "N/A";
            }

            modeloTabla.addRow(new Object[]{plataforma, contraseniaDescifrada, fechaCreacion});
        }
    }

    private void abrirVentanaCrearContrasenia() {
        new VentanaCrearContrasenia(gestorContrasenias, cifrador, this).setVisible(true);
    }

    private void modificarContrasenia() {
        int selectedRow = tablaContrasenias.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una plataforma para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String plataforma = modeloTabla.getValueAt(selectedRow, 0).toString();

        String nuevaPlataforma = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre de la plataforma (o deje en blanco para no cambiar):");
        String nuevaContrasenia = JOptionPane.showInputDialog(this, "Ingrese la nueva contraseña (o deje en blanco para no cambiar):");

        if (nuevaPlataforma != null && !nuevaPlataforma.isEmpty()) {
            gestorContrasenias.getUsuarioIniciado().eliminarContrasenia(plataforma);
            plataforma = nuevaPlataforma;
        }

        if (nuevaContrasenia != null && !nuevaContrasenia.isEmpty()) {
            try {
                Contrasenia contrasenia = new Contrasenia(nuevaContrasenia);
                contrasenia.cifrarContrasenia(cifrador);
                gestorContrasenias.getUsuarioIniciado().agregarContrasenia(plataforma, contrasenia, cifrador); // Corrigiendo firma
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cifrar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        gestorContrasenias.guardarUsuarios();
        cargarContrasenias();
    }

    private void eliminarContrasenia() {
        int selectedRow = tablaContrasenias.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una contraseña para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String plataforma = modeloTabla.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar la contraseña para " + plataforma + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gestorContrasenias.getUsuarioIniciado().eliminarContrasenia(plataforma);
            gestorContrasenias.guardarUsuarios();
            cargarContrasenias();
        }
    }

}

