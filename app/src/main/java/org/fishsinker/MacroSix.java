package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;

public class MacroSix extends MacroBase {

    public MacroSix(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void run() throws Exception {
        drawStatus("MacroSix running...");

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