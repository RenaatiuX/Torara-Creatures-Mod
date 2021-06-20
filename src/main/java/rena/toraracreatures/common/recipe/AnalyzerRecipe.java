package rena.toraracreatures.common.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rena.toraracreatures.init.ItemInit;
import rena.toraracreatures.init.RecipeInit;

import java.util.Random;

public class AnalyzerRecipe implements IRecipe<IInventory> {

    private final ResourceLocation id;
    private Ingredient fossil;
    public final ItemStack output1 = ItemInit.RAW_GOOSE.get().getDefaultInstance();
    public final ItemStack output2 = Items.SAND.getDefaultInstance();
    public final ItemStack output3 = Items.BONE_MEAL.getDefaultInstance();
    public final ItemStack output4 = Items.DIRT.getDefaultInstance();

    public AnalyzerRecipe(ResourceLocation id, Ingredient fossil)
    {
        this.id = id;
        this.fossil = fossil;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn)
    {
        if(this.fossil.test(inv.getItem(0)))
        {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(IInventory inv)
    {
        Random rand = new Random();
        int chance = rand.nextInt(4);

        if(chance == 0)
        {
            return this.output1;
        }
        if(chance == 1)
        {
            return this.output2;
        }
        if(chance == 2)
        {
            return this.output3;
        }
        else {
            return this.output4;
        }
    }

    @Override
    public ItemStack getResultItem()
    {
        Random rand = new Random();
        int chance = rand.nextInt(4);

        if(chance == 0)
        {
            return this.output1;
        }
        else if(chance == 1)
        {
            return this.output2;
        }
        else if(chance == 2)
        {
            return this.output3;
        }
        else
        {
            return this.output4;
        }
    }

    public NonNullList<ItemStack> getOutputs()
    {
        NonNullList<ItemStack> outputs = NonNullList.create();
        outputs.add(this.output1);
        outputs.add(this.output2);
        outputs.add(this.output3);
        outputs.add(this.output4);
        return outputs;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    public int getAnalyzerTime()
    {
        return 300;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return RecipeInit.ANALYZER_SERIALIZER.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.fossil);
        return nonnulllist;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return false;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return RecipeInit.ANALYZER_RECIPE;
    }
}
