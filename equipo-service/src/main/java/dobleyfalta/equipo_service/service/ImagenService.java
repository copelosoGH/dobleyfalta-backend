package dobleyfalta.equipo_service.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class ImagenService {

    private static final String UPLOAD_DIR = "uploads/equipos/";

    public String guardarImagenBase64(String base64, String nombreArchivo) throws IOException {
        File directorio = new File(UPLOAD_DIR);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        if (base64.contains(",")) {
            base64 = base64.split(",")[1];
        }

        base64 = base64.replaceAll("\\s+", "");

        byte[] imagenBytes = Base64.getDecoder().decode(base64);

        File archivo = new File(UPLOAD_DIR + nombreArchivo);
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(imagenBytes);
        }

        return archivo.getPath();
    }
}
