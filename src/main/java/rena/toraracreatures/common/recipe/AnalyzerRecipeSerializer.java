package rena.toraracreatures.common.recipe;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AnalyzerRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AnalyzerRecipe>
{
    @Override
    public AnalyzerRecipe fromJson(ResourceLocation recipeId, JsonObject json)
    {
        ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
        Ingredient fossil = Ingredient.fromJson((JSONUtils.isArrayNode(json, "fossil") ? JSONUtils.getAsJsonArray(json, "fossil") : JSONUtils.getAsJsonObject(json, "fossil")));

        return new AnalyzerRecipe(recipeId, fossil);
    }

    @Override
    public AnalyzerRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer)
    {
        ItemStack output = buffer.readItem();
        Ingredient fossil = Ingredient.fromNetwork(buffer);

        return new AnalyzerRecipe(recipeId, fossil);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, AnalyzerRecipe recipe)
    {
        Ingredient input = recipe.getIngredients().get(0);
        input.toNetwork(buffer);

        buffer.writeItemStack(recipe.getResultItem(), false);
    }

}
