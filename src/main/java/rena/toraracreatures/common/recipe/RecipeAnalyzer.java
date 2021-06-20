package rena.toraracreatures.common.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.tileentity.AnalyzerTileEntity;
import rena.toraracreatures.core.init.RecipeInit;

public class RecipeAnalyzer implements IRecipe<IInventory>
{
    public static final IRecipeType<IRecipe<IInventory>> RECIPE_TYPE_ANALYZER = IRecipeType.register(ToraraCreatures.MOD_ID + ":recipe_analyzer");

    private final IRecipeSerializer<?> serializer;
    private final IRecipeType<?> type;
    protected final ResourceLocation location;
    protected final String group;
    protected final ItemStack input;
    protected final AnalyzerResult results;

    public RecipeAnalyzer(ResourceLocation location, String group, ItemStack input, AnalyzerResult results)
    {
        this.serializer = RecipeInit.RECIPE_SERIALIZER_ANALYZER.get();
        this.type = RecipeAnalyzer.RECIPE_TYPE_ANALYZER;
        this.location = location;
        this.group = group;
        this.input = input;
        this.results = results;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return this.serializer;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return this.type;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.location;
    }

    @Override
    public String getGroup()
    {
        return this.group;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(Ingredient.of(this.input.copy()));
        return nonNullList;
    }

    @Override
    public ItemStack getResultItem()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack assemble(IInventory iInventory)
    {
        return this.results.next().copy();
    }

    @Override
    public boolean matches(IInventory iInventory, World worldIn)
    {
        return this.input.sameItem(iInventory.getItem(AnalyzerTileEntity.SLOT_FOSSIL));
    }
}
