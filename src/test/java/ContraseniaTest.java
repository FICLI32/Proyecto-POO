import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContraseniaTest {

    private  Contrasenia contrasenia;

    @BeforeEach
    void setUp(){
        contrasenia = new Contrasenia("claveSegura123");
    }

    @Test
    void testCifrarYDescifrarContrasenia() throws Exception {
        String contraseniaMaestra = "contraseniaMaestra1";

        contrasenia.cifrarContrasenia(contraseniaMaestra);

        assertNotEquals("claveSegura123", contrasenia);

        String contraseniaDescifrada = contrasenia.descifrarContrasenia(contraseniaMaestra);

        assertEquals("claveSegura123", contraseniaDescifrada);
    }

    @Test
    void generarContraseniaSegura() {
        String contraseniaGenerada = Contrasenia.generarContraseniaSegura();

        assertTrue(contraseniaGenerada.length() >= 16);
    }
}
