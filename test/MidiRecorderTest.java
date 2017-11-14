
import org.junit.Before; //JUnit 4
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.Test; //JUnit 4

import synthpp.MidiRecorder;

import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Steven on 10/27/2017.
 */

@Nested
@DisplayName("MidiRecorder Testing")
public class MidiRecorderTest {
    private static MidiRecorder midiRecorder;

    //Change to BeforeAll for JUnit 5
    @Before
    public void initialize(){
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
        int inRangeNote = random.nextInt(127 + 1 - 0) + 0; //0 to 127
        int belowRangeNote = random.nextInt(-1 + 1 - (-999)) + (-999); //-999 to -1
        int overRangeNote = random.nextInt(999 + 1 - 128) + 128; //128 to 999

        //this should be valid and return the note added
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
        //add a note to the buffer and make sure when getting the buffer it contains the note added
        int noteToAdd = 64;
        int ret = midiRecorder.addNote(noteToAdd,125,0,0);
        assertEquals(midiRecorder.getNoteBuffer()[0].intValue(), noteToAdd);
    }
    @Test
    @DisplayName("Delete note operation.")
    public void deleteNoteAt(){
        int note0 = 64;
        int note1 = 65;
        int note2 = 66;
        int note3 = 67;
        int tickIndex = 0;
        midiRecorder.addNote(note0,125,tickIndex++,0);
        midiRecorder.addNote(note1,125,tickIndex++,0);
        midiRecorder.addNote(note2,125,tickIndex++,0);
        midiRecorder.addNote(note3,125,tickIndex++,0);

        //check that tick index 2 is equal to note1
        assertEquals(midiRecorder.getNoteBuffer()[2].intValue(), note2);

        //now delete note at tick index 2
        midiRecorder.deleteNoteAt(2);

        //now make sure tick index is equal to note2
        assertNotEquals(midiRecorder.getNoteBuffer()[2].intValue(), note2);
    }
    @Test
    @DisplayName("Crate new track operation.")
    public void createNewTrack(){
        //Creating a new track in the MidiRecorder class deletes the old track and starts a new one
        //The midiRecorder creates a track when instantiated so I'll add some notes
        //to that track, verify it has the notes, then call create new track to confirm
        //the size of the new track which should be 0
        int note0 = 64;
        int note1 = 65;
        int note2 = 66;
        int note3 = 67;
        int tickIndex = 0;
        midiRecorder.addNote(note0,125,tickIndex++,0);
        midiRecorder.addNote(note1,125,tickIndex++,0);
        midiRecorder.addNote(note2,125,tickIndex++,0);
        midiRecorder.addNote(note3,125,tickIndex++,0);

        //confirm the size is 4
        assertEquals(midiRecorder.getNoteBuffer().length, 4);

        //now create a new track which should delete the old track
        midiRecorder.createNewTrack();

        //now confirm the size is 0, indicating a new track was created
        assertEquals(midiRecorder.getNoteBuffer().length, 0);

    }
    @Test
    @DisplayName("Save to file operation.")
    public void saveToFile(){
        //I'll create a midi track and save it, the attempt to open it which
        //will verify that it was created and saved to file
        int note0 = 64;
        int note1 = 65;
        int note2 = 66;
        int note3 = 67;
        int tickIndex = 0;
        midiRecorder.addNote(note0,125,tickIndex++,0);
        midiRecorder.addNote(note1,125,tickIndex++,0);
        midiRecorder.addNote(note2,125,tickIndex++,0);
        midiRecorder.addNote(note3,125,tickIndex++,0);


        String filename = "test.mid";

        //save the midi file to disk
        midiRecorder.saveToFile(filename);

        //create a reference to a file named "filename"
        File savedFile = new File(filename);

        //check to see that it actually exists
        assert(savedFile.exists());

        //clean up for the next test
        savedFile.delete();

        //make sure it got deleted
        assertEquals(savedFile.exists(), false);
    }

}
