package Entities;

import Entities.Shells.Shell;
import Entities.Shells.ShellWrapper;
import Game.GameGL;
import Rendering.Teselator;
import Vectors.Vector2;
import WorldProviding.TankEntity.Effect;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static Rendering.WorldRenderer.GL20;
import static com.jogamp.opengl.GL.GL_LINE_STRIP;

/**
 * Created by Yuri on 11.01.17.
 */
public class HUD implements KeyListener, MouseListener {

    public static final HUD instance = new HUD();
    private ArrayList<Shell> shells = new ArrayList<>();
    private int index = 0;

    private HUD() {
        GameGL.window.addKeyListener(this);
        GameGL.window.addMouseListener(this);

    }

    public void addShell(Shell shell) {
        shells.add(shell);
        setIndex(0);
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


        HashSet<Effect> effects = GameGL.player.getEffectsList();
        Iterator<Effect> iterator = effects.iterator();

        {
            int i = effects.size();
            while (iterator.hasNext()) {
                Effect effect = iterator.next();

                GL20.glPushMatrix();
                {
                    GL20.glTranslated(1 - ((effects.size() - i) * 0.1f), -1, -0.5);
                    drawEffect(effect);
                }
                GL20.glPopMatrix();
                i--;
            }
        }

        for (int i = 0; i < shells.size(); i++) {
            GL20.glPushMatrix();
            {
                GL20.glTranslated(i * 0.1 - 1, -1, -0.5);
                drawShell(shells.get(i), index == i, 1);
            }
            GL20.glPopMatrix();
        }

    }

    private void drawShell(Shell shell, boolean isChosen, int count) {
        GL20.glPushMatrix();
        {
            GL20.glScaled(0.7, 0.7, 1);
            GL20.glTranslated(0.02, 0.02, 0);

            ShellWrapper wrapper = shell.createWrapper(new Vector2(0, 0), new Vector2(0, 0), 0, null);
            GL20.glPushMatrix();
            {
                GL20.glTranslated(0.05, 0.05, 0);
                wrapper.onDraw();
            }
            GL20.glPopMatrix();


            Teselator te = Teselator.instance;

            if (isChosen)
                GL20.glColor3d(0, 1, 0);
            else
                GL20.glColor3d(1.0, 0.8, 0);

            GL20.glTranslated(0, 0, -0.01);
            GL20.glLineWidth(10);

            te.disableTexture();
            te.startDrawing(GL_LINE_STRIP);
            {
                te.add2DVertex(0, 0);
                te.add2DVertex(0.1f, 0);
                te.add2DVertex(0.1f, 0.1f);
                te.add2DVertex(0, 0.1f);
                te.add2DVertex(0, 0);
            }
            te.draw();
            GL20.glPushMatrix();
            {
                GL20.glScaled(0.01, 0.01, 1);
                te.drawText(("x" + count), new Vector2(0.0f, 0.0f));
            }
            GL20.glPopMatrix();

        }
        GL20.glPopMatrix();

    }

    private void drawEffect(Effect effect) {
        GL20.glPushMatrix();
        {
            Teselator te = Teselator.instance;
            switch (effect) {
                case SHOOT_SPEED_UP: {
                    te.bindTexture("shoot_speed_up.png");
                    break;
                }
                default: {
                    te.disableTexture();
                }
            }


            te.startDrawingQuads();
            {
                te.add2DVertexWithUV(0, 0, 1, 1);
                te.add2DVertexWithUV(-0.1f, 0, 0, 1);
                te.add2DVertexWithUV(-0.1f, 0.1f, 0, 0);
                te.add2DVertexWithUV(0, 0.1f, 1, 0);
            }
            te.draw();
        }
        GL20.glPopMatrix();
    }

    private void setIndex(int index) {
        if (index == 0)
            this.index = index;
        else
            this.index = index % shells.size();

        GameGL.player.setShell(shells.get(this.index));

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int a = e.getKeyCode();

        if (a == 9) setIndex(index + 1);

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {

    }
}
