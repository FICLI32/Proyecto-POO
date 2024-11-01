import java.util.List;
import java.util.Map;

public class Usuario {
	private String idUsuario;
	private String nombre;
	private String contraseniaMaestra;
	private byte[] salt;
	private List<Contrasenia> contrasenias;

	private byte[] generarSalt() {
		throw new UnsupportedOperationException();
	}

	private String hashContraseniaMaestra(String contraseniaMaestra) {
		throw new UnsupportedOperationException();
	}

	public boolean verificarContraseniaMaestra(Object string_contraseniaMaestra) {
		throw new UnsupportedOperationException();
	}

	public void agregarContrasenia(Contrasenia contrasenia, Object string_contraseniaMaestra) {
		throw new UnsupportedOperationException();
	}

	public void eliminarContrasenia(Object int_indice) {
		throw new UnsupportedOperationException();
	}

	public String obtenerContrasenia(String plataforma, String contraseniaMaestra) {
		throw new UnsupportedOperationException();
	}

	public Map<String, Contrasenia> contraseniaMap() {
		throw new UnsupportedOperationException();
	}
}