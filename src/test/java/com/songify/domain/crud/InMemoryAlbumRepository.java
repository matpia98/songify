package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

class InMemoryAlbumRepository implements AlbumRepository {
    @Override
    public Optional<Album> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public int deleteByIdIn(Collection<Long> ids) {
        return 0;
    }

    @Override
    public Album save(Album album) {
        return null;
    }

    @Override
    public Optional<AlbumInfo> findAlbumByIdWithSongsAndArtists(Long id) {
        return Optional.empty();
    }

    @Override
    public Set<Album> findAllAlbumsByArtistId(Long id) {
        return null;
    }
}
