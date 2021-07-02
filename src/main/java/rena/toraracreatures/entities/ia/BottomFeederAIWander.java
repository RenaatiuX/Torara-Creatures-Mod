package rena.toraracreatures.entities.ia;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import rena.toraracreatures.entities.ISemiAquatic;

import javax.annotation.Nullable;
import java.util.Random;

public class BottomFeederAIWander extends RandomWalkingGoal {

    private int waterChance = 0;
    private int landChance = 0;
    private int range = 5;

    public BottomFeederAIWander(CreatureEntity creature, double speed, int waterChance, int landChance) {
        super(creature, speed, waterChance);
        this.waterChance = waterChance;
        this.landChance = landChance;
    }

    public BottomFeederAIWander(CreatureEntity creature, double speed, int waterChance, int landChance, int range) {
        super(creature, speed, waterChance);
        this.waterChance = waterChance;
        this.landChance = landChance;
        this.range = range;
    }

    public boolean canUse(){
        if(this.mob instanceof ISemiAquatic && ((ISemiAquatic) this.mob).shouldStopMoving()){
            return false;
        }
        this.interval = this.mob.isInWater() ? waterChance : landChance;
        return super.canUse();
    }

    public boolean canContinueToUse() {
        if(this.mob instanceof ISemiAquatic && ((ISemiAquatic) this.mob).shouldStopMoving()){
            return false;
        }
        return super.canContinueToUse();
    }

    @Nullable
    protected Vector3d getPosition() {
        if(this.mob.isInWater()) {
            BlockPos blockpos = null;
            Random random = new Random();
            for (int i = 0; i < 15; i++) {
                BlockPos blockpos1 = this.mob.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
                while ((this.mob.level.isEmptyBlock(blockpos1) || this.mob.level.getFluidState(blockpos1).is(FluidTags.WATER)) && blockpos1.getY() > 1) {
                    blockpos1 = blockpos1.below();
                }
                if (isBottomOfSeafloor(this.mob.level, blockpos1.above())) {
                    blockpos = blockpos1;
                }
            }

            return blockpos != null ? new Vector3d(blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F) : null;
        }else{
            return super.getPosition();

        }
    }

    private boolean isBottomOfSeafloor(IWorld world, BlockPos pos){
        return world.getFluidState(pos).is(FluidTags.WATER) && world.getFluidState(pos.below()).isEmpty() && world.getBlockState(pos.below()).canOcclude();
    }
}
