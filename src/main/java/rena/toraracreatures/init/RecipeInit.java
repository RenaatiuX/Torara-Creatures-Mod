package rena.toraracreatures.init;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.recipe.AnalyzerRecipe;
import rena.toraracreatures.common.recipe.AnalyzerRecipeSerializer;

public class RecipeInit {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ToraraCreatures.MOD_ID);

    public static final IRecipeSerializer<AnalyzerRecipe> ANALYZER_RECIPE_SERIALIZER = new AnalyzerRecipeSerializer();
    public static final IRecipeType<AnalyzerRecipe> ANALYZER_RECIPE = registerType(ToraraCreatures.rL("analyzer"));
    public static final RegistryObject<IRecipeSerializer<?>> ANALYZER_SERIALIZER = RECIPE.register("analyzer", () -> ANALYZER_RECIPE_SERIALIZER);



    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T>
    {
        @Override
        public String toString()
        {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }

    private static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeId)
    {
        return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
    }

}
