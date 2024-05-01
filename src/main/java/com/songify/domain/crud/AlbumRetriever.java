package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.infrastructure.crud.album.AlbumNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;

    AlbumInfo findAlbumByIdWithArtistsAndSongs(Long id) {
        return albumRepository.findAlbumByIdWithSongsAndArtists(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id " + id + " not found"));
    }

    Set<Album> findAlbumsByArtistId(Long id) {
        return albumRepository.findAllAlbumsByArtistId(id);
    }
}
