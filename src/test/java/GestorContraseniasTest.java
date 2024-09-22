
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestorContraseniasTest {
    private SecretKey claveCifrado;
    private Map<String, String> contrasenias;

    @BeforeEach
    public void setUp() throws Exception {
        claveCifrado = GestorContrasenias.generarClaveCifrado();//Genera una clave antes de cada prueba
        contrasenias = new HashMap<>();//Inicai el mapa vacio de contraseñas
    }

    @Test
    public void crearContraseniaTest() throws Exception {
        //simular la entrada del usuario para crear la contraseña
        String input = "1\nEtiquetaPrueba\nContrasenia123\n5\n"; //crea, etiqueta, contraseña
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        //ejecutamo el menu
        GestorContrasenias.menu(scanner, claveCifrado, contrasenias);
        //verificar que se haya cifraado y almacenado
        assertTrue(contrasenias.containsKey("EtiquetaPrueba"));
        String contraseniaCifrada = contrasenias.get("EtiquetaPrueba");
        assertNotNull(contraseniaCifrada);
    }

    @Test
    public void eliminarContraseniaTest() throws Exception {
        //añade una contraseña para despues eliminarla
        contrasenias.put("EtiquetaEliminar", GestorContrasenias.cifrarContrasenia(claveCifrado, "ContraseniaEliminar"));

        //Simula la entrada del usuario para eliminar
        String input = "2\nEtiquetaEliminar\n5\n"; //elimina, etiqueta a eliminar
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //ejecutar menu
        GestorContrasenias.menu(scanner, claveCifrado, contrasenias);
        //verificar si se elimino
        assertFalse(contrasenias.containsKey("EtiquetaEliminar"));
    }

    @Test
    public void buscarContraseniaTest() throws Exception {
        Map<String, String> contrasenias = new HashMap<>();
        //añadir contraseña para buscarla
        String contrasenia = "Buscar123";
        contrasenias.put("EtiquetaBuscar", GestorContrasenias.cifrarContrasenia(claveCifrado, contrasenia));
        //simulams la entrada
        String input = "3\nEtiquetaBuscar\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        //ejecuta el menu
        GestorContrasenias.menu(scanner, claveCifrado, contrasenias);
        //verificamos
        String contraseniaCifrada = contrasenias.get("EtiquetaBuscar");
        String contraseniaDescifrada = GestorContrasenias.descifrarContrasenia(claveCifrado, contraseniaCifrada);
        assertEquals(contrasenia, contraseniaDescifrada);
    
    }

    public void listarContraseniasTest() throws Exception {
        //añadimos algunas contraseñas
        contrasenias.put("Etiqueta1", GestorContrasenias.cifrarContrasenia(claveCifrado, "Contrasenia1"));
        contrasenias.put("Etiqueta2", GestorContrasenias.cifrarContrasenia(claveCifrado, "Contrasenia2"));
        //Simula la entrada para listar
        String input = "4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        //Ejecuta el menu
        GestorContrasenias.menu(scanner, claveCifrado, contrasenias);
        //Verifica si existen
        assertTrue(contrasenias.containsKey("Etiqueta1"));
        assertTrue(contrasenias.containsKey("Etiqueta2"));
    }
}

    private SecretKey claveCifrada;
    private String contraseniaOriginal;
    private String contraseniaCifrada;

    @BeforeEach
    void cargarCifrado() throws Exception {
        contraseniaOriginal = "claveultrasecreta123";
        claveCifrada = GestorContrasenias.generarClaveCifrado();
        contraseniaCifrada = GestorContrasenias.cifrarContrasenia(claveCifrada, contraseniaOriginal);
    }

    @Test
    void descifrarContrasenia() throws Exception {
        String contraseniaDescifrada = GestorContrasenias.descifrarContrasenia(claveCifrada, contraseniaCifrada);
        assertEquals(contraseniaOriginal, contraseniaDescifrada);
    }
}
