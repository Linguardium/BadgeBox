package mod.linguardium.badgebox.neoforge;

import mod.linguardium.badgebox.common.BadgeBoxCommonInitializer;
import net.neoforged.fml.common.Mod;


@Mod(BadgeBoxCommonInitializer.MOD_ID)
public final class BadgeBoxNeoForge {
    public BadgeBoxNeoForge() {
        // Run our common setup.
        BadgeBoxCommonInitializer.init();
    }
}
