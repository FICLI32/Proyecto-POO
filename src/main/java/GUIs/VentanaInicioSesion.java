package GUIs;

import Controlador.GestorContrasenias;
import Modelo.Usuario;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class VentanaInicioSesion extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasenia;
    private JButton botonIniciarSesion;
    private JLabel enlaceRegistro;
    private GestorContrasenias gestorContrasenias;

    public VentanaInicioSesion(GestorContrasenias gestorContrasenias) {
        this.gestorContrasenias = gestorContrasenias;

        setTitle("Geco Security");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Configuración del panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(210, 240, 240)); // Fondo celeste claro
        add(panelPrincipal);

        // Título
        JLabel etiquetaTitulo = new JLabel("Geco Security", JLabel.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setBounds(50, 20, 300, 40);
        panelPrincipal.add(etiquetaTitulo);

        // Logo (Placeholder)
        JLabel etiquetaLogo = new JLabel();
        etiquetaLogo.setBounds(125, 80, 150, 150);
        ImageIcon iconoLogo = new ImageIcon("ruta_del_logo_geco.png"); // Coloca aquí la ruta del logo
        etiquetaLogo.setIcon(new ImageIcon(iconoLogo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        panelPrincipal.add(etiquetaLogo);

        // Usuario
        JLabel etiquetaUsuario = new JLabel("Usuario");
        etiquetaUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        etiquetaUsuario.setBounds(50, 260, 300, 20);
        panelPrincipal.add(etiquetaUsuario);

        campoUsuario = new JTextField();
        campoUsuario.setBounds(50, 290, 300, 30);
        panelPrincipal.add(campoUsuario);

        // Contraseña
        JLabel etiquetaContrasenia = new JLabel("Contraseña");
        etiquetaContrasenia.setFont(new Font("Arial", Font.PLAIN, 16));
        etiquetaContrasenia.setBounds(50, 340, 300, 20);
        panelPrincipal.add(etiquetaContrasenia);

        campoContrasenia = new JPasswordField();
        campoContrasenia.setBounds(50, 370, 300, 30);
        panelPrincipal.add(campoContrasenia);

        // Botón iniciar sesión
        botonIniciarSesion = new JButton("Iniciar sesión");
        botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 16));
        botonIniciarSesion.setBounds(50, 430, 300, 40);
        botonIniciarSesion.setBackground(new Color(0, 122, 255));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonIniciarSesion.setFocusPainted(false);
        botonIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelPrincipal.add(botonIniciarSesion);

        // Enlace de registro
        enlaceRegistro = new JLabel("¿No tienes una cuenta? Regístrate");
        enlaceRegistro.setFont(new Font("Arial", Font.PLAIN, 14));
        enlaceRegistro.setBounds(90, 490, 220, 20);
        enlaceRegistro.setForeground(Color.RED);
        enlaceRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelPrincipal.add(enlaceRegistro);

        // Eventos
        botonIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String contrasenia = new String(campoContrasenia.getPassword());

                try {
                    if (gestorContrasenias.loguearse(usuario, contrasenia)) {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
                        VentanaListaContrasenias listaContrasenias = new VentanaListaContrasenias(gestorContrasenias);
                        listaContrasenias.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta. Intente nuevamente.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        enlaceRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VentanaRegistroUsuario registroUsuario = new VentanaRegistroUsuario(gestorContrasenias);
                registroUsuario.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        // Ejemplo de clave para AES (debería ser generada y almacenada de manera segura)
        String claveAES = "1234567890123456"; // Debe ser de 16 bytes
        SecretKeySpec claveCifrado = new SecretKeySpec(claveAES.getBytes(), "AES");

        // Inicialización del gestor de archivos y contraseñas
        Data.GestionArchivos gestionArchivos = new Data.GestionArchivos("usuarios.json", claveCifrado);
        Controlador.GestorContrasenias gestorContrasenias = new Controlador.GestorContrasenias(gestionArchivos);

        // Cargar los usuarios desde el archivo JSON
        gestorContrasenias.cargarUsuarios();

        // Mostrar la ventana de inicio de sesión
        SwingUtilities.invokeLater(() -> {
            VentanaInicioSesion ventana = new VentanaInicioSesion(gestorContrasenias);
            ventana.setVisible(true);
        });
    }
}
