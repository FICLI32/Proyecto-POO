import Data.GestionArchivos;
import Modelo.Usuario;
import Utils.Cifrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionArchivosTest {

    private GestionArchivos gestionArchivos;
    private Cifrador cifrador;
    private File archivo;

    @BeforeEach
    void setUp() {
        cifrador = new Cifrador("claveSegura123");
        gestionArchivos = new GestionArchivos(cifrador);
        archivo = new File("usuarios.json");
    }

    @Test
    void testEscribirYLeerDatos() throws Exception {
        Usuario usuario = new Usuario("usuario1", "Usuario prueba", "password123", cifrador);
        List<Usuario> usuarios = List.of(usuario);

        gestionArchivos.writeData(usuarios);
        assertTrue(archivo.exists());

        List<Usuario> usuariosLeidos = gestionArchivos.readData();
        assertEquals(1, usuariosLeidos.size());
        assertEquals("usuario1", usuariosLeidos.get(0).getIdUsuario());
    }

    @Test
    void testValidarClave() throws Exception {
        Usuario usuario = new Usuario("usuario1", "Uusuario prueba", "password123", cifrador);
        gestionArchivos.writeData(List.of(usuario));
        assertTrue(gestionArchivos.validarClave(cifrador));
        Cifrador cifradorInvalido = new Cifrador("claveIncorrecta");
        assertFalse(gestionArchivos.validarClave(cifradorInvalido));
    }
}
