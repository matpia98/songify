package com.songify.domain.crud.dto;

import java.util.Set;

public record AlbumDtoWithArtistsAndSongs(
        AlbumDto albumDto,
        Set<ArtistDto> artists,
        Set<SongDto> songs
) {
}
