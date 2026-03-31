package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class MacroOneMenu extends MacroBase {

    private int selected = 0;

    private final String[] options = {
        "1. Barn",
        "2. Instant"
    };

    public MacroOneMenu(Screen screen) throws Exception {
        super(screen);
    }

    public void run() throws Exception {
        while (true) {
            draw();

            KeyStroke key = screen.readInput();

            if (key.getKeyType() == KeyType.ArrowDown || isKey(key, 'j')) {
                if (selected < options.length - 1) selected++;
            } else if (key.getKeyType() == KeyType.ArrowUp || isKey(key, 'k')) {
                if (selected > 0) selected--;
            } else if (key.getKeyType() == KeyType.Enter) {
                if (selected == 0) {
                    new MacroBarn(screen).run();
                } else if (selected == 1) {
                    new MacroInstant(screen).run();
                }
            } else if (key.getKeyType() == KeyType.Escape) {
                break;
            }
        }
    }

    private void draw() throws Exception {
        screen.doResizeIfNecessary();
        screen.clear();
        TextGraphics tg = screen.newTextGraphics();
        Renderer.drawOuterBorder(screen, tg, "One");
        Renderer.drawMenuBox(screen, tg, options, selected);
        screen.refresh();
    }

    private boolean isKey(KeyStroke key, char c) {
        return key.getKeyType() == KeyType.Character && key.getCharacter() == c;
    }
}