package rena.toraracreatures.client.model;

import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.client.render.LoveBirdRenderer;
import rena.toraracreatures.entities.mobs.LoveBirdEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class LoveBirdModel extends AnimatedGeoModel<LoveBirdEntity> {

    @Override
    public ResourceLocation getModelLocation(LoveBirdEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "geo/love_bird.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LoveBirdEntity object) {
        return LoveBirdRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LoveBirdEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/love_bird.animation.json");
    }
}
