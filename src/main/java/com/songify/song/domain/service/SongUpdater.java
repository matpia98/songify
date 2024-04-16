package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
public class SongUpdater {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    SongUpdater(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }

    public void updateById(Long id, Song newSong) {
        songRepository.updateById(id, newSong);
    }
}
