package d.dina.musiclibrary.controllers.api;

import d.dina.musiclibrary.dtos.song.SongDto;
import d.dina.musiclibrary.services.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    // GET ALL SONGS
    @GetMapping
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(
                songService.findAll()
        );
    }

    // GET SONG BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                songService.findById(id)
        );
    }

    // CHECK IF TITLE EXISTS
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByTitle(
            @RequestParam String title
    ) {

        return ResponseEntity.ok(
                songService.existsByTitle(title)
        );
    }

    // CREATE SONG
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody SongDto songDto
    ) {

        songDto.setDuration(
                parseDuration(
                        songDto.getDurationFormatted()
                )
        );

        return ResponseEntity.ok(
                songService.create(songDto)
        );
    }

    // UPDATE SONG
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @Valid @RequestBody SongDto songDto
    ) {

        songDto.setDuration(
                parseDuration(
                        songDto.getDurationFormatted()
                )
        );

        return ResponseEntity.ok(
                songService.update(id, songDto)
        );
    }

    // DELETE SONG
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id
    ) {

        songService.delete(id);

        return ResponseEntity.ok(
                "Song deleted successfully"
        );
    }

    // DURATION PARSER
    private int parseDuration(String duration) {

        String[] parts = duration.split(":");

        int minutes = Integer.parseInt(parts[0]);

        int seconds = Integer.parseInt(parts[1]);

        return minutes * 60 + seconds;
    }
}