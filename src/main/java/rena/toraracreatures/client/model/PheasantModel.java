package rena.toraracreatures.client.model;

import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.PheasantEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class PheasantModel extends AnimatedGeoModel<PheasantEntity> {

    private static final ResourceLocation TEXTURE_BABY = new ResourceLocation(ToraraCreatures.MOD_ID,"textures/entity/pheasant/pheasant_baby.png");
    private static final ResourceLocation TEXTURE_MALE = new ResourceLocation(ToraraCreatures.MOD_ID,"textures/entity/pheasant/pheasant_male.png");
    private static final ResourceLocation TEXTURE_FEMALE = new ResourceLocation(ToraraCreatures.MOD_ID,"textures/entity/pheasant/pheasant_female.png");
    private static final ResourceLocation TEXTURE_RARE = new ResourceLocation(ToraraCreatures.MOD_ID,"textures/entity/pheasant/pheasant_gold.png");



    @Override
    public ResourceLocation getModelLocation(PheasantEntity object) {
        return object.isBaby() ? new ResourceLocation(ToraraCreatures.MOD_ID, "geo/pheasant/pheasant_baby.geo.json") : new ResourceLocation(ToraraCreatures.MOD_ID, "geo/pheasant/pheasant.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PheasantEntity object) {
        return object.isBaby() ? TEXTURE_BABY : object.getVariant() == 0 ? TEXTURE_MALE : TEXTURE_FEMALE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PheasantEntity animatable) {
        return new ResourceLocation(ToraraCreatures.MOD_ID, "animations/pheasant.animation.json");
    }

    @Override
    public void setLivingAnimations(PheasantEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * 0.017453292F);
        head.setRotationY(extraData.netHeadYaw * 0.017453292F);


    }
}
