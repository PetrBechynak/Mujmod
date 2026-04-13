package com.example.mujmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MujMod.MOD_ID);

    public static final RegistryObject<EntityType<MiniQueenEntity>> MINI_QUEEN =
            ENTITY_TYPES.register("mini_queen",
                    () -> EntityType.Builder.<MiniQueenEntity>of(MiniQueenEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 2.5f)
                            .clientTrackingRange(8)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mini_queen"))));

    public static final RegistryObject<EntityType<MujMobEntity>> MUJMOB =
            ENTITY_TYPES.register("mujmob",
                    () -> EntityType.Builder.<MujMobEntity>of(MujMobEntity::new, MobCategory.MONSTER)
                            .sized(1.6f, 3.2f)
                            .clientTrackingRange(8)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mujmob"))));
}
