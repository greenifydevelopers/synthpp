package synthpp;

import javax.sound.midi.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Steven on 10/5/2017.
 */
public class MidiRecorder {
    private Sequence seq = null;
    private Track track = null;
    private ArrayList<Integer> noteBuffer;

    public MidiRecorder(){
        try{
            seq = new Sequence(Sequence.PPQ, 1);
        }catch(InvalidMidiDataException e){
            e.printStackTrace();
        }
        track = seq.createTrack();
        noteBuffer = new ArrayList<>();
    }


    public void addNote(int noteNumber,int velocity, long tickIndex, int channel){
        track.add(createNoteOnEvent(noteNumber, velocity, tickIndex, channel));
        noteBuffer.add(noteNumber);
    }

    public int[] getNoteBuffer(){
        int notes[] = new int[noteBuffer.size()];
        for(int i = 0; i < noteBuffer.size(); i++){
            notes[i] = noteBuffer.get(i);
        }
        return notes;
    }

    public void deleteNoteAt(int tickIndex){
        track.remove(track.get(tickIndex));
        noteBuffer.remove(tickIndex);
    }

    public void createNewTrack(){
        track = null;
        track = seq.createTrack();
    }

    public void saveToFile(String filename){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            MidiSystem.write(seq, 0, fileOutputStream);
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error saving to file: " +  filename);
        }
    }

    private static MidiEvent createNoteOnEvent(int nKey,int velocity, long tickIndex, int channel){
        return createNoteEvent(ShortMessage.NOTE_ON, nKey,velocity,tickIndex, channel);
    }

    private static MidiEvent createNoteOffEvent(int nKey,int velocity, long tickIndex, int channel){
        return createNoteEvent(ShortMessage.NOTE_OFF, nKey,velocity,tickIndex, channel);
    }

    private static MidiEvent createNoteEvent(int nCommand,
                                             int nKey,
                                             int nVelocity,
                                             long tickIndex,
                                             int channel){
        ShortMessage message = new ShortMessage();
        try{
            message.setMessage(nCommand,channel,nKey,nVelocity);
        }catch (InvalidMidiDataException e){
            e.printStackTrace();
            System.exit(1);
        }
        MidiEvent event = new MidiEvent(message,tickIndex);
        return event;
    }
}
