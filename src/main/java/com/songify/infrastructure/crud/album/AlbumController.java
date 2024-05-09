package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/albums")
class AlbumController {

    private final SongifyCrudFacade songifyCrudFacade;

    @PostMapping()
    ResponseEntity<AlbumDto> postArtist(@RequestBody AlbumRequestDto albumRequestDto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(albumRequestDto);
        return ResponseEntity.ok(albumDto);
    }

    @GetMapping("/{albumId}")
    ResponseEntity<AlbumInfo> getAlbumWithArtistsAndSongs(@PathVariable Long albumId) {
        AlbumInfo albumInfo = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumInfo);
    }

    @GetMapping
    ResponseEntity<GetAllAlbumsResponseDto> getAllAlbums() {
        Set<AlbumDto> allAlbums = songifyCrudFacade.findAllAlbums();
        return ResponseEntity.ok(new GetAllAlbumsResponseDto(allAlbums));
    }

}
