package rena.toraracreatures.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.client.model.ManateeModel;
import rena.toraracreatures.entities.mobs.ManateeEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ManateeRender extends GeoEntityRenderer<ManateeEntity> {

    public ManateeRender(EntityRendererManager rendererManager){
        super(rendererManager, new ManateeModel());
        this.shadowRadius = 0.5F;
        this.shadowStrength = 0.5F;
    }

    @Override
    public RenderType getRenderType(ManateeEntity animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation){
        return RenderType.entityTranslucent(getTextureLocation(animatable));

    }


}
