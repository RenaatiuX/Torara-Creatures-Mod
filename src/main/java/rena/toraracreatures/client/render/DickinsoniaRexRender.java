package rena.toraracreatures.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import rena.toraracreatures.client.model.DickinsoniaRexModel;
import rena.toraracreatures.entities.mobs.DickinsoniaRexEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class DickinsoniaRexRender extends GeoEntityRenderer<DickinsoniaRexEntity> {

    private boolean isChild;

    public DickinsoniaRexRender(EntityRendererManager renderManager) {
        super(renderManager, new DickinsoniaRexModel());
        this.shadowRadius = 0.7F;
    }

    public void renderEarly(DickinsoniaRexEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.rtb = renderTypeBuffer;
        this.whTexture = this.getTextureLocation(animatable);
        this.isChild = animatable.isBaby();
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }


    @Override
    public RenderType getRenderType(DickinsoniaRexEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
