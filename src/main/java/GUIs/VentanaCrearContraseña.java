package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.Contrasenia;
import Modelo.GestorContrasenias;

public class VentanaCrearContraseña extends JFrame {
    private JTextField plataformaField;
    private JTextField usuarioField;
    private JPasswordField contraseniaField;
    private JButton guardarButton;
    private GestorContrasenias gestor;

    public VentanaCrearContraseña(GestorContrasenias gestor) {
        this.gestor = gestor;

        setTitle("Crear Contraseña");
        setSize(300, 200);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Plataforma:"));
        plataformaField = new JTextField();
        add(plataformaField);

        add(new JLabel("Usuario:"));
        usuarioField = new JTextField();
        add(usuarioField);

        add(new JLabel("Contraseña:"));
        contraseniaField = new JPasswordField();
        add(contraseniaField);

        guardarButton = new JButton("Guardar");
        add(guardarButton);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String plataforma = plataformaField.getText();
                    String usuario = usuarioField.getText();
                    String contraseniaTexto = new String(contraseniaField.getPassword());
                    Contrasenia contrasenia = new Contrasenia(contraseniaTexto);

                    gestor.getUsuarioIniciado().agregarContrasenia(plataforma, contrasenia, contraseniaTexto);
                    JOptionPane.showMessageDialog(null, "Contraseña guardada.");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

