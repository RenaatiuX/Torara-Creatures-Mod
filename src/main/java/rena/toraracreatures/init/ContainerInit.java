package rena.toraracreatures.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.container.AnalyzerContainer;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, ToraraCreatures.MOD_ID);

    public static final RegistryObject<ContainerType<AnalyzerContainer>> ANALYZER_CONTAINER = CONTAINER.register("analyzer_container",
            () -> IForgeContainerType.create(AnalyzerContainer::new));


}
