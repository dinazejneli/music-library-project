package d.dina.musiclibrary.services;

import d.dina.musiclibrary.dtos.album.AlbumDto;

import java.util.List;

public interface AlbumService {

    List<AlbumDto> findAll();

    AlbumDto findById(Integer id);

    AlbumDto create(AlbumDto albumDto);

    AlbumDto update(Integer id, AlbumDto albumDto);

    void delete(Integer id);
}
