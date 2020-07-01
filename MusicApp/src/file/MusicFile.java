/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Album;

import model.Library;
import model.Playlist;
import model.Song;
import model.User;
/**
 *
 * @author Ignacio
 */
public class MusicFile {
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    private final Library library;
    
    public MusicFile(Library library){
        this.library = library;
    }
    
    public void loadTo(User usuario){
        
        try {            
            archivo = new File ("Playlists.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            
            String linea;
            while((linea=br.readLine())!=null){
                int corteUserFin = linea.indexOf('+');
                String nombreUser = linea.substring(0, corteUserFin);
                if(usuario.getName().equals(nombreUser)){
                    linea = linea.substring(corteUserFin+1, linea.length());
                    int cortePlayIni = linea.indexOf('{');
                    int cortePlayFin = linea.indexOf('}');
                    if(cortePlayIni != -1){
                        String nombrePlay = linea.substring(0, cortePlayIni);
                        String contPlay = linea.substring(cortePlayIni+1, cortePlayFin);
                        usuario.addPlaylist(nombrePlay);
                        while(contPlay.length() > 0){
                            int corteAlbumIni = contPlay.indexOf('[');
                            int corteAlbumFin = contPlay.indexOf(']');
                            if(corteAlbumIni != -1){
                                String nombreAlbum = contPlay.substring(0, corteAlbumIni);
                                contPlay = contPlay.substring(corteAlbumFin+1, contPlay.length());
                                usuario.searchPlayList(nombrePlay).albums.add(library.searchAlbum(nombreAlbum));
                            }else{                       
                                int corteSongIniNa = contPlay.indexOf('(');
                                int corteSongFinNa = contPlay.indexOf(')');
                                if(corteSongIniNa != -1){
                                    String nombreSongNa = contPlay.substring(corteSongIniNa+1, corteSongFinNa);
                                    contPlay = contPlay.substring(corteSongFinNa+1, contPlay.length());
                                    usuario.searchPlayList(nombrePlay).songs.add(library.searchSong(nombreSongNa));                            
                                }
                            }
                        }
                    }
                }                
            }           
        }catch(Exception e){
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
            }
        }
    }
    
    public void saveTo(User usuario){
        FileWriter fichero = null;
        PrintWriter escribir;
        
        archivo = new File ("Playlists.txt");
        try {
            fr = new FileReader (archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        br = new BufferedReader(fr);
        String linea;
        String auxiliar = "";
        try {
            while((linea = br.readLine()) != null){
                int corteUserFin = linea.indexOf('+');
                String nombreUser = linea.substring(0, corteUserFin);
                if(!usuario.getName().equals(nombreUser)){
                    auxiliar += linea + '\n';
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
           try {
           if (null != fichero)
              fr.close();
           } catch (Exception e2) {
           }
        }
        
        try {   
            fichero = new FileWriter("Playlists.txt");
            escribir = new PrintWriter(fichero); 
            escribir.write("");
            Iterator<Playlist> playIter = usuario.getPlaylist().iterator();
            while(playIter.hasNext()){
                Playlist playAux = playIter.next();
                escribir.print(usuario.getName() + "+" + playAux.getName() + '{');
                Iterator<Album> albumIter = usuario.searchPlayList(playAux.getName()).getAlbums().iterator();
                while(albumIter.hasNext()){
                    Album albAux = albumIter.next();
                    escribir.print(albAux.getName() + '[');
                    Iterator<Song> songIterAlb = usuario.searchPlayList(playAux.getName()).searchAlbum(albAux.getName()).getSongs().iterator();
                    while(songIterAlb.hasNext()){
                        Song songAux = songIterAlb.next();
                        escribir.print('(' + songAux.getName() + ')');
                    }
                    escribir.print(']');
                }      

                Iterator<Song> songIter = usuario.searchPlayList(playAux.getName()).getSongs().iterator();
                while(songIter.hasNext()){
                    Song song = songIter.next();
                    escribir.print('(' + song.getName() + ')');
                }
                escribir.print("}\n");                      
            }
            escribir.print(auxiliar);
        } catch (Exception e) {
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
           }
        }
    }
    
    public void escribirFichero(String usuario, String password, String playlist){
        FileWriter fichero = null;
        PrintWriter escribir;
        try {   
            fichero = new FileWriter("Usuarios.txt", true);
            escribir = new PrintWriter(fichero);
            if(playlist == null){
                escribir.print(usuario + ", " + password + ". ");                
            }else if(playlist.equals("")){
                escribir.print('\n'+usuario + ", ");                
            }else{
                escribir.print(playlist + ", ");
            }           
        } catch (Exception e) {
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
           }
        }
    }
    
    public void leerFichero(){
        try {            
            archivo = new File ("Usuarios.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
        
            String linea;
            while((linea=br.readLine())!=null){
                int last = -2;
                while(linea.length() > 0){ 
                    last = last+2;
                    linea = linea.substring(last, linea.length());                    
                    last = linea.indexOf(',');
                    String usuario = linea.substring(0, last);
                    
                    last = last+2;
                    linea = linea.substring(last, linea.length()); 
                    last = linea.indexOf('.');
                    String password = linea.substring(0, last);                    
                    library.addUser(usuario, password);
                }
            }           
        }catch(Exception e){
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
            }
        }
    }
    
    public void inicializaAlbumes(String ruta){
        File carpeta = new File(ruta);
        String[] aux;
        aux = carpeta.list();
        if (aux == null || aux.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
        }else{
            for (String listado1 : aux) {
                int corte = listado1.indexOf('-');
                String titulo = listado1.substring(0, corte-1);
                String artista = listado1.substring(corte+1, listado1.length());
                library.addAlbum(titulo, artista);
                String path = "Musica/Albums/"+listado1+'/';
                System.out.println(path);
                inicializaCanciones(path, titulo);                
            }
        }
    }
    
    
    
    public void inicializaCanciones(String path, String album){
        File carpeta = new File(path);
        String[] aux;
        aux = carpeta.list();
        if (aux == null || aux.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
        }else{
            if(album == null){
                for (String listado1 : aux) {
                    int corte = listado1.indexOf('-');
                    String titulo = listado1.substring(0, corte-1);
                    String artista = listado1.substring(corte+1, listado1.length()-4);
                    library.addSongLibrary(titulo, artista);
                    System.out.print(titulo);
                    System.out.println(artista);
                }
            }else{
                for (String listado1 : aux) {
                    int corte = listado1.indexOf('-');
                    String titulo = listado1.substring(0, corte-1);
                    String artista = listado1.substring(corte+1, listado1.length()-4);
                    library.searchAlbum(album).addSongAlbumLibrary(titulo, artista);
                    System.out.print(album + " ");
                    System.out.print(titulo);
                    System.out.println(artista);
                }
            }
        }
    }
}
