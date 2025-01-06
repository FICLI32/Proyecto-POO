package GUIs;

import Controlador.GestorContrasenias;
import Data.GestionArchivos;
import Utils.Cifrador;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VentanaClaveMaestra extends JFrame {
    private JPasswordField txtClaveMaestra;
    private JButton btnAcceder;
    private JLabel lblMensaje;
    private File archivoUsuarios;

    public VentanaClaveMaestra(File archivoUsuarios) {
        this.archivoUsuarios = archivoUsuarios;

        setTitle("Geco Security - Clave Maestra");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();

    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(210, 250, 210));
        add(panel);
        //
        JLabel lblTitulo = new JLabel("Geco Security");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(120, 20, 200, 30);
        panel.add(lblTitulo);
        //
        JLabel lblClaveMaestra = new JLabel("Ingrese la Clave Maestra:");
        lblClaveMaestra.setFont(new Font("Arial", Font.BOLD, 14));
        lblClaveMaestra.setBounds(50, 70, 300, 20);
        panel.add(lblClaveMaestra);


        txtClaveMaestra = new JPasswordField();
        txtClaveMaestra.setBounds(50, 100, 300, 30);
        panel.add(txtClaveMaestra);

        btnAcceder = new JButton("Acceder");
        btnAcceder.setBounds(50, 150, 300, 30);
        btnAcceder.setBackground(new Color(50, 150, 255)); // Azul
        btnAcceder.setForeground(Color.WHITE);
        panel.add(btnAcceder);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setBounds(50, 190, 300, 20);
        panel.add(lblMensaje);

        agregarEventos();

    }

    private void agregarEventos() {
        btnAcceder.addActionListener(e -> {
            String claveMaestra = new String(txtClaveMaestra.getPassword());

            if (claveMaestra.isEmpty()) {
                lblMensaje.setText("Debe ingresar una clave maestra.");
                return;
            }

            try {
                Cifrador cifrador = new Cifrador(claveMaestra);
                GestionArchivos gestionArchivos = new GestionArchivos(cifrador);
                if (archivoUsuarios.exists() && !gestionArchivos.validarClave(cifrador)) {
                    throw new Exception("Clave maestra incorrecta o error al descifrar el archivo.");
                }
                GestorContrasenias gestorContrasenias = new GestorContrasenias(gestionArchivos, cifrador);

                if (!archivoUsuarios.exists()) {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún archivo de usuarios. Registre uno nuevo.");
                    new VentanaUsuario(gestorContrasenias, cifrador).setVisible(true);
                } else {
                    gestorContrasenias.cargarUsuarios();
                    new VentanaUsuario(gestorContrasenias, cifrador).setVisible(true);
                }
                dispose();

            } catch (Exception ex) {
                lblMensaje.setText(ex.getMessage());
                txtClaveMaestra.setText("");
                ex.printStackTrace();
            }
        });
    }
}

