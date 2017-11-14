package synthpp;


import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Metronome extends Thread
{
    private AtomicBoolean keepRunning;
    static double bpm = 120.0;
    private Synthesizer synth;
    private int defaultChannel = 1;
    private MidiChannel channel;

    public static  String getBPM()
    {
        int intBpm = (int)bpm;
        return Integer.toString(intBpm);
    }


    public Metronome()
    {
        keepRunning = new AtomicBoolean(true);
        try {
            this.synth = MidiSystem.getSynthesizer();
            this.synth.open();
        }catch(MidiUnavailableException e){
            e.printStackTrace();
        }
        channel = synth.getChannels()[defaultChannel];
    }

    public void end()
    {
        channel.setMute(true);
        keepRunning.set(false);
    }

    @Override
    public void run()
    {
        while (keepRunning.get())
        {
            try
            {
                Thread.sleep((long)(1000*(60.0/bpm)));
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            int noteNum = (int)(69 + 12 * Math.log(1280/440));
            channel.noteOn(noteNum,125 );
        }
    }
}
