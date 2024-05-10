package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );



    @Test
    @DisplayName("Should add artist 'amigo' with id: 0 When amigo was sent")
    public void should_add_artist_amigo_with_id_zero_when_amigo_was_sent(){
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertTrue(allArtists.isEmpty());

        // when
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);

        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("amigo");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Should add artist 'shawn mendes' with id: 0 When shawn mendes was sent")
    public void should_add_artist_shawn_mendes_with_id_zero_when_shawn_mendes_was_sent(){
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);


        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("shawn mendes");
    }

    @Test
    @DisplayName("Should throw exception 'ArtistNotFound' with id: 1")
    public void should_throw_exception_arist_not_found_when_id_was_zero(){
        // given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();

        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));

        // then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("artist with id 0 not found");
    }

    @Test
    @DisplayName("Should delete artist by id when he have no albums")
    public void should_delete_artist_by_id_when_he_have_no_albums(){
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).isEmpty();
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);

        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist with album and songs by id when artist had one album and he was the only artist in album")
    public void should_delete_artist_with_album_and_songs_by_id_when_artist_had_one_album_and_he_was_the_only_artist_in_album(){
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId))
                .title("album title 1")
                .build());
        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);

        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThatThrownBy(() -> songifyCrudFacade.findSongDtoById(songId))
                .isInstanceOf(SongNotFoundException.class)
                .hasMessage("Song with id 0 not found");

        assertThatThrownBy(() -> songifyCrudFacade.findAlbumById(albumId))
                .isInstanceOf(AlbumNotFoundException.class)
                .hasMessage("Album with id: 0 not found");
    }



    @Test
    @DisplayName("Should add album with song")
    public void should_add_album_with_song(){
        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto.id()))
                .title("album title 1")
                .build();
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();
        // when
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        // then
        assertThat(songifyCrudFacade.findAllAlbums()).isNotEmpty();
        AlbumInfo albumWithSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
        Set<AlbumInfo.SongInfo> songs = albumWithSongs.getSongs();
        assertTrue(songs.stream().anyMatch(song -> song.getId().equals(songDto.id())));
    }

    @Test
    @DisplayName("Should add song")
    public void should_add_song(){
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();

        // when
        SongDto songDto = songifyCrudFacade.addSong(song);

        // then
        List<SongDto> allSongs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
        assertThat(songDto.genre().id()).isEqualTo(1L);
        assertThat(allSongs.size()).isEqualTo(1);
        assertThat(allSongs)
                .extracting("id")
                .containsExactly(0L);
    }

    @Test
    @DisplayName("Should retrieve song with genre")
    public void should_retrieve_song_with_genre(){
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);

        // when
        SongDto songDtoById = songifyCrudFacade.findSongDtoById(songDto.id());

        // then
        assertThat(songDtoById.id()).isEqualTo(0L);
        assertThat(songDtoById.name()).isEqualTo("song1");
        assertThat(songDtoById.genre())
                .extracting(GenreDto::id)
                .isEqualTo(1L);
        assertThat(songDtoById.genre())
                .extracting(GenreDto::name)
                .isEqualTo("default");
    }

    @Test
    @DisplayName("Should update genre")
    public void should_update_genre() {
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        assertThat(songDto.genre().id()).isEqualTo(1L);
        assertThat(songDto.genre().name()).isEqualTo("default");

        GenreDto genreDto = songifyCrudFacade.addGenre(new GenreRequestDto("Rock"));

        // when
        songifyCrudFacade.assignGenreToSong(songDto.id(), genreDto.id());

        // then
        SongDto songDtoById = songifyCrudFacade.findSongDtoById(songDto.id());
        assertThat(songDtoById.genre().id()).isEqualTo(2L);
        assertThat(songDtoById.genre().name()).isEqualTo("Rock");

    }

    @Test
    @DisplayName("should add genre")
    public void should_add_genre() {
        // given
        GenreRequestDto genreRequestDto = new GenreRequestDto("Rock");
        assertThat(songifyCrudFacade.findGenreById(1L)).isNotNull();
        assertThatThrownBy(() -> songifyCrudFacade.findGenreById(2L)).isInstanceOf(GenreNotFoundException.class);

        // when
        GenreDto genreDto = songifyCrudFacade.addGenre(genreRequestDto);

        // then
        GenreDto genre = songifyCrudFacade.findGenreById(genreDto.id());
        assertThat(genre)
                .extracting(GenreDto::id)
                .isEqualTo(2L);
        assertThat(genre)
                .extracting(GenreDto::name)
                .isEqualTo("Rock");

    }


    @Test
    @DisplayName("Should add artist to album")
    public void should_add_artist_to_album(){
        //given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder()
                .title("first album")
                .songIds(Set.of(songDto.id()))
                .build();

        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("artist")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(albumRequestDto);
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistDto.id())).isEmpty();

        // when
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());

        // then
        Set<AlbumDto> albumsByArtistId = songifyCrudFacade.findAlbumsByArtistId(artistDto.id());
        assertThat(albumsByArtistId)
                .extracting("id")
                .containsExactly(albumDto.id());
    }

    @Test
    @DisplayName("Should add artist")
    public void should_add_artist(){
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                .name("artist")
                .build();
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();

        // when
        songifyCrudFacade.addArtist(artist);

        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return album by id")
    public void should_return_album_by_id(){
        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder()
                .title("first album")
                .songIds(Set.of(songDto.id()))
                .build();
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();
        AlbumDto addedAlbumDto = songifyCrudFacade.addAlbumWithSong(albumRequestDto);

        // when
        AlbumDto albumById = songifyCrudFacade.findAlbumById(addedAlbumDto.id());

        // then
        assertThat(albumById.id()).isEqualTo(addedAlbumDto.id());
        assertThat(albumById.name()).isEqualTo("first album");
        assertThat(albumById)
                .isEqualTo(new AlbumDto(addedAlbumDto.id(), "first album", Set.of(songDto.id())));
    }

    @Test
    @DisplayName("Should throw exception when album not found by id")
    public void should_throw_exception_when_album_not_found_by_id(){
        // given
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();

        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumById(0L));

        // then
        assertThat(throwable)
                .isInstanceOf(AlbumNotFoundException.class)
                .hasMessage("Album with id: 0 not found");

        assertThatThrownBy(() -> songifyCrudFacade.findAlbumById(1L))
                .isInstanceOf(AlbumNotFoundException.class)
                .hasMessage("Album with id: 1 not found");

        assertThatThrownBy(() -> songifyCrudFacade.findAlbumById(5L))
                .isInstanceOf(AlbumNotFoundException.class)
                .hasMessage("Album with id: 5 not found");

        assertThatThrownBy(() -> songifyCrudFacade.findAlbumById(10L))
                .isInstanceOf(AlbumNotFoundException.class)
                .hasMessage("Album with id: 10 not found");
    }

    @Test
    @DisplayName("Should throw exception when song not found by id")
    public void should_throw_exception_when_song_not_found_by_id(){
        // given
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();

        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(0L));

        // then
        assertThat(throwable)
                .isInstanceOf(SongNotFoundException.class)
                .hasMessage("Song with id 0 not found");

        assertThatThrownBy(() -> songifyCrudFacade.findSongDtoById(1L))
                .isInstanceOf(SongNotFoundException.class)
                .hasMessage("Song with id 1 not found");

        assertThatThrownBy(() -> songifyCrudFacade.findSongDtoById(5L))
                .isInstanceOf(SongNotFoundException.class)
                .hasMessage("Song with id 5 not found");

        assertThatThrownBy(() -> songifyCrudFacade.findSongDtoById(10L))
                .isInstanceOf(SongNotFoundException.class)
                .hasMessage("Song with id 10 not found");
    }

    @Test
    @DisplayName("Should delete only artist from album by id When there were more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_id_when_there_were_more_than_one_artist_in_album() {
        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto.id()))
                .title("album title 1")
                .build();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);

        ArtistRequestDto artistRequestDto1 = ArtistRequestDto.builder()
                .name("artist1")
                .build();
        ArtistDto artistDto1 = songifyCrudFacade.addArtist(artistRequestDto1);
        ArtistRequestDto artistRequestDto2 = ArtistRequestDto.builder()
                .name("artist2")
                .build();
        ArtistDto artistDto2 = songifyCrudFacade.addArtist(artistRequestDto2);

        songifyCrudFacade.addArtistToAlbum(artistDto1.id(), albumDto.id());
        songifyCrudFacade.addArtistToAlbum(artistDto2.id(), albumDto.id());

        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
                .getArtists()
                .size()).isGreaterThanOrEqualTo(2);

        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistDto1.id());

        // then
