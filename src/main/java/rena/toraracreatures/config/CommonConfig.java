package rena.toraracreatures.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public final ForgeConfigSpec.IntValue dickinsoniaSpawnWeight;
    public final ForgeConfigSpec.IntValue dickinsoniaSpawnRolls;

    public CommonConfig(final ForgeConfigSpec.Builder builder) {
        dickinsoniaSpawnWeight = buildInt(builder, "dickinsoniaSpawnWeight", "spawns", ToraraConfig.dickinsoniaSpawnWeight, 0, 1000, "Spawn Weight, added to a pool of other mobs for each biome. Higher number = higher chance of spawning. 0 = disable spawn");
        dickinsoniaSpawnRolls = buildInt(builder, "dickinsoniaSpawnWeightSpawnRolls", "spawns", ToraraConfig.dickinsoniaSpawnRolls, 0, Integer.MAX_VALUE, "Random roll chance to enable mob spawning. Higher number = lower chance of spawning");

    }

    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, String catagory, int defaultValue, int min, int max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

}
