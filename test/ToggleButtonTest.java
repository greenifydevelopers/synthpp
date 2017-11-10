import org.junit.Before;
import org.junit.Test;
import synthpp.ToggleButton;
import processing.core.PApplet;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToggleButtonTest
{
    private static ToggleButton toggleButton;

    @Before
    public void initialize()
    {
        PApplet p = new PApplet();
        toggleButton = new ToggleButton(p, 20, 36,768, 12, Color.black, Color.white, Color.green, ToggleButton.DIRECTION.vertical);
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



}