//        assertThat(songifyCrudFacade.findAllAlbums()).isNotEmpty();
        AlbumInfo albumInfo = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
        Set<AlbumInfo.ArtistInfo> artists = albumInfo.getArtists();
        assertThat(artists.size()).isEqualTo(1);
        assertThat(artists)
                .extracting("id")
                .containsOnly(artistDto2.id());
    }

    @Test
    @DisplayName("Should delete artist with albums and songs by id when artist was the only artist in albums")
    public void should_delete_artist_with_albums_and_songs_by_id_when_artist_was_the_only_artist_in_albums() {
        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("seniorita")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto songRequestDto2 = SongRequestDto.builder()
                .name("seniorita2")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto songRequestDto3 = SongRequestDto.builder()
                .name("seniorita3")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto songRequestDto4 = SongRequestDto.builder()
                .name("seniorita4")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        SongDto songDto2 = songifyCrudFacade.addSong(songRequestDto2);
        SongDto songDto3 = songifyCrudFacade.addSong(songRequestDto3);
        SongDto songDto4 = songifyCrudFacade.addSong(songRequestDto4);

        AlbumRequestDto album1 = AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto.id(), songDto2.id()))
                .title("album1")
                .build();
        AlbumRequestDto album2 = AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto3.id(), songDto4.id()))
                .title("album2")
                .build();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album1);
        AlbumDto albumDto2 = songifyCrudFacade.addAlbumWithSong(album2);

        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);

        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto2.id());

//        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
//                .getArtists()
//                .size()).isEqualTo(1);
//        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto2.id())
//                .getArtists()
//                .size()).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumDto.id())).isEqualTo(1L);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumDto2.id())).isEqualTo(1L);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllAlbums().size()).isEqualTo(2);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(4);

        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistDto.id());

        // then
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();

    }



}