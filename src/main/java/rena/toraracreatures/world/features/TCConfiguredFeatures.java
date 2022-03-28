package rena.toraracreatures.world.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import rena.toraracreatures.core.init.BlockInit;
import rena.toraracreatures.util.config.TCMushroomConfig;

import static rena.toraracreatures.world.util.WorldGenRegistrationHelper.createConfiguredFeature;

public class TCConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> DEATH_CAP_MUSHROOM = createConfiguredFeature("death_cap_mushroom", Feature.FLOWER.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockInit.DEATH_CAP.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(64).noProjection().build()));


    public static final ConfiguredFeature<TCMushroomConfig, ?> DEATH_CAP_MUSHROOM_HUGE1 = createConfiguredFeature("huge_death_cap_mushroom1", TCFeatures.DEATH_CAP_MUSHROOM_HUGE1.configured(new TCMushroomConfig.Builder().setStemDirtBlock(BlockInit.DEATH_CAP_DIRT_STEM.get()).setStemBlock(BlockInit.DEATH_CAP_STEM.get()).setMushroomBlock(BlockInit.DEATH_CAP_BLOCK.get()).setMinHeight(6).setMaxHeight(12).build()));
    public static final ConfiguredFeature<TCMushroomConfig, ?> DEATH_CAP_MUSHROOM_HUGE2= createConfiguredFeature("huge_death_cap_mushroom2", TCFeatures.DEATH_CAP_MUSHROOM_HUGE1.configured(new TCMushroomConfig.Builder().setStemDirtBlock(BlockInit.DEATH_CAP_DIRT_STEM.get()).setStemBlock(BlockInit.DEATH_CAP_STEM.get()).setMushroomBlock(BlockInit.DEATH_CAP_BLOCK.get()).setMinHeight(6).setMaxHeight(12).build()));
    public static final ConfiguredFeature<TCMushroomConfig, ?> DEATH_CAP_MUSHROOM_HUGE3 = createConfiguredFeature("huge_death_cap_mushroom3", TCFeatures.DEATH_CAP_MUSHROOM_HUGE1.configured(new TCMushroomConfig.Builder().setStemDirtBlock(BlockInit.DEATH_CAP_DIRT_STEM.get()).setStemBlock(BlockInit.DEATH_CAP_STEM.get()).setMushroomBlock(BlockInit.DEATH_CAP_BLOCK.get()).setMinHeight(6).setMaxHeight(12).build()));



    public static final ConfiguredFeature<?, ?> RANDOM_DEATH_CAP_TREES = createConfiguredFeature("death_cap_trees", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(
            DEATH_CAP_MUSHROOM_HUGE1.weighted(0.35F),
            DEATH_CAP_MUSHROOM_HUGE2.weighted(0.35F)),
            DEATH_CAP_MUSHROOM_HUGE3)).decorated(Placement.DARK_OAK_TREE.configured(IPlacementConfig.NONE)));
}
