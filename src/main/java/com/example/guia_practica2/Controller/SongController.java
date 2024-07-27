package com.example.guia_practica2.Controller;

import com.example.guia_practica2.Entity.Song;
import com.example.guia_practica2.Repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @PostMapping("/crear")
    public ResponseEntity<?> createSong(@RequestBody Song song) {
        if (song.getTitulo() == null || song.getTitulo().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Título de la canción no es válido");
        }
        Song savedSong = songRepository.save(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSong(@PathVariable Integer id) {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Canción no encontrada");
        }
        return ResponseEntity.ok(song);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Integer id, @RequestBody Song songDetails) {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Canción no encontrada");
        }
        song.setTitulo(songDetails.getTitulo());
        song.setArtista(songDetails.getArtista());
        song.setAlbum(songDetails.getAlbum());
        song.setAños(songDetails.getAños());
        songRepository.save(song);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Integer id) {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Canción no encontrada");
        }
        songRepository.delete(song);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
