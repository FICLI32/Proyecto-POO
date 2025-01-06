package Utils;

import Utils.Cifrador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CifradorTest {

    @Test
    void testCifrarYDescifrar() throws Exception {
        String claveMaestra = "claveSegura123";
        String textoOriginal = "TextoDePrueba";

        Cifrador cifrador = new Cifrador(claveMaestra);

        String textoCifrado = cifrador.cifrar(textoOriginal);
        assertNotNull(textoCifrado, "El texto cifrado no debería ser nulo.");

        String textoDescifrado = cifrador.descifrar(textoCifrado);
        assertEquals(textoOriginal, textoDescifrado, "El texto descifrado debería coincidir con el original.");
    }

    @Test
    void testDescifrarConClaveIncorrecta() {
        String claveMaestraCorrecta = "claveSegura123";
        String claveMaestraIncorrecta = "otraClave";
        String textoOriginal = "TextoDePrueba";

        assertThrows(Exception.class, () -> {
            Cifrador cifradorCorrecto = new Cifrador(claveMaestraCorrecta);
            String textoCifrado = cifradorCorrecto.cifrar(textoOriginal);

            Cifrador cifradorIncorrecto = new Cifrador(claveMaestraIncorrecta);
            cifradorIncorrecto.descifrar(textoCifrado);
        });
    }
}
