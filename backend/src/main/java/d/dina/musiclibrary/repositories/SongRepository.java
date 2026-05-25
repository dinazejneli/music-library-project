package d.dina.musiclibrary.repositories;

import d.dina.musiclibrary.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    List<Song> findAllByTitleContainingIgnoreCase(String title);
    List<Song> findByArtistContainingIgnoreCase(String artist);
    List<Song> findAllByOrderByIdAsc();

    int countByAlbum_Id(Integer albumId);

    // useful for "album details page" later
    List<Song> findAllByAlbum_Id(Integer albumId);

    boolean existsByTitleIgnoreCase(String title);
}
