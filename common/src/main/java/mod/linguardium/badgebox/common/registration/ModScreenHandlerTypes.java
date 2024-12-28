package mod.linguardium.badgebox.common.registration;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.common.Util;
import mod.linguardium.badgebox.common.screen.BadgeBoxContainerScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;

import static mod.linguardium.badgebox.common.registration.BadgeBoxRegistrar.SCREEN_HANDLER_TYPES;

public class ModScreenHandlerTypes {

    public static final RegistrySupplier<ScreenHandlerType<BadgeBoxContainerScreenHandler>> BADGE_BOX_SCREEN_HANDLER_TYPE =  SCREEN_HANDLER_TYPES.register(
            Util.id("badgebox"),
            ()->MenuRegistry.ofExtended((id, inventory, buf) ->  createBadgeScreenHandler(id, inventory, buf))
    );

    public static void init() { }
    public static BadgeBoxContainerScreenHandler createBadgeScreenHandler(int id, PlayerInventory inventory, PacketByteBuf buf) {
        return BadgeBoxContainerScreenHandler.create(id, inventory, buf);
    }
}
