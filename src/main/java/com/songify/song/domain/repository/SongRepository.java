package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("Shape of You", "Ed Sheeran"),
            2, new Song("Rain on Me", "Lady Gaga, Ariana Grande"),
            3, new Song("Blinding Lights", "The Weeknd"),
            4, new Song("Levitating", "Dua Lipa ft. DaBaby")
    ));

    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    public Map<Integer, Song> findAll() {
        return database;
    }
}
