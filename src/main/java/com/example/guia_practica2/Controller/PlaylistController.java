package com.example.guia_practica2.Controller;

import com.example.guia_practica2.Entity.Playlist;
import com.example.guia_practica2.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @PostMapping("/crear")
    public ResponseEntity<?> createPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getNombre() == null || playlist.getNombre().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre de la lista no es válido");
        }
        Playlist savedPlaylist = playlistRepository.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlaylist);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{listNombre}")
    public ResponseEntity<?> getPlaylist(@PathVariable String listNombre) {
        Playlist playlist = playlistRepository.findByNombre(listNombre);
        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista no encontrada");
        }
        return ResponseEntity.ok(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlistDetails) {
        Playlist playlist = playlistRepository.findById(Math.toIntExact(id)).orElse(null);
        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista no encontrada");
        }
        if (!playlist.getId().equals(playlistDetails.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede modificar el ID de la lista");
        }
        playlist.setDescripcion(playlistDetails.getDescripcion());
        playlist.setSongs(playlistDetails.getSongs());
        playlistRepository.save(playlist);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        Playlist playlist = playlistRepository.findById(Math.toIntExact(id)).orElse(null);
        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista no encontrada");
        }
        playlistRepository.delete(playlist);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
