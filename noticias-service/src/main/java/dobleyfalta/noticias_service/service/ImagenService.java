package dobleyfalta.noticias_service.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class ImagenService {

    private static final String UPLOAD_DIR = "uploads/";

    public String guardarImagenBase64(String base64, String nombreArchivo) throws IOException {
        // Crear carpeta si no existe
        File directorio = new File(UPLOAD_DIR);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Esto es para sacar la parte que no nos importa del base64
        if (base64.contains(",")) {
            base64 = base64.split(",")[1];
        }

        // Eliminar espacios o saltos de línea
        base64 = base64.replaceAll("\\s+", "");

        // Decodificar Base64
        byte[] imagenBytes = Base64.getDecoder().decode(base64);

        // Guardar archivo
        File archivo = new File(UPLOAD_DIR + nombreArchivo);
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(imagenBytes);
        }

        // Devolver ruta relativa
        return archivo.getPath();
    }
}