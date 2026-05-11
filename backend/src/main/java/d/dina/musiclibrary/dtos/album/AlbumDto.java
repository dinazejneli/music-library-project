package d.dina.musiclibrary.dtos.album;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlbumDto {

    private Integer id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Artist is required")
    private String artist;

    private String coverImage;
}

