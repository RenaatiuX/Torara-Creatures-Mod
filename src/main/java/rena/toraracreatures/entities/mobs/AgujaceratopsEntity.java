package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

public class AgujaceratopsEntity extends AnimalEntity implements IAngerable, IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(GreenlandSharkEntity.class, DataSerializers.INT);
    public static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(GreenlandSharkEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> EAT = EntityDataManager.defineId(GreenlandSharkEntity.class, DataSerializers.BOOLEAN);
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private UUID persistentAngerTarget;
    private int eatAnimationTick;
    private EatGrassGoal eatBlockGoal;

    protected AgujaceratopsEntity(EntityType<? extends AgujaceratopsEntity> entity, World worldIn) {
        super(entity, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.eatBlockGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new AgujaceratopsEntity.ModAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, this.eatBlockGoal);
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetAngerGoal<>(this, true));

    }

    @Override
    protected void customServerAiStep() {
        this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }

        super.aiStep();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleEntityEvent(p_70103_1_);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadEatPositionScale(float p_70894_1_) {
        if (this.eatAnimationTick <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTick < 4 ? ((float)this.eatAnimationTick - p_70894_1_) / 4.0F : -((float)(this.eatAnimationTick - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadEatAngleScale(float p_70890_1_) {
        if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36) {
            float f = ((float)(this.eatAnimationTick - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.eatAnimationTick > 0 ? ((float)Math.PI / 5F) : this.xRot * ((float)Math.PI / 180F);
        }
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
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
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
    protected int getExperienceReward(PlayerEntity playerEntity)
    {
        return 1 + this.level.random.nextInt(3);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size)
    {
        return size.height * 1.5F;
    }

    @Override
    public boolean removeWhenFarAway(double doubleIn)
    {
        return false;
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    public void setAttacking(boolean attacking)
    {
        this.entityData.set(ATTACKING, attacking);
    }

    public void setEating(boolean eating)
    {
        this.entityData.set(EAT, eating);
    }

    public boolean isAttacking()
    {
        return this.entityData.get(ATTACKING);
    }

    public boolean isEating()
    {
        return this.entityData.get(EAT);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.getEntityData().define(ATTACKING, Boolean.FALSE);
        this.getEntityData().define(EAT, Boolean.FALSE);
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
        private final AgujaceratopsEntity entity;
        @SuppressWarnings("unused")
        private int attackStep;

        public ModAttackGoal(AgujaceratopsEntity entityIn, double speedIn, boolean useMemory)
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
                AgujaceratopsEntity.this.setAttacking(false);
            }
            else if(distToEnemySqr <= d0 * 2.0D)
            {
                if(this.isTimeToAttack())
                {
                    AgujaceratopsEntity.this.setAttacking(false);
                    this.resetAttackCooldown();
                }

                if(this.getTicksUntilNextAttack() <= 10)
                {
                    AgujaceratopsEntity.this.setAttacking(true);
                }
            }
            else
            {
                this.resetAttackCooldown();
                AgujaceratopsEntity.this.setAttacking(false);
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
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.agujaceratops.attack", true));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.agujaceratops.walk", true));
            return PlayState.CONTINUE;
        }
        if(isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.agujaceratops.swim", true));
            return PlayState.CONTINUE;
        }
        if(this.entityData.get(AgujaceratopsEntity.EAT)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.agujaceratops.eat", false));
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
