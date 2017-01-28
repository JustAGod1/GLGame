package Game;

import Entities.HUD;
import Entities.PlayerAI;
import Entities.Shells.Shells;
import Rendering.WorldRenderer;
import WorldProviding.TankEntity;
import WorldProviding.World;
import com.jogamp.nativewindow.WindowClosingProtocol;
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
        window = GLWindow.create(new GLCapabilities(GLProfile.getDefault()));

        World.generateNewWorld();


        World.getInstance().addEntity(player = new TankEntity().setAI(new PlayerAI()));
        //World.getInstance().addEntity(new TankEntity().setAI(new NPCTankAI()));

        HUD.instance.addShell(Shells.SHELL);
        HUD.instance.addShell(Shells.POWERFUL_SHELL);
        HUD.instance.addShell(Shells.UNUSUAL_SHELL);

        registerKeyProcessors();

        window.addGLEventListener(WorldRenderer.getInstance());
        window.setSize(900, 900);
        window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.setVisible(true);


        while (true) {
        }

    }

    private static void registerKeyProcessors() {
        // Space
        PlayerAI.registerKeyProcessor(new PlayerAI.KeyProcessor() {
            @Override
            public void onPressed() {
                player.shoot();
            }

            @Override
            public int getKeyCode() {
                return 32;
            }
        });

        // Arrow Up
        PlayerAI.registerKeyProcessor(new PlayerAI.KeyProcessor() {
            @Override
            public void onPressed() {
                player.moveForward(0.01f);
            }

            @Override
            public int getKeyCode() {
                return 150;
            }
        });

        // Arrow Down
        PlayerAI.registerKeyProcessor(new PlayerAI.KeyProcessor() {
            @Override
            public void onPressed() {
                player.moveBackwards(0.01f);
            }

            @Override
            public int getKeyCode() {
                return 152;
            }
        });

        // Arrow Left
        PlayerAI.registerKeyProcessor(new PlayerAI.KeyProcessor() {
            @Override
            public void onPressed() {
                player.moveLeft(0.01f);
            }

            @Override
            public int getKeyCode() {
                return 149;
            }
        });

        // Arrow Right
        PlayerAI.registerKeyProcessor(new PlayerAI.KeyProcessor() {
            @Override
            public void onPressed() {
                player.moveRight(0.01f);
            }

            @Override
            public int getKeyCode() {
                return 151;
            }
        });

        // Shift
        PlayerAI.registerKeyProcessor(new PlayerAI.KeyProcessor() {
            @Override
            public void onPressed() {
                player.addEffect(TankEntity.Effect.SHOOT_SPEED_UP, 0.2f, 5);
            }

            @Override
            public int getKeyCode() {
                return 15;
            }
        });

    }
}
