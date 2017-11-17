
import org.junit.Before; //JUnit 4
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.Test; //JUnit 4

import synthpp.MP3Player;

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
        mp3Player = new MP3Player("sample.mp3");
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
}