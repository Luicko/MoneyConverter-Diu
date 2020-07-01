package model;
 
public class Song implements Comparable<Song> {
    private final String name;
    private final String artist;
    
    public Song(String name, String artist){
        this.name = name;
        this.artist = artist;
    }
        
    public String getName() {
        return this.name;
    }
    
    public String getArtist() {
        return this.artist;
    }

    @Override
    public int compareTo(Song newSong) { // El compareTo ordena alfabeticamente los nombres de las canciones dentro del Sortset
        return name.compareTo(newSong.getName());
    }
}    
