package com.example.mujmod;

import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    private static final ArmorMaterial TUTORIAL_ARMOR = new ArmorMaterial(
            37,
            Map.of(
                    ArmorType.HELMET, 5,
                    ArmorType.CHESTPLATE, 9,
                    ArmorType.LEGGINGS, 7,
                    ArmorType.BOOTS, 5),
            30,
            SoundEvents.ARMOR_EQUIP_IRON,
            3.0F,
            0.2F,
            ItemTags.REPAIRS_IRON_ARMOR,
            EquipmentAssets.IRON);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MujMod.MOD_ID);

    public static final RegistryObject<Item> TUTORIAL_SWORD = ITEMS.register("tutorial_sword",
            () -> new TutorialSwordItem(tutorialItemProperties("tutorial_sword")
                    .sword(ToolMaterial.NETHERITE, 8.0f, -2.2f)));

    public static final RegistryObject<Item> TUTORIAL_AXE = ITEMS.register("tutorial_axe",
            () -> new TreeFellingAxeItem(tutorialItemProperties("tutorial_axe")
                    .axe(ToolMaterial.NETHERITE, 17.0f, 0.4f)));

    public static final RegistryObject<Item> TUTORIAL_SHOVEL = ITEMS.register("tutorial_shovel",
            () -> new ThreeByThreeShovelItem(tutorialItemProperties("tutorial_shovel")
                    .shovel(ToolMaterial.NETHERITE, 17.0f, 0.4f)));

    public static final RegistryObject<Item> TUTORIAL_HOE = ITEMS.register("tutorial_hoe",
            () -> new Item(tutorialItemProperties("tutorial_hoe")
                    .hoe(ToolMaterial.NETHERITE, 17.0f, 0.4f)));

    public static final RegistryObject<Item> TUTORIAL_HELMET = ITEMS.register("tutorial_helmet",
            () -> new Item(tutorialItemProperties("tutorial_helmet")
                    .humanoidArmor(TUTORIAL_ARMOR, ArmorType.HELMET)));

    public static final RegistryObject<Item> TUTORIAL_CHESTPLATE = ITEMS.register("tutorial_chestplate",
            () -> new Item(tutorialItemProperties("tutorial_chestplate")
                    .humanoidArmor(TUTORIAL_ARMOR, ArmorType.CHESTPLATE)));

    public static final RegistryObject<Item> TUTORIAL_LEGGINGS = ITEMS.register("tutorial_leggings",
            () -> new Item(tutorialItemProperties("tutorial_leggings")
                    .humanoidArmor(TUTORIAL_ARMOR, ArmorType.LEGGINGS)));

    public static final RegistryObject<Item> TUTORIAL_BOOTS = ITEMS.register("tutorial_boots",
            () -> new Item(tutorialItemProperties("tutorial_boots")
                    .humanoidArmor(TUTORIAL_ARMOR, ArmorType.BOOTS)));

    public static final RegistryObject<Item> TUTORIAL_PICKAXE = ITEMS.register("tutorial_pickaxe",
            () -> new ThreeByThreePickaxeItem(tutorialItemProperties("tutorial_pickaxe")
                    .pickaxe(ToolMaterial.NETHERITE, 17.0f, 0.4f)));

    static Item.Properties tutorialItemProperties(String itemName) {
        return new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM,
                        Identifier.fromNamespaceAndPath(MujMod.MOD_ID, itemName)))
                .fireResistant()
                .rarity(Rarity.EPIC)
                .enchantable(30);
    }
}
