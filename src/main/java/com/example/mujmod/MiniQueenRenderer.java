package com.example.mujmod;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

public class MiniQueenRenderer extends HumanoidMobRenderer<MiniQueenEntity, ZombieRenderState, MiniQueenModel<ZombieRenderState>> {

    private static final Identifier MINI_QUEEN_TEXTURE =
            Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "textures/entity/mini_queen.png");

    public MiniQueenRenderer(EntityRendererProvider.Context context) {
        super(context,
                new MiniQueenModel<>(context.bakeLayer(MiniQueenModel.LAYER_LOCATION)),
                0.5f);
    }

    @Override
    public ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState state) {
        return MINI_QUEEN_TEXTURE;
    }
}
