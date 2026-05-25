package d.dina.musiclibrary.mappers;

import d.dina.musiclibrary.dtos.album.AlbumDto;
import d.dina.musiclibrary.entities.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumDto toDto(Album album);

    Album toEntity(AlbumDto albumDto);
}
