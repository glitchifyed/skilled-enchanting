package net.glitchifyed.skilled_enchanting.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class EnchanterScreen extends HandledScreen<EnchanterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(SkilledEnchanting.MODID, "textures/gui/container/enchanter.png");

    public EnchanterScreen(EnchanterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    /*private static Optional<Text> getPositionText(ScreenHandler handler) {
        if (handler instanceof EnchanterScreenHandler) {
            BlockPos pos = ((EnchanterScreenHandler) handler).getPos();
            // for versions 1.18.2 and below, use `new LiteralText`
            return pos != null ? Optional.of(Text.literal("(" + pos.toShortString() + ")")) : Optional.empty();
        } else {
            return Optional.empty();
        }
    }*/

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
