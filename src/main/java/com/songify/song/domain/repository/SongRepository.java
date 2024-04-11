package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmendes song", "Shawn Mendes"),
            2, new Song("ariana grande song2", "Ariana Grande"),
            3, new Song("ariana grande song22121", "Ariana Grande"),
            4, new Song("ariana grande song2another", "Ariana Grande")
    ));

    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    public Map<Integer, Song> findAll() {
        return database;
    }
}
