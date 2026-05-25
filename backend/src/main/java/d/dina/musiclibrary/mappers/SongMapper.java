package d.dina.musiclibrary.mappers;

import d.dina.musiclibrary.dtos.song.SongDto;
import d.dina.musiclibrary.entities.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "album.title", target = "albumTitle")
    @Mapping(source = "album.coverImage", target = "albumCover")
    SongDto toDto(Song song);

    @Mapping(source = "albumId", target = "album.id")
    Song toEntity(SongDto songDto);
}
