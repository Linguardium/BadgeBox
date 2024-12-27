package mod.linguardium.badgebox;

import net.minecraft.util.Identifier;

import static mod.linguardium.badgebox.BadgeBoxCommon.MOD_ID;

public class Util {

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
