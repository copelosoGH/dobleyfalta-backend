package dobleyfalta.noticias_service.repository;

import dobleyfalta.noticias_service.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
}

