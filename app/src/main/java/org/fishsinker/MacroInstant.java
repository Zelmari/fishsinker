package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyType;

public class MacroInstant extends MacroBase {

    public MacroInstant(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void run() throws Exception {
        drawStatus("MacroInstant — not yet implemented");

        while (true) {
            if (shouldExit()) break;
            Thread.sleep(100);
        }
    }
}