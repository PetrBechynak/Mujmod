package com.example.mujmod;

import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    private static final TagKey<Item> MITHRIL_REPAIR_MATERIALS = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mithril_repair_materials"));

    private static final ToolMaterial MITHRIL_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            420,
            7.0F,
            2.5F,
            18,
            MITHRIL_REPAIR_MATERIALS);

    private static final ArmorMaterial MITHRIL_ARMOR = new ArmorMaterial(
            18,
            Map.of(
                    ArmorType.HELMET, 3,
                    ArmorType.CHESTPLATE, 6,
                    ArmorType.LEGGINGS, 7,
                    ArmorType.BOOTS, 3),
            12,
            SoundEvents.ARMOR_EQUIP_IRON,
            1.0F,
            0.0F,
            MITHRIL_REPAIR_MATERIALS,
            ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mithril")));

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MujMod.MOD_ID);

    public static final RegistryObject<Item> RAW_MITHRIL = ITEMS.register("raw_mithril",
            () -> new Item(mithrilItemProperties("raw_mithril")));

    public static final RegistryObject<Item> MITHRIL_INGOT = ITEMS.register("mithril_ingot",
            () -> new Item(mithrilItemProperties("mithril_ingot")));

    public static final RegistryObject<Item> MITHRIL_SWORD = ITEMS.register("mithril_sword",
            () -> new TutorialSwordItem(mithrilItemProperties("mithril_sword")
                    .sword(MITHRIL_TOOL_MATERIAL, 3.0f, -2.4f)));

    public static final RegistryObject<Item> MITHRIL_AXE = ITEMS.register("mithril_axe",
            () -> new TreeFellingAxeItem(mithrilItemProperties("mithril_axe")
                    .axe(MITHRIL_TOOL_MATERIAL, 6.0f, -3.1f)));

    public static final RegistryObject<Item> MITHRIL_SHOVEL = ITEMS.register("mithril_shovel",
            () -> new ThreeByThreeShovelItem(mithrilItemProperties("mithril_shovel")
                    .shovel(MITHRIL_TOOL_MATERIAL, 1.5f, -3.0f)));

    public static final RegistryObject<Item> MITHRIL_HOE = ITEMS.register("mithril_hoe",
            () -> new Item(mithrilItemProperties("mithril_hoe")
                    .hoe(MITHRIL_TOOL_MATERIAL, -2.0f, -1.0f)));

    public static final RegistryObject<Item> MITHRIL_HELMET = ITEMS.register("mithril_helmet",
            () -> new Item(mithrilItemProperties("mithril_helmet")
                    .humanoidArmor(MITHRIL_ARMOR, ArmorType.HELMET)));

    public static final RegistryObject<Item> MITHRIL_CHESTPLATE = ITEMS.register("mithril_chestplate",
            () -> new Item(mithrilItemProperties("mithril_chestplate")
                    .humanoidArmor(MITHRIL_ARMOR, ArmorType.CHESTPLATE)));

    public static final RegistryObject<Item> MITHRIL_LEGGINGS = ITEMS.register("mithril_leggings",
            () -> new Item(mithrilItemProperties("mithril_leggings")
                    .humanoidArmor(MITHRIL_ARMOR, ArmorType.LEGGINGS)));

    public static final RegistryObject<Item> MITHRIL_BOOTS = ITEMS.register("mithril_boots",
            () -> new Item(mithrilItemProperties("mithril_boots")
                    .humanoidArmor(MITHRIL_ARMOR, ArmorType.BOOTS)));

    public static final RegistryObject<Item> MITHRIL_PICKAXE = ITEMS.register("mithril_pickaxe",
            () -> new ThreeByThreePickaxeItem(mithrilItemProperties("mithril_pickaxe")
                    .pickaxe(MITHRIL_TOOL_MATERIAL, 1.0f, -2.8f)));

    public static final RegistryObject<Item> MINI_QUEEN_SPAWN_EGG = ITEMS.register("mini_queen_spawn_egg",
            () -> new SpawnEggItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM,
                            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mini_queen_spawn_egg")))
                    .spawnEgg(ModEntities.MINI_QUEEN.get())));

        static Item.Properties mithrilItemProperties(String itemName) {
        return new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM,
                        Identifier.fromNamespaceAndPath(MujMod.MOD_ID, itemName)))
                                .rarity(Rarity.UNCOMMON);
    }
}
