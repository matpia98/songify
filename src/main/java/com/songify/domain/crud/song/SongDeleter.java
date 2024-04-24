package com.songify.domain.crud.song;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongDeleter {

    private final SongRepository songRepository;

    void deleteById(Long id) {
        log.info("deleting song by id: " + id);
        songRepository.deleteById(id);
    }
}
