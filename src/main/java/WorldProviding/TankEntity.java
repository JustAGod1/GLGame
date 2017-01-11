package WorldProviding;

import Objects.Entity;
import Objects.PlayerAI;
import Objects.Shell;
import Rendering.Teselator;
import Vectors.Vector2;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 05.01.17.
 */
public class TankEntity extends Entity {


    private static final int SHOOT_DELAY = (int) (5);
    private int timeToNextShoot = 0;
    private Vector2 position = new Vector2(0.05f, 0.05f);
    private Direction direction = Direction.UP;
    private PlayerAI ai;


    public boolean moveForward(float distance) {
        if (direction != Direction.UP) {
            direction = Direction.UP;
            return true;
        }

        if (!canMoveToUp()) return false;

        for (float i = 0; i < distance; i += 0.0001) {
            position.y += distance / (distance / 0.0001);
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

    @Override
    public void updateEntity() {
        if (ai != null) {
            ai.update();
        }
        if (timeToNextShoot > 0) timeToNextShoot--;
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

        GL20.glColor3f(0, 1, 0);

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

            GL20.glColor3f(1, 0, 0);

            te.translate2D(-position.x, -position.y);

        }
        GL20.glPopMatrix();
    }

    public TankEntity setAI(PlayerAI ai) {
        this.ai = ai;
        ai.setPlayer(this);
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
            Shell shell = new Shell(position.clone(), new Vector2(x, y), rotation);

            World.getInstance().addEntity(shell);

            timeToNextShoot = SHOOT_DELAY;
        }
    }

    private boolean isShootAvailable() {
        return timeToNextShoot <= 0;
    }

    private enum Direction {UP, DOWN, LEFT, RIGHT}
}
