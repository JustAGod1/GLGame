package Vectors;

import WorldProviding.World;

/**
 * Created by Yuri on 01.01.17.
 */
public class BlockPos {

    private Vector2 position;

    public BlockPos(int x, int y) {
        position = new Vector2(x, y);
    }

    public int getX() {
        return (int) position.x;
    }

    public int getY() {
        return (int) position.y;
    }

    public BlockPos upperBlock() {
        return new BlockPos((int) position.x, (int) position.y + 1);
    }

    public BlockPos downBlock() {
        return new BlockPos((int) position.x, (int) (position.y - 1));
    }

    public BlockPos rightBlock() {
        return new BlockPos((int) position.x + 1, (int) (position.y));
    }

    public BlockPos leftBlock() {
        return new BlockPos((int) position.x - 1, (int) (position.y));
    }

    public BlockPos convertToChunkPos() {
        return new BlockPos(getX() % (World.CHUNK_SIZE + 1), getY() % (World.CHUNK_SIZE + 1));
    }

    @Override
    public String toString() {
        return String.format("[x: %d; y: %d]", (int) position.x, (int) position.y);
    }

    @Override
    public int hashCode() {
        return (int) (position.x * 10 + position.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof BlockPos) {
            return ((BlockPos) obj).position.equals(position);
        } else {
            return false;
        }
    }
}
