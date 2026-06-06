package d.dina.musiclibrary.dtos.song;

import d.dina.musiclibrary.validation.ValidDuration;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class SongDto {

    private Integer id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Artist is required")
    private String artist;

    @ValidDuration
    private String durationFormatted;



    private Integer duration;

    @NotNull(message = "Album is required")
    private Integer albumId;

    private String albumTitle;

    private String albumCover;

}
