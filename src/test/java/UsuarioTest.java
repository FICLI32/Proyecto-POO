import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    private Usuario usuario;

    @BeforeEach
    void setUp() throws Exception{
        usuario = new Usuario("001","Juan", "contraseniaMaestra1");
    }

    @Test
    void verificarContraseniaMaestra() throws Exception {
        assertTrue(usuario.verificarContraseniaMaestra("contraseniaMaestra1"));
        assertFalse(usuario.verificarContraseniaMaestra("noContraseniaMaestra"));
    }

    @Test
    void testAgregarYObtenerContrasenia() throws Exception{
        Contrasenia contraseniaGmail = new Contrasenia("contraseniaGmail");
        String contraseniaMaestra = "contraseniaMaestra1";

        usuario.agregarContrasenia("Gmail", contraseniaGmail, contraseniaMaestra);
        String contraseniaDescifrada = usuario.obtenerContrasenia("Gmail", contraseniaMaestra);

        assertEquals("contraseniaGmail", contraseniaDescifrada);
    }

    @Test
    void eliminarContrasenia() throws Exception {
        Contrasenia contraseniaDiscord = new Contrasenia("Discord123");
        String contraseniaMaestra = "contraseniaMaestra1";

        usuario.agregarContrasenia("Discord", contraseniaDiscord,contraseniaMaestra);
        usuario.eliminarContrasenia("Discord");

        assertNull(usuario.obtenerContrasenia("Discord",contraseniaMaestra));
    }
}