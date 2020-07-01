package model;

import java.util.HashSet;
import java.util.Iterator;

public class Library {  
     public HashSet<User> users;
     public HashSet<Song> songs;
     public HashSet<Album> album;
     
      public Library(){
        users = new HashSet<>();
        songs = new HashSet<>();
        album = new HashSet<>();
    }
    
    public void addAlbum(String name, String interprete){
        album.add(new Album(name, interprete));
    }  
      
    //Añadir usuario
    public void addUser(String username, String password){        
        users.add(new User(username, password)); // como se crea el grupo al añadir, si se elimina agenda desaparecen todos los grupos        
    }
    
    //Añadir song
    public void addSongLibrary(String name, String artist){
        songs.add(new Song(name, artist)); // como se crea el grupo al añadir, si se elimina agenda desaparecen todos los grupos
    }

    public HashSet<User> getUsers() {
        return users;
    }

    public HashSet<Song> getSongs() {
        return songs;
    }
    
    //Buscar album
    public Album searchAlbum(String nombre){
        Iterator<Album> iteratorAlbum = album.iterator();
            while(iteratorAlbum.hasNext()){
                Album albumActual = iteratorAlbum.next();
                if (albumActual.getName().equals(nombre)){
                    return albumActual;
                }
            }
        return null;
    }
    
    //Buscar playlist
    public Song searchSong(String nombre){        
        Iterator<Song> iteratorSon = songs.iterator();
        while(iteratorSon.hasNext()){
            Song songActual = iteratorSon.next();    
            if(songActual.getName().toLowerCase().equals(nombre.toLowerCase())){
                return (songActual);
            }
        }
        
        Iterator<Album> iteratorAlbum = album.iterator();
        while(iteratorAlbum.hasNext()){
            Album albumActual = iteratorAlbum.next();            
            Iterator<Song> iteratorSong = albumActual.getSongs().iterator();
            while(iteratorSong.hasNext()){
                Song songActual = iteratorSong.next();
                if(songActual.getName().toLowerCase().equals(nombre.toLowerCase())){
                    return (songActual);
                }
            }                        
        }      
        return null;
    }
}
