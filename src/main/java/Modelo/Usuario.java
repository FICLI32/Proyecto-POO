package Modelo;

import Utils.Cifrador;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Usuario {
	private String idUsuario;
	private String nombre;
	private String contraseniaMaestraHash;
	private byte[] salt;
	private Map<String, Contrasenia> contrasenias;

	public Usuario(String idUsuario, String nombre, String contraseniaMaestra, Cifrador cifrador) throws Exception {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.salt = generarSalt();
		this.contraseniaMaestraHash = cifrador.cifrar(contraseniaMaestra);
		this.contrasenias = new HashMap<>();
	}

	public Map<String, Contrasenia> getContrasenias() {
		return contrasenias;
	}

	public String getNombre() {
		return nombre;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	private byte[] generarSalt() {
		byte[] salt = new byte[16];
		new java.security.SecureRandom().nextBytes(salt);
		return salt;
	}

	public boolean verificarContraseniaMaestra(String contraseniaMaestra, Cifrador cifrador) throws Exception {
		String intentoHash = cifrador.cifrar(contraseniaMaestra);
		return intentoHash.equals(contraseniaMaestraHash);
	}

	public void agregarContrasenia(String plataforma, Contrasenia contrasenia, Cifrador cifrador) throws Exception {
		contrasenia.cifrarContrasenia(cifrador);
		contrasenias.put(plataforma, contrasenia);
	}

	public void eliminarContrasenia(String plataforma) {
		contrasenias.remove(plataforma);
	}

	public String obtenerContrasenia(String plataforma, Cifrador cifrador) throws Exception {
		Contrasenia contrasenia = contrasenias.get(plataforma);
		return (contrasenia != null) ? contrasenia.descifrarContrasenia(cifrador) : null;
	}
}
