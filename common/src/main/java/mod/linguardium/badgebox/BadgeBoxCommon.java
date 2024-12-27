package mod.linguardium.badgebox;

import mod.linguardium.badgebox.registration.ModDataComponentType;
import mod.linguardium.badgebox.registration.ModItemGroups;
import mod.linguardium.badgebox.registration.ModItems;
import mod.linguardium.badgebox.registration.ModScreenHandlerTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BadgeBoxCommon {
    public static final String MOD_ID = "badgebox";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        ModScreenHandlerTypes.init();
        ModDataComponentType.init();
        ModItems.init();
        ModItemGroups.init();
    }
}
