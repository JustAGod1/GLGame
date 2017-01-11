package Objects;

import Rendering.Teselator;
import Vectors.Vector2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static Rendering.WorldRenderer.GL20;
import static com.jogamp.opengl.GL.GL_LINE_STRIP;

/**
 * Created by Yuri on 11.01.17.
 */
public class HUD {

    public static final HUD instance = new HUD();
    private ArrayList<Class<? extends Shell>> shells = new ArrayList<>();

    private HUD() {
    }

    public void addShell(Class<? extends Shell> shell) {
        shells.add(shell);
    }

    public void onDraw() {
        GL20.glPushMatrix();
        {
            Teselator te = Teselator.instance;

            te.disableTexture();
            GL20.glColor3f(0, 0, 0);
            GL20.glTranslated(-1, -1, -0.5);
            GL20.glLineWidth(15);
            te.startDrawing(GL_LINE_STRIP);
            {
                te.add2DVertex(0, 0);
                te.add2DVertex(2, 0);
                te.add2DVertex(2, 0.1f);
                te.add2DVertex(0, 0.1f);
                te.add2DVertex(0, 0);
            }
            te.draw();

            GL20.glColor3f(1, 1, 1);
            GL20.glTranslated(0, 0, 0.1);
            te.startDrawingQuads();
            {
                te.add2DVertex(0, 0);
                te.add2DVertex(2, 0);
                te.add2DVertex(2, 0.1f);
                te.add2DVertex(0, 0.1f);
            }
            te.draw();

        }
        GL20.glPopMatrix();
        for (int i = 0; i < shells.size(); i++) {
            GL20.glPushMatrix();
            {
                GL20.glTranslated(i * 0.1, 0, 0);
                drawShell(shells.get(i));
            }
            GL20.glPopMatrix();
        }

    }

    private void drawShell(Class<? extends Shell> shell) {
        GL20.glPushMatrix();
        {
            GL20.glScaled(0.95, 0.95, 1);
            try {
                Constructor<? extends Shell> constructor = shell.getConstructor(Vector2.class, Vector2.class, float.class);
                Shell constructedShell = constructor.newInstance(new Vector2(0, 0), new Vector2(0, 0), 0);
                constructedShell.onDraw();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

            Teselator te = Teselator.instance;

            GL20.glColor3f(0, 1, 0);
            te.startDrawing(GL_LINE_STRIP);
            {
                te.add2DVertex(0, 0);
                te.add2DVertex(0.1f, 0);
                te.add2DVertex(0.1f, 0.1f);
                te.add2DVertex(0, 0.1f);
                te.add2DVertex(0, 0);
            }
            te.draw();

        }
        GL20.glPopMatrix();

    }
}
