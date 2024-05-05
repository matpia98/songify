package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class SongifyCrudFacade {

    private final SongRetriever songRetriever;
    private final SongAdder songAdder;
    private final SongUpdater songUpdater;
    private final SongDeleter songDeleter;
    private final ArtistRetriever artistRetriever;
    private final ArtistAdder artistAdder;
    private final ArtistUpdater artistUpdater;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final GenreAdder genreAdder;
    private final AlbumRetriever albumRetriever;
    private final AlbumAdder albumAdder;


    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }

    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto dto) {
        return albumAdder.addAlbum(dto.songId(), dto.title(), dto.releaseDate());
    }

    public void addArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }

    public ArtistDto updateArtistNameById(Long artistId, String name) {
        return artistUpdater.updateArtistNameById(artistId, name);
    }

    public ArtistDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto) {
        return artistAdder.addArtistWithDefaultAlbumAndSong(dto);
    }

    public SongDto addSong(final SongRequestDto dto) {
        return songAdder.addSong(dto);
    }

    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistRetriever.findAllArtists(pageable);
    }

    public AlbumInfo findAlbumByIdWithArtistsAndSongs(Long id) {
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(id);
    }

    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
        artistDeleter.deleteArtistByIdWithAlbumsAndSongs(artistId);
    }

    public List<SongDto> findAllSongs(Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public SongDto findSongDtoById(Long id) {
        return songRetriever.findSongDtoById(id);
    }


    public void updateSongById(Long id, SongDto newSongDto) {
        songRetriever.existsById(id);
        // some domain validator
        Song songValidatedAndReadyToUpdate = new Song(newSongDto.name());
        // some domain validator ended checking
        songUpdater.updateById(id, songValidatedAndReadyToUpdate);
    }

    public SongDto updatePartiallySongById(Long id, SongDto songFromRequest) {
        songRetriever.existsById(id);
        Song songFromDatabase = songRetriever.findSongById(id);
        Song toSave = new Song();
        if (songFromRequest.name() != null) {
            toSave.setName(songFromRequest.name());
        } else {
            toSave.setName(songFromDatabase.getName());
        }
//        todo
//        if (songFromRequest.getArtist() != null) {
//            builder.artist(songFromRequest.getArtist());
//        } else {
//            builder.artist(songFromDatabase.getArtist());
//        }
        songUpdater.updateById(id, toSave);
        return SongDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .build();

    }

    public void deleteSongById(Long id) {
        songRetriever.existsById(id);
        songDeleter.deleteById(id);
    }

    Set<AlbumDto> findAlbumsByArtistId(Long artistId) {
        return albumRetriever.findAlbumsDtoByArtistId(artistId);
    }

    int countArtistsByAlbumId(Long albumId) {
        return albumRetriever.countArtistsByAlbumId(albumId);
    }

    AlbumDto findAlbumById(Long albumId) {
        return albumRetriever.findDtoById(albumId);
    }

    Set<AlbumDto> findAllAlbums() {
        return albumRetriever.findAll();
    }

    ArtistDto findArtistById(Long artistId) {
        return artistRetriever.findDtoById(artistId);
    }

//    public void deleteSongAndGenreById(Long songId) {
//        songDeleter.deleteSongAndGenreById(songId);
//    }
}
