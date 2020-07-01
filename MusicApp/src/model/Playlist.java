package model;

import java.util.ArrayList;

 
public class Playlist {
    private String name;
    public ArrayList<Song> songs;
    public ArrayList<Album> albums;
    
    // Constructor
    public Playlist(String name) {
        this.name = name;
        songs = new ArrayList<>();
        albums = new ArrayList<>();
    }
    
    // Getters y Setters
    public void setName (String name) {
        this.name = name;
    }
    
     public String getName() {
        return this.name;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
    
    // Añade una cancion
    public void addSong (Song song) {
        songs.add(song);
    }
    
    // Elimina una cancion
    public void removeSong (Song song) {
        songs.remove(song);
    }
    
     public void removeSong(int index){
        songs.remove(index); // elimina en esa posicion
    }
    
    // Añade un album, composición
    public void addAlbumPlaylistUser (Album album) {
        albums.add(album);
    }
    
    // Añade un album, agreagación
    public void addAlbumPlaylistLibrary (String name, String artist) {
        albums.add(new Album(name, artist));
    }
    
    // Elimina un album
    public void removeAlbum (Album album) {
        albums.remove(album);
    }
    
     public void removeAlbum(int index){
        albums.remove(index); // elimina en esa posicion
    }
          
    // Busca cancion
    public Song searchSong(String name){
        for (Song song : songs) {
            if (song.getName().equals(name)) {
                return song;
            }
        }
        return null;
    }
    
    // Busca album
    public Album searchAlbum(String name){
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }    
}