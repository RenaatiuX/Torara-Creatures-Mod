package rena.toraracreatures.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.tileentity.AnalyzerTileEntity;

public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ToraraCreatures.MOD_ID);

    public static final RegistryObject<TileEntityType<AnalyzerTileEntity>> ANALYZER_TILE_ENTITY = TILE_ENTITY.register("analyser_tile_entity", () -> TileEntityType.Builder.of(AnalyzerTileEntity::new, BlockInit.ANALYZER.get()).build(null));

}
