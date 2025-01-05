package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCrearContrasenia {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> crearVentana());
    }

    public static void crearVentana() {
        // Crear el marco principal
        JFrame frame = new JFrame("Crear Contraseña");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(210, 250, 210)); // Fondo verde claro
        frame.add(panel);

        // Etiqueta "Crear contraseña"
        JLabel tituloLabel = new JLabel("Crear contraseña");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setBounds(220, 20, 200, 30);
        panel.add(tituloLabel);

        // Campo para ingresar nombre de la plataforma
        JLabel plataformaLabel = new JLabel("Ingrese Nombre de Plataforma");
        plataformaLabel.setBounds(50, 80, 200, 20);
        panel.add(plataformaLabel);

        JTextField plataformaField = new JTextField();
        plataformaField.setBounds(50, 110, 300, 30);
        panel.add(plataformaField);

        JButton listoPlataformaButton = new JButton("Listo");
        listoPlataformaButton.setBounds(370, 110, 100, 30);
        listoPlataformaButton.setBackground(new Color(100, 150, 255)); // Azul
        listoPlataformaButton.setForeground(Color.WHITE);
        panel.add(listoPlataformaButton);

        // Campo para escribir contraseña
        JLabel contrasenaLabel = new JLabel("Escriba su contraseña");
        contrasenaLabel.setBounds(50, 180, 200, 20);
        panel.add(contrasenaLabel);

        JPasswordField contrasenaField = new JPasswordField();
        contrasenaField.setBounds(50, 210, 300, 30);
        panel.add(contrasenaField);

        JButton listoContrasenaButton = new JButton("Listo");
        listoContrasenaButton.setBounds(370, 210, 100, 30);
        listoContrasenaButton.setBackground(new Color(100, 150, 255)); // Azul
        listoContrasenaButton.setForeground(Color.WHITE);
        panel.add(listoContrasenaButton);

        // Botón para generar contraseña segura
        JButton generarContrasenaButton = new JButton("Click aquí");
        generarContrasenaButton.setBounds(370, 260, 100, 30);
        generarContrasenaButton.setBackground(new Color(50, 200, 100)); // Verde
        generarContrasenaButton.setForeground(Color.WHITE);
        panel.add(generarContrasenaButton);

        // Acción del botón "Generar contraseña segura"
        generarContrasenaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contrasenaGenerada = generarContrasenaSegura();
                JOptionPane.showMessageDialog(frame, "Contraseña generada: " + contrasenaGenerada,
                        "Contraseña Segura", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Hacer visible la ventana
        frame.setVisible(true);
    }

    // Método para generar una contraseña segura
    public static String generarContrasenaSegura() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder contrasena = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = (int) (Math.random() * caracteres.length());
            contrasena.append(caracteres.charAt(index));
        }
        return contrasena.toString();
    }
}

