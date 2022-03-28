package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import rena.toraracreatures.core.init.ItemInit;
import rena.toraracreatures.entities.TCWaterEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class LampreyEntity extends TCWaterEntity implements IAnimatable {

    public static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(LampreyEntity.class, DataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    public LampreyEntity(EntityType<? extends LampreyEntity> type, World world) {
        super(type, world);
        this.moveControl = new LampreyEntity.MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //this.goalSelector.addGoal(0, new LampreyEntity.ModAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());

    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.1D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected PathNavigator createNavigation(World p_175447_1_) {
        return new SwimmerPathNavigator(this, p_175447_1_);
    }

    @Override
    public void travel(Vector3d vec3d) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, vec3d);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(vec3d);
        }

    }

    public static class MoveHelperController extends MovementController
    {
        private final LampreyEntity lamprey;

        MoveHelperController(LampreyEntity entityIn)
        {
            super(entityIn);
            this.lamprey = entityIn;
        }

        public void tick()
        {
            if(this.lamprey.isEyeInFluid(FluidTags.WATER))
            {
                this.lamprey.setDeltaMovement(this.lamprey.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if(this.operation == MovementController.Action.MOVE_TO && !this.lamprey.getNavigation().isDone())
            {
                float f = (float)(this.speedModifier * this.lamprey.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.lamprey.setSpeed(MathHelper.lerp(0.125F, this.lamprey.getSpeed(), f));
                double d0 = this.wantedX - this.lamprey.getX();
                double d1 = this.wantedY - this.lamprey.getY();
                double d2 = this.wantedZ - this.lamprey.getZ();
                if(d1 != 0.0D)
                {
                    double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.lamprey.setDeltaMovement(this.lamprey.getDeltaMovement().add(0.0D, (double)this.lamprey.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if(d0 != 0.0D || d2 != 0.0D)
                {
                    float f1 = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.lamprey.yRot = this.rotlerp(this.lamprey.yRot, f1, 90.0F);
                    this.lamprey.yBodyRot = this.lamprey.yRot;
                }
            }
            else
            {
                this.lamprey.setSpeed(0.0F);
            }
        }
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    protected int getExperienceReward(PlayerEntity playerEntity)
    {
        return 1 + this.level.random.nextInt(3);
    }

    @Override
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
    public void baseTick()
    {
        int i = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(i);
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
    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }


    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected ItemStack getBucketItemStack() {
        return new ItemStack(ItemInit.LAMPREY_BUCKET.get());
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(this.isDeadOrDying() && !this.isInWater() && this.onGround)
        {
            if(level.isClientSide)
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lamprey.outofwater", false));
        }
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
}
