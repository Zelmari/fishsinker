package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;

public abstract class MacroBase {

    protected final Screen screen;

    public MacroBase(Screen screen) {
        this.screen = screen;
    }

    public abstract void run() throws Exception;

    protected void drawStatus(String message) throws Exception {
        screen.clear();
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(2, 2, message);
        tg.putString(2, 2, message);
        tg.putString(2, 4, "Press Escape to stop and go back");
        screen.refresh();
    }

    protected boolean shouldExit() throws Exception {
        var key = screen.pollInput();
        return key != null && key.getKeyType() == KeyType.Escape;
    }
}