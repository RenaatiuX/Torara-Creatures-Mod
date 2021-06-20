package rena.toraracreatures;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rena.toraracreatures.core.init.*;
import software.bernie.geckolib3.GeckoLib;


@Mod(ToraraCreatures.MOD_ID)
@Mod.EventBusSubscriber(modid = ToraraCreatures.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToraraCreatures {

    public static ToraraCreatures instance;

    public static final String NAME = "Torara Creatures";
    public static final String MOD_ID = "toraracreatures";
    public static final Logger LOGGER = LogManager.getLogger();

    public static TranslationTextComponent tTC(String displayText)
    {
        return new TranslationTextComponent(MOD_ID + "." + displayText);
    }

    public static ResourceLocation rL(String id)
    {
        return new ResourceLocation(MOD_ID, id);
    }


    public ToraraCreatures() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext context = ModLoadingContext.get();
        modEventBus.addListener(this::setup);

        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        ContainerInit.CONTAINER.register(modEventBus);
        RecipeInit.RECIPES.register(modEventBus);
        TileEntityInit.TILE_ENTITY_TYPES.register(modEventBus);
        EntityInit.register();

        GeckoLib.initialize();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup("toraracreatures_items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.BLACK_FEATHER.get());
        }
    };

    public static final ItemGroup BLOCK_GROUP = new ItemGroup("toraracreatures_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.VOLCANIC_ROCK.get());
        }
    };

    public static final ItemGroup FOSSIL_GROUP = new ItemGroup("toraracreatures_eggs") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.BROWN_FEATHER.get());
        }
    };


}
