package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import rena.toraracreatures.entities.ISemiAquatic;
import rena.toraracreatures.init.EntityInit;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;


public class DickinsoniaRexEntity extends AnimalEntity implements IAnimatable, ISemiAquatic {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.SAND);

    @Override
    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    protected final GroundPathNavigator groundNavigation;
    private AnimationFactory factory = new AnimationFactory(this);

    public DickinsoniaRexEntity(EntityType<? extends AnimalEntity> entity, World worldIn) {
        super(entity, worldIn);
        this.groundNavigation = new GroundPathNavigator(this, worldIn);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    public boolean checkSpawnObstruction(IWorldReader worldIn) {
        return worldIn.isUnobstructed(this);
    }

    protected void registerGoals() {

        this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new DickinsoniaRexEntity.GoToOceanGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public void updateSwimming() {
        if (!this.level.isClientSide) {
            if (!this.isEffectiveAi() || !this.isInWater()) {
                this.navigation = this.groundNavigation;
                this.setSwimming(false);
            }
        }
    }

    static class GoToOceanGoal extends MoveToBlockGoal {
        private final DickinsoniaRexEntity dickinsoniaRex;

        public GoToOceanGoal(DickinsoniaRexEntity dickinsonia, double val1) {
            super(dickinsonia, val1, 8, 2);
            this.dickinsoniaRex = dickinsonia;
        }

        public boolean canUse() {
            return super.canUse() && !this.dickinsoniaRex.level.isDay() && this.dickinsoniaRex.isInWater() && this.dickinsoniaRex.getY() >= (double)(this.dickinsoniaRex.level.getSeaLevel() - 3);
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }

        protected boolean isValidTarget(IWorldReader worldReader, BlockPos blockPos) {
            BlockPos blockpos = blockPos.above();
            return worldReader.isEmptyBlock(blockpos) && worldReader.isEmptyBlock(blockpos.above()) && worldReader.getBlockState(blockPos).entityCanStandOn(worldReader, blockPos, this.dickinsoniaRex);
        }

        public void start() {
            this.dickinsoniaRex.navigation = this.dickinsoniaRex.groundNavigation;
            super.start();
        }

        public void stop() {
            super.stop();
        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("Movement", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    @Override
    public DickinsoniaRexEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        return EntityInit.DICKINSONIA_REX.create(world);
    }

    @Override
    public boolean isPushedByFluid()
    {
        return false;
    }

    @Override
    public boolean canBeLeashed(PlayerEntity playerEntity)
    {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double doubleIn)
    {
        return false;
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }

    protected void handleAirSupply(int p_209207_1_)
    {
        if(this.isAlive() && !this.isInWaterOrBubble())
        {
            this.setAirSupply(p_209207_1_ - 1);
            if(this.getAirSupply() == -20)
            {
                this.setAirSupply(0);
                this.hurt(DamageSource.DROWN, 2.0F);
            }
        }
        else
        {
            this.setAirSupply(300);
        }
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            if(this.jumping){
                this.setDeltaMovement(this.getDeltaMovement().scale(1.4D));
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.72D, 0.0D));
            }else{
                this.setDeltaMovement(this.getDeltaMovement().scale(0.4D));
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.08D, 0.0D));
            }

        } else {
            super.travel(travelVector);
        }

    }

    @Override
    public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getFluidState(pos.below()).isEmpty() && worldIn.getFluidState(pos).is(FluidTags.WATER) ? 10.0F : super.getWalkTargetValue(pos, worldIn);
    }


    @Override
    public void baseTick()
    {
        int i = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(i);
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    public boolean shouldEnterWater() {
        return true;
    }

    @Override
    public boolean shouldLeaveWater() {
        return false;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    @Override
    public int getWaterSearchRange() {
        return 5;
    }

}
