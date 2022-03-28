package rena.toraracreatures.client.model;

import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.mobs.AgujaceratopsEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AgujaceratopsModel extends AnimatedGeoModel<AgujaceratopsEntity> {

    @Override
    public ResourceLocation getModelLocation(AgujaceratopsEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "geo/agujaceratops.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AgujaceratopsEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/agujaceratops.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AgujaceratopsEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/agujaceratops.animation.json");

    }
}
