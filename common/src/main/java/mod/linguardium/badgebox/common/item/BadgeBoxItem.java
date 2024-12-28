package mod.linguardium.badgebox.common.item;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import mod.linguardium.badgebox.common.item.components.BadgeInventoryComponent;
import mod.linguardium.badgebox.common.registration.ModDataComponentType;
import mod.linguardium.badgebox.common.screen.BadgeBoxContainerScreenHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class BadgeBoxItem extends Item {
    public BadgeBoxItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BadgeInventoryComponent component = stack.get(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get());
        if (component == null) return TypedActionResult.fail(stack);
        int slot = user.getInventory().selectedSlot;
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            MenuRegistry.openExtendedMenu(serverPlayerEntity, new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(PacketByteBuf buf) {
                    BadgeBoxContainerScreenHandler.ExtendedScreenHandlerData.PACKET_CODEC.encode(buf,
                            new BadgeBoxContainerScreenHandler.ExtendedScreenHandlerData(component.hasRibbonSlot(), slot)
                    );
                }

                @Override
                public Text getDisplayName() {
                    return stack.getOrDefault(DataComponentTypes.CUSTOM_NAME, Text.translatable("gui.badgebox.badge_box_gui.label"));
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    Inventory badgeInventory = component.asInventory(stack);
                    return new BadgeBoxContainerScreenHandler(syncId, playerInventory, badgeInventory, component.hasRibbonSlot(), slot);
                }
            });
        }
        return TypedActionResult.success(stack);
    }

}
