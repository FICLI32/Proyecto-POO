package GUIs;

import Controlador.GestorContrasenias;
import Modelo.Contrasenia;
import Utils.Cifrador;
import Utils.LimitarCaracteres;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaCrearContrasenia extends JFrame {
    private JTextField txtPlataforma;
    private JPasswordField txtContrasena;
    private JButton btnGenerar, btnGuardar;

    private JLabel lblRequisitoLongitud;
    private JLabel lblRequisitoMayuscula;
    private JLabel lblRequisitoNumero;
    private JLabel lblRequisitoSimbolo;

    private GestorContrasenias gestorContrasenias;
    private Cifrador cifrador;
    private VentanaListaContrasenias ventanaLista;

    public VentanaCrearContrasenia(GestorContrasenias gestorContrasenias, Cifrador cifrador, VentanaListaContrasenias ventanaLista) {
        this.gestorContrasenias = gestorContrasenias;
        this.cifrador = cifrador;
        this.ventanaLista = ventanaLista;

        setTitle("Crear Contraseña");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(210, 250, 210));
        add(panel);

        JLabel lblPlataforma = new JLabel("Plataforma:");
        lblPlataforma.setBounds(50, 30, 100, 20);
        panel.add(lblPlataforma);

        txtPlataforma = new JTextField();
        txtPlataforma.setBounds(50, 60, 300, 30);
        ((javax.swing.text.AbstractDocument) txtPlataforma.getDocument()).setDocumentFilter(new LimitarCaracteres(20));
        panel.add(txtPlataforma);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(50, 100, 100, 20);
        panel.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(50, 130, 300, 30);
        ((javax.swing.text.AbstractDocument) txtPlataforma.getDocument()).setDocumentFilter(new LimitarCaracteres(20));
        panel.add(txtContrasena);

        // Etiquetas para los requisitos de la contraseña
        lblRequisitoLongitud = new JLabel("Debe incluir al menos 10 caracteres.");
        lblRequisitoLongitud.setBounds(50, 170, 300, 20);
        lblRequisitoLongitud.setForeground(Color.RED);
        panel.add(lblRequisitoLongitud);

        lblRequisitoMayuscula = new JLabel("Debe incluir al menos una letra mayúscula.");
        lblRequisitoMayuscula.setBounds(50, 190, 300, 20);
        lblRequisitoMayuscula.setForeground(Color.RED);
        panel.add(lblRequisitoMayuscula);

        lblRequisitoNumero = new JLabel("Debe incluir al menos un número.");
        lblRequisitoNumero.setBounds(50, 210, 300, 20);
        lblRequisitoNumero.setForeground(Color.RED);
        panel.add(lblRequisitoNumero);

        lblRequisitoSimbolo = new JLabel("Debe incluir al menos un símbolo.");
        lblRequisitoSimbolo.setBounds(50, 230, 300, 20);
        lblRequisitoSimbolo.setForeground(Color.RED);
        panel.add(lblRequisitoSimbolo);

        btnGenerar = new JButton("Generar Contraseña");
        btnGenerar.setBounds(50, 270, 150, 30);
        btnGenerar.setBackground(new Color(50, 200, 100));
        btnGenerar.setForeground(Color.WHITE);
        panel.add(btnGenerar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(220, 270, 130, 30);
        btnGuardar.setBackground(new Color(50, 150, 255));
        btnGuardar.setForeground(Color.WHITE);
        panel.add(btnGuardar);

        agregarEventos();
    }

    private void agregarEventos() {
        btnGenerar.addActionListener(e -> {
            String contraseniaGenerada = Contrasenia.generarContraseniaSegura();
            txtContrasena.setText(contraseniaGenerada);
            actualizarRequisitos(contraseniaGenerada);
        });

        btnGuardar.addActionListener(e -> {
            String plataforma = txtPlataforma.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (plataforma.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
                return;
            }

            if (gestorContrasenias.getUsuarioIniciado().getContrasenias().containsKey(plataforma)) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "La plataforma ingresada ya existe. ¿Desea sobrescribirla?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            if (!esContrasenaSegura(contrasena)) {
                JOptionPane.showMessageDialog(this, "La contraseña no cumple con los requisitos de seguridad.");
                return;
            }

            try {
                Contrasenia nuevaContrasenia = new Contrasenia(contrasena);
                nuevaContrasenia.cifrarContrasenia(cifrador);

                gestorContrasenias.getUsuarioIniciado().agregarContrasenia(plataforma, nuevaContrasenia, cifrador);
                gestorContrasenias.guardarUsuarios();

                JOptionPane.showMessageDialog(this, "Contraseña guardada exitosamente.");
                ventanaLista.cargarContrasenias();
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar la contraseña.");
            }
        });

        txtContrasena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String contrasena = new String(txtContrasena.getPassword());
                actualizarRequisitos(contrasena);
            }
        });
    }

    private void actualizarRequisitos(String contrasena) {
        lblRequisitoLongitud.setForeground(contrasena.length() >= 10 ? new Color(0, 128, 0) : Color.RED);
        lblRequisitoMayuscula.setForeground(tieneMayusculas(contrasena) ? new Color(0, 128, 0) : Color.RED);
        lblRequisitoNumero.setForeground(tieneNumeros(contrasena) ? new Color(0, 128, 0) : Color.RED);
        lblRequisitoSimbolo.setForeground(tieneSimbolos(contrasena) ? new Color(0, 128, 0) : Color.RED);
    }

    private boolean esContrasenaSegura(String contrasena) {
        return contrasena.length() >= 10 && tieneMayusculas(contrasena) && tieneNumeros(contrasena) && tieneSimbolos(contrasena);
    }

    private boolean tieneMayusculas(String contrasena) {
        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) return true;
        }
        return false;
    }

    private boolean tieneNumeros(String contrasena) {
        for (char c : contrasena.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }

    private boolean tieneSimbolos(String contrasena) {
        for (char c : contrasena.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return true;
        }
        return false;
    }
}




