package net.glitchifyed.skilled_enchanting.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;

public class CondensedObsidianBlock extends Block {
    public CondensedObsidianBlock(Settings settings) {
        super(settings);
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }
}
