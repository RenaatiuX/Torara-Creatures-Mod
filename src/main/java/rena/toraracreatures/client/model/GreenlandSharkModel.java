package rena.toraracreatures.client.model;

import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.mobs.GreenlandSharkEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GreenlandSharkModel extends AnimatedGeoModel<GreenlandSharkEntity> {

    public GreenlandSharkModel(){

    }

    @Override
    public ResourceLocation getModelLocation(GreenlandSharkEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "geo/greenland_shark.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GreenlandSharkEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/greenland_shark.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GreenlandSharkEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/greenland_shark.animation.json");
    }



}
