package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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

    GenreDto findGenreDtoById(Long genreId) {
        Genre genreById = findGenreById(genreId);
        return new GenreDto(genreById.getId(), genreById.getName());
    }

    Set<GenreDto> findAllGenres() {
        Set<Genre> all = genreRepository.findAll();
        return all.stream()
                .map(genre -> GenreDto.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build())
                .collect(Collectors.toSet());
    }
}
