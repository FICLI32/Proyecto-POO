package GUIs;

import Modelo.GestorContrasenias;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistroUsuario extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contraseniaField;
    private JButton registrarButton;
    private GestorContrasenias gestor;

    public VentanaRegistroUsuario(GestorContrasenias gestor) {
        this.gestor = gestor;

        setTitle("Registro de Usuario");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuario:"));
        usuarioField = new JTextField();
        add(usuarioField);

        add(new JLabel("Contraseña:"));
        contraseniaField = new JPasswordField();
        add(contraseniaField);

        registrarButton = new JButton("Registrar");
        add(registrarButton);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idUsuario = usuarioField.getText();
                String contrasenia = new String(contraseniaField.getPassword());
                if (idUsuario.isEmpty() || contrasenia.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    return;
                }

                if (gestor.registrarUsuario(idUsuario, contrasenia)) {
                    JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ya existe. Intente con otro nombre.");
                }
            }
        });
    }
}
