package com.example.mujmod;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class MujMobModel extends EntityModel<LivingEntityRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION =
                        new ModelLayerLocation(Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mujmob"), "main");

        public MujMobModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition bbMain = partDefinition.addOrReplaceChild("bb_main",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        bbMain.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.0112F, -0.0413F, -0.384F));

        bbMain.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 14)
                        .addBox(-0.8063F, -2.7245F, -1.2951F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, 0.1756F, 0.3462F, -0.1165F));

        bbMain.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(8, 14)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
                this.resetPose();
    }
}