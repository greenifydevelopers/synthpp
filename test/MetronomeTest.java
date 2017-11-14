import org.junit.Before;
import org.junit.Test;
import synthpp.Metronome;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetronomeTest
{
    private static Metronome metro;

    @Test
    public void metronomeCreation()
    {
        boolean results = false;

        metro = new Metronome();

        if(metro != null)
        {
            results = true;
        }

        assertEquals(true,results);
    }

    @Before
    public void initialize()
    {
        metro = new Metronome();
    }
    @Test
    public void getBPMTest()
    {
        boolean results = false;

        String bpm = metro.getBPM();

        int intBPM = Integer.parseInt(bpm);

        if(intBPM == 120)
        {
            results = true;
        }

        assertEquals(true,results);
    }

    @Test
    public void isRunningTest()
    {
        boolean[] expected = new boolean[2];
        Arrays.fill(expected, Boolean.TRUE);

        boolean[] results = new boolean[2];

        boolean finalResult = false;

        //On Functionality in MainWindow
        Thread t = new Thread(metro);
        t.start();

        if(t.isAlive())
        {
            results[0] = true;
        }
        else
        {
            results[0] = false;
        }

        //Off Functionality in MainWindow
        metro.end();
        metro = null;

        if(metro == null)
        {
            results[1] = true;
        }
        else
        {
            results[1] = false;
        }

        if(expected[0] == results[0] && expected[1] == results[1])
        {
            finalResult = true;
        }

        assertEquals(true,finalResult);
    }
}