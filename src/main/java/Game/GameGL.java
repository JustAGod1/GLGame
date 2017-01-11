package Game;

import Objects.HUD;
import Objects.PlayerAI;
import Objects.Shell;
import Rendering.WorldRenderer;
import WorldProviding.TankEntity;
import WorldProviding.World;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;


/**
 * Created by Yuri on 29.11.16.
 */
public class GameGL {

    public static GLWindow window;
    public static TankEntity player;


    public static void main(String[] args) throws InterruptedException {


        World.generateNewWorld();
        window = GLWindow.create(new GLCapabilities(GLProfile.getDefault()));

        window.addGLEventListener(WorldRenderer.getInstance());
        window.setSize(900, 900);
        window.setVisible(true);

        HUD.instance.addShell(Shell.class);



        World.getInstance().addEntity(player = new TankEntity().setAI(new PlayerAI()));


        while (true) {
        }

    }
}
