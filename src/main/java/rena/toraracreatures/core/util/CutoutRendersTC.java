package rena.toraracreatures.core.util;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import rena.toraracreatures.core.init.BlockInit;

public class CutoutRendersTC {

    public static void renderCutOuts(){

        RenderTypeLookup.setRenderLayer(BlockInit.DEATH_CAP.get(), RenderType.cutout());

    }

}
