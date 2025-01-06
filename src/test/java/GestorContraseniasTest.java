import Controlador.GestorContrasenias;
import Data.GestionArchivos;
import Modelo.Usuario;
import Utils.Cifrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorContraseniasTest {

    private GestionArchivos gestionArchivosMock;
    private Cifrador cifrador;
    private GestorContrasenias gestorContrasenias;

    @BeforeEach
    void setUp() {
        gestionArchivosMock = mock(GestionArchivos.class);
        cifrador = new Cifrador("claveSegura123");
        gestorContrasenias = new GestorContrasenias(gestionArchivosMock, cifrador);

    }

    @Test
    void testRegistrarUsuario() {

        assertTrue(gestorContrasenias.registrarUsuario("usuario1", "password123"));
        assertFalse(gestorContrasenias.registrarUsuario("usuario1", "otraPassword"));
    }

    @Test
    void testLoguearse() throws Exception {

        Cifrador cifradorMock = mock(Cifrador.class);
        GestorContrasenias gestor = new GestorContrasenias(gestionArchivosMock, cifradorMock);

        String contraseniaMaestra = "password123";
        String contraseniaCifrada = "hashSimulado";

        when(cifradorMock.cifrar(contraseniaMaestra)).thenReturn(contraseniaCifrada);
        when(cifradorMock.cifrar("claveIncorrecta")).thenReturn("otroHash");

        gestor.registrarUsuario("usuario1", contraseniaMaestra);
        when(cifradorMock.cifrar(contraseniaMaestra)).thenReturn(contraseniaCifrada);
        assertTrue(gestor.loguearse("usuario1", contraseniaMaestra),
                "la contraseña maestra deberia ser valida.");
        assertFalse(gestor.loguearse("usuario1", "claveIncorrecta"),
                "una contraseña incorrecta no deberia ser valida.");

    }

    @Test
    void testGuardarYcargarUsuarios() throws Exception {

        Usuario usuarioMock = mock(Usuario.class);
        List<Usuario> usuariosMock = List.of(usuarioMock);

        when(gestionArchivosMock.readData()).thenReturn(usuariosMock);
        gestorContrasenias.cargarUsuarios();

        verify(gestionArchivosMock, atLeastOnce()).readData();
        assertEquals(1, gestorContrasenias.getUsuarios().size(),
                "deberia cargar un usuario desde el archivo.");

        gestorContrasenias.guardarUsuarios();
        verify(gestionArchivosMock, times(1)).writeData(any());
    }
}