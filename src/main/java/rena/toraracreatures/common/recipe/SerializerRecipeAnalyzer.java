package rena.toraracreatures.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class SerializerRecipeAnalyzer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeAnalyzer>
{

    protected final IRecipeFactory<RecipeAnalyzer> factory;

    public SerializerRecipeAnalyzer(IRecipeFactory<RecipeAnalyzer> factory)
    {
        this.factory = factory;
    }

    @Override
    public RecipeAnalyzer fromJson(ResourceLocation location, JsonObject json) {
        String group = JSONUtils.getAsString(json, "group", "");
        ItemStack input = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "input"));

        AnalyzerResult.Builder builder = new AnalyzerResult.Builder();
        JsonArray resultArray = JSONUtils.getAsJsonArray(json, "results");
        for (JsonElement resultElement : resultArray)
        {
            JsonObject resultEntry = resultElement.getAsJsonObject();
            builder.add(JSONUtils.isValidNode(resultEntry, "weight") ? JSONUtils.convertToFloat(resultEntry.get("weight"), "weight") : 1.0F, ShapedRecipe.itemFromJson(resultEntry));
        }

        return this.factory.create(location, group, input, builder.build());
    }

    @Nullable
    @Override
    public RecipeAnalyzer fromNetwork(ResourceLocation location, PacketBuffer buffer) {
        String group = buffer.readUtf(32767);
        ItemStack input = buffer.readItem();
        AnalyzerResult results = AnalyzerResult.read(buffer);

        return this.factory.create(location, group, input, results);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, RecipeAnalyzer recipe) {
        buffer.writeUtf(recipe.group);
        buffer.writeItem(recipe.input);
        recipe.results.write(buffer);
    }

    public interface IRecipeFactory<T extends RecipeAnalyzer>
    {
        T create(ResourceLocation location, String group, ItemStack input, AnalyzerResult results);
    }
}
