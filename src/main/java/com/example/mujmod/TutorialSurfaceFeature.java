package com.example.mujmod;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class TutorialSurfaceFeature extends Feature<NoneFeatureConfiguration> {

    public TutorialSurfaceFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        ChunkPos chunkPos = ChunkPos.containing(context.origin());
        int minX = chunkPos.getMinBlockX();
        int minZ = chunkPos.getMinBlockZ();
        int minY = level.getMinY();
        BlockState tutorialBlock = ModBlocks.MITHRIL_BLOCK.get().defaultBlockState();
        boolean placedAnyBlock = false;

        for (int x = minX; x < minX + 16; x++) {
            for (int z = minZ; z < minZ + 16; z++) {
                int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);

                for (int y = minY; y <= surfaceY; y++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    BlockState currentState = level.getBlockState(currentPos);

                    if (!shouldReplace(currentState)) {
                        continue;
                    }

                    level.setBlock(currentPos, tutorialBlock, 2);
                    placedAnyBlock = true;
                }
            }
        }

        return placedAnyBlock;
    }

    private static boolean shouldReplace(BlockState blockState) {
        return blockState.is(BlockTags.BASE_STONE_OVERWORLD)
                || blockState.is(BlockTags.DIRT)
                || blockState.is(BlockTags.SAND)
                || blockState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
                || blockState.is(BlockTags.STONE_ORE_REPLACEABLES)
                || blockState.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
                || blockState.is(Blocks.GRASS_BLOCK)
                || blockState.is(Blocks.PODZOL)
                || blockState.is(Blocks.COARSE_DIRT)
                || blockState.is(Blocks.MYCELIUM)
                || blockState.is(Blocks.GRAVEL)
                || blockState.is(Blocks.CLAY)
                || blockState.is(Blocks.SNOW_BLOCK);
    }
}