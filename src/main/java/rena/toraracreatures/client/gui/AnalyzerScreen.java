package rena.toraracreatures.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.container.AnalyzerContainer;
import rena.toraracreatures.common.tileentity.AnalyzerTileEntity;

import javax.annotation.Nullable;

public class AnalyzerScreen <T extends AnalyzerContainer> extends ContainerScreen<T>
{
    private static final ResourceLocation TEXTURE = ToraraCreatures.rL("textures/gui/analyzer.png");
    private final AnalyzerContainer container;
    private final AnalyzerTileEntity tile;

    public AnalyzerScreen(T container, PlayerInventory playerInv, ITextComponent text)
    {
        super(container, playerInv, text);
        this.container = container;
        this.tile = container.tile;
    }

    @Override
    public void render(MatrixStack stack, int i1, int i2, float f)
    {
        this.renderBackground(stack);
        super.render(stack, i1, i2, f);
        this.renderTooltip(stack, i1, i2);
    }

    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int x, int y)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        this.blit(stack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

        int progress = this.container.getProgress();
        this.blit(stack, this.leftPos + 75, this.topPos + 37, 176, 0, progress + 1, 16);
    }


}
