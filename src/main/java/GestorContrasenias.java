import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class GestorContrasenias {
	private int intentosFallidos;
	private Usuario usuarioIniciado;
	private List<Usuario> usuarios ;
	private GestionArchivos gestorArchivos;

	public GestorContrasenias() {
		this.usuarios = new ArrayList<>();
		this.intentosFallidos = 0;
	}

	public void agregarUsuario(Usuario usuario){
		usuarios.add(usuario);
	}

	public boolean loguearse(String idUsuario, String contraseniaMaestra) throws Exception{
		Usuario usuario = buscarUsuarioId(idUsuario);
		if (usuario != null && usuario.verificarContraseniaMaestra(contraseniaMaestra)){
			this.usuarioIniciado = usuario;
			this.intentosFallidos = 0;
			return true;
		}else{
			intentosFallidos++;
			if (intentosFallidos >=3 ){
				System.out.println("Demasiados intentos fallidos. Intente nuvamanete mas tarde.");
				//sistema de bloqueo temporal
			}
			return false;
		}
	}

	public void desloguearse(){
		this.usuarioIniciado = null;
	}

	private Usuario buscarUsuarioId(String usuarioId){
		for (Usuario usuario: usuarios){
			if (usuario.getIdUsuario().equals(usuarioId)){
				return usuario;
			}
		}
		return null;
	}

	public Usuario obtenerUsuarioIniciado() {
		return usuarioIniciado;
	}

	public void guardarUsuarios(){
		gestorArchivos.writeData(usuarios);
	}

	public void cargarUsuarios(){
		List<Usuario> usuariosCargados = gestorArchivos.readData();
		if (usuariosCargados != null) {
			this.usuarios = usuariosCargados;
		}
	}
}