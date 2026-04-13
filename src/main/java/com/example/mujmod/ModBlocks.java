package com.example.mujmod;

import java.util.ArrayList;
import java.util.List;

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

        public static final List<RegistryObject<Item>> SHELF_ITEMS = new ArrayList<>();

    public static final RegistryObject<Block> MITHRIL_BLOCK = BLOCKS.register("mithril_block",
            () -> new MithrilBlock(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)
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

    public static final RegistryObject<Block> TUTORIAL_SHELF = registerShelf("tutorial_shelf", Blocks.IRON_BLOCK);
    public static final RegistryObject<Block> OAK_SHELF = registerShelf("oak_shelf", Blocks.OAK_PLANKS);
    public static final RegistryObject<Block> SPRUCE_SHELF = registerShelf("spruce_shelf", Blocks.SPRUCE_PLANKS);
    public static final RegistryObject<Block> BIRCH_SHELF = registerShelf("birch_shelf", Blocks.BIRCH_PLANKS);
    public static final RegistryObject<Block> JUNGLE_SHELF = registerShelf("jungle_shelf", Blocks.JUNGLE_PLANKS);
    public static final RegistryObject<Block> ACACIA_SHELF = registerShelf("acacia_shelf", Blocks.ACACIA_PLANKS);
    public static final RegistryObject<Block> DARK_OAK_SHELF = registerShelf("dark_oak_shelf", Blocks.DARK_OAK_PLANKS);
    public static final RegistryObject<Block> MANGROVE_SHELF = registerShelf("mangrove_shelf", Blocks.MANGROVE_PLANKS);
    public static final RegistryObject<Block> CHERRY_SHELF = registerShelf("cherry_shelf", Blocks.CHERRY_PLANKS);
    public static final RegistryObject<Block> PALE_OAK_SHELF = registerShelf("pale_oak_shelf", Blocks.PALE_OAK_PLANKS);
    public static final RegistryObject<Block> BAMBOO_SHELF = registerShelf("bamboo_shelf", Blocks.BAMBOO_PLANKS);
    public static final RegistryObject<Block> BAMBOO_MOSAIC_SHELF = registerShelf("bamboo_mosaic_shelf", Blocks.BAMBOO_MOSAIC);
    public static final RegistryObject<Block> CRIMSON_SHELF = registerShelf("crimson_shelf", Blocks.CRIMSON_PLANKS);
    public static final RegistryObject<Block> WARPED_SHELF = registerShelf("warped_shelf", Blocks.WARPED_PLANKS);
    public static final RegistryObject<Block> STONE_SHELF = registerShelf("stone_shelf", Blocks.STONE);
    public static final RegistryObject<Block> SMOOTH_STONE_SHELF = registerShelf("smooth_stone_shelf", Blocks.SMOOTH_STONE);
    public static final RegistryObject<Block> COBBLESTONE_SHELF = registerShelf("cobblestone_shelf", Blocks.COBBLESTONE);
    public static final RegistryObject<Block> MOSSY_COBBLESTONE_SHELF = registerShelf("mossy_cobblestone_shelf", Blocks.MOSSY_COBBLESTONE);
    public static final RegistryObject<Block> STONE_BRICK_SHELF = registerShelf("stone_brick_shelf", Blocks.STONE_BRICKS);
    public static final RegistryObject<Block> MOSSY_STONE_BRICK_SHELF = registerShelf("mossy_stone_brick_shelf", Blocks.MOSSY_STONE_BRICKS);
    public static final RegistryObject<Block> BRICK_SHELF = registerShelf("brick_shelf", Blocks.BRICKS);
    public static final RegistryObject<Block> MUD_BRICK_SHELF = registerShelf("mud_brick_shelf", Blocks.MUD_BRICKS);
    public static final RegistryObject<Block> SANDSTONE_SHELF = registerShelf("sandstone_shelf", Blocks.SANDSTONE);
    public static final RegistryObject<Block> SMOOTH_SANDSTONE_SHELF = registerShelf("smooth_sandstone_shelf", Blocks.SMOOTH_SANDSTONE);
    public static final RegistryObject<Block> CUT_SANDSTONE_SHELF = registerShelf("cut_sandstone_shelf", Blocks.CUT_SANDSTONE);
    public static final RegistryObject<Block> RED_SANDSTONE_SHELF = registerShelf("red_sandstone_shelf", Blocks.RED_SANDSTONE);
    public static final RegistryObject<Block> SMOOTH_RED_SANDSTONE_SHELF = registerShelf("smooth_red_sandstone_shelf", Blocks.SMOOTH_RED_SANDSTONE);
    public static final RegistryObject<Block> CUT_RED_SANDSTONE_SHELF = registerShelf("cut_red_sandstone_shelf", Blocks.CUT_RED_SANDSTONE);
    public static final RegistryObject<Block> QUARTZ_SHELF = registerShelf("quartz_shelf", Blocks.QUARTZ_BLOCK);
    public static final RegistryObject<Block> SMOOTH_QUARTZ_SHELF = registerShelf("smooth_quartz_shelf", Blocks.SMOOTH_QUARTZ);
    public static final RegistryObject<Block> NETHER_BRICK_SHELF = registerShelf("nether_brick_shelf", Blocks.NETHER_BRICKS);
    public static final RegistryObject<Block> RED_NETHER_BRICK_SHELF = registerShelf("red_nether_brick_shelf", Blocks.RED_NETHER_BRICKS);
    public static final RegistryObject<Block> PURPUR_SHELF = registerShelf("purpur_shelf", Blocks.PURPUR_BLOCK);
    public static final RegistryObject<Block> PRISMARINE_SHELF = registerShelf("prismarine_shelf", Blocks.PRISMARINE);
    public static final RegistryObject<Block> PRISMARINE_BRICK_SHELF = registerShelf("prismarine_brick_shelf", Blocks.PRISMARINE_BRICKS);
    public static final RegistryObject<Block> DARK_PRISMARINE_SHELF = registerShelf("dark_prismarine_shelf", Blocks.DARK_PRISMARINE);
    public static final RegistryObject<Block> END_STONE_BRICK_SHELF = registerShelf("end_stone_brick_shelf", Blocks.END_STONE_BRICKS);
    public static final RegistryObject<Block> BLACKSTONE_SHELF = registerShelf("blackstone_shelf", Blocks.BLACKSTONE);
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_SHELF = registerShelf("polished_blackstone_shelf", Blocks.POLISHED_BLACKSTONE);
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICK_SHELF = registerShelf("polished_blackstone_brick_shelf", Blocks.POLISHED_BLACKSTONE_BRICKS);
    public static final RegistryObject<Block> COBBLED_DEEPSLATE_SHELF = registerShelf("cobbled_deepslate_shelf", Blocks.COBBLED_DEEPSLATE);
    public static final RegistryObject<Block> POLISHED_DEEPSLATE_SHELF = registerShelf("polished_deepslate_shelf", Blocks.POLISHED_DEEPSLATE);
    public static final RegistryObject<Block> DEEPSLATE_BRICK_SHELF = registerShelf("deepslate_brick_shelf", Blocks.DEEPSLATE_BRICKS);
    public static final RegistryObject<Block> DEEPSLATE_TILE_SHELF = registerShelf("deepslate_tile_shelf", Blocks.DEEPSLATE_TILES);
    public static final RegistryObject<Block> GRANITE_SHELF = registerShelf("granite_shelf", Blocks.GRANITE);
    public static final RegistryObject<Block> POLISHED_GRANITE_SHELF = registerShelf("polished_granite_shelf", Blocks.POLISHED_GRANITE);
    public static final RegistryObject<Block> DIORITE_SHELF = registerShelf("diorite_shelf", Blocks.DIORITE);
    public static final RegistryObject<Block> POLISHED_DIORITE_SHELF = registerShelf("polished_diorite_shelf", Blocks.POLISHED_DIORITE);
    public static final RegistryObject<Block> ANDESITE_SHELF = registerShelf("andesite_shelf", Blocks.ANDESITE);
    public static final RegistryObject<Block> POLISHED_ANDESITE_SHELF = registerShelf("polished_andesite_shelf", Blocks.POLISHED_ANDESITE);
    public static final RegistryObject<Block> TUFF_SHELF = registerShelf("tuff_shelf", Blocks.TUFF);
    public static final RegistryObject<Block> POLISHED_TUFF_SHELF = registerShelf("polished_tuff_shelf", Blocks.POLISHED_TUFF);
    public static final RegistryObject<Block> TUFF_BRICK_SHELF = registerShelf("tuff_brick_shelf", Blocks.TUFF_BRICKS);
    public static final RegistryObject<Block> CUT_COPPER_SHELF = registerShelf("cut_copper_shelf", Blocks.CUT_COPPER);
    public static final RegistryObject<Block> EXPOSED_CUT_COPPER_SHELF = registerShelf("exposed_cut_copper_shelf", Blocks.EXPOSED_CUT_COPPER);
    public static final RegistryObject<Block> WEATHERED_CUT_COPPER_SHELF = registerShelf("weathered_cut_copper_shelf", Blocks.WEATHERED_CUT_COPPER);
    public static final RegistryObject<Block> OXIDIZED_CUT_COPPER_SHELF = registerShelf("oxidized_cut_copper_shelf", Blocks.OXIDIZED_CUT_COPPER);
    public static final RegistryObject<Block> WAXED_CUT_COPPER_SHELF = registerShelf("waxed_cut_copper_shelf", Blocks.WAXED_CUT_COPPER);
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_COPPER_SHELF = registerShelf("waxed_exposed_cut_copper_shelf", Blocks.WAXED_EXPOSED_CUT_COPPER);
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_COPPER_SHELF = registerShelf("waxed_weathered_cut_copper_shelf", Blocks.WAXED_WEATHERED_CUT_COPPER);
    public static final RegistryObject<Block> WAXED_OXIDIZED_CUT_COPPER_SHELF = registerShelf("waxed_oxidized_cut_copper_shelf", Blocks.WAXED_OXIDIZED_CUT_COPPER);
    public static final RegistryObject<Block> RESIN_BRICK_SHELF = registerShelf("resin_brick_shelf", Blocks.RESIN_BRICKS);

    private static RegistryObject<Block> registerShelf(String blockName, Block sourceBlock) {
        RegistryObject<Block> shelf = BLOCKS.register(blockName,
                () -> new VerticalShelfBlock(Block.Properties.ofFullCopy(sourceBlock)
                        .noOcclusion()
                        .setId(ResourceKey.create(Registries.BLOCK,
                                Identifier.fromNamespaceAndPath(MujMod.MOD_ID, blockName)))));
        SHELF_ITEMS.add(ModItems.ITEMS.register(blockName,
                () -> new BlockItem(shelf.get(), shelfItemProperties(blockName))));
        return shelf;
    }

    private static Item.Properties shelfItemProperties(String itemName) {
        return new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM,
                        Identifier.fromNamespaceAndPath(MujMod.MOD_ID, itemName)));
    }
}