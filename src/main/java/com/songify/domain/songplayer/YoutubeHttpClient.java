package com.songify.domain.songplayer;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

interface YoutubeHttpClient {
    String playSongByName(String name);
}
