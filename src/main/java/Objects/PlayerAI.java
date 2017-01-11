package Objects;


import Game.GameGL;
import WorldProviding.TankEntity;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Yuri on 08.01.17.
 */
public class PlayerAI extends TankAI implements KeyListener{

    private TankEntity player;
    private Set<Key> typedKeys = new HashSet<>();

    @Override
    public void update() {
        Iterator<Key> iterator = typedKeys.iterator();


        try {
            for (Key key : typedKeys) {
                processKey(key);
            }
        } catch (ConcurrentModificationException ignored) {}


    }

    public PlayerAI() {
        GameGL.window.addKeyListener(this);
    }

    public void setPlayer(TankEntity player) {
        this.player = player;
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
            case 32: {
                typedKeys.add(Key.SPACE_BAR);
                break;
            }
            case 5: {
                typedKeys.add(Key.TAB);
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
            case 32: {
                typedKeys.remove(Key.SPACE_BAR);
                break;
            }
            case 5: {
                typedKeys.remove(Key.TAB);
                break;
            }
        }

    }

    private void processKey(Key key) {
        if (player == null) return;
        switch (key) {
            case KEY_DOWN: {
                player.moveBackwards(0.01f);
                break;
            }
            case KEY_LEFT: {
                player.moveLeft(0.01f);
                break;
            }
            case KEY_RIGHT: {
                player.moveRight(0.01f);
                break;
            }
            case KEY_UP: {
                player.moveForward(0.01f);
                break;
            }
            case SPACE_BAR: {
                player.shoot();
                break;
            }
            case TAB: {

            }
        }

    }

    private enum Key {KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN, TAB, SPACE_BAR}
}
