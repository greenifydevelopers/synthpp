import org.junit.Before; //JUnit 4
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.Test; //JUnit 4

import processing.core.PApplet;
import synthpp.KeyBoard;

import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayName("KeyBoard Testing")
public class KeyBoardTest {
    private static KeyBoard keyboard;
    private static PApplet pApp;
    @Before

    public void initialize() {keyboard = new KeyBoard(pApp, 250, 100, 200,200);}

    @Test
    @DisplayName("Test Creation")
    public void checkCreation(){
        boolean results = false;
        if (keyboard != null){
            results = true;
        }
        assertEquals(true, results);
    }

    @Test
    @DisplayName("Test Get Octave")
    public void checkGetOctave(){
        boolean results = false;
        int currentOctave = 100;
        currentOctave = keyboard.getOctave();
        if (currentOctave >= -1 && currentOctave <= 11){
            results = true;
        }
        assertEquals(true, results);
    }

    @Test
    @DisplayName("Test Set Octave")
    public void checkSetOctave(){
        boolean results = false;
        keyboard.setOctave(4);
        if (keyboard.getOctave() == 4){
            results = true;
        }
        assertEquals(true, results);
    }

    @Test
    @DisplayName("Test Get Reference Tones")
    public void checkGetReferenceTones(){
        boolean results = true;
        int[] refToneCopy = keyboard.getReferenceTones();
        int prev = refToneCopy[0];
        int current;
        for (int x = 1; x < refToneCopy.length; x++){
            current = refToneCopy[x];
            if (current != prev + 1){
                results = false;
            }
            prev = current;
        }
        assertEquals(true, results);
    }

    @Test
    @DisplayName("Test Shift Octave")
    public void checkShiftOctave(){
        boolean results = true;
        int[] refToneCopy = keyboard.getReferenceTones();
        int[] comparisonArr =  {0,1,2,3,4,5,6,7,8,9,10,11,12};
        keyboard.setOctave(0);
        for (int x = 0; x < refToneCopy.length; x++){
            if (refToneCopy[x] != comparisonArr[x]){
                results = false;
            }
        }

        assertEquals(true, results);
    }

    @Test
    @DisplayName("Test Get Notes")
    public void checkGetNotes(){
        boolean results = true;
        String[] sample = {"C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B","C"};
        String[] data = keyboard.getNotes();
        for (int x = 0; x < sample.length; x++){
            if (sample[x] != data[x]){
                results = false;
            }
        }
        assertEquals(true, results);
    }






}
