package com.songify.domain.crud.song;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "song")
class Song extends BaseEntity {
    @Id
    @GeneratedValue(generator = "song_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String artist;

    private Instant releaseDate;
    private Long duration;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public Song(String name) {
        this.name = name;
    }

}