import Modelo.Contrasenia;
import Utils.Cifrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContraseniaTest {

    private Contrasenia contrasenia;
    private Cifrador cifrador;

    @BeforeEach
    void setUp() {
        contrasenia = new Contrasenia("clavesegura123");
        cifrador = new Cifrador("clavemaestra123");
    }


    @Test
    void testCifradoYDescifrado() throws Exception {
        String textoOriginal = "clavesegura123";
        String textoCifrado = cifrador.cifrar(textoOriginal);
        String textoDescifrado = cifrador.descifrar(textoCifrado);

        System.out.println("Texto origina: " + textoOriginal);
        System.out.println("Texto cifrado: " + textoCifrado);
        System.out.println("Texto descifrado: " + textoDescifrado);
        assertNotEquals(textoOriginal, textoCifrado, "El texto cifrado no deberia ser igual al texto original.");
        assertEquals(textoOriginal, textoDescifrado, "El texto descifrado deberia ser igual al texto original.");
    }


    @Test
    void generarContraseniaSegura() {
        String contraseniaGenerada = Contrasenia.generarContraseniaSegura();
        assertTrue(contraseniaGenerada.length() >= 16);
    }
}
