package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;

public class MacroFive extends MacroBase {

    public MacroFive(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void run() throws Exception {
        drawStatus("MacroFive running...");

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