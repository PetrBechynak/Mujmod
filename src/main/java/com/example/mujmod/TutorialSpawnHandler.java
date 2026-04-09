package com.example.mujmod;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;

import java.util.Set;

public final class TutorialSpawnHandler {

    private static final String SPAWNED_IN_GROVE_TAG = "mujmod.spawned_in_tutorial_grove";
    private static final int BIOME_SEARCH_RADIUS = 64000;
    private static final int BIOME_SEARCH_HORIZONTAL_STEP = 32;
    private static final int BIOME_SEARCH_VERTICAL_STEP = 64;
    private static final int SPAWN_AREA_RADIUS_CHUNKS = 1;
    private static final int SPAWN_BLEND_WIDTH = 6;

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

        BlockPos targetPos = serverPlayer.blockPosition();

        if (serverLevel.getBiome(serverPlayer.blockPosition()).is(ModBiomes.TUTORIAL_GROVE)) {
            prepareSpawnArea(serverLevel, targetPos);
            persistentData.putBoolean(SPAWNED_IN_GROVE_TAG, true);
            return;
        }

        BlockPos spawnPos = findTutorialSpawn(serverLevel, serverPlayer.blockPosition());
        if (spawnPos != null) {
            LevelData.RespawnData respawnData = LevelData.RespawnData.of(Level.OVERWORLD, spawnPos, 0.0F, 0.0F);
            serverLevel.setRespawnData(respawnData);
            ((WritableLevelData) serverLevel.getLevelData()).setSpawn(respawnData);
            serverPlayer.teleportTo(serverLevel, spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D, Set.<Relative>of(), serverPlayer.getYRot(), serverPlayer.getXRot(), true);
            targetPos = spawnPos;
        }

        prepareSpawnArea(serverLevel, targetPos);
        persistentData.putBoolean(SPAWNED_IN_GROVE_TAG, true);
    }

    private static void prepareSpawnArea(ServerLevel serverLevel, BlockPos centerPos) {
        ChunkPos centerChunk = ChunkPos.containing(centerPos);
        int minX = centerChunk.x() - SPAWN_AREA_RADIUS_CHUNKS << 4;
        int minZ = centerChunk.z() - SPAWN_AREA_RADIUS_CHUNKS << 4;
        int maxX = ((centerChunk.x() + SPAWN_AREA_RADIUS_CHUNKS) << 4) + 15;
        int maxZ = ((centerChunk.z() + SPAWN_AREA_RADIUS_CHUNKS) << 4) + 15;
        int minY = serverLevel.getMinY();
        BlockState tutorialBlock = ModBlocks.TUTORIAL_BLOCK.get().defaultBlockState();

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                float conversionChance = getConversionChance(x, z, minX, maxX, minZ, maxZ);
                if (conversionChance <= 0.0F) {
                    continue;
                }

                int surfaceY = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

                for (int y = minY; y <= surfaceY; y++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    BlockState currentState = serverLevel.getBlockState(currentPos);

                    if (currentState.isAir() || !currentState.getFluidState().isEmpty() || currentState.is(Blocks.BEDROCK)) {
                        continue;
                    }

                    if (conversionChance < 1.0F && !shouldReplaceAtEdge(x, y, z, conversionChance)) {
                        continue;
                    }

                    serverLevel.setBlock(currentPos, tutorialBlock, 3);
                }
            }
        }
    }

    private static float getConversionChance(int x, int z, int minX, int maxX, int minZ, int maxZ) {
        int edgeDistance = Math.min(
                Math.min(x - minX, maxX - x),
                Math.min(z - minZ, maxZ - z));

        return Mth.clamp(edgeDistance / (float) SPAWN_BLEND_WIDTH, 0.0F, 1.0F);
    }

    private static boolean shouldReplaceAtEdge(int x, int y, int z, float conversionChance) {
        long hash = 1469598103934665603L;
        hash ^= x * 341873128712L;
        hash *= 1099511628211L;
        hash ^= y * 132897987541L;
        hash *= 1099511628211L;
        hash ^= z * 42317861L;
        hash *= 1099511628211L;
        float randomValue = ((hash >>> 40) & 1023L) / 1023.0F;
        return randomValue <= conversionChance;
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

        BlockPos biomePos = foundBiome.getFirst();
        int spawnY = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, biomePos.getX(), biomePos.getZ());
        return new BlockPos(biomePos.getX(), spawnY, biomePos.getZ());
    }
}