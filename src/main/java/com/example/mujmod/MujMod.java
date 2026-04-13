package com.example.mujmod;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
        ModEntities.ENTITY_TYPES.register(modBusGroup);
        BuildCreativeModeTabContentsEvent.BUS.addListener(this::addCreative);
        TickEvent.PlayerTickEvent.Post.BUS.addListener(TutorialArmorEffects::onPlayerTick);
        LevelEvent.CreateSpawnPosition.BUS.addListener(TutorialSpawnHandler::onCreateSpawnPosition);
        PlayerEvent.PlayerLoggedInEvent.BUS.addListener(TutorialSpawnHandler::onPlayerLoggedIn);
        EntityAttributeCreationEvent.BUS.addListener(event ->
                event.put(ModEntities.MINI_QUEEN.get(), MiniQueenEntity.createAttributes().build()));
        EntityAttributeCreationEvent.BUS.addListener(event ->
            event.put(ModEntities.MUJMOB.get(), MujMobEntity.createAttributes().build()));
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener(event ->
                event.registerLayerDefinition(MiniQueenModel.LAYER_LOCATION,
                        MiniQueenModel::createBodyLayer));
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener(event ->
            event.registerLayerDefinition(MujMobModel.LAYER_LOCATION,
            MujMobModel::createBodyLayer));
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(event ->
                event.registerEntityRenderer(ModEntities.MINI_QUEEN.get(),
                        MiniQueenRenderer::new));
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(event ->
            event.registerEntityRenderer(ModEntities.MUJMOB.get(),
            MujMobRenderer::new));

        LOGGER.info("MujMod byl načten!");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.MITHRIL_SWORD);
            event.accept(ModItems.MITHRIL_HELMET);
            event.accept(ModItems.MITHRIL_CHESTPLATE);
            event.accept(ModItems.MITHRIL_LEGGINGS);
            event.accept(ModItems.MITHRIL_BOOTS);
            event.accept(ModItems.MINI_QUEEN_SPAWN_EGG);
            event.accept(ModItems.MUJMOB_SPAWN_EGG);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.MITHRIL_AXE);
            event.accept(ModItems.MITHRIL_PICKAXE);
            event.accept(ModItems.MITHRIL_SHOVEL);
            event.accept(ModItems.MITHRIL_HOE);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RAW_MITHRIL);
            event.accept(ModItems.MITHRIL_INGOT);
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.MITHRIL_BLOCK_ITEM);
            event.accept(ModBlocks.MITHRIL_ORE_ITEM);
            for (var shelfItem : ModBlocks.SHELF_ITEMS) {
                event.accept(shelfItem);
            }
        }
    }
}