package Game;

import Gui.KeyboardHandler;
import Gui.MainGUI;
import Rendering.WorldRenderer;
import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.swt.NewtCanvasSWT;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;



/**
 * Created by Yuri on 29.11.16.
 */
public class GameGL {

    public static GLWindow window;


    public static void main(String[] args) throws InterruptedException {


        window = GLWindow.create(new GLCapabilities(GLProfile.getDefault()));

        window.addGLEventListener(WorldRenderer.getInstance());
        window.setVisible(true);
        window.addKeyListener(new KeyboardHandler());


        while (true) {
        }

    }
}
