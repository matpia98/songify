package com.songify.domain.crud;

import org.springframework.stereotype.Service;

@Service
class GenreRetriever {

    private final GenreRepository genreRepository;

    GenreRetriever(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    Genre findGenreById(Long genreId) {
        return genreRepository
                .findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id: " + genreId + " not found"));
    }
}
