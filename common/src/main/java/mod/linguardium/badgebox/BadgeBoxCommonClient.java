package mod.linguardium.badgebox;

import mod.linguardium.badgebox.registration.ModHandledScreens;

public class BadgeBoxCommonClient {
    public static void init() {
        BadgeBoxCommon.LOGGER.info("Registering Screens");
        ModHandledScreens.init();
    }
}
