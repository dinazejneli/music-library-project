package d.dina.musiclibrary.controllers.api;

import d.dina.musiclibrary.dtos.album.AlbumDto;
import d.dina.musiclibrary.services.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumRestController {

    private final AlbumService albumService;

    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(albumService.findAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                albumService.findById(id)
        );
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody AlbumDto albumDto) {

        return ResponseEntity.ok(
                albumService.create(albumDto)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @Valid @RequestBody AlbumDto albumDto) {

        return ResponseEntity.ok(
                albumService.update(id, albumDto)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id) {

        albumService.delete(id);

        return ResponseEntity.ok(
                "Album deleted successfully"
        );
    }
}