package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;

public class MacroTwo extends MacroBase {

    public MacroTwo(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void run() throws Exception {
        drawStatus("MacroTwo running...");

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