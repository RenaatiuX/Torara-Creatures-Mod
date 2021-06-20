package rena.toraracreatures.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rena.toraracreatures.entities.WallFossilEntity;

public class TCPainting extends Item {

    public TCPainting(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext p_195939_1_) {
        BlockPos blockpos = p_195939_1_.getClickedPos();
        Direction direction = p_195939_1_.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        PlayerEntity playerentity = p_195939_1_.getPlayer();
        ItemStack itemstack = p_195939_1_.getItemInHand();
        if (playerentity != null && !this.mayPlace(playerentity, direction, itemstack, blockpos1)) {
            return ActionResultType.FAIL;
        } else {
            World world = p_195939_1_.getLevel();
            WallFossilEntity entity = new WallFossilEntity(world, blockpos1, direction);

            CompoundNBT compoundnbt = itemstack.getTag();
            if (compoundnbt != null) {
                EntityType.updateCustomEntityTag(world, playerentity, entity, compoundnbt);
            }

            if (entity.survives()) {
                if (!world.isClientSide) {
                    entity.playPlacementSound();
                    world.addFreshEntity(entity);
                }

                itemstack.shrink(1);
                return ActionResultType.sidedSuccess(world.isClientSide);
            } else {
                return ActionResultType.CONSUME;
            }
        }
    }

    protected boolean mayPlace(PlayerEntity p_200127_1_, Direction p_200127_2_, ItemStack p_200127_3_, BlockPos p_200127_4_) {
        return !p_200127_2_.getAxis().isVertical() && p_200127_1_.mayUseItemAt(p_200127_4_, p_200127_2_, p_200127_3_);
    }
}
