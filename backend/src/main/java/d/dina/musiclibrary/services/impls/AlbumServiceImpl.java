package d.dina.musiclibrary.services.impls;

import d.dina.musiclibrary.dtos.album.AlbumDto;
import d.dina.musiclibrary.entities.Album;
import d.dina.musiclibrary.mappers.AlbumMapper;
import d.dina.musiclibrary.repositories.AlbumRepository;
import d.dina.musiclibrary.repositories.SongRepository;
import d.dina.musiclibrary.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import d.dina.musiclibrary.exceptions.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final SongRepository songRepository;


    @Override
    public List<AlbumDto> findAll() {
        return albumRepository.findAllByOrderByIdAsc()
                .stream()
                .map(albumMapper::toDto)
                .toList();

    }

    @Override
    public AlbumDto findById(Integer id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        return albumMapper.toDto(album);
    }

    @Override
    public AlbumDto create(AlbumDto albumDto) {

        if (
                albumRepository
                        .existsByTitleIgnoreCase(
                                albumDto.getTitle()
                        )
        ) {

            throw new RuntimeException(
                    "Album already exists"
            );
        }

        Album album =
                albumMapper.toEntity(albumDto);

        Album saved =
                albumRepository.save(album);

        return albumMapper.toDto(saved);
    }

    @Override
    public AlbumDto update(
            Integer id,
            AlbumDto albumDto
    ) {

        Album existing =
                albumRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Album not found"
                                )
                        );

        if (
                !existing.getTitle()
                        .equalsIgnoreCase(
                                albumDto.getTitle()
                        )
                        &&
                        albumRepository
                                .existsByTitleIgnoreCase(
                                        albumDto.getTitle()
                                )
        ) {

            throw new RuntimeException(
                    "Album already exists"
            );
        }

        existing.setTitle(
                albumDto.getTitle()
        );

        existing.setArtist(
                albumDto.getArtist()
        );

        existing.setCoverImage(
                albumDto.getCoverImage()
        );

        Album updated =
                albumRepository.save(existing);

        return albumMapper.toDto(updated);
    }

    @Override
    public void delete(Integer id) {

        Album album = albumRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Album not found"
                        )
                );

        if (
                songRepository.countByAlbum_Id(id)
                        > 0
        ) {

            throw new BusinessException(
                    "Cannot delete album because it contains songs"
            );
        }

        albumRepository.delete(album);
    }
}
