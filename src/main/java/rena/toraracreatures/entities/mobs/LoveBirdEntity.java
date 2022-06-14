package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import rena.toraracreatures.entities.enums.LoveBirdVariant;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class LoveBirdEntity extends AnimalEntity implements IFlyingAnimal, IAnimatable {

    private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.defineId(LoveBirdEntity.class, DataSerializers.INT);
    private AnimationFactory factory = new AnimationFactory(this);
    public LoveBirdEntity(EntityType<? extends LoveBirdEntity> p_i48568_1_, World p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
        this.moveControl = new FlyingMovementController(this, 10, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity
                .createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FLYING_SPEED, 0.4F)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected PathNavigator createNavigation(World p_175447_1_) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, p_175447_1_);
        return flyingpathnavigator;
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.6F;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.entityData.set(DATA_VARIANT_ID, p_70037_1_.getInt("Variant"));
    }

    //VARIANTS
    public LoveBirdVariant getVariant() {
        return LoveBirdVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    private void setVariant(LoveBirdVariant variant) {
        this.entityData.set(DATA_VARIANT_ID, variant.getId() & 255);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        LoveBirdVariant variant = Util.getRandom(LoveBirdVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lovebird.fly", true));
            return PlayState.CONTINUE;
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
