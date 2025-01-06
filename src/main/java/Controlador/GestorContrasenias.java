package Controlador;

import Data.GestionArchivos;
import Modelo.Usuario;
import Utils.Cifrador;

import java.util.ArrayList;
import java.util.List;

public class GestorContrasenias {
	private int intentosFallidos;
	private Usuario usuarioIniciado;
	private List<Usuario> usuarios;
	private GestionArchivos gestorArchivos;
	private Cifrador cifrador;

	public GestorContrasenias(GestionArchivos gestorArchivos, Cifrador cifrador) {
		this.usuarios = new ArrayList<>();
		this.intentosFallidos = 0;
		this.gestorArchivos = gestorArchivos;
		this.cifrador = cifrador;
		cargarUsuarios();
	}

	public Usuario getUsuarioIniciado() {
		return usuarioIniciado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarioIniciado(String idUsuario) {
		this.usuarioIniciado = buscarUsuarioId(idUsuario);
	}

	public void agregarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}

	public boolean loguearse(String idUsuario, String contraseniaMaestra) {
		Usuario usuario = buscarUsuarioId(idUsuario);
		try {
			if (usuario != null && usuario.verificarContraseniaMaestra(contraseniaMaestra, cifrador)) {
				this.usuarioIniciado = usuario;
				this.intentosFallidos = 0;
				return true;
			} else {
				intentosFallidos++;
				if (intentosFallidos >= 3) {
					System.out.println("Demasiados intentos fallidos. Intente nuevamente más tarde.");
				}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al verificar las credenciales.");
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
		try {
			Usuario nuevoUsuario = new Usuario(idUsuario, "Usuario", contraseniaTexto, cifrador);
			usuarios.add(nuevoUsuario);
			guardarUsuarios();
			System.out.println("Usuario registrado exitosamente.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al registrar nuevo usuario.");
			return false;
		}
	}

	public void guardarUsuarios() {
		try {
			gestorArchivos.writeData(usuarios);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al guardar los usuarios en el archivo.");
		}
	}

	public void cargarUsuarios() {
		try {
			List<Usuario> usuariosCargados = gestorArchivos.readData();
			if (usuariosCargados != null) {
				this.usuarios = usuariosCargados;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al cargar los usuarios desde el archivo.");
		}
	}
}

