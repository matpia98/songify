package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepositoryInMemory implements SongRepository {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("Shape of You", "Ed Sheeran"),
            2, new Song("Rain on Me", "Lady Gaga, Ariana Grande"),
            3, new Song("Blinding Lights", "The Weeknd"),
            4, new Song("Levitating", "Dua Lipa ft. DaBaby")
    ));

    @Override
    public Song save(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    @Override
    public List<Song> findAll() {
        return database.values()
                .stream()
                .toList();
    }
}
