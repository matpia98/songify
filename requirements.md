SONGIFY: AN APPLICATION TO MANAGE ALBUMS, ARTISTS AND SONGS

~~1. an artist can be added (artist name)~~
~~2. you can add a music genre (name of the genre)~~
~~3. you can add an album (title, release date, but at least one song must be in it)~~
~~4. you can add a song (title, duration, release date, and the artist it belongs to)~~
   You can delete an artist (delete their songs and albums)
6. You can delete a genre (but there cannot be a song with this genre)
7. you can delete an album (but only when there is no longer any song associated with the album)
8. you can delete a song
9. you can edit the artist's song, and the artist's name
10. You can edit the name of the music genre
11. you can edit the album (add songs, artists, change album name)
12. You can edit the song (duration, artist, song name)
13. Songs can only be assigned to albums
14. you can assign artists to albums (an album can have more artists, an artist can have several albums)
15. you can assign only one genre to a song
16. if there is no music genre assigned to a song, "default" is displayed
~~17. all songs can be displayed~~
18. All genres can be displayed
~~19. all artists may be displayed~~ 
20. All Albums may be displayed~~
21. ~~All albums with artists and songs in the album may be displayed.~~
22. Can display specific music genres with songs
23. You may view specific artists with their albums
24. you want to have persistent data


HAPPY PATH (user tworzy album "Eminema" z piosenkami "Til i collapse", "Lose Yourself", o gatunku Rap)

given there is no songs, artists, albums and genres created before

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