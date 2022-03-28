package rena.toraracreatures.world.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.world.features.TCFeatures;

public class WorldGenRegistrationHelper {

    public static <C extends IFeatureConfig, F extends Feature<C>> F createFeature(String id, F feature) {
        ResourceLocation tcID = new ResourceLocation(ToraraCreatures.MOD_ID, id);
        if (Registry.FEATURE.keySet().contains(tcID))
            throw new IllegalStateException("Feature ID: \"" + tcID.toString() + "\" already exists in the Features registry!");

//        Registry.register(Registry.FEATURE, tcID, feature);
        feature.setRegistryName(tcID); //Forge
        TCFeatures.features.add(feature);
        return feature;
    }

    public static <FC extends IFeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF createConfiguredFeature(String id, CF configuredFeature) {
        ResourceLocation tcID = new ResourceLocation(ToraraCreatures.MOD_ID, id);
        if (WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(tcID))
            throw new IllegalStateException("Configured Feature ID: \"" + tcID + "\" already exists in the Configured Features registry!");

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, tcID, configuredFeature);
        return configuredFeature;
    }

}
