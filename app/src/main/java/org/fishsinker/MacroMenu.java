package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalSize;

public class MacroMenu {

    private final Screen screen;
    private int selected = 0;

    // Script names to be edited in the future
    private final String[] options = {
        "1. One",
        "2. Two",
        "3. Three",
        "4. Four",
        "5. Five",
        "6. Six"
    };

    public MacroMenu(Screen screen) {
        this.screen = screen;
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
                runMacro(selected);
            } else if (key.getKeyType() == KeyType.Escape) {
                break; // Go back to the main menu
            }
        }
    }

    private void runMacro(int index) throws Exception {
        switch (index) {
            case 0 -> new MacroOne(screen).run();
            case 1 -> new MacroTwo(screen).run();
            case 2 -> new MacroThree(screen).run();
            case 3 -> new MacroFour(screen).run();
            case 4 -> new MacroFive(screen).run();
            case 5 -> new MacroSix(screen).run();
        }
    }

    private void draw() throws Exception {
        screen.clear();

        TerminalSize size = screen.getTerminalSize();
        int centerCol = size.getColumns() / 2;
        int centerRow = size.getRows() / 2 - (options.length / 2);

        TextGraphics tg = screen.newTextGraphics();

        for (int i = 0; i < options.length; i++) {
            int row = centerRow + i;
            int col = centerCol - (options[i].length() / 2);

            if (i == selected) {
                tg.putString(col, row, "> " + options[i]);
            } else {
                tg.putString(col, row, "  " + options[i]);
            }
        }

        screen.refresh();
    }

    private boolean isKey(KeyStroke key, char c) {
        return key.getKeyType() == KeyType.Character && key.getCharacter() == c;
    }
}