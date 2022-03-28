package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
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

public class ManateeEntity extends WaterMobEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);


    public ManateeEntity(EntityType<? extends WaterMobEntity> p_i48565_1_, World p_i48565_2_) {
        super(p_i48565_1_, p_i48565_2_);
        this.moveControl = new ManateeEntity.MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));

    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.MOVEMENT_SPEED, 0.1D);
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

    @Override
    protected PathNavigator createNavigation(World p_175447_1_) {
        return new SwimmerPathNavigator(this, p_175447_1_);
    }

    public static class MoveHelperController extends MovementController
    {
        private final ManateeEntity manatee;

        MoveHelperController(ManateeEntity entityIn)
        {
            super(entityIn);
            this.manatee = entityIn;
        }

        public void tick()
        {
            if(this.manatee.isEyeInFluid(FluidTags.WATER))
            {
                this.manatee.setDeltaMovement(this.manatee.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if(this.operation == MovementController.Action.MOVE_TO && !this.manatee.getNavigation().isDone())
            {
                float f = (float)(this.speedModifier * this.manatee.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.manatee.setSpeed(MathHelper.lerp(0.125F, this.manatee.getSpeed(), f));
                double d0 = this.wantedX - this.manatee.getX();
                double d1 = this.wantedY - this.manatee.getY();
                double d2 = this.wantedZ - this.manatee.getZ();
                if(d1 != 0.0D)
                {
                    double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.manatee.setDeltaMovement(this.manatee.getDeltaMovement().add(0.0D, (double)this.manatee.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if(d0 != 0.0D || d2 != 0.0D)
                {
                    float f1 = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.manatee.yRot = this.rotlerp(this.manatee.yRot, f1, 90.0F);
                    this.manatee.yBodyRot = this.manatee.yRot;
                }
            }
            else
            {
                this.manatee.setSpeed(0.0F);
            }
        }
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }

    @Override
    public boolean removeWhenFarAway(double doubleIn)
    {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Manatee_ActiveSwimHurt", true));
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Manatee_IdleSwim", true));
        }
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
