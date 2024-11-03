import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionArchivosTest {

    private GestionArchivos gestionArchivos;
    private SecretKeySpec claveCifrado;
    private File archivo;

    @BeforeEach
    void setUp() throws Exception{
        archivo = File.createTempFile("test",".json");
        claveCifrado = new SecretKeySpec("1234567890123456".getBytes(),"AES");
        gestionArchivos = new GestionArchivos(archivo.getAbsolutePath(), claveCifrado);
    }

    @Test
    void testWriteYReadData() throws Exception {
        Usuario usuario1 = new Usuario("001", "Juan", "ContraseniaMaestra123");
        Usuario usuario2 = new Usuario("002", "Ana", "ContraseniaMaestra1234");
        List<Usuario> usuarios = List.of(usuario1, usuario2);

        gestionArchivos.writeData(usuarios);

        List<Usuario> usuariosCargados = gestionArchivos.readData();

        assertNotNull(usuariosCargados);
        assertEquals(2, usuariosCargados.size());
        assertEquals("Juan", usuariosCargados.get(0).getNombre());
        assertEquals("Ana", usuariosCargados.get(1).getNombre());
    }


}