import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
	private String idUsuario;
	private String nombre;
	private String contraseniaMaestraHash;
	private byte[] salt;
	private Map<String, Contrasenia> contrasenias;

	public Usuario(String userId, String nombre, String contraseniaMaestra) throws Exception {
		this.idUsuario = userId;
		this.nombre = nombre;
		this.salt = generarSalt();
		this.contraseniaMaestraHash = hashContraseniaMaestra(contraseniaMaestra);
		this.contrasenias = new HashMap<>();
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	private byte[] generarSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	private String hashContraseniaMaestra(String contraseniaMaestra) throws Exception {
		PBEKeySpec spec = new PBEKeySpec(contraseniaMaestra.toCharArray(), salt,65536,128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte [] hash = factory.generateSecret(spec).getEncoded();
		return Base64.getEncoder().encodeToString(hash);
	}

	public boolean verificarContraseniaMaestra(String contraseniaMaestra) throws Exception{
		String intento = hashContraseniaMaestra(contraseniaMaestra);
		return intento.equals(contraseniaMaestraHash);
	}

	public void agregarContrasenia(String plataforma, Contrasenia contrasenia, String contraseniaMaestra) throws Exception{
		contrasenia.cifrarContrasenia(contraseniaMaestra);
		contrasenias.put(plataforma,contrasenia);
	}

	public void eliminarContrasenia(String plataforma){
		contrasenias.remove(plataforma);
	}

	public String obtenerContrasenia(String plataforma, String contraseniaMaestra) throws Exception {
		Contrasenia contrasenia = contrasenias.get(plataforma);
		return (contrasenia != null) ? contrasenia.descifrarContrasenia(contraseniaMaestra) : null;
	}

	public Map<String, Contrasenia> contraseniaMap() {
		return contrasenias;
	}
}