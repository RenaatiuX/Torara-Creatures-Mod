package rena.toraracreatures.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.client.model.CaracalModel;
import rena.toraracreatures.entities.mobs.CaracalEntity;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;


public class CaracalRender extends GeoEntityRenderer<CaracalEntity> {

    public CaracalRender(EntityRendererManager rendererManager){
        super(rendererManager, new CaracalModel());
        this.shadowRadius = 0.5F;
        this.shadowStrength = 0.5F;
    }

    @Override
    public RenderType getRenderType(CaracalEntity animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation){
        return RenderType.entityTranslucent(getTextureLocation(animatable));

    }

    @Override
    public void render(GeoModel model, CaracalEntity animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if(animatable.isBaby()){
            matrixStackIn.scale(0.7F, 0.7F, 0.7F);
        }
            matrixStackIn.scale(1.0F, 1.0F, 1.0F);;

            super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red,green, blue, alpha);
    }
}
