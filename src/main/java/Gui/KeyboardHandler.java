package Gui;

import WorldProviding.PlayerEntity;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import java.util.*;


/**
 * Created by Yuri on 05.01.17.
 */
public class KeyboardHandler implements KeyListener {

    private Set<Key> typedKeys = new HashSet<>();
    private Thread thread;
    private boolean mutex = false;

    public KeyboardHandler() {
        thread = new Thread(this::loop);
        thread.start();
        //thread.setDaemon(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int a = e.getKeyCode();
        switch (a) {
            case 149: {
                typedKeys.add(Key.KEY_LEFT);
                break;
            }
            case 151: {
                typedKeys.add(Key.KEY_RIGHT);
                break;
            }
            case 150: {
                typedKeys.add(Key.KEY_UP);
                break;
            }
            case 152: {
                typedKeys.add(Key.KEY_DOWN);
                break;

            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int a = e.getKeyCode();
        switch (a) {
            case 149: {
                typedKeys.remove(Key.KEY_LEFT);
                break;
            }
            case 151: {
                typedKeys.remove(Key.KEY_RIGHT);
                break;
            }
            case 150: {
                typedKeys.remove(Key.KEY_UP);
                break;
            }
            case 152: {
                typedKeys.remove(Key.KEY_DOWN);

                break;
            }
        }

    }

    private void loop() {

        while (true) {
            Iterator<Key> iterator = typedKeys.iterator();

            try {
                for (Key key : typedKeys) {
                    processKey(key);
                }
                Thread.sleep(10);
            } catch (ConcurrentModificationException e) {

            } catch (InterruptedException e) {

            }

        }
    }

    private void processKey(Key key) {
        switch (key) {
            case KEY_DOWN: {
                PlayerEntity.getInstance().moveBackwards(0.0009f);
                break;
            }
            case KEY_LEFT: {
                PlayerEntity.getInstance().rotateToLeft(0.5f);
                break;
            }
            case KEY_RIGHT: {
                PlayerEntity.getInstance().rotateToRight(0.5f);
                break;
            }
            case KEY_UP: {
                PlayerEntity.getInstance().moveForward(0.0009f);
                break;
            }
        }

    }

    private enum Key {KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN}
}
