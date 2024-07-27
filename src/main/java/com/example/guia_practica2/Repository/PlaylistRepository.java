package com.example.guia_practica2.Repository;

import com.example.guia_practica2.Entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    Playlist findByNombre(String nombre);
}
