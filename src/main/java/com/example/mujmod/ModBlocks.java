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

    public static final RegistryObject<Block> MITHRIL_BLOCK = BLOCKS.register("mithril_block",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .strength(6.0F, 8.0F)
                    .requiresCorrectToolForDrops()
                    .setId(ResourceKey.create(Registries.BLOCK,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mithril_block")))));

    public static final RegistryObject<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_ORE)
                    .strength(3.0F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .setId(ResourceKey.create(Registries.BLOCK,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mithril_ore")))));

    public static final RegistryObject<Item> MITHRIL_BLOCK_ITEM = ModItems.ITEMS.register("mithril_block",
            () -> new BlockItem(MITHRIL_BLOCK.get(), ModItems.mithrilItemProperties("mithril_block")));

    public static final RegistryObject<Item> MITHRIL_ORE_ITEM = ModItems.ITEMS.register("mithril_ore",
            () -> new BlockItem(MITHRIL_ORE.get(), ModItems.mithrilItemProperties("mithril_ore")));

    public static final RegistryObject<Block> TUTORIAL_SHELF = BLOCKS.register("tutorial_shelf",
            () -> new VerticalShelfBlock(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .strength(8.0F, 12.0F)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .setId(ResourceKey.create(Registries.BLOCK,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "tutorial_shelf")))));

    public static final RegistryObject<Item> TUTORIAL_SHELF_ITEM = ModItems.ITEMS.register("tutorial_shelf",
            () -> new BlockItem(TUTORIAL_SHELF.get(), new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "tutorial_shelf")))));
}