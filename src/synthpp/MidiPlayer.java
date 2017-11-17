package synthpp;

import java.io.*;
import javax.sound.midi.*;

/**
 * Created by Steven on 10/9/2017.
 */
public class MidiPlayer {
    private String filename;
    private Sequence sequence;
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    private boolean sequenceLoaded;
    private boolean isPlaying;


    public MidiPlayer() {
        try {
            //default Sequencer
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            if (!(sequencer instanceof Synthesizer)) {
                synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();
                Receiver synthReceiver = synthesizer.getReceiver();
                Transmitter seqTransmitter = sequencer.getTransmitter();
                seqTransmitter.setReceiver(synthReceiver);
            }
            filename = "";
            sequenceLoaded = false;
            isPlaying = false;
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void load(String filename){
        this.filename = filename;
        try{
            sequence = MidiSystem.getSequence(new File(filename));
            sequencer.setSequence(sequence);
            sequenceLoaded = true;
        }catch(InvalidMidiDataException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void play(){
        //make sure the sequencer is open
        try {
            if (!sequencer.isOpen()) {
                sequencer.open();
                load(filename);
            }
        }catch(MidiUnavailableException e){
            e.printStackTrace();
        }

        //start playing
        if(sequenceLoaded && !sequencer.isRunning() && !isPlaying){
                isPlaying = true;
                sequencer.start();
        }
    }

    public void stop() {
        if(sequencer.isRunning()) {
            isPlaying = false;
            sequencer.stop();
        }
        sequencer.setTickPosition(0);
    }

    public void pause(){
        if(sequencer.isRunning()) {
            isPlaying = false;
            sequencer.stop();
        }
        //don't reset position to beginning
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public boolean sequenceLoaded(){
        return this.sequenceLoaded;
    }
}
