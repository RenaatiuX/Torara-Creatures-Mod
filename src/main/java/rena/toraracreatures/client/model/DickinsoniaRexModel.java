package rena.toraracreatures.client.model;

import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.mobs.DickinsoniaRexEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DickinsoniaRexModel extends AnimatedGeoModel<DickinsoniaRexEntity> {

    public DickinsoniaRexModel() {
    }

    @Override
    public ResourceLocation getModelLocation(DickinsoniaRexEntity object) {
        return object.isBaby() ? new ResourceLocation("toraracreatures", "geo/dickinsonia/dickinsonia_rex_baby.geo.json") : new ResourceLocation("toraracreatures", "geo/dickinsonia/dickinsonia_rex.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DickinsoniaRexEntity object) {
        return object.isBaby() ? new ResourceLocation("toraracreatures", "textures/entity/dickinsonia/dickinsonia_rex_baby.png") : new ResourceLocation("toraracreatures", "textures/entity/dickinsonia/dickinsonia_rex.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DickinsoniaRexEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/dickinsonia_rex.animation.json");
    }
}
