package mod.linguardium.badgebox.item;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import mod.linguardium.badgebox.registration.ModDataComponentType;
import mod.linguardium.badgebox.screen.BadgeBoxContainerScreenHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

import static mod.linguardium.badgebox.registration.ModDataComponentType.DEFAULT_ITEM_LIST;


public class BadgeBoxItem extends Item {
    public BadgeBoxItem(Settings settings) {
        super(settings.component(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(),DEFAULT_ITEM_LIST));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        List<ItemStack> inventoryList = stack.get(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get());
        ItemStack ribbonStack = stack.get(ModDataComponentType.BADGEBOX_INVENTORY_RIBBON_COMPONENT.get());
        if (inventoryList == null) return TypedActionResult.fail(stack);
        int slot = user.getInventory().selectedSlot;
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            MenuRegistry.openExtendedMenu(serverPlayerEntity, new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(PacketByteBuf buf) {
                    BadgeBoxContainerScreenHandler.ExtendedScreenHandlerData.PACKET_CODEC.encode(buf,
                            new BadgeBoxContainerScreenHandler.ExtendedScreenHandlerData(ribbonStack != null, slot)
                    );
                }

                @Override
                public Text getDisplayName() {
                    return stack.getOrDefault(DataComponentTypes.CUSTOM_NAME, Text.translatable("gui.badgebox.badge_box_gui.label"));
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new BadgeBoxContainerScreenHandler(syncId, playerInventory, new ComponentStorageInventory(stack, inventoryList, ribbonStack), ribbonStack, slot);
                }
            });
        }
        return TypedActionResult.success(stack);
    }
}
