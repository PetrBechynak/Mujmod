package com.example.mujmod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MujMod.MOD_ID)
public class MujMod {

    public static final String MOD_ID = "mujmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MujMod(FMLJavaModLoadingContext context) {
        BusGroup modBusGroup = context.getModBusGroup();

        ModItems.ITEMS.register(modBusGroup);
        BuildCreativeModeTabContentsEvent.BUS.addListener(this::addCreative);

        LOGGER.info("MujMod byl načten!");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.TUTORIAL_SWORD);
        }
    }
}
