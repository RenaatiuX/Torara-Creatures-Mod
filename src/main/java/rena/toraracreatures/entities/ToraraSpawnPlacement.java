package rena.toraracreatures.entities;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;
import rena.toraracreatures.core.init.EntityInit;
import rena.toraracreatures.entities.mobs.DickinsoniaRexEntity;

public class ToraraSpawnPlacement {

    public static void spawnPlacement() {
        EntitySpawnPlacementRegistry.register(EntityInit.DICKINSONIA_REX, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DickinsoniaRexEntity::canDickinsoniaSpawn);
    }

}
