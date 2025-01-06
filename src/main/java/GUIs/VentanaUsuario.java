package GUIs;

import Utils.LimitarCaracteres;
import Controlador.GestorContrasenias;
import Utils.Cifrador;

import javax.swing.*;
import java.awt.*;

public class VentanaUsuario extends JFrame {
    private JTextField txtUsuario;
    private JButton btnAcceder;
    private JLabel lblMensaje;

    private GestorContrasenias gestorContrasenias;
    private Cifrador cifrador;

    public VentanaUsuario(GestorContrasenias gestorContrasenias, Cifrador cifrador) {
        this.gestorContrasenias = gestorContrasenias;
        this.cifrador = cifrador;

        setTitle("Ingrese su Usuario");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(210, 250, 210)); // Fondo verde claro
        add(panel);

        JLabel lblTitulo = new JLabel("Ingrese su Nombre de Usuario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(50, 20, 300, 30);
        panel.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setBounds(50, 70, 120, 20);
        panel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 70, 200, 30);
        ((javax.swing.text.AbstractDocument) txtUsuario.getDocument()).setDocumentFilter(new LimitarCaracteres(20));
        panel.add(txtUsuario);

        btnAcceder = new JButton("Acceder");
        btnAcceder.setBounds(140, 120, 120, 30);
        btnAcceder.setBackground(new Color(50, 150, 255)); // Azul
        btnAcceder.setForeground(Color.WHITE);
        panel.add(btnAcceder);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setBounds(50, 100, 300, 20);
        panel.add(lblMensaje);

        agregarEventos();
    }

    private void agregarEventos() {
        btnAcceder.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            if (usuario.isEmpty()) {
                lblMensaje.setText("Debe ingresar un nombre de usuario.");
                return;
            }

            if (gestorContrasenias.buscarUsuarioId(usuario) == null) {
                int opcion = JOptionPane.showConfirmDialog(
                        this,
                        "No existe un usuario con este nombre. ¿Desea crearlo?",
                        "Usuario no encontrado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    gestorContrasenias.registrarUsuario(usuario, cifrador.getClaveMaestra());
                    gestorContrasenias.setUsuarioIniciado(usuario); // Establecer como usuario activo
                    JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                } else {
                    lblMensaje.setText("");
                    return;
                }
            } else {
                gestorContrasenias.setUsuarioIniciado(usuario);
                JOptionPane.showMessageDialog(this, "Usuario encontrado y configurado como activo.");
            }

            new VentanaListaContrasenias(gestorContrasenias, cifrador).setVisible(true);
            dispose();
        });
    }
}





