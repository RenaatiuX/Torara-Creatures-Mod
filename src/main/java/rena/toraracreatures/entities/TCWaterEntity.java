package rena.toraracreatures.entities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public abstract class TCWaterEntity extends AbstractFishEntity {

    public TCWaterEntity(EntityType<? extends TCWaterEntity> type, World world) {
        super(type, world);
    }


    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getBucketItemStack();
            this.saveToBucketTag(itemstack1);
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)playerEntity, itemstack1);
            }

            if (itemstack.isEmpty()) {
                playerEntity.setItemInHand(hand, itemstack1);
            } else if (!playerEntity.inventory.add(itemstack1)) {
                playerEntity.drop(itemstack1, false);
            }

            this.remove();
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(playerEntity, hand);
        }
    }

    protected void saveToBucketTag(ItemStack stack) {
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }

    }

    protected abstract ItemStack getBucketItemStack();


}
