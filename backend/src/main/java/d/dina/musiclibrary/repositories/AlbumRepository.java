package d.dina.musiclibrary.repositories;

import d.dina.musiclibrary.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByArtistContainingIgnoreCase(String artist);
    List<Album> findAllByOrderByIdAsc();

}
