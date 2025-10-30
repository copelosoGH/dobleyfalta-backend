package dobleyfalta.noticias_service.service;

import dobleyfalta.noticias_service.model.Noticia;
import dobleyfalta.noticias_service.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private ImagenService imagenService;

    public List<Noticia> obtenerTodas() {
        return noticiaRepository.findAll();
    }

    public Optional<Noticia> obtenerPorId(Integer id) {
        return noticiaRepository.findById(id);
    }

    public Noticia guardar(Noticia noticia) {
        try {
            if (noticia.getImagen() != null && !noticia.getImagen().isEmpty()) {
                String nombreArchivo = "noticia_" + System.currentTimeMillis() + ".jpg";
                String rutaGuardada = imagenService.guardarImagenBase64(noticia.getImagen(), nombreArchivo);
                noticia.setImagen(rutaGuardada);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
        }

        return noticiaRepository.save(noticia);
    }

    // El map solo se ejecuta si encuentra la noticia por id, hago esta logica para que podamos modificar 
    // solo un atributo si queremos y que no de error.
    // si no encuentra noticia ni se ejecuta el map y se va al .orElse y devuelve null
    public Noticia actualizar(Integer id, Noticia noticiaActualizada) {
        return noticiaRepository.findById(id).map(noticia -> {
            if (noticiaActualizada.getTitulo() != null)
                noticia.setTitulo(noticiaActualizada.getTitulo());
            if (noticiaActualizada.getContenido() != null)
                noticia.setContenido(noticiaActualizada.getContenido());
            if (noticiaActualizada.getImagen() != null)
                noticia.setImagen(noticiaActualizada.getImagen());
            if (noticiaActualizada.getFechaPublicacion() != null)
                noticia.setFechaPublicacion(noticiaActualizada.getFechaPublicacion());

            return noticiaRepository.save(noticia);
        }).orElse(null);
    }

    public void eliminar(Integer id) {
        noticiaRepository.deleteById(id);
    }
}

