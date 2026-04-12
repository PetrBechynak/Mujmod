package com.example.mujmod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MithrilBlock extends Block {

    public MithrilBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (player.getMainHandItem().is(Items.NETHERITE_PICKAXE)) {
            return super.getDestroyProgress(state, player, level, pos);
        }

        return 0.0F;
    }
}