package WorldBlocks.Turret;

import Entities.Shells.TurretShell.TurretShell;
import Vectors.BlockPos;
import WorldBlocks.Block;
import WorldBlocks.BlockWrapper;

import static Entities.Shells.Shells.TURRET_SHELL;

/**
 * Created by Yuri on 29.01.17.
 */
public class Turret extends Block {

    private TurretShell shell = TURRET_SHELL;

    public Turret() {
        setHardness(30).setTexture("turret_base.png");
    }

    @Override
    public BlockWrapper createWrapper(BlockPos pos) {
        return new TurretWrapper(this, pos, shell);
    }

    public TurretShell getShell() {
        return shell;
    }

    public Turret setShell(TurretShell shell) {
        this.shell = shell;
        return this;
    }

    @Override
    public Turret setTexture(String texture) {
        this.texture = texture;
        return this;
    }
}
