package Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Cifrador {
    private final String claveMaestra;

    public Cifrador(String claveMaestra) {
        this.claveMaestra = claveMaestra;
    }

    public String getClaveMaestra() {
        return claveMaestra;
    }

    public String cifrar(String datos) throws Exception {
        SecretKeySpec keySpec = derivarClave(claveMaestra);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = generarIv();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cifrado = cipher.doFinal(datos.getBytes());
        return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(cifrado);
    }

    public String descifrar(String datosCifrados) throws Exception {
        String[] partes = datosCifrados.split(":");
        byte[] iv = Base64.getDecoder().decode(partes[0]);
        byte[] datos = Base64.getDecoder().decode(partes[1]);

        SecretKeySpec keySpec = derivarClave(claveMaestra);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        return new String(cipher.doFinal(datos));
    }

    private SecretKeySpec derivarClave(String clave) throws Exception {
        byte[] salt = "138".getBytes();
        PBEKeySpec spec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }

    private byte[] generarIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}

