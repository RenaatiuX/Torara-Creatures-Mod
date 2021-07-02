package rena.toraracreatures.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.client.gui.AnalyzerScreen;
import rena.toraracreatures.client.render.DickinsoniaRexRender;
import rena.toraracreatures.client.render.GreenlandSharkRender;
import rena.toraracreatures.client.render.WallFossilRenderer;
import rena.toraracreatures.config.ToraraConfig;
import rena.toraracreatures.core.init.ContainerInit;
import rena.toraracreatures.entities.mobs.DickinsoniaRexEntity;
import rena.toraracreatures.entities.mobs.GreenlandSharkEntity;
import rena.toraracreatures.core.init.EntityInit;

@Mod.EventBusSubscriber(modid = ToraraCreatures.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GREENLAND_SHARK,
                GreenlandSharkRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DICKINSONIA_REX,
                DickinsoniaRexRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WALL_FOSSIL_ENTITY,
                WallFossilRenderer::new);

        //Machine
        ScreenManager.register(ContainerInit.ANALYZER_CONTAINER.get(), AnalyzerScreen::new);
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.GREENLAND_SHARK, GreenlandSharkEntity.createAttributes().build());
        event.put(EntityInit.DICKINSONIA_REX, DickinsoniaRexEntity.createAttributes().build());
    }

}
