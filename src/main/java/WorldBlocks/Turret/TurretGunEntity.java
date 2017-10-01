package WorldBlocks.Turret;

import Entities.Entity;
import Entities.Shells.Shell;
import Entities.Shells.TurretShell.TurretShell;
import Game.GameGL;
import Rendering.Teselator;
import Vectors.Vector2;
import WorldProviding.World;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 29.01.17.
 */
class TurretGunEntity extends Entity {

    private Vector2 pos;
    private Shell shell;
    private float direction = 0;
    private int delay = 0;

    public TurretGunEntity(Vector2 pos, TurretShell shell) {
        this.pos = pos;
        this.shell = shell;
    }

    @Override
    public void updateEntity() {
        Vector2 vector = pos.clone();
        try {
            vector.decrease(GameGL.player.getPos());
        } catch (NullPointerException e) {

        }

        direction = vector.direction();

        if (delay <= 0) shoot();

        delay--;
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    @Override
    public void onDraw() {
        GL20.glPushMatrix();
        {
            Teselator te = Teselator.instance;
            te.bindTexture("turret_gun.png");

            GL20.glTranslated(pos.x, pos.y, -0.3);

            GL20.glPushMatrix();
            {
                GL20.glRotated(direction + 90, 0, 0, 1);
                GL20.glColor3f(1, 1, 1);

                te.startDrawingQuads();
                {
                    te.add2DVertexWithUV(-0.05f, -0.05f, 0, 1);
                    te.add2DVertexWithUV(-0.05f, 0.05f, 0, 0);
                    te.add2DVertexWithUV(0.05f, 0.05f, 1, 0);
                    te.add2DVertexWithUV(0.05f, -0.05f, 1, 1);
                }
                te.draw();
            }
            GL20.glPopMatrix();
        }
        GL20.glPopMatrix();
    }

    @Override
    public void onEntityCollision(Entity entity) {

    }

    @Override
    public boolean isVectorInBounds(Vector2 vector) {
        return false;
    }

    private void shoot() {
        World.getInstance().addEntity(shell.createWrapper(pos.clone(), new Vector2(direction + 180).normalize(), direction, this));
        delay = shell.getShootDelay();
    }


}
