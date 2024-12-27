package mod.linguardium.badgebox.registration;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import mod.linguardium.badgebox.BadgeBoxCommon;
import mod.linguardium.badgebox.screen.client.BadgeBoxContainerScreen;

public class ModHandledScreens {
    public static void init() {
        ClientLifecycleEvent.CLIENT_SETUP.register(client -> {
            BadgeBoxCommon.LOGGER.info("Registering screen factories");
            MenuRegistry.registerScreenFactory(ModScreenHandlerTypes.BADGE_BOX_SCREEN_HANDLER_TYPE.get(), BadgeBoxContainerScreen::new);
    });
    }

}
