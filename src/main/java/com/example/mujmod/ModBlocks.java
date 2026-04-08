package com.example.mujmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MujMod.MOD_ID);

    public static final RegistryObject<Block> TUTORIAL_BLOCK = BLOCKS.register("tutorial_block",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .setId(ResourceKey.create(Registries.BLOCK,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "tutorial_block")))));

    public static final RegistryObject<Item> TUTORIAL_BLOCK_ITEM = ModItems.ITEMS.register("tutorial_block",
            () -> new BlockItem(TUTORIAL_BLOCK.get(), new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "tutorial_block")))));
}