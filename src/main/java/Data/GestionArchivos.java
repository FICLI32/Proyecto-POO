package Data;

import Modelo.Usuario;
import Utils.Cifrador;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestionArchivos {
	private final String rutaArchivo = "usuarios.json";
	private final Cifrador cifrador;

	public GestionArchivos(Cifrador cifrador) {
		this.cifrador = cifrador;
		crearArchivoSiNoExiste(); // Crear archivo si no existe
	}

	private void crearArchivoSiNoExiste() {
		try {
			File archivo = new File(rutaArchivo);
			if (!archivo.exists()) {
				archivo.createNewFile();
				writeData(new ArrayList<>()); // Escribir una lista vacía
				System.out.println("Archivo JSON creado: " + rutaArchivo);
			}
		} catch (IOException e) {
			System.err.println("Error al crear el archivo JSON: " + e.getMessage());
		}
	}

	// Método para leer datos
	public List<Usuario> readData() {
		try {
			FileReader reader = new FileReader(rutaArchivo);
			char[] buffer = new char[4096];
			int read = reader.read(buffer);
			String cifrado = new String(buffer, 0, read);
			String descifrado = cifrador.descifrar(cifrado);

			Gson gson = new Gson();
			Type tipoLista = new TypeToken<List<Usuario>>() {
			}.getType();
			return gson.fromJson(descifrado, tipoLista);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	// Método para escribir datos
	public void writeData(List<Usuario> usuarios) {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(usuarios);

			String cifrado = cifrador.cifrar(json);

			FileWriter writer = new FileWriter(rutaArchivo);
			writer.write(cifrado);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validarClave(Cifrador cifrador) {
		try {
			// Leer datos del archivo (si existe algo cifrado, intenta descifrarlo).
			File archivo = new File(rutaArchivo);
			if (!archivo.exists() || archivo.length() == 0) {
				return true; // Si no hay archivo o está vacío, la clave no es relevante aún.
			}

			FileReader reader = new FileReader(rutaArchivo);
			char[] buffer = new char[4096];
			int read = reader.read(buffer);
			String cifrado = new String(buffer, 0, read);
			cifrador.descifrar(cifrado); // Intentar descifrar
			return true;
		} catch (Exception e) {
			return false; // La clave no puede descifrar los datos
		}
	}

}




