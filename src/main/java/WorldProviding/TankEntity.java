package WorldProviding;

import Entities.Entity;
import Entities.Shells.Shell;
import Entities.Shells.ShellWrapper;
import Entities.TankAI;
import Rendering.Teselator;
import Vectors.Vector2;

import java.util.HashSet;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 05.01.17.
 */
public class TankEntity extends Entity {


    private int timeToNextShoot = 0;
    private Vector2 position = new Vector2(0.05f, 0.05f);
    private Direction direction = Direction.UP;
    private TankAI ai;
    private Shell shell = new Shell();

    private float shootDelayModifier = 1;
    private int shootDelayModifierTime = 0;
    private int health = 20;


    public boolean moveForward(float distance) {
        if (direction != Direction.UP) {
            direction = Direction.UP;
            return true;
        }

        if (!canMoveToUp()) return false;

        for (float i = 0; i < distance; i += 0.0001) {
            position.y += distance / (distance / 0.0001);
            processMove();
        }
        return true;
    }

    public boolean moveBackwards(float distance) {

        if (direction != Direction.DOWN) {
            direction = Direction.DOWN;
            return true;
        }

        if (!canMoveToDown()) return false;

        for (float i = 0; i < distance; i += 0.0001) {
            position.y -= distance / (distance / 0.0001);
            processMove();
        }
        return true;
    }

    public boolean moveLeft(float distance) {
        if (direction != Direction.LEFT) {
            direction = Direction.LEFT;
            return true;
        }

        if (!canMoveToLeft()) return false;

        for (float i = 0; i < distance; i += 0.0001) {
            position.x -= distance / (distance / 0.0001);
            processMove();
        }
        return true;
    }

    public boolean moveRight(float distance) {
        if (direction != Direction.RIGHT) {
            direction = Direction.RIGHT;
            return true;
        }

        if (!canMoveToRight()) return false;

        for (float i = 0; i < distance; i += 0.0001) {
            position.x += distance / (distance / 0.0001);
            processMove();
        }
        return true;
    }

    private boolean canMoveToLeft() {
        float x = (float) (position.x - 0.00501);
        float y = position.y;

        Vector2 pos1, pos2;

        // Верхний левый угол
        pos1 = new Vector2(x - 0.04f, y + 0.04f);

        // Нижний левый угол
        pos2 = new Vector2(x - 0.04f, y - 0.03f);

        World w = World.getInstance();

        return !(w.hasBlockAtPos(pos1) || w.hasBlockAtPos(pos2)) && !(pos1.x - 0.01 > 1 || pos1.x + 0.01 < -1 || pos1.y - 0.01 > 1 || pos1.y + 0.01< -1 || pos2.x - 0.01 > 1 || pos2.x + 0.01 < -1 || pos2.y - 0.01> 1 || pos2.y + 0.01 < -1);
    }

    private boolean canMoveToRight() {
        float x = (float) (position.x + 0.00501);
        float y = position.y;

        Vector2 pos1, pos2;

        // Верхний правый угол
        pos1 = new Vector2(x + 0.04f, y + 0.04f);

        // Нижний правый угол
        pos2 = new Vector2(x + 0.04f, y - 0.04f);

        World w = World.getInstance();

        return !(w.hasBlockAtPos(pos1) || w.hasBlockAtPos(pos2)) && !(pos1.x - 0.01 > 1 || pos1.x + 0.01 < -1 || pos1.y - 0.01 > 1 || pos1.y + 0.01< -1 || pos2.x - 0.01 > 1 || pos2.x + 0.01 < -1 || pos2.y - 0.01> 1 || pos2.y + 0.01 < -1);
    }

    private boolean canMoveToUp() {
        float x = position.x;
        float y = (float) (position.y + 0.005);

        Vector2 pos1, pos2;

        // Верхний правый угол
        pos1 = new Vector2(x + 0.04f, y + 0.04f);

        // Верхний левый угол
        pos2 = new Vector2(x - 0.04f, y + 0.04f);

        World w = World.getInstance();

        return !(w.hasBlockAtPos(pos1) || w.hasBlockAtPos(pos2)) && !(pos1.x - 0.01 > 1 || pos1.x + 0.01 < -1 || pos1.y - 0.01 > 1 || pos1.y + 0.01< -1 || pos2.x - 0.01 > 1 || pos2.x + 0.01 < -1 || pos2.y - 0.01> 1 || pos2.y + 0.01 < -1);
    }

    private boolean canMoveToDown() {
        float x = position.x;
        float y = position.y - 0.005f;

        Vector2 pos1, pos2;

        // Нижний правый угол
        pos1 = new Vector2(x + 0.04f, y - 0.04f);

        // Нижний левый угол
        pos2 = new Vector2(x - 0.04f, y - 0.04f);

        World w = World.getInstance();

        return !(w.hasBlockAtPos(pos1) || w.hasBlockAtPos(pos2)) && !(pos1.x - 0.01 > 1 || pos1.x + 0.01 < -1 || pos1.y - 0.01 > 1 || pos1.y + 0.01< -1 || pos2.x - 0.01 > 1 || pos2.x + 0.01 < -1 || pos2.y - 0.01> 1 || pos2.y + 0.01 < -1);
    }

    public void setShell(Shell shell) {

        this.shell = shell;
    }

    public HashSet<Effect> getEffectsList() {
        HashSet<Effect> res = new HashSet<>();

        if (shootDelayModifierTime > 0)
            res.add(Effect.SHOOT_SPEED_UP);

        return res;
    }

