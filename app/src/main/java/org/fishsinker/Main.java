package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {
    public static void main(String[] args) throws Exception { // If something crashes, shows the error that lanterna throws
        Terminal terminal = new DefaultTerminalFactory()
        .setForceTextTerminal(true) // Forcing lanterna to use the current terminal
        .createTerminal();

        try (Screen screen = new TerminalScreen(terminal)) {
            screen.startScreen();
            Menu menu = new Menu(screen);
            menu.run();
        }
    }
}