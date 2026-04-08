package com.example.mujmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MujMod.MOD_ID);

    public static final RegistryObject<Item> TUTORIAL_SWORD = ITEMS.register("tutorial_sword",
            () -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "tutorial_sword")))
                    .sword(ToolMaterial.IRON, 3.0f, -2.4f)));
}
