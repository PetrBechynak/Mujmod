package com.example.mujmod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ThreeByThreePickaxeItem extends Item {

    private static final ThreadLocal<Boolean> IS_MINING = ThreadLocal.withInitial(() -> false);

    public ThreeByThreePickaxeItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || IS_MINING.get()) {
            return false;
        }

        Level level = player.level();
        BlockState originState = level.getBlockState(pos);
        if (originState.isAir() || stack.getDestroySpeed(originState) <= 1.0F) {
            return false;
        }

        IS_MINING.set(true);
        try {
            for (BlockPos targetPos : getBlocksToMine(pos, player)) {
                if (targetPos.equals(pos)) {
                    continue;
                }

                BlockState targetState = level.getBlockState(targetPos);
                if (targetState.isAir() || stack.getDestroySpeed(targetState) <= 1.0F) {
                    continue;
                }

                serverPlayer.gameMode.destroyBlock(targetPos);
            }
        } finally {
            IS_MINING.set(false);
        }

        return false;
    }

    private static BlockPos[] getBlocksToMine(BlockPos origin, Player player) {
        if (Math.abs(player.getXRot()) > 40.0F) {
            return createHorizontalPlane(origin);
        }

        return switch (player.getDirection().getAxis()) {
            case X -> createYZPlane(origin);
            case Z -> createXYPlane(origin);
            case Y -> createHorizontalPlane(origin);
        };
    }

    private static BlockPos[] createHorizontalPlane(BlockPos origin) {
        BlockPos[] positions = new BlockPos[9];
        int index = 0;
        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetZ = -1; offsetZ <= 1; offsetZ++) {
                positions[index++] = origin.offset(offsetX, 0, offsetZ);
            }
        }
        return positions;
    }

    private static BlockPos[] createXYPlane(BlockPos origin) {
        BlockPos[] positions = new BlockPos[9];
        int index = 0;
        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetY = -1; offsetY <= 1; offsetY++) {
                positions[index++] = origin.offset(offsetX, offsetY, 0);
            }
        }
        return positions;
    }

    private static BlockPos[] createYZPlane(BlockPos origin) {
        BlockPos[] positions = new BlockPos[9];
        int index = 0;
        for (int offsetY = -1; offsetY <= 1; offsetY++) {
            for (int offsetZ = -1; offsetZ <= 1; offsetZ++) {
                positions[index++] = origin.offset(0, offsetY, offsetZ);
            }
        }
        return positions;
    }
}