    public void addEffect(Effect effect, float value, int time) {
        switch (effect) {
            case SHOOT_SPEED_UP: {
                setShootDelayModifier(value, time);
                break;
            }
        }
    }

    public void processMove() {
        float x = position.x;
        float y = position.y - 0.005f;

        Vector2 pos1, pos2, pos3, pos4;

        // Нижний правый угол
        pos1 = new Vector2(x + 0.04f, y - 0.04f);

        // Нижний левый угол
        pos2 = new Vector2(x - 0.04f, y - 0.04f);

        // Верхний правый угол
        pos3 = new Vector2(x + 0.04f, y + 0.04f);

        // Верхний левый угол
        pos4 = new Vector2(x - 0.04f, y + 0.04f);

        World w = World.getInstance();
        Entity entity1, entity2, entity3, entity4;

        entity1 = w.findEntity(pos1, this);
        if ((entity1 != null) && !(entity1 instanceof TankEntity))
            entity1.onEntityCollision(this);

        entity2 = w.findEntity(pos2, this);
        if ((entity2 != null) && !(entity2 instanceof TankEntity) && !(entity1 == entity2))
            entity2.onEntityCollision(this);

        entity3 = w.findEntity(pos3, this);
        if ((entity3 != null) && !(entity3 instanceof TankEntity) && !(entity3 == entity2) && !(entity3 == entity1))
            entity3.onEntityCollision(this);

        entity4 = w.findEntity(pos4, this);
        if ((entity4 != null) && !(entity4 instanceof TankEntity) && !(entity4 == entity1) && !(entity4 == entity2) && !(entity4 == entity3))
            entity4.onEntityCollision(this);
    }

    @Override
    public void updateEntity() {
        if (ai != null) {
            ai.update();
        }
        if (timeToNextShoot > 0) timeToNextShoot--;

        if (shootDelayModifierTime > 0) shootDelayModifierTime--;
        else shootDelayModifier = 1;
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    @Override
    public void onDraw() {

        Teselator te = Teselator.instance;

        int rotation = 0;

        switch (direction) {
            case DOWN: {
                rotation = 180;
                break;
            }
            case UP: {
                rotation = 0;
                break;
            }
            case LEFT: {
                rotation = 90;
                break;
            }
            case RIGHT: {
                rotation = 270;
                break;
            }
        }


        GL20.glLineWidth(5);
        GL20.glPushMatrix();
        {
            te.translate2D(position.x, position.y);


            GL20.glRotated(rotation - 90, 0, 0, 4);
            GL20.glColor3f(1, 1, 1);
            te.bindTexture("tank.png");
            te.startDrawingQuads();
            {
                te.add3DVertexWithUV(-0.05f, -0.05f, -0.1f, 0, 1);
                te.add3DVertexWithUV(0.05f, -0.05f, -0.1f, 1, 1);
                te.add3DVertexWithUV(0.05f, 0.05f, -0.1f, 1, 0);
                te.add3DVertexWithUV(-0.05f, 0.05f, -0.1f, 0, 0);
            }
            te.draw();


            te.translate2D(-position.x, -position.y);

        }
        GL20.glPopMatrix();
    }

    @Override
    public void onEntityCollision(Entity entity) {
        if (entity instanceof ShellWrapper) {

            health -= ((ShellWrapper) entity).getPower();
            if (health <= 0) World.getInstance().removeEntity(this);
        }

    }

    @Override
    public boolean isVectorInBounds(Vector2 pos) {
        float mx = position.x - 0.05f;
        float my = position.y - 0.05f;

        float x = pos.x;
        float y = pos.y;

        return (x <= (mx + 0.1)) && (x >= (mx)) && (y <= (my + 0.1)) && (y >= (my));
    }

    public TankEntity setAI(TankAI ai) {
        this.ai = ai;
        ai.setTankEntity(this);
        return this;
    }

    public void shoot() {
        if (isShootAvailable()) {
            int x = 0, y = 0;

            switch (direction) {
                case UP: {
                    x = 0;
                    y = 1;
                    break;
                }
                case DOWN: {
                    x = 0;
                    y = -1;
                    break;
                }
                case RIGHT: {
                    x = 1;
                    y = 0;
                    break;
                }
                case LEFT: {
                    x = -1;
                    y = 0;
                    break;
                }
            }

            int rotation = 0;

            switch (direction) {
                case DOWN: {
                    rotation = 180;
                    break;
                }
                case UP: {
                    rotation = 0;
                    break;
                }
                case LEFT: {
                    rotation = 90;
                    break;
                }
                case RIGHT: {
                    rotation = 270;
                    break;
                }
            }

            ShellWrapper wrapper = shell.createWrapper(position.clone(), new Vector2(x, y), rotation, this);
            World.getInstance().addEntity(wrapper);


            int delay = (int) (shell.getShootDelay() * shootDelayModifier);
            if (delay < 1) delay = 1;
            timeToNextShoot = delay;
        }
    }

    private boolean isShootAvailable() {
        return timeToNextShoot <= 0;
    }

    public void setShootDelayModifier(float shootDelayModifier, int forTime) {
        this.shootDelayModifier = shootDelayModifier;
        this.shootDelayModifierTime = forTime;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Vector2 getPos() {
        return position;
    }

    private enum Direction {UP, DOWN, LEFT, RIGHT}

    public enum Effect {SHOOT_SPEED_UP, SPEED_UP}
}
