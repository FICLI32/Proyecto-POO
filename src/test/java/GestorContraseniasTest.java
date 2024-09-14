import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class GestorContraseniasTest {

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