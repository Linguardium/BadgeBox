package mod.linguardium.badgebox.common;

import mod.linguardium.badgebox.common.registration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BadgeBoxCommonInitializer {
    public static final String MOD_ID = "badgebox";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        ModScreenHandlerTypes.init();
        ModDataComponentType.init();
        ModItems.init();
        ModItemGroups.init();
        ModRecipeSerializers.init();
    }
}
