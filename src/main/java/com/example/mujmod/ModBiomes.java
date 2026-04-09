package com.example.mujmod;

import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeManager;

public final class ModBiomes {

    private static final int OVERWORLD_WEIGHT_REPEATS = 12;

    public static final ResourceKey<Biome> TUTORIAL_GROVE = ResourceKey.create(
            Registries.BIOME,
            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "tutorial_grove"));

    private ModBiomes() {
    }

    public static void registerOverworldBiomes() {
        List<BiomeManager.BiomeType> biomeTypes = List.of(
                BiomeManager.BiomeType.WARM,
                BiomeManager.BiomeType.COOL,
                BiomeManager.BiomeType.DESERT,
                BiomeManager.BiomeType.DESERT_LEGACY,
                BiomeManager.BiomeType.ICY);

        for (BiomeManager.BiomeType biomeType : biomeTypes) {
            for (int index = 0; index < OVERWORLD_WEIGHT_REPEATS; index++) {
                BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(TUTORIAL_GROVE, 100));
            }
        }

        BiomeManager.addAdditionalOverworldBiomes(TUTORIAL_GROVE);
    }
}