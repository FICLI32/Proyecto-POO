import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class GestorContrasenias {

    public static void main(String[] args) throws Exception{
        SecretKey claveCifrado = generarClaveCifrado();
        String contrasenia = "claveultrasecreta123";
        String contraseniaCifrada = cifrarContrasenia(claveCifrado,contrasenia);
        System.out.println(contraseniaCifrada);
        System.out.println(descifrarContrasenia(claveCifrado,contraseniaCifrada));

    }

    public static SecretKey generarClaveCifrado () throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES de 128 bits
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    public static String cifrarContrasenia (SecretKey claveCifrado, String contrasenia) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, claveCifrado);
        byte[] textoCifrado = cipher.doFinal(contrasenia.getBytes());
        String textoCifradoBase64 = Base64.getEncoder().encodeToString(textoCifrado);
        return textoCifradoBase64;
    }

    public static String descifrarContrasenia (SecretKey claveCifrado, String contraseniaCifrada) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, claveCifrado);
        byte[] textoDescifrado = cipher.doFinal(Base64.getDecoder().decode(contraseniaCifrada));
        return new String(textoDescifrado);
    }

}

