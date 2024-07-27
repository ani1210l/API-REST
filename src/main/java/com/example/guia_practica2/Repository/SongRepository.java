package com.example.guia_practica2.Repository;

import com.example.guia_practica2.Entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository  extends JpaRepository<Song, Integer> {
}
