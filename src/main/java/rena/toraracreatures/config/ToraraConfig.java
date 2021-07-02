package rena.toraracreatures.config;

import net.minecraftforge.fml.config.ModConfig;
import rena.toraracreatures.ToraraCreatures;

public class ToraraConfig {

    public static int dickinsoniaSpawnWeight = 7;
    public static int dickinsoniaSpawnRolls = 0;

    public static void bake(ModConfig config) {
        try {

            dickinsoniaSpawnWeight = ConfigHolder.COMMON.dickinsoniaSpawnWeight.get();
            dickinsoniaSpawnRolls = ConfigHolder.COMMON.dickinsoniaSpawnRolls.get();

        }catch (Exception e) {
            ToraraCreatures.LOGGER.warn("An exception was caused trying to load the config for Torara Creatures.");
            e.printStackTrace();
        }
    }

}
