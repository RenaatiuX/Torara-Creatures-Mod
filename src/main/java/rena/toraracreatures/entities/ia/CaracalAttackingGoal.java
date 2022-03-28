package rena.toraracreatures.entities.ia;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import rena.toraracreatures.entities.mobs.CaracalEntity;

import java.util.EnumSet;

public class CaracalAttackingGoal extends Goal {

    protected final CaracalEntity caracal;
    protected double speedModifier, baseSpeed;
    protected final boolean followingTargetEvenIfNotSeen;
    protected Path path;
    protected double pathedTargetX;
    protected double pathedTargetY;
    protected double pathedTargetZ;
    protected int ticksUntilNextPathRecalculation;
    protected int attackCooldown = 0;
    protected long lastCanUseCheck;

    public CaracalAttackingGoal(CaracalEntity caracal, double speed) {
        this.caracal = caracal;
        this.baseSpeed = speed;
        this.followingTargetEvenIfNotSeen = false;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Flag.JUMP));
    }

    public boolean canUse() {
        long i = this.caracal.level.getGameTime();
        if (i - this.lastCanUseCheck < 20L) {
            return false;
        } else {
            this.lastCanUseCheck = i;
            LivingEntity livingentity = this.caracal.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                this.path = this.caracal.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackRangeSqr(livingentity) >= this.caracal.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                }
            }
        }
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.caracal.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.caracal.getNavigation().isDone();
        } else if (!this.caracal.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
        }
    }

    public void start() {
        this.caracal.getNavigation().moveTo(this.path, this.speedModifier);
        this.caracal.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.attackCooldown = 0;
        this.caracal.setStalk(true);
        this.caracal.setPounce(false);
    }

    public void stop() {
        LivingEntity livingentity = this.caracal.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.caracal.setTarget((LivingEntity)null);
        }

        this.caracal.setAggressive(false);
        this.caracal.getNavigation().stop();
        this.caracal.setStalk(false);
        this.caracal.setPounce(false);
    }

    public void tick() {
        LivingEntity livingentity = this.caracal.getTarget();
        this.caracal.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
        double d0 = this.caracal.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
        if(d0 <= getNeedeDistanceToPounceSqr()){
            this.caracal.setStalk(false);
            this.caracal.setPounce(true);
            speedModifier = baseSpeed * 1.5;
            System.out.println("now it should be pouncing");
        }
        else if (d0 <= getNeededDistanzeToStalkSqr()){
            caracal.setPounce(false);
            caracal.setStalk(true);
            speedModifier = baseSpeed * 0.25;
            System.out.println("now it should be Stalking");
        }
        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
        if ((this.followingTargetEvenIfNotSeen || this.caracal.getSensing().canSee(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.caracal.getRandom().nextFloat() < 0.05F)) {
            this.pathedTargetX = livingentity.getX();
            this.pathedTargetY = livingentity.getY();
            this.pathedTargetZ = livingentity.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.caracal.getRandom().nextInt(7);
            if (d0 > 1024.0D) {
                this.ticksUntilNextPathRecalculation += 10;
            } else if (d0 > 256.0D) {
                this.ticksUntilNextPathRecalculation += 5;
            }

            if (!this.caracal.getNavigation().moveTo(livingentity, this.speedModifier)) {
                this.ticksUntilNextPathRecalculation += 15;
            }
        }

        this.attackCooldown = Math.max(this.attackCooldown - 1, 0);
        this.checkAndPerformAttack(livingentity, d0);
    }

    protected double getAttackRangeSqr(LivingEntity p_179512_1_) {
        return this.caracal.getBbWidth() * 2.0F * this.caracal.getBbWidth() * 2.0F + p_179512_1_.getBbWidth();
    }

    protected void checkAndPerformAttack(LivingEntity target, double distance) {
        double d0 = this.getAttackRangeSqr(target);
        if (distance <= d0 && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.caracal.swing(Hand.MAIN_HAND);
            this.caracal.doHurtTarget(target);
            //caracal.setPounce(false);
            //caracal.setStalk(false);
        }
    }

    protected void resetAttackCooldown() {
        this.attackCooldown = getAttackInterval();
    }

    protected boolean isTimeToAttack() {
        return this.attackCooldown <= 0;
    }

    /**
     *
     * @return cooldown between attacks in ticks
     */
    public int getAttackCooldown() {
        return this.attackCooldown;
    }


    public int getAttackInterval() {
        return 20;
    }

    public double getNeededDistanzeToStalkSqr(){
        return Math.pow(15, 2);
    }

    public double getNeedeDistanceToPounceSqr(){
        return Math.pow(5, 2);
    }
}
