package com.example.mujmod;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

public class MiniQueenModel<S extends ZombieRenderState> extends ZombieModel<S> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(MujMod.MOD_ID, "mini_queen"), "main");

    private final ModelPart extraHead;
    private final ModelPart firstHead;

    public MiniQueenModel(ModelPart root) {
        super(root);
        this.extraHead = root.getChild("extra_head");
        this.firstHead = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f);
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, CubeDeformation.NONE),
            PartPose.offset(-4.0f, 0.0f, 0.0f));
        root.addOrReplaceChild("hat",
            CubeListBuilder.create().texOffs(32, 0)
                .addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.5f)),
            PartPose.offset(-4.0f, 0.0f, 0.0f));
        // Dve hlavy vedle sebe, symetricky kolem stredu tela.
        root.addOrReplaceChild("extra_head",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, CubeDeformation.NONE),
            PartPose.offset(4.0f, 0.0f, 0.0f));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(S state) {
        super.setupAnim(state);
        // Druhá hlava kopíruje rotaci první hlavy
        this.extraHead.xRot = this.firstHead.xRot;
        this.extraHead.yRot = this.firstHead.yRot;
        this.extraHead.zRot = this.firstHead.zRot;
    }
}
