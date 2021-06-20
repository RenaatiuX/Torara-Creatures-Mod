package rena.toraracreatures.init;

import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.block.AnalyzerBlock;
import rena.toraracreatures.item.ModSpawnEggItem;
import rena.toraracreatures.item.TCPainting;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ToraraCreatures.MOD_ID);

    //Item
    public static final RegistryObject<Item> LIGHT_GRAY_FEATHER = ITEMS.register("light_gray_feather",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRAY_FEATHER = ITEMS.register("gray_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> BLACK_FEATHER = ITEMS.register("black_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> ORANGE_FEATHER = ITEMS.register("orange_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> RED_FEATHER = ITEMS.register("red_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> YELLOW_FEATHER = ITEMS.register("yellow_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> LIGHT_BLUE_FEATHER = ITEMS.register("light_blue_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> BLUE_FEATHER = ITEMS.register("blue_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> BROWN_FEATHER = ITEMS.register("brown_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> LIME_FEATHER = ITEMS.register("lime_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> GREEN_FEATHER = ITEMS.register("green_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> PURPLE_FEATHER = ITEMS.register("purple_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> MAGENTA_FEATHER = ITEMS.register("magenta_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> PINK_FEATHER = ITEMS.register("pink_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> CYAN_FEATHER = ITEMS.register("cyan_feather",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> CAVEASPHAERA = ITEMS.register("caveasphaera",
            ()-> new Item(new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));


    //Food
    public static final RegistryObject<Item> RAW_GOOSE = ITEMS.register("raw_goose",
            ()-> new Item(new Item.Properties().food(new Food.Builder().nutrition(2).saturationMod(0.3f).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3f).build()).tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> COOKED_GOOSE = ITEMS.register("cooked_goose",
            ()-> new Item(new Item.Properties().food(new Food.Builder().nutrition(6).saturationMod(0.6f).build()).tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> RAW_PARROT = ITEMS.register("raw_parrot",
            ()-> new Item(new Item.Properties().food(new Food.Builder().nutrition(2).saturationMod(0.3f).build()).tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> COOKED_PARROT = ITEMS.register("cooked_parrot",
            ()-> new Item(new Item.Properties().food(new Food.Builder().nutrition(6).saturationMod(0.6f).build()).tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> RAW_OSTRICH = ITEMS.register("raw_ostrich",
            ()-> new Item(new Item.Properties().food(new Food.Builder().nutrition(2).saturationMod(0.3f).build()).tab(ToraraCreatures.ITEM_GROUP)));
    public static final RegistryObject<Item> COOKED_OSTRICH = ITEMS.register("cooked_ostrich",
            ()-> new Item(new Item.Properties().food(new Food.Builder().nutrition(6).saturationMod(0.6f).build()).tab(ToraraCreatures.ITEM_GROUP)));


    //Block
    public static final RegistryObject<Item> VOLCANIC_ROCK = ITEMS.register("volcanic_rock",
            ()-> new BlockItem(BlockInit.VOLCANIC_ROCK.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> POLISHED_VOLCANIC_ROCK = ITEMS.register("polished_volcanic_rock",
            ()-> new BlockItem(BlockInit.POLISHED_VOLCANIC_ROCK.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> CHISELED_VOLCANIC_ROCK = ITEMS.register("chiseled_volcanic_rock",
            ()-> new BlockItem(BlockInit.CHISELED_VOLCANIC_ROCK.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> STONE_MICROFOSSIL = ITEMS.register("stone_microfossil",
            ()-> new BlockItem(BlockInit.STONE_MICROFOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> GRAVEL_MICROFOSSIL = ITEMS.register("gravel_microfossil",
            ()-> new BlockItem(BlockInit.GRAVEL_MICROFOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> STROMATOLITE = ITEMS.register("stromatolite",
            ()-> new BlockItem(BlockInit.STROMATOLITE.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));

    //Fossil
    public static final RegistryObject<Item> RED_SANDSTONE_DICKINSONIA_FOSSIL = ITEMS.register("red_sandstone_dickinsonia_fossil",
            ()-> new BlockItem(BlockInit.RED_SANDSTONE_DICKINSONIA_FOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> SANDSTONE_DICKINSONIA_FOSSIL = ITEMS.register("sandstone_dickinsonia_fossil",
            ()-> new BlockItem(BlockInit.SANDSTONE_DICKINSONIA_FOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> STONE_DICKINSONIA_FOSSIL = ITEMS.register("stone_dickinsonia_fossil",
            ()-> new BlockItem(BlockInit.STONE_DICKINSONIA_FOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> THECTARDIS_ANDESITE_FOSSIL = ITEMS.register("thectardis_andesite_fossil",
            ()-> new BlockItem(BlockInit.THECTARDIS_ANDESITE_FOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> THECTARDIS_STONE_FOSSIL = ITEMS.register("thectardis_stone_fossil",
            ()-> new BlockItem(BlockInit.THECTARDIS_STONE_FOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));
    public static final RegistryObject<Item> TRIBRACHIDIUM_STONE_FOSSIL = ITEMS.register("tribrachidium_stone_fossil",
            ()-> new BlockItem(BlockInit.TRIBRACHIDIUM_STONE_FOSSIL.get(), new Item.Properties().tab(ToraraCreatures.BLOCK_GROUP)));

    //Egg
    public static final RegistryObject<Item> DICKINSONIA_REX_SPAWN_EGG = ITEMS.register("dickinsonia_spawn_egg",
            ()-> new ModSpawnEggItem(EntityInit.DICKINSONIA_REX, 1029020, 1037985, new Item.Properties().tab(ToraraCreatures.FOSSIL_GROUP)));
    public static final RegistryObject<Item> GREENLAND_SHARK_SPAWN_EGG = ITEMS.register("greenland_shark_spawn_egg",
            ()-> new ModSpawnEggItem(EntityInit.GREENLAND_SHARK,  7624522, 6378329, new Item.Properties().tab(ToraraCreatures.FOSSIL_GROUP)));

    //Painting
    public static final RegistryObject<TCPainting> TC_PAINTING = ITEMS.register("painting",
            ()-> new TCPainting((new Item.Properties()).tab(ToraraCreatures.ITEM_GROUP)));

    //Machine
    public static final RegistryObject<Item> ANALYZER = ITEMS.register("analyzer",
            ()-> new BlockItem(BlockInit.ANALYZER.get(), new Item.Properties().tab(ToraraCreatures.ITEM_GROUP)));


}
