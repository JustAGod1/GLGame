package Entities.Shells;

import Entities.Shells.TurretShell.TurretShell;

/**
 * Created by Yuri on 27.01.17.
 */
public class Shells {
    public static final PowerfulShell POWERFUL_SHELL = new PowerfulShell();
    public static final Shell SHELL = new Shell();
    public static final UnusualShell UNUSUAL_SHELL = new UnusualShell();
    public static final TurretShell TURRET_SHELL = (TurretShell) new TurretShell().setTexture("fireball.png");
    public static final TurretShell RAPID_TURRET_SHELL = (TurretShell) new TurretShell().setShootDelay(10).setPower(1);
}
