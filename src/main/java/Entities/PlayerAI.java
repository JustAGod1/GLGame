package Entities;


import Game.GameGL;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yuri on 08.01.17.
 */
public class PlayerAI extends TankAI implements KeyListener{


    private static final Set<KeyProcessor> registredProcessors = new HashSet<>();
    private Set<KeyProcessor> typedKeys = new HashSet<>();

    public PlayerAI() {
        GameGL.window.addKeyListener(this);
    }

    public static void registerKeyProcessor(KeyProcessor processor) {
        if (registredProcessors.contains(processor)) {
            System.out.println("Попытка дважды зарегистрировать один и тот же обработчик клавиш");
        } else {
            registredProcessors.add(processor);
        }
    }

    @Override
    public void update() {



        try {
            for (KeyProcessor key : typedKeys) {
                key.onPressed();
            }
        } catch (ConcurrentModificationException ignored) {}


    }

    @Override
    public void keyPressed(KeyEvent e) {

        int a = e.getKeyCode();

        for (KeyProcessor processor : registredProcessors) {
            if (processor.getKeyCode() == a) typedKeys.add(processor);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int a = e.getKeyCode();

        typedKeys.removeIf(processor -> processor.getKeyCode() == a);

    }


    public interface KeyProcessor {

        void onPressed();

        int getKeyCode();
    }
}
