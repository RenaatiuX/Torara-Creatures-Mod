package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import rena.toraracreatures.core.init.EntityInit;
import rena.toraracreatures.entities.ia.CaracalAttackingGoal;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class CaracalEntity extends TameableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public static final DataParameter<Boolean> STALK = EntityDataManager.defineId(CaracalEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> POUNCE = EntityDataManager.defineId(CaracalEntity.class, DataSerializers.BOOLEAN);

    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.CHICKEN);

    public static final Predicate<LivingEntity> PREY_SELECTOR = entity -> {
        EntityType<?> entitytype = entity.getType();
        return entitytype == EntityType.CHICKEN;
    };

    private static final Predicate<Entity> STALKABLE_PREY = (p_213498_0_) -> p_213498_0_ instanceof ChickenEntity;

    public CaracalEntity(EntityType<? extends CaracalEntity> p_i48574_1_, World p_i48574_2_) {
        super(p_i48574_1_, p_i48574_2_);
        this.setTame(false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, FOOD_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new CaracalAttackingGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));/*{
            public boolean canUse(){
                return !CaracalEntity.this.isInSittingPose() && super.canUse();
            }
        });*/
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STALK, false);
        this.entityData.define(POUNCE, false);
    }

    public void setStalk(boolean stalk){
        this.entityData.set(STALK, stalk);
    }

    public boolean isStalk(){
        return this.entityData.get(STALK);
    }

    public void setPounce(boolean pounce){
        this.entityData.set(POUNCE, pounce);
    }

    public boolean isPounce(){
        return this.entityData.get(POUNCE);
    }

    @Override
    public boolean isFood(ItemStack p_70877_1_) {
        return FOOD_ITEMS.test(p_70877_1_);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        CaracalEntity caracalEntity = EntityInit.CARACAL.create(world);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            caracalEntity.setOwnerUUID(uuid);
            caracalEntity.setTame(true);
        }

        return caracalEntity;
    }

    @Override
    public boolean canMate(AnimalEntity animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof CaracalEntity)) {
            return false;
        } else {
            CaracalEntity caracalEntity = (CaracalEntity)animal;
            if (!caracalEntity.isTame()) {
                return false;
            } else if (caracalEntity.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && caracalEntity.isInLove();
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return this.isBaby() ? p_213348_2_.height * 0.5F : 0.5F;
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(p_230254_1_) || this.isTame() || item == Items.BONE && !this.isTame();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!p_230254_1_.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) item.getFoodProperties().getNutrition());
                    return ActionResultType.SUCCESS;
                }

                ActionResultType actionresulttype = super.mobInteract(p_230254_1_, p_230254_2_);
                    if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(p_230254_1_)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.navigation.stop();
                        this.setTarget(null);
                        return ActionResultType.SUCCESS;
                    }
                    return actionresulttype;

            } else if (item == Items.BONE) {
                if (!p_230254_1_.abilities.instabuild) {
                    itemstack.shrink(1);
                }
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
                    this.tame(p_230254_1_);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                return ActionResultType.SUCCESS;
            }
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        //System.out.println("stalking:" + this.entityData.get(CaracalEntity.STALK));
        //System.out.println("pounce:" + this.entityData.get(CaracalEntity.POUNCE));
        if(isInSittingPose()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Caracal_SitIdle", true));
            return PlayState.CONTINUE;
        }
        if(this.entityData.get(CaracalEntity.STALK)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Caracal_Stalk", false));
            return PlayState.CONTINUE;
        }
        if(this.entityData.get(CaracalEntity.POUNCE)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Caracal_Pounce", false));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Caracal_Walk", true));
            return PlayState.CONTINUE;

        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("Caracal_Idle", true));
        return PlayState.CONTINUE;

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
