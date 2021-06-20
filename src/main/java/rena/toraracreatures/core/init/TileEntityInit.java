package rena.toraracreatures.core.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.tileentity.AnalyzerTileEntity;

public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ToraraCreatures.MOD_ID);

    public static final RegistryObject<TileEntityType<AnalyzerTileEntity>> ANALYZER = TILE_ENTITY_TYPES.register("analyzer_table_tile_entity", () -> TileEntityType.Builder.of(AnalyzerTileEntity::new, BlockInit.ANALYZER.get()).build(null));


}
