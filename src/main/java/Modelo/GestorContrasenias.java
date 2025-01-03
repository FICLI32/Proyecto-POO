package Modelo;

import Data.GestionArchivos;

import java.util.ArrayList;
import java.util.List;

public class GestorContrasenias {
	private int intentosFallidos;
	private Usuario usuarioIniciado;
	private List<Usuario> usuarios;
	private GestionArchivos gestorArchivos;

	public GestorContrasenias(GestionArchivos gestorArchivos) {
		this.usuarios = new ArrayList<>();
		this.intentosFallidos = 0;
		this.gestorArchivos = gestorArchivos;
	}

	public Usuario getUsuarioIniciado() {
		return usuarioIniciado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void agregarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}

	public boolean loguearse(String idUsuario, String contraseniaMaestra) throws Exception {
		Usuario usuario = buscarUsuarioId(idUsuario);
		if (usuario != null && usuario.verificarContraseniaMaestra(contraseniaMaestra)) {
			this.usuarioIniciado = usuario;
			this.intentosFallidos = 0;
			return true;
		} else {
			intentosFallidos++;
			if (intentosFallidos >= 3) {
				System.out.println("Demasiados intentos fallidos. Intente nuevamente más tarde.");
				// sistema de bloqueo temporal
			}
			return false;
		}
	}

	public void desloguearse() {
		this.usuarioIniciado = null;
	}

	public Usuario buscarUsuarioId(String usuarioId) {
		for (Usuario usuario : usuarios) {
			if (usuario.getIdUsuario().equals(usuarioId)) {
				return usuario;
			}
		}
		return null;
	}

	public boolean registrarUsuario(String idUsuario, String contraseniaTexto) {

		if (buscarUsuarioId(idUsuario) != null) {
			System.out.println("El usuario ya existe.");
			return false;
		}

		// Crear nueva instancia de Usuario y agregarla a la lista
		Usuario nuevoUsuario = new Usuario(idUsuario, new Contrasenia(contraseniaTexto));
		usuarios.add(nuevoUsuario);

		// Persistir los datos
		guardarUsuarios();
		System.out.println("Usuario registrado exitosamente.");

		return true;
	}

	public void guardarUsuarios() {
		gestorArchivos.writeData(usuarios);
	}

	public void cargarUsuarios() {
		List<Usuario> usuariosCargados = gestorArchivos.readData();
		if (usuariosCargados != null) {
			this.usuarios = usuariosCargados;
		}
	}
}
