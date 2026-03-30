package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;

public class MacroThree extends MacroBase {

    public MacroThree(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void run() throws Exception {
        drawStatus("MacroThree running...");

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