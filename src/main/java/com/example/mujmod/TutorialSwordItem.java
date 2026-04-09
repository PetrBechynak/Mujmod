package com.example.mujmod;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TutorialSwordItem extends Item {

    public TutorialSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0), attacker);
        attacker.heal(1.0F);
        super.postHurtEnemy(stack, target, attacker);
    }
}