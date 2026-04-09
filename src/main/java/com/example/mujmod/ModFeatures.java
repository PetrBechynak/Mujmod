package com.example.mujmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, MujMod.MOD_ID);

    public static final RegistryObject<TutorialSurfaceFeature> TUTORIAL_SURFACE = FEATURES.register(
            "tutorial_surface",
            TutorialSurfaceFeature::new);

    private ModFeatures() {
    }
}