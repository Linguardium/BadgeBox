package mod.linguardium.badgebox.screen.client;

import mod.linguardium.badgebox.screen.BadgeBoxContainerScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BadgeBoxContainerScreen extends HandledScreen<BadgeBoxContainerScreenHandler> {
    public static final Identifier BACKGROUND = Identifier.of("badgebox","textures/screens/badges_cobble_1.png");
    public static final Identifier BACKGROUND_WITH_RIBBON_SLOT = Identifier.of("badgebox","textures/screens/badges_cobble_2.png");
    public static final int BACKGROUND_WIDTH=184;
    public static final int BACKGROUND_HEIGHT=161;
    public BadgeBoxContainerScreen(BadgeBoxContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 184;
        this.backgroundHeight = 161;
        this.height = 167;
        this.width = 184;
        this.titleY = 12;
        this.titleX = 10;
        this.playerInventoryTitleX = 12;
        this.playerInventoryTitleY = this.backgroundHeight - 90;
//        guiGraphics.drawString(this.font, Component.translatable("gui.badgebox.badge_box_gui.label_league_badges"), 10, 12, -1, false);
//        guiGraphics.drawString(this.font, Component.translatable("gui.badgebox.badge_box_gui.label_inventory"), 12, 71, -1, false);

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int left = (this.width - this.backgroundWidth) / 2;
        int top = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(getBackgroundTexture(), left, top+5, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0xFFFFFFFF, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 0xFFFFFFFF, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private Identifier getBackgroundTexture() {
        if (this.getScreenHandler().hasRibbonSlot()) {
            return BACKGROUND_WITH_RIBBON_SLOT;
        }
        return BACKGROUND;
    }
}
