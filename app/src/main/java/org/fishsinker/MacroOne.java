package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;

public class MacroOne extends MacroBase {

    public MacroOne(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void run() throws Exception {
        drawStatus("MacroOne running...");

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