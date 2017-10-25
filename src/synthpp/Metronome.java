package synthpp;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import java.util.concurrent.atomic.AtomicBoolean;

public class Metronome extends Thread
{
    private AtomicBoolean keepRunning;
    static double bpm = 120.0;
    private Minim minim = new Minim(this);
    AudioOutput out = minim.getLineOut(Minim.STEREO);

    public static  String getBPM()
    {
        int intBpm = (int)bpm;
        return Integer.toString(intBpm);
    }


    public Metronome()
    {
        keepRunning = new AtomicBoolean(true);
    }

    public void end()
    {
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

            out.playNote(1250);
        }
    }
}
