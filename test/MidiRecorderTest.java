
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import synthpp.MidiRecorder;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Steven on 10/27/2017.
 */

@Nested
@DisplayName("MidiRecorder Testing")
public class MidiRecorderTest {
    private static MidiRecorder midiRecorder;

    @BeforeAll
    public static void initialize(){
        midiRecorder = new MidiRecorder();
    }

    @Test
    @DisplayName("Add note operation.")
    public void addNote(){
        //Equivalence testing. Values for the notes from 0 to 127 should all be valid
        //and all values lower than 0 and all values higher than 127 are invalid, thus we have 3 tests to do
        //that will test that valid and invalid notes are identified and handled properly

        //randomly generate a note to add below, in, and over the range of valid notes
        Random random = new Random();
        int inRangeNote = random.nextInt(127 + 1 + 0) - 0; //0 to 127
        int belowRangeNote = random.nextInt(-1 + 1 + 999) - 999; //-999 to -1
        int overRangeNote = random.nextInt(999 + 1 + 128) - 128; //128 to 999

        //this shoudld be valid and return the note added
        int ret = midiRecorder.addNote(inRangeNote,125,0,0);
            assertEquals(inRangeNote,ret);

        //this should return -1 because it will be a random number below the valid range
        ret = midiRecorder.addNote(belowRangeNote,125,0,0);
            assertEquals(-1, ret);

        //this should return -1 because it will be a random number above the valid range
        ret = midiRecorder.addNote(overRangeNote,125,0,0);
            assertEquals(-1, ret);
    }
    @Test
    @DisplayName("Get note buffer operation.")
    public void getNoteBuffer(){
         assertEquals(1,1);
    }
    @Test
    @DisplayName("Delete note operation.")
    public void deleteNoteAt(){
        assertEquals(1,1);
    }
    @Test
    @DisplayName("Crate new track operation.")
    public void createNewTrack(){
        assertEquals(1,1);
    }
    @Test
    @DisplayName("Save to file operation.")
    public void saveToFile(){
        assertEquals(1,1);
    }

}
