package WorldProviding;


import Blocks.Block;
import Blocks.Blocks;
import Blocks.BlockWrapper;
import Game.GameGL;
import Gui.MouseHandler;
import Objects.HUDElement;
import Vectors.BlockPos;
import Vectors.Vector2;
import com.jogamp.newt.event.MouseListener;

import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Создано Юрием в 31.12.16.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class World implements Iterable<BlockWrapper> {
    public static final int CHUNK_SIZE = 20;
    private HashMap<BlockPos, Chunk> chunks = new HashMap<>();
    public ArrayList<HUDElement> hudElements = new ArrayList<>();

    private static World instance;

    public static void generateNewWorld() {
        Random random = new Random();
        instance = new World();
        for (int i = 0; i < 400; i++) {
            BlockPos pos = new BlockPos(random.nextInt(20) - 10, random.nextInt(20) - 10);
            instance.setBlock(pos, Blocks.stone);
        }
    }

    public World() {

        GameGL.window.addMouseListener(new MouseHandler());
    }



    public static void load(String worldName) {

    }

    public static World getInstance() {
        return instance;
    }

    public void setBlock(BlockPos pos, Block block) {
        Chunk chunk = getChunkByBlockPos(pos);

        chunk.setBlock(pos.convertToChunkPos(), block);
    }

    public Chunk getChunkByBlockPos(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();

        x = x / (CHUNK_SIZE + 1);
        y = y / (CHUNK_SIZE + 1);

        pos = new BlockPos(x, y);

        return getChunk(pos);
    }

    public Chunk getChunk(BlockPos pos) {
        Chunk res = chunks.get(pos);
        if (res == null) {
            res = new Chunk();
            res.generate();
            chunks.put(pos, res);
        }
        return res;
    }

    public Collection<Chunk> getChuncks() {
        return chunks.values();
    }

    public Set<Map.Entry<BlockPos, Chunk>> getChunksSet() {
        return chunks.entrySet();
    }


    @Override
    public Iterator<BlockWrapper> iterator() {
        return new WorldIterator(chunks);
    }

    private Vector2 translateMouseVector(float x, float y) {
        x -= GameGL.window.getWidth() / 2.0;
        y -= GameGL.window.getHeight() / 2.0;
        x /= GameGL.window.getWidth() / 2;
        y /= GameGL.window.getHeight() / 2;
        y *= -1;

        return new Vector2(x, y);
    }

    private class WorldIterator implements Iterator<BlockWrapper> {

        private HashMap<BlockPos, Chunk> map;
        private Iterator<Chunk> mapIterator;
        private Iterator<BlockWrapper> currentIterator;

        public WorldIterator(HashMap<BlockPos, Chunk> map) {
            this.map = map;
            mapIterator = map.values().iterator();
        }

        @Override
        public boolean hasNext() {
            if (currentIterator == null) {
                if (!mapIterator.hasNext()) return false;
                currentIterator = mapIterator.next().iterator();
            }
            if (!currentIterator.hasNext()) {
                if (!mapIterator.hasNext()) {
                    return false;
                }
                currentIterator = mapIterator.next().iterator();
            }
            return currentIterator.hasNext();
        }

        @Override
        public BlockWrapper next() {
            if (!hasNext()) return null;
            return currentIterator.next();
        }

        @Override
        public void remove() {

        }

    }
}
