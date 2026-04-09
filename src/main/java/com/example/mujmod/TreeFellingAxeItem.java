package com.example.mujmod;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TreeFellingAxeItem extends Item {

    private static final int MAX_LOGS_TO_BREAK = 128;
    private static final ThreadLocal<Boolean> IS_FELLING = ThreadLocal.withInitial(() -> false);

    public TreeFellingAxeItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || IS_FELLING.get()) {
            return false;
        }

        Level level = player.level();
        BlockState originState = level.getBlockState(pos);
        if (!originState.is(BlockTags.LOGS)) {
            return false;
        }

        IS_FELLING.set(true);
        try {
            for (BlockPos targetPos : findConnectedLogs(level, pos)) {
                if (targetPos.equals(pos)) {
                    continue;
                }

                serverPlayer.gameMode.destroyBlock(targetPos);
            }
        } finally {
            IS_FELLING.set(false);
        }

        return false;
    }

    private static Set<BlockPos> findConnectedLogs(Level level, BlockPos startPos) {
        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(startPos);

        while (!queue.isEmpty() && visited.size() < MAX_LOGS_TO_BREAK) {
            BlockPos currentPos = queue.removeFirst();
            if (!visited.add(currentPos)) {
                continue;
            }

            for (int offsetX = -1; offsetX <= 1; offsetX++) {
                for (int offsetY = -1; offsetY <= 1; offsetY++) {
                    for (int offsetZ = -1; offsetZ <= 1; offsetZ++) {
                        if (offsetX == 0 && offsetY == 0 && offsetZ == 0) {
                            continue;
                        }

                        BlockPos nextPos = currentPos.offset(offsetX, offsetY, offsetZ);
                        if (!visited.contains(nextPos) && level.getBlockState(nextPos).is(BlockTags.LOGS)) {
                            queue.addLast(nextPos);
                        }
                    }
                }
            }
        }

        return visited;
    }
}