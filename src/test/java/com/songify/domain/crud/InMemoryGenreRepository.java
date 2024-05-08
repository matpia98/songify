package com.songify.domain.crud;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryGenreRepository implements GenreRepository {

    Map<Long, Genre> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(1);

    InMemoryGenreRepository() {
        save(new Genre(1L, "default"));
    }
    @Override
    public Genre save(Genre genre) {
        long index = this.index.getAndIncrement();
        db.put(index, genre);
        genre.setId(index);
        return genre;
    }

    @Override
    public int deleteById(Long id) {
        db.remove(id);
        return 0;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        Genre genre = db.get(id);
        return Optional.ofNullable(genre);
    }
}
