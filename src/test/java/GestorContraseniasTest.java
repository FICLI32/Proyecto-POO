import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class GestorContraseniasTest {

    private GestorContrasenias gestorContrasenias;
    private GestionArchivos gestionArchivos;
    private SecretKeySpec claveCifrado;
    private File archivo;


    @BeforeEach
    void setUp() throws Exception{
        archivo = File.createTempFile("test",".json");
        claveCifrado = new SecretKeySpec("1234567890123456".getBytes(),"AES");
        gestionArchivos = new GestionArchivos(archivo.getAbsolutePath(), claveCifrado);
        gestorContrasenias = new GestorContrasenias(gestionArchivos);
    }

    @Test
    void testAgregarUsuarioYAutenticacion() throws Exception {
        Usuario usuario = new Usuario("001", "Juan", "ContraseniaMaestra123");
        gestorContrasenias.agregarUsuario(usuario);

        assertNotNull(gestorContrasenias.buscarUsuarioId("001"));
        assertEquals("Juan", gestorContrasenias.buscarUsuarioId("001").getNombre());
    }

    @Test
    void testLoguearse() throws Exception{
        Usuario usuario = new Usuario("001", "Juan", "ContraseniaMaestra123");
        gestorContrasenias.agregarUsuario(usuario);

        //ingreso incorrecto
        assertFalse(gestorContrasenias.loguearse("001","contraseniaMaestraMala"));
        assertNull(gestorContrasenias.getUsuarioIniciado());

        //correcto ingreso
        assertTrue(gestorContrasenias.loguearse("001","ContraseniaMaestra123"));
        assertEquals(usuario, gestorContrasenias.getUsuarioIniciado());
    }

    @Test
    void testGuardarYCargarUsuarios() throws Exception {
        Usuario usuario1 = new Usuario("001", "Juan", "ContraseniaMaestra123");
        Usuario usuario2 = new Usuario("002", "Ana", "ContraseniaMaestra1234");
        gestorContrasenias.agregarUsuario(usuario1);
        gestorContrasenias.agregarUsuario(usuario2);

        //Guardar
        gestorContrasenias.guardarUsuarios();

        GestorContrasenias gestorCargado = new GestorContrasenias(gestionArchivos);
        gestorCargado.cargarUsuarios();

        assertEquals(2, gestorCargado.getUsuarios().size());
        assertNotNull(gestorCargado.buscarUsuarioId("001"));
        assertNotNull(gestorCargado.buscarUsuarioId("002"));
        assertEquals("Juan", gestorCargado.buscarUsuarioId("001").getNombre());
        assertEquals("Ana", gestorCargado.buscarUsuarioId("002").getNombre());
    }
}