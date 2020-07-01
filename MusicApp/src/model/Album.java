package model;

import java.util.Iterator;
import java.util.TreeSet;


public class Album {
    private final String name;
    private final String artist;
    public TreeSet<Song> songs; // ordena según el comparable de Songs
    
    // Constructor
    public Album (String name, String artist){
        this.name = name;
        this.artist = artist;
        songs = new TreeSet<>();
    }
    
    public String getName () {
        return this.name;
    }
    
    public String getArtist(){
        return this.artist;
    }
    
    //composición, Añadir cancion al album desde playlist de un usuario, no se debe usar
    public void addSongPlaylistUser (Song song) {
        songs.add(song);
    }
    
    // Añadir cancion al album desde library, agregación
    public void addSongAlbumLibrary (String name, String artist) {
        songs.add(new Song(name, artist));
    }
    
     //Eliminar usuario   
    public void removeSong(String nombre){
        Iterator<Song> iteratorSong = songs.iterator();
        while(iteratorSong.hasNext()){
            if (iteratorSong.next().getName().equals(nombre)){
                iteratorSong.remove();
            }
        }
    }
    
    // Buscar cancion en un album
    public Song searchSong(String nombre){
        for(Song song : getSongs()){
            if(song.getName().equals(nombre)){
                return (song);
            }
        }
        return null;
    }
    
    // Obtener todas las canciones del album
    public TreeSet<Song> getSongs() {
        return songs;
    }
    
    //Mostrar canciones
}
