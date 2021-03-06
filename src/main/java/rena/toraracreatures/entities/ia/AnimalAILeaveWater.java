package rena.toraracreatures.entities.ia;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import rena.toraracreatures.entities.ISemiAquatic;

import java.util.EnumSet;


public class AnimalAILeaveWater extends Goal {
    private final CreatureEntity creature;
    private BlockPos targetPos;
    private int executionChance = 30;

    public AnimalAILeaveWater(CreatureEntity creature) {
        this.creature = creature;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        if (this.creature.level.getFluidState(this.creature.blockPosition()).is(FluidTags.WATER) && (this.creature.getTarget() != null || this.creature.getRandom().nextInt(executionChance) == 0)){
            if(this.creature instanceof ISemiAquatic && ((ISemiAquatic) this.creature).shouldLeaveWater()){
                targetPos = generateTarget();
                return targetPos != null;
            }
        }
        return false;
    }

    public void start() {
        if(targetPos != null){
            this.creature.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
        }
    }

    public void tick() {
        if(targetPos != null){
            this.creature.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
        }
        if(this.creature.horizontalCollision && this.creature.isInWater()){
            float f1 = creature.yRot * ((float)Math.PI / 180F);
            creature.setDeltaMovement(creature.getDeltaMovement().add(-MathHelper.sin(f1) * 0.2F, 0.1D, MathHelper.cos(f1) * 0.2F));

        }
    }

    public boolean canContinueToUse() {
        if(this.creature instanceof ISemiAquatic && !((ISemiAquatic) this.creature).shouldLeaveWater()){
            this.creature.getNavigation().stop();
            return false;
        }
        return !this.creature.getNavigation().isDone() && targetPos != null && !this.creature.level.getFluidState(targetPos).is(FluidTags.WATER);
    }

    public BlockPos generateTarget() {
        Vector3d vector3d = RandomPositionGenerator.getLandPos(this.creature, 23, 7);
        int tries = 0;
        while(vector3d != null && tries < 8){
            boolean waterDetected = false;
            for(BlockPos blockpos1 : BlockPos.betweenClosed(MathHelper.floor(vector3d.x - 2.0D), MathHelper.floor(vector3d.y - 1.0D), MathHelper.floor(vector3d.z - 2.0D), MathHelper.floor(vector3d.x + 2.0D), MathHelper.floor(vector3d.y), MathHelper.floor(vector3d.z + 2.0D))) {
                if (this.creature.level.getFluidState(blockpos1).is(FluidTags.WATER)) {
                    waterDetected = true;
                    break;
                }
            }
            if(waterDetected){
                vector3d = RandomPositionGenerator.getLandPos(this.creature, 23, 7);
            }else{
                return new BlockPos(vector3d);
            }
            tries++;
        }
        return null;
    }
}
