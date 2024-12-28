package mod.linguardium.badgebox.common;

import net.minecraft.util.Identifier;

import static mod.linguardium.badgebox.common.BadgeBoxCommonInitializer.MOD_ID;

public class Util {

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
