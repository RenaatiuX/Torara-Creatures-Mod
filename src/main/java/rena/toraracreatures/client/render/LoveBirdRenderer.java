package rena.toraracreatures.client.render;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.client.model.LoveBirdModel;
import rena.toraracreatures.entities.enums.LoveBirdVariant;
import rena.toraracreatures.entities.mobs.LoveBirdEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Map;

public class LoveBirdRenderer extends GeoEntityRenderer<LoveBirdEntity> {

    public static final Map<LoveBirdVariant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(LoveBirdVariant.class), (map) -> {
        map.put(LoveBirdVariant.GREEN, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/green.png"));
        map.put(LoveBirdVariant.DARK_BLUE, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/dark_blue.png"));
        map.put(LoveBirdVariant.SKY_BLUE, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/sky_blue.png"));
        map.put(LoveBirdVariant.BROWN, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/brown.png"));
        map.put(LoveBirdVariant.BLUE, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/blue.png"));
        map.put(LoveBirdVariant.LIGHT_BLUE, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/light_blue.png"));
        map.put(LoveBirdVariant.GOLDEN, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/golden.png"));
        map.put(LoveBirdVariant.PURPLE, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/purple.png"));
        map.put(LoveBirdVariant.GRAY, new ResourceLocation(ToraraCreatures.MOD_ID, "textures/entity/love_bird/gray.png"));
    });

    public LoveBirdRenderer(EntityRendererManager rendererManager){
        super(rendererManager, new LoveBirdModel());
    }

    @Override
    public ResourceLocation getTextureLocation(LoveBirdEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderType getRenderType(LoveBirdEntity animatable, float partialTicks, MatrixStack stack,
                                    @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
