package mod.linguardium.badgebox.common.client.registration;

import dev.architectury.registry.menu.MenuRegistry;
import mod.linguardium.badgebox.common.BadgeBoxCommonInitializer;
import mod.linguardium.badgebox.common.client.screen.BadgeBoxContainerScreen;
import mod.linguardium.badgebox.common.registration.ModScreenHandlerTypes;

public class ModHandledScreens {
    public static void init() {
        BadgeBoxCommonInitializer.LOGGER.info("Registering screen type to screen");
        MenuRegistry.registerScreenFactory(ModScreenHandlerTypes.BADGE_BOX_SCREEN_HANDLER_TYPE.get(), BadgeBoxContainerScreen::new);
    }

}
