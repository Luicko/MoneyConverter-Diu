package model;
import java.util.HashSet;
import java.util.Iterator;
 
public class User {
    private final String username;
    private final String password;
    //private password password;
    private HashSet<Playlist> playlists;
    
    //Constructor
    public User (String username, String password) {
        this.username = username;
        this.password = password;
        playlists = new HashSet<>();
    }
    
    public String getName() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    // Obtener las playlists del usuario
    public HashSet<Playlist> getPlaylist () {
        return playlists;
    }
    
    //Crear playList
    public void addPlaylist (String name){
        playlists.add(new Playlist(name));
    }
    
    //Eliminar playlist   
    public void removePlaylist(String nombre){
        Iterator<Playlist> iteratorPlaylist = playlists.iterator();
        while(iteratorPlaylist.hasNext()){
            if (iteratorPlaylist.next().getName().equals(nombre)){
                iteratorPlaylist.remove();
            }
        }
    } 
    
    // Buscar por nombre y devolver objeto playlist   
    public Playlist searchPlayList(String nombre){
        Iterator<Playlist> iteratorPlaylist = playlists.iterator();
        while(iteratorPlaylist.hasNext()){
            Playlist playlistActual = iteratorPlaylist.next();
            if (playlistActual.getName().equals(nombre)){
                return playlistActual;
            }
        }
        return null;
    }
}
 
