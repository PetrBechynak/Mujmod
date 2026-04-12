package com.example.mujmod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class MiniQueenEntity extends Zombie {

    private BlockPos lastValidTutorialBlock;

    public MiniQueenEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.SCALE, 2.5 / 1.95);
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        BlockPos below = this.blockPosition().below();
        BlockState belowState = this.level().getBlockState(below);
        
        if (belowState.is(ModBlocks.TUTORIAL_BLOCK.get())) {
            // Na validním blocku - zapamatuju si jej
            this.lastValidTutorialBlock = below;
        } else {
            // Mimo tutorial_block - vrátit zpět
            if (this.lastValidTutorialBlock != null) {
                this.setPos(this.lastValidTutorialBlock.getX() + 0.5,
                        this.lastValidTutorialBlock.getY() + 1,
                        this.lastValidTutorialBlock.getZ() + 0.5);
            } else {
                // Nikdy nebyla validně umístěna - zemřít
                this.setHealth(0.0f);
            }
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, EntitySpawnReason reason) {
        BlockPos below = this.blockPosition().below();
        return level.getBlockState(below).is(ModBlocks.TUTORIAL_BLOCK.get());
    }
}
