package rena.toraracreatures.core.init;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.recipe.RecipeAnalyzer;
import rena.toraracreatures.common.recipe.SerializerRecipeAnalyzer;

public class RecipeInit {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ToraraCreatures.MOD_ID);

    public static final RegistryObject<SerializerRecipeAnalyzer> RECIPE_SERIALIZER_ANALYZER = RECIPES.register("recipe_analyzer", () -> new SerializerRecipeAnalyzer(RecipeAnalyzer::new));


}
