package rena.toraracreatures.common.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;
import rena.toraracreatures.block.AnalyzerBlock;
import rena.toraracreatures.common.tileentity.AnalyzerTileEntity;
import rena.toraracreatures.core.init.ContainerInit;

public class AnalyzerContainer extends Container
{
    private final IWorldPosCallable canInteractWithCallable;
    private final AnalyzerTileEntity tileEntity;

    public AnalyzerContainer(final int windowID, final PlayerInventory playerInventory, final AnalyzerTileEntity tileEntity)
    {
        super(ContainerInit.ANALYZER_CONTAINER.get(), windowID);

        this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
        this.tileEntity = tileEntity;

        int playerX = 8;
        int playerY = 84;
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(playerInventory, i, playerX + i * 18, playerY + 58));
        for (int i = 0; i < 3; i++)
            for (int k = 0; k < 9; k++)
                this.addSlot(new Slot(playerInventory, k + i * 9 + 9, playerX + k * 18, playerY + i * 18));

        this.addSlot(new SlotItemHandler(this.tileEntity.getItemStackHandler(), AnalyzerTileEntity.SLOT_FOSSIL, 36, 42) {

            @Override
            public boolean mayPlace(@Nonnull ItemStack stack)
            {
                return super.mayPlace(stack) && tileEntity.isFossilStack(stack);
            }
        });

        int resultX = 92;
        int resultY = 23;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                this.addSlot(new AnalyzerResultSlot(playerInventory.player, this.tileEntity.getInventory(), AnalyzerTileEntity.SLOT_RESULTS[0] + j * 3 + i, resultX + i * 18, resultY + j * 18));

        this.addDataSlots(this.tileEntity.getIntArray());
    }

    public AnalyzerContainer(final int windowID, final PlayerInventory playerInventory, final PacketBuffer data)
    {
        this(windowID, playerInventory, AnalyzerContainer.getTileEntity(playerInventory, data));
    }

    private static AnalyzerTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data)
    {
        Objects.requireNonNull(playerInventory, "Error: " + AnalyzerContainer.class.getSimpleName() + " - Player Inventory cannot be null!");
        Objects.requireNonNull(data, "Error: " + AnalyzerContainer.class.getSimpleName() + " - Packer Buffer Data cannot be null!");

        final TileEntity tileEntityAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileEntityAtPos instanceof AnalyzerTileEntity)
            return (AnalyzerTileEntity) tileEntityAtPos;

        throw new IllegalStateException("Error: " + AnalyzerContainer.class.getSimpleName() + " - TileEntity is not corrent! " + tileEntityAtPos);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn)
    {
        return this.canInteractWithCallable.evaluate((world, blockPos) ->
                world.getBlockState(blockPos).getBlock() instanceof AnalyzerBlock && playerIn.distanceToSqr((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
    {
        int playerInvSize = this.slots.size() - this.tileEntity.getInventorySize();

        Slot sourceSlot = this.slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index >= playerInvSize)
        {
            if (!this.moveItemStackTo(sourceStack, 0, playerInvSize, false))
                return ItemStack.EMPTY;
        }
        else
        {
            if (this.tileEntity.isFossilStack(sourceStack))
            {
                int slotIndex = AnalyzerTileEntity.SLOT_FOSSIL;
                if (!this.moveItemStackTo(sourceStack, playerInvSize + slotIndex, playerInvSize + slotIndex + 1, false))
                    return ItemStack.EMPTY;
            }
            else
                return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty())
            sourceSlot.set(ItemStack.EMPTY);
        else
            sourceSlot.setChanged();

        if (sourceStack.getCount() == copyOfSourceStack.getCount())
            return ItemStack.EMPTY;

        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getWorkProgressionScaled(int size)
    {
        return this.tileEntity.getWorkProgressionScaled(size);
    }

    public class AnalyzerResultSlot extends Slot
    {
        private final PlayerEntity player;
        private int removeCount;

        public AnalyzerResultSlot(PlayerEntity player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
        {
            super(inventoryIn, slotIndex, xPosition, yPosition);
            this.player = player;
        }

        @Override
        public boolean mayPlace(ItemStack stack)
        {
            return false;
        }

        @Override
        public ItemStack remove(int amount)
        {
            if (this.hasItem())
            {
                this.removeCount += Math.min(amount, this.getItem().getCount());
            }

            return super.remove(amount);
        }

        @Override
        public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack)
        {
            this.checkTakeAchievements(stack);
            super.onTake(thePlayer, stack);
            return stack;
        }

        @Override
        protected void onQuickCraft(ItemStack stack, int amount)
        {
            this.removeCount += amount;
            this.checkTakeAchievements(stack);
        }

        @Override
        protected void checkTakeAchievements(ItemStack stack)
        {
            stack.onCraftedBy(this.player.level, this.player, this.removeCount);
            if (!this.player.level.isClientSide && this.container instanceof AnalyzerTileEntity)
                ((AnalyzerTileEntity) this.container).givePlayerXP(player, this.removeCount);

            this.removeCount = 0;
        }
    }

}
