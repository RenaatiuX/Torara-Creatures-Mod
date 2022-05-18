package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import rena.toraracreatures.core.init.EntityInit;
import rena.toraracreatures.core.init.ItemInit;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class PheasantEntity extends AnimalEntity implements IAnimatable {

    public static final String EGG_LAY_TIME_TAG = "pheasantEggLayTime";
    public static final String VARIANT_TAG = "pheasantVariant";

    private static final DataParameter<Byte> VARIANT = EntityDataManager.defineId(PheasantEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> ANIMATION  = EntityDataManager.defineId(PheasantEntity.class, DataSerializers.BYTE);
    protected static final byte ANIMATION_IDLE = 0;

    private static final AnimationBuilder WALK_ANIM = new AnimationBuilder().addAnimation("Pheasant_Walk", true);
    private static final AnimationBuilder IDLE_ANIM = new AnimationBuilder().addAnimation("Pheasant_Idle", true);
    private static final AnimationBuilder FLY_ANIM = new AnimationBuilder().addAnimation("Pheasant_Flight", true);

    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT_SEEDS);
    private static final int MIN_EGG_LAY_TIME = 6000;
    private static final int MAX_EGG_LAY_TIME = 12000;
    private int eggLayTime;
    private boolean isFlapping;

    private AnimationFactory factory = new AnimationFactory(this);

    public PheasantEntity(EntityType<? extends PheasantEntity> p_i48568_1_, World p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
        eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        byte variant = (byte) random.nextInt(2); // Chooses between 0 and 1 randomly
        this.entityData.define(VARIANT, variant);
        this.entityData.define(ANIMATION, ANIMATION_IDLE);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        setVariant(compoundNBT.getByte(VARIANT_TAG));
        if (compoundNBT.contains(EGG_LAY_TIME_TAG)) {
            this.eggLayTime = compoundNBT.getInt(EGG_LAY_TIME_TAG);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putByte(VARIANT_TAG, getVariant());
        compoundNBT.putInt(EGG_LAY_TIME_TAG, eggLayTime);
    }

    public byte getVariant() {
        return entityData.get(VARIANT);
    }

    public void setVariant(byte variant) {
        entityData.set(VARIANT, variant);
    }

    public byte getAnimation() {
        return entityData.get(ANIMATION);
    }

    public void setAnimation(byte animation) {
        entityData.set(ANIMATION, animation);
    }

    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, FOOD_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {
            // Lay egg
            if (isAlive() && !isBaby() && --eggLayTime <= 0) {
                this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.spawnAtLocation(ItemInit.PHEASANT_EGG.get());
                this.eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
            }

            // Slow fall speed when flapping
            Vector3d velocity = this.getDeltaMovement();
            if (!this.onGround && velocity.y < 0.0D) {
                this.setDeltaMovement(velocity.multiply(1.0D, 0.6D, 1.0D));
            }

        }
            // Play flapping/fly animation when falling
            isFlapping = level.isClientSide && !isInWater() && !this.onGround;

    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }


    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageableEntity) {
        return EntityInit.PHEASANT.create(world);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 2, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        float limbSwingAmount = event.getLimbSwingAmount();
        boolean isMoving = !(limbSwingAmount > -0.05F && limbSwingAmount < 0.05F);
        AnimationController controller = event.getController();
        if (isFlapping) {
            controller.setAnimation(FLY_ANIM);
            return PlayState.CONTINUE;
        }
        byte currentAnimation = getAnimation();
        controller.setAnimation(isMoving ? WALK_ANIM : IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
