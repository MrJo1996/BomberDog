/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberDog.utils;

import bomberDog.logic.GameManager;

import javax.sound.sampled.*;
import java.net.URL;

/*La classe SoundManager, di seguito scritta, è una classe "helper" ovvero
  aiuta ad ottenere un codice più ordinato riducendo la scrittura dello stesso
  codice più volte.
  Il compito di SoundManager è quello di caricare file audio, essendo questi
  risorse esterne, e di gestire la loro riproduzione nel gioco.*/
public class SoundManager {

    //Oggetti Clip per la riproduzione dei file audio
    private static Clip clip, clip_theme;

    //Metodi per ritornare i nomi dei vari file audio
    public static String themeMusic() {
        return "bomberman_theme.wav";
    }

    public static String gameOverMusic() {
        return "gameover.wav";
    }

    public static String youWinMusic() {
        return "youWin.wav";
    }

    public static String bombMusic() {
        return "bomb.wav";
    }

    public static String powerUpMusic() {
        return "power_up.wav";
    }

    public static String pointMusic() {
        return "money.wav";
    }

    public static String deathMusic() {
        return "zombie_death.wav";
    }

    public static String clickMusic() {
        return "click.wav";
    }

    public static String lifeLostMusic() {
        return "oneLifeLost.wav";
    }

    /*Metodo per il caricamento del file audio di sottofondo e della sua
      riproduzione*/
    public static void musicThemeStart() {

        //Gestisco l'accezione
        try {

            //Nome della cartella del progetto dove leggere il file
            String folder = "/bomberDog/musics/";

            //Carica l'url del file
            URL yourFile = GameManager.class.getResource(folder + themeMusic());
            //Variabile per la linea di ingresso
            AudioInputStream stream;
            //Formato del file audio (per la decodifica)
            AudioFormat format;
            //Informazioni della linea (tipo il volume)
            DataLine.Info info;

            //Setto la linea di ingresso con il file
            stream = AudioSystem.getAudioInputStream(yourFile);
            //Acquisisco il formato audio della linea di ingresso
            format = stream.getFormat();
            //Creo le informazioni sulla linea dal suo formato
            info = new DataLine.Info(Clip.class, format);
            //Inizializzo il clip (linea di uscita) con il DataLine dell'input
            clip_theme = (Clip) AudioSystem.getLine(info);
            //Apro e avvio il clip per la riproduzione del file audio
            clip_theme.open(stream);
            clip_theme.start();

        } catch (Exception e) {
            System.out.println("Errore nella riproduzione del file audio.");
        }
    }

    /*Metodo per il caricamento dei file audio generici e della loro
      riproduzione*/
    public static void musicClipStart(String file_name) {

        //Gestisco l'eccezione
        try {

            //Nome della cartella del progetto dove leggere il file
            String folder = "/bomberDog/musics/";

            //Carica l'url del file
            URL yourFile = GameManager.class.getResource(folder + file_name);
            //Variabile per la linea di ingresso
            AudioInputStream stream;
            //Formato del file audio (per la decodifica)
            AudioFormat format;
            //Informazioni della linea (tipo il volume)
            DataLine.Info info;

            //Setto la linea di ingresso con il file
            stream = AudioSystem.getAudioInputStream(yourFile);
            //Acquisisco il formato audio della linea di ingresso
            format = stream.getFormat();
            //Creo le informazioni sulla linea dal suo formato
            info = new DataLine.Info(Clip.class, format);
            //Inizializzo il clip (linea di uscita) con il DataLine dell'input
            clip = (Clip) AudioSystem.getLine(info);
            //Apro e avvio il clip per la riproduzione del file audio

            clip.open(stream);
            clip.start();

            //volume_theme.setValue(1.0f);

        } catch (Exception e) {

            //whatevers
            System.out.println("Errore nella riproduzione del file audio.");

        }

    }

    //Metodo per fermare la musica di sottofondo
    private static void musicThemeStop() {

        //Gestisco l'eccezione
        try {

            //Fermo la riproduzione e chiudo il clip
            clip_theme.stop();

        } catch (Exception e) {
        }

    }

    //Metodo per fermare la musica generica
    private static void musicClipStop() {

        //Gestisco l'eccezione
        try {

            //Fermo la riproduzione e chiudo il clip
            clip.stop();

        } catch (Exception e) {
        }

    }

    //Metodo per fermare tutto l'audio
    public static void musicStop() {

        musicThemeStop();
        musicClipStop();

    }

}
