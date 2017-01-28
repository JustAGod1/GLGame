package Entities.Shells;


/**
 * Created by Yuri on 11.01.17.
 */
public class UnusualShell extends Shell {
    public UnusualShell() {
        setTexture("Shells/unusual_shell.png");
    }

    @Override
    public int getShootDelay() {
        return 4;
    }
}
