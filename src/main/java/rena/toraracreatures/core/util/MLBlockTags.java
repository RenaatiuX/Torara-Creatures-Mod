package rena.toraracreatures.core.util;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class MLBlockTags {

    public static final String MOD_LOADER_TAG_TARGET = "forge";

    public static final ITag.INamedTag<Block> DIRT = createTag("dirt");

    public static ITag.INamedTag<Block> createTag(String path) {
        return BlockTags.bind(new ResourceLocation(MOD_LOADER_TAG_TARGET, path).toString());
    }

}
