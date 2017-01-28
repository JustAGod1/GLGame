package Entities.Shells;

import Entities.Entity;
import Rendering.Teselator;
import Vectors.Vector2;
import WorldProviding.World;

import java.util.function.Predicate;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 11.01.17.
 */
public class ShellWrapper extends Entity {


    protected Vector2 position;
    protected float rotation;
    protected Shell shell;
    protected Vector2 direction;
    protected Entity entity;
    private Predicate<Entity> tester = new Predicate<Entity>() {
        @Override
        public boolean test(Entity entity) {
            return (entity.isVectorInBounds(position) && (!(entity instanceof ShellWrapper) || testShell((ShellWrapper) entity)));
        }

        private boolean testShell(ShellWrapper shell) {
            return shell.getEntity() != getEntity();
        }
    };

    public ShellWrapper(Vector2 position, float rotation, Shell shell, Vector2 direction, Entity tankEntity) {
        this.position = position;
        this.rotation = rotation;
        this.shell = shell;
        this.direction = new Vector2(direction.x / 50, direction.y / 50);
        this.entity = tankEntity;
    }

    @Override
    public void updateEntity() {
        position.add(direction);

        if (position.x > 1 || position.y > 1 || position.x < -1 || position.y < -1) shell.onOutOfBorders(this);
        if (World.getInstance().hasBlockAtPos(position))
            shell.onBlockCollision(World.getInstance().getBlockByPos(position), this);
        Entity entity = World.getInstance().findEntity(tester);

        if (entity != null) onEntityCollision(entity);
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    @Override
    public void onDraw() {

        Teselator te = Teselator.instance;
        te.bindTexture(shell.getTexture());

        GL20.glPushMatrix();
        {
            //GL20.glRotated(rotation, 0, 0, 1);
            GL20.glTranslated(position.x, position.y, -0.1f);
            GL20.glRotated(rotation + 90, 0, 0, 1);
            GL20.glColor3f(1, 1, 1);
            te.startDrawingQuads();
            {


                te.add2DVertexWithUV(-0.0125f, -0.0125f, 0, 1);
                te.add2DVertexWithUV(0.0125f, -0.0125f, 1, 1);
                te.add2DVertexWithUV(0.0125f, 0.0125f, 1, 0);
                te.add2DVertexWithUV(-0.0125f, 0.0125f, 0, 0);
            }
            te.draw();
        }
        GL20.glPopMatrix();
    }

    @Override
    public void onEntityCollision(Entity entity) {
        if (entity != getEntity() && !entity.isInvincible()) {
            entity.onEntityCollision(this);

            World.getInstance().removeEntity(this);
        }
    }

    @Override
    public boolean isVectorInBounds(Vector2 pos) {
        float mx = this.position.x * 10 - 0.0125f;
        float my = this.position.y * 10 - 0.0125f;

        float x = pos.x;
        float y = pos.y;

        return (x <= (mx * 0.1 + 0.1)) && (x >= (mx * 0.1)) && (y <= (my * 0.1 + 0.1)) && (y >= (my * 0.1));
    }

    public int getPower() {
        return shell.getPower();
    }

    public Entity getEntity() {
        return entity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Shell getShell() {
        return shell;
    }

    public Predicate<Entity> getTester() {
        return tester;
    }

    public ShellWrapper setTester(Predicate<Entity> tester) {
        this.tester = tester;
        return this;
    }
}
