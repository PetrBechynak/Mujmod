package com.example.mujmod;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

public final class TutorialArmorEffects {

    private TutorialArmorEffects() {
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent.Post event) {
        Player player = event.player();
        if (event.side().isClient() || !isWearingFullTutorialSet(player)) {
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 220, 1, true, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.HASTE, 220, 1, true, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 220, 0, true, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 220, 0, true, false, true));
    }

    private static boolean isWearingFullTutorialSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.MITHRIL_HELMET.get()
            && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.MITHRIL_CHESTPLATE.get()
            && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.MITHRIL_LEGGINGS.get()
            && player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.MITHRIL_BOOTS.get();
    }
}