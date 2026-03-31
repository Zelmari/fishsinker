package org.fishsinker;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Renderer {

    // Single line box characters
    private static final char H  = '─'; // horizontal
    private static final char V  = '│'; // vertical
    private static final char TL = '┌'; // top left
    private static final char TR = '┐'; // top right
    private static final char BL = '└'; // bottom left
    private static final char BR = '┘'; // bottom right

    // Gap between outer border and screen edge
    private static final int PADDING = 2;

    // Draws outer border around the whole screen
    public static void drawOuterBorder(Screen screen, TextGraphics tg, String title) {
        TerminalSize size = screen.getTerminalSize();
        int cols = size.getColumns();
        int rows = size.getRows();

        int left = PADDING;
        int right = cols - PADDING - 1;
        int top = PADDING;
        int bottom = rows - PADDING - 1;

        // Draws the corners
        tg.setCharacter(left, top,    TL);
        tg.setCharacter(right, top,    TR);
        tg.setCharacter(left, bottom, BL);
        tg.setCharacter(right, bottom, BR);

        // Draws top and bottom edges
        for (int col = left + 1; col < right; col++) {
            tg.setCharacter(col, top,    H);
            tg.setCharacter(col, bottom, H);
        }

        // Draws left and right edges
        for (int row = top + 1; row < bottom; row++) {
            tg.setCharacter(left, row, V);
            tg.setCharacter(right, row, V);
        }

        // Draw title centred on the top border
        if (title != null && !title.isEmpty()) {
            String t = " " + title + " ";
            int titleCol = (cols / 2) - (t.length() / 2);
            tg.putString(titleCol, top, t);
        }
    }

    // Draws a box around the menu list and the items inside it
    public static void drawMenuBox(Screen screen, TextGraphics tg, String[] options, int selected) {
        TerminalSize size = screen.getTerminalSize();
        int cols = size.getColumns();
        int rows = size.getRows();

        // Find the longest option so that the box fits around it
        int maxLen = 0;
        for (String option : options) {
            if (option.length() > maxLen) maxLen = option.length();
        }

        // Box dimensions - 2 padding on each side
        // 2 gap between items
        int boxWidth = maxLen + 6;
        int boxHeight = (options.length * 2) + 1; // Space between each item

        // Center the box
        int left = (cols / 2) - (boxWidth / 2);
        int right = left + boxWidth;
        int top = (rows / 2) - (boxHeight / 2);
        int bottom = top + boxHeight;

        // Draws box corners
        tg.setCharacter(left,  top,    TL);
        tg.setCharacter(right, top,    TR);
        tg.setCharacter(left,  bottom, BL);
        tg.setCharacter(right, bottom, BR);

        // Draws box top and bottom edges
        for (int col = left + 1; col < right; col++) {
            tg.setCharacter(col, top,    H);
            tg.setCharacter(col, bottom, H);
        }

        // Draws box left and right edges
        for (int row = top + 1; row < bottom; row++) {
            tg.setCharacter(left,  row, V);
            tg.setCharacter(right, row, V);
        }

        // Draws each option inside the box with spacing
        for (int i = 0; i < options.length; i++) {
            int row = top + 1 + (i * 2); // * 2 gives the spacing between items
            int col = left + 3;

            if (i == selected) {
                tg.putString(col - 2, row, "> " + options[i]);
            } else {
                tg.putString(col - 2, row, "  " + options[i]);
            }
        }
    }
}