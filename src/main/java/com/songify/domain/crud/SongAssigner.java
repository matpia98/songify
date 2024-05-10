package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import org.springframework.stereotype.Service;

@Service
class SongAssigner {

    private final AlbumRetriever albumRetriever;
    private final SongRetriever songRetriever;
    SongAssigner(AlbumRetriever albumRetriever, SongRetriever songRetriever) {
        this.albumRetriever = albumRetriever;
        this.songRetriever = songRetriever;
    }

    AlbumDto assignSongToAlbum(Long albumId, Long songId) {
        Album album = albumRetriever.findById(albumId);
        Song song = songRetriever.findSongById(songId);
        album.addSongToAlbum(song);
        return new AlbumDto(album.getId(), album.getTitle(), album.getSongsIds());
    }
}
