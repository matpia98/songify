package com.songify.domain.crud;

class ArtistNotFoundException extends RuntimeException {
    ArtistNotFoundException(String message) {
        super("artist with id " + message + " not found");
    }
}
