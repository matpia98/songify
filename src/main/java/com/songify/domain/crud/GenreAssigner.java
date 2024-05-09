package com.songify.domain.crud;

import org.springframework.stereotype.Service;

@Service
class GenreAssigner {

    private final SongRetriever songRetriever;
    private final GenreRetriever genreRetriever;

    GenreAssigner(SongRetriever songRetriever, GenreRetriever genreRetriever) {
        this.songRetriever = songRetriever;
        this.genreRetriever = genreRetriever;
    }

    void assignDefaultGenreToSong(Long songId) {
        assignGenreToSong(songId, 1L);
    }

    void assignGenreToSong(Long songId, Long genreId) {
        Song song = songRetriever.findSongById(songId);
        Genre genre = genreRetriever.findGenreById(genreId);
        song.setGenre(genre);
    }
}
