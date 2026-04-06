package com.example.mujmod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MujMod.MOD_ID)
public class MujMod {

    public static final String MOD_ID = "mujmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MujMod(FMLJavaModLoadingContext context) {
        LOGGER.info("MujMod byl načten!");
    }
}
