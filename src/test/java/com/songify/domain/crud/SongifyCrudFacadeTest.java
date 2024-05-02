package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.Test;

import static com.songify.domain.crud.SongifyCrudFacadeConfiguration.createSongifyCrud;
import static org.junit.jupiter.api.Assertions.*;
class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );



    @Test
    public void first(){
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(shawnMendes);
    }

}