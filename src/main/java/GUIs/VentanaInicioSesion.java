package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.GestorContrasenias;

public class VentanaInicioSesion extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contraseniaField;
    private JButton iniciarSesionButton;
    private GestorContrasenias gestor;
    private JButton registrarButton;


    public VentanaInicioSesion(GestorContrasenias gestor) {
        this.gestor = gestor;

        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Usuario:"));
        usuarioField = new JTextField();
        add(usuarioField);

        add(new JLabel("Contraseña:"));
        contraseniaField = new JPasswordField();
        add(contraseniaField);

        iniciarSesionButton = new JButton("Iniciar Sesión");
        add(iniciarSesionButton);

        registrarButton = new JButton("Registrar");
        add(registrarButton);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaRegistroUsuario(gestor).setVisible(true);
            }
        });

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idUsuario = usuarioField.getText();
                String contrasenia = new String(contraseniaField.getPassword());
                try {
                    if (gestor.loguearse(idUsuario, contrasenia)) {
                        new VentanaListaContraseñas(gestor).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
