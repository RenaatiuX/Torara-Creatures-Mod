package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class GreenlandSharkEntity extends WaterMobEntity implements IAngerable, IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(GreenlandSharkEntity.class, DataSerializers.INT);
    public static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(GreenlandSharkEntity.class, DataSerializers.BOOLEAN);
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private UUID persistentAngerTarget;

    public GreenlandSharkEntity(EntityType<? extends WaterMobEntity> entity, World world) {
        super(entity, world);
        this.moveControl = new GreenlandSharkEntity.MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new ModAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetAngerGoal<>(this, true));

    }

    @Override
    public boolean hurt(DamageSource damage, float val1) {
        if (this.isInvulnerableTo(damage)) {
            return false;
        } else {
            Entity entity = damage.getEntity();
            if (entity != null && !(entity instanceof PlayerEntity)) {
                val1 = (val1 + 1.0F) / 2.0F;
            }

            return super.hurt(damage, val1);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = entity.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entity);
        }

        return flag;
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
        private final GreenlandSharkEntity greenlandShark;

        MoveHelperController(GreenlandSharkEntity entityIn)
        {
            super(entityIn);
            this.greenlandShark = entityIn;
        }

        public void tick()
        {
            if(this.greenlandShark.isEyeInFluid(FluidTags.WATER))
            {
                this.greenlandShark.setDeltaMovement(this.greenlandShark.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if(this.operation == MovementController.Action.MOVE_TO && !this.greenlandShark.getNavigation().isDone())
            {
                float f = (float)(this.speedModifier * this.greenlandShark.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.greenlandShark.setSpeed(MathHelper.lerp(0.125F, this.greenlandShark.getSpeed(), f));
                double d0 = this.wantedX - this.greenlandShark.getX();
                double d1 = this.wantedY - this.greenlandShark.getY();
                double d2 = this.wantedZ - this.greenlandShark.getZ();
                if(d1 != 0.0D)
                {
                    double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.greenlandShark.setDeltaMovement(this.greenlandShark.getDeltaMovement().add(0.0D, (double)this.greenlandShark.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if(d0 != 0.0D || d2 != 0.0D)
                {
                    float f1 = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.greenlandShark.yRot = this.rotlerp(this.greenlandShark.yRot, f1, 90.0F);
                    this.greenlandShark.yBodyRot = this.greenlandShark.yRot;
                }
            }
            else
            {
                this.greenlandShark.setSpeed(0.0F);
            }
        }
    }



    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int val1) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, val1);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID val1) {
        this.persistentAngerTarget = val1;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
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
    protected float getStandingEyeHeight(Pose pose, EntitySize size)
    {
        return size.height * 0.8F;
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

    public void setAttacking(boolean attacking)
    {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking()
    {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.getEntityData().define(ATTACKING, Boolean.FALSE);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt)
    {
        super.addAdditionalSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt)
    {
        super.readAdditionalSaveData(nbt);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag)
        {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    class ModAttackGoal extends MeleeAttackGoal
    {
        private final GreenlandSharkEntity entity;
        @SuppressWarnings("unused")
        private int attackStep;

        public ModAttackGoal(GreenlandSharkEntity entityIn, double speedIn, boolean useMemory)
        {
            super(entityIn, speedIn, useMemory);
            this.entity = entityIn;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        protected void checkAndPerformAttack(LivingEntity entity, double distToEnemySqr)
        {
            double d0 = this.getAttackReachSqr(entity);
            if(distToEnemySqr <= d0 && this.isTimeToAttack())
            {
                this.resetAttackCooldown();
                this.entity.attackEntityAsMob(entity);
                GreenlandSharkEntity.this.setAttacking(false);
            }
            else if(distToEnemySqr <= d0 * 2.0D)
            {
                if(this.isTimeToAttack())
                {
                    GreenlandSharkEntity.this.setAttacking(false);
                    this.resetAttackCooldown();
                }

                if(this.getTicksUntilNextAttack() <= 10)
                {
                    GreenlandSharkEntity.this.setAttacking(true);
                }
            }
            else
            {
                this.resetAttackCooldown();
                GreenlandSharkEntity.this.setAttacking(false);
            }
        }

        public boolean canUse()
        {
            LivingEntity livingentity = this.entity.getTarget();
            return livingentity != null && livingentity.isAlive() && this.entity.canAttack(livingentity);
        }

        public void start()
        {
            this.attackStep = 0;
        }

        public void stop()
        {
            super.stop();
            this.entity.setAttacking(false);
        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(this.entityData.get(GreenlandSharkEntity.ATTACKING)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.greenlandshark.attack.new", true));
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Swim", true));
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
