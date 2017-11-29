
import org.junit.Before; //JUnit 4
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.Test; //JUnit 4

import synthpp.MP3Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Steven on 11/16/2017.
 */

@Nested
@DisplayName("MP3Player Testing")
public class MP3PlayerTest {
    private static MP3Player mp3Player;

    //Change to BeforeAll for JUnit 5
    @Before
    public void initialize() {
        //this test mp3 is only 2 seconds in length
        mp3Player = new MP3Player("beep1.mp3");
    }
    @Test
    @DisplayName("Test all controls")
    public void playPauseStop(){
        mp3Player.play();
        assertEquals(mp3Player.isPlaying(), true);
        mp3Player.pause();
        assertEquals(mp3Player.isPaused(), true);
        mp3Player.stop();
        assertEquals(mp3Player.isStopped(), true);
    }
    @Test
    @DisplayName("Test For Audio File Replaying Bug")
    public void checkMP3FinishedPlayingState(){
        mp3Player.play();
        while(mp3Player.isPlaying()){
            try{
                Thread.sleep(20);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        assert(mp3Player.isStopped());
    }
}
