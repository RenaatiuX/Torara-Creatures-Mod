package rena.toraracreatures.world.features;

import net.minecraft.world.gen.feature.Feature;
import rena.toraracreatures.common.mushrooms.deathcap.DeathCapMushroom1;
import rena.toraracreatures.common.mushrooms.deathcap.DeathCapMushroom2;
import rena.toraracreatures.common.mushrooms.deathcap.DeathCapMushroom3;
import rena.toraracreatures.util.TCAbstractMushroomFeature;
import rena.toraracreatures.util.config.TCMushroomConfig;

import java.util.ArrayList;
import java.util.List;

import static rena.toraracreatures.world.util.WorldGenRegistrationHelper.createFeature;

public class TCFeatures {

    public static List<Feature<?>> features = new ArrayList<>();

    public static final TCAbstractMushroomFeature<TCMushroomConfig> DEATH_CAP_MUSHROOM_HUGE1 = createFeature("huge_death_cap_mushroom1", new DeathCapMushroom1(TCMushroomConfig.CODEC.stable()));
    public static final TCAbstractMushroomFeature<TCMushroomConfig> DEATH_CAP_MUSHROOM_HUGE2 = createFeature("huge_death_cap_mushroom2", new DeathCapMushroom2(TCMushroomConfig.CODEC.stable()));
    public static final TCAbstractMushroomFeature<TCMushroomConfig> DEATH_CAP_MUSHROOM_HUGE3 = createFeature("huge_death_cap_mushroom3", new DeathCapMushroom3(TCMushroomConfig.CODEC.stable()));

    public static void init() {
    }
}
