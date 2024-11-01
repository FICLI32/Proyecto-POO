import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Base64;
import java.util.List;

public class GestionArchivos {
	private String rutaArchivo;
	private SecretKeySpec claveCifrado;

	public void writeData(List<Usuario> data) {
		try (FileWriter writer = new FileWriter(rutaArchivo)) {
			Gson gson = new Gson();
			String jsonData = gson.toJson(data);
			//cifrado
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, claveCifrado);
			byte[] encyptedData = cipher.doFinal(jsonData.getBytes());
			writer.write(Base64.getEncoder().encodeToString(encyptedData));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Usuario> readData() {
		try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
			String datosCodificados = reader.readLine();
			byte[] datosDecodificados = Base64.getDecoder().decode(datosCodificados);
			//Descifrado
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, claveCifrado);
			byte[] datosDescifrados = cipher.doFinal(datosDecodificados);
			Gson gson = new Gson();
			return gson.fromJson(new String(datosDescifrados), new TypeToken<List<Usuario>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}