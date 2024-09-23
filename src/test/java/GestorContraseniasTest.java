import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

public class GestorContraseniasTest {

    private SecretKey claveCifrado;
    private Map<String, String> contrasenias;

    @Before
    public void setUp() throws Exception {
        claveCifrado = GestorContrasenias.generarClaveCifrado();
        contrasenias = new HashMap<>();
    }

    @Test
    public void testGenerarContrasenia() {
        String contrasenia = GestorContrasenias.generarContrasenia(10);
        assertNotNull(contrasenia);
        assertEquals(10, contrasenia.length());
    }

    @Test
    public void testAniadirContrasenia() throws Exception {
        String etiqueta = "steam";
        String contrasenia = "contraseña123";

        GestorContrasenias.aniadirContrasenia(etiqueta, contrasenia, claveCifrado, contrasenias);
        assertTrue(contrasenias.containsKey(etiqueta));
    }

    @Test
    public void testEliminarContrasenia() throws Exception {
        String etiqueta = "steam";
        String contrasenia = "contraseña123";
        GestorContrasenias.aniadirContrasenia(etiqueta, contrasenia, claveCifrado, contrasenias);

        GestorContrasenias.eliminarContrasenia(etiqueta, contrasenias);

        assertFalse(contrasenias.containsKey(etiqueta));
    }

    @Test
    public void testMostrarContrasenia() throws Exception {
        String etiqueta = "steam";
        String contrasenia = "contraseña123";
        GestorContrasenias.aniadirContrasenia(etiqueta, contrasenia, claveCifrado, contrasenias);

        String contraseniaDescifrada = GestorContrasenias.descifrarContrasenia(claveCifrado, contrasenias.get(etiqueta));

        assertEquals(contrasenia, contraseniaDescifrada);
    }

    @Test
    public void testListarContrasenias() throws Exception {
        String etiqueta1 = "email";
        String contrasenia1 = "contraseña1234";
        String etiqueta2 = "steam";
        String contrasenia2 = "contraseña123";

        GestorContrasenias.aniadirContrasenia(etiqueta1, contrasenia1, claveCifrado, contrasenias);
        GestorContrasenias.aniadirContrasenia(etiqueta2, contrasenia2, claveCifrado, contrasenias);

        assertEquals(2, contrasenias.size());
    }
}
