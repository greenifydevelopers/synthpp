package synthpp;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

/**
 * Created by Steven on 10/26/2017.
 */
public class MP3Player {
    private String path;
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean isPlaying;
    private boolean isStopped;
    private boolean isPaused;
    private Runnable playListener;
    private Runnable externalPlayingEndedListener;
    final JFXPanel fxPanel = new JFXPanel(); //this is required to start JAVAFX Tookkit

    /**
     * ctor
     *
     * @param path - the system path to a valid mp3 file
     */
    public MP3Player(String path){
        this.path = path;
        try {
            media = new Media(Paths.get(path).toUri().toString());
            mediaPlayer = new MediaPlayer(media);
        }catch(MediaException e){
            media = null;
            mediaPlayer = null;
        }
        isPlaying = false;
        isPaused = false;
        isStopped = false;

        playListener = new Runnable() {
            @Override
            public void run() {
                stop();
                //if there has been an external listener registered then run it
                if(externalPlayingEndedListener != null){
                    externalPlayingEndedListener.run();
                }
            }
        };
    }

    /**
     * Opens and loads an mp3 file into the player
     *
     * @param path - String containing path to a valid mp3 file
     */
    public void open(String path){
        mediaPlayer.stop();
        this.path = path;
        try {
            media = new Media(Paths.get(path).toUri().toString());
            mediaPlayer = new MediaPlayer(media);
        }catch(MediaException e){
            media = null;
            mediaPlayer = null;
        }
        isPlaying = false;
        isPaused = false;
        isStopped = false;
    }

    /**
     * Play the mp3 file
     * */
    public void play() {
        if(mediaPlayer != null){
            mediaPlayer.play();
            System.out.println("Playing...");
            isPlaying = true;
            isPaused = false;
            isStopped = false;
            mediaPlayer.setOnEndOfMedia(playListener);
        }else{
            System.out.println("Error mediaPlayer null");
            isPlaying = false;
            isPaused = false;
            isStopped = false;
        }
    }

    /**
     * Pause the mp3 file
     * */
    public void pause(){
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            isPlaying = false;
            isPaused = true;
            isStopped = false;
        }
    }

    /**
     * Stop the mp3 file
     * */
    public void stop(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
            isPaused = false;
            isStopped = true;
        }
    }

    //returns the states of the mp3 player
    public boolean isPlaying(){return isPlaying;}
    public boolean isStopped(){return isStopped;}
    public boolean isPaused(){return isPaused;}
    public String getPath(){return path;}

    //this can be used to register an external listener to be ran once playing has finished
    public void registerPlayingEndedListener(Runnable r){
        externalPlayingEndedListener = r;
    }
}
