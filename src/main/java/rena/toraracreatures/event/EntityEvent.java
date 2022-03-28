package rena.toraracreatures.event;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import rena.toraracreatures.config.ToraraConfig;
import rena.toraracreatures.core.init.EntityInit;

public class EntityEvent {

    public static void onBiomesLoad(BiomeLoadingEvent event){
        if (event.getName() == null)
            return;
        MobSpawnInfoBuilder spawns = event.getSpawns();
        if (event.getCategory().equals(Biome.Category.OCEAN) && ToraraConfig.dickinsoniaSpawnWeight > 0) {
            spawns.getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(EntityInit.DICKINSONIA_REX, ToraraConfig.dickinsoniaSpawnWeight, 3, 5));
        }
    }
}
