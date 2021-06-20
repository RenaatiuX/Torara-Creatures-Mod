package rena.toraracreatures.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.block.AnalyzerBlock;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ToraraCreatures.MOD_ID);

    public static final RegistryObject<Block> VOLCANIC_ROCK = BLOCKS.register("volcanic_rock",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
            .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
            .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> POLISHED_VOLCANIC_ROCK = BLOCKS.register("polished_volcanic_rock",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CHISELED_VOLCANIC_ROCK = BLOCKS.register("chiseled_volcanic_rock",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> STONE_MICROFOSSIL = BLOCKS.register("stone_microfossil",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> GRAVEL_MICROFOSSIL = BLOCKS.register("gravel_microfossil",
            ()-> new GravelBlock(AbstractBlock.Properties.of(Material.SAND, MaterialColor.STONE)
                    .strength(0.6F)
                    .sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> STROMATOLITE = BLOCKS.register("stromatolite",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    //Fossil
    public static final RegistryObject<Block> RED_SANDSTONE_DICKINSONIA_FOSSIL = BLOCKS.register("red_sandstone_dickinsonia_fossil",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SANDSTONE_DICKINSONIA_FOSSIL = BLOCKS.register("sandstone_dickinsonia_fossil",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> STONE_DICKINSONIA_FOSSIL = BLOCKS.register("stone_dickinsonia_fossil",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> THECTARDIS_ANDESITE_FOSSIL = BLOCKS.register("thectardis_andesite_fossil",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> THECTARDIS_STONE_FOSSIL = BLOCKS.register("thectardis_stone_fossil",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TRIBRACHIDIUM_STONE_FOSSIL = BLOCKS.register("tribrachidium_stone_fossil",
            ()-> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    //Machine
    public static final RegistryObject<Block> ANALYZER = BLOCKS.register("analyzer",
            ()-> new AnalyzerBlock(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY)
                    .strength(3.0f, 6.0F)
                    .requiresCorrectToolForDrops().noOcclusion().sound(SoundType.METAL)));

}
