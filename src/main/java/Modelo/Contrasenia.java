package Modelo;

import Utils.Cifrador;

import java.text.SimpleDateFormat;
import java.util.*;

public class Contrasenia {
	private String contrasenia;
	private Date fechaCreacion;
	private boolean isEncrypted;

	public Contrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
		this.fechaCreacion = new Date();
		this.isEncrypted = false;
	}

	public String getFechaCreacion() {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatoFecha.format(fechaCreacion);
	}


	public void cifrarContrasenia(Cifrador cifrador) throws Exception {
		if (!isEncrypted) {
			this.contrasenia = cifrador.cifrar(contrasenia);
			isEncrypted = true;
		}
	}

	public String descifrarContrasenia(Cifrador cifrador) throws Exception {
		if (isEncrypted) {
			return cifrador.descifrar(contrasenia);
		}
		return contrasenia;
	}

	public static String generarContraseniaSegura() {
		String letrasMayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letrasMinusculas = "abcdefghijklmnopqrstuvwxyz";
		String numeros = "0123456789";
		String simbolos = "!@#$%^&*()-_=+[]{}|;:,.<>?";
		String todosLosCaracteres = letrasMayusculas + letrasMinusculas + numeros + simbolos;

		StringBuilder contrasenia = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 2; i++) {
			contrasenia.append(simbolos.charAt(random.nextInt(simbolos.length())));
		}
		for (int i = 0; i < 4; i++) {
			contrasenia.append(numeros.charAt(random.nextInt(numeros.length())));
		}
		contrasenia.append(letrasMayusculas.charAt(random.nextInt(letrasMayusculas.length())));

		while (contrasenia.length() < 16) {
			contrasenia.append(todosLosCaracteres.charAt(random.nextInt(todosLosCaracteres.length())));
		}

		List<Character> caracteres = new ArrayList<>();
		for (char c : contrasenia.toString().toCharArray()) {
			caracteres.add(c);
		}
		Collections.shuffle(caracteres);

		StringBuilder contraseniaFinal = new StringBuilder();
		for (char c : caracteres) {
			contraseniaFinal.append(c);
		}

		return contraseniaFinal.toString();
	}
}
