import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class Contrasenia {
	private String contrasenia;
	private Date fechaCreacion;
	private boolean isEncrypted;
	private byte[] salt;
	private byte[] iv;


	public byte[] generarSalt() {
		throw new UnsupportedOperationException();
	}

	public byte[] generarIv() {
		throw new UnsupportedOperationException();
	}

	public void cifrarContrasenia(Object string_contraseniaMaestra) {
		throw new UnsupportedOperationException();
	}

	public String descifrarContrasenia(Object contraseniaMaestra) {
		throw new UnsupportedOperationException();
	}

	public SecretKeySpec derivarContrasenia(Object string_contraseniaMaestra) {
		throw new UnsupportedOperationException();
	}

	public String generarContraseniaSegura() {
		throw new UnsupportedOperationException();
	}
}