package Modelo;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class Contrasenia {
	private String contrasenia;
	private Date fechaCreacion;
	private boolean isEncrypted;
	private byte[] salt;
	private byte[] iv;

	public Contrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
		this.fechaCreacion = new Date();
		this.isEncrypted = false;
		this.salt = generarSalt();
		this.iv = generarIv();
	}

	public byte[] generarSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	public byte[] generarIv() {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		return iv;
	}

	public void cifrarContrasenia(String contraseniaMaestra) throws Exception {
		if (!isEncrypted){
			SecretKeySpec keySpec = derivarContrasenia(contraseniaMaestra);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] contraseniaCifrada = cipher.doFinal(contrasenia.getBytes());
			this.contrasenia = Base64.getEncoder().encodeToString(contraseniaCifrada);
			isEncrypted = true;
		}
	}

	public String descifrarContrasenia (String contraseniaMaestra) throws Exception {
		if (isEncrypted){
			SecretKeySpec keySpec = derivarContrasenia(contraseniaMaestra);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE,keySpec, ivSpec);
			byte[] contraseniaDescifrada = cipher.doFinal(Base64.getDecoder().decode(contrasenia));
			return new String(contraseniaDescifrada);
		}
		return contrasenia;
	}

	private SecretKeySpec derivarContrasenia(String contraseniaMaestra) throws Exception {
		PBEKeySpec spec = new PBEKeySpec(contraseniaMaestra.toCharArray(), salt, 65536,128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] key = factory.generateSecret(spec).getEncoded();
		return new SecretKeySpec(key,"AES");
	}

	public static String generarContraseniaSegura(){
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
		StringBuilder contrasenia = new StringBuilder();
		Random random = new Random();
		while (contrasenia.length() < 16){
			int indice = (int) (random.nextFloat() * chars.length());
			contrasenia.append(chars.charAt(indice));
		}
		return contrasenia.toString();
	}
}