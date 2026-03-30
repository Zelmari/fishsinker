package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;

public class MacroFour extends MacroBase {

    public MacroFour(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void run() throws Exception {
        drawStatus("MacroFour running...");

        while (true) {
            //
            //
            //  Macro logic
            //
            //

            if (shouldExit()) break;
        }
    }
}