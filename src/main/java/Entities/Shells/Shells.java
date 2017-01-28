package Entities.Shells;

import Entities.Entity;
import Vectors.Vector2;

import java.util.function.Predicate;

/**
 * Created by Yuri on 27.01.17.
 */
public class Shells {
    public static final PowerfulShell POWERFUL_SHELL = new PowerfulShell();
    public static final Shell SHELL = new Shell();
    public static final UnusualShell UNUSUAL_SHELL = new UnusualShell();
    public static final Shell TURRET_SHELL = new Shell() {
        @Override
        public int getPower() {
            return 2;
        }

        @Override
        public int getShootDelay() {
            return 30;
        }

        @Override
        public ShellWrapper createWrapper(Vector2 position, Vector2 direction, float rotation, Entity entity) {
            return super.createWrapper(position, direction, rotation, entity).setTester(new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    return !(entity instanceof ShellWrapper) || ((ShellWrapper) (entity)).getShell() != Shells.TURRET_SHELL;
                }
            });
        }
    }.setTexture("tnt.png");
}
