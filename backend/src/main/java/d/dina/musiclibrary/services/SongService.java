package d.dina.musiclibrary.services;

import d.dina.musiclibrary.dtos.song.SongDto;

import java.util.List;

public interface SongService {

    List<SongDto> findAll();

    SongDto findById(Integer id);

    List<SongDto> findByAlbum(Integer albumId);

    SongDto create(SongDto songDto);

    SongDto update(Integer id, SongDto songDto);

    void delete(Integer id);

    boolean existsByTitle(String title);
}
