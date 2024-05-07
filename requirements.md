SONGIFY: AN APPLICATION TO MANAGE ALBUMS, ARTISTS AND SONGS

~~1. you can add an artist (artist name)~~
~~2. you can add a music genre (genre name)~~
~~3. you can add an album (title, release date, but there must be at least one song in it)~~
~~4. You can add a song (title, duration, release date, song language)~~
~~5. you can add the artist right away with the album and song (default values)~~
~~5. You can delete an artist (in which case you delete their songs and albums)~~
6. You can delete a genre (but there cannot be a song with that genre)
7. an album can be deleted (but only when there is no longer any song associated with the album)
~~8. you can delete a song, but you don't delete the album and artists when there was only 1 song in the album~~
~~9. artist's name can be edited~~
10. You can edit the name of a music genre
11. you can edit the album (add songs, artists, change album name)
12. You can edit the song (duration, artist, song name)
13. Songs can only be assigned to albums
14. songs can be assigned to artist (through album)
~~14. you can assign artists to albums (an album can have more artists, an artist can have several albums)~~
15. you can assign only one music genre to a song
16. if there is no music genre assigned to a song, "default" is displayed
~~17. you can display all songs~~
18. all genres can be displayed
~~19. You can display all artists~~
20. You can display all albums
~~21. You can display specific albums with artists and songs in the album~~
22. Specific music genres can be displayed with songs
23. You can display specific artists with their albums
24. you want to have persistent data

HAPPY PATH (user creates the album "Eminem" with songs "Til and collapse", "Lose Yourself", about the Rap genre)

1. when I go to /song then I can see no songs
2. when I post to /song with Song "Till i collapse" then Song "Til i collapse" is returned with id 1
3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2
4. when I go to /genre then I can see no genres
5. when I post to /genre with Genre "Rap" then Genre "Rap" is returned with id 1
6. when I go to /song/1 then I can see default genre
7. when I put to /song/1/genre/1 then Genre with id 1 ("Rap") is added to Song with id 1 ("Til i collapse")
8. when I go to /song/1 then I can see "Rap" genre
9. when I put to /song/2/genre/1 then Genre with id 1 ("Rap") is added to Song with id 2 ("Lose Yourself")
10. when I go to /album then I can see no albums
11. when I post to /album with Album "EminemAlbum1" and Song with id 1 then Album "EminemAlbum1" is returned with id 1
12. when I go to /album/1 then I can see song with id 1 added to it
13. when I put to /album/1/song/1 then Song with id 1 ("Til i collapse") is added to Album with id 1 ("EminemAlbum1")
14. when I put to /album/1/song/2 then Song with id 2 ("Lose Yourself") is added to Album with id 1 ("EminemAlbum1")
15. when I go to /album/1/song then I can see 2 songs (id 1, id 2)
16. when I post to /artist with Artist "Eminem" then Artist "Eminem" is returned with id 1
17. when I put to /album/1/artist/2 then Artist with id 1 ("Eminem") is added to Album with id 1 ("EminemAlbum1")


HAPPY PATH V2 (after refactoring)
1. when I go to /song then I can see no songs
2. when I post to /song with Song "Till i collapse" then Song "Til i collapse" is returned with id 1
3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2
4. when I go to /genre then I can see only default genre with id 1
5. when I post to /genre with Genre "Rap" then Genre "Rap" is returned with id 2
6. when I go to /song/1 then I can see default genre with id 1 and name default
7. when I put to /song/1/genre/1 then Genre with id 2 ("Rap") is added to Song with id 1 ("Til i collapse")
8. when I go to /song/1 then I can see "Rap" genre
9. when I go to /albums then I can see no albums
10. when I post to /albums with Album "EminemAlbum1" and Song with id 1 then Album "EminemAlbum1" is returned with id 1
11. when I go to /albums/1 then I can not see any albums because there is no artist in system
12. when I post to /artists with Artist "Eminem" then Artist "Eminem" is returned with id 1
13. when I put to /artists/1/albums/2 then Artist with id 1 ("Eminem") is added to Album with id 1 ("EminemAlbum1")
14. when I go to /albums/1 then I can see album with single song with id 1 and single artist with id 1
15. when I put to /albums/1/songs/2 then Song with id 2 ("Lose Yourself") is added to Album with id 1 ("EminemAlbum1")
16. when I go to /albums/1 then I can see album with 2 songs (id1 and id2)