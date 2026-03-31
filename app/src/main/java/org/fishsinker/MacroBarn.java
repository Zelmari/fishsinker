package org.fishsinker;

import com.googlecode.lanterna.screen.Screen;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MacroBarn extends MacroBase implements NativeKeyListener {

    private volatile boolean macroRunning = false;
    private volatile boolean shouldQuit = false;
    private final Random random =  new Random();

    // Hardcoded bobber colors when floating, NOT UNDER WATER
    private static final int BOBBER_R = 200;
    private static final int BOBBER_G = 40;
    private static final int BOBBER_B = 40;

    // Tolerance for how far off the real colors can be from the hardcoded RBG values
    private static final int COLOR_TOLERANCE = 30;

    // How many pixels must match to confirm that bobber is present
    private static final int BOBBER_PIXEL_THRESHOLD = 10;
    
    // Settle delay as the rod technically sinks as soon as it enters the water
    private static final long SETTLE_DELAY_MS = 1500;

    public MacroBarn(Screen screen) throws Exception {
        super(screen);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        boolean ctrlHeld = (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0;
        boolean kPressed = e.getKeyCode() == NativeKeyEvent.VC_K;
        if (ctrlHeld && kPressed) {
            macroRunning = !macroRunning;
        }
    }

    @Override public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override public void nativeKeyTyped(NativeKeyEvent e) {}

    @Override
    public void run() throws Exception {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);

        Robot robot = new Robot();
        boolean bobberWasVisible = false;

        drawStatus("Barn - ,cast rod, wait 2s, move cursor to bobber - CTRL + K to start");

        while (!shouldQuit) {

            if (macroRunning) {
                drawStatus("Barn running - CTRL + K to stop, then ESC to exit ");

                // Get cursor position -
                // Then build a 400x300 scan region centred on cursor
                Point cursor = MouseInfo.getPointerInfo().getLocation();
                int scanX = cursor.x - 200;
                int scanY = cursor.y - 150;
                Rectangle scanRegion = new Rectangle(scanX, scanY, 400, 300);

                // Take screenshot of that region
                BufferedImage screenshot = robot.createScreenCapture(scanRegion);

                // Count how many pixels match the red color of the bobber
                int redPixelCount = countMatchingPixels(screenshot, BOBBER_R, BOBBER_G, BOBBER_B, COLOR_TOLERANCE);
                boolean bobberVisible = redPixelCount >= BOBBER_PIXEL_THRESHOLD;

                // Bobber just disappeared = fish caught, reel in and recast
                if (bobberWasVisible && !bobberVisible) {

                    // wait random 0-100ms offset then right click to reel in
                    Thread.sleep(random.nextInt(100));
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

                    // wait random 200-400ms offset then right click again to recast
                    Thread.sleep(200 + random.nextInt(200));
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

                    // Adding the settle delay to try and prevent false positives
                    bobberWasVisible = false;
                    Thread.sleep(SETTLE_DELAY_MS);
                }

                bobberWasVisible = bobberVisible;
                Thread.sleep(50); // Scans 20 times per second
            } else {
                drawStatus("Barn - cast rod, wait 2s, move cursor to bobber - CTRL + K to start");
                Thread.sleep(100);
            }

            if (shouldExit()) shouldQuit = true;
        }

        GlobalScreen.removeNativeKeyListener(this);
        GlobalScreen.unregisterNativeHook();
    }

    // Loop through every pixel in the screenshot -
    // Count how many match the target color within the tolerance
    private int countMatchingPixels(BufferedImage img, int targetR, int targetG, int targetB, int tolerance) {
        int count = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixel = new Color(img.getRGB(x, y));
                if (Math.abs(pixel.getRed() - targetR) <= tolerance &&
                    Math.abs(pixel.getGreen() - targetG) <= tolerance &&
                    Math.abs(pixel.getBlue() - targetB) <= tolerance) {
                    count++;
                }
            }
        }

        return count;
    }
}