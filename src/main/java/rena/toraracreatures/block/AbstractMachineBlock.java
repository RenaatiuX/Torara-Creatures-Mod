package rena.toraracreatures.block;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import rena.toraracreatures.common.tileentity.AnalyzerTileEntity;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public abstract class AbstractMachineBlock extends Block implements ITileEntityProvider {

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ON = BooleanProperty.create("on");

    public AbstractMachineBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON, Boolean.valueOf(false)).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction)
    {
        return state.setValue(HORIZONTAL_FACING, direction.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int i1, int i2)
    {
        super.triggerEvent(state, world, pos, i1, i2);
        TileEntity tileentity = world.getBlockEntity(pos);
        return tileentity == null ? false : tileentity.triggerEvent(i1, i2);
    }

    @Nullable
    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World world, BlockPos pos)
    {
        TileEntity tileentity = world.getBlockEntity(pos);
        return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider)tileentity : null;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result)
    {
        if(world.isClientSide)
        {
            return ActionResultType.SUCCESS;
        }
        else
        {
            this.openContainer(world, pos, player);
            return ActionResultType.CONSUME;
        }
    }

    protected abstract void openContainer(World world, BlockPos pos, PlayerEntity player);

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack)
    {
        if(stack.hasCustomHoverName())
        {
            TileEntity tileentity = world.getBlockEntity(pos);
            if(tileentity instanceof AnalyzerTileEntity)
            {
                ((AnalyzerTileEntity)tileentity).setCustomName(stack.getHoverName());
            }

        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean b)
    {
        if(!state.is(newState.getBlock()))
        {
            TileEntity tileentity = world.getBlockEntity(pos);
            if(tileentity instanceof AnalyzerTileEntity)
            {
                InventoryHelper.dropContents(world, pos, (AnalyzerTileEntity)tileentity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, World world, BlockPos pos)
    {
        return Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, ON);
    }
}
