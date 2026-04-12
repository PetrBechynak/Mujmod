package com.example.mujmod;

import com.mojang.datafixers.util.Pair;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;

public final class TutorialSpawnHandler {

    private static final String SPAWNED_IN_GROVE_TAG = "mujmod.spawned_in_tutorial_grove";
    private static final int BIOME_SEARCH_RADIUS = 64000;
    private static final int BIOME_SEARCH_HORIZONTAL_STEP = 32;
    private static final int BIOME_SEARCH_VERTICAL_STEP = 64;
    private static final int SPAWN_SEARCH_RADIUS_BLOCKS = 96;
    private static final int SPAWN_SEARCH_STEP_BLOCKS = 8;
    private static final int MIN_DRY_SURFACE_Y_OFFSET = 2;
    private static final int FALLBACK_AREA_RADIUS_BLOCKS = 48;
    private static final int FALLBACK_FULL_STRENGTH_RADIUS_BLOCKS = 20;
    private static final int FALLBACK_SURFACE_DEPTH = 4;

    private TutorialSpawnHandler() {
    }

    public static boolean onCreateSpawnPosition(LevelEvent.CreateSpawnPosition event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)
                || serverLevel.dimension() != Level.OVERWORLD) {
            return false;
        }

        BlockPos spawnPos = findTutorialSpawn(serverLevel, BlockPos.ZERO);
        if (spawnPos == null) {
            return false;
        }

        LevelData.RespawnData respawnData = LevelData.RespawnData.of(Level.OVERWORLD, spawnPos, 0.0F, 0.0F);
        serverLevel.setRespawnData(respawnData);
        event.getSettings().setSpawn(respawnData);
        event.getSettings().setInitialized(true);
        return true;
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ServerLevel serverLevel = serverPlayer.level();
        if (serverLevel.dimension() != Level.OVERWORLD) {
            return;
        }

        CompoundTag persistentData = serverPlayer.getPersistentData();
        if (persistentData.getBooleanOr(SPAWNED_IN_GROVE_TAG, false)) {
            return;
        }

        if (serverLevel.getBiome(serverPlayer.blockPosition()).is(ModBiomes.TUTORIAL_GROVE)) {
            applyTutorialBlocksNearSpawn(serverLevel, serverPlayer.blockPosition());
            persistentData.putBoolean(SPAWNED_IN_GROVE_TAG, true);
            return;
        }

        BlockPos spawnPos = findTutorialSpawn(serverLevel, serverPlayer.blockPosition());
        if (spawnPos != null) {
            LevelData.RespawnData respawnData = LevelData.RespawnData.of(Level.OVERWORLD, spawnPos, 0.0F, 0.0F);
            serverLevel.setRespawnData(respawnData);
            ((WritableLevelData) serverLevel.getLevelData()).setSpawn(respawnData);
            serverPlayer.teleportTo(serverLevel, spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D, Set.<Relative>of(), serverPlayer.getYRot(), serverPlayer.getXRot(), true);
            applyTutorialBlocksNearSpawn(serverLevel, spawnPos);
        } else {
            applyTutorialBlocksNearSpawn(serverLevel, serverPlayer.blockPosition());
        }

        persistentData.putBoolean(SPAWNED_IN_GROVE_TAG, true);
    }

    private static void applyTutorialBlocksNearSpawn(ServerLevel serverLevel, BlockPos centerPos) {
        int minY = serverLevel.getMinY();
        int maxY = serverLevel.getMaxY();
        BlockState tutorialBlock = ModBlocks.MITHRIL_BLOCK.get().defaultBlockState();
        int radiusSquared = FALLBACK_AREA_RADIUS_BLOCKS * FALLBACK_AREA_RADIUS_BLOCKS;

        for (int x = centerPos.getX() - FALLBACK_AREA_RADIUS_BLOCKS; x <= centerPos.getX() + FALLBACK_AREA_RADIUS_BLOCKS; x++) {
            for (int z = centerPos.getZ() - FALLBACK_AREA_RADIUS_BLOCKS; z <= centerPos.getZ() + FALLBACK_AREA_RADIUS_BLOCKS; z++) {
                int offsetX = x - centerPos.getX();
                int offsetZ = z - centerPos.getZ();
                int distanceSquared = offsetX * offsetX + offsetZ * offsetZ;
                if (distanceSquared > radiusSquared) {
                    continue;
                }

                double blendFactor = getFallbackBlendFactor(distanceSquared);
                if (blendFactor <= 0.0D) {
                    continue;
                }

                if (!shouldApplyFallbackColumn(x, z, blendFactor)) {
                    continue;
                }

                int terrainTopY = findTerrainTopY(serverLevel, x, z);
                if (terrainTopY < minY) {
                    continue;
                }

                int topReplacementStart = Math.max(minY, terrainTopY - FALLBACK_SURFACE_DEPTH + 1);
                for (int y = minY; y <= terrainTopY; y++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    BlockState currentState = serverLevel.getBlockState(currentPos);
                    if (!shouldReplaceForTutorialArea(currentState, y >= topReplacementStart)) {
                        continue;
                    }

                    serverLevel.setBlock(currentPos, tutorialBlock, 3);
                }

                clearAboveSurface(serverLevel, x, z, terrainTopY + 1, maxY);
            }
        }
    }

    private static void clearAboveSurface(ServerLevel serverLevel, int x, int z, int startY, int maxY) {
        for (int y = startY; y <= maxY; y++) {
            BlockPos currentPos = new BlockPos(x, y, z);
            BlockState currentState = serverLevel.getBlockState(currentPos);
            if (currentState.isAir() || shouldPreserveAboveSurface(currentState)) {
                continue;
            }

            serverLevel.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    private static double getFallbackBlendFactor(int distanceSquared) {
        double distance = Math.sqrt(distanceSquared);
        if (distance <= FALLBACK_FULL_STRENGTH_RADIUS_BLOCKS) {
            return 1.0D;
        }

        double fadeDistance = FALLBACK_AREA_RADIUS_BLOCKS - FALLBACK_FULL_STRENGTH_RADIUS_BLOCKS;
        if (fadeDistance <= 0.0D || distance >= FALLBACK_AREA_RADIUS_BLOCKS) {
            return 0.0D;
        }

        double normalized = 1.0D - ((distance - FALLBACK_FULL_STRENGTH_RADIUS_BLOCKS) / fadeDistance);
        return normalized * normalized * (3.0D - (2.0D * normalized));
    }

    private static boolean shouldApplyFallbackColumn(int x, int z, double blendFactor) {
        if (blendFactor >= 1.0D) {
            return true;
        }

        return coordinateNoise(x, 0, z) <= Math.min(1.0D, blendFactor + 0.1D);
    }

    private static double coordinateNoise(int x, int y, int z) {
        long hash = 1469598103934665603L;
        hash ^= x * 73428767L;
        hash *= 1099511628211L;
        hash ^= y * 912931L;
        hash *= 1099511628211L;
        hash ^= z * 43828931L;
        hash *= 1099511628211L;
        long positive = hash & Long.MAX_VALUE;
        return (positive % 10_000L) / 9_999.0D;
    }

    private static boolean shouldPreserveAboveSurface(BlockState blockState) {
        return blockState.getFluidState().is(FluidTags.WATER)
                || blockState.is(BlockTags.ICE)
                || blockState.is(Blocks.ICE)
                || blockState.is(Blocks.PACKED_ICE)
                || blockState.is(Blocks.BLUE_ICE)
                || blockState.is(Blocks.FROSTED_ICE);
    }

    private static boolean shouldReplaceForTutorialArea(BlockState blockState, boolean nearSurface) {
        if (blockState.isAir() || !blockState.getFluidState().isEmpty() || blockState.is(Blocks.BEDROCK)) {
            return false;
        }

        if (nearSurface) {
            return blockState.is(BlockTags.DIRT)
                    || blockState.is(BlockTags.SAND)
                    || blockState.is(BlockTags.BASE_STONE_OVERWORLD)
                    || blockState.is(BlockTags.STONE_ORE_REPLACEABLES)
                    || blockState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
                    || blockState.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
                    || blockState.is(Blocks.GRASS_BLOCK)
                    || blockState.is(Blocks.PODZOL)
                    || blockState.is(Blocks.COARSE_DIRT)
                    || blockState.is(Blocks.MYCELIUM)
                    || blockState.is(Blocks.GRAVEL)
                    || blockState.is(Blocks.CLAY)
                    || blockState.is(Blocks.SNOW_BLOCK);
        }

        return blockState.is(BlockTags.BASE_STONE_OVERWORLD)
                || blockState.is(BlockTags.STONE_ORE_REPLACEABLES)
                || blockState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
                || blockState.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES);
    }

    private static int findTerrainTopY(ServerLevel serverLevel, int x, int z) {
        int scanY = serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

        for (int y = scanY; y >= serverLevel.getMinY(); y--) {
            if (!serverLevel.getBlockState(new BlockPos(x, y, z)).getFluidState().isEmpty()) {
                continue;
            }

            if (!serverLevel.getBlockState(new BlockPos(x, y, z)).isAir()) {
                return y;
            }
        }

        return serverLevel.getMinY() - 1;
    }

    private static BlockPos findTutorialSpawn(ServerLevel serverLevel, BlockPos origin) {
        Pair<BlockPos, Holder<Biome>> foundBiome = serverLevel.findClosestBiome3d(
                holder -> holder.is(ModBiomes.TUTORIAL_GROVE),
                origin,
                BIOME_SEARCH_RADIUS,
                BIOME_SEARCH_HORIZONTAL_STEP,
                BIOME_SEARCH_VERTICAL_STEP);

        if (foundBiome == null) {
            return null;
        }

        return findBestSpawnPosition(serverLevel, foundBiome.getFirst());
    }

    private static BlockPos findBestSpawnPosition(ServerLevel serverLevel, BlockPos biomePos) {
        BlockPos bestPos = null;
        int bestScore = Integer.MIN_VALUE;

        for (int x = biomePos.getX() - SPAWN_SEARCH_RADIUS_BLOCKS; x <= biomePos.getX() + SPAWN_SEARCH_RADIUS_BLOCKS; x += SPAWN_SEARCH_STEP_BLOCKS) {
            for (int z = biomePos.getZ() - SPAWN_SEARCH_RADIUS_BLOCKS; z <= biomePos.getZ() + SPAWN_SEARCH_RADIUS_BLOCKS; z += SPAWN_SEARCH_STEP_BLOCKS) {
                int terrainTopY = findTerrainTopY(serverLevel, x, z);
                if (terrainTopY < serverLevel.getMinY()) {
                    continue;
                }

                BlockPos candidateGround = new BlockPos(x, terrainTopY, z);
                if (!serverLevel.getBiome(candidateGround).is(ModBiomes.TUTORIAL_GROVE)) {
                    continue;
                }

                int score = scoreSpawnCandidate(serverLevel, candidateGround);
                if (score > bestScore) {
                    bestScore = score;
                    bestPos = candidateGround.above();
                }
            }
        }

        if (bestPos != null) {
            return bestPos;
        }

        int fallbackTopY = findTerrainTopY(serverLevel, biomePos.getX(), biomePos.getZ());
        return fallbackTopY >= serverLevel.getMinY() ? new BlockPos(biomePos.getX(), fallbackTopY + 1, biomePos.getZ()) : null;
    }

    private static int scoreSpawnCandidate(ServerLevel serverLevel, BlockPos candidateGround) {
        int score = 0;
        int seaLevel = serverLevel.getSeaLevel();
        int centerY = candidateGround.getY();

        if (centerY >= seaLevel + MIN_DRY_SURFACE_Y_OFFSET) {
            score += 30;
        } else {
            score -= 40;
        }

        for (int offsetX = -16; offsetX <= 16; offsetX += 8) {
            for (int offsetZ = -16; offsetZ <= 16; offsetZ += 8) {
                int sampleX = candidateGround.getX() + offsetX;
                int sampleZ = candidateGround.getZ() + offsetZ;
                int sampleTopY = findTerrainTopY(serverLevel, sampleX, sampleZ);
                if (sampleTopY < serverLevel.getMinY()) {
                    score -= 25;
                    continue;
                }

                BlockPos sampleGround = new BlockPos(sampleX, sampleTopY, sampleZ);
                if (!serverLevel.getBiome(sampleGround).is(ModBiomes.TUTORIAL_GROVE)) {
                    score -= 20;
                    continue;
                }

                if (sampleTopY >= seaLevel + MIN_DRY_SURFACE_Y_OFFSET) {
                    score += 6;
                } else {
                    score -= 12;
                }

                score -= Math.abs(sampleTopY - centerY) * 2;
            }
        }

        return score;
    }
}