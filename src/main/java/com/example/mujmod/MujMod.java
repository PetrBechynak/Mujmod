package com.example.mujmod;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MujMod.MOD_ID)
public class MujMod {

    public static final String MOD_ID = "mujmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MujMod(FMLJavaModLoadingContext context) {
        BusGroup modBusGroup = context.getModBusGroup();

        ModBiomes.registerOverworldBiomes();
        ModItems.ITEMS.register(modBusGroup);
        ModBlocks.BLOCKS.register(modBusGroup);
        ModFeatures.FEATURES.register(modBusGroup);
        BuildCreativeModeTabContentsEvent.BUS.addListener(this::addCreative);
        TickEvent.PlayerTickEvent.Post.BUS.addListener(TutorialArmorEffects::onPlayerTick);
        LevelEvent.CreateSpawnPosition.BUS.addListener(TutorialSpawnHandler::onCreateSpawnPosition);
        PlayerEvent.PlayerLoggedInEvent.BUS.addListener(TutorialSpawnHandler::onPlayerLoggedIn);

        LOGGER.info("MujMod byl načten!");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.TUTORIAL_SWORD);
            event.accept(ModItems.TUTORIAL_HELMET);
            event.accept(ModItems.TUTORIAL_CHESTPLATE);
            event.accept(ModItems.TUTORIAL_LEGGINGS);
            event.accept(ModItems.TUTORIAL_BOOTS);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.TUTORIAL_AXE);
            event.accept(ModItems.TUTORIAL_PICKAXE);
            event.accept(ModItems.TUTORIAL_SHOVEL);
            event.accept(ModItems.TUTORIAL_HOE);
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.TUTORIAL_BLOCK_ITEM);
        }
    }
}