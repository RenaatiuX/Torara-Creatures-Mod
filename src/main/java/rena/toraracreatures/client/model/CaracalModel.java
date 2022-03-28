package rena.toraracreatures.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.mobs.CaracalEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CaracalModel extends AnimatedGeoModel<CaracalEntity> {

    @Override
    public ResourceLocation getModelLocation(CaracalEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "geo/caracal.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CaracalEntity object) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/caracal.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CaracalEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/caracal.animation.json");
    }

    @Override
    public void setLivingAnimations(CaracalEntity entity, Integer uniqueID, AnimationEvent customPredicate){
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (entity.isInSittingPose()) {
            IBone head = this.getAnimationProcessor().getBone("Head");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * 0.017453292F);
            head.setRotationY(extraData.netHeadYaw * 0.017453292F);
        }

        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * 0.017453292F);
        head.setRotationY(extraData.netHeadYaw * 0.017453292F);

    }
}
