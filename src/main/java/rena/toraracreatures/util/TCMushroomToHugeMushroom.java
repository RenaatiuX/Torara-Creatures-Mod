package rena.toraracreatures.util;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import rena.toraracreatures.util.config.TCMushroomConfig;
import rena.toraracreatures.world.features.TCConfiguredFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class TCMushroomToHugeMushroom {

    public static class DeathCap extends TCHugeMushroom {
        @Nullable
        public ConfiguredFeature<TCMushroomConfig, ?> getHugeMushroomFeature(Random random) {
            return (random.nextInt(2) == 0) ? TCConfiguredFeatures.DEATH_CAP_MUSHROOM_HUGE2 : TCConfiguredFeatures.DEATH_CAP_MUSHROOM_HUGE3;
        }
    }

}
