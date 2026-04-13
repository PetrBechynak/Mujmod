package com.example.mujmod;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class MujMobRenderer extends MobRenderer<MujMobEntity, LivingEntityRenderState, MujMobModel> {

    private static final Identifier MUJMOB_TEXTURE =
            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "textures/entity/mujmob.png");

    public MujMobRenderer(EntityRendererProvider.Context context) {
        super(context, new MujMobModel(context.bakeLayer(MujMobModel.LAYER_LOCATION)), 0.9F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return MUJMOB_TEXTURE;
    }

    @Override
    protected void scale(LivingEntityRenderState state, PoseStack poseStack) {
        poseStack.scale(2.0F, 2.0F, 2.0F);
    }
}