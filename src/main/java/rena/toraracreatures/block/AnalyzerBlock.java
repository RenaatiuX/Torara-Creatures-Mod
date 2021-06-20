package rena.toraracreatures.block;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import rena.toraracreatures.common.tileentity.AnalyzerTileEntity;

import javax.annotation.Nullable;

public class AnalyzerBlock extends AbstractMachineBlock {


    public AnalyzerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void openContainer(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileentity = world.getBlockEntity(pos);
        if(tileentity instanceof AnalyzerTileEntity)
        {
            player.openMenu((INamedContainerProvider)tileentity);
        }
    }


    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new AnalyzerTileEntity();    }
}
