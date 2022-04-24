package rena.toraracreatures.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.client.gui.AnalyzerScreen;
import rena.toraracreatures.client.render.*;
import rena.toraracreatures.core.init.ContainerInit;
import rena.toraracreatures.core.init.EntityInit;
import rena.toraracreatures.core.util.CutoutRendersTC;

@Mod.EventBusSubscriber(modid = ToraraCreatures.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event)
    {
        ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GREENLAND_SHARK,
                GreenlandSharkRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DICKINSONIA_REX,
                DickinsoniaRexRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WALL_FOSSIL_ENTITY,
                WallFossilRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.AGUJACERATOPS,
                AgujaceratopsRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.LAMPREY,
                LampreyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MANATEE,
                ManateeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CARACAL,
                CaracalRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.PHEASANT,
                PheasantRender::new);


        //Machine
        ScreenManager.register(ContainerInit.ANALYZER_CONTAINER.get(), AnalyzerScreen::new);

        //item
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.PHEASANT_EGG,
                manager -> new SpriteRenderer<>(manager, itemRendererIn));

        //Render
        CutoutRendersTC.renderCutOuts();
    }

}
