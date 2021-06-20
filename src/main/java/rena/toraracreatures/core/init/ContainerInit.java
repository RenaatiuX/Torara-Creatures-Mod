package rena.toraracreatures.core.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.container.AnalyzerContainer;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = ToraraCreatures.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, ToraraCreatures.MOD_ID);

    public static final RegistryObject<ContainerType<AnalyzerContainer>> ANALYZER_CONTAINER = CONTAINER.register("analyzer_container", () -> IForgeContainerType.create(AnalyzerContainer::new));

    @SuppressWarnings("rawtypes")
    public static ContainerType register(ContainerType type, String name) {
        type.setRegistryName(name);
        return type;
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        try {
            for (Field f : ContainerInit.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof ContainerType) {
                    event.getRegistry().register((ContainerType) obj);
                } else if (obj instanceof ContainerType[]) {
                    for (ContainerType container : (ContainerType[]) obj) {
                        event.getRegistry().register(container);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
