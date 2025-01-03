package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.GestorContrasenias;
import Modelo.Usuario;

public class VentanaListaContraseñas extends JFrame {
    private JList<String> listaContraseñas;
    private JButton crearContraseñaButton;
    private GestorContrasenias gestor;

    public VentanaListaContraseñas(GestorContrasenias gestor) {
        this.gestor = gestor;

        setTitle("Lista de Contraseñas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Usuario usuario = gestor.getUsuarioIniciado();
        DefaultListModel<String> model = new DefaultListModel<>();
        usuario.getContrasenias().forEach((plataforma, contrasenia) -> model.addElement(plataforma));
        listaContraseñas = new JList<>(model);
        add(new JScrollPane(listaContraseñas), BorderLayout.CENTER);

        crearContraseñaButton = new JButton("Crear Nueva Contraseña");
        add(crearContraseñaButton, BorderLayout.SOUTH);

        crearContraseñaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaCrearContraseña(gestor).setVisible(true);
            }
        });
    }
}
