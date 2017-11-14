import org.junit.Before;
import org.junit.Test;
import synthpp.ToggleButton;
import processing.core.PApplet;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToggleButtonTest
{
    private static ToggleButton toggleButton;
    private static PApplet p;

    @Before
    public void initialize()
    {
        p = new PApplet();
        toggleButton = new ToggleButton(p, 20, 36,768, 12, Color.black, Color.white, Color.green, ToggleButton.DIRECTION.horizontal);
    }

    @Test
    public void toggleButtonOff()
    {
        boolean result = false;

        if(!toggleButton.isOn())
        {
            result = true;
        }

        assertEquals(true, result);
    }

    @Test
    public void toggleButtonOn()
    {
        boolean result = false;

        toggleButton.mousePressed(p);

        if(toggleButton.isOn())
        {
            result = true;
        }

        assertEquals(true, result);
    }

    @Test
    public void toggleButtonDirection()
    {
        boolean result = false;

        if(toggleButton.isHorizontal())
        {
            result = true;
        }

        assertEquals(true, result);
    }
}
