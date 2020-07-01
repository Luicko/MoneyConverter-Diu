package musicapp;

import file.MusicFile;
import gui.MusicAppForm;
import model.Library;

public class MusicApp {
    public static void main(String[] args) {
        Library library = new Library();
        MusicFile music = new MusicFile(library);
        music.inicializaCanciones("Musica/Songs", null);
        music.inicializaAlbumes("Musica/Albums");
        music.leerFichero();
        
        MusicAppForm musicform = new MusicAppForm();
        musicform.assignLibrary(library);
        musicform.setVisible(true);
    }
    
}
