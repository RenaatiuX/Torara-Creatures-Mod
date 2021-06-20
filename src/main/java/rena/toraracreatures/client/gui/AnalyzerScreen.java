package rena.toraracreatures.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.common.container.AnalyzerContainer;

public class AnalyzerScreen extends ContainerScreen<AnalyzerContainer>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToraraCreatures.MOD_ID, "textures/gui/analyzer.png");

    public AnalyzerScreen(AnalyzerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
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
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        this.blit(stack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

        int progress = this.menu.getWorkProgressionScaled(26);
        this.blit(stack, this.leftPos + 60, this.topPos + 44, 178, 2, progress, 16);

    }

}
