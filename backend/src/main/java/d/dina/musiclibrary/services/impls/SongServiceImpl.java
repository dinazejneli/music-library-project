package d.dina.musiclibrary.services.impls;

import d.dina.musiclibrary.dtos.song.SongDto;
import d.dina.musiclibrary.entities.Album;
import d.dina.musiclibrary.entities.Song;
import d.dina.musiclibrary.mappers.SongMapper;
import d.dina.musiclibrary.repositories.AlbumRepository;
import d.dina.musiclibrary.repositories.SongRepository;
import d.dina.musiclibrary.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final SongMapper songMapper;

    @Override
    public List<SongDto> findAll() {
        return songRepository.findAllByOrderByIdAsc()
                .stream()
                .map(songMapper::toDto)
                .toList();
    }


    @Override
    public SongDto findById(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        return songMapper.toDto(song);
    }

    @Override
    public List<SongDto> findByAlbum(Integer albumId) {
        return songRepository.findAllByAlbum_Id(albumId)
                .stream()
                .map(songMapper::toDto)
                .toList();
    }

    @Override
    public SongDto create(SongDto songDto) {

        if (
                songRepository
                        .existsByTitleIgnoreCase(
                                songDto.getTitle()
                        )
        ) {

            throw new RuntimeException(
                    "Song already exists"
            );
        }

        Album album = albumRepository.findById(
                songDto.getAlbumId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Album not found"
                )
        );

        Song song = songMapper.toEntity(songDto);
        song.setAlbum(album);

        Song saved = songRepository.save(song);

        return songMapper.toDto(saved);
    }

    @Override
    public SongDto update(Integer id, SongDto songDto) {

        Song existing = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        existing.setTitle(songDto.getTitle());
        existing.setArtist(songDto.getArtist());
        existing.setDuration(songDto.getDuration());


        if (songDto.getAlbumId() != null) {
            Album album = albumRepository.findById(songDto.getAlbumId())
                    .orElseThrow(() -> new RuntimeException("Album not found"));
            existing.setAlbum(album);
        }

        Song updated = songRepository.save(existing);
        return songMapper.toDto(updated);
    }


    @Override
    public void delete(Integer id) {

        System.out.println("DELETE METHOD CALLED");

        songRepository.deleteById(id);

        System.out.println("DELETE FINISHED");
    }

    public boolean existsByTitle(String title) {
        return songRepository.existsByTitleIgnoreCase(title);
    }

}
