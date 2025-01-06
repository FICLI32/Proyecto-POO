import Modelo.Usuario;
import Utils.Cifrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsuarioTest {

    private Usuario usuario;
    private Cifrador cifrador;

    @BeforeEach
    void setUp() throws Exception {
        String claveMaestra = "claveSegura123";
        cifrador = new Cifrador(claveMaestra);
        usuario = new Usuario("usuario1", "Usuario Prueba", claveMaestra, cifrador);

    }

    @Test
    void testVerificarContraseniaMaestra() throws Exception {
        Cifrador cifradorMock = mock(Cifrador.class);
        String contraseniaMaestra = "claveSegura123";
        String contraseniaCifrada = "hashSimulado";
        when(cifradorMock.cifrar(contraseniaMaestra)).thenReturn(contraseniaCifrada);

        Usuario usuario = new Usuario("usuario1", "usuario Test", contraseniaMaestra, cifradorMock);

        when(cifradorMock.cifrar("claveSegura123")).thenReturn(contraseniaCifrada);
        assertTrue(usuario.verificarContraseniaMaestra("claveSegura123", cifradorMock),
                "La contraseña maestra deberia ser valida.");
        when(cifradorMock.cifrar("claveIncorrecta")).thenReturn("otroHash");
        assertFalse(usuario.verificarContraseniaMaestra("claveIncorrecta", cifradorMock),
                "Una contraseña incorrecta no debería ser válida.");

    }

    @Test
    void testAgregarYObtenerContrasenia() throws Exception {

        usuario.agregarContrasenia("Plataforma1", new Modelo.Contrasenia("password123"), cifrador);
        String contrasenia = usuario.obtenerContrasenia("Plataforma1", cifrador);

        assertNotNull(contrasenia);
        assertEquals("password123", contrasenia);
    }

    @Test
    void testEliminarContrasenia() throws Exception {

        usuario.agregarContrasenia("Plataforma1", new Modelo.Contrasenia("password123"), cifrador);
        usuario.eliminarContrasenia("Plataforma1");

        assertNull(usuario.obtenerContrasenia("Plataforma1", cifrador));
    }
}
