package rena.toraracreatures.client.model;

import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.mobs.LampreyEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LampreyModel extends AnimatedGeoModel<LampreyEntity> {

    @Override
    public ResourceLocation getModelLocation(LampreyEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "geo/lamprey.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LampreyEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/lamprey.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LampreyEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/lamprey.animation.json");
    }
}
