package rena.toraracreatures.client.model;

import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.mobs.ManateeEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ManateeModel extends AnimatedGeoModel<ManateeEntity> {

    @Override
    public ResourceLocation getModelLocation(ManateeEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "geo/manatee.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ManateeEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/manatee.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ManateeEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/manatee.animation.json");
    }
}
