package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MacroOne extends MacroBase implements NativeKeyListener {

    private volatile boolean macroRunning = false;
    private volatile boolean shouldQuit = false;

    public MacroOne(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Check for Ctrl + K
        boolean ctrlHeld = (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0;
        boolean kPressed = e.getKeyCode() == NativeKeyEvent.VC_K;

        if (ctrlHeld && kPressed) {
            macroRunning = !macroRunning; // Toggling on and off
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    @Override
    public void run() throws Exception {
        // Silence JNativeHook's own logging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Register global hotkey listener
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);

        Robot robot = new Robot();

        drawStatus("MacroOne - Ctrl + K to start");

        while (!shouldQuit) {
            if (macroRunning) {
                drawStatus("MacroOne Running - Ctrl + K to stop");

                // Take screenshot of the full screen
                Rectangle fullScreen = new Rectangle(
                    java.awt.Toolkit.getDefaultToolkit().getScreenSize()
                );
                BufferedImage screenshot = robot.createScreenCapture(fullScreen);

                //
                // SCREENSHOT ANALSYS AND CONSEQUENT INPUTS WILL GO HERE LATER
                //

                Thread.sleep(50); // 20 times per second
            } else {
                drawStatus("MacroOne - Ctrl + K to start");
                Thread.sleep(100);
            }

            // Check if user pressed escape in the TUI to go back
            if (shouldExit()) shouldQuit = true;
        }

        // Clean up
        GlobalScreen.removeNativeKeyListener(this);
        GlobalScreen.unregisterNativeHook();
    }
}