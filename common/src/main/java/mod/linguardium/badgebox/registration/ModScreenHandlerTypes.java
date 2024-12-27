package mod.linguardium.badgebox.registration;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.Util;
import mod.linguardium.badgebox.screen.BadgeBoxContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import static mod.linguardium.badgebox.registration.BadgeBoxRegistrar.SCREEN_HANDLER_TYPES;

public class ModScreenHandlerTypes {

    public static final RegistrySupplier<ScreenHandlerType<BadgeBoxContainerScreenHandler>> BADGE_BOX_SCREEN_HANDLER_TYPE =  SCREEN_HANDLER_TYPES.register(
            Util.id("badgebox"),
            ()->MenuRegistry.ofExtended(BadgeBoxContainerScreenHandler::create)
    );

    public static void init() { }
}
