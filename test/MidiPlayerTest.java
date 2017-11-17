import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import synthpp.MidiPlayer;
import synthpp.MidiRecorder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;


@DisplayName("MidiPlayerTest")
public class MidiPlayerTest {
    //the player object to be tested
    MidiPlayer player;

    @Before
    public void setUp(){
        player = new MidiPlayer();
    }

    @After
    public void tearDown(){
            File file = new File("testFile");
            file.delete();
    }

    @Test
    @DisplayName("Load File Test")
    public void loadFileTest(){
        player.load("testFile");
        assertFalse(player.sequenceLoaded());

        //create testFile
        MidiRecorder recorder = new MidiRecorder();
        //the note to use for the file
        int note;
        Random random = new Random();
        note = random.nextInt(128);
        for(int i = 0; i < 10000; i++){
            recorder.addNote(note, 125, i, 0);
        }
        recorder.saveToFile("testFile");

        player.load("testFile");
        assertTrue(player.sequenceLoaded());

    }

    @Test
    @DisplayName("Play Test")
    public void playTest(){

        //create testFile
        MidiRecorder recorder = new MidiRecorder();
        //the note to use for the file
        int note;
        Random random = new Random();
        note = random.nextInt(128);
        for(int i = 0; i < 100000; i++){
            recorder.addNote(note, 125, i, 0);
        }

        recorder.saveToFile("testFile");

        player.load("testFile");
        player.play();
        assertTrue(player.isPlaying());
        //might want to add a test to make sure that the MidiPlayer stops playing after hitting EOF

    }

    @Test
    @DisplayName("Stop Method Test")
    public void stopTest(){
        assertFalse(player.isPlaying());

        player.stop();
        assertFalse(player.isPlaying());

        //create testFile
        MidiRecorder recorder = new MidiRecorder();
        //the note to use for the file
        int note;
        Random random = new Random();
        note = random.nextInt(128);
        for(int i = 0; i < 1000; i++){
            recorder.addNote(note, 125, i, 0);
        }

        recorder.saveToFile("testFile");

        player.load("testFile");
        player.play();

        player.stop();
        assertFalse(player.isPlaying());

    }

    @Test
    @DisplayName("pause Test")
    public void pauseTest(){
        //create testFile
        MidiRecorder recorder = new MidiRecorder();
        //the note to use for the file
        int note;
        Random random = new Random();
        note = random.nextInt(128);
        for(int i = 0; i < 1000; i++){
            recorder.addNote(note, 125, i, 0);
        }

        recorder.saveToFile("testFile");

        player.load("testFile");
        player.play();

        assertTrue(player.isPlaying());

        player.pause();
        assertFalse(player.isPlaying());

        player.play();
        assertTrue(player.isPlaying());

    }

}
